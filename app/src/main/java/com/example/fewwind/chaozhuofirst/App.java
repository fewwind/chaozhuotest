package com.example.fewwind.chaozhuofirst;

import android.app.Application;
import android.content.Intent;
import com.example.fewwind.chaozhuofirst.service.MyService;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-4-18.
 */

public class App extends Application {

    public static App mApp;

    @Override public void onCreate() {
        super.onCreate();
        mApp = this;
        Logger.init("First").setLogLevel(LogLevel.FULL);
        startService(new Intent(this, MyService.class));
    }

}
