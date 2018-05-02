package com.example.fewwind.chaozhuofirst.ui;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fewwind.chaozhuofirst.R;

/**
 * Created by fewwind on 17-10-11.
 */

public class EllipseTipView extends LinearLayout {

    private CircleImageView mCircleView;
    private TextView mTvContent;
    private Context mContext;
    public EllipseTipView(Context context) {
        this(context,null);
    }
    public EllipseTipView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }
    public EllipseTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_ellipse_tip,this);
        mCircleView = (CircleImageView) findViewById(R.id.circle_iv);
        mTvContent = (TextView)findViewById(R.id.circle_tv);
    }

    public void setShow(int resId,String name){
        if (resId > 0)
        mCircleView.setImageResource(resId);
        String content = String.format(mContext.getString(R.string.tip_content),name);
        ForegroundColorSpan fontColor = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent));
        int start = content.indexOf(name);
        int end = start + name.length();
        SpannableStringBuilder mSpannableBuilder = new SpannableStringBuilder(content);
        mSpannableBuilder.setSpan(fontColor, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvContent.setText(mSpannableBuilder);
    }
}
