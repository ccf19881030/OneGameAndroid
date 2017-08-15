package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/13.
 * 设置界面
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("设置");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        getView(R.id.setting_abutus).setOnClickListener(this);
        getView(R.id.setting_clearcatch).setOnClickListener(this);
        getView(R.id.setting_evaluate).setOnClickListener(this);
        getView(R.id.setting_feadback).setOnClickListener(this);
        getView(R.id.setting_logout).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_abutus:
                AboutUsActivity.startActivity(this);
                break;
            case R.id.setting_clearcatch:
                final MaterialDialog clearCatchDialog = new MaterialDialog.Builder(this)
                        .title("清理缓存")
                        .content("正在清理请稍后...")
                        .progress(false, 3000, false)
                        .progressIndeterminateStyle(true)
                        .show();
                FrescoUtils.clearDiskCache();
                FrescoUtils.clearMemeryCache();
                new CountDownTimer(3000, 200){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        clearCatchDialog.incrementProgress((int)(3000 - millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        clearCatchDialog.dismiss();
                    }
                }.start();
                break;
            case R.id.setting_evaluate:
                openApplicationMarket("com.guohe.onegame");
                break;
            case R.id.setting_feadback:
                FeadbackActivity.startActivity(this);
                break;
            case R.id.setting_logout:
                new MaterialDialog.Builder(this)
                        .title("退出登录")
                        .content("您确定要退出登录？退出后将清除该用户相关数据")
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
                break;
        }
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "打开应用商店失败", Toast.LENGTH_SHORT).show();
            // 调用系统浏览器进入商城
            String url = "http://app.mi.com/detail/163525?ref=search";
            openLinkBySystem(url);
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
