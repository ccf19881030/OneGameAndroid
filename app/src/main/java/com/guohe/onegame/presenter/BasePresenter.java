package com.guohe.onegame.presenter;

import com.guohe.onegame.MvpModel;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.MvpView;

/**
 * Created by 水寒 on 2017/8/28.
 */

public class BasePresenter<T extends MvpView, M extends MvpModel> implements MvpPresenter<T, M> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void dettachView() {
        mMvpView = null;
    }

    public boolean isAttachedMvpView(){
        return mMvpView != null;
    }

    public MvpView getMvpView(){
        return mMvpView;
    }

    public interface OnPresenterResult<T>{
        void result(T result);
    }
}
