package com.guohe.onegame.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.circle.DynamicDetailActivity;
import com.guohe.onegame.view.circle.MoreMenuActivity;
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

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment2 extends BaseMainFragment implements TakePhoto.TakeResultListener,InvokeListener {

    private static File mUploadFile = new File(Environment.getExternalStorageDirectory(), "/temp/publish.jpg");
    private static Uri mImageUri;
    private InvokeParam mInvokeParam;
    private TakePhoto mTakePhoto;
    private RecyclerView mRecyclerView;
    private DynamicAdapter mAdapter;
    private int mScreenWidth;
    private int mRecyclerViewScrollState;
    private ViewGroup mToolbar;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        getView(R.id.takephoto_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(MainFragment2.this.getContext())
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
        mToolbar = getView(R.id.main_page2_toolbar);
        mScreenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        mRecyclerView = getView(R.id.main_two_recycerview);
        bindRecyclerView();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRecyclerViewScrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mRecyclerViewScrollState == RecyclerView.SCROLL_STATE_SETTLING && Math.abs(dy) > 50){
                    LogUtil.d("dy == " + dy);
                    if(dy > 0){ //向上滚动
                        mToolbar.setVisibility(View.GONE);
                    }else{
                        mToolbar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        refreshView(R.id.main_page2_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new DynamicAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class DynamicAdapter extends RecyclerView.Adapter<DynamicViewHolder>{

        @Override
        public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DynamicViewHolder(LayoutInflater.from(MainFragment2.this.getContext())
                    .inflate(R.layout.item_main_dynamic_list, parent, false));
        }

        @Override
        public void onBindViewHolder(DynamicViewHolder holder, int position) {
            FrescoUtils.loadRes(holder.mPicture, R.mipmap.test_img1, null, 0, 0, null);
            holder.mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DynamicDetailActivity.startActivity(MainFragment2.this.getContext());
                }
            });
            holder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalPageActivity.startActivity(MainFragment2.this.getContext());
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(15), DimenUtil.dp2px(15));
            params.setMargins(0, 0, DimenUtil.dp2px(3), 0);
            holder.mFollowdArea.removeAllViews();
            for(int i = 0 ; i < 5; i++){
                SimpleDraweeView draweeView = new SimpleDraweeView(MainFragment2.this.getContext());
                draweeView.setLayoutParams(params);
                GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
                hierarchy.setPlaceholderImage(R.mipmap.default_header);
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                holder.mFollowdArea.addView(draweeView);
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalPageActivity.startActivity(MainFragment2.this.getContext());
                    }
                });
            }
            holder.mMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreMenuActivity.startActivity(MainFragment2.this.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mPicture;
        private LinearLayout mFollowdArea;
        private ImageButton mMoreButton;
        private SimpleDraweeView mHead;
        public DynamicViewHolder(View itemView) {
            super(itemView);
            mHead = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_head);
            mMoreButton = (ImageButton) itemView.findViewById(R.id.item_dynamic_more);
            mFollowdArea = (LinearLayout) itemView.findViewById(R.id.item_dynamic_followd_head_area);
            mPicture = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_picture);
            ViewGroup.LayoutParams params = mPicture.getLayoutParams();
            params.height = mScreenWidth;
            mPicture.setLayoutParams(params);
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
        PermissionManager.handlePermissionsResult(this.getActivity(), type, mInvokeParam, this);
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
