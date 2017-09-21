package com.example.fewwind.chaozhuofirst.bean;

import com.example.fewwind.chaozhuofirst.R;

/**
 * Created by fewwind on 17-8-3.
 */

public class TypeFactoryForList implements TypeFactory {
    @Override public int type(FileObject fileObject) {
        return R.layout.activity_main;
    }
}
