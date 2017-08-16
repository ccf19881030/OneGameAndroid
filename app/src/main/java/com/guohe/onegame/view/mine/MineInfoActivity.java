package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/13.
 * 个人信息界面
 */

public class MineInfoActivity extends BaseActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener {

    private static File mUploadFile = new File(
            CustomeApplication.getApplication().getCacheDirPath(),
            CustomeApplication.FILE_HEADER);
    private static Uri mImageUri;
    private InvokeParam mInvokeParam;
    private TakePhoto mTakePhoto;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("个人信息");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_info;
    }

    @Override
    protected void initView() {
        getView(R.id.mine_info_head).setOnClickListener(this);
        getView(R.id.mine_info_role).setOnClickListener(this);
        getView(R.id.mine_info_age).setOnClickListener(this);
        getView(R.id.mine_info_nickname).setOnClickListener(this);
        getView(R.id.mine_info_name).setOnClickListener(this);
        getView(R.id.mine_info_certification).setOnClickListener(this);
        getView(R.id.mine_info_mobile).setOnClickListener(this);
        getView(R.id.mine_info_weixin).setOnClickListener(this);
        getView(R.id.mine_info_qq).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MineInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_info_head:
                new MaterialDialog.Builder(this)
                        .title("设置头像")
                        .items(R.array.take_photo_type)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(mImageUri == null) {
                                    if (!mUploadFile.getParentFile().exists())mUploadFile.getParentFile().mkdirs();
                                    mImageUri = Uri.fromFile(mUploadFile);
                                }
                                configCompress(mTakePhoto);
                                if("拍照上传".equals(text)){
                                    mTakePhoto.onPickFromCaptureWithCrop(mImageUri, getCropOptions());
                                }else{
                                    mTakePhoto.onPickFromDocumentsWithCrop(mImageUri, getCropOptions());
                                }
                            }
                        })
                        .show();
                break;
            case R.id.mine_info_role:

                break;
            case R.id.mine_info_age:
                new MaterialDialog.Builder(this)
                        .title("修改年龄")
                        .content("年龄不能小于18岁，不能大于60岁")
                        .inputRangeRes(2, 2, R.color.app_error)
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL)
                        .input("输入年龄", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.mine_info_nickname:
                new MaterialDialog.Builder(this)
                        .title("修改昵称")
                        .content("昵称最长不能超过8个字，最短不能小于2个字")
                        .inputRangeRes(2, 8, R.color.app_error)
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                        .input("新昵称...", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.mine_info_name:

                break;
            case R.id.mine_info_certification:

                break;
            case R.id.mine_info_mobile:
                BindMobileActivity.startActivity(this);
                break;
            case R.id.mine_info_weixin:

                break;
            case R.id.mine_info_qq:

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
            //mTakePhoto.onEnableCompress(getCompressConfig(), true);
        }
        return mTakePhoto;
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1024 * 50;  //50kb
        int width = 200;
        int height = 200;
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

    private static CropOptions getCropOptions(){
        int height= DimenUtil.dp2px(100);
        int width= DimenUtil.dp2px(100);
        boolean withWonCrop= true;

        CropOptions.Builder builder=new CropOptions.Builder();

        builder.setAspectX(width).setAspectY(height);
        //builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
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
        String imgPath = result.getImage().getOriginalPath();

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
