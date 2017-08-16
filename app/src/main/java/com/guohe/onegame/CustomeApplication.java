package com.guohe.onegame;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.guohe.onegame.util.FrescoUtils;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class CustomeApplication extends MultiDexApplication{

    public static final String FILE_DYNAMIC_ORIGIN = "dynamic_origin_temp.jpg";
    public static final String FILE_HEADER = "header_temp.jpg";

    private static CustomeApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FrescoUtils.init(this, 1024);  //1G缓存
    }

    public static CustomeApplication getApplication(){
        return mInstance;
    }

    public static Context getContext(){
        return mInstance.getApplicationContext();
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        //return getFilesDir().getAbsolutePath();
        String filePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            filePath = getCacheDir().getAbsolutePath();
        }
        return filePath;
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath(){
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }
}
