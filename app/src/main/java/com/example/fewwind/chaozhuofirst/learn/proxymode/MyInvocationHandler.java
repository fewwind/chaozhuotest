package com.example.fewwind.chaozhuofirst.learn.proxymode;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fewwind on 18-4-25.
 */

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.d("执行之前");
        Object result = method.invoke(target, args);
        Logger.e("执行完成");
        return result;
    }

    public Object getProxy() {
        target.getClass().getClassLoader();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(contextClassLoader, interfaces, this);
    }
}
