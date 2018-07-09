package com.example.fewwind.chaozhuofirst.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.ui.view.LinearLayoutSubClass;

/**
 * Created by fewwind on 18-5-3.
 */

public class ScrollerFragment extends BaseFragment {
    public ScrollerFragment() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_scroller;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        Button btn = (Button) view.findViewById(R.id.button);
        final LinearLayoutSubClass subClass = (LinearLayoutSubClass) view.findViewById(R.id.linearLayoutSubClass);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subClass.beginScroll();
            }
        });
    }
}
