package com.guohe.onegame.manage.rxbus.bean;

import com.amap.api.location.AMapLocation;

/**
 * Created by 水寒 on 2017/8/18.
 */

public class LocationRefreshEvent extends BaseBusEvent {

    private AMapLocation mapLocation;

    public LocationRefreshEvent(AMapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }

    public AMapLocation getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(AMapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }
}
