package com.example.fewwind.chaozhuofirst.rxtest;

/**
 * Created by fewwind on 17-6-26.
 */

public interface ObserverF <T>{
    void Completed();
    void onError(Throwable t);
    void onNext(T t);
}
