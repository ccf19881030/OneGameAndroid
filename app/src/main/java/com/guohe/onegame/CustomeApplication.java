package com.guohe.onegame;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.guohe.onegame.util.FrescoUtils;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class CustomeApplication extends MultiDexApplication{

    public static final String FILE_DYNAMIC_ORIGIN = "dynamic_origin_temp.jpg";
    public static final String FILE_HEADER = "header_temp.jpg";

    private static CustomeApplication mInstance;
    public static AMapLocationClient mLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FrescoUtils.init(this, 1024);  //1G缓存
        //高德地图
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation.getErrorCode() == 0){
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getCity();
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(1000 * 60 * 30);
        //设置定位参数
        mLocationClient.setLocationOption(locationOption);
        mLocationClient.startLocation();
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
        String filePath = getFilesDir().getAbsolutePath();
        /*if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            filePath = getCacheDir().getAbsolutePath();
        }*/
        return filePath;
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath(){
        String cachePath = getCacheDir().getAbsolutePath();
        /*if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = getCacheDir().getAbsolutePath();
        }*/
        return cachePath;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mLocationClient.onDestroy();
    }
}
