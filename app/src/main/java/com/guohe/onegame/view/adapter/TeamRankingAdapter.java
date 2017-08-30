package com.guohe.onegame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.R;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.mine.PersonalPageActivity;

/**
 * Created by 水寒 on 2017/8/23.
 */

public class TeamRankingAdapter extends RecyclerView.Adapter<TeamRankingAdapter.TeamRankingViewHolder>{

    public static final int TYPE_MVP_SORT = 0;
    public static final int TYPE_TEAM_SORT = 1;

    private Context mContext;
    private int mType;

    public TeamRankingAdapter(Context context, int type){
        mContext = context;
        mType = type;
    }

    @Override
    public TeamRankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeamRankingViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_team_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(TeamRankingViewHolder holder, int position) {
        FrescoUtils.setCircle(holder.mTeamHead, mContext.getResources().getColor(R.color.app_background));
        //FrescoUtils.loadRes(holder.mTeamHead, R.mipmap.default_team_logo, null,
        //        DimenUtil.dp2px(35), DimenUtil.dp2px(35), null);

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
        holder.mTeamHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalPageActivity.startActivity(mContext);
            }
        });
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
            GenericDraweeHierarchy hierarchy = mTeamHead.getHierarchy();
            if(mType == TYPE_MVP_SORT){
                hierarchy.setPlaceholderImage(R.mipmap.default_header);
            }else if(mType == TYPE_TEAM_SORT){
                hierarchy.setPlaceholderImage(R.mipmap.default_team_logo);
            }
            mTeamHead.setHierarchy(hierarchy);
        }
    }
}
