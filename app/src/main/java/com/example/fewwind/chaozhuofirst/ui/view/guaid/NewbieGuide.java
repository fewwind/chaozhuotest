package com.example.fewwind.chaozhuofirst.ui.view.guaid;

import android.app.Activity;

/**
 * Created by fewwind on 17-9-12.
 */

public class NewbieGuide {
    public static final String TAG = "NewbieGuide";

    /**
     * 成功显示标示
     */
    public static final int SUCCESS = 1;

    /**
     * 显示失败标示，即已经显示过一次
     */
    public static final int FAILED = -1;

    /**
     * 新手指引入口
     *
     * @param activity activity
     * @return builder对象，设置参数
     */
    public static Builder with(Activity activity) {
        return new Builder(activity);
    }
}
