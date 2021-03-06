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

public class TLoadDataResp implements org.apache.thrift.TBase<TLoadDataResp, TLoadDataResp._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TLoadDataResp");

  private static final org.apache.thrift.protocol.TField LOAD_SUMMARY_FIELD_DESC = new org.apache.thrift.protocol.TField("load_summary", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TLoadDataRespStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TLoadDataRespTupleSchemeFactory());
  }

  public com.cloudera.impala.thrift.TResultRow load_summary; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOAD_SUMMARY((short)1, "load_summary");

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
        case 1: // LOAD_SUMMARY
          return LOAD_SUMMARY;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOAD_SUMMARY, new org.apache.thrift.meta_data.FieldMetaData("load_summary", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TResultRow.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TLoadDataResp.class, metaDataMap);
  }

  public TLoadDataResp() {
  }

  public TLoadDataResp(
    com.cloudera.impala.thrift.TResultRow load_summary)
  {
    this();
    this.load_summary = load_summary;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TLoadDataResp(TLoadDataResp other) {
    if (other.isSetLoad_summary()) {
      this.load_summary = new com.cloudera.impala.thrift.TResultRow(other.load_summary);
    }
  }

  public TLoadDataResp deepCopy() {
    return new TLoadDataResp(this);
  }

  @Override
  public void clear() {
    this.load_summary = null;
  }

  public com.cloudera.impala.thrift.TResultRow getLoad_summary() {
    return this.load_summary;
  }

  public TLoadDataResp setLoad_summary(com.cloudera.impala.thrift.TResultRow load_summary) {
    this.load_summary = load_summary;
    return this;
  }

  public void unsetLoad_summary() {
    this.load_summary = null;
  }

  /** Returns true if field load_summary is set (has been assigned a value) and false otherwise */
  public boolean isSetLoad_summary() {
    return this.load_summary != null;
  }

  public void setLoad_summaryIsSet(boolean value) {
    if (!value) {
      this.load_summary = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LOAD_SUMMARY:
      if (value == null) {
        unsetLoad_summary();
      } else {
        setLoad_summary((com.cloudera.impala.thrift.TResultRow)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LOAD_SUMMARY:
      return getLoad_summary();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LOAD_SUMMARY:
      return isSetLoad_summary();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TLoadDataResp)
      return this.equals((TLoadDataResp)that);
    return false;
  }

  public boolean equals(TLoadDataResp that) {
    if (that == null)
      return false;

    boolean this_present_load_summary = true && this.isSetLoad_summary();
    boolean that_present_load_summary = true && that.isSetLoad_summary();
    if (this_present_load_summary || that_present_load_summary) {
      if (!(this_present_load_summary && that_present_load_summary))
        return false;
      if (!this.load_summary.equals(that.load_summary))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TLoadDataResp other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TLoadDataResp typedOther = (TLoadDataResp)other;

    lastComparison = Boolean.valueOf(isSetLoad_summary()).compareTo(typedOther.isSetLoad_summary());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoad_summary()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.load_summary, typedOther.load_summary);
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
    StringBuilder sb = new StringBuilder("TLoadDataResp(");
    boolean first = true;

    sb.append("load_summary:");
    if (this.load_summary == null) {
      sb.append("null");
    } else {
      sb.append(this.load_summary);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (load_summary == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'load_summary' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (load_summary != null) {
      load_summary.validate();
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

  private static class TLoadDataRespStandardSchemeFactory implements SchemeFactory {
    public TLoadDataRespStandardScheme getScheme() {
      return new TLoadDataRespStandardScheme();
    }
  }

  private static class TLoadDataRespStandardScheme extends StandardScheme<TLoadDataResp> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TLoadDataResp struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOAD_SUMMARY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.load_summary = new com.cloudera.impala.thrift.TResultRow();
              struct.load_summary.read(iprot);
              struct.setLoad_summaryIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TLoadDataResp struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.load_summary != null) {
        oprot.writeFieldBegin(LOAD_SUMMARY_FIELD_DESC);
        struct.load_summary.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TLoadDataRespTupleSchemeFactory implements SchemeFactory {
    public TLoadDataRespTupleScheme getScheme() {
      return new TLoadDataRespTupleScheme();
    }
  }

  private static class TLoadDataRespTupleScheme extends TupleScheme<TLoadDataResp> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TLoadDataResp struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.load_summary.write(oprot);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TLoadDataResp struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.load_summary = new com.cloudera.impala.thrift.TResultRow();
      struct.load_summary.read(iprot);
      struct.setLoad_summaryIsSet(true);
    }
  }

}

