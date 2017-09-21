package com.example.fewwind.chaozhuofirst.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.adapter.BaseViewHolder;
import com.example.fewwind.chaozhuofirst.adapter.OnListClickListener;
import com.example.fewwind.chaozhuofirst.adapter.PageListAdapter;
import com.example.fewwind.chaozhuofirst.rxtest.observerlearn.AccountUser;
import com.example.fewwind.chaozhuofirst.rxtest.observerlearn.SubscriptionUser;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        for (int i = 0; i < 8; i++) {
            mData.add("我是新闻标题啊，信不信由你啊");
        }

        mRVList =  (RecyclerView)findViewById(R.id.id_page_list);
        mAdapterStyle = new PageListAdapter(this,mData,R.layout.item_page_list);
        mRVList.setLayoutManager(new LinearLayoutManager(this));
        mRVList.setAdapter(mAdapterStyle);
        mRVList.setVisibility(View.VISIBLE);
        mAdapterStyle.setClickListener(this);

        findViewById(R.id.id_fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mData.add(3,"我是新增"+new Random().nextInt(8));
                mAdapterStyle.notifyItemRangeInserted(3,1);
            }
        });

        mRgType= (RadioGroup) findViewById(R.id.id_tab_type);
        mRbNormal = (RadioButton) findViewById(R.id.id_rb_normal);
        mRbIncognito = (RadioButton) findViewById(R.id.id_rb_incognito);
        mRgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_rb_normal){
                    mRbNormal.setChecked(true);
                    mRbIncognito.setChecked(false);
                } else if(checkedId == R.id.id_rb_incognito){
                    mRbNormal.setChecked(false);
                    mRbIncognito.setChecked(true);
                }
            }
        });
        mRgStyle= (RadioGroup) findViewById(R.id.id_tab_style);
        mRbCard = (RadioButton) findViewById(R.id.id_rb_style_card);
        mRbList = (RadioButton) findViewById(R.id.id_rb_style_list);
        mRgStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {

                Logger.d("id="+checkedId);
                if (checkedId == R.id.id_rb_style_card){
                    mRbCard.setChecked(true);
                    mRbList.setChecked(false);
                } else if(checkedId == R.id.id_rb_style_list){
                    mRbCard.setChecked(false);
                    mRbList.setChecked(true);
                }
            }
        });
        mRbList.setVisibility(View.GONE);

        SubscriptionUser user = new SubscriptionUser();
        user.addUser(new AccountUser(1));
        user.addUser(new AccountUser(2));
        user.addUser(new AccountUser(3));
        user.sendMsg("今天提前放假");

    }




    public  boolean isInstalled(String packageName) {
        PackageManager manager =this.getPackageManager();
        try {
            manager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override public void OnListClickLitener(View view, BaseViewHolder holder, int position) {
        switch (view.getId()){
            case R.id.id_item_page_iv_delete:
                Logger.w("data="+mData.get(position));
                mData.remove(position);
                mAdapterStyle.notifyItemRemoved(position);
                break;
            case R.id.id_item_page_tv_title:
                mAdapterStyle.setCurrentPagePos(position);
                mData.remove(position);
                mData.add(position,"大家搜集佛顶山");
                TextView textView = (TextView) view;
                //textView.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_check_no_color),null,null,null);
                //textView.setCompoundDrawablePadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8f,getResources().getDisplayMetrics()));

                textView.setText("zenmehuishi");
                mAdapterStyle.notifyDataSetChanged();
                break;
        }
    }
}
