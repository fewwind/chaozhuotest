package com.example.fewwind.chaozhuofirst.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 17-8-29.
 */

public abstract class SuperBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected final Context context;
    protected final LayoutInflater mLayoutInflater;
    protected List<T> dataList = new ArrayList<>();
    protected int layoutId;
    private OnListClickListener mListener;

    public SuperBaseAdapter(Context activity, List<T> dataList ,int layoutId) {
        this.context = activity;
        if (null != dataList && !dataList.isEmpty()) {
            this.dataList.clear();
            //this.dataList.addAll(dataList);
            this.dataList = dataList;
        }
        this.layoutId = layoutId;
        mLayoutInflater = LayoutInflater.from(this.context);
    }


    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(layoutId,parent,false);
        BaseViewHolder holder = creatViewHolder(itemView);
        holder.setOnListener(mListener);
        return holder;
    }


    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindViewData(dataList.get(position),position);
    }

    protected abstract  BaseViewHolder creatViewHolder(View itemView);

    public void setClickListener(OnListClickListener listener){
        this.mListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public void setData(List<T> dataList) {
        if (null != dataList && !dataList.isEmpty()) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
        }
    }

}
