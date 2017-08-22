package com.guohe.onegame.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;

import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.WeakRefrenceHandler;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.fragment.VideoItemFragment;
import com.wou.commonutils.FileUtils;
import com.wou.commonutils.ScreenSizeUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/11.
 * 启动界面
 */

public class SplashActivity extends BaseActivity{
    private static final int SPLASH_TIME = 3000;        //闪屏时间
    private static final int HAND_START_INIT = 0x0001;  //开始初始化操作
    private static final int HAND_TURN_NEXT = 0x0002;   //跳转到下一个界面
    public static String mapFilePath;
    private long mStartTime;  //开始启动页时间

    private WeakRefrenceHandler<SplashActivity> mHandler = new WeakRefrenceHandler<SplashActivity>(this) {
        @Override
        protected void handleMessage(SplashActivity ref, Message msg) {
            switch (msg.what){
                case HAND_START_INIT:
                    mStartTime = SystemClock.elapsedRealtime();
                    doInit();
                    int remainDelay =(int)(SPLASH_TIME -
                            (SystemClock.elapsedRealtime() - mStartTime));
                    if(remainDelay <= 0){
                        turnToOtherView();
                    }else{
                        mHandler.sendEmptyMessageDelayed(HAND_TURN_NEXT, remainDelay);
                    }
                    break;
                case HAND_TURN_NEXT:
                    turnToOtherView();
                    break;
            }
        }
    };

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    /**
     * 做一些初始化操作
     */
    private void doInit(){
        GlobalConfigManage.getInstance().setScreenWidth(ScreenSizeUtil.getScreenWidth(this));
        GlobalConfigManage.getInstance().setScreenHeight(ScreenSizeUtil.getScreenHeight(this));
        AssetManager assertManager = getAssets();
        try {
            File file = new File(CustomeApplication.getApplication().getFilesDirPath(), "gaodeditu.data");
            mapFilePath = file.getAbsolutePath();
            LogUtil.d("file.Path == " + mapFilePath);
            if(file.exists()) return;
            FileUtils.writeFile(file, assertManager.open("mystyle_sdk_1503030101_0100.data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        VideoItemFragment videoItemFragment = new VideoItemFragment();
        Bundle localBundle = new Bundle();
        localBundle.putInt("videoRes", R.raw.splash_1);
        localBundle.putInt("imgRes", R.drawable.slogan_1);
        videoItemFragment.setArguments(localBundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, videoItemFragment)
                .show(videoItemFragment)
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean canSlidr() {
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            mHandler.sendEmptyMessage(HAND_START_INIT);
        }
    }

    @Override
    public void turnToOtherView() {
        if(GlobalConfigManage.getInstance().getHasGuide()) {
            MainActivity.startActivity(SplashActivity.this);
        }else{
            GuideActivity.startActivity(SplashActivity.this);
        }
        SplashActivity.this.finish();
    }
}
