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

public class TAggregateExpr implements org.apache.thrift.TBase<TAggregateExpr, TAggregateExpr._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAggregateExpr");

  private static final org.apache.thrift.protocol.TField IS_STAR_FIELD_DESC = new org.apache.thrift.protocol.TField("is_star", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField IS_DISTINCT_FIELD_DESC = new org.apache.thrift.protocol.TField("is_distinct", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField OP_FIELD_DESC = new org.apache.thrift.protocol.TField("op", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TAggregateExprStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TAggregateExprTupleSchemeFactory());
  }

  public boolean is_star; // required
  public boolean is_distinct; // required
  /**
   * 
   * @see TAggregationOp
   */
  public TAggregationOp op; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IS_STAR((short)1, "is_star"),
    IS_DISTINCT((short)2, "is_distinct"),
    /**
     * 
     * @see TAggregationOp
     */
    OP((short)3, "op");

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
        case 1: // IS_STAR
          return IS_STAR;
        case 2: // IS_DISTINCT
          return IS_DISTINCT;
        case 3: // OP
          return OP;
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
  private static final int __IS_STAR_ISSET_ID = 0;
  private static final int __IS_DISTINCT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IS_STAR, new org.apache.thrift.meta_data.FieldMetaData("is_star", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_DISTINCT, new org.apache.thrift.meta_data.FieldMetaData("is_distinct", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.OP, new org.apache.thrift.meta_data.FieldMetaData("op", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TAggregationOp.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAggregateExpr.class, metaDataMap);
  }

  public TAggregateExpr() {
  }

  public TAggregateExpr(
    boolean is_star,
    boolean is_distinct,
    TAggregationOp op)
  {
    this();
    this.is_star = is_star;
    setIs_starIsSet(true);
    this.is_distinct = is_distinct;
    setIs_distinctIsSet(true);
    this.op = op;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAggregateExpr(TAggregateExpr other) {
    __isset_bitfield = other.__isset_bitfield;
    this.is_star = other.is_star;
    this.is_distinct = other.is_distinct;
    if (other.isSetOp()) {
      this.op = other.op;
    }
  }

  public TAggregateExpr deepCopy() {
    return new TAggregateExpr(this);
  }

  @Override
  public void clear() {
    setIs_starIsSet(false);
    this.is_star = false;
    setIs_distinctIsSet(false);
    this.is_distinct = false;
    this.op = null;
  }

  public boolean isIs_star() {
    return this.is_star;
  }

  public TAggregateExpr setIs_star(boolean is_star) {
    this.is_star = is_star;
    setIs_starIsSet(true);
    return this;
  }

  public void unsetIs_star() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_STAR_ISSET_ID);
  }

  /** Returns true if field is_star is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_star() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_STAR_ISSET_ID);
  }

  public void setIs_starIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_STAR_ISSET_ID, value);
  }

  public boolean isIs_distinct() {
    return this.is_distinct;
  }

  public TAggregateExpr setIs_distinct(boolean is_distinct) {
    this.is_distinct = is_distinct;
    setIs_distinctIsSet(true);
    return this;
  }

  public void unsetIs_distinct() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID);
  }

  /** Returns true if field is_distinct is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_distinct() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID);
  }

  public void setIs_distinctIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID, value);
  }

  /**
   * 
   * @see TAggregationOp
   */
  public TAggregationOp getOp() {
    return this.op;
  }

  /**
   * 
   * @see TAggregationOp
   */
  public TAggregateExpr setOp(TAggregationOp op) {
    this.op = op;
    return this;
  }

  public void unsetOp() {
    this.op = null;
  }

  /** Returns true if field op is set (has been assigned a value) and false otherwise */
  public boolean isSetOp() {
    return this.op != null;
  }

  public void setOpIsSet(boolean value) {
    if (!value) {
      this.op = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IS_STAR:
      if (value == null) {
        unsetIs_star();
      } else {
        setIs_star((Boolean)value);
      }
      break;

    case IS_DISTINCT:
      if (value == null) {
        unsetIs_distinct();
      } else {
        setIs_distinct((Boolean)value);
      }
      break;

    case OP:
      if (value == null) {
        unsetOp();
      } else {
        setOp((TAggregationOp)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IS_STAR:
      return Boolean.valueOf(isIs_star());

    case IS_DISTINCT:
      return Boolean.valueOf(isIs_distinct());

    case OP:
      return getOp();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IS_STAR:
      return isSetIs_star();
    case IS_DISTINCT:
      return isSetIs_distinct();
    case OP:
      return isSetOp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TAggregateExpr)
      return this.equals((TAggregateExpr)that);
    return false;
  }

  public boolean equals(TAggregateExpr that) {
    if (that == null)
      return false;

    boolean this_present_is_star = true;
    boolean that_present_is_star = true;
    if (this_present_is_star || that_present_is_star) {
      if (!(this_present_is_star && that_present_is_star))
        return false;
      if (this.is_star != that.is_star)
        return false;
    }

    boolean this_present_is_distinct = true;
    boolean that_present_is_distinct = true;
    if (this_present_is_distinct || that_present_is_distinct) {
      if (!(this_present_is_distinct && that_present_is_distinct))
        return false;
      if (this.is_distinct != that.is_distinct)
        return false;
    }

    boolean this_present_op = true && this.isSetOp();
    boolean that_present_op = true && that.isSetOp();
    if (this_present_op || that_present_op) {
      if (!(this_present_op && that_present_op))
        return false;
      if (!this.op.equals(that.op))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TAggregateExpr other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TAggregateExpr typedOther = (TAggregateExpr)other;

    lastComparison = Boolean.valueOf(isSetIs_star()).compareTo(typedOther.isSetIs_star());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_star()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_star, typedOther.is_star);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIs_distinct()).compareTo(typedOther.isSetIs_distinct());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_distinct()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_distinct, typedOther.is_distinct);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOp()).compareTo(typedOther.isSetOp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.op, typedOther.op);
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
    StringBuilder sb = new StringBuilder("TAggregateExpr(");
    boolean first = true;

    sb.append("is_star:");
    sb.append(this.is_star);
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_distinct:");
    sb.append(this.is_distinct);
    first = false;
    if (!first) sb.append(", ");
    sb.append("op:");
    if (this.op == null) {
      sb.append("null");
    } else {
      sb.append(this.op);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'is_star' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'is_distinct' because it's a primitive and you chose the non-beans generator.
    if (op == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'op' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
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

  private static class TAggregateExprStandardSchemeFactory implements SchemeFactory {
    public TAggregateExprStandardScheme getScheme() {
      return new TAggregateExprStandardScheme();
    }
  }

  private static class TAggregateExprStandardScheme extends StandardScheme<TAggregateExpr> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAggregateExpr struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IS_STAR
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_star = iprot.readBool();
              struct.setIs_starIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IS_DISTINCT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_distinct = iprot.readBool();
              struct.setIs_distinctIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.op = TAggregationOp.findByValue(iprot.readI32());
              struct.setOpIsSet(true);
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
      if (!struct.isSetIs_star()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'is_star' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetIs_distinct()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'is_distinct' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAggregateExpr struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IS_STAR_FIELD_DESC);
      oprot.writeBool(struct.is_star);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_DISTINCT_FIELD_DESC);
      oprot.writeBool(struct.is_distinct);
      oprot.writeFieldEnd();
      if (struct.op != null) {
        oprot.writeFieldBegin(OP_FIELD_DESC);
        oprot.writeI32(struct.op.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAggregateExprTupleSchemeFactory implements SchemeFactory {
    public TAggregateExprTupleScheme getScheme() {
      return new TAggregateExprTupleScheme();
    }
  }

  private static class TAggregateExprTupleScheme extends TupleScheme<TAggregateExpr> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAggregateExpr struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBool(struct.is_star);
      oprot.writeBool(struct.is_distinct);
      oprot.writeI32(struct.op.getValue());
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAggregateExpr struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.is_star = iprot.readBool();
      struct.setIs_starIsSet(true);
      struct.is_distinct = iprot.readBool();
      struct.setIs_distinctIsSet(true);
      struct.op = TAggregationOp.findByValue(iprot.readI32());
      struct.setOpIsSet(true);
    }
  }

}

