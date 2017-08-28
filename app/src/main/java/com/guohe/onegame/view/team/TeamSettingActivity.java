package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.dialog.DialogChooseColorFragment;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/24.
 * 球队设置
 */

public class TeamSettingActivity extends BaseActivity{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("球队设置");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_setting;
    }

    @Override
    protected void initView() {
        getView(R.id.team_setting_clothes_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogChooseColorFragment()
                        .show(TeamSettingActivity.this.getFragmentManager());
            }
        });

        getView(R.id.team_setting_signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(TeamSettingActivity.this)
                        .title("退出球队")
                        .content("您确定要退出该球队")
                        .positiveText("确定退出")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TeamSettingActivity.class);
        context.startActivity(intent);
    }
}
