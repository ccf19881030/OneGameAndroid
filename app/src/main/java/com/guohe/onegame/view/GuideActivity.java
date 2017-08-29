package com.guohe.onegame.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/13.
 * 引导页
 */

public class GuideActivity extends BaseActivity {

    private LinearLayout mPointLayout;
    private ViewPager mViewPager;
    private Button mComeinButton;
    private List<View> mViewList = new ArrayList<>();

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        mViewPager = getView(R.id.guide_viewpager);
        mPointLayout = getView(R.id.first_guide_point);
        mComeinButton = getView(R.id.first_guide_button);
        mComeinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginGuideActivity.startActivity(GuideActivity.this);
                GuideActivity.this.finish();
            }
        });

        initViewPager();
        initPoint();
    }

    private void initViewPager(){
        int[] guideImgs = {R.mipmap.test_img1, R.mipmap.test_img2,
                R.mipmap.test_img3};
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < guideImgs.length; i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(guideImgs[i]);

            //将ImageView加入到集合中
            mViewList.add(imageView);
        }
        mViewPager.setAdapter(new GuidePagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mPointArray.length; i++){
                    mPointArray[position].setImageResource(R.mipmap.guide_choose_point);
                    if (position != i){
                        mPointArray[i].setImageResource(R.mipmap.guide_unchoose_point);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    //实例化原点View
    private ImageView mPointView;
    private ImageView [] mPointArray;
    /**
     * 加载底部圆点
     */
    private void initPoint() {
        mPointArray = new ImageView[mViewList.size()];
        for (int i = 0; i < mPointArray.length; i++) {
            ImageView pointView = new ImageView(this);
            pointView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            pointView.setPadding(DimenUtil.dp2px(9), 0, DimenUtil.dp2px(9), 0);//left,top,right,bottom
            mPointArray[i] = pointView;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                pointView.setImageResource(R.mipmap.guide_choose_point);
            } else {
                pointView.setImageResource(R.mipmap.guide_unchoose_point);
            }
            //将数组中的ImageView加入到ViewGroup
            mPointLayout.addView(mPointArray[i]);
        }
    }


    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 初始化position位置的界面
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }
}
