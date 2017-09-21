package com.example.fewwind.chaozhuofirst.rxtest.observerlearn;

import java.util.ArrayList;

/**
 * Created by fewwind on 17-9-19.
 */

public class SubscriptionUser implements Subscription {

    private ArrayList<AccountUser> mUserList ;


    public SubscriptionUser() {
        this.mUserList = new ArrayList<>();
    }


    @Override public void addUser(AccountUser id) {
        if (mUserList !=null){
            mUserList.add(id);
        }
    }


    @Override public void removeUser(AccountUser id) {
        if (mUserList!=null && mUserList.contains(id)){
            mUserList.remove(id);
        }
    }


    @Override public void sendMsg(String msg) {
        if (mUserList != null){
            for (AccountUser id:mUserList) {
                id.receiveMsg(msg);
            }
        }
    }
}
