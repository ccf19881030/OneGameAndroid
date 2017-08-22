package com.guohe.onegame.view.dialog;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;

/**
 * Created by 水寒 on 2017/8/22.
 * 选择场地时间
 */

public class DialogChoosePlaceTime extends BaseDialogFragment {

    private RecyclerView mRecyclerView;
    private PlaceTimeChooseAdapter mAdapter;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_choose_placetime;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mRecyclerView = getView(R.id.dialog_choose_placetime_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if(position % 2 == 0){
                    outRect.left = DimenUtil.dp2px(13);
                }
                outRect.top = DimenUtil.dp2px(10);
                outRect.bottom = DimenUtil.dp2px(10);
                outRect.right = DimenUtil.dp2px(13);
            }
        });
        mAdapter = new PlaceTimeChooseAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class PlaceTimeChooseAdapter extends RecyclerView.Adapter<PlaceTimeChooseViewHolder>{

        @Override
        public PlaceTimeChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlaceTimeChooseViewHolder(LayoutInflater.from(DialogChoosePlaceTime.this.getActivity())
                    .inflate(R.layout.item_choose_placetime, parent, false));
        }

        @Override
        public void onBindViewHolder(PlaceTimeChooseViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class PlaceTimeChooseViewHolder extends RecyclerView.ViewHolder{

        public PlaceTimeChooseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
