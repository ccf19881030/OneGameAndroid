package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/13.
 * 钱包明细
 */

public class WalletDetailActivity extends BaseActivity{

    private RecyclerView mRecyclerView;
    private WalletDetailAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("明细");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.wallet_detail_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WalletDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    class WalletDetailAdapter extends RecyclerView.Adapter<WalletDetailViewHolder>{

        @Override
        public WalletDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WalletDetailViewHolder(LayoutInflater.from(WalletDetailActivity.this)
                    .inflate(R.layout.item_wallet_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(WalletDetailViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class WalletDetailViewHolder extends RecyclerView.ViewHolder{

        public WalletDetailViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, WalletDetailActivity.class);
        context.startActivity(intent);
    }
}
