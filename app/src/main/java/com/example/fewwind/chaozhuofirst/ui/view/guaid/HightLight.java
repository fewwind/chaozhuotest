package com.example.fewwind.chaozhuofirst.ui.view.guaid;

import android.graphics.RectF;
import android.view.View;

/**
 * Created by fewwind on 17-9-12.
 */

public class HightLight {
    private View mHole;
    private SHOWTYPE  mType;
    private int round;


    public HightLight(View mHole,SHOWTYPE mType) {
        this.mType = mType;
        this.mHole = mHole;
    }


    public int getRadius(){
       return mHole!= null?Math.max(mHole.getWidth()/2,mHole.getHeight()/2):0;
    }


    public SHOWTYPE getType() {
        return mType;
    }


    public void setType(SHOWTYPE mType) {
        this.mType = mType;
    }


    public int getRound() {
        return round;
    }


    public void setRound(int round) {
        this.round = round;
    }


    public RectF getRectF(){
        RectF rectF = new RectF();
        if (mHole != null){
            int[] location = new int[2];
            mHole.getLocationOnScreen(location);
            rectF.left = location[0];
            rectF.top= location[1];
            rectF.right = location[0]+mHole.getWidth();
            rectF.bottom = location[1]+mHole.getHeight();
        }


        return rectF;
    }

    public static enum SHOWTYPE{
        CIRCLE,
        RECTANGLE,
        OVAL,
        ROUND_RECTANGLE
    }
}
