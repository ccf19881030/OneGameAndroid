package com.guohe.onegame.view.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.ScrollBanner;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.team.BallTeamActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/19.
 */

public class HomeFragment1 extends BaseHomeFragment {

    private RollPagerView mRollpagerView;
    private RollPagerAdapter mRollPagerAdapter;
    private List<ScrollBanner> mScrollBanners = new ArrayList<>();
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private YuezhanAdapter mAdapter;
    private AppBarLayout mAppBarLayout;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_page1;
    }

    @Override
    protected void initData() {
        mScrollBanners.clear();
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143936&di=7db5d885c7ded66b3731339391e3c17a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015734571993136ac7254fbc8f63eb.jpg%40900w_1l_2o_100sh.jpg"));
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143938&di=52c113bfd067d46bc54f0ef06e1d8c12&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d3835719932832f8759bff6d3a9b.jpg%40900w_1l_2o_100sh.jpg"));
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143937&di=ffadd1bb53821f7f89dbce73ad0136a3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01dcf9571993136ac7254fbc5b48b6.jpg%40900w_1l_2o_100sh.jpg"));
        mRollPagerAdapter.notifyDataSetChanged();
    }

    private int mRecyclerScrollStatu;
    @Override
    protected void initView(View view) {
        bindRollpagerView();
        mPtrFrameLayout = refreshView(R.id.main_home1_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
        mAppBarLayout = getView(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogUtil.d("verticalOffset == " + verticalOffset);
                if (verticalOffset >= 0) {
                    mPtrFrameLayout.setEnabled(true);
                } else {
                    mPtrFrameLayout.setEnabled(false);
                }
            }
        });
        mRecyclerView = getView(R.id.home1_recycerview);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRecyclerScrollStatu = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.d("dy == " + dy);
                if(dy < 0 && mRecyclerScrollStatu == RecyclerView.SCROLL_STATE_SETTLING
                        && mLinearLayoutManager.findFirstVisibleItemPosition() <= 1){
                    mAppBarLayout.setExpanded(true);
                }
            }
        });
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(HomeFragment1.this.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new YuezhanAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 轮播view
     */
    private void bindRollpagerView() {
        mRollpagerView = getView(R.id.main_one_rollpagerview);
        //设置播放时间间隔
        mRollpagerView.setPlayDelay(6000);
        //设置透明度
        mRollpagerView.setAnimationDurtion(1500);
        //设置适配器
        mRollPagerAdapter = new RollPagerAdapter();
        mRollpagerView.setAdapter(mRollPagerAdapter);
        mRollpagerView.setHintView(null);
    }

    /**
     * 图片轮播器适配器
     */
    private class RollPagerAdapter extends StaticPagerAdapter {

        private LayoutInflater mInflater;
        private int width;
        private int height;

        public RollPagerAdapter(){
            mInflater = HomeFragment1.this.getActivity().getLayoutInflater();
            width = DimenUtil.getScreenWidth(HomeFragment1.this.getActivity());
            height = DimenUtil.dp2px(150);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.item_mainfragment_two_rollview, null);
            SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.item_rollview_image);
            ScrollBanner scrollBanner = mScrollBanners.get(position);
            FrescoUtils.loadUrl(imageView, scrollBanner.getUrl(), null, width, height, null);
            return view;
        }

        @Override
        public int getCount() {
            return mScrollBanners.size();
        }
    }

    class YuezhanAdapter extends RecyclerView.Adapter<YuezhanViewHolder>{

        @Override
        public YuezhanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new YuezhanViewHolder(LayoutInflater.from(HomeFragment1.this.getContext())
                    .inflate(R.layout.item_yuezhan_list, parent, false));
        }

        @Override
        public void onBindViewHolder(YuezhanViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BallTeamActivity.startActivity(HomeFragment1.this.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class YuezhanViewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        public YuezhanViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
