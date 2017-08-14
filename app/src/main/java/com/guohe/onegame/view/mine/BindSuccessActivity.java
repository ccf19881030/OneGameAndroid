package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 绑定成功界面
 */

public class BindSuccessActivity extends BaseActivity{

    private static final int BIND_TYPE_MOBILE = 1;
    private static final int BIND_TYPE_WEIXIN = 2;
    private static final int BIND_TYPE_QQ = 3;

    private int mBindType = 1;

    private ImageView mBindIcon;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("绑定成功");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_success;
    }

    @Override
    protected void initView() {
        mBindIcon = getView(R.id.bind_success_icon);
        switch (mBindType){
            case BIND_TYPE_MOBILE:
                mBindIcon.setImageResource(R.mipmap.icon_bind_mobile_success);
                break;
            case BIND_TYPE_WEIXIN:
                mBindIcon.setImageResource(R.mipmap.icon_bind_weixin_success);
                break;
            case BIND_TYPE_QQ:
                mBindIcon.setImageResource(R.mipmap.icon_bind_qq_success);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, BindSuccessActivity.class);
        context.startActivity(intent);
    }
}
