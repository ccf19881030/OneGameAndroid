package com.guohe.onegame.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.circle.DynamicDetailActivity;
import com.guohe.onegame.view.circle.MoreMenuActivity;
import com.guohe.onegame.view.circle.SearchAttentionActivity;
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

    private static File mUploadFile = new File(
            CustomeApplication.getApplication().getCacheDirPath(),
            CustomeApplication.FILE_DYNAMIC_ORIGIN);
    private static Uri mImageUri;
    private InvokeParam mInvokeParam;
    private TakePhoto mTakePhoto;
    private RecyclerView mRecyclerView;
    private DynamicAdapter mAdapter;
    private int mScreenWidth;
    private int mRecyclerViewScrollState;
    private ViewGroup mToolbar;
    private SmallBang mSamllBang;

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
        mSamllBang = SmallBang.attach2Window(this.getActivity());
        getView(R.id.takephoto_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configCompress(mTakePhoto);
                mTakePhoto.onPickFromDocuments();
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

        getView(R.id.search_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAttentionActivity.startActivity(MainFragment2.this.getContext());
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
        public void onBindViewHolder(final DynamicViewHolder holder, int position) {
            FrescoUtils.loadRes(holder.mPicture, TestImageUtil.getDynamicImgRes(), null, 0, 0, null);
            FrescoUtils.loadRes(holder.mHead, TestImageUtil.getHeadImgRes(), null, DimenUtil.dp2px(30), DimenUtil.dp2px(30), null);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(18), DimenUtil.dp2px(18));
            params.setMargins(0, 0, DimenUtil.dp2px(5), 0);
            holder.mFollowdArea.removeAllViews();
            for(int i = 0 ; i < 5; i++){
                SimpleDraweeView draweeView = new SimpleDraweeView(MainFragment2.this.getContext());
                draweeView.setLayoutParams(params);
                GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
                hierarchy.setPlaceholderImage(R.mipmap.default_header);
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                FrescoUtils.setCircle(draweeView, getResources().getColor(R.color.app_background));
                FrescoUtils.loadRes(draweeView, TestImageUtil.getHeadImgRes(), null, DimenUtil.dp2px(18), DimenUtil.dp2px(18), null);
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
                    MoreMenuActivity.startActivity(MainFragment2.this.getContext(), MoreMenuActivity.TYPE_PERSOANL, 2, 1);
                }
            });
            holder.mFollowdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSamllBang.bang(view, new SmallBangListener() {
                        @Override
                        public void onAnimationStart() {
                            holder.mFollowdButton.setImageResource(R.mipmap.icon_followed);
                        }

                        @Override
                        public void onAnimationEnd() {

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

       /* @Override
        public void onViewAttachedToWindow(DynamicViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            final List<TagItem> mTagItems = new ArrayList<>();
            TagItem tagItem = new TagItem(POST_TYPE_TAG, "这是一个可爱的标签");
            tagItem.setX(100);
            tagItem.setY(100);
            mTagItems.add(tagItem);
            final RelativeLayout imageOutLayout = holder.mImageOutLayout;
            if(mTagItems != null) {
                // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
                imageOutLayout.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (TagItem feedImageTag : mTagItems) {
                            LabelView tagView = new LabelView(MainFragment2.this.getContext());
                            tagView.init(feedImageTag);
                            tagView.draw(imageOutLayout,
                                    (int) (feedImageTag.getX() * ((double) imageOutLayout.getWidth() / (double) DynamicUtil.DEFAULT_PIXEL)),
                                    (int) (feedImageTag.getY() * ((double) imageOutLayout.getWidth() / (double) DynamicUtil.DEFAULT_PIXEL)),
                                    feedImageTag.isLeft());
                            tagView.wave();
                        }
                    }
                }, 200);
            }
        }

        @Override
        public void onViewRecycled(DynamicViewHolder holder) {
            // 将标签移除,避免回收使用时标签重复
            holder.mImageOutLayout.removeViews(1, holder.mImageOutLayout.getChildCount() - 1);
            super.onViewRecycled(holder);
        }*/
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mPicture;
        private LinearLayout mFollowdArea;
        private ImageButton mMoreButton;
        private SimpleDraweeView mHead;
        private ImageButton mFollowdButton;
        private RelativeLayout mImageOutLayout;
        public DynamicViewHolder(View itemView) {
            super(itemView);
            mImageOutLayout = (RelativeLayout) itemView.findViewById(R.id.item_dynamic_picture_outlayout);
            mHead = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_head);
            mMoreButton = (ImageButton) itemView.findViewById(R.id.item_dynamic_more);
            mFollowdArea = (LinearLayout) itemView.findViewById(R.id.item_dynamic_followd_head_area);
            mFollowdButton = (ImageButton) itemView.findViewById(R.id.item_dynamic_followed_button);
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
        int maxSize = 1024 * 512;  //500kb
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
        LogUtil.d("imagePath ..... " + imgPath);
        //ImageFilterActivity.startActivity(MainFragment2.this.getContext(), imgPath);
        DynamicUtil.processPhotoItem(MainFragment2.this.getActivity(), imgPath);
    }

    @Override
    public void takeFail(TResult result,String msg) {
        ToastUtil.showToast("压缩图片失败");
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
