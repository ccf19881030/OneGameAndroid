package com.guohe.onegame.view.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.ScrollBanner;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment1 extends BaseMainFragment {

    private RollPagerView mRollpagerView;
    private RollPagerAdapter mRollPagerAdapter;
    private List<ScrollBanner> mScrollBanners = new ArrayList<>();

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page1;
    }

    @Override
    protected void initData() {
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143936&di=7db5d885c7ded66b3731339391e3c17a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015734571993136ac7254fbc8f63eb.jpg%40900w_1l_2o_100sh.jpg"));
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143938&di=52c113bfd067d46bc54f0ef06e1d8c12&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d3835719932832f8759bff6d3a9b.jpg%40900w_1l_2o_100sh.jpg"));
        mScrollBanners.add(new ScrollBanner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502521143937&di=ffadd1bb53821f7f89dbce73ad0136a3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01dcf9571993136ac7254fbc5b48b6.jpg%40900w_1l_2o_100sh.jpg"));
        mRollPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView(View view) {

        bindRollpagerView();
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
        mRollpagerView.setHintView(new ColorPointHintView(
                this.getActivity(), Color.WHITE, Color.rgb(54, 54, 54)));
    }

    /**
     * 图片轮播器适配器
     */
    private class RollPagerAdapter extends StaticPagerAdapter {

        private LayoutInflater mInflater;
        private int width;
        private int height;

        public RollPagerAdapter(){
            mInflater = MainFragment1.this.getActivity().getLayoutInflater();
            width = DimenUtil.getScreenWidth(MainFragment1.this.getActivity());
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
}
