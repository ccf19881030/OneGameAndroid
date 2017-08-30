package com.guohe.onegame.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.adapter.TeamRankingAdapter;
import com.guohe.onegame.view.base.BaseFragment;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/23.
 */

public class TeamRankingMVPFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private TeamRankingAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_team_mvp_ranking;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mRecyclerView = getView(R.id.team_mvp_recycerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new TeamRankingAdapter(this.getContext(), TeamRankingAdapter.TYPE_MVP_SORT);
        mRecyclerView.setAdapter(mAdapter);
    }
}
