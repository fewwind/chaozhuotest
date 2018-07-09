package com.example.fewwind.chaozhuofirst.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-5-3.
 */

public class RefreshRelayout extends LinearLayout {

    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    private TextView mTv;
    private int headerHeight = 168;

    public RefreshRelayout(Context context) {
        this(context, null);
    }

    public RefreshRelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        setOrientation(VERTICAL);
        mGestureDetector = new GestureDetector(context, new GestureDetectorImp());
        mTv = new TextView(context);
        mTv.setText("我会刷新");
        mTv.setGravity(Gravity.CENTER);
        mTv.setBackgroundColor(Color.GRAY);
        mTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight));
        addView(mTv, 0);
//        scrollBy(0, headerHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Logger.w("onMove = "+getScrollY());
                if (getScrollY() <= -headerHeight) {
                    return true;
                }
                return mGestureDetector.onTouchEvent(event);
            case MotionEvent.ACTION_UP:
//                reset(0, 0);
                resetMy(0, 0);
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
//            measureChild(view,widthMeasureSpec,heightMeasureSpec);
        }
        setMeasuredDimension(getMeasuredWidth(),getChildAt(1).getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int headerW = mTv.getMeasuredWidth();
            int headerH = mTv.getMeasuredHeight();
            mTv.layout(0, -headerHeight, headerW, 0);
            View childAt = getChildAt(1);
            int viewW = childAt.getMeasuredWidth();
            int viewH = childAt.getMeasuredHeight();
            childAt.layout(0, 0, viewW, viewH);
        }
    }

    private class GestureDetectorImp implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.5) / 2);
            Logger.e(getScrollY() + "onscroll = " + mScroller.getFinalY());
//            beginScroll(0, disY);
            scrollBy(0, disY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void beginScroll(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }

    private void beginScrollMy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy);
        invalidate();
    }


    private void reset(int x, int y) {
        int dx = x - mScroller.getFinalX();
        int dy = y - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    private void resetMy(int x, int y) {
        int dx = x - getScrollX();
        int dy = y - getScrollY();
        beginScrollMy(dx, dy);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Logger.w("computeScroll = " + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }
}
