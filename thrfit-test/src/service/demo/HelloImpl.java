<<<<<<< HEAD
package service.demo;

import org.apache.thrift.TException;

import service.demo.Hello.Iface;

public class HelloImpl implements Iface {

	@Override
	public String helloString(String word) throws TException {
		System.out.println("get " + word);
		return "hello " + word;
	}

}
=======
package service.demo;

import org.apache.thrift.TException;

import service.demo.Hello.Iface;

public class HelloImpl implements Iface {

	@Override
	public String helloString(String word) throws TException {
		System.out.println("get " + word);
		return "hello " + word;
	}

}
>>>>>>> refs/remotes/sohu/master
