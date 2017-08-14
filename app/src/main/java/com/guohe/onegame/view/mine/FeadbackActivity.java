package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.WeakRefrenceHandler;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 意见反馈
 */

public class FeadbackActivity extends BaseActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener {

    private static final int HAND_TAKE_PHOTO = 0x0001;
    private static File mUploadFile = new File(Environment.getExternalStorageDirectory(), "/temp/feadback_" + System.currentTimeMillis() + ".jpg");
    private static Uri mImageUri;
    private InvokeParam mInvokeParam;
    private TakePhoto mTakePhoto;
    private View mImagesArea;
    private List<View> mImgAreas = new ArrayList<>();
    private List<SimpleDraweeView> mImages = new ArrayList<>();
    private List<String> mImgPaths = new ArrayList<>();

    private WeakRefrenceHandler<FeadbackActivity> mHandler = new WeakRefrenceHandler<FeadbackActivity>(this) {
        @Override
        protected void handleMessage(FeadbackActivity ref, Message msg) {
            switch (msg.what){
                case HAND_TAKE_PHOTO:
                    if(mImgPaths.isEmpty()){
                        for(View area : mImgAreas){
                            area.setVisibility(View.GONE);
                        }
                        mImagesArea.setVisibility(View.GONE);
                    }else {
                        mImagesArea.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mImgAreas.size(); i++) {
                            if (i < mImgPaths.size()) {
                                mImgAreas.get(i).setVisibility(View.VISIBLE);
                                FrescoUtils.loadFile(mImages.get(i), mImgPaths.get(i), null, DimenUtil.dp2px(80),
                                        DimenUtil.dp2px(80), null);
                            } else {
                                mImgAreas.get(i).setVisibility(View.GONE);
                            }
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("意见反馈");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feadback;
    }

    @Override
    protected void initView() {
        mImagesArea = getView(R.id.feadback_images_area);
        mImgAreas.add(getView(R.id.feadback_img1_area));
        mImgAreas.add(getView(R.id.feadback_img2_area));
        mImgAreas.add(getView(R.id.feadback_img3_area));
        mImages.add((SimpleDraweeView)getView(R.id.feadback_img1));
        mImages.add((SimpleDraweeView)getView(R.id.feadback_img2));
        mImages.add((SimpleDraweeView)getView(R.id.feadback_img3));
        getView(R.id.feedback_takephoto_camera).setOnClickListener(this);
        getView(R.id.feedback_takephoto_picture).setOnClickListener(this);
        getView(R.id.feadback_img1_delete).setOnClickListener(this);
        getView(R.id.feadback_img2_delete).setOnClickListener(this);
        getView(R.id.feadback_img3_delete).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, FeadbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.feedback_takephoto_camera || view.getId() == R.id.feedback_takephoto_picture){
            if(mImgPaths.size() >= 3){
                ToastUtil.showToast("最多选择3张图片");
                return;
            }
            if (!mUploadFile.getParentFile().exists())mUploadFile.getParentFile().mkdirs();
            mImageUri = Uri.fromFile(mUploadFile);
            configCompress(mTakePhoto);
        }
        switch (view.getId()){
            case R.id.feedback_takephoto_camera:
                mTakePhoto.onPickFromCapture(mImageUri);
                break;
            case R.id.feedback_takephoto_picture:
                mTakePhoto.onPickFromDocuments();
                break;
            case R.id.feadback_img1_delete:
                if(mImgPaths.size() >= 1){
                    mImgPaths.remove(0);
                }
                mHandler.sendEmptyMessage(HAND_TAKE_PHOTO);
                break;
            case R.id.feadback_img2_delete:
                if(mImgPaths.size() >= 2){
                    mImgPaths.remove(1);
                }
                mHandler.sendEmptyMessage(HAND_TAKE_PHOTO);
                break;
            case R.id.feadback_img3_delete:
                if(mImgPaths.size() >= 3){
                    mImgPaths.remove(2);
                }
                mHandler.sendEmptyMessage(HAND_TAKE_PHOTO);
                break;
        }
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (mTakePhoto == null){
            mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return mTakePhoto;
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1024 * 100;  //50kb
        int width = 800;
        int height = 800;
        boolean showProgressBar = true;
        boolean enableRawFile = false;
        CompressConfig config;
        if (false) {
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
        TakePhotoOptions options = new TakePhotoOptions.Builder()
                .setCorrectImage(true)
                .create();
        takePhoto.setTakePhotoOptions(options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this, type, mInvokeParam, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void takeSuccess(TResult result) {
        String imgPath = result.getImage().getCompressPath();
        mImgPaths.add(imgPath);
        mHandler.sendEmptyMessage(HAND_TAKE_PHOTO);
    }

    @Override
    public void takeFail(TResult result,String msg) {

    }
    @Override
    public void takeCancel() {
        LogUtil.d(getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.mInvokeParam = invokeParam;
        }
        return type;
    }
}
