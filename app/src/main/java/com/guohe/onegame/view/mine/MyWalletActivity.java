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
 * Created by 水寒 on 2017/8/13.
 * 我的钱包
 */

public class MyWalletActivity extends BaseActivity {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("我的钱包");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("明细");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletDetailActivity.startActivity(MyWalletActivity.this);
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected boolean showErroNetView() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initView() {
        findViewById(R.id.to_recharge_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeActivity.startActivity(MyWalletActivity.this);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyWalletActivity.class);
        context.startActivity(intent);
    }
}
