'''
Created on Oct 25, 2013

@author: hanchensu
'''

import matplotlib.pyplot as plt
from numpy import *
import string
# def plotROC(predStrengths, classLabels, posLabel):
#   cur = (1.0, 1.0)
#   ySum = 0.0
#   numPosClas = sum(array(classLabels) == posLabel)
#   yStep = 1 / float(numPosClas)
#   xStep = 1 / float(len(classLabels) - numPosClas)
#   sortedIndicies = array(predStrengths).argsort()
#   fig = plt.figure()
#   fig.clf()
#   ax = plt.subplot(111)
#   count = 0
#   for index in sortedIndicies.tolist():
#     print count
#     count += 1
#     if classLabels[index] == posLabel:
#       delX = 0; delY = yStep;
#     else:
#       delX = xStep; delY = 0
#       ySum += cur[1]
#     ax.plot([cur[0], cur[0] - delX], [cur[1], cur[1] - delY], c='b')
#     cur = (cur[0] - delX, cur[1] - delY)
#   ax.plot([0, 1], [0, 1], 'b--')
#   plt.xlabel('False Positive Rate'); plt.ylabel('True Positive Rate')
#   plt.title('ROC curve')
#   ax.axis([0, 1, 0, 1])
#   plt.show()
#   print "the Area Under the Curve is: ", ySum * xStep

def plotROC2(sortedTestFile, posLabel):
     
  predStrengths = []
  classLabels = []
  for line in open(sortedTestFile):
    predStrengths.append(line.split()[0])
    classLabels.append(line.split()[1])

# def plotROC2(predStrengths, classLabels, posLabel):
  
  numPosClas = sum(array(classLabels) == posLabel)
  print 
  yStep = 1 / float(numPosClas)
  xStep = 1 / float(len(classLabels) - numPosClas)
  fig = plt.figure()
  fig.clf()
  ax = plt.subplot(111)
  
 
  lastPre = predStrengths[0]
  area = 0.0
  
  pointStart = (1.0, 1.0); pointEnd = (1.0, 1.0)
  
  count = 0
  for index in range(0, len(classLabels)):
    print count; count += 1
    
    if classLabels[index] == posLabel:
      delX = 0; delY = yStep;
    else:
      delX = xStep; delY = 0
    pointEnd = (pointEnd[0] - delX, pointEnd[1] - delY)
    
    if index + 1 == len(classLabels) or predStrengths[index + 1] != lastPre:
      area += (pointStart[1] + pointEnd[1]) * (pointStart[0] - pointEnd[0]) / 2
      ax.plot([pointStart[0], pointEnd[0]], [pointStart[1], pointEnd[1]], c='b')
      pointStart = pointEnd
      if index + 1 < len(classLabels):
        lastPre = predStrengths[index + 1]
      
  ax.plot([0, 1], [0, 1], 'b--')
  plt.xlabel('False Positive Rate'); plt.ylabel('True Positive Rate')
  plt.title('ROC curve, AUC:' + str(area))
  ax.axis([0, 1, 0, 1])
  plt.show()

# predStrengths = []
# classLabels = []
# for line in open("D:/worktmp/people/testres.txt"):
#   predStrengths.append(string.atof(line.split()[0]))
#   classLabels.append(line.split()[1])
# 
# sortedFile = open("D:/worktmp/people/sort2.txt",'w+')
# sortedIndicies = array(predStrengths).argsort()
# for index in sortedIndicies:
#   sortedFile.write('%.3f' % predStrengths[index] +'\t' + classLabels[index]+'\n') 


# testlst = [0.1,0.1,0.1,0.1,0.1,0.4,0.5,0.6]
# classLabels = [1,2,1,1,1,1,2,1]
# plotROC2(testlst, classLabels, 1)



predStrengths = []
classLabels = []
for line in open("D:/worktmp/people/sort2.txt"):
  predStrengths.append(line.split()[0])
  classLabels.append(line.split()[1])
  
numPosClas = sum(array(classLabels) == '2')
print numPosClas, len(classLabels) - numPosClas

m = 0;f = 0
count = 0
for line in open("D:/worktmp/people/sort2.txt"):
  count += 1;
  if count >= 10000: break
  if line.split()[1] == '2':
    f += 1
  else:
    m += 1
  
  
print numPosClas / float(len(classLabels))
print f/float(f+m)


# plotROC2('D:/worktmp/people/sort2.txt','1')

