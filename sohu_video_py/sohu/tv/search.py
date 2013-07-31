'''
Created on Jun 24, 2013

@author: Tekchen
'''
import codecs
import string

fin = codecs.open('D:/worktmp/video/0731/forecastTeam_used.txt',encoding='utf-8')
fout = open('D:/worktmp/video/0731/sql', 'w')
 
rowNum = 0
sql = ""
for line in fin:
    rowNum = rowNum + 1
    if rowNum %4 == 1:
        sql += "ALBUM_ID = " + line[0:len(line)-2] + " OR "
 
print sql
