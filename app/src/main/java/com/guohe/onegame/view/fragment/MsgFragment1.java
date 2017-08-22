package com.guohe.onegame.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.RefreshUtil;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/16.
 */

public class MsgFragment1 extends BaseMsgFragment {

    private RecyclerView mRecyclerView;
    private Msg1ViewAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_msg1;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mRecyclerView = getView(R.id.msg1_recyclerview);
        bindRecyclerView();
        refreshView(R.id.msg1_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new Msg1ViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class Msg1ViewAdapter extends RecyclerView.Adapter<Msg1ViewHolder>{

        @Override
        public Msg1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Msg1ViewHolder(LayoutInflater.from(MsgFragment1.this.getContext())
                    .inflate(R.layout.item_msg1_list, parent, false));
        }

        @Override
        public void onBindViewHolder(Msg1ViewHolder holder, int position) {
            FrescoUtils.setCircle(holder.mHeadDrawee, getResources().getColor(R.color.app_background));
            FrescoUtils.loadRes(holder.mHeadDrawee, R.mipmap.icon_logo,
                    null, DimenUtil.dp2px(38), DimenUtil.dp2px(38), null);
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    class Msg1ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mHeadDrawee;
        public Msg1ViewHolder(View itemView) {
            super(itemView);
            mHeadDrawee = (SimpleDraweeView) itemView.findViewById(R.id.item_msg1_head);
        }
    }
}
