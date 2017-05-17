package com.example.fewwind.chaozhuofirst.util.disk;

import android.os.Environment;
import com.example.fewwind.chaozhuofirst.App;
import java.io.IOException;

/**
 * 功能描述：
 */
public class CacheUtil {

    private DiskLruCacheHelper cacheHelper;


    public CacheUtil() {

    }


    private DiskLruCacheHelper getCacheHelper() {
        if (cacheHelper == null) {
            try {
                cacheHelper = new DiskLruCacheHelper(App.mApp,
                    App.mApp.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cacheHelper;
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final CacheUtil INSTANCE = new CacheUtil();
    }


    public static DiskLruCacheHelper getInstance() {
        return SingletonHolder.INSTANCE.getCacheHelper();
    }

}
