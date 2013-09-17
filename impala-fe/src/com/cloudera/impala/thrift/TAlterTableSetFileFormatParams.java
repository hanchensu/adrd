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

public class TAlterTableSetFileFormatParams implements org.apache.thrift.TBase<TAlterTableSetFileFormatParams, TAlterTableSetFileFormatParams._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAlterTableSetFileFormatParams");

  private static final org.apache.thrift.protocol.TField FILE_FORMAT_FIELD_DESC = new org.apache.thrift.protocol.TField("file_format", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PARTITION_SPEC_FIELD_DESC = new org.apache.thrift.protocol.TField("partition_spec", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TAlterTableSetFileFormatParamsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TAlterTableSetFileFormatParamsTupleSchemeFactory());
  }

  /**
   * 
   * @see TFileFormat
   */
  public TFileFormat file_format; // required
  public List<TPartitionKeyValue> partition_spec; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see TFileFormat
     */
    FILE_FORMAT((short)1, "file_format"),
    PARTITION_SPEC((short)2, "partition_spec");

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
        case 1: // FILE_FORMAT
          return FILE_FORMAT;
        case 2: // PARTITION_SPEC
          return PARTITION_SPEC;
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
  private _Fields optionals[] = {_Fields.PARTITION_SPEC};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE_FORMAT, new org.apache.thrift.meta_data.FieldMetaData("file_format", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TFileFormat.class)));
    tmpMap.put(_Fields.PARTITION_SPEC, new org.apache.thrift.meta_data.FieldMetaData("partition_spec", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPartitionKeyValue.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAlterTableSetFileFormatParams.class, metaDataMap);
  }

  public TAlterTableSetFileFormatParams() {
  }

  public TAlterTableSetFileFormatParams(
    TFileFormat file_format)
  {
    this();
    this.file_format = file_format;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAlterTableSetFileFormatParams(TAlterTableSetFileFormatParams other) {
    if (other.isSetFile_format()) {
      this.file_format = other.file_format;
    }
    if (other.isSetPartition_spec()) {
      List<TPartitionKeyValue> __this__partition_spec = new ArrayList<TPartitionKeyValue>();
      for (TPartitionKeyValue other_element : other.partition_spec) {
        __this__partition_spec.add(new TPartitionKeyValue(other_element));
      }
      this.partition_spec = __this__partition_spec;
    }
  }

  public TAlterTableSetFileFormatParams deepCopy() {
    return new TAlterTableSetFileFormatParams(this);
  }

  @Override
  public void clear() {
    this.file_format = null;
    this.partition_spec = null;
  }

  /**
   * 
   * @see TFileFormat
   */
  public TFileFormat getFile_format() {
    return this.file_format;
  }

  /**
   * 
   * @see TFileFormat
   */
  public TAlterTableSetFileFormatParams setFile_format(TFileFormat file_format) {
    this.file_format = file_format;
    return this;
  }

  public void unsetFile_format() {
    this.file_format = null;
  }

  /** Returns true if field file_format is set (has been assigned a value) and false otherwise */
  public boolean isSetFile_format() {
    return this.file_format != null;
  }

  public void setFile_formatIsSet(boolean value) {
    if (!value) {
      this.file_format = null;
    }
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

  public TAlterTableSetFileFormatParams setPartition_spec(List<TPartitionKeyValue> partition_spec) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FILE_FORMAT:
      if (value == null) {
        unsetFile_format();
      } else {
        setFile_format((TFileFormat)value);
      }
      break;

    case PARTITION_SPEC:
      if (value == null) {
        unsetPartition_spec();
      } else {
        setPartition_spec((List<TPartitionKeyValue>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_FORMAT:
      return getFile_format();

    case PARTITION_SPEC:
      return getPartition_spec();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FILE_FORMAT:
      return isSetFile_format();
    case PARTITION_SPEC:
      return isSetPartition_spec();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TAlterTableSetFileFormatParams)
      return this.equals((TAlterTableSetFileFormatParams)that);
    return false;
  }

  public boolean equals(TAlterTableSetFileFormatParams that) {
    if (that == null)
      return false;

    boolean this_present_file_format = true && this.isSetFile_format();
    boolean that_present_file_format = true && that.isSetFile_format();
    if (this_present_file_format || that_present_file_format) {
      if (!(this_present_file_format && that_present_file_format))
        return false;
      if (!this.file_format.equals(that.file_format))
        return false;
    }

    boolean this_present_partition_spec = true && this.isSetPartition_spec();
    boolean that_present_partition_spec = true && that.isSetPartition_spec();
    if (this_present_partition_spec || that_present_partition_spec) {
      if (!(this_present_partition_spec && that_present_partition_spec))
        return false;
      if (!this.partition_spec.equals(that.partition_spec))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TAlterTableSetFileFormatParams other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TAlterTableSetFileFormatParams typedOther = (TAlterTableSetFileFormatParams)other;

    lastComparison = Boolean.valueOf(isSetFile_format()).compareTo(typedOther.isSetFile_format());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFile_format()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.file_format, typedOther.file_format);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    StringBuilder sb = new StringBuilder("TAlterTableSetFileFormatParams(");
    boolean first = true;

    sb.append("file_format:");
    if (this.file_format == null) {
      sb.append("null");
    } else {
      sb.append(this.file_format);
    }
    first = false;
    if (isSetPartition_spec()) {
      if (!first) sb.append(", ");
      sb.append("partition_spec:");
      if (this.partition_spec == null) {
        sb.append("null");
      } else {
        sb.append(this.partition_spec);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (file_format == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'file_format' was not present! Struct: " + toString());
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

  private static class TAlterTableSetFileFormatParamsStandardSchemeFactory implements SchemeFactory {
    public TAlterTableSetFileFormatParamsStandardScheme getScheme() {
      return new TAlterTableSetFileFormatParamsStandardScheme();
    }
  }

  private static class TAlterTableSetFileFormatParamsStandardScheme extends StandardScheme<TAlterTableSetFileFormatParams> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAlterTableSetFileFormatParams struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_FORMAT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.file_format = TFileFormat.findByValue(iprot.readI32());
              struct.setFile_formatIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARTITION_SPEC
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list48 = iprot.readListBegin();
                struct.partition_spec = new ArrayList<TPartitionKeyValue>(_list48.size);
                for (int _i49 = 0; _i49 < _list48.size; ++_i49)
                {
                  TPartitionKeyValue _elem50; // required
                  _elem50 = new TPartitionKeyValue();
                  _elem50.read(iprot);
                  struct.partition_spec.add(_elem50);
                }
                iprot.readListEnd();
              }
              struct.setPartition_specIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAlterTableSetFileFormatParams struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.file_format != null) {
        oprot.writeFieldBegin(FILE_FORMAT_FIELD_DESC);
        oprot.writeI32(struct.file_format.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.partition_spec != null) {
        if (struct.isSetPartition_spec()) {
          oprot.writeFieldBegin(PARTITION_SPEC_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.partition_spec.size()));
            for (TPartitionKeyValue _iter51 : struct.partition_spec)
            {
              _iter51.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAlterTableSetFileFormatParamsTupleSchemeFactory implements SchemeFactory {
    public TAlterTableSetFileFormatParamsTupleScheme getScheme() {
      return new TAlterTableSetFileFormatParamsTupleScheme();
    }
  }

  private static class TAlterTableSetFileFormatParamsTupleScheme extends TupleScheme<TAlterTableSetFileFormatParams> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAlterTableSetFileFormatParams struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.file_format.getValue());
      BitSet optionals = new BitSet();
      if (struct.isSetPartition_spec()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPartition_spec()) {
        {
          oprot.writeI32(struct.partition_spec.size());
          for (TPartitionKeyValue _iter52 : struct.partition_spec)
          {
            _iter52.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAlterTableSetFileFormatParams struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.file_format = TFileFormat.findByValue(iprot.readI32());
      struct.setFile_formatIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list53 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.partition_spec = new ArrayList<TPartitionKeyValue>(_list53.size);
          for (int _i54 = 0; _i54 < _list53.size; ++_i54)
          {
            TPartitionKeyValue _elem55; // required
            _elem55 = new TPartitionKeyValue();
            _elem55.read(iprot);
            struct.partition_spec.add(_elem55);
          }
        }
        struct.setPartition_specIsSet(true);
      }
    }
  }

}

