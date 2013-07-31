'''
Created on Jul 10, 2013

@author: Tekchen
'''
import org.apache.pig.impl.logicalLayer.schema.Schema as Schema
import org.apache.pig.impl.logicalLayer.schema.Schema.FieldSchema as FieldSchema
from org.apache.pig.data import DataType
fields=[]
field1 = FieldSchema('x',DataType.INTEGER)
field2 = FieldSchema('y',DataType.BYTEARRAY)
fields.append(field1)
fields.append(field2)
schema=Schema(fields)

print schema