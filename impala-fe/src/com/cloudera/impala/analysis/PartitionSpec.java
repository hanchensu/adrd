package com.cloudera.impala.analysis;

import java.util.List;
import java.util.Set;

import org.apache.hadoop.hive.metastore.api.FieldSchema;

import com.cloudera.impala.authorization.Privilege;
import com.cloudera.impala.catalog.AuthorizationException;
import com.cloudera.impala.catalog.Column;
import com.cloudera.impala.catalog.HdfsTable;
import com.cloudera.impala.catalog.PrimitiveType;
import com.cloudera.impala.catalog.Table;
import com.cloudera.impala.common.AnalysisException;
import com.cloudera.impala.thrift.TPartitionKeyValue;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/*
 * Represents a partition spec - a collection of partition key/values.
 */
public class PartitionSpec implements ParseNode {
  private final ImmutableList<PartitionKeyValue> partitionSpec;
  private TableName tableName;
  private Boolean partitionShouldExist;
  private Privilege privilegeRequirement;

 // The value Hive is configured to use for NULL partition key values.
 // Set during analysis.
 private String nullPartitionKeyValue;

  public PartitionSpec(List<PartitionKeyValue> partitionSpec) {
    this.partitionSpec = ImmutableList.copyOf(partitionSpec);
  }

  public List<PartitionKeyValue> getPartitionSpecKeyValues() {
    return partitionSpec;
  }

  public String getTbl() {
    return tableName.getTbl();
  }

  // The value Hive is configured to use for NULL partition key values.
  // Set during analysis.
  public String getNullPartitionKeyValue() {
    Preconditions.checkNotNull(nullPartitionKeyValue);
    return nullPartitionKeyValue;
  }

  // If set, an additional analysis check will be performed to validate the target table
  // contains the given partition spec.
  public void setPartitionShouldExist() {
    partitionShouldExist = Boolean.TRUE;
  }

  // If set, an additional analysis check will be performed to validate the target table
  // does not contain the given partition spec.
  public void setPartitionShouldNotExist() {
    partitionShouldExist = Boolean.FALSE;
  }

  public void setTableName(TableName tableName) {
    this.tableName = tableName;
  }

  // Set the privilege requirement for this partition spec. Must be set prior to
  // analysis.
  public void setPrivilegeRequirement(Privilege privilege) {
    privilegeRequirement = privilege;
  }

  @Override
  public void analyze(Analyzer analyzer) throws AnalysisException,
      AuthorizationException {
    Preconditions.checkNotNull(tableName);
    Preconditions.checkNotNull(privilegeRequirement);

    Table table = analyzer.getTable(tableName, privilegeRequirement);
    String tableName = table.getDb().getName() + "." + getTbl();

    // Make sure the target table is partitioned.
    if (table.getMetaStoreTable().getPartitionKeysSize() == 0) {
      throw new AnalysisException("Table is not partitioned: " + tableName);
    }

    // Make sure static partition key values only contain constant exprs.
    for (PartitionKeyValue kv: partitionSpec) {
      kv.analyze(analyzer);
    }

    // Get all keys in the target table.
    Set<String> targetPartitionKeys = Sets.newHashSet();
    for (FieldSchema fs: table.getMetaStoreTable().getPartitionKeys()) {
      targetPartitionKeys.add(fs.getName().toLowerCase());
    }

    // All partition keys need to be specified.
    if (targetPartitionKeys.size() != partitionSpec.size()) {
      throw new AnalysisException(String.format("Items in partition spec must exactly " +
          "match the partition columns in the table definition: %s (%d vs %d)",
          tableName, partitionSpec.size(), targetPartitionKeys.size()));
    }

    Set<String> keyNames = Sets.newHashSet();
    // Validate each partition key/value specified, ensuring a matching partition column
    // exists in the target table, no duplicate keys were specified, and that all the
    // column types are compatible.
    for (PartitionKeyValue pk: partitionSpec) {
      if (!keyNames.add(pk.getColName().toLowerCase())) {
        throw new AnalysisException("Duplicate partition key name: " + pk.getColName());
      }

      Column c = table.getColumn(pk.getColName());
      if (c == null) {
        throw new AnalysisException(String.format(
            "Partition column '%s' not found in table: %s", pk.getColName(), tableName));
      } else if (!targetPartitionKeys.contains(pk.getColName().toLowerCase())) {
        throw new AnalysisException(String.format(
            "Column '%s' is not a partition column in table: %s",
             pk.getColName(), tableName));
      } else if (pk.getValue() instanceof NullLiteral) {
        // No need for further analysis checks of this partition key value.
        continue;
      }

      PrimitiveType colType = c.getType();
      PrimitiveType literalType = pk.getValue().getType();
      PrimitiveType compatibleType =
          PrimitiveType.getAssignmentCompatibleType(colType, literalType);
      if (!compatibleType.isValid()) {
        throw new AnalysisException(String.format("Target table not compatible.\n" +
            "Incompatible types '%s' and '%s' in column '%s'", colType.toString(),
            literalType.toString(), pk.getColName()));
      }
      // Check for loss of precision with the partition value
      if (compatibleType != colType) {
        throw new AnalysisException(
            String.format("Partition key value may result in loss of precision.\n" +
            "Would need to cast '%s' to '%s' for partition column: %s",
            pk.getValue().toSql(), colType.toString(), pk.getColName()));
      }
    }
    // Only HDFS tables are partitioned.
    Preconditions.checkState(table instanceof HdfsTable);
    HdfsTable hdfsTable = (HdfsTable) table;
    nullPartitionKeyValue = hdfsTable.getNullPartitionKeyValue();

    if (partitionShouldExist != null) {
      boolean partitionAlreadyExists = hdfsTable.getPartition(partitionSpec) != null;
      if (partitionShouldExist && !partitionAlreadyExists) {
          throw new AnalysisException("Partition spec does not exist: (" +
              Joiner.on(", ").join(partitionSpec) + ").");
      } else if (!partitionShouldExist && partitionAlreadyExists) {
          throw new AnalysisException("Partition spec already exists: (" +
              Joiner.on(", ").join(partitionSpec) + ").");
      }
    }
  }

  /*
   * Returns the Thrift representation of this PartitionSpec.
   */
  public List<TPartitionKeyValue> toThrift() {
    List<TPartitionKeyValue> thriftPartitionSpec = Lists.newArrayList();
    for (PartitionKeyValue kv: partitionSpec) {
      String value = kv.getPartitionKeyValueString(getNullPartitionKeyValue());
      thriftPartitionSpec.add(new TPartitionKeyValue(kv.getColName(), value));
    }
    return thriftPartitionSpec;
  }

  @Override
  public String toSql() {
    List<String> partitionSpecStr = Lists.newArrayList();
    for (PartitionKeyValue kv: partitionSpec) {
      partitionSpecStr.add(kv.getColName() + "=" + kv.getValue());
    }
    return String.format("PARTITION (%s)", Joiner.on(", ").join(partitionSpecStr));
  }

  @Override
  public String debugString() {
    return toSql();
  }
}