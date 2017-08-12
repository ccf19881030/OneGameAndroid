package com.guohe.onegame.util;

import android.widget.Toast;

import com.guohe.onegame.CustomeApplication;
import com.wou.commonutils.StringUtils;

/**
 * Created by shuihan on 2016/12/7.
 */

public class ToastUtil {

    /**
     * 显示一个toast到界面上
     * @param message
     */
    public static void showToast(String message){
        if(StringUtils.isEmpty(message)) return;
        Toast.makeText(CustomeApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
