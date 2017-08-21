package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.mobike.library.MobikeView;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/21.
 */

public class MobiDemo extends BaseActivity {

    private MobikeView mobikeView;
    private SensorManager sensorManager;
    private Sensor defaultSensor;

    private int[] imgs = {
            R.mipmap.test_head_img1,
            R.mipmap.test_head_img2,
            R.mipmap.test_head_img3,
            R.mipmap.test_head_img4,
            R.mipmap.test_head_img5,
            R.mipmap.test_head_img6,
            R.mipmap.test_head_img7
    };

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initView() {
        mobikeView = (MobikeView) findViewById(R.id.mobike_view);
        initMobikeTiezhi();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void initMobikeTiezhi() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(DimenUtil.dp2px(40), DimenUtil.dp2px(40));
        layoutParams.gravity = Gravity.CENTER;
        for(int i = 0; i < imgs.length  ; i ++){
            SimpleDraweeView draweeView = new SimpleDraweeView(this);
            draweeView.setLayoutParams(layoutParams);
            GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
            hierarchy.setPlaceholderImage(R.mipmap.default_header);
            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
            FrescoUtils.setCircle(draweeView, getResources().getColor(R.color.app_background));
            FrescoUtils.loadRes(draweeView, TestImageUtil.getHeadImgRes(), null, DimenUtil.dp2px(40), DimenUtil.dp2px(40), null);
            /*SimpleDraweeView imageView = new SimpleDraweeView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.mobike_view_circle_tag,true);*/
            mobikeView.addView(draweeView, layoutParams);
        }
    }

    @Override
    protected void initData() {

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

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MobiDemo.class);
        context.startActivity(intent);
    }
}
