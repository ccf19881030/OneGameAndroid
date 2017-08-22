package com.guohe.onegame.view.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

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

    class PlaceChooseAdapter extends RecyclerView.Adapter<PlaceChooseViewHolder> implements View.OnClickListener{

        private int mSelectedItem = -1;

        @Override
        public PlaceChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlaceChooseViewHolder(LayoutInflater.from(DialogChoosePlace.this.getActivity())
                    .inflate(R.layout.item_choose_place, parent, false));
        }

        @Override
        public void onBindViewHolder(PlaceChooseViewHolder holder, int position) {
            if(position == 3){
                holder.mHalfRadio.setEnabled(false);
            }else if(position == 8){
                holder.mFullRadio.setEnabled(false);
            }else{
                holder.mHalfRadio.setEnabled(true);
                holder.mFullRadio.setEnabled(true);
            }

            holder.mHalfRadio.setOnClickListener(this);
            holder.mFullRadio.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public void onClick(View view) {

        }
    }

    class PlaceChooseViewHolder extends RecyclerView.ViewHolder{

        private RadioButton mHalfRadio;
        private RadioButton mFullRadio;
        public PlaceChooseViewHolder(View itemView) {
            super(itemView);

            mHalfRadio = (RadioButton) itemView.findViewById(R.id.item_choose_place_half);
            mFullRadio = (RadioButton) itemView.findViewById(R.id.item_choose_place_full);
        }
    }
}
