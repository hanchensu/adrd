'''
Created on Jun 24, 2013

@author: Tekchen
'''
import codecs
import string

fin = codecs.open('D:/worktmp/video/0628/201209',encoding='utf-8')
fout = open('D:/worktmp/video/0628/search-count-09', 'w')
searchCnt={}
for line in fin:
    key=line.split("\t")[0]
    value=line.split("\t")[1]
    if(key in searchCnt):
        searchCnt[key] += string.atoi(value);
    else:
        searchCnt[key] = string.atoi(value);
    #print key+":"+value
fin.close()

for key,value in searchCnt.items():
    print key+'\t'+str(value)
    fout.write(key+'\t'+str(value)+'\n')
fout.close()
