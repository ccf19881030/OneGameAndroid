package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/26.
 * 认证界面
 */

public class AuthenticationActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("提交资料");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }
}
