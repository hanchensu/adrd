'''
Created on Nov 8, 2013

@author: hanchensu
'''
import string
from numpy import *

index=1
numberFile = open("./sample-Oct-number",'w+')
for line in open("./sample-Oct"):
  numberFile.write(str(index)+'#'+line)
  index +=1; print index