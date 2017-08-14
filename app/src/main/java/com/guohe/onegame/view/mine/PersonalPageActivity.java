package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.circle.MoreMenuActivity;
import com.guohe.onegame.view.fragment.MainFragment4;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/12.
 * 个人主页
 */

public class PersonalPageActivity extends BaseActivity {

    private TextView mToolbarTitle;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_background));
    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        mToolbarTitle = titleText;
        moreButton.setVisibility(View.VISIBLE);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreMenuActivity.startActivity(PersonalPageActivity.this);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_page;
    }

    @Override
    protected void initView() {
        MainFragment4 fragment = new MainFragment4();
        fragment.setOnNickNameHideListener(new MainFragment4.NickNameHideListener() {
            @Override
            public void changed(boolean hide, String nickname) {
                if(mToolbarTitle == null) return;
                if(hide){
                    mToolbarTitle.setText(nickname);
                }else{
                    mToolbarTitle.setText("");
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putInt("userid", 2);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .show(fragment)
                .commit();
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, PersonalPageActivity.class);
        context.startActivity(intent);
    }
}
