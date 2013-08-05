'''
Created on 2013-6-2

@author: Tekchen
'''
from sessionlog.ttypes import AdInfoOperation
import base64
from thrift.protocol import TBinaryProtocol
from thrift.TSerialization import deserialize

def main():
    protocol_factory = TBinaryProtocol.TBinaryProtocolFactory
    base64_string = 'CwABAAAABTEyMzQ1AA=='
    print 'base64 string: '+base64_string # deserialize the string back into an object
    decodeString = base64.urlsafe_b64decode(base64_string)
    print decodeString
    x = AdInfoOperation()
    deserialize(x, decodeString, protocol_factory=protocol_factory())
    print 'x.adId:'+x.adId

if __name__ == '__main__':
    main()