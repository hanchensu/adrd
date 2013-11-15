'''
Created on Oct 31, 2013

@author: hanchensu
'''

import matplotlib.pyplot as plt
from numpy import *
import string 

def plotLift(sortedTestFile, posLabel):
     
  predStrengths = []
  classLabels = []
  for line in open(sortedTestFile):
    predStrengths.append(line.split()[0])
    classLabels.append(line.split()[1])
  numPosClas = sum(array(classLabels) == posLabel)
  posRate = numPosClas / float(len(classLabels))
  
  fig = plt.figure()
  fig.clf()
  ax = plt.subplot(111)
  
  lastPre = predStrengths[0]
  pointStart = (1.0, 1.0); pointEnd = (1.0, 1.0)
  prePos = len(classLabels)
  realPos = sum(array(classLabels) == posLabel)
  maxRate = 1.0
  
  for index in range(0, len(classLabels)):
    prePos -= 1
    if classLabels[index] == posLabel:
      realPos -= 1
      
    if prePos == 0:
      pointEnd = (0.0,0.0)
    else:
      pointEnd = (prePos/float(len(classLabels)), (realPos/float(prePos)) / float(posRate))
    
    if pointEnd[1] > maxRate: maxRate = pointEnd[1]
    
    print pointEnd
    
    if index + 1 == len(classLabels) or predStrengths[index + 1] != lastPre:
      
      if index + 1 < len(classLabels):
        ax.plot([pointStart[0], pointEnd[0]], [pointStart[1], pointEnd[1]], c='b')
        pointStart = pointEnd
        lastPre = predStrengths[index + 1]
  
  print maxRate
  plt.xlabel('Depth (Rate Of Predict Positive)'); plt.ylabel('Lift')
  ax.axis([0, 1, 1, maxRate])
  plt.show()



def reachCTR(sortedTestFile, posLabel):
     
  predStrengths = []
  classLabels = []
  for line in open(sortedTestFile):
    predStrengths.append(line.split()[0])
    classLabels.append(line.split()[1])
  numPosClas = sum(array(classLabels) == posLabel)
  posRate = numPosClas / float(len(classLabels))
  
  fig = plt.figure()
  fig.clf()
  ax = plt.subplot(111)
  
  lastPre = predStrengths[0]
  pointStart = (1.0, posRate); pointEnd = (1.0, 1.0)
  prePos = len(classLabels)
  realPos = sum(array(classLabels) == posLabel)
  maxRate = 1.0
  
  for index in range(0, len(classLabels)):
    prePos -= 1
    if classLabels[index] == posLabel:
      realPos -= 1
      
    if prePos == 0:
      pointEnd = (0.0,0.0)
    else:
      pointEnd = (prePos/float(len(classLabels)), (realPos/float(prePos)))
    
    if pointEnd[1] > maxRate: maxRate = pointEnd[1]
    
    print pointEnd
    
    if index + 1 == len(classLabels) or predStrengths[index + 1] != lastPre:
      
      if index + 1 < len(classLabels):
        ax.plot([pointStart[0], pointEnd[0]], [pointStart[1], pointEnd[1]], c='b')
        pointStart = pointEnd
        lastPre = predStrengths[index + 1]
  
  print maxRate
  plt.xlabel('Depth (Rate Of Predict Positive)'); plt.ylabel('Precision')
  ax.axis([0, 1, 0, 1])
  plt.show()

reachCTR('C:/Users/hanchensu/git/adrd/py_bayes/bayes/sortedTestRes','F')