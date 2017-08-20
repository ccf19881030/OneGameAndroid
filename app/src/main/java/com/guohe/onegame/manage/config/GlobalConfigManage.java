package com.guohe.onegame.manage.config;

import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.util.SystemConfigUtil;

/**
 * Created by shuihan on 2016/12/8.
 * 一些全局的配置
 */

public class GlobalConfigManage extends BaseConfigManage{

    private static final String SHARE_NAME = "global_config";

    private static GlobalConfigManage mInstance;

    private String mVersionName;    //版本名称
    private int mVersionCode ;      //版本号
    private String mChannelName;    //渠道名称
    private int mScreenWidth;       //屏幕宽
    private int mScreenHeight;      //屏幕高
    private boolean mRandomTheme;   //是否随机主题
    private boolean mHasGuide;      //是否引导过了
    private String mFbPlaceCollection1;  //足球场地收藏1
    private String mFbPlaceCollection2;  //足球场地收藏2

    /**
     * 配置相关的key
     */
    interface configKeys{
        String KEY_VERSION_NAME = "version_name";
        String KEY_VERSION_CODE = "version_code";
        String KEY_CHANNEL_NAME = "channel_name";
        String KEY_SCREEN_WIDTH = "screen_width";
        String KEY_SCREEN_HEIGHT = "screen_height";
        String KEY_RANDOM_THEME = "random_theme";
        String KEY_HAS_GUIDE = "has_guide";
        String KEY_FB_PLACE_COLLECTION1 = "fb_place_collection1";
        String KEY_FB_PLACE_COLLECTION2 = "fb_place_collection2";
    }

    private GlobalConfigManage(String shareName) {
        super(shareName);
        mVersionName = mSharePreference.getString(configKeys.KEY_VERSION_NAME,
                SystemConfigUtil.getAppVersionName(CustomeApplication.getContext()));
        mVersionCode = mSharePreference.getInt(configKeys.KEY_VERSION_CODE,
                SystemConfigUtil.getAppVersionCode(CustomeApplication.getContext()));
        mChannelName = mSharePreference.getString(configKeys.KEY_CHANNEL_NAME, "form");
        mScreenWidth = mSharePreference.getInt(configKeys.KEY_SCREEN_WIDTH, 0);
        mScreenHeight = mSharePreference.getInt(configKeys.KEY_SCREEN_HEIGHT, 0);
        mRandomTheme = mSharePreference.getBoolean(configKeys.KEY_RANDOM_THEME, false);
        mHasGuide = mSharePreference.getBoolean(configKeys.KEY_HAS_GUIDE, false);
        mFbPlaceCollection1 = mSharePreference.getString(configKeys.KEY_FB_PLACE_COLLECTION1, null);
        mFbPlaceCollection2 = mSharePreference.getString(configKeys.KEY_FB_PLACE_COLLECTION2, null);
    }

    public static GlobalConfigManage getInstance(){
        if(mInstance == null){
            mInstance = new GlobalConfigManage(SHARE_NAME);
        }
        return mInstance;
    }

    public String getFbPlaceCollection1() {
        return mFbPlaceCollection1;
    }

    public void setFbPlaceCollection1(String fbPlaceCollection1) {
        this.mFbPlaceCollection1 = fbPlaceCollection1;
        setConfig(configKeys.KEY_FB_PLACE_COLLECTION1, mFbPlaceCollection1);
    }

    public String getFbPlaceCollection2() {
        return mFbPlaceCollection2;
    }

    public void setFbPlaceCollection2(String mFbPlaceCollection2) {
        this.mFbPlaceCollection2 = mFbPlaceCollection2;
        setConfig(configKeys.KEY_FB_PLACE_COLLECTION2, mFbPlaceCollection2);
    }

    public void setHasGuide(boolean hasGuide){
        mHasGuide = hasGuide;
        setConfig(configKeys.KEY_HAS_GUIDE, mHasGuide);
    }

    public boolean getHasGuide(){
        return mHasGuide;
    }

    /**
     * 设置渠道名称
     * @param channelName
     */
    public void setChannelName(String channelName){
        mChannelName = channelName;
        setConfig(configKeys.KEY_CHANNEL_NAME, mChannelName);
    }

    /**
     * 获取渠道名称
     * @return
     */
    public String getChannelName(){
        return mChannelName;
    }


    /**
     * 获取版本名称
     * @return
     */
    public String getVersionName(){
        if(mVersionName == null){
            mVersionName = SystemConfigUtil.getAppVersionName(CustomeApplication.getContext());
        }
        return mVersionName;
    }

    public int getVersionCode(){
        if(mVersionCode == -1){
            mVersionCode = SystemConfigUtil.getAppVersionCode(CustomeApplication.getContext());
        }
        return mVersionCode;
    }

    public void setScreenWidth(int width){
        mScreenWidth = width;
        setConfig(configKeys.KEY_SCREEN_WIDTH, mScreenWidth);
    }

    public void setScreenHeight(int height){
        mScreenHeight = height;
        setConfig(configKeys.KEY_SCREEN_HEIGHT, mScreenHeight);
    }

    public int getScreenWidth(){
        return mScreenWidth;
    }

    public int getScreenHeight(){
        return mScreenHeight;
    }

    public void setRandomTheme(boolean random){
        mRandomTheme = random;
        setConfig(configKeys.KEY_RANDOM_THEME, mRandomTheme);
    }

    public boolean getRandomTheme(){
        return mRandomTheme;
    }
}
