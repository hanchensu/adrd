'''
Created on 2013-6-2

@author: Tekchen
'''
import base64

from thrift.protocol import TBinaryProtocol
from thrift.TSerialization import deserialize

from foo.ttypes import FooObj


def main():
    protocol_factory = TBinaryProtocol.TBinaryProtocolFactory
    base64_string = 'BAABQAul41P3ztkA'
    
    print 'base64 string: ', base64_string
    
    # deserialize the string back into an object
    foo_blob = base64.urlsafe_b64decode(base64_string)
    foo_obj = FooObj()
    deserialize(foo_obj, foo_blob, protocol_factory=protocol_factory())
    print 'foo_obj.bar: ', foo_obj.bar

if __name__ == '__main__':
    main()