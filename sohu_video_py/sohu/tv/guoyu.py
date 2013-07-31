'''
Created on Jun 25, 2013

@author: Tekchen
'''
import codecs

fin = codecs.open('D:/worktmp/video/0624/actors', encoding='utf-8')
actors = {}
for line in fin:
    actors[line.split()[0]] = line.split()[1]
fin.close()

fin = codecs.open('D:/worktmp/video/0624/playcount', encoding='utf-8')
playcount = {}
for line in fin:
    playcount[line.split()[0]] = line.split()[1]
fin.close()

fin = codecs.open('D:/worktmp/video/0624/album.txt',encoding='utf-8')
fout = open('D:/worktmp/video/0624/album-playcount', 'w')
for line in fin:
    items = line.split('\t')

    aclist=[]
    for id in items[2].split(","):
        if actors.get(id) != None:
            aclist.append(actors.get(id))
    items[2] = ",".join(aclist)
    
    dirlist = []
    for id in items[7].split(","):
        if actors.get(id) != None:
            dirlist.append(actors.get(id))
    items[7] = ",".join(dirlist)
    
    items.insert(0,playcount.get(items[0]))
    
    lineout = '\t'.join(items[0:])
    fout.write(lineout)
fin.close()
fout.close()
