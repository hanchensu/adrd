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

public class TAlterTableAddPartitionParams implements org.apache.thrift.TBase<TAlterTableAddPartitionParams, TAlterTableAddPartitionParams._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAlterTableAddPartitionParams");

  private static final org.apache.thrift.protocol.TField PARTITION_SPEC_FIELD_DESC = new org.apache.thrift.protocol.TField("partition_spec", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField IF_NOT_EXISTS_FIELD_DESC = new org.apache.thrift.protocol.TField("if_not_exists", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField LOCATION_FIELD_DESC = new org.apache.thrift.protocol.TField("location", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TAlterTableAddPartitionParamsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TAlterTableAddPartitionParamsTupleSchemeFactory());
  }

  public List<TPartitionKeyValue> partition_spec; // required
  public boolean if_not_exists; // required
  public String location; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PARTITION_SPEC((short)1, "partition_spec"),
    IF_NOT_EXISTS((short)3, "if_not_exists"),
    LOCATION((short)2, "location");

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
        case 1: // PARTITION_SPEC
          return PARTITION_SPEC;
        case 3: // IF_NOT_EXISTS
          return IF_NOT_EXISTS;
        case 2: // LOCATION
          return LOCATION;
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
  private static final int __IF_NOT_EXISTS_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);
  private _Fields optionals[] = {_Fields.LOCATION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PARTITION_SPEC, new org.apache.thrift.meta_data.FieldMetaData("partition_spec", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPartitionKeyValue.class))));
    tmpMap.put(_Fields.IF_NOT_EXISTS, new org.apache.thrift.meta_data.FieldMetaData("if_not_exists", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.LOCATION, new org.apache.thrift.meta_data.FieldMetaData("location", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAlterTableAddPartitionParams.class, metaDataMap);
  }

  public TAlterTableAddPartitionParams() {
  }

  public TAlterTableAddPartitionParams(
    List<TPartitionKeyValue> partition_spec,
    boolean if_not_exists)
  {
    this();
    this.partition_spec = partition_spec;
    this.if_not_exists = if_not_exists;
    setIf_not_existsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAlterTableAddPartitionParams(TAlterTableAddPartitionParams other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetPartition_spec()) {
      List<TPartitionKeyValue> __this__partition_spec = new ArrayList<TPartitionKeyValue>();
      for (TPartitionKeyValue other_element : other.partition_spec) {
        __this__partition_spec.add(new TPartitionKeyValue(other_element));
      }
      this.partition_spec = __this__partition_spec;
    }
    this.if_not_exists = other.if_not_exists;
    if (other.isSetLocation()) {
      this.location = other.location;
    }
  }

  public TAlterTableAddPartitionParams deepCopy() {
    return new TAlterTableAddPartitionParams(this);
  }

  @Override
  public void clear() {
    this.partition_spec = null;
    setIf_not_existsIsSet(false);
    this.if_not_exists = false;
    this.location = null;
  }

  public int getPartition_specSize() {
    return (this.partition_spec == null) ? 0 : this.partition_spec.size();
  }

  public java.util.Iterator<TPartitionKeyValue> getPartition_specIterator() {
    return (this.partition_spec == null) ? null : this.partition_spec.iterator();
  }

  public void addToPartition_spec(TPartitionKeyValue elem) {
    if (this.partition_spec == null) {
      this.partition_spec = new ArrayList<TPartitionKeyValue>();
    }
    this.partition_spec.add(elem);
  }

  public List<TPartitionKeyValue> getPartition_spec() {
    return this.partition_spec;
  }

  public TAlterTableAddPartitionParams setPartition_spec(List<TPartitionKeyValue> partition_spec) {
    this.partition_spec = partition_spec;
    return this;
  }

  public void unsetPartition_spec() {
    this.partition_spec = null;
  }

  /** Returns true if field partition_spec is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition_spec() {
    return this.partition_spec != null;
  }

  public void setPartition_specIsSet(boolean value) {
    if (!value) {
      this.partition_spec = null;
    }
  }

  public boolean isIf_not_exists() {
    return this.if_not_exists;
  }

  public TAlterTableAddPartitionParams setIf_not_exists(boolean if_not_exists) {
    this.if_not_exists = if_not_exists;
    setIf_not_existsIsSet(true);
    return this;
  }

  public void unsetIf_not_exists() {
    __isset_bit_vector.clear(__IF_NOT_EXISTS_ISSET_ID);
  }

  /** Returns true if field if_not_exists is set (has been assigned a value) and false otherwise */
  public boolean isSetIf_not_exists() {
    return __isset_bit_vector.get(__IF_NOT_EXISTS_ISSET_ID);
  }

  public void setIf_not_existsIsSet(boolean value) {
    __isset_bit_vector.set(__IF_NOT_EXISTS_ISSET_ID, value);
  }

  public String getLocation() {
    return this.location;
  }

  public TAlterTableAddPartitionParams setLocation(String location) {
    this.location = location;
    return this;
  }

  public void unsetLocation() {
    this.location = null;
  }

  /** Returns true if field location is set (has been assigned a value) and false otherwise */
  public boolean isSetLocation() {
    return this.location != null;
  }

  public void setLocationIsSet(boolean value) {
    if (!value) {
      this.location = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PARTITION_SPEC:
      if (value == null) {
        unsetPartition_spec();
      } else {
        setPartition_spec((List<TPartitionKeyValue>)value);
      }
      break;

    case IF_NOT_EXISTS:
      if (value == null) {
        unsetIf_not_exists();
      } else {
        setIf_not_exists((Boolean)value);
      }
      break;

    case LOCATION:
      if (value == null) {
        unsetLocation();
      } else {
        setLocation((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PARTITION_SPEC:
      return getPartition_spec();

    case IF_NOT_EXISTS:
      return Boolean.valueOf(isIf_not_exists());

    case LOCATION:
      return getLocation();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PARTITION_SPEC:
      return isSetPartition_spec();
    case IF_NOT_EXISTS:
      return isSetIf_not_exists();
    case LOCATION:
      return isSetLocation();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TAlterTableAddPartitionParams)
      return this.equals((TAlterTableAddPartitionParams)that);
    return false;
  }

  public boolean equals(TAlterTableAddPartitionParams that) {
    if (that == null)
      return false;

    boolean this_present_partition_spec = true && this.isSetPartition_spec();
    boolean that_present_partition_spec = true && that.isSetPartition_spec();
    if (this_present_partition_spec || that_present_partition_spec) {
      if (!(this_present_partition_spec && that_present_partition_spec))
        return false;
      if (!this.partition_spec.equals(that.partition_spec))
        return false;
    }

    boolean this_present_if_not_exists = true;
    boolean that_present_if_not_exists = true;
    if (this_present_if_not_exists || that_present_if_not_exists) {
      if (!(this_present_if_not_exists && that_present_if_not_exists))
        return false;
      if (this.if_not_exists != that.if_not_exists)
        return false;
    }

    boolean this_present_location = true && this.isSetLocation();
    boolean that_present_location = true && that.isSetLocation();
    if (this_present_location || that_present_location) {
      if (!(this_present_location && that_present_location))
        return false;
      if (!this.location.equals(that.location))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TAlterTableAddPartitionParams other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TAlterTableAddPartitionParams typedOther = (TAlterTableAddPartitionParams)other;

    lastComparison = Boolean.valueOf(isSetPartition_spec()).compareTo(typedOther.isSetPartition_spec());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_spec()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partition_spec, typedOther.partition_spec);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIf_not_exists()).compareTo(typedOther.isSetIf_not_exists());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIf_not_exists()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.if_not_exists, typedOther.if_not_exists);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLocation()).compareTo(typedOther.isSetLocation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLocation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.location, typedOther.location);
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
    StringBuilder sb = new StringBuilder("TAlterTableAddPartitionParams(");
    boolean first = true;

    sb.append("partition_spec:");
    if (this.partition_spec == null) {
      sb.append("null");
    } else {
      sb.append(this.partition_spec);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("if_not_exists:");
    sb.append(this.if_not_exists);
    first = false;
    if (isSetLocation()) {
      if (!first) sb.append(", ");
      sb.append("location:");
      if (this.location == null) {
        sb.append("null");
      } else {
        sb.append(this.location);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (partition_spec == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partition_spec' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'if_not_exists' because it's a primitive and you chose the non-beans generator.
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

  private static class TAlterTableAddPartitionParamsStandardSchemeFactory implements SchemeFactory {
    public TAlterTableAddPartitionParamsStandardScheme getScheme() {
      return new TAlterTableAddPartitionParamsStandardScheme();
    }
  }

  private static class TAlterTableAddPartitionParamsStandardScheme extends StandardScheme<TAlterTableAddPartitionParams> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAlterTableAddPartitionParams struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PARTITION_SPEC
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.partition_spec = new ArrayList<TPartitionKeyValue>(_list32.size);
                for (int _i33 = 0; _i33 < _list32.size; ++_i33)
                {
                  TPartitionKeyValue _elem34; // required
                  _elem34 = new TPartitionKeyValue();
                  _elem34.read(iprot);
                  struct.partition_spec.add(_elem34);
                }
                iprot.readListEnd();
              }
              struct.setPartition_specIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IF_NOT_EXISTS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.if_not_exists = iprot.readBool();
              struct.setIf_not_existsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LOCATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.location = iprot.readString();
              struct.setLocationIsSet(true);
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
      if (!struct.isSetIf_not_exists()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'if_not_exists' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAlterTableAddPartitionParams struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.partition_spec != null) {
        oprot.writeFieldBegin(PARTITION_SPEC_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.partition_spec.size()));
          for (TPartitionKeyValue _iter35 : struct.partition_spec)
          {
            _iter35.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.location != null) {
        if (struct.isSetLocation()) {
          oprot.writeFieldBegin(LOCATION_FIELD_DESC);
          oprot.writeString(struct.location);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(IF_NOT_EXISTS_FIELD_DESC);
      oprot.writeBool(struct.if_not_exists);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAlterTableAddPartitionParamsTupleSchemeFactory implements SchemeFactory {
    public TAlterTableAddPartitionParamsTupleScheme getScheme() {
      return new TAlterTableAddPartitionParamsTupleScheme();
    }
  }

  private static class TAlterTableAddPartitionParamsTupleScheme extends TupleScheme<TAlterTableAddPartitionParams> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAlterTableAddPartitionParams struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.partition_spec.size());
        for (TPartitionKeyValue _iter36 : struct.partition_spec)
        {
          _iter36.write(oprot);
        }
      }
      oprot.writeBool(struct.if_not_exists);
      BitSet optionals = new BitSet();
      if (struct.isSetLocation()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetLocation()) {
        oprot.writeString(struct.location);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAlterTableAddPartitionParams struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.partition_spec = new ArrayList<TPartitionKeyValue>(_list37.size);
        for (int _i38 = 0; _i38 < _list37.size; ++_i38)
        {
          TPartitionKeyValue _elem39; // required
          _elem39 = new TPartitionKeyValue();
          _elem39.read(iprot);
          struct.partition_spec.add(_elem39);
        }
      }
      struct.setPartition_specIsSet(true);
      struct.if_not_exists = iprot.readBool();
      struct.setIf_not_existsIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.location = iprot.readString();
        struct.setLocationIsSet(true);
      }
    }
  }

}

