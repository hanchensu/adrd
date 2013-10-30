'''
Created on Oct 29, 2013

@author: hanchensu
'''
import string
from math import log
from numpy import *
from bayes.util import line2Record

MAX_IDX = 6998 + 5

def calcEnt(dataSet):
  labelCnt = {}
  numVec = len(dataSet)
  for featVec in dataSet:
    labelCnt[featVec[0]] = labelCnt.get(featVec[0], 0) + 1
  shannonEnt = 0.0
  for key in labelCnt:
    prob = float(labelCnt[key]) / numVec
    shannonEnt -= prob * log(prob, 2)
  return shannonEnt
    
def makeDataSet(trainFile, cIdx):
  dataSet = []
  for line in open(trainFile):
    dataSet.append(line2Record(line, cIdx))
  return dataSet


def splitDataSet(dataSet, axis):
  retDataSet = {}
  for featVec in dataSet:
    axisValue = featVec[1].get(axis, '0')
    retDataSet[axisValue] = retDataSet.get(axisValue, [])
    retDataSet[axisValue].append(featVec)
  return retDataSet
  
def calcGain(dataSet, feaIdx):
  subDataSets = splitDataSet(dataSet, feaIdx)
  infoEnt = calcEnt(dataSet)
  for subDataSet in subDataSets.values():
    prob = len(subDataSet) / float(len(dataSet))
    infoEnt -= prob * calcEnt(subDataSet)
  return infoEnt
  
if __name__ == '__main__':
  feaId=[]
  info=[]
  for line in open("D:/worktmp/people/infoGain.txt"):
    feaId.append(line.split()[0])
    info.append(string.atof(line.split()[1]))
  
  indecies = array(info).argsort()
#   print indecies
  feas=[]
  for i in indecies:
    feas.append(i)
  feas.reverse()
  
  outFile = open("D:/worktmp/people/sortGain.txt", 'w')
  for i in feas:
    outFile.write(str(feaId[i]) + '\t' + str(info[i]) + '\n')
  
    
#     print feaId[i],info[i]
#   dataSet = makeDataSet('D:/worktmp/people/lab-data.txt', '1')
#   outFile = open("D:/worktmp/people/infoGain.txt", 'w+')
#   for i in range(2, MAX_IDX + 1):
#     print i
#     outFile.write(str(i) + '\t' + str(calcGain(dataSet, str(i))) + '\n')

