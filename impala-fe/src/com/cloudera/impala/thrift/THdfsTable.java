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

public class THdfsTable implements org.apache.thrift.TBase<THdfsTable, THdfsTable._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("THdfsTable");

  private static final org.apache.thrift.protocol.TField HDFS_BASE_DIR_FIELD_DESC = new org.apache.thrift.protocol.TField("hdfsBaseDir", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField COL_NAMES_FIELD_DESC = new org.apache.thrift.protocol.TField("colNames", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField NULL_PARTITION_KEY_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("nullPartitionKeyValue", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField NULL_COLUMN_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("nullColumnValue", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField AVRO_SCHEMA_FIELD_DESC = new org.apache.thrift.protocol.TField("avroSchema", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField PARTITIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("partitions", org.apache.thrift.protocol.TType.MAP, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new THdfsTableStandardSchemeFactory());
    schemes.put(TupleScheme.class, new THdfsTableTupleSchemeFactory());
  }

  public String hdfsBaseDir; // required
  public List<String> colNames; // required
  public String nullPartitionKeyValue; // required
  public String nullColumnValue; // required
  public String avroSchema; // optional
  public Map<Long,THdfsPartition> partitions; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HDFS_BASE_DIR((short)1, "hdfsBaseDir"),
    COL_NAMES((short)2, "colNames"),
    NULL_PARTITION_KEY_VALUE((short)3, "nullPartitionKeyValue"),
    NULL_COLUMN_VALUE((short)5, "nullColumnValue"),
    AVRO_SCHEMA((short)6, "avroSchema"),
    PARTITIONS((short)4, "partitions");

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
        case 1: // HDFS_BASE_DIR
          return HDFS_BASE_DIR;
        case 2: // COL_NAMES
          return COL_NAMES;
        case 3: // NULL_PARTITION_KEY_VALUE
          return NULL_PARTITION_KEY_VALUE;
        case 5: // NULL_COLUMN_VALUE
          return NULL_COLUMN_VALUE;
        case 6: // AVRO_SCHEMA
          return AVRO_SCHEMA;
        case 4: // PARTITIONS
          return PARTITIONS;
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
  private _Fields optionals[] = {_Fields.AVRO_SCHEMA};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HDFS_BASE_DIR, new org.apache.thrift.meta_data.FieldMetaData("hdfsBaseDir", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COL_NAMES, new org.apache.thrift.meta_data.FieldMetaData("colNames", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.NULL_PARTITION_KEY_VALUE, new org.apache.thrift.meta_data.FieldMetaData("nullPartitionKeyValue", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NULL_COLUMN_VALUE, new org.apache.thrift.meta_data.FieldMetaData("nullColumnValue", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AVRO_SCHEMA, new org.apache.thrift.meta_data.FieldMetaData("avroSchema", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARTITIONS, new org.apache.thrift.meta_data.FieldMetaData("partitions", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, THdfsPartition.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(THdfsTable.class, metaDataMap);
  }

  public THdfsTable() {
  }

  public THdfsTable(
    String hdfsBaseDir,
    List<String> colNames,
    String nullPartitionKeyValue,
    String nullColumnValue,
    Map<Long,THdfsPartition> partitions)
  {
    this();
    this.hdfsBaseDir = hdfsBaseDir;
    this.colNames = colNames;
    this.nullPartitionKeyValue = nullPartitionKeyValue;
    this.nullColumnValue = nullColumnValue;
    this.partitions = partitions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public THdfsTable(THdfsTable other) {
    if (other.isSetHdfsBaseDir()) {
      this.hdfsBaseDir = other.hdfsBaseDir;
    }
    if (other.isSetColNames()) {
      List<String> __this__colNames = new ArrayList<String>();
      for (String other_element : other.colNames) {
        __this__colNames.add(other_element);
      }
      this.colNames = __this__colNames;
    }
    if (other.isSetNullPartitionKeyValue()) {
      this.nullPartitionKeyValue = other.nullPartitionKeyValue;
    }
    if (other.isSetNullColumnValue()) {
      this.nullColumnValue = other.nullColumnValue;
    }
    if (other.isSetAvroSchema()) {
      this.avroSchema = other.avroSchema;
    }
    if (other.isSetPartitions()) {
      Map<Long,THdfsPartition> __this__partitions = new HashMap<Long,THdfsPartition>();
      for (Map.Entry<Long, THdfsPartition> other_element : other.partitions.entrySet()) {

        Long other_element_key = other_element.getKey();
        THdfsPartition other_element_value = other_element.getValue();

        Long __this__partitions_copy_key = other_element_key;

        THdfsPartition __this__partitions_copy_value = new THdfsPartition(other_element_value);

        __this__partitions.put(__this__partitions_copy_key, __this__partitions_copy_value);
      }
      this.partitions = __this__partitions;
    }
  }

  public THdfsTable deepCopy() {
    return new THdfsTable(this);
  }

  @Override
  public void clear() {
    this.hdfsBaseDir = null;
    this.colNames = null;
    this.nullPartitionKeyValue = null;
    this.nullColumnValue = null;
    this.avroSchema = null;
    this.partitions = null;
  }

  public String getHdfsBaseDir() {
    return this.hdfsBaseDir;
  }

  public THdfsTable setHdfsBaseDir(String hdfsBaseDir) {
    this.hdfsBaseDir = hdfsBaseDir;
    return this;
  }

  public void unsetHdfsBaseDir() {
    this.hdfsBaseDir = null;
  }

  /** Returns true if field hdfsBaseDir is set (has been assigned a value) and false otherwise */
  public boolean isSetHdfsBaseDir() {
    return this.hdfsBaseDir != null;
  }

  public void setHdfsBaseDirIsSet(boolean value) {
    if (!value) {
      this.hdfsBaseDir = null;
    }
  }

  public int getColNamesSize() {
    return (this.colNames == null) ? 0 : this.colNames.size();
  }

  public java.util.Iterator<String> getColNamesIterator() {
    return (this.colNames == null) ? null : this.colNames.iterator();
  }

  public void addToColNames(String elem) {
    if (this.colNames == null) {
      this.colNames = new ArrayList<String>();
    }
    this.colNames.add(elem);
  }

  public List<String> getColNames() {
    return this.colNames;
  }

  public THdfsTable setColNames(List<String> colNames) {
    this.colNames = colNames;
    return this;
  }

  public void unsetColNames() {
    this.colNames = null;
  }

  /** Returns true if field colNames is set (has been assigned a value) and false otherwise */
  public boolean isSetColNames() {
    return this.colNames != null;
  }

  public void setColNamesIsSet(boolean value) {
    if (!value) {
      this.colNames = null;
    }
  }

  public String getNullPartitionKeyValue() {
    return this.nullPartitionKeyValue;
  }

  public THdfsTable setNullPartitionKeyValue(String nullPartitionKeyValue) {
    this.nullPartitionKeyValue = nullPartitionKeyValue;
    return this;
  }

  public void unsetNullPartitionKeyValue() {
    this.nullPartitionKeyValue = null;
  }

  /** Returns true if field nullPartitionKeyValue is set (has been assigned a value) and false otherwise */
  public boolean isSetNullPartitionKeyValue() {
    return this.nullPartitionKeyValue != null;
  }

  public void setNullPartitionKeyValueIsSet(boolean value) {
    if (!value) {
      this.nullPartitionKeyValue = null;
    }
  }

  public String getNullColumnValue() {
    return this.nullColumnValue;
  }

  public THdfsTable setNullColumnValue(String nullColumnValue) {
    this.nullColumnValue = nullColumnValue;
    return this;
  }

  public void unsetNullColumnValue() {
    this.nullColumnValue = null;
  }

  /** Returns true if field nullColumnValue is set (has been assigned a value) and false otherwise */
  public boolean isSetNullColumnValue() {
    return this.nullColumnValue != null;
  }

  public void setNullColumnValueIsSet(boolean value) {
    if (!value) {
      this.nullColumnValue = null;
    }
  }

  public String getAvroSchema() {
    return this.avroSchema;
  }

  public THdfsTable setAvroSchema(String avroSchema) {
    this.avroSchema = avroSchema;
    return this;
  }

  public void unsetAvroSchema() {
    this.avroSchema = null;
  }

  /** Returns true if field avroSchema is set (has been assigned a value) and false otherwise */
  public boolean isSetAvroSchema() {
    return this.avroSchema != null;
  }

  public void setAvroSchemaIsSet(boolean value) {
    if (!value) {
      this.avroSchema = null;
    }
  }

  public int getPartitionsSize() {
    return (this.partitions == null) ? 0 : this.partitions.size();
  }

  public void putToPartitions(long key, THdfsPartition val) {
    if (this.partitions == null) {
      this.partitions = new HashMap<Long,THdfsPartition>();
    }
    this.partitions.put(key, val);
  }

  public Map<Long,THdfsPartition> getPartitions() {
    return this.partitions;
  }

  public THdfsTable setPartitions(Map<Long,THdfsPartition> partitions) {
    this.partitions = partitions;
    return this;
  }

  public void unsetPartitions() {
    this.partitions = null;
  }

  /** Returns true if field partitions is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitions() {
    return this.partitions != null;
  }

  public void setPartitionsIsSet(boolean value) {
    if (!value) {
      this.partitions = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case HDFS_BASE_DIR:
      if (value == null) {
        unsetHdfsBaseDir();
      } else {
        setHdfsBaseDir((String)value);
      }
      break;

    case COL_NAMES:
      if (value == null) {
        unsetColNames();
      } else {
        setColNames((List<String>)value);
      }
      break;

    case NULL_PARTITION_KEY_VALUE:
      if (value == null) {
        unsetNullPartitionKeyValue();
      } else {
        setNullPartitionKeyValue((String)value);
      }
      break;

    case NULL_COLUMN_VALUE:
      if (value == null) {
        unsetNullColumnValue();
      } else {
        setNullColumnValue((String)value);
      }
      break;

    case AVRO_SCHEMA:
      if (value == null) {
        unsetAvroSchema();
      } else {
        setAvroSchema((String)value);
      }
      break;

    case PARTITIONS:
      if (value == null) {
        unsetPartitions();
      } else {
        setPartitions((Map<Long,THdfsPartition>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case HDFS_BASE_DIR:
      return getHdfsBaseDir();

    case COL_NAMES:
      return getColNames();

    case NULL_PARTITION_KEY_VALUE:
      return getNullPartitionKeyValue();

    case NULL_COLUMN_VALUE:
      return getNullColumnValue();

    case AVRO_SCHEMA:
      return getAvroSchema();

    case PARTITIONS:
      return getPartitions();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case HDFS_BASE_DIR:
      return isSetHdfsBaseDir();
    case COL_NAMES:
      return isSetColNames();
    case NULL_PARTITION_KEY_VALUE:
      return isSetNullPartitionKeyValue();
    case NULL_COLUMN_VALUE:
      return isSetNullColumnValue();
    case AVRO_SCHEMA:
      return isSetAvroSchema();
    case PARTITIONS:
      return isSetPartitions();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof THdfsTable)
      return this.equals((THdfsTable)that);
    return false;
  }

  public boolean equals(THdfsTable that) {
    if (that == null)
      return false;

    boolean this_present_hdfsBaseDir = true && this.isSetHdfsBaseDir();
    boolean that_present_hdfsBaseDir = true && that.isSetHdfsBaseDir();
    if (this_present_hdfsBaseDir || that_present_hdfsBaseDir) {
      if (!(this_present_hdfsBaseDir && that_present_hdfsBaseDir))
        return false;
      if (!this.hdfsBaseDir.equals(that.hdfsBaseDir))
        return false;
    }

    boolean this_present_colNames = true && this.isSetColNames();
    boolean that_present_colNames = true && that.isSetColNames();
    if (this_present_colNames || that_present_colNames) {
      if (!(this_present_colNames && that_present_colNames))
        return false;
      if (!this.colNames.equals(that.colNames))
        return false;
    }

    boolean this_present_nullPartitionKeyValue = true && this.isSetNullPartitionKeyValue();
    boolean that_present_nullPartitionKeyValue = true && that.isSetNullPartitionKeyValue();
    if (this_present_nullPartitionKeyValue || that_present_nullPartitionKeyValue) {
      if (!(this_present_nullPartitionKeyValue && that_present_nullPartitionKeyValue))
        return false;
      if (!this.nullPartitionKeyValue.equals(that.nullPartitionKeyValue))
        return false;
    }

    boolean this_present_nullColumnValue = true && this.isSetNullColumnValue();
    boolean that_present_nullColumnValue = true && that.isSetNullColumnValue();
    if (this_present_nullColumnValue || that_present_nullColumnValue) {
      if (!(this_present_nullColumnValue && that_present_nullColumnValue))
        return false;
      if (!this.nullColumnValue.equals(that.nullColumnValue))
        return false;
    }

    boolean this_present_avroSchema = true && this.isSetAvroSchema();
    boolean that_present_avroSchema = true && that.isSetAvroSchema();
    if (this_present_avroSchema || that_present_avroSchema) {
      if (!(this_present_avroSchema && that_present_avroSchema))
        return false;
      if (!this.avroSchema.equals(that.avroSchema))
        return false;
    }

    boolean this_present_partitions = true && this.isSetPartitions();
    boolean that_present_partitions = true && that.isSetPartitions();
    if (this_present_partitions || that_present_partitions) {
      if (!(this_present_partitions && that_present_partitions))
        return false;
      if (!this.partitions.equals(that.partitions))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(THdfsTable other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    THdfsTable typedOther = (THdfsTable)other;

    lastComparison = Boolean.valueOf(isSetHdfsBaseDir()).compareTo(typedOther.isSetHdfsBaseDir());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHdfsBaseDir()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hdfsBaseDir, typedOther.hdfsBaseDir);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetColNames()).compareTo(typedOther.isSetColNames());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColNames()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.colNames, typedOther.colNames);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNullPartitionKeyValue()).compareTo(typedOther.isSetNullPartitionKeyValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNullPartitionKeyValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nullPartitionKeyValue, typedOther.nullPartitionKeyValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNullColumnValue()).compareTo(typedOther.isSetNullColumnValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNullColumnValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nullColumnValue, typedOther.nullColumnValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAvroSchema()).compareTo(typedOther.isSetAvroSchema());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAvroSchema()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.avroSchema, typedOther.avroSchema);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPartitions()).compareTo(typedOther.isSetPartitions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitions, typedOther.partitions);
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
    StringBuilder sb = new StringBuilder("THdfsTable(");
    boolean first = true;

    sb.append("hdfsBaseDir:");
    if (this.hdfsBaseDir == null) {
      sb.append("null");
    } else {
      sb.append(this.hdfsBaseDir);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("colNames:");
    if (this.colNames == null) {
      sb.append("null");
    } else {
      sb.append(this.colNames);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nullPartitionKeyValue:");
    if (this.nullPartitionKeyValue == null) {
      sb.append("null");
    } else {
      sb.append(this.nullPartitionKeyValue);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nullColumnValue:");
    if (this.nullColumnValue == null) {
      sb.append("null");
    } else {
      sb.append(this.nullColumnValue);
    }
    first = false;
    if (isSetAvroSchema()) {
      if (!first) sb.append(", ");
      sb.append("avroSchema:");
      if (this.avroSchema == null) {
        sb.append("null");
      } else {
        sb.append(this.avroSchema);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("partitions:");
    if (this.partitions == null) {
      sb.append("null");
    } else {
      sb.append(this.partitions);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (hdfsBaseDir == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'hdfsBaseDir' was not present! Struct: " + toString());
    }
    if (colNames == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'colNames' was not present! Struct: " + toString());
    }
    if (nullPartitionKeyValue == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'nullPartitionKeyValue' was not present! Struct: " + toString());
    }
    if (nullColumnValue == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'nullColumnValue' was not present! Struct: " + toString());
    }
    if (partitions == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partitions' was not present! Struct: " + toString());
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

  private static class THdfsTableStandardSchemeFactory implements SchemeFactory {
    public THdfsTableStandardScheme getScheme() {
      return new THdfsTableStandardScheme();
    }
  }

  private static class THdfsTableStandardScheme extends StandardScheme<THdfsTable> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, THdfsTable struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HDFS_BASE_DIR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hdfsBaseDir = iprot.readString();
              struct.setHdfsBaseDirIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COL_NAMES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.colNames = new ArrayList<String>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  String _elem10; // required
                  _elem10 = iprot.readString();
                  struct.colNames.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setColNamesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NULL_PARTITION_KEY_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.nullPartitionKeyValue = iprot.readString();
              struct.setNullPartitionKeyValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // NULL_COLUMN_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.nullColumnValue = iprot.readString();
              struct.setNullColumnValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // AVRO_SCHEMA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.avroSchema = iprot.readString();
              struct.setAvroSchemaIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PARTITIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map11 = iprot.readMapBegin();
                struct.partitions = new HashMap<Long,THdfsPartition>(2*_map11.size);
                for (int _i12 = 0; _i12 < _map11.size; ++_i12)
                {
                  long _key13; // required
                  THdfsPartition _val14; // required
                  _key13 = iprot.readI64();
                  _val14 = new THdfsPartition();
                  _val14.read(iprot);
                  struct.partitions.put(_key13, _val14);
                }
                iprot.readMapEnd();
              }
              struct.setPartitionsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, THdfsTable struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.hdfsBaseDir != null) {
        oprot.writeFieldBegin(HDFS_BASE_DIR_FIELD_DESC);
        oprot.writeString(struct.hdfsBaseDir);
        oprot.writeFieldEnd();
      }
      if (struct.colNames != null) {
        oprot.writeFieldBegin(COL_NAMES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.colNames.size()));
          for (String _iter15 : struct.colNames)
          {
            oprot.writeString(_iter15);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.nullPartitionKeyValue != null) {
        oprot.writeFieldBegin(NULL_PARTITION_KEY_VALUE_FIELD_DESC);
        oprot.writeString(struct.nullPartitionKeyValue);
        oprot.writeFieldEnd();
      }
      if (struct.partitions != null) {
        oprot.writeFieldBegin(PARTITIONS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, struct.partitions.size()));
          for (Map.Entry<Long, THdfsPartition> _iter16 : struct.partitions.entrySet())
          {
            oprot.writeI64(_iter16.getKey());
            _iter16.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.nullColumnValue != null) {
        oprot.writeFieldBegin(NULL_COLUMN_VALUE_FIELD_DESC);
        oprot.writeString(struct.nullColumnValue);
        oprot.writeFieldEnd();
      }
      if (struct.avroSchema != null) {
        if (struct.isSetAvroSchema()) {
          oprot.writeFieldBegin(AVRO_SCHEMA_FIELD_DESC);
          oprot.writeString(struct.avroSchema);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class THdfsTableTupleSchemeFactory implements SchemeFactory {
    public THdfsTableTupleScheme getScheme() {
      return new THdfsTableTupleScheme();
    }
  }

  private static class THdfsTableTupleScheme extends TupleScheme<THdfsTable> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, THdfsTable struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.hdfsBaseDir);
      {
        oprot.writeI32(struct.colNames.size());
        for (String _iter17 : struct.colNames)
        {
          oprot.writeString(_iter17);
        }
      }
      oprot.writeString(struct.nullPartitionKeyValue);
      oprot.writeString(struct.nullColumnValue);
      {
        oprot.writeI32(struct.partitions.size());
        for (Map.Entry<Long, THdfsPartition> _iter18 : struct.partitions.entrySet())
        {
          oprot.writeI64(_iter18.getKey());
          _iter18.getValue().write(oprot);
        }
      }
      BitSet optionals = new BitSet();
      if (struct.isSetAvroSchema()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetAvroSchema()) {
        oprot.writeString(struct.avroSchema);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, THdfsTable struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.hdfsBaseDir = iprot.readString();
      struct.setHdfsBaseDirIsSet(true);
      {
        org.apache.thrift.protocol.TList _list19 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.colNames = new ArrayList<String>(_list19.size);
        for (int _i20 = 0; _i20 < _list19.size; ++_i20)
        {
          String _elem21; // required
          _elem21 = iprot.readString();
          struct.colNames.add(_elem21);
        }
      }
      struct.setColNamesIsSet(true);
      struct.nullPartitionKeyValue = iprot.readString();
      struct.setNullPartitionKeyValueIsSet(true);
      struct.nullColumnValue = iprot.readString();
      struct.setNullColumnValueIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map22 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.partitions = new HashMap<Long,THdfsPartition>(2*_map22.size);
        for (int _i23 = 0; _i23 < _map22.size; ++_i23)
        {
          long _key24; // required
          THdfsPartition _val25; // required
          _key24 = iprot.readI64();
          _val25 = new THdfsPartition();
          _val25.read(iprot);
          struct.partitions.put(_key24, _val25);
        }
      }
      struct.setPartitionsIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.avroSchema = iprot.readString();
        struct.setAvroSchemaIsSet(true);
      }
    }
  }

}

