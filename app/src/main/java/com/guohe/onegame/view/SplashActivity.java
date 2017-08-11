package com.guohe.onegame.view;

import android.os.Message;
import android.os.SystemClock;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.WeakRefrenceHandler;
import com.guohe.onegame.view.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/11.
 * 启动界面
 */

public class SplashActivity extends BaseActivity{
    private static final int SPLASH_TIME = 3000;        //闪屏时间
    private static final int HAND_START_INIT = 0x0001;  //开始初始化操作
    private static final int HAND_TURN_NEXT = 0x0002;   //跳转到下一个界面

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

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setTranslucent(this);
    }

    /**
     * 做一些初始化操作
     */
    private void doInit(){

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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
        MainActivity.startActivity(SplashActivity.this);
        SplashActivity.this.finish();
    }
}
