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


#ifndef IMPALA_UTIL_UID_UTIL_H
#define IMPALA_UTIL_UID_UTIL_H

#include "gen-cpp/Types_types.h"  // for TUniqueId

namespace impala {
  // This function must be called 'hash_value' to be picked up by boost.
  inline std::size_t hash_value(const impala::TUniqueId& id) {
    std::size_t seed = 0;
    boost::hash_combine(seed, id.lo);
    boost::hash_combine(seed, id.hi);
    return seed;
  }
}

#endif
