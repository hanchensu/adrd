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

#ifndef IMPALA_SERVICE_FRONTEND_H
#define IMPALA_SERVICE_FRONTEND_H

#include <jni.h>

#include "gen-cpp/ImpalaService.h"
#include "gen-cpp/ImpalaHiveServer2Service.h"
#include "gen-cpp/ImpalaInternalService.h"
#include "gen-cpp/Frontend_types.h"
#include "common/status.h"

namespace impala {

// The Frontend is a proxy for the Java-side JniFrontend class. The interface is a set of
// wrapper methods for methods called over JNI.
// TODO: Consider changing all methods to accept and return only Thrift structures so that
// all go through exactly the same calling code.
class Frontend {
 public:
  // Does all the work of initialising the JNI method stubs. If any method can't be found,
  // or if there is any further exception, the constructor will terminate the process.
  Frontend();

  // Make any changes required to the metastore as a result of an INSERT query, e.g. newly
  // created partitions.
  Status UpdateMetastore(const TCatalogUpdate& catalog_update);

  // Call FE to get explain plan
  Status GetExplainPlan(const TClientRequest& query_request, std::string* explain_string);

  // Call FE to get TClientRequestResult.
  Status GetExecRequest(const TClientRequest& request, TExecRequest* result);

  // Returns all matching table names, per Hive's "SHOW TABLES <pattern>". Each
  // table name returned is unqualified.
  // If pattern is NULL, match all tables otherwise match only those tables that
  // match the pattern string. Patterns are "p1|p2|p3" where | denotes choice,
  // and each pN may contain wildcards denoted by '*' which match all strings.
  // The TSessionState parameter is used to filter results of metadata operations when
  // authorization is enabled. If this is a user initiated request, it should
  // be set to the user's current session. If this is an Impala internal request,
  // the session should be set to NULL which will skip privilege checks returning all
  // results.
  Status GetTableNames(const std::string& db, const std::string* pattern,
      const TSessionState* session, TGetTablesResult* table_names);

  // Return all databases matching the optional argument 'pattern'.
  // If pattern is NULL, match all databases otherwise match only those databases that
  // match the pattern string. Patterns are "p1|p2|p3" where | denotes choice,
  // and each pN may contain wildcards denoted by '*' which match all strings.
  // The TSessionState parameter is used to filter results of metadata operations when
  // authorization is enabled. If this is a user initiated request, it should
  // be set to the user's current session. If this is an Impala internal request,
  // the session should be set to NULL which will skip privilege checks returning all
  // results.
  Status GetDbNames(const std::string* pattern, const TSessionState* session,
      TGetDbsResult* table_names);

  // Returns (in the output parameter) the result of a DESCRIBE table command. This
  // command retrieves table metadata, such as the column definitions. The metadata
  // that is returned is controlled by setting the 'output_style' field. If this
  // field is set to MINIMAL, only the column definitions are returned. If set to
  // FORMATTED, extended metadata is returned (in addition to the column defs).
  // This includes info about the table properties, SerDe properties, StorageDescriptor
  // properties, and more.
  Status DescribeTable(const TDescribeTableParams& params,
      TDescribeTableResult* response);

  // Modifies an existing table's metastore metadata. The specific type of operation is
  // defined by the TAlterTableType field in TAlterTableParams. Some supported operations
  // include renaming tables, adding/dropping columns/partitions from tables, and changing
  // a table's file format. Returns OK if the operation was successfull, otherwise a
  // Status object with information on the error will be returned.
  Status AlterTable(const TAlterTableParams& alter_table_params);

  // Modifies an existing view's definition in the metastore metadata.
  // Returns a non-ok status if the view doesn't exist or if the existing metastore
  // entry refers to a table and not a view.
  Status AlterView(const TCreateOrAlterViewParams& alter_view_params);

  // Creates a new database in the metastore with the specified name. Returns OK if the
  // database was successfully created, otherwise CANCELLED is returned with details on
  // the specific error. Common errors include creating a database that already exists
  // and metastore connectivity problems.
  Status CreateDatabase(const TCreateDbParams& create_db_params);

  // Creates a new table in the metastore with the specified name. Returns OK if the
  // table was successfully created, otherwise CANCELLED is returned. Common errors
  // include creating a table that already exists, creating a table in a database that
  // does not exist, and metastore connectivity problems.
  Status CreateTable(const TCreateTableParams& create_table_params);

  // Creates a new table in the metastore that is a based on the table definition of a
  // given source table. This is a metadata only operation - no data is copied.
  Status CreateTableLike(const TCreateTableLikeParams& create_table_like_params);

  // Creates a new view in the metastore with the specified name. Returns OK if the
  // view was created, otherwise a Status detailing the error. Common errors
  // include creating a view whose name already exists (as another table or view),
  // creating a view in a database that does not exist,
  // and metastore connectivity problems.
  Status CreateView(const TCreateOrAlterViewParams& create_view_params);

  // Drops the specified database from the metastore. Returns OK if the database
  // drop was successful, otherwise CANCELLED is returned.
  Status DropDatabase(const TDropDbParams& drop_db_params);

  // Drops the specified table/view from the metastore. Returns OK if the
  // table/view drop was successful, otherwise a Status with an error message.
  Status DropTableOrView(const TDropTableOrViewParams& drop_table_or_view_params);

  // Reset the metadata
  Status ResetMetadata(const TResetMetadataParams& reset_metadata_params);

  // Validate Hadoop config; requires FE
  Status ValidateSettings();

  // Calls FE to execute HiveServer2 metadata operation.
  Status ExecHiveServer2MetadataOp(const TMetadataOpRequest& request,
                                   TMetadataOpResponse* result);

  // Writes a table of all Hadoop configurations, either in text or as HTML per the
  // as_text parameter, to the output stringstream.
  Status RenderHadoopConfigs(bool as_text, std::stringstream* output);

  // Loads a single file or set of files into a table or partition. Saves the RPC
  // response in the TLoadDataResp output parameter. Returns OK if the operation
  // completed successfully.
  Status LoadData(const TLoadDataReq& load_data_request, TLoadDataResp* response);
 private:
  // Descriptor of Java Frontend class itself, used to create a new instance.
  jclass fe_class_;

  jobject fe_;  // instance of com.cloudera.impala.service.JniFrontend
  jmethodID create_exec_request_id_;  // JniFrontend.createExecRequest()
  jmethodID get_explain_plan_id_;  // JniFrontend.getExplainPlan()
  jmethodID get_hadoop_config_id_;  // JniFrontend.getHadoopConfig()
  jmethodID check_config_id_; // JniFrontend.checkConfiguration()
  jmethodID update_metastore_id_; // JniFrontend.updateMetastore()
  jmethodID get_table_names_id_; // JniFrontend.getTableNames
  jmethodID describe_table_id_; // JniFrontend.describeTable
  jmethodID get_db_names_id_; // JniFrontend.getDbNames
  jmethodID exec_hs2_metadata_op_id_; // JniFrontend.execHiveServer2MetadataOp
  jmethodID alter_table_id_; // JniFrontend.alterTable
  jmethodID alter_view_id_; // JniFrontend.alterView
  jmethodID create_database_id_; // JniFrontend.createDatabase
  jmethodID create_table_id_; // JniFrontend.createTable
  jmethodID create_table_like_id_; // JniFrontend.createTableLike
  jmethodID create_view_id_; // JniFrontend.createView
  jmethodID drop_database_id_; // JniFrontend.dropDatabase
  jmethodID drop_table_or_view_id_; // JniFrontend.dropTableOrView
  jmethodID reset_metadata_id_; // JniFrontend.resetMetadata
  jmethodID load_table_data_id_; // JniFrontend.loadTableData
  jmethodID fe_ctor_;

  struct FrontendMethodDescriptor;

  // Utility method to load a method whose signature is in the supplied descriptor; if
  // successful descriptor->method_id is set to a JNI method handle.
  void LoadJniFrontendMethod(JNIEnv* jni_env, FrontendMethodDescriptor* descriptor);

  // Utility methods to avoid repeating lots of the JNI call boilerplate.
  template <typename T>
  Status CallJniMethodWithThriftArgs(const jmethodID& method, const T& arg);
  template <typename T, typename R>
  Status CallJniMethodWithThriftArgs(
      const jmethodID& method, const T& arg, R* response);
  template <typename T>
  Status CallJniMethodWithThriftArgs(
      const jmethodID& method, const T& arg, std::string* response);
};

}

#endif
