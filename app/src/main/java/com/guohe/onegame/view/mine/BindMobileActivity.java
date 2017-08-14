package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 绑定手机号
 */

public class BindMobileActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("绑定手机号");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_mobile;
    }

    @Override
    protected void initView() {
        getView(R.id.bind_mobile_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindSuccessActivity.startActivity(BindMobileActivity.this);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, BindMobileActivity.class);
        context.startActivity(intent);
    }
}
