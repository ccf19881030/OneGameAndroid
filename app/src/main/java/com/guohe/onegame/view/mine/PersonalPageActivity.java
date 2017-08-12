package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.fragment.MainFragment4;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/12.
 * 个人主页
 */

public class PersonalPageActivity extends BaseActivity {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setTranslucentForImageView(this, 30, null);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_page;
    }

    @Override
    protected void initView() {
        MainFragment4 fragment = new MainFragment4();
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
