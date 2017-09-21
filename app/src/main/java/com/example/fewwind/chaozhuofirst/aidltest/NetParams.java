package com.example.fewwind.chaozhuofirst.aidltest;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fewwind on 17-6-26.
 */

public class NetParams {

    public String toJsonString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("mid", "");
            obj.put("extra", "");
        } catch (JSONException var4) {
            Log.e("CZUpdateQuery", "Error to json String", var4);
        }

        this.injectExtraData(obj);
        return obj.toString();

    }


    protected void injectExtraData(JSONObject jsonObj) {
    }

}
