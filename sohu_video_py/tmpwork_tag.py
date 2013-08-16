'''
Created on Jul 10, 2013

@author: Tekchen
'''
import sys
import string

res = ''
for line in open('ztmp_tag.txt'):
    line1 = line.split()[0]
    print line1[1:len(line1)]