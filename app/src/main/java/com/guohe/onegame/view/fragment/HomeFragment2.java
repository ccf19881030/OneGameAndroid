package com.guohe.onegame.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.team.PlaceMapActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/19.
 */

public class HomeFragment2 extends BaseHomeFragment {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_page2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        TextView location = getView(R.id.test_location);
        AMapLocation mapLocation = CustomeApplication.mLocationClient.getLastKnownLocation();
        if(mapLocation != null) {
            location.setText("当前城市:" + mapLocation.getCity());
        }

        getView(R.id.test_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceMapActivity.startActivity(HomeFragment2.this.getContext());
            }
        });
    }
}
