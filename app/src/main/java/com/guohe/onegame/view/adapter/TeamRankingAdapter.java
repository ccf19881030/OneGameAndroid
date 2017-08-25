package com.guohe.onegame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;

/**
 * Created by 水寒 on 2017/8/23.
 */

public class TeamRankingAdapter extends RecyclerView.Adapter<TeamRankingAdapter.TeamRankingViewHolder>{

    private Context mContext;

    public TeamRankingAdapter(Context context){
        mContext = context;
    }

    @Override
    public TeamRankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeamRankingViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_team_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(TeamRankingViewHolder holder, int position) {
        FrescoUtils.setCircle(holder.mTeamHead, mContext.getResources().getColor(R.color.app_background));
        FrescoUtils.loadRes(holder.mTeamHead, TestImageUtil.getHeadImgRes(), null,
                DimenUtil.dp2px(35), DimenUtil.dp2px(35), null);

        if(position == 0){
            holder.mTeamNum.setBackgroundResource(R.mipmap.icon_team_ranking_num1);
        }else if(position == 1){
            holder.mTeamNum.setBackgroundResource(R.mipmap.icon_team_ranking_num2);
        }else if(position == 2){
            holder.mTeamNum.setBackgroundResource(R.mipmap.icon_team_ranking_num3);
        }else{
            holder.mTeamNum.setBackgroundResource(R.mipmap.icon_team_ranking_num);
        }
        holder.mTeamNum.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TeamRankingViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView mTeamHead;
        private TextView mTeamNum;
        public TeamRankingViewHolder(View itemView) {
            super(itemView);
            mTeamHead = (SimpleDraweeView) itemView.findViewById(R.id.item_team_ranking_head);
            mTeamNum = (TextView) itemView.findViewById(R.id.item_team_ranking_num);
        }
    }
}
