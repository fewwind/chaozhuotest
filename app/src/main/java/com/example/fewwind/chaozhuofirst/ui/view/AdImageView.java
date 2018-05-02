package com.example.fewwind.chaozhuofirst.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-12-7.
 */

public class AdImageView extends android.support.v7.widget.AppCompatImageView {

    private int mMinDx;
    private int mDx;
    public AdImageView(Context context) {
        this(context,null);
    }

    public AdImageView(Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
        Logger.v("sizechange " + mMinDx);
    }


    @Override protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth()*1.0f/drawable.getIntrinsicWidth()*drawable.getIntrinsicHeight());
        Logger.v(getDx() + "&&&"+h + "  --with-- " + getWidth() +"  **  "+drawable.getIntrinsicWidth()+"  **  "+drawable.getIntrinsicHeight());
        drawable.setBounds(0,0,w,h);
        canvas.save();
        canvas.translate(0,-getDx());
        super.onDraw(canvas);
        canvas.restore();

    }

    public void setDx(int dx){
        if (getDrawable() == null) return;
        mDx = dx - mMinDx;
        if (mDx <= 0) mDx = 0;
        if (mDx > getDrawable().getBounds().height() - mMinDx){
            mDx = getDrawable().getBounds().height() - mMinDx;
        }
        invalidate();
    }

    public float getDx() {
        return mDx;
    }
}
