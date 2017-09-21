package com.example.fewwind.chaozhuofirst.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by fewwind on 17-7-10.
 */

public abstract class BaseActivity extends AppCompatActivity{



    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initData();

}
