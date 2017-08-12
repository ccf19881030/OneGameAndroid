package com.guohe.onegame.model.entry;

/**
 * Created by 水寒 on 2017/8/12.
 */

public class ScrollBanner extends BaseBean {

    private String url;

    public ScrollBanner(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
