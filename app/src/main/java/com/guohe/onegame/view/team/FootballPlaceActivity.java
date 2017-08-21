package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/18.
 * 足球场
 */

public class FootballPlaceActivity extends BaseActivity {

    private SimpleDraweeView mPlaceBg;
    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_football_place;
    }

    @Override
    protected void initView() {
        mPlaceBg = getView(R.id.football_place_bg);
        FrescoUtils.loadRes(mPlaceBg, R.mipmap.test_football_place_bg, null, 0, 0, null);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, FootballPlaceActivity.class);
        context.startActivity(intent);
    }
}
