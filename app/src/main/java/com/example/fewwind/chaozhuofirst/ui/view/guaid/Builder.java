package com.example.fewwind.chaozhuofirst.ui.view.guaid;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 17-9-12.
 */

public class Builder {
    private Activity mActivity;
    private List<HightLight> mHightLights = new ArrayList<>();
    private OnGuideChangedListener mChangeListener;
    private boolean isAnyWhereCancle = true;
    private int mBgColor;
    private String label;
    private boolean isAlwaysShow;
    private int layoutResId;
    private int [] viewIds;


    public Builder(Activity activity) {
        this.mActivity = activity;
    }


    /**
     * 添加需要高亮的view,默认高亮类型为矩形
     */
    public Builder addHighLight(View view) {
        return addHighLight(view, HightLight.SHOWTYPE.RECTANGLE, 0);
    }


    /**
     * 添加需要高亮的view
     *
     * @param view 需要高亮的view
     * @param type 高亮类型：圆形，椭圆，矩形，圆角矩形
     * @return builder
     */
    public Builder addHighLight(View view, HightLight.SHOWTYPE type) {
        return addHighLight(view, type, 0);
    }


    /**
     * 添加需要高亮的view
     *
     * @param view 需要高亮的view
     * @param type 高亮类型：圆形，椭圆，矩形，圆角矩形
     * @param round 圆角尺寸，单位dp
     * @return builder
     */
    public Builder addHighLight(View view, HightLight.SHOWTYPE type, int round) {
        HightLight highLight = new HightLight(view, type);
        if (round > 0) {
            highLight.setRound(round);
        }
        mHightLights.add(highLight);
        return this;
    }


    public Builder addHighLight(List<HightLight> list) {
        this.mHightLights.addAll(list);
        return this;
    }


    /**
     * 引导层背景色
     */
    public Builder setBackgroundColor(int color) {
        mBgColor = color;
        return this;
    }


    /**
     * 点击任意区域是否隐藏引导层，默认true
     */
    public Builder setEveryWhereCancelable(boolean cancelable) {
        isAnyWhereCancle= cancelable;
        return this;
    }


    /**
     * 设置引导层隐藏，显示监听
     */
    public Builder setOnGuideChangedListener(OnGuideChangedListener listener) {
        mChangeListener = listener;
        return this;
    }


    /**
     * 设置引导层的辨识名，必须设置项，否则报错
     */
    public Builder setLabel(String label) {
        this.label = label;
        return this;
    }


    /**
     * 是否总是显示引导层
     */
    public Builder alwaysShow(boolean b) {
        this.isAlwaysShow = b;
        return this;
    }


    /**
     * 设置引导层控件布局
     *
     * @param resId 布局 id
     * @param id 需要设置点击隐藏引导层的view id
     * @return builder
     */
    public Builder setLayoutRes(int resId, int... id) {
        this.layoutResId = resId;
        viewIds = id;
        return this;
    }


    /**
     * 构建引导层controller
     *
     * @return controller
     */
    public Controller build() {
        if (TextUtils.isEmpty(label)) {
            throw new IllegalArgumentException("缺少必要参数：label,通过setLabel()方法设置");
        }
        return new Controller(this);
    }


    /**
     * 构建引导层controller并直接显示引导层
     *
     * @return controller
     */
    public Controller show() {
        if (TextUtils.isEmpty(label)) {
            throw new IllegalArgumentException("缺少必要参数：label,通过setLabel()方法设置");
        }
        Controller controller = new Controller(this);
        controller.show();
        return controller;
    }


    int getLayoutResId() {
        return layoutResId;
    }


    int[] getViewIds() {
        return viewIds;
    }


    boolean isAlwaysShow() {
        return isAlwaysShow;
    }


    String getLabel() {
        return label;
    }


    Activity getActivity() {
        return mActivity;
    }


    List<HightLight> getList() {
        return mHightLights;
    }


    OnGuideChangedListener getOnGuideChangedListener() {
        return mChangeListener;
    }


    boolean isEveryWhereCancelable() {
        return isAnyWhereCancle;
    }


    int getBackgroundColor() {
        return mBgColor;
    }
}
