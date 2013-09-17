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

public class TUpdateStateRequest implements org.apache.thrift.TBase<TUpdateStateRequest, TUpdateStateRequest._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TUpdateStateRequest");

  private static final org.apache.thrift.protocol.TField PROTOCOL_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("protocol_version", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField TOPIC_DELTAS_FIELD_DESC = new org.apache.thrift.protocol.TField("topic_deltas", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TUpdateStateRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TUpdateStateRequestTupleSchemeFactory());
  }

  /**
   * 
   * @see StateStoreServiceVersion
   */
  public StateStoreServiceVersion protocol_version; // required
  public Map<String,TTopicDelta> topic_deltas; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see StateStoreServiceVersion
     */
    PROTOCOL_VERSION((short)1, "protocol_version"),
    TOPIC_DELTAS((short)2, "topic_deltas");

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
        case 1: // PROTOCOL_VERSION
          return PROTOCOL_VERSION;
        case 2: // TOPIC_DELTAS
          return TOPIC_DELTAS;
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
    tmpMap.put(_Fields.PROTOCOL_VERSION, new org.apache.thrift.meta_data.FieldMetaData("protocol_version", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StateStoreServiceVersion.class)));
    tmpMap.put(_Fields.TOPIC_DELTAS, new org.apache.thrift.meta_data.FieldMetaData("topic_deltas", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TTopicDelta.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TUpdateStateRequest.class, metaDataMap);
  }

  public TUpdateStateRequest() {
    this.protocol_version = com.cloudera.impala.thrift.StateStoreServiceVersion.V1;

  }

  public TUpdateStateRequest(
    StateStoreServiceVersion protocol_version,
    Map<String,TTopicDelta> topic_deltas)
  {
    this();
    this.protocol_version = protocol_version;
    this.topic_deltas = topic_deltas;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TUpdateStateRequest(TUpdateStateRequest other) {
    if (other.isSetProtocol_version()) {
      this.protocol_version = other.protocol_version;
    }
    if (other.isSetTopic_deltas()) {
      Map<String,TTopicDelta> __this__topic_deltas = new HashMap<String,TTopicDelta>();
      for (Map.Entry<String, TTopicDelta> other_element : other.topic_deltas.entrySet()) {

        String other_element_key = other_element.getKey();
        TTopicDelta other_element_value = other_element.getValue();

        String __this__topic_deltas_copy_key = other_element_key;

        TTopicDelta __this__topic_deltas_copy_value = new TTopicDelta(other_element_value);

        __this__topic_deltas.put(__this__topic_deltas_copy_key, __this__topic_deltas_copy_value);
      }
      this.topic_deltas = __this__topic_deltas;
    }
  }

  public TUpdateStateRequest deepCopy() {
    return new TUpdateStateRequest(this);
  }

  @Override
  public void clear() {
    this.protocol_version = com.cloudera.impala.thrift.StateStoreServiceVersion.V1;

    this.topic_deltas = null;
  }

  /**
   * 
   * @see StateStoreServiceVersion
   */
  public StateStoreServiceVersion getProtocol_version() {
    return this.protocol_version;
  }

  /**
   * 
   * @see StateStoreServiceVersion
   */
  public TUpdateStateRequest setProtocol_version(StateStoreServiceVersion protocol_version) {
    this.protocol_version = protocol_version;
    return this;
  }

  public void unsetProtocol_version() {
    this.protocol_version = null;
  }

  /** Returns true if field protocol_version is set (has been assigned a value) and false otherwise */
  public boolean isSetProtocol_version() {
    return this.protocol_version != null;
  }

  public void setProtocol_versionIsSet(boolean value) {
    if (!value) {
      this.protocol_version = null;
    }
  }

  public int getTopic_deltasSize() {
    return (this.topic_deltas == null) ? 0 : this.topic_deltas.size();
  }

  public void putToTopic_deltas(String key, TTopicDelta val) {
    if (this.topic_deltas == null) {
      this.topic_deltas = new HashMap<String,TTopicDelta>();
    }
    this.topic_deltas.put(key, val);
  }

  public Map<String,TTopicDelta> getTopic_deltas() {
    return this.topic_deltas;
  }

  public TUpdateStateRequest setTopic_deltas(Map<String,TTopicDelta> topic_deltas) {
    this.topic_deltas = topic_deltas;
    return this;
  }

  public void unsetTopic_deltas() {
    this.topic_deltas = null;
  }

  /** Returns true if field topic_deltas is set (has been assigned a value) and false otherwise */
  public boolean isSetTopic_deltas() {
    return this.topic_deltas != null;
  }

  public void setTopic_deltasIsSet(boolean value) {
    if (!value) {
      this.topic_deltas = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PROTOCOL_VERSION:
      if (value == null) {
        unsetProtocol_version();
      } else {
        setProtocol_version((StateStoreServiceVersion)value);
      }
      break;

    case TOPIC_DELTAS:
      if (value == null) {
        unsetTopic_deltas();
      } else {
        setTopic_deltas((Map<String,TTopicDelta>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PROTOCOL_VERSION:
      return getProtocol_version();

    case TOPIC_DELTAS:
      return getTopic_deltas();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PROTOCOL_VERSION:
      return isSetProtocol_version();
    case TOPIC_DELTAS:
      return isSetTopic_deltas();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TUpdateStateRequest)
      return this.equals((TUpdateStateRequest)that);
    return false;
  }

  public boolean equals(TUpdateStateRequest that) {
    if (that == null)
      return false;

    boolean this_present_protocol_version = true && this.isSetProtocol_version();
    boolean that_present_protocol_version = true && that.isSetProtocol_version();
    if (this_present_protocol_version || that_present_protocol_version) {
      if (!(this_present_protocol_version && that_present_protocol_version))
        return false;
      if (!this.protocol_version.equals(that.protocol_version))
        return false;
    }

    boolean this_present_topic_deltas = true && this.isSetTopic_deltas();
    boolean that_present_topic_deltas = true && that.isSetTopic_deltas();
    if (this_present_topic_deltas || that_present_topic_deltas) {
      if (!(this_present_topic_deltas && that_present_topic_deltas))
        return false;
      if (!this.topic_deltas.equals(that.topic_deltas))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TUpdateStateRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TUpdateStateRequest typedOther = (TUpdateStateRequest)other;

    lastComparison = Boolean.valueOf(isSetProtocol_version()).compareTo(typedOther.isSetProtocol_version());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProtocol_version()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.protocol_version, typedOther.protocol_version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopic_deltas()).compareTo(typedOther.isSetTopic_deltas());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopic_deltas()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topic_deltas, typedOther.topic_deltas);
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
    StringBuilder sb = new StringBuilder("TUpdateStateRequest(");
    boolean first = true;

    sb.append("protocol_version:");
    if (this.protocol_version == null) {
      sb.append("null");
    } else {
      sb.append(this.protocol_version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("topic_deltas:");
    if (this.topic_deltas == null) {
      sb.append("null");
    } else {
      sb.append(this.topic_deltas);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (protocol_version == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'protocol_version' was not present! Struct: " + toString());
    }
    if (topic_deltas == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'topic_deltas' was not present! Struct: " + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TUpdateStateRequestStandardSchemeFactory implements SchemeFactory {
    public TUpdateStateRequestStandardScheme getScheme() {
      return new TUpdateStateRequestStandardScheme();
    }
  }

  private static class TUpdateStateRequestStandardScheme extends StandardScheme<TUpdateStateRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TUpdateStateRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PROTOCOL_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.protocol_version = StateStoreServiceVersion.findByValue(iprot.readI32());
              struct.setProtocol_versionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOPIC_DELTAS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map40 = iprot.readMapBegin();
                struct.topic_deltas = new HashMap<String,TTopicDelta>(2*_map40.size);
                for (int _i41 = 0; _i41 < _map40.size; ++_i41)
                {
                  String _key42; // required
                  TTopicDelta _val43; // required
                  _key42 = iprot.readString();
                  _val43 = new TTopicDelta();
                  _val43.read(iprot);
                  struct.topic_deltas.put(_key42, _val43);
                }
                iprot.readMapEnd();
              }
              struct.setTopic_deltasIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TUpdateStateRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.protocol_version != null) {
        oprot.writeFieldBegin(PROTOCOL_VERSION_FIELD_DESC);
        oprot.writeI32(struct.protocol_version.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.topic_deltas != null) {
        oprot.writeFieldBegin(TOPIC_DELTAS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.topic_deltas.size()));
          for (Map.Entry<String, TTopicDelta> _iter44 : struct.topic_deltas.entrySet())
          {
            oprot.writeString(_iter44.getKey());
            _iter44.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TUpdateStateRequestTupleSchemeFactory implements SchemeFactory {
    public TUpdateStateRequestTupleScheme getScheme() {
      return new TUpdateStateRequestTupleScheme();
    }
  }

  private static class TUpdateStateRequestTupleScheme extends TupleScheme<TUpdateStateRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TUpdateStateRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.protocol_version.getValue());
      {
        oprot.writeI32(struct.topic_deltas.size());
        for (Map.Entry<String, TTopicDelta> _iter45 : struct.topic_deltas.entrySet())
        {
          oprot.writeString(_iter45.getKey());
          _iter45.getValue().write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TUpdateStateRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.protocol_version = StateStoreServiceVersion.findByValue(iprot.readI32());
      struct.setProtocol_versionIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map46 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.topic_deltas = new HashMap<String,TTopicDelta>(2*_map46.size);
        for (int _i47 = 0; _i47 < _map46.size; ++_i47)
        {
          String _key48; // required
          TTopicDelta _val49; // required
          _key48 = iprot.readString();
          _val49 = new TTopicDelta();
          _val49.read(iprot);
          struct.topic_deltas.put(_key48, _val49);
        }
      }
      struct.setTopic_deltasIsSet(true);
    }
  }

}

