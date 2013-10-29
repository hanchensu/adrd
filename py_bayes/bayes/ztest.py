'''
Created on Oct 28, 2013

@author: hanchensu
'''

from numpy import *
# x = array([[6,1],[3,8],[1,7]])
# sortIdx = x.argsort(axis=1)
# print sortIdx

sortedFile = open('D:/worktmp/people/test.txt','a')
for i in range(0,3):
  sortedFile.write('ads'+str(i)+'\n')
sortedFile.close()