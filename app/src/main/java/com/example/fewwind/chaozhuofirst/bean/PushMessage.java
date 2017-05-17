
package com.example.fewwind.chaozhuofirst.bean;

import android.graphics.Bitmap;

/**
 * Created by xianeng on 16-8-17.
 */
public class PushMessage {
    public int type;
    public String message;
    public String title;
    public String url;
    public String imageurl;
    public Bitmap bitmap;
    public int version;
    public long starttime;
    public String iconurl;


    public PushMessage(long starttime, String message) {
        this.starttime = starttime;
        this.message = message;
    }


    public PushMessage(long starttime, String message, int version) {
        this.starttime = starttime;
        this.message = message;
        this.version = version;
    }


    @Override public String toString() {
        return "PushMessage{" +
            "message='" + message + '\'' +
            '}';
    }
}
