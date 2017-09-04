package com.guohe.onegame.model.template;

import com.guohe.onegame.model.entry.BaseBean;

/**
 * Created by shuihan on 2017/2/9.
 * 状态结果
 */

public class BaseResultBean<T> extends BaseBean {

    public int status;
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return null;
    }

}
