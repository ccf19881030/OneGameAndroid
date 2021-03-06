package com.guohe.onegame.manage.http;

import com.guohe.onegame.model.entry.UserInfo;
import com.guohe.onegame.model.template.BaseResultObjectBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by shuihan on 2016/12/8.
 * 服务器接口管理类
 */

public class ModelApiManage {

    private static ModelApiManage mInstance = new ModelApiManage();

    private ApiService mApiService;

    private ModelApiManage(){
        mApiService = RetrofitManage.getInstance().getRetrofit().create(ApiService.class);
    }

    public static ModelApiManage getInstance(){
        return mInstance;
    }

    public ApiService getApiService(){
        return mApiService;
    }

    public static ApiService API(){
        return ModelApiManage.getInstance().getApiService();
    }

    public static <T> Observable requestApiOnResult(Observable<T> observable, Subscriber<? super T> subscriber){
        observable.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return observable;
    }

    /**
     * 服务器相关的接口定义
     */
    public interface ApiService{


        /**
         * 登录接口
         * @param params
         * @return
         */
        @FormUrlEncoded
        @POST("user/login")
        Observable<BaseResultObjectBean<UserInfo>> postLoginService(@FieldMap Map<String, Object> params);

    }
}
