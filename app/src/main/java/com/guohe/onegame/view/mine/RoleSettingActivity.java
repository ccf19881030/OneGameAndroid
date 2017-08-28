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
 * Created by 水寒 on 2017/8/26.
 * 角色设置
 */

public class RoleSettingActivity extends BaseActivity{

    private RecyclerView mRecyclerView;
    private RoleViewAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("身份设置");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("确认");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.startActivity(RoleSettingActivity.this);
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_role_setting;
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.role_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RoleViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, RoleSettingActivity.class);
        context.startActivity(intent);
    }

    class RoleViewAdapter extends RecyclerView.Adapter<RoleViewHolder>{

        @Override
        public RoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RoleViewHolder(LayoutInflater.from(RoleSettingActivity.this)
                .inflate(R.layout.item_role_choose, parent, false));
        }

        @Override
        public void onBindViewHolder(RoleViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    class RoleViewHolder extends RecyclerView.ViewHolder{

        public RoleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
