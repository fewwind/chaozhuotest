package com.example.fewwind.chaozhuofirst.ui.view.guaid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import java.util.List;

/**
 * Created by fewwind on 17-9-12.
 */

public class GuaidLayout extends RelativeLayout {
    private int mBgColor = 0xb2000000;
    private Paint mPaint;
    private List<HightLight> mHightLights;

    public GuaidLayout(Context context) {
        this(context,null);
    }
    public GuaidLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuaidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
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
        if (mHightLights != null){
            for (HightLight hightLight:mHightLights) {
                RectF rectf = hightLight.getRectF();
                switch (hightLight.getType()){
                    case CIRCLE:
                        canvas.drawCircle(rectf.centerX(),rectf.centerY(),hightLight.getRadius(),mPaint);
                        break;
                    case OVAL:
                        canvas.drawOval(rectf,mPaint);
                        break;
                    case ROUND_RECTANGLE:
                        canvas.drawRoundRect(rectf,hightLight.getRound(),hightLight.getRound(),mPaint);
                        break;
                    case RECTANGLE:
                        default:
                            canvas.drawRect(rectf,mPaint);
                }
            }
        }
    }

    public void setHightLights(List<HightLight> hightlights){
        this.mHightLights = hightlights;
    }

    public void setBgColor(int color){
        this.mBgColor = color;
    }
}
