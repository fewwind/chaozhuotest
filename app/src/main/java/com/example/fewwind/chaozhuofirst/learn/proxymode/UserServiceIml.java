package com.example.fewwind.chaozhuofirst.learn.proxymode;

/**
 * Created by fewwind on 18-4-25.
 */

public class UserServiceIml implements Service {
    @Override
    public void remoteMethod() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                com.orhanobut.logger.Logger.e("实现类 调用远程方法");
            }
        }.start();

    }
}
