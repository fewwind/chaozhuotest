package com.example.fewwind.chaozhuofirst.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fewwind on 18-2-6.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isFirstVisible = true;

    protected boolean isViewCreated;
    protected boolean isUIVisible;
    protected View mRootView;
    protected ViewGroup mContainer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        mContainer = container;
        if (getContentViewLayoutID() != 0) {
            return mRootView = inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewsAndEvents(view);
        isViewCreated = true;
        lazyLoad();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            if (isFirstVisible) {
                isFirstVisible = false;
                lazyLoad();
            }
            onVisibleChange(true);
        } else {
            isUIVisible = false;
            onVisibleChange(false);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents(View view);


    protected void onVisibleChange(boolean isVisible) {
    }

    protected void onFirstUserVisible() {

    }

    private void lazyLoad() {
        if (isViewCreated && isUIVisible) { //需要进行双重判断，避免因为setUserVisibleHint先于onViewCreaetd调用时，出现空指针
            onFirstUserVisible();  //进行初次可见时的加载
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String getHash() {
        return this.hashCode() + "";
    }
}
