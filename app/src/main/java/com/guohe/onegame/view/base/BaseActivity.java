package com.guohe.onegame.view.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.MvpView;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.rxbus.RxBus;
import com.guohe.onegame.manage.rxbus.bean.BaseBusEvent;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.umeng.analytics.MobclickAgent;
import com.wou.commonutils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by 水寒 on 2017/7/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private List<Subscription> mSubscriptions = new ArrayList<>();

    private List<MvpPresenter> mPresenters = new ArrayList<>();
    private PtrFrameLayout mRefreshView;
    private Toolbar mToolbar;
    private View mHiddenNetworkView;
    private NetWorkBroadcastReciver mNetWorkReciver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        View contentView = LayoutInflater.from(this).inflate(getContentView(), null);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(contentView);
        initNetworkStatu(frameLayout);
        setContentView(frameLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
        setStatuBar();
        initView();
        initPresenter(mPresenters);
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.attachView(this);
        }
        initData();
        if(canSlidr()){
            setSlidr();
        }
       /*
        getWindow().getContainer().addContentView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));*/
    }

    private void initNetworkStatu(FrameLayout frameLayout) {
        if(showErroNetView()){
            mHiddenNetworkView = LayoutInflater.from(this).inflate(R.layout.hidden_bg_network, null);
            frameLayout.addView(mHiddenNetworkView);
            if(!delayNetReciver() || delayNetReciver() && !NetWorkUtils.isNetworkConnected(this)) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                mNetWorkReciver = new NetWorkBroadcastReciver();
                registerReceiver(mNetWorkReciver, filter);
            }
            if(NetWorkUtils.isNetworkConnected(this)) {
                mHiddenNetworkView.setVisibility(View.GONE);
            }else{
                mHiddenNetworkView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected boolean canSlidr(){
        return true;
    }

    protected boolean showErroNetView(){
        return false;
    }

    protected boolean delayNetReciver(){
        return false;
    }

    protected void setStatuBar(){
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_background));
    }

    private void setSlidr() {
        SlidrConfig config = new SlidrConfig.Builder()
                //.primaryColor(getResources().getColor(R.color.colorPrimary))
                //.secondaryColor(getResources().getColor(R.color.colorSecondary))
                .position(SlidrPosition.LEFT)
                .sensitivity(1f)
                .scrimColor(Color.BLACK)
                //.scrimStartAlpha(0.8f)
                //.scrimEndAlpha(0f)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .edge(true)
                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                .listener(null)
                .build();
        Slidr.attach(this, config);
    }

    private void initToolbar(){
        mToolbar = getView(R.id.toolbar);
        if(mToolbar == null) return;
        setSupportActionBar(mToolbar);
        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        mToolbar.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        ImageButton moreButton = (ImageButton) mToolbar.findViewById(R.id.toolbar_more);
        TextView toolbarMenu = (TextView) mToolbar.findViewById(R.id.toolbar_menu);
        customeToolbar(toolbarTitle, toolbarMenu, moreButton);
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {

    }

    protected boolean youMenResume(){
        return false;
    }

    protected boolean youMenPause(){
        return false;
    }

    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onPause() {
        super.onPause();
        if(!youMenPause()){
            MobclickAgent.onPageEnd(this.getClass().getName());
        }
        MobclickAgent.onPause(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!youMenResume()) {
            MobclickAgent.onPageStart(this.getClass().getName());
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.dettachView();
        }
        mPresenters.clear();
        for(Subscription subscription : mSubscriptions){
            if(subscription == null) continue;
            if(subscription.isUnsubscribed()) continue;
            subscription.unsubscribe();
        }
        mSubscriptions.clear();
        if(mNetWorkReciver != null) {
            unregisterReceiver(mNetWorkReciver);
        }
    }

    class NetWorkBroadcastReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(NetWorkUtils.isNetworkConnected(BaseActivity.this)){
                if(!delayNetReciver()) {
                    mHiddenNetworkView.setVisibility(View.GONE);
                    initData();
                }
            }else{
                mHiddenNetworkView.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public final <E extends View> E getView (int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogUtil.e("Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    protected <E extends BaseBusEvent> void observerRxBus(Class<E> busClass, final Action1<E> onNext) {
        Subscription subscription = RxBus.getDefault().observerRxBus(busClass, onNext);
        mSubscriptions.add(subscription);
    }

    protected <E extends BaseBusEvent> void observerRxBusSticky(Class<E> busClass, final Action1<E> onNext) {
        Subscription subscription = RxBus.getDefault().observerRxBusSticky(busClass, onNext);
        mSubscriptions.add(subscription);
    }

    /**
     * 刷新界面
     * @param id
     * @param onRefresh
     */
    protected void refreshView(int id, RefreshUtil.OnRefresh onRefresh) {
        final PtrFrameLayout refreshView = getView(id);
        if (refreshView == null) return;
        mRefreshView = refreshView;
        //RefreshUtil.refreshView(this, refreshView, onRefresh);
        RefreshUtil.refreshMainView(this, refreshView, onRefresh);
    }

    public void refreshStop() {
        if(mRefreshView == null) return;
        mRefreshView.refreshComplete();
    }
}
