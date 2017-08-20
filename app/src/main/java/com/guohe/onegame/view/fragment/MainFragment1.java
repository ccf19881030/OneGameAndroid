package com.guohe.onegame.view.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
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
                .add("约战", HomeFragment1.class)
                .add("约裁判", HomeFragment2.class)
                .add("去踢球", HomeFragment3.class)
                .add("学踢球", HomeFragment4.class)
                .create());
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
