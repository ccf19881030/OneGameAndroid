package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.CustomeApplication;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.FullyGridLayoutManager;
import com.guohe.onegame.custome.SmallBang;
import com.guohe.onegame.custome.SmallBangListener;
import com.guohe.onegame.custome.imageFilter.LabelView;
import com.guohe.onegame.entry.TagItem;
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
import java.util.ArrayList;
import java.util.List;

import static com.guohe.onegame.custome.imageFilter.LabelView.POST_TYPE_POI;
import static com.guohe.onegame.custome.imageFilter.LabelView.POST_TYPE_TAG;

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
    private ImageButton mMoreButton;
    private SimpleDraweeView mHead;
    private ImageButton mFollowButton;
    private SmallBang mSamllBang;
    private RelativeLayout mImageOutLayout;
    private List<TagItem> mTagItems;
    private RecyclerView mRecyclerView;
    private FollowdAdapter mAdapter;
    private TextView mExpandButton;

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
                configCompress(mTakePhoto);
                mTakePhoto.onPickFromDocuments();
                /*new MaterialDialog.Builder(DynamicDetailActivity.this)
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
                        .show();*/
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    protected void initView() {
        mImageOutLayout = getView(R.id.item_dynamic_picture_outlayout);
        mImageOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mImageOutLayout.getChildCount() > 1){
                    hideTagItems();
                }else{
                    showTagItems();
                }
            }
        });
        mSamllBang = SmallBang.attach2Window(this);
        mHead = getView(R.id.item_dynamic_head);
        mMoreButton = getView(R.id.item_dynamic_more);
        mPicture = getView(R.id.item_dynamic_picture);
        mExpandButton = getView(R.id.item_dynamic_expand_button);
        mExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = String.valueOf(view.getTag());
                if("closed".equals(tag)){
                    mExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_close_arrow, 0);
                    mExpandButton.setText("收起36个赞");
                    mRecyclerView.setVisibility(View.VISIBLE);
                    view.setTag("expand");
                }else{
                    mExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_expand_arrow, 0);
                    mExpandButton.setText("展开36个赞");
                    mRecyclerView.setVisibility(View.GONE);
                    view.setTag("closed");
                }
            }
        });
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
                MoreMenuActivity.startActivity(DynamicDetailActivity.this, MoreMenuActivity.TYPE_DYNAMIC, 2, 1);
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
        mRecyclerView = getView(R.id.item_dynamic_followed_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        final int num = (GlobalConfigManage.getInstance().getScreenWidth() - DimenUtil.dp2px(36)) / DimenUtil.dp2px(33);
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(this, num));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if(position % num == 0){
                    outRect.top = DimenUtil.dp2px(5);
                }else{
                    outRect.top = DimenUtil.dp2px(5);
                    outRect.left = DimenUtil.dp2px(3);
                }
            }
        });
        mAdapter = new FollowdAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        FrescoUtils.loadRes(mPicture, TestImageUtil.getDynamicImgRes(), null, 0, 0, null);
        mTagItems = new ArrayList<>();
        TagItem tagItem1 = new TagItem(POST_TYPE_TAG, "这是一个可爱的标签");
        tagItem1.setX(100);
        tagItem1.setY(100);
        TagItem tagItem2 = new TagItem(POST_TYPE_TAG, "我淘气吧！");
        tagItem2.setX(GlobalConfigManage.getInstance().getScreenWidth() / 2);
        tagItem2.setY(GlobalConfigManage.getInstance().getScreenWidth() / 2);
        tagItem2.setLeft(false);
        TagItem tagItem3 = new TagItem(POST_TYPE_POI, "西安.大雁塔");
        tagItem3.setX(300);
        tagItem3.setY(GlobalConfigManage.getInstance().getScreenWidth() - 230);
        mTagItems.add(tagItem1);
        mTagItems.add(tagItem2);
        mTagItems.add(tagItem3);
        showTagItems();
    }

    private void hideTagItems(){
        // 将标签移除,避免回收使用时标签重复
       mImageOutLayout.removeViews(1, mImageOutLayout.getChildCount() - 1);
    }

    private void showTagItems(){
        if(mTagItems == null) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (TagItem feedImageTag : mTagItems) {
                    LabelView tagView = new LabelView(DynamicDetailActivity.this);
                    tagView.init(feedImageTag);
                    tagView.draw(mImageOutLayout,
                            (int) (feedImageTag.getX() * ((double) mImageOutLayout.getWidth() / (double) DynamicUtil.DEFAULT_PIXEL)),
                            (int) (feedImageTag.getY() * ((double) mImageOutLayout.getWidth() / (double) DynamicUtil.DEFAULT_PIXEL)),
                            feedImageTag.isLeft());
                    tagView.wave();
                }
            }
        }, 200);
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

    class FollowdAdapter extends RecyclerView.Adapter<FollowdViewHolder>{

        @Override
        public FollowdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FollowdViewHolder((LayoutInflater.from(DynamicDetailActivity.this)
                    .inflate(R.layout.item_main_dynamic_followed_list, parent, false)));
        }

        @Override
        public void onBindViewHolder(FollowdViewHolder holder, int position) {
            FrescoUtils.setCircle(holder.mFollowdHead, getResources().getColor(R.color.app_background));
            FrescoUtils.loadRes(holder.mFollowdHead, TestImageUtil.getHeadImgRes(),
                    null, DimenUtil.dp2px(30), DimenUtil.dp2px(30), null);
            holder.mFollowdHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalPageActivity.startActivity(DynamicDetailActivity.this);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class FollowdViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView mFollowdHead;
        public FollowdViewHolder(View itemView) {
            super(itemView);
            mFollowdHead = (SimpleDraweeView) itemView.findViewById(R.id.item_dynamic_followed_head);
        }
    }

}
