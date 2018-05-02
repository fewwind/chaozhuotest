package com.example.fewwind.chaozhuofirst.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fewwind.chaozhuofirst.R;
import java.util.List;

/**
 * Created by fewwind on 17-8-29.
 */

public class PageListAdapter extends SuperBaseAdapter{

    public int currentPagePos = 0;

    public PageListAdapter(Context activity, List dataList, int layoutId) {
        super(activity, dataList, layoutId);
    }


    @Override protected BaseViewHolder creatViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private class ViewHolder  extends BaseViewHolder<String>{

        TextView tvTitle;
        ImageView ivDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            tvTitle= (TextView) itemView.findViewById(R.id.id_item_page_tv_title);
            ivDelete= (ImageView) itemView.findViewById(R.id.id_item_page_iv_delete);

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mListener.OnListClickLitener(ivDelete,ViewHolder.this,getAdapterPosition());
                }
            });
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mListener.OnListClickLitener(tvTitle,ViewHolder.this,getLayoutPosition());
                }
            });
        }


        @Override public void bindViewData(String data, int position) {
            tvTitle.setText(data+position);
            if (position ==currentPagePos){
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.txt_select));
            }else{
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.txt_normal));
            }
        }
    }


    public void setCurrentPagePos(int currentPagePos) {
        this.currentPagePos = currentPagePos;
        notifyItemChanged(currentPagePos);
    }
}
