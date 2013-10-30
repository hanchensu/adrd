'''
Created on Oct 24, 2013

@author: hanchensu
'''
#普通贝叶斯方式计算
from bayes.util import line2Record
from numpy import *
import math
import string

DEFAULT = '0'
NUM_ALL = 5
NUM_TEST = 3 
FEA_THRESHOLD = 0.1
cntFeas = {}
cntClass = {}
feaSet = set()  
testSet=[]
trainSet=[]

def line2Record_2(line):
  res = []
  featVec = {}
  for word in line.split():
    kv = word.split(':')
    if(len(kv) != 2): 
      res.append(kv[0])
    else:  
      featVec[kv[0]] = '1'
      feaSet.add(kv[0])
  res.append(featVec)
  return res


def preTrain(trainFile):
  count = 0
  for line in open(trainFile):
    count+=1
    if count % NUM_ALL == NUM_TEST:
      testSet.append(line)
    else:
      trainSet.append(line)

def train(classIdx):
  for line in trainSet:
    record = line2Record_2(line)
    clas = record[0]
    cntClass[clas] = cntClass.get(clas, 0) + 1
    for key, value in record[1].items():
      cntFeas[key] = cntFeas.get(key, {})
      cntFeas[key][clas] = cntFeas[key].get(clas, {})
      cntFeas[key][clas][value] = cntFeas[key][clas].get(value, 0) + 1


def distinctNums(colLabel, sparse):
  if sparse == True: addon = 1
  else: addon = 0
  
  if colLabel not in cntFeas:return addon
  
  values = set()
  for value in cntFeas[colLabel].values(): values |= set(value.keys())
  
  return len(values) + addon


def cnt(col, clas, value):
  if clas not in cntClass.keys(): return 0 
  
  if col not in cntFeas:
    if value == DEFAULT: return cntClass[clas]
    else: return 0
  
  if clas not in cntFeas[col].keys():
    if value == DEFAULT: return cntClass[clas]
    else: return 0
  
  if value not in cntFeas[col][clas]:
    if value == DEFAULT: return cntClass[clas] - sum(cntFeas[col][clas].values())
    else: return 0
  
  return cntFeas[col][clas][value]


def test(testres, lamda):
  feasUsed = list(feaSet)
  count = 0
  outputFile = open(testres, 'w+')
  for line in testSet:
    p1_log = 0;
    p2_log = 0;
    changed = False
    count += 1
    print count, len(testSet)
  
    record = line2Record_2(line)
    real = record[0]
    vector = record[1]
    cols = vector.keys()
    
    for i in feasUsed:
      idx = i
      if idx in cols:
        c1 = cnt(idx, '1', vector[idx]) / float(cntClass['1'])
        c2 = cnt(idx, '0', vector[idx]) / float(cntClass['0'])
#         if (c1 > 0 and c1 < FEA_THRESHOLD and c2 > 0 and c2 < FEA_THRESHOLD): 
#           continue
        p1_log += math.log((cnt(idx, '0', vector[idx]) + lamda)) - math.log(cntClass['0'] + lamda * distinctNums(idx, True))
        p2_log += math.log((cnt(idx, '1', vector[idx]) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
        changed = True
    p1 = 0.0
    if changed == False:
      continue
    else:
      p1_log += math.log((cntClass['0'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
      p2_log += math.log((cntClass['1'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
      p1 = 1 / (math.exp(p2_log - p1_log) + 1)
    outputFile.write(str(p1) + '\t' + real + '\n')
  
preTrain('D:/worktmp/people/lab-data-sortCount-1000.txt')
testresFile='D:/worktmp/people/search_1000_res.txt'
train('1')
test(testresFile, 1)
rate = cntClass['0'] / float(sum(cntClass.values()))
print rate

#sort
predStrengths = []
classLabels = []
for line in open(testresFile):
  predStrengths.append(string.atof(line.split()[0]))
  classLabels.append(line.split()[1])
sortedFile = open('D:/worktmp/people/sort_1000_res.txt','w+')
sortedIndicies = array(predStrengths).argsort()
for index in sortedIndicies:
  sortedFile.write('%.3f' % predStrengths[index] +'\t' + classLabels[index]+'\n') 
