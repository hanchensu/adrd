'''
Created on Aug 16, 2013

@author: hanchensu
'''

import string
import codecs
import datetime
import os
import mysql.connector

albums = []; #[[id,name,date]] unicode:unicode:str
searchCnt = {} #{(name,date):count} unicode:str:unicode
playCnt = {} #{(id,date):count} unicode:str:unicode

#make album
fin = codecs.open('D:/worktmp/video/0731/forecastTeam_used.txt', encoding='utf-8')
for line in fin:
    x = line.split()[:3]
    x[2] = datetime.datetime.strftime(datetime.datetime.strptime(x[2],'%Y/%m/%d'),'%Y%m%d')
    albums.append(x)
fin.close()


#make searchCnt
searchDir="D:/worktmp/video/0731/search"
searchFiles = os.listdir(searchDir)
for filename in searchFiles:
    fin = codecs.open(searchDir+'/'+filename, encoding='utf-8')
    for line in fin:
        searchCnt[line.split('\t')[0],filename] = string.atoi(line.split('\t')[1])


#make playCnt    
cnx = mysql.connector.connect(user='forecast',password='tvforecast',host='192.168.4.199',database='forecast')
cursor = cnx.cursor()
for id in [x[0] for x in albums]:
    query = "SELECT * FROM DM_PLAYLIST_VV WHERE PLAYLIST_ID = "+id
    cursor.execute(query)
    for (a,b,c) in cursor:
        datestr = datetime.datetime.strftime(b,'%Y%m%d')
        playCnt[str(a),datestr] = c
cnx.close()


