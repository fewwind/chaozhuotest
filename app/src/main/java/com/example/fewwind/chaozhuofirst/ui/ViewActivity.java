package com.example.fewwind.chaozhuofirst.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.base.BaseActivity;
import com.example.fewwind.chaozhuofirst.utils.ToasF;
import com.orhanobut.logger.Logger;
import java.util.Random;

public class ViewActivity extends BaseActivity {

    //@Bind(R.id.id_view_iv) Button mIv;
    Button mIv;

    @Override public int getLayoutId() {
        return R.layout.activity_view;
    }



    @Override public void initView() {
        mIv = (Button) findViewById(R.id.id_view_iv);
        GestureDetector.SimpleOnGestureListener listener =  new GestureDetector.SimpleOnGestureListener(){
            @Override public boolean onDoubleTap(MotionEvent e) {
                ToasF.show("双击666");
                return super.onDoubleTap(e);
            }


            @Override public boolean onSingleTapConfirmed(MotionEvent e) {
                Logger.i("单机");
                return super.onSingleTapConfirmed(e);
            }


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        Logger.e("哈哈");
        final GestureDetector detector = new GestureDetector(this,listener);
        mIv.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {

                 detector.onTouchEvent(event);
                return true;
            }
        });
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ToasF.show("="+new Random().nextInt());
                Logger.w("点击");
            }
        });
    }


    @Override public void initData() {

    }

}
