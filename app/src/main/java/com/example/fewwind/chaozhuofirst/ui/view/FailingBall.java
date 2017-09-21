package com.example.fewwind.chaozhuofirst.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-7-11.
 */

public class FailingBall extends LinearLayout{

    public FailingBall(Context context) {
        super(context);
    }


    public FailingBall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FailingBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Logger.w("显示改变***"+visibility);
    }
}
