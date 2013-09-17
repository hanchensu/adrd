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

public class TResetMetadataParams implements org.apache.thrift.TBase<TResetMetadataParams, TResetMetadataParams._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TResetMetadataParams");

  private static final org.apache.thrift.protocol.TField IS_REFRESH_FIELD_DESC = new org.apache.thrift.protocol.TField("is_refresh", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField TABLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("table_name", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TResetMetadataParamsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TResetMetadataParamsTupleSchemeFactory());
  }

  public boolean is_refresh; // required
  public TTableName table_name; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IS_REFRESH((short)1, "is_refresh"),
    TABLE_NAME((short)2, "table_name");

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
        case 1: // IS_REFRESH
          return IS_REFRESH;
        case 2: // TABLE_NAME
          return TABLE_NAME;
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
  private static final int __IS_REFRESH_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TABLE_NAME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IS_REFRESH, new org.apache.thrift.meta_data.FieldMetaData("is_refresh", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TABLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("table_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TTableName.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TResetMetadataParams.class, metaDataMap);
  }

  public TResetMetadataParams() {
  }

  public TResetMetadataParams(
    boolean is_refresh)
  {
    this();
    this.is_refresh = is_refresh;
    setIs_refreshIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TResetMetadataParams(TResetMetadataParams other) {
    __isset_bitfield = other.__isset_bitfield;
    this.is_refresh = other.is_refresh;
    if (other.isSetTable_name()) {
      this.table_name = new TTableName(other.table_name);
    }
  }

  public TResetMetadataParams deepCopy() {
    return new TResetMetadataParams(this);
  }

  @Override
  public void clear() {
    setIs_refreshIsSet(false);
    this.is_refresh = false;
    this.table_name = null;
  }

  public boolean isIs_refresh() {
    return this.is_refresh;
  }

  public TResetMetadataParams setIs_refresh(boolean is_refresh) {
    this.is_refresh = is_refresh;
    setIs_refreshIsSet(true);
    return this;
  }

  public void unsetIs_refresh() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_REFRESH_ISSET_ID);
  }

  /** Returns true if field is_refresh is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_refresh() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_REFRESH_ISSET_ID);
  }

  public void setIs_refreshIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_REFRESH_ISSET_ID, value);
  }

  public TTableName getTable_name() {
    return this.table_name;
  }

  public TResetMetadataParams setTable_name(TTableName table_name) {
    this.table_name = table_name;
    return this;
  }

  public void unsetTable_name() {
    this.table_name = null;
  }

  /** Returns true if field table_name is set (has been assigned a value) and false otherwise */
  public boolean isSetTable_name() {
    return this.table_name != null;
  }

  public void setTable_nameIsSet(boolean value) {
    if (!value) {
      this.table_name = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IS_REFRESH:
      if (value == null) {
        unsetIs_refresh();
      } else {
        setIs_refresh((Boolean)value);
      }
      break;

    case TABLE_NAME:
      if (value == null) {
        unsetTable_name();
      } else {
        setTable_name((TTableName)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IS_REFRESH:
      return Boolean.valueOf(isIs_refresh());

    case TABLE_NAME:
      return getTable_name();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IS_REFRESH:
      return isSetIs_refresh();
    case TABLE_NAME:
      return isSetTable_name();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TResetMetadataParams)
      return this.equals((TResetMetadataParams)that);
    return false;
  }

  public boolean equals(TResetMetadataParams that) {
    if (that == null)
      return false;

    boolean this_present_is_refresh = true;
    boolean that_present_is_refresh = true;
    if (this_present_is_refresh || that_present_is_refresh) {
      if (!(this_present_is_refresh && that_present_is_refresh))
        return false;
      if (this.is_refresh != that.is_refresh)
        return false;
    }

    boolean this_present_table_name = true && this.isSetTable_name();
    boolean that_present_table_name = true && that.isSetTable_name();
    if (this_present_table_name || that_present_table_name) {
      if (!(this_present_table_name && that_present_table_name))
        return false;
      if (!this.table_name.equals(that.table_name))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TResetMetadataParams other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TResetMetadataParams typedOther = (TResetMetadataParams)other;

    lastComparison = Boolean.valueOf(isSetIs_refresh()).compareTo(typedOther.isSetIs_refresh());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_refresh()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_refresh, typedOther.is_refresh);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTable_name()).compareTo(typedOther.isSetTable_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTable_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.table_name, typedOther.table_name);
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
    StringBuilder sb = new StringBuilder("TResetMetadataParams(");
    boolean first = true;

    sb.append("is_refresh:");
    sb.append(this.is_refresh);
    first = false;
    if (isSetTable_name()) {
      if (!first) sb.append(", ");
      sb.append("table_name:");
      if (this.table_name == null) {
        sb.append("null");
      } else {
        sb.append(this.table_name);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'is_refresh' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
    if (table_name != null) {
      table_name.validate();
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TResetMetadataParamsStandardSchemeFactory implements SchemeFactory {
    public TResetMetadataParamsStandardScheme getScheme() {
      return new TResetMetadataParamsStandardScheme();
    }
  }

  private static class TResetMetadataParamsStandardScheme extends StandardScheme<TResetMetadataParams> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TResetMetadataParams struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IS_REFRESH
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_refresh = iprot.readBool();
              struct.setIs_refreshIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TABLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.table_name = new TTableName();
              struct.table_name.read(iprot);
              struct.setTable_nameIsSet(true);
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
      if (!struct.isSetIs_refresh()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'is_refresh' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TResetMetadataParams struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IS_REFRESH_FIELD_DESC);
      oprot.writeBool(struct.is_refresh);
      oprot.writeFieldEnd();
      if (struct.table_name != null) {
        if (struct.isSetTable_name()) {
          oprot.writeFieldBegin(TABLE_NAME_FIELD_DESC);
          struct.table_name.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TResetMetadataParamsTupleSchemeFactory implements SchemeFactory {
    public TResetMetadataParamsTupleScheme getScheme() {
      return new TResetMetadataParamsTupleScheme();
    }
  }

  private static class TResetMetadataParamsTupleScheme extends TupleScheme<TResetMetadataParams> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TResetMetadataParams struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBool(struct.is_refresh);
      BitSet optionals = new BitSet();
      if (struct.isSetTable_name()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetTable_name()) {
        struct.table_name.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TResetMetadataParams struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.is_refresh = iprot.readBool();
      struct.setIs_refreshIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.table_name = new TTableName();
        struct.table_name.read(iprot);
        struct.setTable_nameIsSet(true);
      }
    }
  }

}

