package com.guohe.onegame.view.invitation;

import android.content.Context;
import android.content.Intent;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/20.
 * 接受应站结果
 */

public class AcceptTeamInvatationActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_accept_team_invatation;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AcceptTeamInvatationActivity.class);
        context.startActivity(intent);
    }
}
