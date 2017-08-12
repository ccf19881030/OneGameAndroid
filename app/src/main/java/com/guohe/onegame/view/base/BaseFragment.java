package com.guohe.onegame.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.MvpView;
import com.guohe.onegame.manage.rxbus.RxBus;
import com.guohe.onegame.manage.rxbus.bean.BaseBusEvent;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by 水寒 on 2017/7/14.
 */

public abstract class BaseFragment extends Fragment implements MvpView {

    private View mView;
    private List<Subscription> mSubscriptions = new ArrayList<>();
    private List<MvpPresenter> mPresenters = new ArrayList<>();
    private PtrFrameLayout mRefreshView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getContentView(), null);
        initView(mView);
        initPresenter(mPresenters);
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.attachView(this);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.dettachView();
        }
        mPresenters.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart(this.getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd(this.getClass().getName());
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
     * 设置布局文件
     * @return
     */
    protected abstract int getContentView();

    protected abstract void initData();

    protected abstract void initView(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(Subscription subscription : mSubscriptions){
            if(subscription == null) continue;
            if(subscription.isUnsubscribed()) continue;
            subscription.unsubscribe();
        }
        mSubscriptions.clear();
    }


    @SuppressWarnings("unchecked")
    protected final <E extends View> E getView (int id) {
        try {
            return (E) mView.findViewById(id);
        } catch (ClassCastException ex) {
            LogUtil.e("Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    /**
     * 刷新界面
     * @param id
     * @param onRefresh
     */
    protected PtrFrameLayout refreshView(int id, RefreshUtil.OnRefresh onRefresh) {
        final PtrFrameLayout refreshView = getView(id);
        if (refreshView == null) return null;
        mRefreshView = refreshView;
        RefreshUtil.refreshView(this.getContext(), refreshView, onRefresh);
        return refreshView;
    }

    public void refreshStop() {
        if(mRefreshView == null) return;
        mRefreshView.refreshComplete();
    }
}
