# coding=utf-8
import numpy
'''
作者:Jairus Chan
程序:多项式曲线拟合算法
'''

matA = [[1,1,1,1],[1,1,1,1],[1,1,1,1],[1,1,1,1]]

matB = [1,2,3,4]

matA=numpy.array(matA)
matB=numpy.array(matB)

matAA=numpy.linalg.solve(matA,matB)