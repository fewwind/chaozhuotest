package com.example.fewwind.chaozhuofirst.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.adapter.BaseViewHolder;
import com.example.fewwind.chaozhuofirst.adapter.OnListClickListener;
import com.example.fewwind.chaozhuofirst.adapter.PageListAdapter;
import com.example.fewwind.chaozhuofirst.learn.ProxyTest;
import com.example.fewwind.chaozhuofirst.rxtest.observerlearn.AccountUser;
import com.example.fewwind.chaozhuofirst.rxtest.observerlearn.SubscriptionUser;
import com.example.fewwind.chaozhuofirst.ui.view.guaid.Controller;
import com.example.fewwind.chaozhuofirst.ui.view.guaid.GuaidLayout;
import com.example.fewwind.chaozhuofirst.ui.view.guaid.HightLight;
import com.example.fewwind.chaozhuofirst.ui.view.guaid.NewbieGuide;
import com.example.fewwind.chaozhuofirst.utils.DataUtil;
import com.example.fewwind.chaozhuofirst.utils.PreferencesUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvActivity extends AppCompatActivity implements OnListClickListener {

    private RecyclerView mRVList;
    private PageListAdapter mAdapterStyle;
    private List<String> mData = new ArrayList<>();
    private RadioGroup mRgType;
    private RadioButton mRbNormal;
    private RadioButton mRbIncognito;
    private RadioGroup mRgStyle;
    private RadioButton mRbCard;
    private RadioButton mRbList;
    private FloatingActionButton mFab;
    boolean isDay = true;
    LinearLayoutManager mLayoutManager;
    int currPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        for (int i = 0; i < 60; i++) {
            mData.add("我是新闻标题啊");
        }

        mRVList = (RecyclerView) findViewById(R.id.id_page_list);
        mAdapterStyle = new PageListAdapter(this, mData, R.layout.item_page_list);
        mRVList.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRVList.setAdapter(mAdapterStyle);
        mRVList.setVisibility(View.VISIBLE);
        mAdapterStyle.setClickListener(this);

        mRgType = (RadioGroup) findViewById(R.id.id_tab_type);
        mRbNormal = (RadioButton) findViewById(R.id.id_rb_normal);
        mRbIncognito = (RadioButton) findViewById(R.id.id_rb_incognito);
        mRgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_rb_normal) {
                    mRbNormal.setChecked(true);
                    mRbIncognito.setChecked(false);
                } else if (checkedId == R.id.id_rb_incognito) {
                    mRbNormal.setChecked(false);
                    mRbIncognito.setChecked(true);
                }
            }
        });
        mRgStyle = (RadioGroup) findViewById(R.id.id_tab_style);
        mRbCard = (RadioButton) findViewById(R.id.id_rb_style_card);
        mRbList = (RadioButton) findViewById(R.id.id_rb_style_list);
        mRgStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Logger.d("id=" + checkedId);
                if (checkedId == R.id.id_rb_style_card) {
                    mRbCard.setChecked(true);
                    mRbList.setChecked(false);
                } else if (checkedId == R.id.id_rb_style_list) {
                    mRbCard.setChecked(false);
                    mRbList.setChecked(true);
                }
            }
        });

        SubscriptionUser user = new SubscriptionUser();
        user.addUser(new AccountUser(1));
        user.addUser(new AccountUser(2));
        user.addUser(new AccountUser(3));

        user.sendMsg("今天提前放假");
        findViewById(R.id.id_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mData.add(3,"我是新增"+new Random().nextInt(8));
                //mAdapterStyle.notifyItemRangeInserted(3,1);
                if (isDay) {
                    mRgStyle.setBackgroundResource(R.drawable.bg_radio_button);
                } else {
                    mRgStyle.setBackgroundResource(R.drawable.bg_radio_button_night);
                }
                isDay = !isDay;
                mRgType.animate().alphaBy(1.0f).alpha(0f).setDuration(1000);
                mRVList.scrollToPosition(58);
            }
        });
        Controller controller = NewbieGuide.with(this).addHighLight(mRgStyle, HightLight.SHOWTYPE.ROUND_RECTANGLE, 72)
                .setLabel("show")
                .setLayoutRes(R.layout.guaid_layout)
                .alwaysShow(true).build();
        controller.show();
        GuaidLayout layout = controller.getGuaidLayout();
        int first = PreferencesUtils.getInt(this, "first", mLayoutManager.findFirstVisibleItemPosition());
        currPos = PreferencesUtils.getInt(this, "current", 0);
        mRVList.scrollToPosition(first);
        mAdapterStyle.setCurrentPagePos(currPos);
        mRVList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = mLayoutManager.findFirstVisibleItemPosition();
                for (int i = 0; i < mLayoutManager.getChildCount(); i++) {
                    View view = mLayoutManager.getChildAt(i);
                    if (i == 0) {
                        //view.setAlpha(0.3f);
                        view.setBackgroundResource(R.drawable.bg_card_drak);
                    } else {
                        view.setBackgroundResource(R.drawable.bg_card);
                        //view.setAlpha(1.0f);
                    }
                }
            }
        });

        Logger.v(DataUtil.getDisplay(1920, 1080) + "\n" + DataUtil.getDisplay(2048, 1536) + "\n" + DataUtil.getDisplay(18, 9));

        mRVList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                Logger.v("jaoijdoi");
            }
        });
        ProxyTest test = new ProxyTest();
        test.test();
    }


    public boolean isInstalled(String packageName) {
        PackageManager manager = this.getPackageManager();
        try {
            manager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void OnListClickLitener(View view, BaseViewHolder holder, int position) {
        switch (view.getId()) {
            case R.id.id_item_page_iv_delete:
                Logger.w("data=" + mData.get(position));
                mData.remove(position);
                mAdapterStyle.notifyItemRemoved(position);
                String ss = "/storge/emulate/0/Download/test.apk";
                String[] split = ss.split("/");
                break;
            case R.id.id_item_page_tv_title:
                currPos = position;
                mAdapterStyle.setCurrentPagePos(currPos);
                currPos = 66;
                Logger.d(currPos + "--" + mAdapterStyle.currentPagePos);
                mData.remove(position);
                mData.add(position, "大家搜集佛顶山");
                TextView textView = (TextView) view;
                //textView.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_check_no_color),null,null,null);
                //textView.setCompoundDrawablePadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8f,getResources().getDisplayMetrics()));

                textView.setText("111");
                mAdapterStyle.notifyDataSetChanged();
                PreferencesUtils.putInt(this, "current", position);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("onDestroy");
        mLayoutManager.findLastCompletelyVisibleItemPosition();
        PreferencesUtils.putInt(this, "first", mLayoutManager.findFirstCompletelyVisibleItemPosition());
    }
}
