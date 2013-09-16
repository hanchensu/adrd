/**
 * Autogenerated by Thrift Compiler (0.8.0)
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

public class TAlterTableChangeColParams implements org.apache.thrift.TBase<TAlterTableChangeColParams, TAlterTableChangeColParams._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAlterTableChangeColParams");

  private static final org.apache.thrift.protocol.TField COL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("col_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField NEW_COL_DEF_FIELD_DESC = new org.apache.thrift.protocol.TField("new_col_def", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TAlterTableChangeColParamsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TAlterTableChangeColParamsTupleSchemeFactory());
  }

  public String col_name; // required
  public TColumnDef new_col_def; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COL_NAME((short)1, "col_name"),
    NEW_COL_DEF((short)2, "new_col_def");

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
        case 1: // COL_NAME
          return COL_NAME;
        case 2: // NEW_COL_DEF
          return NEW_COL_DEF;
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
    tmpMap.put(_Fields.COL_NAME, new org.apache.thrift.meta_data.FieldMetaData("col_name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NEW_COL_DEF, new org.apache.thrift.meta_data.FieldMetaData("new_col_def", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TColumnDef.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAlterTableChangeColParams.class, metaDataMap);
  }

  public TAlterTableChangeColParams() {
  }

  public TAlterTableChangeColParams(
    String col_name,
    TColumnDef new_col_def)
  {
    this();
    this.col_name = col_name;
    this.new_col_def = new_col_def;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAlterTableChangeColParams(TAlterTableChangeColParams other) {
    if (other.isSetCol_name()) {
      this.col_name = other.col_name;
    }
    if (other.isSetNew_col_def()) {
      this.new_col_def = new TColumnDef(other.new_col_def);
    }
  }

  public TAlterTableChangeColParams deepCopy() {
    return new TAlterTableChangeColParams(this);
  }

  @Override
  public void clear() {
    this.col_name = null;
    this.new_col_def = null;
  }

  public String getCol_name() {
    return this.col_name;
  }

  public TAlterTableChangeColParams setCol_name(String col_name) {
    this.col_name = col_name;
    return this;
  }

  public void unsetCol_name() {
    this.col_name = null;
  }

  /** Returns true if field col_name is set (has been assigned a value) and false otherwise */
  public boolean isSetCol_name() {
    return this.col_name != null;
  }

  public void setCol_nameIsSet(boolean value) {
    if (!value) {
      this.col_name = null;
    }
  }

  public TColumnDef getNew_col_def() {
    return this.new_col_def;
  }

  public TAlterTableChangeColParams setNew_col_def(TColumnDef new_col_def) {
    this.new_col_def = new_col_def;
    return this;
  }

  public void unsetNew_col_def() {
    this.new_col_def = null;
  }

  /** Returns true if field new_col_def is set (has been assigned a value) and false otherwise */
  public boolean isSetNew_col_def() {
    return this.new_col_def != null;
  }

  public void setNew_col_defIsSet(boolean value) {
    if (!value) {
      this.new_col_def = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COL_NAME:
      if (value == null) {
        unsetCol_name();
      } else {
        setCol_name((String)value);
      }
      break;

    case NEW_COL_DEF:
      if (value == null) {
        unsetNew_col_def();
      } else {
        setNew_col_def((TColumnDef)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COL_NAME:
      return getCol_name();

    case NEW_COL_DEF:
      return getNew_col_def();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COL_NAME:
      return isSetCol_name();
    case NEW_COL_DEF:
      return isSetNew_col_def();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TAlterTableChangeColParams)
      return this.equals((TAlterTableChangeColParams)that);
    return false;
  }

  public boolean equals(TAlterTableChangeColParams that) {
    if (that == null)
      return false;

    boolean this_present_col_name = true && this.isSetCol_name();
    boolean that_present_col_name = true && that.isSetCol_name();
    if (this_present_col_name || that_present_col_name) {
      if (!(this_present_col_name && that_present_col_name))
        return false;
      if (!this.col_name.equals(that.col_name))
        return false;
    }

    boolean this_present_new_col_def = true && this.isSetNew_col_def();
    boolean that_present_new_col_def = true && that.isSetNew_col_def();
    if (this_present_new_col_def || that_present_new_col_def) {
      if (!(this_present_new_col_def && that_present_new_col_def))
        return false;
      if (!this.new_col_def.equals(that.new_col_def))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TAlterTableChangeColParams other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TAlterTableChangeColParams typedOther = (TAlterTableChangeColParams)other;

    lastComparison = Boolean.valueOf(isSetCol_name()).compareTo(typedOther.isSetCol_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCol_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.col_name, typedOther.col_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNew_col_def()).compareTo(typedOther.isSetNew_col_def());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNew_col_def()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.new_col_def, typedOther.new_col_def);
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
    StringBuilder sb = new StringBuilder("TAlterTableChangeColParams(");
    boolean first = true;

    sb.append("col_name:");
    if (this.col_name == null) {
      sb.append("null");
    } else {
      sb.append(this.col_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("new_col_def:");
    if (this.new_col_def == null) {
      sb.append("null");
    } else {
      sb.append(this.new_col_def);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (col_name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'col_name' was not present! Struct: " + toString());
    }
    if (new_col_def == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'new_col_def' was not present! Struct: " + toString());
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

  private static class TAlterTableChangeColParamsStandardSchemeFactory implements SchemeFactory {
    public TAlterTableChangeColParamsStandardScheme getScheme() {
      return new TAlterTableChangeColParamsStandardScheme();
    }
  }

  private static class TAlterTableChangeColParamsStandardScheme extends StandardScheme<TAlterTableChangeColParams> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAlterTableChangeColParams struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.col_name = iprot.readString();
              struct.setCol_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NEW_COL_DEF
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.new_col_def = new TColumnDef();
              struct.new_col_def.read(iprot);
              struct.setNew_col_defIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAlterTableChangeColParams struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.col_name != null) {
        oprot.writeFieldBegin(COL_NAME_FIELD_DESC);
        oprot.writeString(struct.col_name);
        oprot.writeFieldEnd();
      }
      if (struct.new_col_def != null) {
        oprot.writeFieldBegin(NEW_COL_DEF_FIELD_DESC);
        struct.new_col_def.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAlterTableChangeColParamsTupleSchemeFactory implements SchemeFactory {
    public TAlterTableChangeColParamsTupleScheme getScheme() {
      return new TAlterTableChangeColParamsTupleScheme();
    }
  }

  private static class TAlterTableChangeColParamsTupleScheme extends TupleScheme<TAlterTableChangeColParams> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAlterTableChangeColParams struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.col_name);
      struct.new_col_def.write(oprot);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAlterTableChangeColParams struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.col_name = iprot.readString();
      struct.setCol_nameIsSet(true);
      struct.new_col_def = new TColumnDef();
      struct.new_col_def.read(iprot);
      struct.setNew_col_defIsSet(true);
    }
  }

}

