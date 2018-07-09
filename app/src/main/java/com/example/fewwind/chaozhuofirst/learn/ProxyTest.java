package com.example.fewwind.chaozhuofirst.learn;

import com.example.fewwind.chaozhuofirst.learn.proxymode.MyInvocationHandler;
import com.example.fewwind.chaozhuofirst.learn.proxymode.Service;
import com.example.fewwind.chaozhuofirst.learn.proxymode.UserServiceIml;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fewwind on 18-4-25.
 */

public class ProxyTest {
    public void test() {
        final Service service = new UserServiceIml();
        MyInvocationHandler handler = new MyInvocationHandler(service);
        Service proxy = (Service) handler.getProxy();
//        proxy.remoteMethod();
        Service proxy2 = (Service) Proxy.newProxyInstance(Service.class.getClassLoader(), new Class<?>[]{Service.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return method.invoke(service, args);
            }
        });
        proxy2.remoteMethod();
    }
}
