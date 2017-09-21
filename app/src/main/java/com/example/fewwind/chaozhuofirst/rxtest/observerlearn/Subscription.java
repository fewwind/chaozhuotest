package com.example.fewwind.chaozhuofirst.rxtest.observerlearn;

/**
 * Created by fewwind on 17-9-19.
 */

public interface Subscription {
    void addUser(AccountUser id);
    void removeUser(AccountUser id);
    void sendMsg(String msg);
}
