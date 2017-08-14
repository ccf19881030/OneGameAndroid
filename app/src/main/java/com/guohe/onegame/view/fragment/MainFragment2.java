package com.guohe.onegame.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.circle.DynamicDetailActivity;
import com.guohe.onegame.view.circle.MoreMenuActivity;
import com.guohe.onegame.view.mine.PersonalPageActivity;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment2 extends BaseMainFragment {

    private RecyclerView mRecyclerView;
    private DynamicAdapter mAdapter;
    private int mScreenWidth;
    private int mRecyclerViewScrollState;
    private ViewGroup mToolbar;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {;
        mToolbar = getView(R.id.main_page2_toolbar);
        mScreenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        mRecyclerView = getView(R.id.main_two_recycerview);
        bindRecyclerView();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRecyclerViewScrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mRecyclerViewScrollState == RecyclerView.SCROLL_STATE_SETTLING && Math.abs(dy) > 50){
                    LogUtil.d("dy == " + dy);
                    if(dy > 0){ //向上滚动
                        mToolbar.setVisibility(View.GONE);
                    }else{
                        mToolbar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        refreshView(R.id.main_page2_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new DynamicAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class DynamicAdapter extends RecyclerView.Adapter<DynamicViewHolder>{

        @Override
        public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DynamicViewHolder(LayoutInflater.from(MainFragment2.this.getContext())
                    .inflate(R.layout.item_main_dynamic_list, parent, false));
        }

        @Override
        public void onBindViewHolder(DynamicViewHolder holder, int position) {
            FrescoUtils.loadRes(holder.mPicture, R.mipmap.test_img1, null, 0, 0, null);
            holder.mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DynamicDetailActivity.startActivity(MainFragment2.this.getContext());
                }
            });
            holder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalPageActivity.startActivity(MainFragment2.this.getContext());
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(15), DimenUtil.dp2px(15));
            params.setMargins(0, 0, DimenUtil.dp2px(3), 0);
            holder.mFollowdArea.removeAllViews();
            for(int i = 0 ; i < 5; i++){
                SimpleDraweeView draweeView = new SimpleDraweeView(MainFragment2.this.getContext());
                draweeView.setLayoutParams(params);
                GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
                hierarchy.setPlaceholderImage(R.mipmap.default_header);
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                holder.mFollowdArea.addView(draweeView);
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalPageActivity.startActivity(MainFragment2.this.getContext());
                    }
                });
            }
            holder.mMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreMenuActivity.startActivity(MainFragment2.this.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mPicture;
        private LinearLayout mFollowdArea;
        private ImageButton mMoreButton;
        private SimpleDraweeView mHead;
        public DynamicViewHolder(View itemView) {
            super(itemView);
            mHead = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_head);
            mMoreButton = (ImageButton) itemView.findViewById(R.id.item_dynamic_more);
            mFollowdArea = (LinearLayout) itemView.findViewById(R.id.item_dynamic_followd_head_area);
            mPicture = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_picture);
            ViewGroup.LayoutParams params = mPicture.getLayoutParams();
            params.height = mScreenWidth;
            mPicture.setLayoutParams(params);
        }
    }
}
