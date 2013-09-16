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

public class TColumnValue implements org.apache.thrift.TBase<TColumnValue, TColumnValue._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TColumnValue");

  private static final org.apache.thrift.protocol.TField BOOL_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("boolVal", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField INT_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("intVal", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField LONG_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("longVal", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField DOUBLE_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("doubleVal", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField STRING_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("stringVal", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TColumnValueStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TColumnValueTupleSchemeFactory());
  }

  public boolean boolVal; // optional
  public int intVal; // optional
  public long longVal; // optional
  public double doubleVal; // optional
  public String stringVal; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BOOL_VAL((short)1, "boolVal"),
    INT_VAL((short)2, "intVal"),
    LONG_VAL((short)3, "longVal"),
    DOUBLE_VAL((short)4, "doubleVal"),
    STRING_VAL((short)5, "stringVal");

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
        case 1: // BOOL_VAL
          return BOOL_VAL;
        case 2: // INT_VAL
          return INT_VAL;
        case 3: // LONG_VAL
          return LONG_VAL;
        case 4: // DOUBLE_VAL
          return DOUBLE_VAL;
        case 5: // STRING_VAL
          return STRING_VAL;
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
  private static final int __BOOLVAL_ISSET_ID = 0;
  private static final int __INTVAL_ISSET_ID = 1;
  private static final int __LONGVAL_ISSET_ID = 2;
  private static final int __DOUBLEVAL_ISSET_ID = 3;
  private BitSet __isset_bit_vector = new BitSet(4);
  private _Fields optionals[] = {_Fields.BOOL_VAL,_Fields.INT_VAL,_Fields.LONG_VAL,_Fields.DOUBLE_VAL,_Fields.STRING_VAL};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BOOL_VAL, new org.apache.thrift.meta_data.FieldMetaData("boolVal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.INT_VAL, new org.apache.thrift.meta_data.FieldMetaData("intVal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LONG_VAL, new org.apache.thrift.meta_data.FieldMetaData("longVal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DOUBLE_VAL, new org.apache.thrift.meta_data.FieldMetaData("doubleVal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.STRING_VAL, new org.apache.thrift.meta_data.FieldMetaData("stringVal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TColumnValue.class, metaDataMap);
  }

  public TColumnValue() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TColumnValue(TColumnValue other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.boolVal = other.boolVal;
    this.intVal = other.intVal;
    this.longVal = other.longVal;
    this.doubleVal = other.doubleVal;
    if (other.isSetStringVal()) {
      this.stringVal = other.stringVal;
    }
  }

  public TColumnValue deepCopy() {
    return new TColumnValue(this);
  }

  @Override
  public void clear() {
    setBoolValIsSet(false);
    this.boolVal = false;
    setIntValIsSet(false);
    this.intVal = 0;
    setLongValIsSet(false);
    this.longVal = 0;
    setDoubleValIsSet(false);
    this.doubleVal = 0.0;
    this.stringVal = null;
  }

  public boolean isBoolVal() {
    return this.boolVal;
  }

  public TColumnValue setBoolVal(boolean boolVal) {
    this.boolVal = boolVal;
    setBoolValIsSet(true);
    return this;
  }

  public void unsetBoolVal() {
    __isset_bit_vector.clear(__BOOLVAL_ISSET_ID);
  }

  /** Returns true if field boolVal is set (has been assigned a value) and false otherwise */
  public boolean isSetBoolVal() {
    return __isset_bit_vector.get(__BOOLVAL_ISSET_ID);
  }

  public void setBoolValIsSet(boolean value) {
    __isset_bit_vector.set(__BOOLVAL_ISSET_ID, value);
  }

  public int getIntVal() {
    return this.intVal;
  }

  public TColumnValue setIntVal(int intVal) {
    this.intVal = intVal;
    setIntValIsSet(true);
    return this;
  }

  public void unsetIntVal() {
    __isset_bit_vector.clear(__INTVAL_ISSET_ID);
  }

  /** Returns true if field intVal is set (has been assigned a value) and false otherwise */
  public boolean isSetIntVal() {
    return __isset_bit_vector.get(__INTVAL_ISSET_ID);
  }

  public void setIntValIsSet(boolean value) {
    __isset_bit_vector.set(__INTVAL_ISSET_ID, value);
  }

  public long getLongVal() {
    return this.longVal;
  }

  public TColumnValue setLongVal(long longVal) {
    this.longVal = longVal;
    setLongValIsSet(true);
    return this;
  }

  public void unsetLongVal() {
    __isset_bit_vector.clear(__LONGVAL_ISSET_ID);
  }

  /** Returns true if field longVal is set (has been assigned a value) and false otherwise */
  public boolean isSetLongVal() {
    return __isset_bit_vector.get(__LONGVAL_ISSET_ID);
  }

  public void setLongValIsSet(boolean value) {
    __isset_bit_vector.set(__LONGVAL_ISSET_ID, value);
  }

  public double getDoubleVal() {
    return this.doubleVal;
  }

  public TColumnValue setDoubleVal(double doubleVal) {
    this.doubleVal = doubleVal;
    setDoubleValIsSet(true);
    return this;
  }

  public void unsetDoubleVal() {
    __isset_bit_vector.clear(__DOUBLEVAL_ISSET_ID);
  }

  /** Returns true if field doubleVal is set (has been assigned a value) and false otherwise */
  public boolean isSetDoubleVal() {
    return __isset_bit_vector.get(__DOUBLEVAL_ISSET_ID);
  }

  public void setDoubleValIsSet(boolean value) {
    __isset_bit_vector.set(__DOUBLEVAL_ISSET_ID, value);
  }

  public String getStringVal() {
    return this.stringVal;
  }

  public TColumnValue setStringVal(String stringVal) {
    this.stringVal = stringVal;
    return this;
  }

  public void unsetStringVal() {
    this.stringVal = null;
  }

  /** Returns true if field stringVal is set (has been assigned a value) and false otherwise */
  public boolean isSetStringVal() {
    return this.stringVal != null;
  }

  public void setStringValIsSet(boolean value) {
    if (!value) {
      this.stringVal = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BOOL_VAL:
      if (value == null) {
        unsetBoolVal();
      } else {
        setBoolVal((Boolean)value);
      }
      break;

    case INT_VAL:
      if (value == null) {
        unsetIntVal();
      } else {
        setIntVal((Integer)value);
      }
      break;

    case LONG_VAL:
      if (value == null) {
        unsetLongVal();
      } else {
        setLongVal((Long)value);
      }
      break;

    case DOUBLE_VAL:
      if (value == null) {
        unsetDoubleVal();
      } else {
        setDoubleVal((Double)value);
      }
      break;

    case STRING_VAL:
      if (value == null) {
        unsetStringVal();
      } else {
        setStringVal((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BOOL_VAL:
      return Boolean.valueOf(isBoolVal());

    case INT_VAL:
      return Integer.valueOf(getIntVal());

    case LONG_VAL:
      return Long.valueOf(getLongVal());

    case DOUBLE_VAL:
      return Double.valueOf(getDoubleVal());

    case STRING_VAL:
      return getStringVal();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BOOL_VAL:
      return isSetBoolVal();
    case INT_VAL:
      return isSetIntVal();
    case LONG_VAL:
      return isSetLongVal();
    case DOUBLE_VAL:
      return isSetDoubleVal();
    case STRING_VAL:
      return isSetStringVal();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TColumnValue)
      return this.equals((TColumnValue)that);
    return false;
  }

  public boolean equals(TColumnValue that) {
    if (that == null)
      return false;

    boolean this_present_boolVal = true && this.isSetBoolVal();
    boolean that_present_boolVal = true && that.isSetBoolVal();
    if (this_present_boolVal || that_present_boolVal) {
      if (!(this_present_boolVal && that_present_boolVal))
        return false;
      if (this.boolVal != that.boolVal)
        return false;
    }

    boolean this_present_intVal = true && this.isSetIntVal();
    boolean that_present_intVal = true && that.isSetIntVal();
    if (this_present_intVal || that_present_intVal) {
      if (!(this_present_intVal && that_present_intVal))
        return false;
      if (this.intVal != that.intVal)
        return false;
    }

    boolean this_present_longVal = true && this.isSetLongVal();
    boolean that_present_longVal = true && that.isSetLongVal();
    if (this_present_longVal || that_present_longVal) {
      if (!(this_present_longVal && that_present_longVal))
        return false;
      if (this.longVal != that.longVal)
        return false;
    }

    boolean this_present_doubleVal = true && this.isSetDoubleVal();
    boolean that_present_doubleVal = true && that.isSetDoubleVal();
    if (this_present_doubleVal || that_present_doubleVal) {
      if (!(this_present_doubleVal && that_present_doubleVal))
        return false;
      if (this.doubleVal != that.doubleVal)
        return false;
    }

    boolean this_present_stringVal = true && this.isSetStringVal();
    boolean that_present_stringVal = true && that.isSetStringVal();
    if (this_present_stringVal || that_present_stringVal) {
      if (!(this_present_stringVal && that_present_stringVal))
        return false;
      if (!this.stringVal.equals(that.stringVal))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TColumnValue other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TColumnValue typedOther = (TColumnValue)other;

    lastComparison = Boolean.valueOf(isSetBoolVal()).compareTo(typedOther.isSetBoolVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBoolVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.boolVal, typedOther.boolVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIntVal()).compareTo(typedOther.isSetIntVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIntVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.intVal, typedOther.intVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLongVal()).compareTo(typedOther.isSetLongVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLongVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.longVal, typedOther.longVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDoubleVal()).compareTo(typedOther.isSetDoubleVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDoubleVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.doubleVal, typedOther.doubleVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStringVal()).compareTo(typedOther.isSetStringVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStringVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stringVal, typedOther.stringVal);
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
    StringBuilder sb = new StringBuilder("TColumnValue(");
    boolean first = true;

    if (isSetBoolVal()) {
      sb.append("boolVal:");
      sb.append(this.boolVal);
      first = false;
    }
    if (isSetIntVal()) {
      if (!first) sb.append(", ");
      sb.append("intVal:");
      sb.append(this.intVal);
      first = false;
    }
    if (isSetLongVal()) {
      if (!first) sb.append(", ");
      sb.append("longVal:");
      sb.append(this.longVal);
      first = false;
    }
    if (isSetDoubleVal()) {
      if (!first) sb.append(", ");
      sb.append("doubleVal:");
      sb.append(this.doubleVal);
      first = false;
    }
    if (isSetStringVal()) {
      if (!first) sb.append(", ");
      sb.append("stringVal:");
      if (this.stringVal == null) {
        sb.append("null");
      } else {
        sb.append(this.stringVal);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TColumnValueStandardSchemeFactory implements SchemeFactory {
    public TColumnValueStandardScheme getScheme() {
      return new TColumnValueStandardScheme();
    }
  }

  private static class TColumnValueStandardScheme extends StandardScheme<TColumnValue> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TColumnValue struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BOOL_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.boolVal = iprot.readBool();
              struct.setBoolValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // INT_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.intVal = iprot.readI32();
              struct.setIntValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LONG_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.longVal = iprot.readI64();
              struct.setLongValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DOUBLE_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.doubleVal = iprot.readDouble();
              struct.setDoubleValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // STRING_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.stringVal = iprot.readString();
              struct.setStringValIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TColumnValue struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetBoolVal()) {
        oprot.writeFieldBegin(BOOL_VAL_FIELD_DESC);
        oprot.writeBool(struct.boolVal);
        oprot.writeFieldEnd();
      }
      if (struct.isSetIntVal()) {
        oprot.writeFieldBegin(INT_VAL_FIELD_DESC);
        oprot.writeI32(struct.intVal);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLongVal()) {
        oprot.writeFieldBegin(LONG_VAL_FIELD_DESC);
        oprot.writeI64(struct.longVal);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDoubleVal()) {
        oprot.writeFieldBegin(DOUBLE_VAL_FIELD_DESC);
        oprot.writeDouble(struct.doubleVal);
        oprot.writeFieldEnd();
      }
      if (struct.stringVal != null) {
        if (struct.isSetStringVal()) {
          oprot.writeFieldBegin(STRING_VAL_FIELD_DESC);
          oprot.writeString(struct.stringVal);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TColumnValueTupleSchemeFactory implements SchemeFactory {
    public TColumnValueTupleScheme getScheme() {
      return new TColumnValueTupleScheme();
    }
  }

  private static class TColumnValueTupleScheme extends TupleScheme<TColumnValue> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TColumnValue struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBoolVal()) {
        optionals.set(0);
      }
      if (struct.isSetIntVal()) {
        optionals.set(1);
      }
      if (struct.isSetLongVal()) {
        optionals.set(2);
      }
      if (struct.isSetDoubleVal()) {
        optionals.set(3);
      }
      if (struct.isSetStringVal()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetBoolVal()) {
        oprot.writeBool(struct.boolVal);
      }
      if (struct.isSetIntVal()) {
        oprot.writeI32(struct.intVal);
      }
      if (struct.isSetLongVal()) {
        oprot.writeI64(struct.longVal);
      }
      if (struct.isSetDoubleVal()) {
        oprot.writeDouble(struct.doubleVal);
      }
      if (struct.isSetStringVal()) {
        oprot.writeString(struct.stringVal);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TColumnValue struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.boolVal = iprot.readBool();
        struct.setBoolValIsSet(true);
      }
      if (incoming.get(1)) {
        struct.intVal = iprot.readI32();
        struct.setIntValIsSet(true);
      }
      if (incoming.get(2)) {
        struct.longVal = iprot.readI64();
        struct.setLongValIsSet(true);
      }
      if (incoming.get(3)) {
        struct.doubleVal = iprot.readDouble();
        struct.setDoubleValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.stringVal = iprot.readString();
        struct.setStringValIsSet(true);
      }
    }
  }

}

