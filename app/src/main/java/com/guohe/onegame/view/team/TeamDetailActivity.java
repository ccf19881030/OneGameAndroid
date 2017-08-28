package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.adapter.FollowAdapter;
import com.guohe.onegame.view.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/27.
 * 球队详情
 */

public class TeamDetailActivity extends BaseActivity {

    private SimpleDraweeView mTeamPicture;
    private RecyclerView mRecyclerView;
    private FollowAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("球队详情");
        toolbarMenu.setText("加入");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComeInTeamActivity.startActivity(TeamDetailActivity.this);
            }
        });
    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setColor(this, Color.parseColor("#262930"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_detail;
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.team_detail_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FollowAdapter(this, true);
        HeaderAndFooterRecyclerViewAdapter headFooterAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.team_detail_head, null);
        mTeamPicture = (SimpleDraweeView) headView.findViewById(R.id.team_detail_picture);
        headFooterAdapter.addHeaderView(headView);
        mRecyclerView.setAdapter(headFooterAdapter);
    }

    @Override
    protected void initData() {
        FrescoUtils.loadRes(mTeamPicture, R.mipmap.default_team_bg, null,
                GlobalConfigManage.getInstance().getScreenWidth(), DimenUtil.dp2px(300), null);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TeamDetailActivity.class);
        context.startActivity(intent);
    }
}
