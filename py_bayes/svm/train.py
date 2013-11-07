'''
Created on Nov 1, 2013

@author: hanchensu
'''
from numpy import *
import numpy as np

def smoSimple(dataMatIn, classLabels, C, toler, maxIter):
  dataMatrix = mat(dataMatIn); labelMat = mat(classLabels).transpose()
  b = 0; m,n = shape(dataMatrix)
  
matrix = mat([[1,2],[3,4],[5,6]])
m,n= shape(matrix)

matA = mat([[1,2],[2,3],[5,6]])
matB = mat([1,2,3]).transpose()
print matA
print matB
print multiply(matA,matB)  

# x1 = np.arange(9.0).reshape((3, 3))
# x2 = np.arange(3.0)
# print x1
# print x2
# print np.multiply(x1, x2)