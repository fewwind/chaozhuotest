package com.example.fewwind.chaozhuofirst.utils;

import com.example.fewwind.chaozhuofirst.bean.MainIntent;
import com.example.fewwind.chaozhuofirst.ui.RxActivity;
import com.example.fewwind.chaozhuofirst.ui.ViewActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 17-7-10.
 */

public class DataUtil {

    public static List<MainIntent> getMainIntent(){
        List<MainIntent> list = new ArrayList<>();
        list.add(new MainIntent("View系列", ViewActivity.class));
        list.add(new MainIntent("RxJava", RxActivity.class));
        return list;
    }
}
