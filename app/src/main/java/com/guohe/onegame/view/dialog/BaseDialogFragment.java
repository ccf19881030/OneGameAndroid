package com.guohe.onegame.view.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.guohe.onegame.util.LogUtil;

/**
 * Created by shuihan on 2016/12/13.
 * 对话框基础类
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        myOnCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(cancelTouchOutSide());
        mView = inflater.inflate(getDialogLayout(), container);
        initView(mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract void initPresenter();

    /**
     * 设置对话框的布局文件
     * @return
     */
    protected abstract int getDialogLayout();

    protected abstract void initData();

    protected abstract void initView(View view);

    protected void myOnCreate(){}

    /**
     * 设置在屏幕外点击是否显示
     * @return
     */
    protected boolean cancelTouchOutSide(){
        return true;
    }

    /**
     * 关闭对话框
     */
    public void close(){
        dismiss();
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
     * 显示对话框
     * @param manager
     */
    public void show(FragmentManager manager){
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if(isAdded()) {
            fragmentTransaction.show(this).commitAllowingStateLoss();
        }else{
            fragmentTransaction.add(this, getClass().getName()).commitAllowingStateLoss();
        }
    }

}
