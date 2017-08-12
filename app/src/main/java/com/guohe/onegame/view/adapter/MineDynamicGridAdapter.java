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

/**
 * Created by 水寒 on 2017/8/12.
 */

public class MineDynamicGridAdapter extends RecyclerView.Adapter<MineDynamicGridAdapter.MineDynamicViewHolder>{

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
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143936&di=7db5d885c7ded66b3731339391e3c17a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015734571993136ac7254fbc8f63eb.jpg%40900w_1l_2o_100sh.jpg";
        FrescoUtils.loadUrl(holder.mDrawView, url, mDiment, mDiment);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MineDynamicViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mDrawView;
        public MineDynamicViewHolder(View itemView) {
            super(itemView);
            mDrawView = (SimpleDraweeView) itemView.findViewById(R.id.mine_dynamic_item_drawview);
            ViewGroup.LayoutParams params = mDrawView.getLayoutParams();
            params.height = mDiment;
            params.width = mDiment;
            mDrawView.setLayoutParams(params);
        }
    }
}
