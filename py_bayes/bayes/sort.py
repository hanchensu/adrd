'''
Created on Oct 28, 2013

@author: hanchensu
'''
import string
from numpy import *

predStrengths = []
classLabels = []
for line in open("../bayesTestRes"):
  predStrengths.append(string.atof(line.split()[0]))
  classLabels.append(line.split()[1])
 
sortedFile = open("../sortTestRes",'w+')
sortedIndicies = array(predStrengths).argsort()
for index in sortedIndicies:
  sortedFile.write(str(predStrengths[index]) +'\t' + classLabels[index]+'\n') 