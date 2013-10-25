'''
Created on Oct 25, 2013

@author: hanchensu
'''

import matplotlib.pyplot as plt
from numpy import *

def plotROC(predStrengths, classLabels, posLabel):
  cur = (1.0, 1.0)
  ySum = 0.0
  numPosClas = sum(array(classLabels) == posLabel)
  yStep = 1 / float(numPosClas)
  xStep = 1 / float(len(classLabels) - numPosClas)
  sortedIndicies = predStrengths.argsort()
  fig = plt.figure()
  fig.clf()
  ax = plt.subplot(111)
  for index in sortedIndicies.tolist()[0]:
    if classLabels[index] == posLabel:
      delX = 0; delY = yStep;
    else:
      delX = xStep; delY = 0
      ySum += cur[1]
    ax.plot([cur[0], cur[0] - delX], [cur[1], cur[1] - delY], c='b')
    cur = (cur[0] - delX,cur[1] - delY)
  
testlst = array([0.1, 0.05, 0.4, 0.2])
print testlst.argsort().tolist()
