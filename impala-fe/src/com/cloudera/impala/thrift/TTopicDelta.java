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

public class TTopicDelta implements org.apache.thrift.TBase<TTopicDelta, TTopicDelta._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTopicDelta");

  private static final org.apache.thrift.protocol.TField TOPIC_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("topic_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TOPIC_ENTRIES_FIELD_DESC = new org.apache.thrift.protocol.TField("topic_entries", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField TOPIC_DELETIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("topic_deletions", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField IS_DELTA_FIELD_DESC = new org.apache.thrift.protocol.TField("is_delta", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TTopicDeltaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TTopicDeltaTupleSchemeFactory());
  }

  public String topic_name; // required
  public List<TTopicItem> topic_entries; // required
  public List<String> topic_deletions; // required
  public boolean is_delta; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOPIC_NAME((short)1, "topic_name"),
    TOPIC_ENTRIES((short)2, "topic_entries"),
    TOPIC_DELETIONS((short)3, "topic_deletions"),
    IS_DELTA((short)4, "is_delta");

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
        case 1: // TOPIC_NAME
          return TOPIC_NAME;
        case 2: // TOPIC_ENTRIES
          return TOPIC_ENTRIES;
        case 3: // TOPIC_DELETIONS
          return TOPIC_DELETIONS;
        case 4: // IS_DELTA
          return IS_DELTA;
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
  private static final int __IS_DELTA_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOPIC_NAME, new org.apache.thrift.meta_data.FieldMetaData("topic_name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOPIC_ENTRIES, new org.apache.thrift.meta_data.FieldMetaData("topic_entries", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TTopicItem.class))));
    tmpMap.put(_Fields.TOPIC_DELETIONS, new org.apache.thrift.meta_data.FieldMetaData("topic_deletions", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.IS_DELTA, new org.apache.thrift.meta_data.FieldMetaData("is_delta", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTopicDelta.class, metaDataMap);
  }

  public TTopicDelta() {
  }

  public TTopicDelta(
    String topic_name,
    List<TTopicItem> topic_entries,
    List<String> topic_deletions,
    boolean is_delta)
  {
    this();
    this.topic_name = topic_name;
    this.topic_entries = topic_entries;
    this.topic_deletions = topic_deletions;
    this.is_delta = is_delta;
    setIs_deltaIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTopicDelta(TTopicDelta other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTopic_name()) {
      this.topic_name = other.topic_name;
    }
    if (other.isSetTopic_entries()) {
      List<TTopicItem> __this__topic_entries = new ArrayList<TTopicItem>();
      for (TTopicItem other_element : other.topic_entries) {
        __this__topic_entries.add(new TTopicItem(other_element));
      }
      this.topic_entries = __this__topic_entries;
    }
    if (other.isSetTopic_deletions()) {
      List<String> __this__topic_deletions = new ArrayList<String>();
      for (String other_element : other.topic_deletions) {
        __this__topic_deletions.add(other_element);
      }
      this.topic_deletions = __this__topic_deletions;
    }
    this.is_delta = other.is_delta;
  }

  public TTopicDelta deepCopy() {
    return new TTopicDelta(this);
  }

  @Override
  public void clear() {
    this.topic_name = null;
    this.topic_entries = null;
    this.topic_deletions = null;
    setIs_deltaIsSet(false);
    this.is_delta = false;
  }

  public String getTopic_name() {
    return this.topic_name;
  }

  public TTopicDelta setTopic_name(String topic_name) {
    this.topic_name = topic_name;
    return this;
  }

  public void unsetTopic_name() {
    this.topic_name = null;
  }

  /** Returns true if field topic_name is set (has been assigned a value) and false otherwise */
  public boolean isSetTopic_name() {
    return this.topic_name != null;
  }

  public void setTopic_nameIsSet(boolean value) {
    if (!value) {
      this.topic_name = null;
    }
  }

  public int getTopic_entriesSize() {
    return (this.topic_entries == null) ? 0 : this.topic_entries.size();
  }

  public java.util.Iterator<TTopicItem> getTopic_entriesIterator() {
    return (this.topic_entries == null) ? null : this.topic_entries.iterator();
  }

  public void addToTopic_entries(TTopicItem elem) {
    if (this.topic_entries == null) {
      this.topic_entries = new ArrayList<TTopicItem>();
    }
    this.topic_entries.add(elem);
  }

  public List<TTopicItem> getTopic_entries() {
    return this.topic_entries;
  }

  public TTopicDelta setTopic_entries(List<TTopicItem> topic_entries) {
    this.topic_entries = topic_entries;
    return this;
  }

  public void unsetTopic_entries() {
    this.topic_entries = null;
  }

  /** Returns true if field topic_entries is set (has been assigned a value) and false otherwise */
  public boolean isSetTopic_entries() {
    return this.topic_entries != null;
  }

  public void setTopic_entriesIsSet(boolean value) {
    if (!value) {
      this.topic_entries = null;
    }
  }

  public int getTopic_deletionsSize() {
    return (this.topic_deletions == null) ? 0 : this.topic_deletions.size();
  }

  public java.util.Iterator<String> getTopic_deletionsIterator() {
    return (this.topic_deletions == null) ? null : this.topic_deletions.iterator();
  }

  public void addToTopic_deletions(String elem) {
    if (this.topic_deletions == null) {
      this.topic_deletions = new ArrayList<String>();
    }
    this.topic_deletions.add(elem);
  }

  public List<String> getTopic_deletions() {
    return this.topic_deletions;
  }

  public TTopicDelta setTopic_deletions(List<String> topic_deletions) {
    this.topic_deletions = topic_deletions;
    return this;
  }

  public void unsetTopic_deletions() {
    this.topic_deletions = null;
  }

  /** Returns true if field topic_deletions is set (has been assigned a value) and false otherwise */
  public boolean isSetTopic_deletions() {
    return this.topic_deletions != null;
  }

  public void setTopic_deletionsIsSet(boolean value) {
    if (!value) {
      this.topic_deletions = null;
    }
  }

  public boolean isIs_delta() {
    return this.is_delta;
  }

  public TTopicDelta setIs_delta(boolean is_delta) {
    this.is_delta = is_delta;
    setIs_deltaIsSet(true);
    return this;
  }

  public void unsetIs_delta() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_DELTA_ISSET_ID);
  }

  /** Returns true if field is_delta is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_delta() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_DELTA_ISSET_ID);
  }

  public void setIs_deltaIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_DELTA_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOPIC_NAME:
      if (value == null) {
        unsetTopic_name();
      } else {
        setTopic_name((String)value);
      }
      break;

    case TOPIC_ENTRIES:
      if (value == null) {
        unsetTopic_entries();
      } else {
        setTopic_entries((List<TTopicItem>)value);
      }
      break;

    case TOPIC_DELETIONS:
      if (value == null) {
        unsetTopic_deletions();
      } else {
        setTopic_deletions((List<String>)value);
      }
      break;

    case IS_DELTA:
      if (value == null) {
        unsetIs_delta();
      } else {
        setIs_delta((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOPIC_NAME:
      return getTopic_name();

    case TOPIC_ENTRIES:
      return getTopic_entries();

    case TOPIC_DELETIONS:
      return getTopic_deletions();

    case IS_DELTA:
      return Boolean.valueOf(isIs_delta());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOPIC_NAME:
      return isSetTopic_name();
    case TOPIC_ENTRIES:
      return isSetTopic_entries();
    case TOPIC_DELETIONS:
      return isSetTopic_deletions();
    case IS_DELTA:
      return isSetIs_delta();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TTopicDelta)
      return this.equals((TTopicDelta)that);
    return false;
  }

  public boolean equals(TTopicDelta that) {
    if (that == null)
      return false;

    boolean this_present_topic_name = true && this.isSetTopic_name();
    boolean that_present_topic_name = true && that.isSetTopic_name();
    if (this_present_topic_name || that_present_topic_name) {
      if (!(this_present_topic_name && that_present_topic_name))
        return false;
      if (!this.topic_name.equals(that.topic_name))
        return false;
    }

    boolean this_present_topic_entries = true && this.isSetTopic_entries();
    boolean that_present_topic_entries = true && that.isSetTopic_entries();
    if (this_present_topic_entries || that_present_topic_entries) {
      if (!(this_present_topic_entries && that_present_topic_entries))
        return false;
      if (!this.topic_entries.equals(that.topic_entries))
        return false;
    }

    boolean this_present_topic_deletions = true && this.isSetTopic_deletions();
    boolean that_present_topic_deletions = true && that.isSetTopic_deletions();
    if (this_present_topic_deletions || that_present_topic_deletions) {
      if (!(this_present_topic_deletions && that_present_topic_deletions))
        return false;
      if (!this.topic_deletions.equals(that.topic_deletions))
        return false;
    }

    boolean this_present_is_delta = true;
    boolean that_present_is_delta = true;
    if (this_present_is_delta || that_present_is_delta) {
      if (!(this_present_is_delta && that_present_is_delta))
        return false;
      if (this.is_delta != that.is_delta)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TTopicDelta other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TTopicDelta typedOther = (TTopicDelta)other;

    lastComparison = Boolean.valueOf(isSetTopic_name()).compareTo(typedOther.isSetTopic_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopic_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topic_name, typedOther.topic_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopic_entries()).compareTo(typedOther.isSetTopic_entries());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopic_entries()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topic_entries, typedOther.topic_entries);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopic_deletions()).compareTo(typedOther.isSetTopic_deletions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopic_deletions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topic_deletions, typedOther.topic_deletions);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIs_delta()).compareTo(typedOther.isSetIs_delta());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_delta()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_delta, typedOther.is_delta);
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
    StringBuilder sb = new StringBuilder("TTopicDelta(");
    boolean first = true;

    sb.append("topic_name:");
    if (this.topic_name == null) {
      sb.append("null");
    } else {
      sb.append(this.topic_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("topic_entries:");
    if (this.topic_entries == null) {
      sb.append("null");
    } else {
      sb.append(this.topic_entries);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("topic_deletions:");
    if (this.topic_deletions == null) {
      sb.append("null");
    } else {
      sb.append(this.topic_deletions);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_delta:");
    sb.append(this.is_delta);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (topic_name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'topic_name' was not present! Struct: " + toString());
    }
    if (topic_entries == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'topic_entries' was not present! Struct: " + toString());
    }
    if (topic_deletions == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'topic_deletions' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'is_delta' because it's a primitive and you chose the non-beans generator.
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

  private static class TTopicDeltaStandardSchemeFactory implements SchemeFactory {
    public TTopicDeltaStandardScheme getScheme() {
      return new TTopicDeltaStandardScheme();
    }
  }

  private static class TTopicDeltaStandardScheme extends StandardScheme<TTopicDelta> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTopicDelta struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOPIC_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.topic_name = iprot.readString();
              struct.setTopic_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOPIC_ENTRIES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.topic_entries = new ArrayList<TTopicItem>(_list16.size);
                for (int _i17 = 0; _i17 < _list16.size; ++_i17)
                {
                  TTopicItem _elem18; // required
                  _elem18 = new TTopicItem();
                  _elem18.read(iprot);
                  struct.topic_entries.add(_elem18);
                }
                iprot.readListEnd();
              }
              struct.setTopic_entriesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOPIC_DELETIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list19 = iprot.readListBegin();
                struct.topic_deletions = new ArrayList<String>(_list19.size);
                for (int _i20 = 0; _i20 < _list19.size; ++_i20)
                {
                  String _elem21; // required
                  _elem21 = iprot.readString();
                  struct.topic_deletions.add(_elem21);
                }
                iprot.readListEnd();
              }
              struct.setTopic_deletionsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IS_DELTA
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_delta = iprot.readBool();
              struct.setIs_deltaIsSet(true);
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
      if (!struct.isSetIs_delta()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'is_delta' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTopicDelta struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.topic_name != null) {
        oprot.writeFieldBegin(TOPIC_NAME_FIELD_DESC);
        oprot.writeString(struct.topic_name);
        oprot.writeFieldEnd();
      }
      if (struct.topic_entries != null) {
        oprot.writeFieldBegin(TOPIC_ENTRIES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.topic_entries.size()));
          for (TTopicItem _iter22 : struct.topic_entries)
          {
            _iter22.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.topic_deletions != null) {
        oprot.writeFieldBegin(TOPIC_DELETIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.topic_deletions.size()));
          for (String _iter23 : struct.topic_deletions)
          {
            oprot.writeString(_iter23);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_DELTA_FIELD_DESC);
      oprot.writeBool(struct.is_delta);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTopicDeltaTupleSchemeFactory implements SchemeFactory {
    public TTopicDeltaTupleScheme getScheme() {
      return new TTopicDeltaTupleScheme();
    }
  }

  private static class TTopicDeltaTupleScheme extends TupleScheme<TTopicDelta> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTopicDelta struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.topic_name);
      {
        oprot.writeI32(struct.topic_entries.size());
        for (TTopicItem _iter24 : struct.topic_entries)
        {
          _iter24.write(oprot);
        }
      }
      {
        oprot.writeI32(struct.topic_deletions.size());
        for (String _iter25 : struct.topic_deletions)
        {
          oprot.writeString(_iter25);
        }
      }
      oprot.writeBool(struct.is_delta);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTopicDelta struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.topic_name = iprot.readString();
      struct.setTopic_nameIsSet(true);
      {
        org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.topic_entries = new ArrayList<TTopicItem>(_list26.size);
        for (int _i27 = 0; _i27 < _list26.size; ++_i27)
        {
          TTopicItem _elem28; // required
          _elem28 = new TTopicItem();
          _elem28.read(iprot);
          struct.topic_entries.add(_elem28);
        }
      }
      struct.setTopic_entriesIsSet(true);
      {
        org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.topic_deletions = new ArrayList<String>(_list29.size);
        for (int _i30 = 0; _i30 < _list29.size; ++_i30)
        {
          String _elem31; // required
          _elem31 = iprot.readString();
          struct.topic_deletions.add(_elem31);
        }
      }
      struct.setTopic_deletionsIsSet(true);
      struct.is_delta = iprot.readBool();
      struct.setIs_deltaIsSet(true);
    }
  }

}

