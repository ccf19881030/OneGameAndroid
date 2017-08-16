package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.SmallBang;
import com.guohe.onegame.custome.SmallBangListener;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.DynamicUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.mine.PersonalPageActivity;
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
import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 动态详情
 */

public class DynamicDetailActivity extends BaseActivity implements TakePhoto.TakeResultListener,InvokeListener {

    private static File mUploadFile = new File(
            CustomeApplication.getApplication().getCacheDirPath(),
            CustomeApplication.FILE_DYNAMIC_ORIGIN);
    private static Uri mImageUri;
    private InvokeParam mInvokeParam;
    private TakePhoto mTakePhoto;
    private SimpleDraweeView mPicture;
    private LinearLayout mFollowdArea;
    private ImageButton mMoreButton;
    private SimpleDraweeView mHead;
    private ImageButton mFollowButton;
    private SmallBang mSamllBang;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("昵称哦");
        moreButton.setVisibility(View.VISIBLE);
        moreButton.setImageResource(R.mipmap.icon_camera_black);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(DynamicDetailActivity.this)
                        .title("发布动态")
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
                                    mTakePhoto.onPickFromCapture(mImageUri);
                                }else{
                                    mTakePhoto.onPickFromDocuments();
                                }
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    protected void initView() {
        mSamllBang = SmallBang.attach2Window(this);
        mHead = getView(R.id.item_dynamic_head);
        mMoreButton = getView(R.id.item_dynamic_more);
        mFollowdArea = getView(R.id.item_dynamic_followd_head_area);
        mPicture = getView(R.id.item_dynamic_picture);
        ViewGroup.LayoutParams params = mPicture.getLayoutParams();
        params.height = GlobalConfigManage.getInstance().getScreenWidth();
        mPicture.setLayoutParams(params);
        mHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalPageActivity.startActivity(DynamicDetailActivity.this);
            }
        });
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreMenuActivity.startActivity(DynamicDetailActivity.this);
            }
        });

        mFollowButton = getView(R.id.dyanmic_detail_follow_button);
        mFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSamllBang.bang(view, new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                        mFollowButton.setImageResource(R.mipmap.icon_followed);
                    }

                    @Override
                    public void onAnimationEnd() {

                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        FrescoUtils.loadRes(mPicture, TestImageUtil.getDynamicImgRes(), null, 0, 0, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(18), DimenUtil.dp2px(18));
        params.setMargins(0, 0, DimenUtil.dp2px(5), 0);
        mFollowdArea.removeAllViews();
        for(int i = 0 ; i < 5; i++){
            SimpleDraweeView draweeView = new SimpleDraweeView(DynamicDetailActivity.this);
            draweeView.setLayoutParams(params);
            GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
            hierarchy.setPlaceholderImage(R.mipmap.default_header);
            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
            mFollowdArea.addView(draweeView);
            draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalPageActivity.startActivity(DynamicDetailActivity.this);
                }
            });
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, DynamicDetailActivity.class);
        context.startActivity(intent);
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
        int maxSize = 1024 * 1024;  //1M
        int width = (int)DynamicUtil.DEFAULT_PIXEL;
        int height = (int)DynamicUtil.DEFAULT_PIXEL;
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
        if (requestCode == CropPhotoActivity.REQUEST_CROP && resultCode == RESULT_OK) {
            PhotoProcessActivity.startActivity(this, data.getData());
        }
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void takeSuccess(TResult result) {
        String imgPath = result.getImage().getCompressPath();
        DynamicUtil.processPhotoItem(DynamicDetailActivity.this, imgPath);
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
