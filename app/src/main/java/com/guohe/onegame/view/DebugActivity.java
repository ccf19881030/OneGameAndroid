package com.guohe.onegame.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/11.
 * Debug界面
 */

public class DebugActivity extends BaseActivity {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_debug;
    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    protected void initView() {
        refreshView(R.id.debug_refresh, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshStop();
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, DebugActivity.class);
        context.startActivity(intent);
    }
}
