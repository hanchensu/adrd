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

import java.util.ArrayList;

import com.cloudera.impala.authorization.Privilege;
import com.cloudera.impala.catalog.AuthorizationException;
import com.cloudera.impala.common.AnalysisException;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * Represents a CREATE VIEW statement.
 */
public class CreateViewStmt extends CreateOrAlterViewStmtBase {

  public CreateViewStmt(boolean ifNotExists, TableName tableName,
      ArrayList<ColumnDef> columnDefs, String comment, QueryStmt viewDefStmt) {
    super(ifNotExists, tableName, columnDefs, comment, viewDefStmt);
  }

  @Override
  public void analyze(Analyzer analyzer) throws AnalysisException,
      AuthorizationException {
    tableName.analyze();
    viewDefStmt.analyze(analyzer);

    Preconditions.checkState(tableName != null && !tableName.isEmpty());
    dbName = analyzer.getTargetDbName(tableName);
    owner = analyzer.getUser().getName();
    if (analyzer.dbContainsTable(dbName, tableName.getTbl(), Privilege.CREATE) &&
        !ifNotExists) {
      throw new AnalysisException(Analyzer.TBL_ALREADY_EXISTS_ERROR_MSG +
          String.format("%s.%s", dbName, tableName.getTbl()));
    }
    createColumnAndViewDefs(analyzer);
  }

  @Override
  public String toSql() {
    StringBuilder sb = new StringBuilder();
    sb.append("CREATE VIEW ");
    if (ifNotExists) sb.append("IF NOT EXISTS ");
    if (tableName.getDb() != null) sb.append(tableName.getDb() + ".");
    sb.append(tableName.getTbl() + " (");
    sb.append(Joiner.on(", ").join(columnDefs));
    sb.append(") AS ");
    sb.append(viewDefStmt.toSql());
    return sb.toString();
  }
}
