package com.example.fewwind.chaozhuofirst.rxtest.observerlearn;

import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-9-19.
 */

public class AccountUser implements User {

    public  int id;

    @Override public void receiveMsg(String msg) {
        Logger.i("收到消息"+msg);
    }

    public AccountUser(int id) {
        this.id = id;
    }
}
