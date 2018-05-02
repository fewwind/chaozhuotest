package com.example.fewwind.chaozhuofirst.ui.view.guaid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-11-2.
 */

public class GuaidLayout2 extends RelativeLayout {

    private int mBgColor = 0xb2000000;
    private Paint mPaint;
    public View  mLightView;

    public GuaidLayout2(Context context) {
        this(context,null);
    }
    public GuaidLayout2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public GuaidLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private  void  init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(xfermode);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        setWillNotDraw(false);//viewgroup默认设置为true。会导致ondraw方法不执行
        setClickable(true);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        Logger.w("yang ondraw方法");
        if (mLightView != null){
            canvas.drawCircle(mLightView.getX()/2,mLightView.getHeight()/2,96,mPaint);
        }
    }
}
