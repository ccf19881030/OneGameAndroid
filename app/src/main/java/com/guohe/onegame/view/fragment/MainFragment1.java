package com.guohe.onegame.view.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.team.PlaceMapActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;


/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment1 extends BaseMainFragment {

    private ViewPager mViewPager;
    private SmartTabLayout mTabLayout;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page1;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mTabLayout = getView(R.id.home_viewpagertab);
        mViewPager = getView(R.id.home_viewpager);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(MainFragment1.this.getActivity())
                .add("约战", HomeFragment.class)
                .add("约裁判", HomeFragment.class)
                .add("去踢球", HomeFragment.class)
                .add("学踢球", HomeFragment.class)
                .create());
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);

        getView(R.id.main_page1_map_comein).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceMapActivity.startActivity(MainFragment1.this.getContext());
            }
        });
    }
}
