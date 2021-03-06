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

public class TPlanFragment implements org.apache.thrift.TBase<TPlanFragment, TPlanFragment._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TPlanFragment");

  private static final org.apache.thrift.protocol.TField PLAN_FIELD_DESC = new org.apache.thrift.protocol.TField("plan", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField OUTPUT_EXPRS_FIELD_DESC = new org.apache.thrift.protocol.TField("output_exprs", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField OUTPUT_SINK_FIELD_DESC = new org.apache.thrift.protocol.TField("output_sink", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField PARTITION_FIELD_DESC = new org.apache.thrift.protocol.TField("partition", org.apache.thrift.protocol.TType.STRUCT, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TPlanFragmentStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TPlanFragmentTupleSchemeFactory());
  }

  public com.cloudera.impala.thrift.TPlan plan; // optional
  public List<com.cloudera.impala.thrift.TExpr> output_exprs; // optional
  public com.cloudera.impala.thrift.TDataSink output_sink; // optional
  public com.cloudera.impala.thrift.TDataPartition partition; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PLAN((short)2, "plan"),
    OUTPUT_EXPRS((short)4, "output_exprs"),
    OUTPUT_SINK((short)5, "output_sink"),
    PARTITION((short)6, "partition");

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
        case 2: // PLAN
          return PLAN;
        case 4: // OUTPUT_EXPRS
          return OUTPUT_EXPRS;
        case 5: // OUTPUT_SINK
          return OUTPUT_SINK;
        case 6: // PARTITION
          return PARTITION;
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
  private _Fields optionals[] = {_Fields.PLAN,_Fields.OUTPUT_EXPRS,_Fields.OUTPUT_SINK};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PLAN, new org.apache.thrift.meta_data.FieldMetaData("plan", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TPlan.class)));
    tmpMap.put(_Fields.OUTPUT_EXPRS, new org.apache.thrift.meta_data.FieldMetaData("output_exprs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TExpr.class))));
    tmpMap.put(_Fields.OUTPUT_SINK, new org.apache.thrift.meta_data.FieldMetaData("output_sink", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TDataSink.class)));
    tmpMap.put(_Fields.PARTITION, new org.apache.thrift.meta_data.FieldMetaData("partition", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.cloudera.impala.thrift.TDataPartition.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TPlanFragment.class, metaDataMap);
  }

  public TPlanFragment() {
  }

  public TPlanFragment(
    com.cloudera.impala.thrift.TDataPartition partition)
  {
    this();
    this.partition = partition;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TPlanFragment(TPlanFragment other) {
    if (other.isSetPlan()) {
      this.plan = new com.cloudera.impala.thrift.TPlan(other.plan);
    }
    if (other.isSetOutput_exprs()) {
      List<com.cloudera.impala.thrift.TExpr> __this__output_exprs = new ArrayList<com.cloudera.impala.thrift.TExpr>();
      for (com.cloudera.impala.thrift.TExpr other_element : other.output_exprs) {
        __this__output_exprs.add(new com.cloudera.impala.thrift.TExpr(other_element));
      }
      this.output_exprs = __this__output_exprs;
    }
    if (other.isSetOutput_sink()) {
      this.output_sink = new com.cloudera.impala.thrift.TDataSink(other.output_sink);
    }
    if (other.isSetPartition()) {
      this.partition = new com.cloudera.impala.thrift.TDataPartition(other.partition);
    }
  }

  public TPlanFragment deepCopy() {
    return new TPlanFragment(this);
  }

  @Override
  public void clear() {
    this.plan = null;
    this.output_exprs = null;
    this.output_sink = null;
    this.partition = null;
  }

  public com.cloudera.impala.thrift.TPlan getPlan() {
    return this.plan;
  }

  public TPlanFragment setPlan(com.cloudera.impala.thrift.TPlan plan) {
    this.plan = plan;
    return this;
  }

  public void unsetPlan() {
    this.plan = null;
  }

  /** Returns true if field plan is set (has been assigned a value) and false otherwise */
  public boolean isSetPlan() {
    return this.plan != null;
  }

  public void setPlanIsSet(boolean value) {
    if (!value) {
      this.plan = null;
    }
  }

  public int getOutput_exprsSize() {
    return (this.output_exprs == null) ? 0 : this.output_exprs.size();
  }

  public java.util.Iterator<com.cloudera.impala.thrift.TExpr> getOutput_exprsIterator() {
    return (this.output_exprs == null) ? null : this.output_exprs.iterator();
  }

  public void addToOutput_exprs(com.cloudera.impala.thrift.TExpr elem) {
    if (this.output_exprs == null) {
      this.output_exprs = new ArrayList<com.cloudera.impala.thrift.TExpr>();
    }
    this.output_exprs.add(elem);
  }

  public List<com.cloudera.impala.thrift.TExpr> getOutput_exprs() {
    return this.output_exprs;
  }

  public TPlanFragment setOutput_exprs(List<com.cloudera.impala.thrift.TExpr> output_exprs) {
    this.output_exprs = output_exprs;
    return this;
  }

  public void unsetOutput_exprs() {
    this.output_exprs = null;
  }

  /** Returns true if field output_exprs is set (has been assigned a value) and false otherwise */
  public boolean isSetOutput_exprs() {
    return this.output_exprs != null;
  }

  public void setOutput_exprsIsSet(boolean value) {
    if (!value) {
      this.output_exprs = null;
    }
  }

  public com.cloudera.impala.thrift.TDataSink getOutput_sink() {
    return this.output_sink;
  }

  public TPlanFragment setOutput_sink(com.cloudera.impala.thrift.TDataSink output_sink) {
    this.output_sink = output_sink;
    return this;
  }

  public void unsetOutput_sink() {
    this.output_sink = null;
  }

  /** Returns true if field output_sink is set (has been assigned a value) and false otherwise */
  public boolean isSetOutput_sink() {
    return this.output_sink != null;
  }

  public void setOutput_sinkIsSet(boolean value) {
    if (!value) {
      this.output_sink = null;
    }
  }

  public com.cloudera.impala.thrift.TDataPartition getPartition() {
    return this.partition;
  }

  public TPlanFragment setPartition(com.cloudera.impala.thrift.TDataPartition partition) {
    this.partition = partition;
    return this;
  }

  public void unsetPartition() {
    this.partition = null;
  }

  /** Returns true if field partition is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition() {
    return this.partition != null;
  }

  public void setPartitionIsSet(boolean value) {
    if (!value) {
      this.partition = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PLAN:
      if (value == null) {
        unsetPlan();
      } else {
        setPlan((com.cloudera.impala.thrift.TPlan)value);
      }
      break;

    case OUTPUT_EXPRS:
      if (value == null) {
        unsetOutput_exprs();
      } else {
        setOutput_exprs((List<com.cloudera.impala.thrift.TExpr>)value);
      }
      break;

    case OUTPUT_SINK:
      if (value == null) {
        unsetOutput_sink();
      } else {
        setOutput_sink((com.cloudera.impala.thrift.TDataSink)value);
      }
      break;

    case PARTITION:
      if (value == null) {
        unsetPartition();
      } else {
        setPartition((com.cloudera.impala.thrift.TDataPartition)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PLAN:
      return getPlan();

    case OUTPUT_EXPRS:
      return getOutput_exprs();

    case OUTPUT_SINK:
      return getOutput_sink();

    case PARTITION:
      return getPartition();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PLAN:
      return isSetPlan();
    case OUTPUT_EXPRS:
      return isSetOutput_exprs();
    case OUTPUT_SINK:
      return isSetOutput_sink();
    case PARTITION:
      return isSetPartition();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TPlanFragment)
      return this.equals((TPlanFragment)that);
    return false;
  }

  public boolean equals(TPlanFragment that) {
    if (that == null)
      return false;

    boolean this_present_plan = true && this.isSetPlan();
    boolean that_present_plan = true && that.isSetPlan();
    if (this_present_plan || that_present_plan) {
      if (!(this_present_plan && that_present_plan))
        return false;
      if (!this.plan.equals(that.plan))
        return false;
    }

    boolean this_present_output_exprs = true && this.isSetOutput_exprs();
    boolean that_present_output_exprs = true && that.isSetOutput_exprs();
    if (this_present_output_exprs || that_present_output_exprs) {
      if (!(this_present_output_exprs && that_present_output_exprs))
        return false;
      if (!this.output_exprs.equals(that.output_exprs))
        return false;
    }

    boolean this_present_output_sink = true && this.isSetOutput_sink();
    boolean that_present_output_sink = true && that.isSetOutput_sink();
    if (this_present_output_sink || that_present_output_sink) {
      if (!(this_present_output_sink && that_present_output_sink))
        return false;
      if (!this.output_sink.equals(that.output_sink))
        return false;
    }

    boolean this_present_partition = true && this.isSetPartition();
    boolean that_present_partition = true && that.isSetPartition();
    if (this_present_partition || that_present_partition) {
      if (!(this_present_partition && that_present_partition))
        return false;
      if (!this.partition.equals(that.partition))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TPlanFragment other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TPlanFragment typedOther = (TPlanFragment)other;

    lastComparison = Boolean.valueOf(isSetPlan()).compareTo(typedOther.isSetPlan());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlan()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.plan, typedOther.plan);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOutput_exprs()).compareTo(typedOther.isSetOutput_exprs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutput_exprs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.output_exprs, typedOther.output_exprs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOutput_sink()).compareTo(typedOther.isSetOutput_sink());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutput_sink()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.output_sink, typedOther.output_sink);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPartition()).compareTo(typedOther.isSetPartition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partition, typedOther.partition);
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
    StringBuilder sb = new StringBuilder("TPlanFragment(");
    boolean first = true;

    if (isSetPlan()) {
      sb.append("plan:");
      if (this.plan == null) {
        sb.append("null");
      } else {
        sb.append(this.plan);
      }
      first = false;
    }
    if (isSetOutput_exprs()) {
      if (!first) sb.append(", ");
      sb.append("output_exprs:");
      if (this.output_exprs == null) {
        sb.append("null");
      } else {
        sb.append(this.output_exprs);
      }
      first = false;
    }
    if (isSetOutput_sink()) {
      if (!first) sb.append(", ");
      sb.append("output_sink:");
      if (this.output_sink == null) {
        sb.append("null");
      } else {
        sb.append(this.output_sink);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("partition:");
    if (this.partition == null) {
      sb.append("null");
    } else {
      sb.append(this.partition);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (partition == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partition' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (plan != null) {
      plan.validate();
    }
    if (output_sink != null) {
      output_sink.validate();
    }
    if (partition != null) {
      partition.validate();
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

  private static class TPlanFragmentStandardSchemeFactory implements SchemeFactory {
    public TPlanFragmentStandardScheme getScheme() {
      return new TPlanFragmentStandardScheme();
    }
  }

  private static class TPlanFragmentStandardScheme extends StandardScheme<TPlanFragment> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TPlanFragment struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 2: // PLAN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.plan = new com.cloudera.impala.thrift.TPlan();
              struct.plan.read(iprot);
              struct.setPlanIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OUTPUT_EXPRS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.output_exprs = new ArrayList<com.cloudera.impala.thrift.TExpr>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  com.cloudera.impala.thrift.TExpr _elem2; // required
                  _elem2 = new com.cloudera.impala.thrift.TExpr();
                  _elem2.read(iprot);
                  struct.output_exprs.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setOutput_exprsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OUTPUT_SINK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.output_sink = new com.cloudera.impala.thrift.TDataSink();
              struct.output_sink.read(iprot);
              struct.setOutput_sinkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PARTITION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.partition = new com.cloudera.impala.thrift.TDataPartition();
              struct.partition.read(iprot);
              struct.setPartitionIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TPlanFragment struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.plan != null) {
        if (struct.isSetPlan()) {
          oprot.writeFieldBegin(PLAN_FIELD_DESC);
          struct.plan.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.output_exprs != null) {
        if (struct.isSetOutput_exprs()) {
          oprot.writeFieldBegin(OUTPUT_EXPRS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.output_exprs.size()));
            for (com.cloudera.impala.thrift.TExpr _iter3 : struct.output_exprs)
            {
              _iter3.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.output_sink != null) {
        if (struct.isSetOutput_sink()) {
          oprot.writeFieldBegin(OUTPUT_SINK_FIELD_DESC);
          struct.output_sink.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.partition != null) {
        oprot.writeFieldBegin(PARTITION_FIELD_DESC);
        struct.partition.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TPlanFragmentTupleSchemeFactory implements SchemeFactory {
    public TPlanFragmentTupleScheme getScheme() {
      return new TPlanFragmentTupleScheme();
    }
  }

  private static class TPlanFragmentTupleScheme extends TupleScheme<TPlanFragment> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TPlanFragment struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.partition.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetPlan()) {
        optionals.set(0);
      }
      if (struct.isSetOutput_exprs()) {
        optionals.set(1);
      }
      if (struct.isSetOutput_sink()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPlan()) {
        struct.plan.write(oprot);
      }
      if (struct.isSetOutput_exprs()) {
        {
          oprot.writeI32(struct.output_exprs.size());
          for (com.cloudera.impala.thrift.TExpr _iter4 : struct.output_exprs)
          {
            _iter4.write(oprot);
          }
        }
      }
      if (struct.isSetOutput_sink()) {
        struct.output_sink.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TPlanFragment struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.partition = new com.cloudera.impala.thrift.TDataPartition();
      struct.partition.read(iprot);
      struct.setPartitionIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.plan = new com.cloudera.impala.thrift.TPlan();
        struct.plan.read(iprot);
        struct.setPlanIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.output_exprs = new ArrayList<com.cloudera.impala.thrift.TExpr>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            com.cloudera.impala.thrift.TExpr _elem7; // required
            _elem7 = new com.cloudera.impala.thrift.TExpr();
            _elem7.read(iprot);
            struct.output_exprs.add(_elem7);
          }
        }
        struct.setOutput_exprsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.output_sink = new com.cloudera.impala.thrift.TDataSink();
        struct.output_sink.read(iprot);
        struct.setOutput_sinkIsSet(true);
      }
    }
  }

}

