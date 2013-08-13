'''
Created on Jul 10, 2013

@author: Tekchen
'''
import sys
import string

res = ''
for line in open('user_tags_level1 (3).txt'):
    print line.split()[0]  