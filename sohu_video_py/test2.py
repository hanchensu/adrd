'''
Created on Jul 10, 2013

@author: Tekchen
'''
import sys
import string

res = ''
for line in open('mkeys.txt'):
    res += "mkey == '" + line[0:len(line)-1] + "' OR "
    
print res
    