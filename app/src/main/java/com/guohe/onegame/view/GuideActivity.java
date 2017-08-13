package com.guohe.onegame.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.guohe.onegame.R;
import com.guohe.onegame.custome.ColorAnimationView;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by 水寒 on 2017/8/13.
 * 引导页
 */

public class GuideActivity extends FragmentActivity {

    private static final int[] resource = new int[]{R.mipmap.test_img1, R.mipmap.test_img2,
            R.mipmap.test_img3, R.mipmap.test_img4};

    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyFragmentStatePager(getSupportFragmentManager()));
        colorAnimationView.setmViewPager(viewPager, resource.length,
                0xff13b5b1, 0xff661166, 0xff934123, 0xff346123);
        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG","onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                Log.e("TAG","onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAG","onPageScrollStateChanged");
            }
        });
        mCurrentIndex = 0;
        // Four : Also ,you can call this method like this:
        // colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);
    }



    public class MyFragmentStatePager
            extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return new MyFragment(GuideActivity.this, position);
        }

        @Override public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public static class MyFragment extends Fragment {

        private int position;
        private Activity mContext;
        public MyFragment(Activity context, int position) {
            mContext = context;
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = LayoutInflater.from(this.getContext()).inflate(R.layout.item_guide_page, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_guide_page_image);
            imageView.setImageResource(resource[position]);
            Button comeInButton = (Button) view.findViewById(R.id.guide_come_in);
            if(position == 3){
                comeInButton.setVisibility(View.VISIBLE);
            }else{
                comeInButton.setVisibility(View.GONE);
            }
            comeInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalConfigManage.getInstance().setHasGuide(true);
                    MainActivity.startActivity(mContext);
                    mContext.finish();
                }
            });
            return view;
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }
}
