package com.guohe.onegame.presenter;

import com.guohe.onegame.model.BaseModel;
import com.guohe.onegame.model.UserModel;
import com.guohe.onegame.model.entry.UserInfo;

/**
 * Created by 水寒 on 2017/8/28.
 */

public class AccountPresenter extends BasePresenter {

    private UserModel mUserModel;

    public AccountPresenter() {
        mUserModel = new UserModel();
    }

    public void requestLoginByMobile(String mobile, final OnPresenterResult<Boolean> loginSuccess){
        mUserModel.requestLogin(mobile, new BaseModel.OnResult<UserInfo>() {
            @Override
            public void result(UserInfo response) {
                loginSuccess.result(true);
            }
        });
    }

    public void requestLoginByWeixin(){

    }

    public void requestLoginByQQ(){

    }

}

