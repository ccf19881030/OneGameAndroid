package com.guohe.onegame.util;

import android.content.Context;
import android.view.View;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.RentalsSunHeaderView;

import static com.guohe.onegame.CustomeApplication.getContext;

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
        // header
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DimenUtil.dp2px(15), 0, DimenUtil.dp2px(10));
        header.setUp(refreshView);

        refreshView.setLoadingMinTime(1000);
        refreshView.setDurationToCloseHeader(1500);
        refreshView.setHeaderView(header);
        refreshView.addPtrUIHandler(header);
        refreshView.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (onRefresh != null) {
                    onRefresh.refreshBegin(frame);
                }
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        /*refreshView.setResistance(1.7f);
        refreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshView.setDurationToClose(200);
        refreshView.setDurationToCloseHeader(1000);
        refreshView.setPullToRefresh(false);
        refreshView.setKeepHeaderWhenRefresh(true);*/
    }
}
