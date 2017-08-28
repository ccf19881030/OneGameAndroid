package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.circle.MoreMenuActivity;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/23.
 * 我的球队
 */

public class MyTeamActivity extends BaseActivity implements View.OnClickListener{

    private DiscreteScrollView mTeamPick;
    private TeamAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_myteam;
    }

    @Override
    protected void initView() {
        mTeamPick = getView(R.id.team_picker);
        mTeamPick.setOrientation(Orientation.HORIZONTAL);
        //mTeamPick.addOnItemChangedListener(this);
        mAdapter = new TeamAdapter();
        mTeamPick.setAdapter(mAdapter);
        mTeamPick.setItemTransitionTimeMillis(500);
        mTeamPick.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.9f)
                .build());

        getView(R.id.toolbar_back).setOnClickListener(this);
        getView(R.id.toolbar_setting).setOnClickListener(this);
        getView(R.id.myteam_addgame).setOnClickListener(this);
        getView(R.id.myteam_ranking).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyTeamActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myteam_addgame:
                MatchSignupActivity.startActivity(MyTeamActivity.this);
                break;
            case R.id.toolbar_back:
                MyTeamActivity.this.finish();
                break;
            case R.id.toolbar_setting:
                TeamSettingActivity.startActivity(MyTeamActivity.this);
                break;
            case R.id.myteam_ranking:
                TeamRankingActivity.startActivity(MyTeamActivity.this);
                break;
        }
    }

    class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder>{

        @Override
        public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TeamViewHolder(LayoutInflater.from(MyTeamActivity.this)
                    .inflate(R.layout.item_team_page, parent, false));
        }

        @Override
        public void onBindViewHolder(TeamViewHolder holder, int position) {
            FrescoUtils.loadRes(holder.mPicture, TestImageUtil.getDynamicImgRes(),
                    null, DimenUtil.dp2px(280), DimenUtil.dp2px(280), null);
            FrescoUtils.setCircle(holder.mHead1, 0);
            FrescoUtils.setCircle(holder.mHead2, 0);
            FrescoUtils.setCircle(holder.mHead3, 0);
            FrescoUtils.setCircle(holder.mHead4, 0);
            FrescoUtils.loadRes(holder.mHead1, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(26), DimenUtil.dp2px(6), null);
            FrescoUtils.loadRes(holder.mHead2, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(26), DimenUtil.dp2px(6), null);
            FrescoUtils.loadRes(holder.mHead3, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(26), DimenUtil.dp2px(6), null);
            FrescoUtils.loadRes(holder.mHead4, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(26), DimenUtil.dp2px(6), null);
            holder.mTeamNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamMemberActivity.startActivity(MyTeamActivity.this);
                }
            });
            holder.mMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreMenuActivity.startActivity(MyTeamActivity.this, MoreMenuActivity.TYPE_TEAM, 0, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    class TeamViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mPicture;
        private SimpleDraweeView mHead1;
        private SimpleDraweeView mHead2;
        private SimpleDraweeView mHead3;
        private SimpleDraweeView mHead4;
        private TextView mTeamNum;
        private ImageButton mMoreButton;
        public TeamViewHolder(View itemView) {
            super(itemView);
            mPicture = (SimpleDraweeView) itemView.findViewById(R.id.item_team_page_picture);
            mHead1 = (SimpleDraweeView) itemView.findViewById(R.id.item_team_page_head1);
            mHead2 = (SimpleDraweeView) itemView.findViewById(R.id.item_team_page_head2);
            mHead3 = (SimpleDraweeView) itemView.findViewById(R.id.item_team_page_head3);
            mHead4 = (SimpleDraweeView) itemView.findViewById(R.id.item_team_page_head4);
            mTeamNum = (TextView) itemView.findViewById(R.id.item_team_number);
            mMoreButton = (ImageButton) itemView.findViewById(R.id.item_team_more_button);
        }
    }
}
