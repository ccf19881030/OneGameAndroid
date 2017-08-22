package com.guohe.onegame.manage;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.guohe.onegame.CustomeApplication;

import java.io.File;
import java.util.UUID;

/**
 * Created by 水寒 on 2017/8/22.
 */

public class AliOssManager {

    public enum FileType{
        HEAD,
        DYNAMIC,
        TEAM_LOGO
    }

    private static String END_POINT = "http://onegame.oss-cn-beijing.aliyuncs.com";
    private OSS mOss;
    private static AliOssManager mInstance;

    private AliOssManager(){
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("wSBJLkN3evuaSc03", "NjMCMYJkbVKPR5em2IB8SeE6MN2HCw", "<StsToken.SecurityToken>");
        mOss = new OSSClient(CustomeApplication.getApplication(), END_POINT, credentialProvider);
    }

    public static AliOssManager getInstance(){
        if(mInstance == null){
            mInstance = new AliOssManager();
        }
        return mInstance;
    }

    public interface UploadResult{
        void onProgress(long currentSize, long totalSize);
        void onSuccess(String url);
        void onFail(String errMsg);
    }

    public void uploadFile(File file, FileType fileType, final UploadResult uploadResult){
        String objectkey;
        switch (fileType){
            case HEAD:
                objectkey = "/user/head/img_head_" + UUID.randomUUID() + ".jpg";
                break;
            case DYNAMIC:
                objectkey = "/user/dynamic/img_dynamic_" + UUID.randomUUID() + ".jpg";
                break;
            case TEAM_LOGO:
                objectkey = "/user/team/img_logo_" + UUID.randomUUID() + ".jpg";
                break;
            default:
                objectkey = "";
                break;
        }
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest("onegame", objectkey, file.getAbsolutePath());
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if(uploadResult != null){
                    uploadResult.onProgress(currentSize, totalSize);
                }
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                if(uploadResult != null){
                    uploadResult.onSuccess(request.getUploadFilePath());
                }
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    if(uploadResult != null){
                        uploadResult.onFail("网络异常");
                    }
                }
                if (serviceException != null) {
                    if(uploadResult != null){
                        uploadResult.onFail("文件服务器异常");
                    }
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        task.waitUntilFinished();
    }
}
