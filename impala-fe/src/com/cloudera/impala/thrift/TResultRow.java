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

public class TResultRow implements org.apache.thrift.TBase<TResultRow, TResultRow._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TResultRow");

  private static final org.apache.thrift.protocol.TField COL_VALS_FIELD_DESC = new org.apache.thrift.protocol.TField("colVals", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TResultRowStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TResultRowTupleSchemeFactory());
  }

  public List<TColumnValue> colVals; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COL_VALS((short)1, "colVals");

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
        case 1: // COL_VALS
          return COL_VALS;
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
    tmpMap.put(_Fields.COL_VALS, new org.apache.thrift.meta_data.FieldMetaData("colVals", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TColumnValue.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TResultRow.class, metaDataMap);
  }

  public TResultRow() {
  }

  public TResultRow(
    List<TColumnValue> colVals)
  {
    this();
    this.colVals = colVals;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TResultRow(TResultRow other) {
    if (other.isSetColVals()) {
      List<TColumnValue> __this__colVals = new ArrayList<TColumnValue>();
      for (TColumnValue other_element : other.colVals) {
        __this__colVals.add(new TColumnValue(other_element));
      }
      this.colVals = __this__colVals;
    }
  }

  public TResultRow deepCopy() {
    return new TResultRow(this);
  }

  @Override
  public void clear() {
    this.colVals = null;
  }

  public int getColValsSize() {
    return (this.colVals == null) ? 0 : this.colVals.size();
  }

  public java.util.Iterator<TColumnValue> getColValsIterator() {
    return (this.colVals == null) ? null : this.colVals.iterator();
  }

  public void addToColVals(TColumnValue elem) {
    if (this.colVals == null) {
      this.colVals = new ArrayList<TColumnValue>();
    }
    this.colVals.add(elem);
  }

  public List<TColumnValue> getColVals() {
    return this.colVals;
  }

  public TResultRow setColVals(List<TColumnValue> colVals) {
    this.colVals = colVals;
    return this;
  }

  public void unsetColVals() {
    this.colVals = null;
  }

  /** Returns true if field colVals is set (has been assigned a value) and false otherwise */
  public boolean isSetColVals() {
    return this.colVals != null;
  }

  public void setColValsIsSet(boolean value) {
    if (!value) {
      this.colVals = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COL_VALS:
      if (value == null) {
        unsetColVals();
      } else {
        setColVals((List<TColumnValue>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COL_VALS:
      return getColVals();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COL_VALS:
      return isSetColVals();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TResultRow)
      return this.equals((TResultRow)that);
    return false;
  }

  public boolean equals(TResultRow that) {
    if (that == null)
      return false;

    boolean this_present_colVals = true && this.isSetColVals();
    boolean that_present_colVals = true && that.isSetColVals();
    if (this_present_colVals || that_present_colVals) {
      if (!(this_present_colVals && that_present_colVals))
        return false;
      if (!this.colVals.equals(that.colVals))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TResultRow other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TResultRow typedOther = (TResultRow)other;

    lastComparison = Boolean.valueOf(isSetColVals()).compareTo(typedOther.isSetColVals());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColVals()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.colVals, typedOther.colVals);
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
    StringBuilder sb = new StringBuilder("TResultRow(");
    boolean first = true;

    sb.append("colVals:");
    if (this.colVals == null) {
      sb.append("null");
    } else {
      sb.append(this.colVals);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TResultRowStandardSchemeFactory implements SchemeFactory {
    public TResultRowStandardScheme getScheme() {
      return new TResultRowStandardScheme();
    }
  }

  private static class TResultRowStandardScheme extends StandardScheme<TResultRow> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TResultRow struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COL_VALS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.colVals = new ArrayList<TColumnValue>(_list16.size);
                for (int _i17 = 0; _i17 < _list16.size; ++_i17)
                {
                  TColumnValue _elem18; // required
                  _elem18 = new TColumnValue();
                  _elem18.read(iprot);
                  struct.colVals.add(_elem18);
                }
                iprot.readListEnd();
              }
              struct.setColValsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TResultRow struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.colVals != null) {
        oprot.writeFieldBegin(COL_VALS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.colVals.size()));
          for (TColumnValue _iter19 : struct.colVals)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TResultRowTupleSchemeFactory implements SchemeFactory {
    public TResultRowTupleScheme getScheme() {
      return new TResultRowTupleScheme();
    }
  }

  private static class TResultRowTupleScheme extends TupleScheme<TResultRow> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TResultRow struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetColVals()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetColVals()) {
        {
          oprot.writeI32(struct.colVals.size());
          for (TColumnValue _iter20 : struct.colVals)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TResultRow struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.colVals = new ArrayList<TColumnValue>(_list21.size);
          for (int _i22 = 0; _i22 < _list21.size; ++_i22)
          {
            TColumnValue _elem23; // required
            _elem23 = new TColumnValue();
            _elem23.read(iprot);
            struct.colVals.add(_elem23);
          }
        }
        struct.setColValsIsSet(true);
      }
    }
  }

}

