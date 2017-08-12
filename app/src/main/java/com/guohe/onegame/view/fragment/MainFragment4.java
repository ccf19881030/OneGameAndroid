package com.guohe.onegame.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.adapter.MineDynamicGridAdapter;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment4 extends BaseMainFragment {

    private RecyclerView mRecyclerView;
    private MineDynamicGridAdapter mAdapter;
    private SimpleDraweeView mHeaderDraw;
    private PtrFrameLayout mPtrFrameLayout;
    private int mUserId;
    private boolean mIsMine;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page4;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if(bundle != null){
            mUserId = bundle.getInt("userid");
            mIsMine = mUserId == 1;
        }else{
            mIsMine = true;
            mUserId = 1;
        }
        mHeaderDraw = getView(R.id.header_icon);
        ImageButton attentionButton = getView(R.id.attention_icon);
        if(mIsMine){
            attentionButton.setImageResource(R.mipmap.icon_setting);
        }else{
            attentionButton.setImageResource(R.mipmap.icon_not_followed);
        }
        mRecyclerView = getView(R.id.personal_recyclerview);
        bindRecyclerView();
        mPtrFrameLayout = refreshView(R.id.main_mine_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
        AppBarLayout appBarLayout = getView(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mPtrFrameLayout.setEnabled(true);
                } else {
                    mPtrFrameLayout.setEnabled(false);
                }
            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if(position % 3 == 0){
                    outRect.top = 1;
                }else{
                    outRect.top = 1;
                    outRect.left = 1;
                }
            }
        });
        mAdapter = new MineDynamicGridAdapter(this.getContext());
        //HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
       /* View headerView = LayoutInflater.from(
                this.getContext()).inflate(R.layout.header_mine_dynamic_list, null);
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);*/
    }
}
