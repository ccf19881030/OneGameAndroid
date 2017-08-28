package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.adapter.FollowAdapter;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/26.
 * 搜索关注
 */

public class SearchAttentionActivity extends BaseActivity{

    private EditText mSearchEdit;
    private Button mSearchButton;
    private RecyclerView mRecyclerView;
    private FollowAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_attention;
    }

    @Override
    protected void initView() {
        mSearchEdit = getView(R.id.search_searchedit);
        mSearchButton = getView(R.id.search_button);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (!TextUtils.isEmpty(newText)){
                    mSearchButton.setText("搜索");
                }else{
                    mSearchButton.setText("取消");
                }
            }

            @Override
            public void afterTextChanged(Editable newText) {

            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("取消".equals(mSearchButton.getText().toString())){
                    SearchAttentionActivity.this.finish();
                }else{
                    //TODO 去查找
                }
            }
        });
        mRecyclerView = getView(R.id.search_attention_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FollowAdapter(this, true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SearchAttentionActivity.class);
        context.startActivity(intent);
    }
}
