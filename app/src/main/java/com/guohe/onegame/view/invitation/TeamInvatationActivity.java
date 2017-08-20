package com.guohe.onegame.view.invitation;

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
 * Created by 水寒 on 2017/8/20.
 * 约战
 */

public class TeamInvatationActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_invatation;
    }

    @Override
    protected void initView() {
        getView(R.id.team_invatation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptTeamInvatationActivity.startActivity(TeamInvatationActivity.this);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TeamInvatationActivity.class);
        context.startActivity(intent);
    }
}
