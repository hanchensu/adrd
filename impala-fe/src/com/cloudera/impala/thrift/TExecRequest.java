/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.cloudera.impala.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TExecRequest implements org.apache.thrift.TBase<TExecRequest, TExecRequest._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TExecRequest");

  private static final org.apache.thrift.protocol.TField STMT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("stmt_type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField QUERY_OPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("query_options", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField QUERY_EXEC_REQUEST_FIELD_DESC = new org.apache.thrift.protocol.TField("query_exec_request", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField DDL_EXEC_REQUEST_FIELD_DESC = new org.apache.thrift.protocol.TField("ddl_exec_request", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField RESULT_SET_METADATA_FIELD_DESC = new org.apache.thrift.protocol.TField("result_set_metadata", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField EXPLAIN_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("explain_result", org.apache.thrift.protocol.TType.STRUCT, (short)6);
  private static final org.apache.thrift.protocol.TField LOAD_DATA_REQUEST_FIELD_DESC = new org.apache.thrift.protocol.TField("load_data_request", org.apache.thrift.protocol.TType.STRUCT, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TExecRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TExecRequestTupleSchemeFactory());
  }

  /**
   * 
   * @see com.cloudera.impala.thrift.TStmtType
   */
  public com.cloudera.impala.thrift.TStmtType stmt_type; // required
  public com.cloudera.impala.thrift.TQueryOptions query_options; // required
  public TQueryExecRequest query_exec_request; // optional
  public TDdlExecRequest ddl_exec_request; // optional
  public TResultSetMetadata result_set_metadata; // optional
  public TExplainResult explain_result; // optional
  public TLoadDataReq load_data_request; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see com.cloudera.impala.thrift.TStmtType
     */
    STMT_TYPE((short)1, "stmt_type"),
    QUERY_OPTIONS((short)2, "query_options"),
    QUERY_EXEC_REQUEST((short)3, "query_exec_request"),
    DDL_EXEC_REQUEST((short)4, "ddl_exec_request"),
    RESULT_SET_METADATA((short)5, "result_set_metadata"),
    EXPLAIN_RESULT((short)6, "explain_result"),
    LOAD_DATA_REQUEST((short)7, "load_data_request");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // STMT_TYPE
          return STMT_TYPE;
        case 2: // QUERY_OPTIONS
          return QUERY_OPTIONS;
        case 3: // QUERY_EXEC_REQUEST
          return QUERY_EXEC_REQUEST;
        case 4: // DDL_EXEC_REQUEST
          return DDL_EXEC_REQUEST;
        case 5: // RESULT_SET_METADATA
          return RESULT_SET_METADATA;
        case 6: // EXPLAIN_RESULT
          return EXPLAIN_RESULT;
        case 7: // LOAD_DATA_REQUEST
          return LOAD_DATA_REQUEST;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private _Fields optionals[] = {_Fields.QUERY_EXEC_REQUEST,_Fields.DDL_EXEC_REQUEST,_Fields.RESULT_SET_METADATA,_Fields.EXPLAIN_RESULT,_Fields.LOAD_DATA_REQUEST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STMT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("stmt_type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.cloudera.impala.thrift.TStmtType.class)));
    tmpMap.put(_Fields.QUERY_OPTIONS, new org.apache.thrift.meta_data.FieldMetaData("query_options", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TQueryOptions.class)));
    tmpMap.put(_Fields.QUERY_EXEC_REQUEST, new org.apache.thrift.meta_data.FieldMetaData("query_exec_request", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TQueryExecRequest.class)));
    tmpMap.put(_Fields.DDL_EXEC_REQUEST, new org.apache.thrift.meta_data.FieldMetaData("ddl_exec_request", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TDdlExecRequest.class)));
    tmpMap.put(_Fields.RESULT_SET_METADATA, new org.apache.thrift.meta_data.FieldMetaData("result_set_metadata", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TResultSetMetadata.class)));
    tmpMap.put(_Fields.EXPLAIN_RESULT, new org.apache.thrift.meta_data.FieldMetaData("explain_result", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TExplainResult.class)));
    tmpMap.put(_Fields.LOAD_DATA_REQUEST, new org.apache.thrift.meta_data.FieldMetaData("load_data_request", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TLoadDataReq.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TExecRequest.class, metaDataMap);
  }

  public TExecRequest() {
  }

  public TExecRequest(
    com.cloudera.impala.thrift.TStmtType stmt_type,
    com.cloudera.impala.thrift.TQueryOptions query_options)
  {
    this();
    this.stmt_type = stmt_type;
    this.query_options = query_options;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TExecRequest(TExecRequest other) {
    if (other.isSetStmt_type()) {
      this.stmt_type = other.stmt_type;
    }
    if (other.isSetQuery_options()) {
      this.query_options = new com.cloudera.impala.thrift.TQueryOptions(other.query_options);
    }
    if (other.isSetQuery_exec_request()) {
      this.query_exec_request = new TQueryExecRequest(other.query_exec_request);
    }
    if (other.isSetDdl_exec_request()) {
      this.ddl_exec_request = new TDdlExecRequest(other.ddl_exec_request);
    }
    if (other.isSetResult_set_metadata()) {
      this.result_set_metadata = new TResultSetMetadata(other.result_set_metadata);
    }
    if (other.isSetExplain_result()) {
      this.explain_result = new TExplainResult(other.explain_result);
    }
    if (other.isSetLoad_data_request()) {
      this.load_data_request = new TLoadDataReq(other.load_data_request);
    }
  }

  public TExecRequest deepCopy() {
    return new TExecRequest(this);
  }

  @Override
  public void clear() {
    this.stmt_type = null;
    this.query_options = null;
    this.query_exec_request = null;
    this.ddl_exec_request = null;
    this.result_set_metadata = null;
    this.explain_result = null;
    this.load_data_request = null;
  }

  /**
   * 
   * @see com.cloudera.impala.thrift.TStmtType
   */
  public com.cloudera.impala.thrift.TStmtType getStmt_type() {
    return this.stmt_type;
  }

  /**
   * 
   * @see com.cloudera.impala.thrift.TStmtType
   */
  public TExecRequest setStmt_type(com.cloudera.impala.thrift.TStmtType stmt_type) {
    this.stmt_type = stmt_type;
    return this;
  }

  public void unsetStmt_type() {
    this.stmt_type = null;
  }

  /** Returns true if field stmt_type is set (has been assigned a value) and false otherwise */
  public boolean isSetStmt_type() {
    return this.stmt_type != null;
  }

  public void setStmt_typeIsSet(boolean value) {
    if (!value) {
      this.stmt_type = null;
    }
  }

  public com.cloudera.impala.thrift.TQueryOptions getQuery_options() {
    return this.query_options;
  }

  public TExecRequest setQuery_options(com.cloudera.impala.thrift.TQueryOptions query_options) {
    this.query_options = query_options;
    return this;
  }

  public void unsetQuery_options() {
    this.query_options = null;
  }

  /** Returns true if field query_options is set (has been assigned a value) and false otherwise */
  public boolean isSetQuery_options() {
    return this.query_options != null;
  }

  public void setQuery_optionsIsSet(boolean value) {
    if (!value) {
      this.query_options = null;
    }
  }

  public TQueryExecRequest getQuery_exec_request() {
    return this.query_exec_request;
  }

  public TExecRequest setQuery_exec_request(TQueryExecRequest query_exec_request) {
    this.query_exec_request = query_exec_request;
    return this;
  }

  public void unsetQuery_exec_request() {
    this.query_exec_request = null;
  }

  /** Returns true if field query_exec_request is set (has been assigned a value) and false otherwise */
  public boolean isSetQuery_exec_request() {
    return this.query_exec_request != null;
  }

  public void setQuery_exec_requestIsSet(boolean value) {
    if (!value) {
      this.query_exec_request = null;
    }
  }

  public TDdlExecRequest getDdl_exec_request() {
    return this.ddl_exec_request;
  }

  public TExecRequest setDdl_exec_request(TDdlExecRequest ddl_exec_request) {
    this.ddl_exec_request = ddl_exec_request;
    return this;
  }

  public void unsetDdl_exec_request() {
    this.ddl_exec_request = null;
  }

  /** Returns true if field ddl_exec_request is set (has been assigned a value) and false otherwise */
  public boolean isSetDdl_exec_request() {
    return this.ddl_exec_request != null;
  }

  public void setDdl_exec_requestIsSet(boolean value) {
    if (!value) {
      this.ddl_exec_request = null;
    }
  }

  public TResultSetMetadata getResult_set_metadata() {
    return this.result_set_metadata;
  }

  public TExecRequest setResult_set_metadata(TResultSetMetadata result_set_metadata) {
    this.result_set_metadata = result_set_metadata;
    return this;
  }

  public void unsetResult_set_metadata() {
    this.result_set_metadata = null;
  }

  /** Returns true if field result_set_metadata is set (has been assigned a value) and false otherwise */
  public boolean isSetResult_set_metadata() {
    return this.result_set_metadata != null;
  }

  public void setResult_set_metadataIsSet(boolean value) {
    if (!value) {
      this.result_set_metadata = null;
    }
  }

  public TExplainResult getExplain_result() {
    return this.explain_result;
  }

  public TExecRequest setExplain_result(TExplainResult explain_result) {
    this.explain_result = explain_result;
    return this;
  }

  public void unsetExplain_result() {
    this.explain_result = null;
  }

  /** Returns true if field explain_result is set (has been assigned a value) and false otherwise */
  public boolean isSetExplain_result() {
    return this.explain_result != null;
  }

  public void setExplain_resultIsSet(boolean value) {
    if (!value) {
      this.explain_result = null;
    }
  }

  public TLoadDataReq getLoad_data_request() {
    return this.load_data_request;
  }

  public TExecRequest setLoad_data_request(TLoadDataReq load_data_request) {
    this.load_data_request = load_data_request;
    return this;
  }

  public void unsetLoad_data_request() {
    this.load_data_request = null;
  }

  /** Returns true if field load_data_request is set (has been assigned a value) and false otherwise */
  public boolean isSetLoad_data_request() {
    return this.load_data_request != null;
  }

  public void setLoad_data_requestIsSet(boolean value) {
    if (!value) {
      this.load_data_request = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STMT_TYPE:
      if (value == null) {
        unsetStmt_type();
      } else {
        setStmt_type((com.cloudera.impala.thrift.TStmtType)value);
      }
      break;

    case QUERY_OPTIONS:
      if (value == null) {
        unsetQuery_options();
      } else {
        setQuery_options((com.cloudera.impala.thrift.TQueryOptions)value);
      }
      break;

    case QUERY_EXEC_REQUEST:
      if (value == null) {
        unsetQuery_exec_request();
      } else {
        setQuery_exec_request((TQueryExecRequest)value);
      }
      break;

    case DDL_EXEC_REQUEST:
      if (value == null) {
        unsetDdl_exec_request();
      } else {
        setDdl_exec_request((TDdlExecRequest)value);
      }
      break;

    case RESULT_SET_METADATA:
      if (value == null) {
        unsetResult_set_metadata();
      } else {
        setResult_set_metadata((TResultSetMetadata)value);
      }
      break;

    case EXPLAIN_RESULT:
      if (value == null) {
        unsetExplain_result();
      } else {
        setExplain_result((TExplainResult)value);
      }
      break;

    case LOAD_DATA_REQUEST:
      if (value == null) {
        unsetLoad_data_request();
      } else {
        setLoad_data_request((TLoadDataReq)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STMT_TYPE:
      return getStmt_type();

    case QUERY_OPTIONS:
      return getQuery_options();

    case QUERY_EXEC_REQUEST:
      return getQuery_exec_request();

    case DDL_EXEC_REQUEST:
      return getDdl_exec_request();

    case RESULT_SET_METADATA:
      return getResult_set_metadata();

    case EXPLAIN_RESULT:
      return getExplain_result();

    case LOAD_DATA_REQUEST:
      return getLoad_data_request();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STMT_TYPE:
      return isSetStmt_type();
    case QUERY_OPTIONS:
      return isSetQuery_options();
    case QUERY_EXEC_REQUEST:
      return isSetQuery_exec_request();
    case DDL_EXEC_REQUEST:
      return isSetDdl_exec_request();
    case RESULT_SET_METADATA:
      return isSetResult_set_metadata();
    case EXPLAIN_RESULT:
      return isSetExplain_result();
    case LOAD_DATA_REQUEST:
      return isSetLoad_data_request();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TExecRequest)
      return this.equals((TExecRequest)that);
    return false;
  }

  public boolean equals(TExecRequest that) {
    if (that == null)
      return false;

    boolean this_present_stmt_type = true && this.isSetStmt_type();
    boolean that_present_stmt_type = true && that.isSetStmt_type();
    if (this_present_stmt_type || that_present_stmt_type) {
      if (!(this_present_stmt_type && that_present_stmt_type))
        return false;
      if (!this.stmt_type.equals(that.stmt_type))
        return false;
    }

    boolean this_present_query_options = true && this.isSetQuery_options();
    boolean that_present_query_options = true && that.isSetQuery_options();
    if (this_present_query_options || that_present_query_options) {
      if (!(this_present_query_options && that_present_query_options))
        return false;
      if (!this.query_options.equals(that.query_options))
        return false;
    }

    boolean this_present_query_exec_request = true && this.isSetQuery_exec_request();
    boolean that_present_query_exec_request = true && that.isSetQuery_exec_request();
    if (this_present_query_exec_request || that_present_query_exec_request) {
      if (!(this_present_query_exec_request && that_present_query_exec_request))
        return false;
      if (!this.query_exec_request.equals(that.query_exec_request))
        return false;
    }

    boolean this_present_ddl_exec_request = true && this.isSetDdl_exec_request();
    boolean that_present_ddl_exec_request = true && that.isSetDdl_exec_request();
    if (this_present_ddl_exec_request || that_present_ddl_exec_request) {
      if (!(this_present_ddl_exec_request && that_present_ddl_exec_request))
        return false;
      if (!this.ddl_exec_request.equals(that.ddl_exec_request))
        return false;
    }

    boolean this_present_result_set_metadata = true && this.isSetResult_set_metadata();
    boolean that_present_result_set_metadata = true && that.isSetResult_set_metadata();
    if (this_present_result_set_metadata || that_present_result_set_metadata) {
      if (!(this_present_result_set_metadata && that_present_result_set_metadata))
        return false;
      if (!this.result_set_metadata.equals(that.result_set_metadata))
        return false;
    }

    boolean this_present_explain_result = true && this.isSetExplain_result();
    boolean that_present_explain_result = true && that.isSetExplain_result();
    if (this_present_explain_result || that_present_explain_result) {
      if (!(this_present_explain_result && that_present_explain_result))
        return false;
      if (!this.explain_result.equals(that.explain_result))
        return false;
    }

    boolean this_present_load_data_request = true && this.isSetLoad_data_request();
    boolean that_present_load_data_request = true && that.isSetLoad_data_request();
    if (this_present_load_data_request || that_present_load_data_request) {
      if (!(this_present_load_data_request && that_present_load_data_request))
        return false;
      if (!this.load_data_request.equals(that.load_data_request))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TExecRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TExecRequest typedOther = (TExecRequest)other;

    lastComparison = Boolean.valueOf(isSetStmt_type()).compareTo(typedOther.isSetStmt_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStmt_type()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stmt_type, typedOther.stmt_type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQuery_options()).compareTo(typedOther.isSetQuery_options());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuery_options()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.query_options, typedOther.query_options);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQuery_exec_request()).compareTo(typedOther.isSetQuery_exec_request());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuery_exec_request()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.query_exec_request, typedOther.query_exec_request);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDdl_exec_request()).compareTo(typedOther.isSetDdl_exec_request());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDdl_exec_request()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ddl_exec_request, typedOther.ddl_exec_request);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResult_set_metadata()).compareTo(typedOther.isSetResult_set_metadata());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult_set_metadata()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result_set_metadata, typedOther.result_set_metadata);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExplain_result()).compareTo(typedOther.isSetExplain_result());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExplain_result()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.explain_result, typedOther.explain_result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLoad_data_request()).compareTo(typedOther.isSetLoad_data_request());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoad_data_request()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.load_data_request, typedOther.load_data_request);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TExecRequest(");
    boolean first = true;

    sb.append("stmt_type:");
    if (this.stmt_type == null) {
      sb.append("null");
    } else {
      sb.append(this.stmt_type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("query_options:");
    if (this.query_options == null) {
      sb.append("null");
    } else {
      sb.append(this.query_options);
    }
    first = false;
    if (isSetQuery_exec_request()) {
      if (!first) sb.append(", ");
      sb.append("query_exec_request:");
      if (this.query_exec_request == null) {
        sb.append("null");
      } else {
        sb.append(this.query_exec_request);
      }
      first = false;
    }
    if (isSetDdl_exec_request()) {
      if (!first) sb.append(", ");
      sb.append("ddl_exec_request:");
      if (this.ddl_exec_request == null) {
        sb.append("null");
      } else {
        sb.append(this.ddl_exec_request);
      }
      first = false;
    }
    if (isSetResult_set_metadata()) {
      if (!first) sb.append(", ");
      sb.append("result_set_metadata:");
      if (this.result_set_metadata == null) {
        sb.append("null");
      } else {
        sb.append(this.result_set_metadata);
      }
      first = false;
    }
    if (isSetExplain_result()) {
      if (!first) sb.append(", ");
      sb.append("explain_result:");
      if (this.explain_result == null) {
        sb.append("null");
      } else {
        sb.append(this.explain_result);
      }
      first = false;
    }
    if (isSetLoad_data_request()) {
      if (!first) sb.append(", ");
      sb.append("load_data_request:");
      if (this.load_data_request == null) {
        sb.append("null");
      } else {
        sb.append(this.load_data_request);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (stmt_type == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'stmt_type' was not present! Struct: " + toString());
    }
    if (query_options == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'query_options' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (query_options != null) {
      query_options.validate();
    }
    if (query_exec_request != null) {
      query_exec_request.validate();
    }
    if (ddl_exec_request != null) {
      ddl_exec_request.validate();
    }
    if (result_set_metadata != null) {
      result_set_metadata.validate();
    }
    if (explain_result != null) {
      explain_result.validate();
    }
    if (load_data_request != null) {
      load_data_request.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TExecRequestStandardSchemeFactory implements SchemeFactory {
    public TExecRequestStandardScheme getScheme() {
      return new TExecRequestStandardScheme();
    }
  }

  private static class TExecRequestStandardScheme extends StandardScheme<TExecRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TExecRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STMT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.stmt_type = com.cloudera.impala.thrift.TStmtType.findByValue(iprot.readI32());
              struct.setStmt_typeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // QUERY_OPTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.query_options = new com.cloudera.impala.thrift.TQueryOptions();
              struct.query_options.read(iprot);
              struct.setQuery_optionsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // QUERY_EXEC_REQUEST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.query_exec_request = new TQueryExecRequest();
              struct.query_exec_request.read(iprot);
              struct.setQuery_exec_requestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DDL_EXEC_REQUEST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.ddl_exec_request = new TDdlExecRequest();
              struct.ddl_exec_request.read(iprot);
              struct.setDdl_exec_requestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RESULT_SET_METADATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.result_set_metadata = new TResultSetMetadata();
              struct.result_set_metadata.read(iprot);
              struct.setResult_set_metadataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // EXPLAIN_RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.explain_result = new TExplainResult();
              struct.explain_result.read(iprot);
              struct.setExplain_resultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // LOAD_DATA_REQUEST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.load_data_request = new TLoadDataReq();
              struct.load_data_request.read(iprot);
              struct.setLoad_data_requestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TExecRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.stmt_type != null) {
        oprot.writeFieldBegin(STMT_TYPE_FIELD_DESC);
        oprot.writeI32(struct.stmt_type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.query_options != null) {
        oprot.writeFieldBegin(QUERY_OPTIONS_FIELD_DESC);
        struct.query_options.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.query_exec_request != null) {
        if (struct.isSetQuery_exec_request()) {
          oprot.writeFieldBegin(QUERY_EXEC_REQUEST_FIELD_DESC);
          struct.query_exec_request.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.ddl_exec_request != null) {
        if (struct.isSetDdl_exec_request()) {
          oprot.writeFieldBegin(DDL_EXEC_REQUEST_FIELD_DESC);
          struct.ddl_exec_request.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.result_set_metadata != null) {
        if (struct.isSetResult_set_metadata()) {
          oprot.writeFieldBegin(RESULT_SET_METADATA_FIELD_DESC);
          struct.result_set_metadata.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.explain_result != null) {
        if (struct.isSetExplain_result()) {
          oprot.writeFieldBegin(EXPLAIN_RESULT_FIELD_DESC);
          struct.explain_result.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.load_data_request != null) {
        if (struct.isSetLoad_data_request()) {
          oprot.writeFieldBegin(LOAD_DATA_REQUEST_FIELD_DESC);
          struct.load_data_request.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TExecRequestTupleSchemeFactory implements SchemeFactory {
    public TExecRequestTupleScheme getScheme() {
      return new TExecRequestTupleScheme();
    }
  }

  private static class TExecRequestTupleScheme extends TupleScheme<TExecRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TExecRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.stmt_type.getValue());
      struct.query_options.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetQuery_exec_request()) {
        optionals.set(0);
      }
      if (struct.isSetDdl_exec_request()) {
        optionals.set(1);
      }
      if (struct.isSetResult_set_metadata()) {
        optionals.set(2);
      }
      if (struct.isSetExplain_result()) {
        optionals.set(3);
      }
      if (struct.isSetLoad_data_request()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetQuery_exec_request()) {
        struct.query_exec_request.write(oprot);
      }
      if (struct.isSetDdl_exec_request()) {
        struct.ddl_exec_request.write(oprot);
      }
      if (struct.isSetResult_set_metadata()) {
        struct.result_set_metadata.write(oprot);
      }
      if (struct.isSetExplain_result()) {
        struct.explain_result.write(oprot);
      }
      if (struct.isSetLoad_data_request()) {
        struct.load_data_request.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TExecRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.stmt_type = com.cloudera.impala.thrift.TStmtType.findByValue(iprot.readI32());
      struct.setStmt_typeIsSet(true);
      struct.query_options = new com.cloudera.impala.thrift.TQueryOptions();
      struct.query_options.read(iprot);
      struct.setQuery_optionsIsSet(true);
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.query_exec_request = new TQueryExecRequest();
        struct.query_exec_request.read(iprot);
        struct.setQuery_exec_requestIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ddl_exec_request = new TDdlExecRequest();
        struct.ddl_exec_request.read(iprot);
        struct.setDdl_exec_requestIsSet(true);
      }
      if (incoming.get(2)) {
        struct.result_set_metadata = new TResultSetMetadata();
        struct.result_set_metadata.read(iprot);
        struct.setResult_set_metadataIsSet(true);
      }
      if (incoming.get(3)) {
        struct.explain_result = new TExplainResult();
        struct.explain_result.read(iprot);
        struct.setExplain_resultIsSet(true);
      }
      if (incoming.get(4)) {
        struct.load_data_request = new TLoadDataReq();
        struct.load_data_request.read(iprot);
        struct.setLoad_data_requestIsSet(true);
      }
    }
  }

}
