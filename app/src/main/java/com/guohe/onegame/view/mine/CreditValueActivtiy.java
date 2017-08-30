package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/17.
 * 信用积分
 */

public class CreditValueActivtiy extends BaseActivity {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setColor(this, Color.parseColor("#262930"));
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_credit_value;
    }

    @Override
    protected void initView() {
        getView(R.id.credit_value_detail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditDetailActivity.startActivity(CreditValueActivtiy.this);
            }
        });

        getView(R.id.credit_value_rule_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, CreditValueActivtiy.class);
        context.startActivity(intent);
    }
}
