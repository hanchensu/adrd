#coding=utf-8
'''
Created on Jul 1, 2013

@author: Tekchen
'''

from sohu.tv.fore_cnt import *
from sohu.tv.fore_util import *

import fore_plot

import matplotlib.pyplot as plt
# import math

duration = 60
searchBefore = 5

index = 0

#查看平均搜索量和平均播放量的关系
start = 0
end = 8
for start in range(0,duration-1):
    for end in range(start+1,duration):
        trainx=[]
        trainy=[]
        
        for x in albums:
            search_cnt=[]
            play_cnt=[]
            datePlayBegin = x[2]
            dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
            
            for i in range(0, duration):
                search_cnt.append(searchCnt.get(tuple([x[1],deltaDay(dateSearchBegin,i)]),-1))
                play_cnt.append(playCnt.get(tuple([x[0],deltaDay(dateSearchBegin,i)]),-1))
            
            
            search_cnt = delVacant(0, search_cnt)
            search_cnt = search_cnt[searchBefore:]
            play_cnt = play_cnt[searchBefore:]
            
            trainx.append(myAvg(search_cnt[start:end]))
            trainy.append(myAvg(play_cnt[start:end]))
            
        print start,end
        fore_plot.draw(trainx,trainy ,1)

#         fore_plot.draw(search_cnt[5:],play_cnt[5:] ,1)
    
    
#     fig = plt.figure()
#     ax = fig.add_subplot(111)
#       
#     ax.plot(range(0,duration),regularize(play_cnt),color='m',linestyle='-',marker='')
#     ax.plot(range(0,duration),regularize(search_cnt),color='g',linestyle='-',marker='')
#     fore_plot.draw(regularize(search_cnt[5:]),regularize(play_cnt[5:]) ,1)
#     plt.show()
#         break








# coeff_lst=[]
# #make search_cnt
# for gap in range(10,20):
#     index = 0
#     train_xy = []
#     for x in album:
#         index = index + 1
#         searches = []
#         datePlayBegin = x[2]
#         dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
#         
#         for i in range(0, searchDuration):
#             searches.append(searchMap.get(tuple([x[1],deltaDay(dateSearchBegin,i)])))
#         
#         #log or not   
# #         for i in range(0, len(searches)):
# #             if searches[i] != None:
# #                 searches[i] = math.log(searches[i])
#                 
#     #     print x[1]+'___'+x[2]+':'
#     #     print searches
#         
#     #     #处理none
#     #     for i in range(0,len(searches)):
#     #         if searches[i] == None:
#     #             if i == 0:
#     #                 searches[i] = searches[i+1]
#     #             elif i == (len(searches) - 1):
#     #                 searches[i] = searches[i-1]
#     #             else:
#     #                 searches[i] = (searches[i-1] + searches[i+1])/2
#           
#         
#     #     before_all.append([avg(searches[0:5]),avg(searches[5:])]);
#         train_xy.append([avg(searches[0:gap]),avg(searches[0:gap+1])]);
#     #     #求log
#     #     logsearches = []  
#     #     for i in searches:
#     #         logsearches.append(math.log(i)) 
#     #     print logsearches
#     #     
#     #     #画第index剧的情况
#     #     if index == 11:
#     #         print x[1]+'___'+x[2]+':'
#     #         print searches
#     #         plot.draw(range(1,len(searches)+1), searches,10)
#     
#     
#     for index in range(0,len(train_xy)):
#         train_xy[index].append(index+1)
#      
#     xx=[tuple([x[0],x[2]]) for x in train_xy]
#     yy=[tuple([x[1],x[2]]) for x in train_xy]
#      
# #     print sorted(xx,key = lambda asd:asd[0],reverse = True)
# #     print sorted(yy,key = lambda asd:asd[0],reverse = True)
#      
#      
#     xa=[x[0] for x in train_xy]
#     ya=[x[1] for x in train_xy]
#      
#     coeff_lst.append(plot.draw(xa, ya,1))
#     
# # for x in coeff_lst:
# #     print x
#     
#     
#     
#     
# sum = 0
# for x in album:
#     index = index + 1
#     searches = []
#     datePlayBegin = x[2]
#     dateSearchBegin = deltaDay(datePlayBegin, -searchBefore)
#     for i in range(0, searchDuration):
#         searches.append(searchMap.get(tuple([x[1],deltaDay(dateSearchBegin,i)])))
#     
#     #log or not
# #     for i in range(0, len(searches)):
# #         if searches[i] != None:
# #             searches[i] = math.log(searches[i])
#     
#     init_value = avg(searches[0:10])
#     forecast_value = init_value;
#     steps = 5;
#     for step in range(0,steps):
#         forecast_value = coeff_lst[step][0] + coeff_lst[step][1]*forecast_value;
#     
#     #log or not    
# #     init = math.pow(math.e,init_value)
# #     real = math.pow(math.e,avg(searches[0:10+steps]))
# #     forecast = math.pow(math.e,forecast_value)
#     
#     init = init_value
#     real = avg(searches[0:10+steps])
#     forecast = forecast_value
#     
#     print init, real, forecast, (forecast-real)*1.0/min(forecast,real)
#     
#     delta = (forecast-real)*1.0/min(forecast,real)
#     sum+=abs(delta)
# 
# print sum / len(album)
    
