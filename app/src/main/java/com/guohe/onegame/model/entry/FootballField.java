package com.guohe.onegame.model.entry;

/**
 * Created by 水寒 on 2017/8/18.
 * 足球场
 */

public class FootballField extends BaseBean{

    private int id;
    private String city;
    private String label;
    public double latitude;
    public double longitude;

    public FootballField(String label, double latitude, double longitude) {
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
