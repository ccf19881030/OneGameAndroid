package com.guohe.onegame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.mine.PersonalPageActivity;

/**
 * Created by 水寒 on 2017/8/21.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder>{

    private Context mContext;
    private boolean mFans;

    public FollowAdapter(Context context, boolean fans){
        mContext = context;
        mFans = fans;
    }

    @Override
    public FollowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_follow_list, parent, false));
    }

    @Override
    public void onBindViewHolder(FollowViewHolder holder, int position) {
        FrescoUtils.setCircle(holder.followHead, mContext.getResources().getColor(R.color.app_background));
        FrescoUtils.loadRes(holder.followHead, TestImageUtil.getHeadImgRes(), null,
                DimenUtil.dp2px(34), DimenUtil.dp2px(34), null);
        if(mFans){
            holder.followButton.setText("关注");
        }else{
            holder.followButton.setText("取消");
        }

        holder.followHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalPageActivity.startActivity(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class FollowViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView followHead;
        private Button followButton;
        public FollowViewHolder(View itemView) {
            super(itemView);
            followHead = (SimpleDraweeView) itemView.findViewById(R.id.item_follow_head);
            followButton = (Button) itemView.findViewById(R.id.item_follow_button);
        }
    }
}
