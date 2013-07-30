'''
Created on Jun 24, 2013

@author: Tekchen
'''
import codecs

fin = codecs.open('D:/worktmp/video/0624/actors', encoding='utf-8')
x = {}
for line in fin:
    x[line.split()[0]] = line.split()[1]
fin.close()

fin = codecs.open('D:/worktmp/video/0624/joinres', encoding='utf-8')
fout = open('D:/worktmp/video/0624/album', 'w')
for line in fin:
    
#     print line.split('\t')
#     break
#     fout.write('\t'.join(line.split()[1:-1])+'\n')
    
    items = line.split()
    aclist = []
    for id in line.split()[3].split(","):
        if x.get(id) != None:
            aclist.append(x.get(id))
    items[3] = ",".join(aclist)
     
    dirlist = []
    for id in line.split()[9].split(","):
        if x.get(id) != None:
            dirlist.append(x.get(id))
    items[9] = ",".join(dirlist)
     
    fout.write('\t'.join(items[0:])+'\n')
   
fin.close()
fout.close()