package com.guohe.onegame.model.template;

import com.guohe.onegame.model.entry.BaseBean;

/**
 * Created by shuihan on 2017/2/9.
 * 普通对象
 */

public class BaseResultObjectBean<T extends BaseBean> extends BaseResultBean<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
