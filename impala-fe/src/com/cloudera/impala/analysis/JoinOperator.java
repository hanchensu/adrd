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

package com.cloudera.impala.analysis;

import com.cloudera.impala.thrift.TJoinOp;

public enum JoinOperator {
  INNER_JOIN("INNER JOIN", TJoinOp.INNER_JOIN),
  LEFT_OUTER_JOIN("LEFT OUTER JOIN", TJoinOp.LEFT_OUTER_JOIN),
  LEFT_SEMI_JOIN("LEFT SEMI JOIN", TJoinOp.LEFT_SEMI_JOIN),
  RIGHT_OUTER_JOIN("RIGHT OUTER JOIN", TJoinOp.RIGHT_OUTER_JOIN),
  FULL_OUTER_JOIN("FULL OUTER JOIN", TJoinOp.FULL_OUTER_JOIN);

  private final String description;
  private final TJoinOp thriftJoinOp;

  private JoinOperator(String description, TJoinOp thriftJoinOp) {
    this.description = description;
    this.thriftJoinOp = thriftJoinOp;
  }

  @Override
  public String toString() {
    return description;
  }

  public TJoinOp toThrift() {
    return thriftJoinOp;
  }

  public boolean isOuterJoin() {
    return this == LEFT_OUTER_JOIN
        || this == RIGHT_OUTER_JOIN
        || this == FULL_OUTER_JOIN;
  }
}


