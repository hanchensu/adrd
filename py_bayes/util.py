'''
Created on Oct 29, 2013

@author: hanchensu
'''
# [class,{col1:value1,...,colN:valueN}]
def line2Record(line, classIdx):
  res = []
  feas = {}
  for word in line.split():
    kv = word.split(':')
    if(len(kv) != 2): 
      continue
    if(kv[0] == classIdx):
      res.append(kv[1])
    else:  
      feas[kv[0]] = kv[1]
  res.append(feas)
  return res



def getLabel(line, classIdx):
  for word in line.split():
    kv = word.split(':')
    if(len(kv) != 2): 
      continue
    if(kv[0] == classIdx):
      return kv[1]