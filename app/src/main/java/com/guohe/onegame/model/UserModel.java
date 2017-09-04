package com.guohe.onegame.model;

import com.guohe.onegame.manage.http.ModelApiManage;
import com.guohe.onegame.manage.http.RequestParam;
import com.guohe.onegame.model.entry.UserInfo;
import com.guohe.onegame.model.template.BaseResultObjectBean;

/**
 * Created by 水寒 on 2017/9/2.
 */

public class UserModel extends BaseModel {


    public void requestLogin(String mobile, OnResult<UserInfo> result){
        RequestParam requestParam = new RequestParam.Builder()
                .setParam("phone", mobile)
                .setParam("type", 0)
                .builder();
        ModelApiManage.requestApiOnResult(ModelApiManage.API().postLoginService(requestParam.getParams()),
                new ModelResult<BaseResultObjectBean<UserInfo>>(result));
    }
}
