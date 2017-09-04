package com.guohe.onegame.util;

import android.content.Context;
import android.view.View;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.PtrMDHeader;

/**
 * Created by 水寒 on 2017/8/11.
 */

public class RefreshUtil {

    public interface OnRefresh {
        void refreshBegin(PtrFrameLayout frame);
    }

    /**
     * 刷新界面
     *
     * @param refreshView
     */
    public static void refreshView(Context context, final
            PtrFrameLayout refreshView, final OnRefresh onRefresh) {
        if (refreshView == null) return;
       /* final String show = "YI CHANG";
        // header
        final StoreHouseHeader header = new StoreHouseHeader(context);
        header.setPadding(0, DimenUtil.dp2px(15), 0, 0);
        header.setTextColor(Color.parseColor("#ff5c1c"));

        *//**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
         *//*
        header.initWithString(show);*/
        final PtrMDHeader header = new PtrMDHeader(context);
        //header.setPadding(0, DimenUtil.dp2px(15), 0, 0);
        refreshView.setLoadingMinTime(1000);
        refreshView.setDurationToCloseHeader(1500);
        refreshView.setHeaderView(header);
        refreshView.addPtrUIHandler(header);
        refreshView.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (onRefresh != null) {
                    onRefresh.refreshBegin(frame);
                }
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.refreshComplete();
                        header.refreshComplete(true, frame);
                    }
                }, 1500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }
}
