#coding=utf-8
'''
Created on Jul 1, 2013

@author: Tekchen
'''

import datetime
import codecs
import os
import string
import plot
import math

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

searchDuration = 60
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

coeff_lst=[]
#make search_cnt
for gap in range(10,20):
    index = 0
    train_xy = []
    for x in album:
        index = index + 1
        searches = []
        datePlayBegin = x[2]
        dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
        
        for i in range(0, searchDuration):
            searches.append(searchMap.get(tuple([x[1],deltaDay(dateSearchBegin,i)])))
        
        #log or not   
#         for i in range(0, len(searches)):
#             if searches[i] != None:
#                 searches[i] = math.log(searches[i])
                
    #     print x[1]+'___'+x[2]+':'
    #     print searches
        
    #     #处理none
    #     for i in range(0,len(searches)):
    #         if searches[i] == None:
    #             if i == 0:
    #                 searches[i] = searches[i+1]
    #             elif i == (len(searches) - 1):
    #                 searches[i] = searches[i-1]
    #             else:
    #                 searches[i] = (searches[i-1] + searches[i+1])/2
          
        
    #     before_all.append([avg(searches[0:5]),avg(searches[5:])]);
        train_xy.append([avg(searches[0:gap]),avg(searches[0:gap+1])]);
    #     #求log
    #     logsearches = []  
    #     for i in searches:
    #         logsearches.append(math.log(i)) 
    #     print logsearches
    #     
    #     #画第index剧的情况
    #     if index == 11:
    #         print x[1]+'___'+x[2]+':'
    #         print searches
    #         plot.draw(range(1,len(searches)+1), searches,10)
    
    
    for index in range(0,len(train_xy)):
        train_xy[index].append(index+1)
     
    xx=[tuple([x[0],x[2]]) for x in train_xy]
    yy=[tuple([x[1],x[2]]) for x in train_xy]
     
#     print sorted(xx,key = lambda asd:asd[0],reverse = True)
#     print sorted(yy,key = lambda asd:asd[0],reverse = True)
     
     
    xa=[x[0] for x in train_xy]
    ya=[x[1] for x in train_xy]
     
    coeff_lst.append(plot.draw(xa, ya,1))
    
# for x in coeff_lst:
#     print x
    
    
    
    
sum = 0
for x in album:
    index = index + 1
    searches = []
    datePlayBegin = x[2]
    dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
    for i in range(0, searchDuration):
        searches.append(searchMap.get(tuple([x[1],deltaDay(dateSearchBegin,i)])))
    
    #log or not
#     for i in range(0, len(searches)):
#         if searches[i] != None:
#             searches[i] = math.log(searches[i])
    
    init_value = avg(searches[0:10])
    forecast_value = init_value;
    steps = 5;
    for step in range(0,steps):
        forecast_value = coeff_lst[step][0] + coeff_lst[step][1]*forecast_value;
    
    #log or not    
#     init = math.pow(math.e,init_value)
#     real = math.pow(math.e,avg(searches[0:10+steps]))
#     forecast = math.pow(math.e,forecast_value)
    
    init = init_value
    real = avg(searches[0:10+steps])
    forecast = forecast_value
    
    print init, real, forecast, (forecast-real)*1.0/min(forecast,real)
    
    delta = (forecast-real)*1.0/min(forecast,real)
    sum+=abs(delta)

print sum / len(album)
    
