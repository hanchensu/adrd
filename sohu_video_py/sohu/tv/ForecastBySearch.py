'''
Created on Jul 1, 2013

@author: Tekchen
'''

import datetime
import codecs
import os
import mysql.connector
import string

def deltaDay(date,delta):
    now = datetime.datetime.strptime(date,'%Y%m%d')
    delta = datetime.timedelta(days=delta)
    n_days = now + delta
    return datetime.datetime.strftime(n_days,'%Y%m%d')

def avg(lst):
    r=0.0
    for i in lst:
        if i is not None:
            r+=i
    result=r/len(lst)
    return round(result,3)
#     print round(result,3)

interval_search = 3
interval_count = 3
interval_month = 30
album = []; #[[id,name,date]] unicode:unicode:str
searchMap = {} #{(name,date):count} unicode:str:unicode
countMap = {} #{(id,date):count} str:str:int
search_count = [] #[name,[search],[count]] 

#make album list
fin = codecs.open('D:/worktmp/video/0701/album_used2.txt', encoding='GBK')
for line in fin:
    x = line.split()[:3]
    x[2] = datetime.datetime.strftime(datetime.datetime.strptime(x[2],'%Y/%m/%d'),'%Y%m%d')
    album.append(x)
fin.close()

# for x in album:
#     print x[1],x[2]
# for x in album:
#     print x[0].__class__
#     print x[1].__class__
#     print x[2].__class__

#make search map
searchDir="D:/worktmp/video/0701/search"
searchFiles = os.listdir(searchDir)

for filename in searchFiles:
    fin = codecs.open(searchDir+'/'+filename, encoding='utf-8')
    for line in fin:
#         print line.split('\t')[0],filename
#         print line.split('\t')[1]
        
        searchMap[line.split('\t')[0],filename] = string.atoi(line.split('\t')[1])

# for key,value in searchMap.items():
#     print key[0].__class__,key[1].__class__
#     print value.__class__
#     break

#make count map
cnx = mysql.connector.connect(user='forecast',password='tvforecast',host='192.168.4.199',database='forecast')
cursor = cnx.cursor()
for id in [x[0] for x in album]:
    query = "SELECT * FROM vrs_play_count_album WHERE tv_count != 0 AND tv_alumn_id = "+id
    cursor.execute(query)
    for (a,b,c,d) in cursor:
        datestr = datetime.datetime.strftime(d,'%Y%m%d')
        countMap[str(a),datestr] = b
cnx.close()

# for key,value in countMap.items():
#     print key[0].__class__,key[1].__class__
#     print value.__class__
#     break

#make search_cnt
for x in album:
    srh = []
    count = []
    date = x[2]
    date1M = deltaDay(date, -interval_month)
    for i in range(0,interval_count):
        count.append(countMap.get(tuple([x[0],deltaDay(date, i)])))
    for i in range(-1*interval_search + 1,1):
        srh.append(searchMap.get(tuple([x[1],deltaDay(date1M,i)])))
    search_count.append([x[1],x[2],srh,count])


for x in search_count:
#     print x[0],avg(x[1]),avg(x[2])
    print x[0],x[1]
    print 'srh:',x[2],avg(x[2])
    print 'cnt:',x[3],avg(x[3])
#     
for x in search_count:
    print x[0],x[1],avg(x[2]),avg(x[3])



