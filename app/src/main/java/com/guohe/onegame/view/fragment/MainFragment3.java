package com.guohe.onegame.view.fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment3 extends BaseMainFragment {

    private ViewPager mViewPager;
    private List<BaseMsgFragment> mMsgFragments = new ArrayList<>();
    private MsgFragmentPagerAdapter mAdapter;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page3;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mViewPager = getView(R.id.msg_viewpager);
        mRadioButton1 = getView(R.id.main_msg_radiobutton1);
        mRadioButton2 = getView(R.id.main_msg_radiobutton2);
        mRadioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mViewPager.setCurrentItem(0);
                }
            }
        });
        mRadioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mViewPager.setCurrentItem(1);
                }
            }
        });
        MsgFragment1 fragment1 = new MsgFragment1();
        MsgFragment2 fragment2 = new MsgFragment2();
        mMsgFragments.add(fragment1);
        mMsgFragments.add(fragment2);

        mAdapter = new MsgFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mRadioButton1.setChecked(true);
                }else if(position == 1){
                    mRadioButton2.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MsgFragmentPagerAdapter extends FragmentPagerAdapter {

        public MsgFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mMsgFragments.get(position);
        }

        @Override
        public int getCount() {
            return mMsgFragments.size();
        }
    }
}
