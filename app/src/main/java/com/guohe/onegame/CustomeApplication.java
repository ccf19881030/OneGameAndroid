package com.guohe.onegame;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.guohe.onegame.manage.rxbus.RxBus;
import com.guohe.onegame.manage.rxbus.bean.LocationRefreshEvent;
import com.guohe.onegame.util.FrescoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class CustomeApplication extends MultiDexApplication{

    public static final String FILE_DYNAMIC_ORIGIN = "dynamic_origin_temp.jpg";
    public static final String FILE_HEADER = "header_temp.jpg";

    private static CustomeApplication mInstance;
    public static AMapLocationClient mLocationClient;
    private OfflineMapManager mOfflineMapManager;
    public static Map<String, String> mCitys = new HashMap<>();

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
                    RxBus.getDefault().post(new LocationRefreshEvent(aMapLocation));
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(1000 * 60 * 5);  //5分钟刷一次
        //设置定位参数
        mLocationClient.setLocationOption(locationOption);
        mLocationClient.startLocation();
        initCitys();
    }

    private void initCitys(){
        mCitys.put("北京市", "010");
        mCitys.put("上海市", "021");
        mCitys.put("广州市", "020");
        mCitys.put("西安市", "029");
        mCitys.put("杭州市", "0571");
        mCitys.put("成都市", "028");
        mCitys.put("南京市", "025");
        mCitys.put("天津市", "022");
        mCitys.put("重庆市", "023");
        mCitys.put("哈尔滨", "0451");
        mCitys.put("长春市", "0431");
        mCitys.put("沈阳市", "024");
        mCitys.put("呼和浩特", "0471");
        mCitys.put("乌鲁木齐", "0991");
        mCitys.put("兰州市", "0931");
        mCitys.put("郑州市", "0371");
        mCitys.put("昆明市", "0871");
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
