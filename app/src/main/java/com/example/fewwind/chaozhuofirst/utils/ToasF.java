package com.example.fewwind.chaozhuofirst.utils;

import android.widget.Toast;
import com.example.fewwind.chaozhuofirst.App;

/**
 * Created by fewwind on 17-7-10.
 */

public class ToasF {

    private static Toast mToast;
    public static void show(String txt){
        if (mToast == null){
            mToast = Toast.makeText(App.mApp,txt,Toast.LENGTH_SHORT);
        }
        mToast.setText(txt);
        mToast.show();
    }
}
