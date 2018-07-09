package com.example.fewwind.chaozhuofirst.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.fewwind.chaozhuofirst.MainActivity;
import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.adapter.baseadapter.CommonAdapter;
import com.example.fewwind.chaozhuofirst.adapter.baseadapter.base.ViewHolder;
import com.example.fewwind.chaozhuofirst.bean.LearnUITypeBwan;
import com.example.fewwind.chaozhuofirst.magicvp.StandardViewPagerActivity;
import com.example.fewwind.chaozhuofirst.ui.fragment.ScrollerFragment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {

    private static List<LearnUITypeBwan> mDatas = new ArrayList<>();
    private RecyclerView mRv;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRv = (RecyclerView) findViewById(R.id.id_splash_rv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRv.setAdapter(new CommonAdapter<LearnUITypeBwan>(this, R.layout.item_main, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final LearnUITypeBwan learnUITypeBwan, int position) {
                holder.setText(R.id.id_item_tv_main, learnUITypeBwan.clazz.getSimpleName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityFragment(learnUITypeBwan);
                    }
                });
            }
        });
    }

    private void startActivityFragment(LearnUITypeBwan bean) {
        Class<?> clazz = bean.clazz;
        if (bean.type == LearnUITypeBwan.Activity) {
            startActivity(new Intent(this, clazz));
        } else {
            container = (FrameLayout) findViewById(R.id.id_content_splash);
            try {
                Constructor<?> constructor = clazz.getConstructor();
                constructor.newInstance();
                Fragment frag = (Fragment) clazz.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.id_content_splash, frag).commit();
                mRv.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (container != null && container.isShown()) {
            container.setVisibility(View.GONE);
            mRv.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }

    static {
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Activity, MainActivity.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Activity, RvActivity.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Activity, RxActivity.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Activity, ViewActivity.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Activity, StandardViewPagerActivity.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Fragment, MainFragment.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Fragment, ScrollerFragment.class));
        mDatas.add(new LearnUITypeBwan(LearnUITypeBwan.Fragment, SecondFragment.class));

    }

}
