'''
Created on Jul 10, 2013

@author: Tekchen
'''
from org.apache.pig.scripting import *
P = Pig.compile("""a = Load 'xxx' as (a:int,b:int); describe a;""")
bind = P.bind()
stats = bind.runSingle()