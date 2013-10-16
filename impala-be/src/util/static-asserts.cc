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

#include <boost/static_assert.hpp>

#include "common/hdfs.h"
#include "runtime/string-value.h"
#include "runtime/timestamp-value.h"

namespace impala {
// This class is unused.  It contains static (compile time) asserts.
// This is useful to validate struct sizes and other similar things
// at compile time.  If these asserts fail, the compile will fail.
class UnusedClass {
 private:
  BOOST_STATIC_ASSERT(sizeof(StringValue) == 16);
  BOOST_STATIC_ASSERT(offsetof(StringValue, len) == 8);
  BOOST_STATIC_ASSERT(sizeof(TimestampValue) == 16);
  BOOST_STATIC_ASSERT(offsetof(TimestampValue, date_) == 8);
  BOOST_STATIC_ASSERT(sizeof(hdfsFS) == sizeof(void*));
  BOOST_STATIC_ASSERT(sizeof(hdfsFile) == sizeof(void*));
};

}
