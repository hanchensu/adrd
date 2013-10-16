// Copyright 2012 Cloudera Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#ifndef IMPALA_UTIL_DICT_ENCODING_H
#define IMPALA_UTIL_DICT_ENCODING_H

#include <map>

#include <boost/foreach.hpp>
#include <boost/scoped_ptr.hpp>
#include <boost/unordered_map.hpp>

#include "exec/parquet-common.h"
#include "runtime/mem-pool.h"
#include "runtime/string-value.h"
#include "util/rle-encoding.h"

namespace impala {

// See the dictionary encoding section of https://github.com/Parquet/parquet-format.
// This class supports dictionary encoding of all Impala types.
// The encoding supports streaming encoding. Values are encoded as they are added while
// the dictionary is being constructed. At any time, the buffered values can be
// written out with the current dictionary size. More values can then be added to
// the encoder, including new dictionary entries.
// TODO: if the dictionary was made to be ordered, the dictionary would compress better.
// Add this to the spec as future improvement.
template<typename T> 
class DictEncoder {
 public:
  DictEncoder(const std::vector<MemLimit*>* limits)
    : dict_encoded_size_(0),
      pool_(new MemPool(limits)) {
  }

  ~DictEncoder() {
    DCHECK(buffered_indices_.empty());
  }

  // Encode value. Returns the number of bytes added to the dictionary page length (will
  // be 0 if this value is already in the dictionary). Note that this does not actually
  // write any data, just buffers the value's index to be written later.
  int Put(const T& value);

  // Writes out the encoded dictionary to buffer. buffer must be preallocated to
  // dict_encoded_size() bytes.
  void WriteDict(uint8_t* buffer);

  // Writes out any buffered indices to buffer preceded by the bit width of this data and
  // clears the buffered indices, meaning it cannot be called multiple times for the
  // same buffer. buffer must be preallocated with buffer_len bytes. Use
  // EstimatedDataEncodedSize() to size buffer.
  int WriteData(uint8_t* buffer, int buffer_len);

  // Returns a conservative estimate of the number of bytes needed to encode the buffered
  // indices. Used to size the buffer passed to WriteData.
  // TODO: better estimate
  int EstimatedDataEncodedSize() { return 1 + bit_width() * buffered_indices_.size(); }

  // The number of entries in the dictionary.
  int num_entries() { return dict_index_.size(); }

  // The minimum bit width required to encode the currently buffered indices.
  int bit_width();

  int dict_encoded_size() { return dict_encoded_size_; }

 private:
  // Dictionary mapping value to index (i.e. the number used to encode this value in the
  // data).
  typedef boost::unordered_map<T,int> ValuesIndex;
  ValuesIndex dict_index_;

  // The values of dict_ in order of index (essentially the reverse mapping). Used to
  // write dictionary page.
  std::vector<T> dict_;

  // Indices that have not yet be written out by WriteData().
  std::vector<int> buffered_indices_;

  // The number of bytes needed to encode the dictionary.
  int dict_encoded_size_;

  // Pool to store StringValue data
  boost::scoped_ptr<MemPool> pool_;

  // Adds value to dict_ and updates dict_encoded_size_. Returns the
  // number of bytes added to dict_encoded_size_.
  // *index is the output parameter and is the index into the dict_ for 'value'
  int AddToDict(const T& value, int* index);
};

// Decoder class for dictionary encoded data. This class does not allocate any
// buffers. The input buffers (dictionary buffer and RLE buffer) must be maintained
// by the caller and valid as long as this object is.
template<typename T>
class DictDecoder {
 public:
  // The input buffer containing the dictionary.  'dict_len' is the byte length
  // of dict_buffer.
  // For string data, the decoder returns StringValues with data directly from
  // dict_buffer (i.e. no copies).
  DictDecoder(uint8_t* dict_buffer, int dict_len);

  // The rle encoded indices into the dictionary.
  void SetData(uint8_t* buffer, int buffer_len) {
    DCHECK_GT(buffer_len, 0);
    uint8_t bit_width = *buffer;
    DCHECK_GT(bit_width, 0);
    ++buffer;
    --buffer_len;
    data_decoder_.reset(new RleDecoder(buffer, buffer_len, bit_width));
  }

  // Returns the next value.  Returns false if the data is invalid.
  // For StringValues, this does not make a copy of the data.  Instead,
  // the string data is from the dictionary buffer passed into the c'tor.
  bool GetValue(T* value);

 private:
  std::vector<T> dict_;
  boost::scoped_ptr<RleDecoder> data_decoder_;
};

template<typename T>
inline int DictEncoder<T>::Put(const T& value) {
  int index;
  typename ValuesIndex::iterator it = dict_index_.find(value);
  int bytes_added = 0;
  if (it == dict_index_.end()) {
    bytes_added = AddToDict(value, &index);
  } else {
    index = it->second;
  }
  buffered_indices_.push_back(index);
  return bytes_added;
}

template<typename T>
inline int DictEncoder<T>::AddToDict(const T& value, int* index) {
  *index = dict_index_.size();
  dict_index_[value] = *index;
  dict_.push_back(value);
  int bytes_added = ParquetPlainEncoder::ByteSize(value);
  dict_encoded_size_ += bytes_added;
  return bytes_added;
}

template<>
inline int DictEncoder<StringValue>::AddToDict(const StringValue& value, int* index) {
  char* ptr_copy = reinterpret_cast<char*>(pool_->Allocate(value.len));
  memcpy(ptr_copy, value.ptr, value.len);
  StringValue sv(ptr_copy, value.len);
  *index = dict_index_.size();
  dict_index_[sv] = *index;
  dict_.push_back(sv);
  int bytes_added = ParquetPlainEncoder::ByteSize(sv);
  dict_encoded_size_ += bytes_added;
  return bytes_added;
}

template<typename T>
inline bool DictDecoder<T>::GetValue(T* value) {
  DCHECK(data_decoder_.get() != NULL);
  int index;
  bool result = data_decoder_->Get(&index);
  if (!result) return false;
  if (index >= dict_.size()) return false;
  *value = dict_[index];
  return true;
}

template<typename T>
inline void DictEncoder<T>::WriteDict(uint8_t* buffer) {
  BOOST_FOREACH(const T& value, dict_) {
    buffer += ParquetPlainEncoder::Encode(buffer, value);
  }
}

template<typename T>
inline int DictEncoder<T>::WriteData(uint8_t* buffer, int buffer_len) {
  // Write bit width in first byte
  *buffer = bit_width();
  ++buffer;
  --buffer_len;

  RleEncoder encoder(buffer, buffer_len, bit_width());
  BOOST_FOREACH(int index, buffered_indices_) {
    bool result = encoder.Put(index);
    if (!result) return -1;
  }
  // TODO(skye): fix overflow logic
  encoder.Flush();
  buffered_indices_.clear();
  return 1 + encoder.len();
}

template<typename T>
inline int DictEncoder<T>::bit_width() {
  if (UNLIKELY(num_entries() == 0)) return 0;
  if (UNLIKELY(num_entries() == 1)) return 1;
  int max_id = num_entries() - 1;
  return BitUtil::Log2(max_id);
}

template<typename T>
inline DictDecoder<T>::DictDecoder(uint8_t* dict_buffer, int dict_len) {
  uint8_t* end = dict_buffer + dict_len;
  while (dict_buffer < end) {
    T value;
    dict_buffer += ParquetPlainEncoder::Decode(dict_buffer, &value);
    dict_.push_back(value);
  }
}

}
#endif
