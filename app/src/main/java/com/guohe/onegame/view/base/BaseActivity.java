package com.guohe.onegame.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.MvpView;
import com.guohe.onegame.manage.rxbus.RxBus;
import com.guohe.onegame.manage.rxbus.bean.BaseBusEvent;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.jaeger.library.StatusBarUtil;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        setStatuBar();
        initView();
        initPresenter(mPresenters);
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.attachView(this);
        }
        initData();
    }

    protected void setStatuBar(){
        StatusBarUtil.setTranslucent(this, 0);
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
            //MobclickAgent.onPause(this);
           // MobclickAgent.onPageEnd(this.getClass().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!youMenResume()) {
            //MobclickAgent.onResume(this);
            //MobclickAgent.onPageStart(this.getClass().getName());
        }
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
        RefreshUtil.refreshView(this, refreshView, onRefresh);
    }

    public void refreshStop() {
        if(mRefreshView == null) return;
        mRefreshView.refreshComplete();
    }
}
