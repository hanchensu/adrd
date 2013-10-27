'''
Created on Oct 24, 2013

@author: hanchensu
'''

import math
cntFeas = {}
cntClass = {}

DEFAULT = '0'
MAX_IDX = 6998 + 5

NUM_ALL = 8
NUM_TEST = 5

FEA_THRESHOLD = 0.1

testSet = []

def line2Record(line, classIdx):
  res = []
  feas = {}
  for word in line.split():
    kv = word.split(':')
    if(len(kv) != 2): 
      continue
    if(kv[0] == classIdx):
      res.append(kv[1])
    else:  
      feas[kv[0]] = kv[1]
  res.append(feas)
  return res
  

def train(trainFile, classIdx, sparse):
  count = 0
  for line in open(trainFile):
    count += 1
    print count
    
    if count % NUM_ALL == NUM_TEST:
      testSet.append(line)
      continue
    
    record = line2Record(line, classIdx)
    clas = record[0]
#     if clas == '2':
#       cntClass[clas] = cntClass.get(clas, 0) + 4
#     else:
    cntClass[clas] = cntClass.get(clas, 0) + 1
    for key, value in record[1].items():
      cntFeas[key] = cntFeas.get(key, {})
      cntFeas[key][clas] = cntFeas[key].get(clas, {})
#       if clas == '2':
#         cntFeas[key][clas][value] = cntFeas[key][clas].get(value, 0) + 4
#       else:
      cntFeas[key][clas][value] = cntFeas[key][clas].get(value, 0) + 1
#   print 'cntFeas: ', cntFeas
#   print 'cntClass: ', cntClass
#   print 'disNums:', distinctNums('2', sparse)


def distinctNums(colLabel, sparse):
  if sparse == True:
    addon = 1
  else:
    addon = 0
  if colLabel not in cntFeas:
    return addon
  values = set()
  for value in cntFeas[colLabel].values():
    values |= set(value.keys())
  return len(values) + addon


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


def test(classifyFile, testres, lamda):
  
  count = 0
  outputFile = open(testres,'w+')
  for line in testSet:
    p1_log = 0;
    p2_log = 0;
    count += 1
    print count, len(testSet)
       
    record = line2Record(line, '1')
    real = record[0]
    vector = record[1]
    cols = vector.keys()
    for i in range(2, MAX_IDX + 1):
      idx = '%d' % i
      if idx in cols:
        c1 = cnt(idx, '1', vector[idx]) / float(cntClass['1'])
        c2 = cnt(idx, '2', vector[idx]) / float(cntClass['2'])
        if (c1 < FEA_THRESHOLD and c2 < FEA_THRESHOLD): 
          continue
        p1_log += math.log((cnt(idx, '1', vector[idx]) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
        p2_log += math.log((cnt(idx, '2', vector[idx]) + lamda)) - math.log(cntClass['2'] + lamda * distinctNums(idx, True))
    p1_log += math.log((cntClass['1'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
    p2_log += math.log((cntClass['2'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
    
    p1=1/(math.exp(p2_log-p1_log)+1)
    
    outputFile.write(str(1-p1)+'\t'+real+'\n')
  
  
train('D:/worktmp/people/lab-data.txt', '1', 0)
test('D:/worktmp/people/lab-data.txt','D:/worktmp/people/testres.txt', 1)

# outputFile = open('D:/worktmp/people/testres.txt','w')
# outputFile.write(str(0.1)+'\t'+'shc')
