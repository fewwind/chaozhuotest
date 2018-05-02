package com.example.fewwind.chaozhuofirst.utils;

import com.example.fewwind.chaozhuofirst.App;
import com.example.fewwind.chaozhuofirst.bean.MainIntent;
import com.example.fewwind.chaozhuofirst.ui.RxActivity;
import com.example.fewwind.chaozhuofirst.ui.ViewActivity;
import com.orhanobut.logger.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static int getIdByName(String name){
        try{
            return App.mApp.getResources().getIdentifier(name,"drawable",App.mApp.getPackageName());
        }catch (Exception e){
            Logger.e("---"+e.toString());
            return 1;
        }
    }

    public static <T> List<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Display getDisplay(int w , int h) {
        float ratio = w * 1.0f / h;
        if (ratio > (4 * 1.0f / 3 + 0.001) && ratio < (18.0f / 9)) {
            return Display.D_16_9;
        } else if (ratio >= (18.0f / 9 - 0.1)) {
            return Display.D_18_9;
        } else {
            return Display.D_4_3;
        }
    }

    public enum Display {
        D_4_3, D_16_9, D_18_9
    }

}
