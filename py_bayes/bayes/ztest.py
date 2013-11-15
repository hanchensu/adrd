'''
Created on Oct 28, 2013

@author: hanchensu
'''


import string
import sys 
res=[]
for line in open(sys.argv[1]):
    rate=string.atof(line.split()[0])
    gender=line.split()[1]
    res.append(rate)

outFile = open(sys.argv[2],'w+')
for x in sorted(res,key=lambda asd:asd[0],reverse = False):
  outFile.write(str(x[0])+'\t'+x[1]+'\n')