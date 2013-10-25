'''
Created on Oct 24, 2013

@author: hanchensu
'''

import math
cntFeas = {}
cntClass = {}

DEFAULT = '0'
MAX_IDX = 6998 + 5

NUM_ALL=9
NUM_TEST=3

FEA_THRESHOLD = 0.1

testSet=[]

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


def test(classifyFile, lamda):
  
  A = 0
  B = 0;
  C = 0;
  D = 0;
  count = 0
#   for line in open(classifyFile):
  for line in testSet:
    p1 = 0;
    p2 = 0;
    count += 1
    print count,len(testSet)
       
#     if count % NUM_ALL != NUM_TEST:
#       continue
    
#     if count > 2000:
#       break
    
    record = line2Record(line, '1')
    real = record[0]
    vector = record[1]
    cols = vector.keys()
    for i in range(2, MAX_IDX + 1):
      idx = '%d' % i
      if idx in cols:
        c1 = cnt(idx, '1', vector[idx])/float(cntClass['1'])
        c2 = cnt(idx, '2', vector[idx])/float(cntClass['2'])
        if (c1 < FEA_THRESHOLD and c2 < FEA_THRESHOLD): 
          continue
        p1 += math.log((cnt(idx, '1', vector[idx]) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
        p2 += math.log((cnt(idx, '2', vector[idx]) + lamda)) - math.log(cntClass['2'] + lamda * distinctNums(idx, True))
#         print "class1:",cnt(idx, '1', vector[idx]), cntClass['1'], cnt(idx, '1', vector[idx])/float(cntClass['1']),math.log((cnt(idx, '1', vector[idx]) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
#         print "class2:",cnt(idx, '2', vector[idx]), cntClass['2'], cnt(idx, '2', vector[idx])/float(cntClass['2']),math.log((cnt(idx, '2', vector[idx]) + lamda)) - math.log(cntClass['2'] + lamda * distinctNums(idx, True))
#         print p1,p2

#       else:
#         p1 += math.log((cnt(idx, '1', DEFAULT) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
#         p2 += math.log((cnt(idx, '2', DEFAULT) + lamda)) - math.log(cntClass['2'] + lamda * distinctNums(idx, True))
#         print "class1:",cnt(idx, '1', DEFAULT), cntClass['1'], cnt(idx, '1', DEFAULT)/float(cntClass['1']), math.log((cnt(idx, '1', DEFAULT) + lamda)) - math.log(cntClass['1'] + lamda * distinctNums(idx, True))
#         print "class2:",cnt(idx, '2', DEFAULT), cntClass['2'], cnt(idx, '2', DEFAULT)/float(cntClass['2']), math.log((cnt(idx, '2', DEFAULT) + lamda)) - math.log(cntClass['2'] + lamda * distinctNums(idx, True))
#       print "step:",p1,p2
    p1 += math.log((cntClass['1'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
    p2 += math.log((cntClass['2'] + lamda)) - math.log(sum(cntClass.values()) + len(cntClass) * lamda)
    
    if p1 > p2:
      res = '1'
    else:
      res = '2'
    if res == '1' and real == '1':
      A += 1
    if res == '2' and real == '1':
      B += 1
    if res == '1' and real == '2':
      C += 1
    if res == '2' and real == '2':
      D += 1
  print  A, B, C, D
  print (A+D)/float(A+B+C+D)
      
    

train('D:/worktmp/people/lab-data.txt', '1',0)
test('D:/worktmp/people/lab-data.txt', 1)

  
