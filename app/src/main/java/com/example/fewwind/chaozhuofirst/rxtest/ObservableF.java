package com.example.fewwind.chaozhuofirst.rxtest;

/**
 * Created by fewwind on 17-6-26.
 */

public class ObservableF<T> {

    OnSubscribeF<T> onSubscribeF = null;

    private ObservableF(OnSubscribeF<T> subscribeF){
        this.onSubscribeF = subscribeF;
    }

    public static <T> ObservableF<T> creat(OnSubscribeF<T> onSubscribeF){
        return new ObservableF<>(onSubscribeF);
    }

    public void subscribe(SubscriberF<T> subscriberF){
        subscriberF.onStart();
        onSubscribeF.call(subscriberF);
    }



    public interface OnSubscribeF<T>{
        void call(SubscriberF<? super T> subscriber);
    }
}
