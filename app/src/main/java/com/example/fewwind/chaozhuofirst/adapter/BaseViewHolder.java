package com.example.fewwind.chaozhuofirst.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by fewwind on 17-8-22.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected SparseArray<View> mViews;
    protected View mItemView;
    protected OnListClickListener mListener;
    protected Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
        mContext = mItemView.getContext();
    }

    public View getView(int resId){
        View view = mViews.get(resId);
        if (view == null){
            view = mItemView.findViewById(resId);
            mViews.put(resId,view);
        }

        return view;
    }

    public void  setOnListener(OnListClickListener listener){
        mListener = listener;
    }

    public abstract void bindViewData(T data,int position);
}
