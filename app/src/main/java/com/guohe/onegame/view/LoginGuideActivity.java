package com.guohe.onegame.view;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/28.
 * 登录引导界面
 */

public class LoginGuideActivity extends BaseActivity{

    private static final long BACK_WAIT_TIME = 2000;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_guide;
    }

    @Override
    protected void initView() {
        getView(R.id.login_guide_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private long mTouchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - mTouchTime) >= BACK_WAIT_TIME){
                mTouchTime = System.currentTimeMillis();
                ToastUtil.showToast("再按一次退出");
            }else{
                mTouchTime = 0;
                LoginGuideActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, LoginGuideActivity.class);
        context.startActivity(intent);
    }
}
