package com.example.fewwind.chaozhuofirst.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import com.example.fewwind.chaozhuofirst.MainActivity;
import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.bean.FileObject;
import com.example.fewwind.chaozhuofirst.utils.RxBus;
import com.orhanobut.logger.Logger;
import rx.functions.Action1;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RxBus.getInstance().toObservable(FileObject.class).subscribe(new Action1<FileObject>() {
            @Override public void call(FileObject fileObject) {
                Logger.w("收到bus 事件--"+fileObject.toString());
            }
        });

        new Thread(new Runnable() {
            @Override public void run() {
                Looper.prepare();

                Handler handler = new Handler(){
                    @Override public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Logger.w("收到msg="+msg.what);
                    }
                };
                handler.sendEmptyMessage(1);
                Looper.loop();
            }
        }).start();
    }


    @Override public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
