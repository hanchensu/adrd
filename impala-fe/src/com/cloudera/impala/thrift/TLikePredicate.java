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

public class TLikePredicate implements org.apache.thrift.TBase<TLikePredicate, TLikePredicate._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TLikePredicate");

  private static final org.apache.thrift.protocol.TField ESCAPE_CHAR_FIELD_DESC = new org.apache.thrift.protocol.TField("escape_char", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TLikePredicateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TLikePredicateTupleSchemeFactory());
  }

  public String escape_char; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ESCAPE_CHAR((short)1, "escape_char");

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
        case 1: // ESCAPE_CHAR
          return ESCAPE_CHAR;
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
    tmpMap.put(_Fields.ESCAPE_CHAR, new org.apache.thrift.meta_data.FieldMetaData("escape_char", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TLikePredicate.class, metaDataMap);
  }

  public TLikePredicate() {
  }

  public TLikePredicate(
    String escape_char)
  {
    this();
    this.escape_char = escape_char;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TLikePredicate(TLikePredicate other) {
    if (other.isSetEscape_char()) {
      this.escape_char = other.escape_char;
    }
  }

  public TLikePredicate deepCopy() {
    return new TLikePredicate(this);
  }

  @Override
  public void clear() {
    this.escape_char = null;
  }

  public String getEscape_char() {
    return this.escape_char;
  }

  public TLikePredicate setEscape_char(String escape_char) {
    this.escape_char = escape_char;
    return this;
  }

  public void unsetEscape_char() {
    this.escape_char = null;
  }

  /** Returns true if field escape_char is set (has been assigned a value) and false otherwise */
  public boolean isSetEscape_char() {
    return this.escape_char != null;
  }

  public void setEscape_charIsSet(boolean value) {
    if (!value) {
      this.escape_char = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ESCAPE_CHAR:
      if (value == null) {
        unsetEscape_char();
      } else {
        setEscape_char((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ESCAPE_CHAR:
      return getEscape_char();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ESCAPE_CHAR:
      return isSetEscape_char();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TLikePredicate)
      return this.equals((TLikePredicate)that);
    return false;
  }

  public boolean equals(TLikePredicate that) {
    if (that == null)
      return false;

    boolean this_present_escape_char = true && this.isSetEscape_char();
    boolean that_present_escape_char = true && that.isSetEscape_char();
    if (this_present_escape_char || that_present_escape_char) {
      if (!(this_present_escape_char && that_present_escape_char))
        return false;
      if (!this.escape_char.equals(that.escape_char))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TLikePredicate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TLikePredicate typedOther = (TLikePredicate)other;

    lastComparison = Boolean.valueOf(isSetEscape_char()).compareTo(typedOther.isSetEscape_char());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEscape_char()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.escape_char, typedOther.escape_char);
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
    StringBuilder sb = new StringBuilder("TLikePredicate(");
    boolean first = true;

    sb.append("escape_char:");
    if (this.escape_char == null) {
      sb.append("null");
    } else {
      sb.append(this.escape_char);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (escape_char == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'escape_char' was not present! Struct: " + toString());
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

  private static class TLikePredicateStandardSchemeFactory implements SchemeFactory {
    public TLikePredicateStandardScheme getScheme() {
      return new TLikePredicateStandardScheme();
    }
  }

  private static class TLikePredicateStandardScheme extends StandardScheme<TLikePredicate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TLikePredicate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ESCAPE_CHAR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.escape_char = iprot.readString();
              struct.setEscape_charIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TLikePredicate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.escape_char != null) {
        oprot.writeFieldBegin(ESCAPE_CHAR_FIELD_DESC);
        oprot.writeString(struct.escape_char);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TLikePredicateTupleSchemeFactory implements SchemeFactory {
    public TLikePredicateTupleScheme getScheme() {
      return new TLikePredicateTupleScheme();
    }
  }

  private static class TLikePredicateTupleScheme extends TupleScheme<TLikePredicate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TLikePredicate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.escape_char);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TLikePredicate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.escape_char = iprot.readString();
      struct.setEscape_charIsSet(true);
    }
  }

}

