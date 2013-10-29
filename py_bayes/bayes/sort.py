'''
Created on Oct 28, 2013

@author: hanchensu
'''
import string
from numpy import *

predStrengths = []
classLabels = []
for line in open("D:/worktmp/people/fea500res.txt"):
  predStrengths.append(string.atof(line.split()[0]))
  classLabels.append(line.split()[1])
 
sortedFile = open("D:/worktmp/people/sort_fea500.txt",'w+')
sortedIndicies = array(predStrengths).argsort()
for index in sortedIndicies:
  sortedFile.write('%.3f' % predStrengths[index] +'\t' + classLabels[index]+'\n') 