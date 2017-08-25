package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.fragment.TeamRankingFragment;
import com.guohe.onegame.view.fragment.TeamRankingMVPFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/23.
 * 球队排名
 */

public class TeamRankingActivity extends BaseActivity {

    private SmartTabLayout mSmartTabLayout;
    private ViewPager mViewPager;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("排名");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_ranking;
    }

    @Override
    protected void initView() {
        mSmartTabLayout = getView(R.id.team_ranking_viewpagertab);
        mViewPager = getView(R.id.team_ranking_viewpager);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("MVP", TeamRankingMVPFragment.class)
                .add("球队", TeamRankingFragment.class)
                .create());
        mViewPager.setAdapter(adapter);
       mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TeamRankingActivity.class);
        context.startActivity(intent);
    }
}
