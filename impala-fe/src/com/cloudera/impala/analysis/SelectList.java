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

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Select list items plus distinct clause.
 *
 */
class SelectList {
  private final ArrayList<SelectListItem> items = Lists.newArrayList();
  private boolean isDistinct;

  public SelectList() {
    this.isDistinct = false;
  }

  public SelectList(List<SelectListItem> items) {
    isDistinct = false;
    this.items.addAll(items);
  }

  public ArrayList<SelectListItem> getItems() {
    return items;
  }

  public boolean isDistinct() {
    return isDistinct;
  }

  public void setIsDistinct(boolean value) {
    isDistinct = value;
  }

  @Override
  public SelectList clone() {
    SelectList clone = new SelectList();
    for (SelectListItem item: items) {
      clone.items.add(item.clone());
    }
    clone.setIsDistinct(isDistinct);
    return clone;
  }
}
