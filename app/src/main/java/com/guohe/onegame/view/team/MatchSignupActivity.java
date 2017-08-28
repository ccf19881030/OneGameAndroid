package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;
import com.mobike.library.MobikeView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItem;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/22.
 * 比赛报名
 */

public class MatchSignupActivity extends BaseActivity {

    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private RecyclerView mSignupRecycerView;
    private SignupDetailAdapter mAdapter;

    private MobikeView mobikeView;
    private SensorManager sensorManager;
    private Sensor defaultSensor;
    private FrameLayout.LayoutParams mLayoutParams;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void setStatuBar() {
        StatusBarUtil.setColor(this, Color.parseColor("#262930"), 255);
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_match_signup;
    }

    @Override
    protected void initView() {
        mViewPager = getView(R.id.match_signup_viewpager);
        mSmartTabLayout = getView(R.id.match_signup_viewpagertab);
        ViewPagerItem signupPager = ViewPagerItem.of("报名详情", R.layout.viewpager_gameteam);
        ViewPagerItems viewPagerItems = ViewPagerItems.with(this)
                .add("比赛信息", R.layout.viewpager_gameinfo)
                .add(signupPager)
                .create();
        ViewPagerItemAdapter adapter = new ViewPagerItemAdapter(viewPagerItems);

        mSignupRecycerView = (RecyclerView) signupPager.initiate(
                getLayoutInflater(), null).findViewById(R.id.gameteam_recyclerview);
        mViewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(mViewPager);

        mobikeView = (MobikeView) findViewById(R.id.mobike_view);
        mobikeView.getmMobike().setDensity(1.5f);
        mobikeView.getmMobike().setFriction(0.1f);
        mobikeView.getmMobike().setRestitution(0.1f);
        mobikeView.getmMobike().setRatio(50f);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mSignupRecycerView.setHasFixedSize(false);
        mSignupRecycerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new SignupDetailAdapter();
        mSignupRecycerView.setAdapter(mAdapter);
    }

    private void initMobikeTiezhi() {
        mLayoutParams = new FrameLayout.LayoutParams(DimenUtil.dp2px(40), DimenUtil.dp2px(40));
        mLayoutParams.gravity = Gravity.TOP|Gravity.CENTER_HORIZONTAL;
        new CountDownTimer(500 * 7, 500){
            @Override
            public void onTick(long millisUntilFinished) {
                addMobiView();
            }

            @Override
            public void onFinish() {
                addMobiView();
            }
        }.start();
    }

    private void addMobiView(){
        SimpleDraweeView draweeView = new SimpleDraweeView(this);
        draweeView.setLayoutParams(mLayoutParams);
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        hierarchy.setPlaceholderImage(R.mipmap.default_header);
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        FrescoUtils.setCircle(draweeView, getResources().getColor(R.color.app_background));
        FrescoUtils.loadRes(draweeView, TestImageUtil.getHeadImgRes(), null, DimenUtil.dp2px(40), DimenUtil.dp2px(40), null);
            /*SimpleDraweeView imageView = new SimpleDraweeView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.mobike_view_circle_tag,true);*/
        mobikeView.addView(draweeView, mLayoutParams);
    }

    @Override
    protected void initData() {
        initMobikeTiezhi();
    }

    public static void startActivity(Context context){
        Intent inten = new Intent(context, MatchSignupActivity.class);
        context.startActivity(inten);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mobikeView.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobikeView.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listerner);
    }

    private SensorEventListener listerner = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float x =  event.values[0];
                float y =   event.values[1] * 2.0f;
                mobikeView.getmMobike().onSensorChanged(-x,y);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    class SignupDetailAdapter extends RecyclerView.Adapter<SignupDetailViewHolder>{

        @Override
        public SignupDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SignupDetailViewHolder(LayoutInflater.from(MatchSignupActivity.this)
                    .inflate(R.layout.item_signup_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(SignupDetailViewHolder holder, int position) {
            if(position % 2 == 0){
                holder.mNum.setBackgroundResource(R.mipmap.icon_match_signup_ok);
            }else{
                holder.mNum.setBackgroundResource(R.mipmap.icon_match_signup_notok);
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class SignupDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView mNum;
        public SignupDetailViewHolder(View itemView) {
            super(itemView);
            mNum = (TextView) itemView.findViewById(R.id.item_signup_detail_num);
        }
    }
}
