package com.guohe.onegame.model;


import com.guohe.onegame.MvpModel;
import com.guohe.onegame.model.template.BaseResultBean;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.ToastUtil;

import rx.Subscriber;

/**
 * Created by 水寒 on 2017/7/14.
 */

public class BaseModel implements MvpModel {

    public static final int RESULT_SUCCESS = 1;   //请求成功
    public static final int RESULT_MESSAGE = 2;   //提示消息
    public static final int RESULT_SYS_ERR = 3;   //系统异常

    public static abstract class OnResult<V> {
        public abstract void result(V response);
        public void onFaild(String faildMsg){
            ToastUtil.showToast(faildMsg);
        }
        public void completed(){ }
    }

    protected class ModelResult<T extends BaseResultBean> extends Subscriber<T> {

        private OnResult mResult;

        public ModelResult(OnResult result){
            mResult = result;
        }

        @Override
        public void onCompleted() {
            //TODO 做一些统一的完成操作
            mResult.completed();
        }

        @Override
        public void onError(Throwable e) {
            //TODO 做一些统一的错误处理
            LogUtil.e("请求接口出错 == " + e.getMessage());
            e.printStackTrace();
            ToastUtil.showToast("请求服务器出错");
            mResult.completed();
        }

        @Override
        public void onNext(T response) {
            switch (response.getStatus()){
                case RESULT_SUCCESS:
                    if(response.getData() == null){
                        mResult.result(response);
                    }else{
                        mResult.result(response.getData());
                    }
                    break;
                case RESULT_MESSAGE:
                    mResult.onFaild(response.getMsg());
                    break;
                case RESULT_SYS_ERR:
                    mResult.onFaild("系统错误");
                    break;
                default:
                    mResult.onFaild("未知错误");
                    break;
            }
        }
    }
}
