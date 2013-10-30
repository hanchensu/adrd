'''
Created on Oct 29, 2013

@author: hanchensu
'''

from math import log
from bayes.util import *
from numpy import *

cntFeas = {}
cntClass = {}
testSet = []

DEFAULT = '0'
MAX_IDX = 6998 + 5
NUM_ALL = 9
NUM_TEST = 5
FEA_THRESHOLD = 0.1
FEA_USED_NUM = 500

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


def sortFeas():
  feaId=[]
  feaGap=[]
  for i in range(2,MAX_IDX+1):
    idx = str(i)
    c1 = cnt(idx, '1', '1') / float(cntClass['1'])
    c2 = cnt(idx, '2', '1') / float(cntClass['2'])
    feaId.append(idx)
    feaGap.append(abs(c1-c2))
  indecies = array(feaGap).argsort()
  outFile = open("D:/worktmp/people/Gap.txt", 'w')
  for i in reversed(indecies.tolist()):
    if feaId[i] not in ['1','2','3','4','5']:
      print feaId[i],feaGap[i]
      outFile.write(str(feaId[i])+'\t'+str(feaGap[i])+'\n')
    
train('D:/worktmp/people/lab-data.txt', '1', 0)
sortFeas()