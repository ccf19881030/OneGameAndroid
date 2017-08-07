package com.guohe.onegame;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.guohe.onegame.util.FrescoUtils;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class CustomeApplication extends MultiDexApplication{

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
}
