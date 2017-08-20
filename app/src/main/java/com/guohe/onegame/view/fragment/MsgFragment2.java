package com.guohe.onegame.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.circle.DynamicDetailActivity;
import com.guohe.onegame.view.mine.PersonalPageActivity;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/16.
 */

public class MsgFragment2 extends BaseMsgFragment {

    private RecyclerView mRecyclerView;
    private Msg2ViewAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_msg2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mRecyclerView = getView(R.id.msg2_recyclerview);
        bindRecyclerView();
        refreshView(R.id.msg2_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new Msg2ViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class Msg2ViewAdapter extends RecyclerView.Adapter<Msg2ViewHolder>{

        @Override
        public Msg2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Msg2ViewHolder(LayoutInflater.from(MsgFragment2.this.getContext())
                    .inflate(R.layout.item_msg2_list, parent, false));
        }

        @Override
        public void onBindViewHolder(Msg2ViewHolder holder, int position) {
            FrescoUtils.setCircle(holder.mHeadDrawee, getResources().getColor(R.color.app_background));
            FrescoUtils.loadRes(holder.mHeadDrawee, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(38), DimenUtil.dp2px(38), null);
            FrescoUtils.loadRes(holder.mPictureDrawee, TestImageUtil.getDynamicImgRes(),
                    null, DimenUtil.dp2px(38), DimenUtil.dp2px(38), null);
            holder.mNickname.setText("一场" + position);
            holder.mHeadDrawee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalPageActivity.startActivity(MsgFragment2.this.getContext());
                }
            });
            holder.mPictureDrawee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DynamicDetailActivity.startActivity(MsgFragment2.this.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    class Msg2ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mHeadDrawee;
        private SimpleDraweeView mPictureDrawee;
        private TextView mNickname;
        public Msg2ViewHolder(View itemView) {
            super(itemView);
            mHeadDrawee = (SimpleDraweeView) itemView.findViewById(R.id.item_msg1_head);
            mPictureDrawee = (SimpleDraweeView) itemView.findViewById(R.id.item_msg2_image);
            mNickname = (TextView) itemView.findViewById(R.id.item_msg2_nickname);
        }
    }
}
