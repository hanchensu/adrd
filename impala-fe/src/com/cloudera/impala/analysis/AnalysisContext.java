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

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java_cup.internal_error;

import org.apache.hadoop.hive.ql.parse.HiveParser.ifExists_return;

import com.cloudera.impala.authorization.User;
import com.cloudera.impala.catalog.AuthorizationException;
import com.cloudera.impala.catalog.Catalog;
import com.cloudera.impala.common.AnalysisException;
import com.google.common.base.Preconditions;

/**
 * Wrapper class for parser and analyzer.
 *
 */
public class AnalysisContext {
  private final Catalog catalog;

  // The name of the database to use if one is not explicitly specified by a query.
  private final String defaultDatabase;

  // The user who initiated the request.
  private final User user;

  public AnalysisContext(Catalog catalog, String defaultDb, User user) {
    this.catalog = catalog;
    this.defaultDatabase = defaultDb;
    this.user = user;
  }

  static public class AnalysisResult {
    public StatementBase stmt;
    public Analyzer analyzer;

    public boolean isAlterTableStmt() {
      return stmt instanceof AlterTableStmt;
    }

    public boolean isAlterViewStmt() {
      return stmt instanceof AlterViewStmt;
    }

    public boolean isQueryStmt() {
      return stmt instanceof QueryStmt;
    }

    public boolean isInsertStmt() {
      return stmt instanceof InsertStmt;
    }

    public boolean isDropDbStmt() {
      return stmt instanceof DropDbStmt;
    }

    public boolean isDropTableOrViewStmt() {
      return stmt instanceof DropTableOrViewStmt;
    }

    public boolean isCreateTableLikeStmt() {
      return stmt instanceof CreateTableLikeStmt;
    }

    public boolean isCreateViewStmt() {
      return stmt instanceof CreateViewStmt;
    }

    public boolean isCreateTableStmt() {
      return stmt instanceof CreateTableStmt;
    }

    public boolean isCreateDbStmt() {
      return stmt instanceof CreateDbStmt;
    }

    public boolean isLoadDataStmt() {
      return stmt instanceof LoadDataStmt;
    }

    public boolean isUseStmt() {
      return stmt instanceof UseStmt;
    }

    public boolean isShowTablesStmt() {
      return stmt instanceof ShowTablesStmt;
    }

    public boolean isShowDbsStmt() {
      return stmt instanceof ShowDbsStmt;
    }

    public boolean isDescribeStmt() {
      return stmt instanceof DescribeStmt;
    }

    public boolean isResetMetadataStmt() {
      return stmt instanceof ResetMetadataStmt;
    }

    public boolean isExplainStmt() {
      if (isQueryStmt()) return ((QueryStmt)stmt).isExplain();
      if (isInsertStmt()) return ((InsertStmt)stmt).isExplain();
      return false;
    }

    public boolean isDdlStmt() {
      return isUseStmt() || isShowTablesStmt() || isShowDbsStmt() || isDescribeStmt() ||
          isCreateTableLikeStmt() || isCreateTableStmt() || isCreateViewStmt() ||
          isCreateDbStmt() || isDropDbStmt() || isDropTableOrViewStmt() ||
          isResetMetadataStmt() || isAlterTableStmt() || isAlterViewStmt();
    }

    public boolean isDmlStmt() {
      return isInsertStmt();
    }

    public AlterTableStmt getAlterTableStmt() {
      Preconditions.checkState(isAlterTableStmt());
      return (AlterTableStmt) stmt;
    }

    public AlterViewStmt getAlterViewStmt() {
      Preconditions.checkState(isAlterViewStmt());
      return (AlterViewStmt) stmt;
    }

    public CreateTableLikeStmt getCreateTableLikeStmt() {
      Preconditions.checkState(isCreateTableLikeStmt());
      return (CreateTableLikeStmt) stmt;
    }

    public CreateViewStmt getCreateViewStmt() {
      Preconditions.checkState(isCreateViewStmt());
      return (CreateViewStmt) stmt;
    }

    public CreateTableStmt getCreateTableStmt() {
      Preconditions.checkState(isCreateTableStmt());
      return (CreateTableStmt) stmt;
    }

    public CreateDbStmt getCreateDbStmt() {
      Preconditions.checkState(isCreateDbStmt());
      return (CreateDbStmt) stmt;
    }

    public DropDbStmt getDropDbStmt() {
      Preconditions.checkState(isDropDbStmt());
      return (DropDbStmt) stmt;
    }

    public DropTableOrViewStmt getDropTableOrViewStmt() {
      Preconditions.checkState(isDropTableOrViewStmt());
      return (DropTableOrViewStmt) stmt;
    }

    public LoadDataStmt getLoadDataStmt() {
      Preconditions.checkState(isLoadDataStmt());
      return (LoadDataStmt) stmt;
    }

    public QueryStmt getQueryStmt() {
      Preconditions.checkState(isQueryStmt());
      return (QueryStmt) stmt;
    }

    public InsertStmt getInsertStmt() {
      Preconditions.checkState(isInsertStmt());
      return (InsertStmt) stmt;
    }

    public UseStmt getUseStmt() {
      Preconditions.checkState(isUseStmt());
      return (UseStmt) stmt;
    }

    public ShowTablesStmt getShowTablesStmt() {
      Preconditions.checkState(isShowTablesStmt());
      return (ShowTablesStmt) stmt;
    }

    public ShowDbsStmt getShowDbsStmt() {
      Preconditions.checkState(isShowDbsStmt());
      return (ShowDbsStmt) stmt;
    }

    public DescribeStmt getDescribeStmt() {
      Preconditions.checkState(isDescribeStmt());
      return (DescribeStmt) stmt;
    }

    public StatementBase getStmt() {
      return stmt;
    }

    public Analyzer getAnalyzer() {
      return analyzer;
    }
  }

  /**
   * Parse and analyze 'stmt'.
   *
   * @param stmt
   * @return AnalysisResult
   *         containing the analyzer and the analyzed insert or select statement.
   * @throws AnalysisException
   *           on any kind of error, including parsing error.
   */
  public AnalysisResult analyze(String stmt) throws AnalysisException,
      AuthorizationException {
    SqlScanner input = new SqlScanner(new StringReader(stmt));
    SqlParser parser = new SqlParser(input);
    try {
      AnalysisResult result = new AnalysisResult();
      result.stmt = (StatementBase) parser.parse().value;
      if (result.stmt == null) {
        return null;
      }
      result.analyzer = new Analyzer(catalog, defaultDatabase, user);
      result.stmt.analyze(result.analyzer);
      return result;
    } catch (AnalysisException e) {
      throw e;
    } catch (AuthorizationException e) {
      throw e;
    } catch (Exception e) {
      throw new AnalysisException(parser.getErrorMsg(stmt), e);
    }
  }
  
  public static void main(String[] args) throws Exception {
//		String stmt = "select 5*(c + d) as f,max(b) from shc a where a > 10 AND b < 10 group by a";
//	  String stmt = "SELECT a, AVG(Distinct b), MAX(d),COUNT(f) FROM shctest WHERE a=5 AND (b<10 OR c>3) AND c>4 GROUP BY a HAVING MAX(e) < 10";
	  String stmt = "SELECT g,h FROM shctest FULL OUTER JOIN shctest2 ON shctest.a = shctest2.a WHERE shctest.b = 4 OR shctest.c = 3";
	  SqlScanner input = new SqlScanner(new StringReader(stmt));
	  SqlParser parser = new SqlParser(input);
	   
      AnalysisResult result = new AnalysisResult();
      result.stmt = (StatementBase) parser.parse().value;
      
	  result.analyzer = new Analyzer(new Catalog(), "default", new User("impala"));
	  result.stmt.analyze(result.analyzer);
	  
//	  System.out.println("main: "+ ((SelectStmt)result.stmt).getAggInfo().getAggregateExprs().get(0).toSql());
//	 aggInfoDebugString(((SelectStmt)result.stmt).getAggInfo(), "(main) agginfo");
//	 aggInfoDebugString(((SelectStmt)result.stmt).getAggInfo().getMergeAggInfo(), "(main) mergeinfo");
//	 aggInfoDebugString(((SelectStmt)result.stmt).getAggInfo().getSecondPhaseDistinctAggInfo(), "(main) 2nd agginfo");
	 for(SelectListItem seleExpr : ((SelectStmt)result.stmt).getSelectList().getItems()) {
		 System.out.println("(main) selectItem: "+seleExpr.toSql());
	 }
	 
	 for(Expr seleExpr : ((SelectStmt)result.stmt).getResultExprs()) {
		 System.out.println("(main) resultExpr: "+seleExpr.getClass());
	 }
	 
//	 System.out.println(((SelectStmt)result.stmt).getWhereClause().toSql());
	 
	 for(TupleDescriptor td : result.analyzer.getDescTbl().getTupleDescs()) {
//		 tdDebugString(td);
		 System.out.println("(main) "+td.debugString());
	 }
	
	 System.out.println(result.analyzer.getDescTbl().getTupleDescs().size());
	 
	 
	
	 
	 Iterator<Map.Entry<SlotId, List<ExprId>>> iterator = result.analyzer.slotPredicates.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.slotPredicates: ---");
	 while(iterator.hasNext()) {
		 Map.Entry<SlotId, List<ExprId>> entry = iterator.next();
		 System.out.println("(main) Slot"+entry.getKey().asInt()+": ");
		 for(ExprId id : entry.getValue()) {
			 System.out.println("	"+result.analyzer.conjuncts.get(id).toSql());
		 }
	 }
	 
	 
	 Iterator<Map.Entry<TupleId, List<ExprId>>> iterator2 = result.analyzer.tuplePredicates.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.tuplePredicates: ---");
	 while(iterator2.hasNext()) {
		 Map.Entry<TupleId, List<ExprId>> entry = iterator2.next();
		 System.out.println("(main) Tuple"+entry.getKey().asInt()+": ");
		 for(ExprId id : entry.getValue()) {
			 System.out.println("	"+result.analyzer.conjuncts.get(id).toSql());
		 }
	 }
	 
	 
	 Iterator<Map.Entry<TupleId, List<ExprId>>> iterator3 = result.analyzer.eqJoinConjuncts.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.eqJoinConjuncts: ---");
	 while(iterator3.hasNext()) {
		 Map.Entry<TupleId, List<ExprId>> entry = iterator3.next();
		 System.out.println("(main) Tuple"+entry.getKey().asInt()+": ");
		 for(ExprId id : entry.getValue()) {
			 System.out.println("	"+result.analyzer.conjuncts.get(id).toSql());
		 }
	 }
	 
	 
	 Iterator<Map.Entry<TupleId, TableRef>> iterator4 = result.analyzer.outerJoinedTupleIds.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.outerJoinTupleIds: ---");
	 while(iterator4.hasNext()) {
		 Map.Entry<TupleId, TableRef> entry = iterator4.next();
		 System.out.println("(main) Tuple"+entry.getKey().asInt()+": "+entry.getValue().toSql());
		 
	 }
	 
	 
	 
	 Iterator<Entry<TableRef, List<ExprId>>> iterator5 = result.analyzer.conjunctsByOjClause.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.conjunctsByOjClause: ---");
	 while(iterator5.hasNext()) {
		 Map.Entry<TableRef, List<ExprId>> entry = iterator5.next();
		 System.out.println("(main) TableRef: "+entry.getKey().toSql());
		 for(ExprId id : entry.getValue()) {
			 System.out.println("	"+result.analyzer.conjuncts.get(id).toSql());
		 }
	 }
	 
	 
	 Iterator<Entry<ExprId,TableRef>> iterator8 = result.analyzer.ojClauseByConjunct.entrySet().iterator();
	 System.out.println("\n---(main) result.analyzer.ojClauseByConjunct: ---");
	 while(iterator8.hasNext()) {
		 Map.Entry<ExprId,TableRef> entry = iterator8.next();
		 System.out.println("(main) ojClauseByConjunct: "+result.analyzer.conjuncts.get(entry.getKey()).toSql()+"#Tuple"+entry.getValue().getId()+":"+entry.getValue().toSql());
		 
	 }
	
	System.out.println("\n---(main) getWhereClause().getConjuncts(): ---");
	for(Expr expr: ((SelectStmt)result.stmt).getWhereClause().getConjuncts()) {
		System.out.println("(main) Conjuncts: "+ expr.toSql());
	}
	
	System.out.println("\n---(main) result.stmt.getTableRefs(): ---");
	for(TableRef expr: ((SelectStmt)result.stmt).getTableRefs()) {
		System.out.println("(main) tableRef: "+ expr.toSql());
		System.out.println("	JoinOp: "+expr.getJoinOp());
		if(expr.getJoinHints()!=null) {
			for(String hint:expr.getJoinHints()) {
				System.out.println(" join hint:"+hint);
			}
		}
		if(expr.getLeftTblRef() != null) {
			
			System.out.println("	leftRef: "+expr.getLeftTblRef().toSql());
		}
	}
	
	
	Iterator<Map.Entry<TupleId, TupleDescriptor>> iterator6 = result.analyzer.getDescTbl().tupleDescs.entrySet().iterator();
	System.out.println("\n---(main) result.analyzer.DescriptorTable.tupleDescs: ---");
	 while(iterator6.hasNext()) {
		Map.Entry<TupleId,TupleDescriptor> entry = iterator6.next();
		 System.out.println("(main) tupleDescs: "+entry.getKey().asInt()+"#"+entry.getValue().debugString());
	}
	
	 
	Iterator<Map.Entry<SlotId, SlotDescriptor>> iterator7 = result.analyzer.getDescTbl().slotDescs.entrySet().iterator();
		System.out.println("\n---(main) result.analyzer.DescriptorTable.slotDescs: ---");
		 while(iterator7.hasNext()) {
			Map.Entry<SlotId, SlotDescriptor> entry = iterator7.next();
			 System.out.println("(main) slotDescs: "+entry.getKey().asInt()+"#"+entry.getValue().debugString());
	}
	
		 
		 
		 result.analyzer.computeEquivClasses();
		 for(int i = 0; i < result.analyzer.valueTransfer.length; i++) {
			 for(int j = 0; j < result.analyzer.valueTransfer[i].length; j++) {
				 System.out.print(result.analyzer.valueTransfer[i][j]+" ");
			 }
			 System.out.println("\n");
		 }
		 
		 Iterator<Map.Entry<TupleId, TableRef>> iterator9 = result.analyzer.outerJoinedTupleIds.entrySet().iterator();
			System.out.println("\n---(main) result.analyzer.outerJoinedTupleIds: ---");
			 while(iterator9.hasNext()) {
				Map.Entry<TupleId, TableRef> entry = iterator9.next();
				 System.out.println("(main) outerJoinedTupleIds: "+entry.getKey().asInt()+"#"+entry.getValue().getTable().getName()+"_"+entry.getValue().getLeftTblRef().getTable().getName());
		}
	
	}
  
  
 
  public static void tdDebugString(TupleDescriptor tupleDescriptor) {
	  for(SlotDescriptor sr: tupleDescriptor.getSlots()) {
		  System.out.print(sr.debugString()+"__");
	  }
	  System.out.println("end");
  }
  
  public static void aggInfoDebugString(AggregateInfo agginfo, String name) {
	  for(Expr expr: agginfo.getAggregateExprs()){
		  System.out.println(name+" aggexpr: "+ expr.toSql());
	  }
	  
	  for(Expr expr: agginfo.getGroupingExprs()){
		  System.out.println(name+" groupexpr: "+ expr.toSql());
	  }
	  
	  for(Expr expr: agginfo.getSMap().lhs) {
		  System.out.println(name+" SMap lhs: "+ expr.toSql());
	  }
	  
	  for(Expr expr: agginfo.getSMap().rhs) {
		  System.out.println(name+" SMap rhs: "+ expr.toSql());
	  }
  }
  
}
