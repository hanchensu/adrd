<<<<<<< HEAD
'''
Created on 2013-6-2

@author: Tekchen
'''
from sessionlog.ttypes import AdInfoOperation
from thrift.protocol.TBinaryProtocol import TBinaryProtocol
from thrift.transport.TTransport import TMemoryBuffer
from thrift.TSerialization import serialize
x = AdInfoOperation()
x.adId='122'
x.yyId='123'
#asd

trans = TMemoryBuffer()
prot = TBinaryProtocol(trans)
x.write(prot)

byteArray = trans.getvalue()

print byteArray

y = AdInfoOperation()

trans2 = TMemoryBuffer(byteArray)
prot2 = TBinaryProtocol(trans2)
y.read(prot2)

print y.adId+' '+y.yyId

=======
'''
Created on 2013-6-2

@author: Tekchen
'''
from sessionlog.ttypes import AdInfoOperation
from thrift.protocol.TBinaryProtocol import TBinaryProtocol
from thrift.transport.TTransport import TMemoryBuffer
from thrift.TSerialization import serialize
x = AdInfoOperation()
x.adId='122'
x.yyId='123'
//test

trans = TMemoryBuffer()
prot = TBinaryProtocol(trans)
x.write(prot)

byteArray = trans.getvalue()

print byteArray

y = AdInfoOperation()

trans2 = TMemoryBuffer(byteArray)
prot2 = TBinaryProtocol(trans2)
y.read(prot2)

print y.adId+' '+y.yyId

>>>>>>> refs/remotes/sohu/master
print serialize(x)
