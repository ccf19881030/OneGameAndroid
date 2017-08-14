package com.guohe.onegame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.circle.DynamicDetailActivity;

/**
 * Created by 水寒 on 2017/8/12.
 */

public class MineDynamicGridAdapter extends RecyclerView.Adapter<MineDynamicGridAdapter.MineDynamicViewHolder>{

    private int[] testImgs = {R.mipmap.test_img1, R.mipmap.test_img2,
        R.mipmap.test_img3, R.mipmap.test_img4,
        R.mipmap.test_img5, R.mipmap.test_img6,
        R.mipmap.test_img7, R.mipmap.test_img8};

    private Context mContext;
    private int mDiment;

    public MineDynamicGridAdapter(Context context){
        mContext = context;
        mDiment = (GlobalConfigManage.getInstance().getScreenWidth() - 2) / 3;
    }

    @Override
    public MineDynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineDynamicViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_mine_dynamic_view, parent, false));
    }

    @Override
    public void onBindViewHolder(MineDynamicViewHolder holder, int position) {
        FrescoUtils.loadRes(holder.mDrawView, testImgs[position % testImgs.length],
                null, mDiment, mDiment, null);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicDetailActivity.startActivity(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    public class MineDynamicViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mDrawView;
        private View mItemView;
        public MineDynamicViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mDrawView = (SimpleDraweeView) itemView.findViewById(R.id.mine_dynamic_item_drawview);
            ViewGroup.LayoutParams params = mDrawView.getLayoutParams();
            params.height = mDiment;
            params.width = mDiment;
            mDrawView.setLayoutParams(params);
        }
    }
}
