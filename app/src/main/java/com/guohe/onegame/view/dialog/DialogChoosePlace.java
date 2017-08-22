package com.guohe.onegame.view.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guohe.onegame.R;

/**
 * Created by 水寒 on 2017/8/22.
 */

public class DialogChoosePlace extends BaseDialogFragment{

    private RecyclerView mRecyclerView;
    private PlaceChooseAdapter mAdapter;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_choose_place;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean cancelTouchOutSide() {
        return true;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = getView(R.id.dialog_choose_place_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DialogChoosePlace.this.getActivity()));
        mAdapter = new PlaceChooseAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class PlaceChooseAdapter extends RecyclerView.Adapter<PlaceChooseViewHolder>{

        @Override
        public PlaceChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlaceChooseViewHolder(LayoutInflater.from(DialogChoosePlace.this.getActivity())
                    .inflate(R.layout.item_choose_place, parent, false));
        }

        @Override
        public void onBindViewHolder(PlaceChooseViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class PlaceChooseViewHolder extends RecyclerView.ViewHolder{

        public PlaceChooseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
