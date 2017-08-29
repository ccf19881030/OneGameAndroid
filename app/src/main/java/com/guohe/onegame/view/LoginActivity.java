package com.guohe.onegame.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/28.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    public static final int REQUEST_CODE = 0x3241;

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("手机验证");
    }

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setResult(RESULT_CANCELED);
        getView(R.id.login_close_button).setOnClickListener(this);
        getView(R.id.login_confirm_button).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivityForResult(Activity context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_close_button:
                LoginActivity.this.finish();
                break;
            case R.id.login_confirm_button:
                setResult(RESULT_OK);
                MainActivity.startActivity(LoginActivity.this);
                LoginActivity.this.finish();
                break;
        }
    }
}
