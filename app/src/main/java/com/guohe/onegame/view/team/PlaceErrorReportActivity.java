package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/18.
 * 球场错误信息上报
 */

public class PlaceErrorReportActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("球场信息上报");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_placeerror_report;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, PlaceErrorReportActivity.class);
        context.startActivity(intent);
    }
}
