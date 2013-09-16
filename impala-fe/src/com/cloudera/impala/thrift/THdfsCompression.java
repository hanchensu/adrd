/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.cloudera.impala.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum THdfsCompression implements org.apache.thrift.TEnum {
  NONE(0),
  DEFAULT(1),
  GZIP(2),
  DEFLATE(3),
  BZIP2(4),
  SNAPPY(5),
  SNAPPY_BLOCKED(6),
  LZO(7);

  private final int value;

  private THdfsCompression(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static THdfsCompression findByValue(int value) { 
    switch (value) {
      case 0:
        return NONE;
      case 1:
        return DEFAULT;
      case 2:
        return GZIP;
      case 3:
        return DEFLATE;
      case 4:
        return BZIP2;
      case 5:
        return SNAPPY;
      case 6:
        return SNAPPY_BLOCKED;
      case 7:
        return LZO;
      default:
        return null;
    }
  }
}
