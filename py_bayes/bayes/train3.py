'''
Created on Oct 30, 2013

@author: Tekchen
'''
#word bag or set bayes
import string
from math import log,exp
from numpy import array

DEFAULT = '0'
NUM_ALL = 5
NUM_TEST = 1
cntFeas = {} #{class0:{'fea1':cnt1,'fea2':cnt2...},class1:{'fea1':cnt1,'fea2':cnt2...}}
cntClass = {}
feaSet = set()  
testSet=[]
trainSet=[]

isSetOrNot = False
def line2Record(line, isSet):
  res = []
  featVec = {}
  for word in line.split():
    kv = word.split(':')
    if(len(kv) != 2): 
      res.append(kv[0])
    else:
      if isSet == True:
        featVec[kv[0]] = 1
      else:
        featVec[kv[0]] = string.atoi(kv[1])
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

def train():
  for line in trainSet:
    record = line2Record(line,isSetOrNot)
    clas = record[0]
    cntClass[clas] = cntClass.get(clas, 0) + 1
    cntFeas[clas] = cntFeas.get(clas,{})
    for fea, value in record[1].items():
      cntFeas[clas][fea] = cntFeas[clas].get(fea, 0) + value
      
def test(testres, lamda):
  count = 0
  outputFile = open(testres, 'w')
  for line in testSet:
    p0_log = 0;
    p1_log = 0;
    count += 1
    print count, len(testSet)
  
    record = line2Record(line,isSetOrNot)
    realClas = record[0]
    vector = record[1]
    vecFeas = vector.keys()
    
    for fea in vecFeas:
      probFea0 = (log(cntFeas['1'].get(fea,0)+lamda) - log(sum(cntFeas['1'].values())+lamda)) * vector[fea]
      probFea1 = (log(cntFeas['0'].get(fea,0)+lamda) - log(sum(cntFeas['0'].values())+lamda)) * vector[fea]
        
      p0_log += probFea0
      p1_log += probFea1
    
    p0_log += log((cntClass['1'] + lamda)) - log(sum(cntClass.values()) + len(cntClass) * lamda)
    p1_log += log((cntClass['0'] + lamda)) - log(sum(cntClass.values()) + len(cntClass) * lamda)
    p0 = 1 / (exp(p0_log - p1_log) + 1)
    outputFile.write(str(p0) + '\t' + realClas + '\n')

preTrain('D:/worktmp/people/lab-data-sortCount-1000.txt')
testresFile='D:/worktmp/people/search_bag_1000_res.txt'
train()
test(testresFile, 1)
rate = cntClass['1'] / float(sum(cntClass.values()))
print rate

#sort
predStrengths = []
classLabels = []
for line in open(testresFile):
  predStrengths.append(string.atof(line.split()[0]))
  classLabels.append(line.split()[1])
sortedFile = open('D:/worktmp/people/sort_search_bag_1000_res.txt','w+')
sortedIndicies = array(predStrengths).argsort()
for index in sortedIndicies:
  sortedFile.write('%.3f' % predStrengths[index] +'\t' + classLabels[index]+'\n') 