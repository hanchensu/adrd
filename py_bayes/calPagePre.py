'''
Created on Nov 8, 2013

@author: hanchensu
'''
import string
import sys
pre={}

for line in open(sys.argv[1]):
  pageid=line.split()[0]
  M=string.atoi(line.split()[1].split(",")[0].split(":")[1])
  F=string.atoi(line.split()[1].split(",")[1].split(":")[1])
  if M>0 and F>0 and M+F > 200:
    pre[pageid] = float(F)/M

outFile = open(sys.argv[2],'w+')
for key,value in pre:
  outFile.write(key+'\t'+value+'\n') 