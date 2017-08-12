package com.guohe.onegame.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.fragment.BaseMainFragment;
import com.guohe.onegame.view.fragment.MainFragment1;
import com.guohe.onegame.view.fragment.MainFragment2;
import com.guohe.onegame.view.fragment.MainFragment3;
import com.guohe.onegame.view.fragment.MainFragment4;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final long BACK_WAIT_TIME = 2000;
    private RadioButton[] mNavButton = new RadioButton[4];
    private int mCurrentIndex;
    private BaseMainFragment mCurrentFragment;
    private ViewGroup mFragmentContainer;
    private List<Class<? extends BaseMainFragment>> mFragmentsClass = new ArrayList<>();
    private Map<Integer, BaseMainFragment> mFragments = new HashMap<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragmentContainer = getView(R.id.fragment_container);
        mFragmentsClass.add(MainFragment1.class);
        mFragmentsClass.add(MainFragment2.class);
        mFragmentsClass.add(MainFragment3.class);
        mFragmentsClass.add(MainFragment4.class);
        mNavButton[0] = (RadioButton) findViewById(R.id.main_nav_menu1);
        mNavButton[1] = (RadioButton) findViewById(R.id.main_nav_menu2);
        mNavButton[2] = (RadioButton) findViewById(R.id.main_nav_menu3);
        mNavButton[3] = (RadioButton) findViewById(R.id.main_nav_menu4);
        mCurrentIndex = 0;
        setRadioButtonStatus();
        getFragmentInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mCurrentFragment)
                .show(mCurrentFragment)
                .commit();

        findViewById(R.id.main_nav1).setOnClickListener(this);
        findViewById(R.id.main_nav2).setOnClickListener(this);
        findViewById(R.id.main_nav3).setOnClickListener(this);
        findViewById(R.id.main_nav4).setOnClickListener(this);
    }

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setTranslucentForImageView(this, 30, mFragmentContainer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_nav1:
                setFragment(0);
                break;
            case R.id.main_nav2:
                setFragment(1);
                break;
            case R.id.main_nav3:
                setFragment(2);
                break;
            case R.id.main_nav4:
                setFragment(3);
                break;
        }
    }

    /**
     * 设置Fragment显示
     */
    private void setFragment(int currentIndex) {
        if(mCurrentIndex == currentIndex) return;
        mCurrentIndex = currentIndex;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        /*for(int i = 0; i < mFragments.size(); i++) {
            if(i != currentIndex) {
                fragmentTransaction.hide(mFragments.get(i));
            }
        }*/
        fragmentTransaction.hide(mCurrentFragment);
        if(!mFragments.containsKey(mCurrentIndex)){
            fragmentTransaction.add(R.id.fragment_container, getFragmentInstance(), String.valueOf(mCurrentIndex));
        }else{
            BaseMainFragment fragment = getFragmentInstance();
            fragment.setUserVisibleHint(true);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
        setRadioButtonStatus();
    }

    private BaseMainFragment getFragmentInstance() {
        if(!mFragments.containsKey(mCurrentIndex)) {
            try {
                mCurrentFragment = mFragmentsClass.get(mCurrentIndex).newInstance();
                mFragments.put(mCurrentIndex, mCurrentFragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mCurrentFragment = mFragments.get(mCurrentIndex);
        return mCurrentFragment;
    }

    /**
     * 设置button样式
     */
    private void setRadioButtonStatus() {
        Log.d("TEST", "currentIndex == " + mCurrentIndex);
        for (int i = 0; i < mNavButton.length; i++) {
            if (i == mCurrentIndex) {
                mNavButton[i].setChecked(true);
            } else
                mNavButton[i].setChecked(false);
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private long mTouchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - mTouchTime) >= BACK_WAIT_TIME){
                mTouchTime = System.currentTimeMillis();
                ToastUtil.showToast("再按一次退出");
            }else{
                mTouchTime = 0;
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
