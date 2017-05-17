package com.example.fewwind.chaozhuofirst.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.orhanobut.logger.Logger;

public class MyService extends Service {
    public MyService() {
    }


    @Override public void onCreate() {
        super.onCreate();
        Logger.d("服务 oncreat");
    }


    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        Logger.d("服务 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
