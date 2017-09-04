package com.guohe.onegame.model.template;

import com.guohe.onegame.model.entry.BaseBean;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/29.
 */

public class BaseResultListBean<T extends BaseBean> extends BaseResultBean<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
