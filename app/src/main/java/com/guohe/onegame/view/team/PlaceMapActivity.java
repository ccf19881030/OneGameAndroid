package com.guohe.onegame.view.team;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.FootballField;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.SplashActivity;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.dialog.DialogChooseMapService;
import com.wou.commonutils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeResource;
import static com.guohe.onegame.CustomeApplication.mLocationClient;

/**
 * Created by 水寒 on 2017/8/17.
 * 场地地图
 */

public class PlaceMapActivity extends BaseActivity implements View.OnClickListener{

    private MapView mMapView = null;
    private AMap mAmap;
    private RotateAnimation mRotateAnim;
    private FloatingActionButton mRefreshButton;
    private List<FootballField> mFootballFields = new ArrayList<>();
    private List<Marker> mMarkes = new ArrayList<>();
    private TextView mTitleText;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        mAmap = mMapView.getMap();
        mAmap.setCustomMapStylePath(SplashActivity.mapFilePath);
        mAmap.setMapCustomEnable(true);
        UiSettings uiSettings = mAmap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        //mAmap.moveCamera(CameraUpdateFactory.zoomTo(15));
        AMapLocation mapLocation = CustomeApplication.mLocationClient.getLastKnownLocation();
        mAmap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mapLocation.getLatitude(), mapLocation.getLongitude()), 12));
        mMapView.onCreate(savedInstanceState);
        mAmap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        mAmap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                FootballPlaceActivity.startActivity(PlaceMapActivity.this);
            }
        });
        mAmap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(StringUtils.isEmpty(marker.getTitle())) return true;
                marker.showInfoWindow();
                return true;
            }
        });
        locationMark();
        poiSearchPlace(CustomeApplication.mLocationClient.getLastKnownLocation().getCityCode());
    }

    private void poiSearchPlace(String cityCode) {
        PoiSearch.Query query = new PoiSearch.Query("", "080105", cityCode);
        query.setPageNum(100);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                LogUtil.d("onPoiSearch——result" + rCode + "," + poiResult.getPois().size());
                if(rCode == 1000) {
                    List<PoiItem> pois = poiResult.getPois();
                    mFootballFields.clear();
                    for(PoiItem poi : pois){
                        LatLonPoint point = poi.getLatLonPoint();
                        mFootballFields.add(new FootballField(poi.getTitle(),
                                point.getLatitude(), point.getLongitude()));
                    }
                    addMapMarks();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int errorCode) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    private void locationMark(){
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(1000 * 60); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(
                decodeResource(getResources(),R.mipmap.icon_map_self_location)));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        myLocationStyle.anchor(0.5f, 1.0f);
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    private void addMapMarks(){
        ArrayList<MarkerOptions> options = new ArrayList<>();
        for(FootballField field : mFootballFields){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(
                    new LatLng(field.getLatitude(), field.getLongitude()));
            markerOption.title(field.getLabel());
            markerOption.draggable(false);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(
                    decodeResource(getResources(),R.mipmap.icon_map_place)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            options.add(markerOption);
        }

        mMarkes.clear();
        mMarkes.addAll(mAmap.addMarkers(options, false));
        for(Marker marker : mMarkes) {
            com.amap.api.maps.model.animation.Animation markerAnimation
                    = new ScaleAnimation(0, 1, 0, 1); //初始化生长效果动画
            markerAnimation.setDuration(1000);  //设置动画时间 单位毫秒
            marker.setAnimation(markerAnimation);
        }
    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        mTitleText = titleText;
        mTitleText.setText(CustomeApplication.mLocationClient.getLastKnownLocation().getCity() + "球场");
        moreButton.setVisibility(View.VISIBLE);
        moreButton.setImageResource(R.mipmap.icon_map_search_icon);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPlaceActivity.startActivity(PlaceMapActivity.this);
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_place_map;
    }

    @Override
    protected void initView() {
        getView(R.id.map_button_location).setOnClickListener(this);
        mRefreshButton = getView(R.id.map_button_refresh);
        mRefreshButton.setOnClickListener(this);
        getView(R.id.map_button_city).setOnClickListener(this);
        getView(R.id.map_button_service).setOnClickListener(this);
        new ObjectAnimator();
        mRotateAnim = new RotateAnimation(0f, 270f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnim.setRepeatCount(1);
        mRotateAnim.setDuration(500);
        mRotateAnim.setInterpolator(new LinearInterpolator()); //设置匀速旋转，不卡顿
        mRefreshButton.setAnimation(mRotateAnim);
        mRotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRefreshButton.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshButton.setVisibility(View.VISIBLE);
                    }
                }, 5000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, PlaceMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.map_button_location:
                AMapLocation mapLocation = mLocationClient.getLastKnownLocation();
                //然后可以移动到定位点,使用animateCamera就有动画效果
                mAmap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mapLocation.getLatitude(), mapLocation.getLongitude()), 12));
                poiSearchPlace(mapLocation.getCityCode());
                mTitleText.setText(mapLocation.getCity() + "球场");
                break;
            case R.id.map_button_refresh:
               /* for(Marker marker : mMarkes){
                    marker.startAnimation();
                }*/
                if(mMarkes.isEmpty()) return;
                new CountDownTimer(mMarkes.size() * 100, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        int position = (int)(millisUntilFinished / 200);
                        LogUtil.d("millisUntilFinished == " + millisUntilFinished);
                        LogUtil.d("position == " + position);
                        mMarkes.get(position).startAnimation();
                    }

                    @Override
                    public void onFinish() {
                        mMarkes.get(0).startAnimation();
                    }
                }.start();
                mRefreshButton.startAnimation(mRotateAnim);
                break;
            case R.id.map_button_service:
                new DialogChooseMapService()
                        .setOnMenuError(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PlaceErrorReportActivity.startActivity(PlaceMapActivity.this);
                            }
                        })
                        .setOnMenuService(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PlaceServiceReportActivity.startActivity(PlaceMapActivity.this);
                            }
                        })
                        .show(getFragmentManager());
                break;
            case R.id.map_button_city:
                new MaterialDialog.Builder(this)
                        .title("切换城市")
                        .content("其他城市敬请期待...")
                        .items(R.array.map_citys)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                getLatlon(text.toString());
                            }
                        })
                        .show();
                break;
        }
    }

    private void getLatlon(final String cityName){

        GeocodeSearch geocodeSearch=new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        mAmap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude, longititude), 12));
                        poiSearchPlace(CustomeApplication.mCitys.get(cityName));
                        mTitleText.setText(cityName + "球场");
                    }else {
                        ToastUtil.showToast("地址名出错了");
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    class MyInfoWindowAdapter implements AMap.InfoWindowAdapter {

        private TextView inforWindow;

        @Override
        public View getInfoWindow(Marker marker) {
            if(inforWindow == null) {
                inforWindow = new TextView(PlaceMapActivity.this);
                inforWindow.setGravity(Gravity.CENTER);
                inforWindow.setBackgroundResource(R.drawable.icon_map_marker_infowindow);
                inforWindow.setTextColor(getResources().getColor(R.color.app_textcolor));
            }
            inforWindow.setText(marker.getTitle());
            return inforWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
