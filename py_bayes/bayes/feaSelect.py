'''
Created on Oct 29, 2013

@author: hanchensu
'''
from numpy import *
from math import log
from Util import *

cntFeas = {}
cntClass = {}

DEFAULT = '0'

def train(trainFile, classIdx, sparse):
  count = 0
  for line in open(trainFile):
    count += 1; print count
    
    record = line2Record(line, classIdx)
    
    clas = record[0]
    cntClass[clas] = cntClass.get(clas, 0) + 1
    
    for key, value in record[1].items():
      cntFeas[key] = cntFeas.get(key, {})
      cntFeas[key][clas] = cntFeas[key].get(clas, {})
      cntFeas[key][clas][value] = cntFeas[key][clas].get(value, 0) + 1

def cnt(col, clas, value):
  if clas not in cntClass.keys():
    return 0 
  
  if col not in cntFeas:
    if value == DEFAULT:
      return cntClass[clas]
    else:
      return 0
  
  if clas not in cntFeas[col].keys():
    if value == DEFAULT:
      return cntClass[clas]
    else:
      return 0
  
  if value not in cntFeas[col][clas]:
    if value == DEFAULT:
      return cntClass[clas] - sum(cntFeas[col][clas].values())
    else:
      return 0
  
  return cntFeas[col][clas][value]

def distinctValues(colLabel, sparse):
  values = set()
  for value in cntFeas[colLabel].values():
    values |= set(value.keys())
  if sparse == True:
    return values.add(DEFAULT)
  return values


def gain(attrIdx , classIdx):
  res = 0
  for val in distinctValues(attrIdx, True):
    probValue = cntFeas()
   



def entropy(prob):
  return -prob * log(prob, 2)   
  
   
train('D:/worktmp/people/lab-data.txt', '1', 0)
  
  
#   labelNum = {}
#   for line in open(trainFile):
#     label = getLabel(line, classIdx)
#     labelNum[label] = labelNum.get(label, 0) + 1
#   
#   etpy = 0.0
#   allNum = sum(labelNum.values())
#   print allNum
#   for classNum in labelNum.values():
#     print classNum
#     prob = classNum / float(allNum)
#     etpy += -prob * log(prob, 2)  
#   return etpy

