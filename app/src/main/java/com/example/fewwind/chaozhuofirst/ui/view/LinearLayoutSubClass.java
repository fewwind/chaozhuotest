package com.example.fewwind.chaozhuofirst.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by fewwind on 18-5-3.
 */
public class LinearLayoutSubClass extends LinearLayout {
    private Scroller mScroller;
    private boolean flag = true;
    private int offsetY;
    private int duration;

    public LinearLayoutSubClass(Context context) {
        super(context);
    }

    public LinearLayoutSubClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        duration = 10000;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), offsetY);
            invalidate();
        }
    }

    public void beginScroll() {
        if (flag) {
            offsetY = -100;
            int startX = -300;
            int startY = -90;
            int dx = -500;
            int dy = 0;
            mScroller.startScroll(startX, startY, dx, dy, duration);
            flag = false;
        } else {
            offsetY = 0;
            int startX = mScroller.getCurrX();
            int startY = mScroller.getCurrX();
            int dx = -startX;
            int dy = 0;
            mScroller.startScroll(startX, startY, dx, dy, duration);
            flag = true;
        }
        invalidate();
    }
}