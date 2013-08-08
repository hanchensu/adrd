'''
Created on Jul 1, 2013

@author: Tekchen
'''

import datetime
import codecs
import os
import string
import plot

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

searchDuration = 40
searchBefore = 5

album = []; #[[id,name,date]] unicode:unicode:str
searchMap = {} #{(name,date):count} unicode:str:unicode


#make album list
fin = codecs.open('D:/worktmp/video/0731/forecastTeam_used.txt', encoding='utf-8')
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
searchDir="D:/worktmp/video/0731/search"
searchFiles = os.listdir(searchDir)

for filename in searchFiles:
    fin = codecs.open(searchDir+'/'+filename, encoding='utf-8')
    for line in fin:
#         print line.split('\t')[0],filename
#         print line.split('\t')[1]
        
        searchMap[line.split('\t')[0],filename] = string.atoi(line.split('\t')[1])


#make search_cnt
index = 0
for x in album:
    index = index + 1
    searches = []
    datePlayBegin = x[2]
    dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
    
    for i in range(0, searchDuration):
        searches.append(searchMap.get(tuple([x[1],deltaDay(dateSearchBegin,i)])))
    
    if index == 18:
        print x[1]+'___'+x[2]+':'
        print range(1,len(searches)+1)
        print searches
        plot.draw(range(1,len(searches)+1), searches,7)



