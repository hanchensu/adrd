'''
Created on Jul 10, 2013

@author: Tekchen
'''
import sys
import string

errMap = {}
for line in open('errlog'):
    words = line.split('\001')[0]
    num = string.atoi(line.split('\001')[1])
#     print num
    for word in words.split(';'):
        word = word.strip()
        if(word.find(':') != -1):
            errMap[word] = errMap.get(word,0) + num

for k,v in errMap.items():
    print k,v


for x in sorted(errMap.iteritems(), key = lambda asd:asd[1],reverse = True):
    print x
