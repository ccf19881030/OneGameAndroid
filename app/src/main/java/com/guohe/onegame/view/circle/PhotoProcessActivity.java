package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.imageFilter.EffectService;
import com.guohe.onegame.custome.imageFilter.FilterEffect;
import com.guohe.onegame.custome.imageFilter.GPUImageFilterTools;
import com.guohe.onegame.custome.imageFilter.LabelSelector;
import com.guohe.onegame.custome.imageFilter.LabelView;
import com.guohe.onegame.custome.imageFilter.MyHighlightView;
import com.guohe.onegame.custome.imageFilter.MyImageViewDrawableOverlay;
import com.guohe.onegame.entry.Addon;
import com.guohe.onegame.entry.TagItem;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DynamicUtil;
import com.guohe.onegame.util.FileUtils;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.adapter.FilterAdapter;
import com.guohe.onegame.view.adapter.StickerToolAdapter;
import com.guohe.onegame.view.base.BaseActivity;
import com.imagezoom.ImageViewTouch;
import com.wou.commonutils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

import static com.guohe.onegame.custome.imageFilter.LabelView.POST_TYPE_TAG;

/**
 * Created by 水寒 on 2017/8/15.
 * 处理图片
 */

public class PhotoProcessActivity extends BaseActivity{

    public static final int ACTION_EDIT_LABEL = 8080;
    public static final int ACTION_EDIT_LABEL_POI = 9090;

    //滤镜图片
    GPUImageView mGPUImageView;
    //绘图区域
    ViewGroup drawArea;
    //底部按钮
    TextView stickerBtn;
    TextView filterBtn;
    TextView labelBtn;
    //工具区
    HListView bottomToolBar;
    ViewGroup toolArea;
    private MyImageViewDrawableOverlay mImageView;
    private LabelSelector labelSelector;

    //当前选择底部按钮
    private TextView currentBtn;
    //当前图片
    private Bitmap currentBitmap;
    //小白点标签
    private LabelView emptyLabelView;
    //标签区域
    private View commonLabelArea;
    //用于预览的小图片
    private Bitmap smallImageBackgroud;

    private List<LabelView> labels = new ArrayList<LabelView>();

    private MaterialDialog mProcessDialog;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("个性化图片");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("下一步");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture();
            }
        });
    }

    //保存图片
    private void savePicture(){
        //加滤镜
        final Bitmap newBitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        RectF dst = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
        try {
            cv.drawBitmap(mGPUImageView.capture(), null, dst, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            cv.drawBitmap(currentBitmap, null, dst, null);
        }
        //加贴纸水印
        DynamicUtil.applyOnSave(cv, mImageView);

        new SavePicToFileTask().execute(newBitmap);
    }

    private class SavePicToFileTask extends AsyncTask<Bitmap,Void,String> {
        Bitmap bitmap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProcessDialog = new MaterialDialog.Builder(PhotoProcessActivity.this)
                    .title("请稍后")
                    .content("图片正在处理中...")
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            String fileName = null;
            try {
                bitmap = params[0];
                String picName = "picture_processed_temp.jpg";
                fileName = DynamicUtil.saveToFile(FileUtils.getInst().getPhotoSavedPath() + "/"+ picName, false, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.showToast("图片处理错误，请返回并重试");
            }
            return fileName;
        }

        @Override
        protected void onPostExecute(String fileName) {
            super.onPostExecute(fileName);
            if(mProcessDialog != null){
                mProcessDialog.dismiss();
            }
            if (StringUtils.isEmpty(fileName)) {
                return;
            }

            //将照片信息保存至sharedPreference
            //保存标签信息
            ArrayList<TagItem> tagInfoList = new ArrayList<TagItem>();
            for (LabelView label : labels) {
                tagInfoList.add(label.getTagInfo());
            }

            ToastUtil.showToast("图片保存成功：" + fileName);


            PublishDynamicActivity.startActivity(PhotoProcessActivity.this, fileName, tagInfoList);

            //将图片信息通过EventBus发送到MainActivity
            //FeedItem feedItem = new FeedItem(tagInfoList,fileName);
            //EventBus.getDefault().post(feedItem);
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_photo_process;
    }

    @Override
    protected void initView() {
        //滤镜图片
        mGPUImageView = getView(R.id.gpuimage);
        //绘图区域
        drawArea = getView(R.id.drawing_view_container);
        //底部按钮
        stickerBtn = getView(R.id.sticker_btn);
        filterBtn = getView(R.id.filter_btn);
        labelBtn = getView(R.id.text_btn);
        //工具区
        bottomToolBar = getView(R.id.list_tools);
        toolArea = getView(R.id.toolbar_area);

        //添加贴纸水印的画布
        View overlay = LayoutInflater.from(PhotoProcessActivity.this).inflate(
                R.layout.view_drawable_overlay, null);
        mImageView = (MyImageViewDrawableOverlay) overlay.findViewById(R.id.drawable_overlay);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(GlobalConfigManage.getInstance().getScreenWidth(),
                GlobalConfigManage.getInstance().getScreenWidth());
        mImageView.setLayoutParams(params);
        overlay.setLayoutParams(params);
        drawArea.addView(overlay);
        //添加标签选择器
        RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(GlobalConfigManage.getInstance().getScreenWidth(),
                GlobalConfigManage.getInstance().getScreenWidth());
        labelSelector = new LabelSelector(this);
        labelSelector.setLayoutParams(rparams);
        drawArea.addView(labelSelector);
        labelSelector.hide();

        //初始化滤镜图片
        mGPUImageView.setLayoutParams(rparams);


        //初始化空白标签
        emptyLabelView = new LabelView(this);
        emptyLabelView.setEmpty();
        DynamicUtil.addLabelEditable(mImageView, drawArea, emptyLabelView,
                mImageView.getWidth() / 2, mImageView.getWidth() / 2);
        emptyLabelView.setVisibility(View.INVISIBLE);

        //初始化推荐标签栏
        commonLabelArea = LayoutInflater.from(PhotoProcessActivity.this).inflate(
                R.layout.view_label_bottom,null);
        commonLabelArea.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        toolArea.addView(commonLabelArea);
        commonLabelArea.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        stickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setCurrentBtn(stickerBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);
                labelSelector.hide();
                emptyLabelView.setVisibility(View.GONE);
                commonLabelArea.setVisibility(View.GONE);
                initStickerToolBar();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setCurrentBtn(filterBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);
                labelSelector.hide();
                emptyLabelView.setVisibility(View.INVISIBLE);
                commonLabelArea.setVisibility(View.GONE);
                initFilterToolBar();
            }
        });
        labelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setCurrentBtn(labelBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.GONE);
                labelSelector.showToTop();
                commonLabelArea.setVisibility(View.VISIBLE);
            }
        });
        labelSelector.setTxtClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureAddTagActivity.startActivityForResult(PhotoProcessActivity.this,"",8, ACTION_EDIT_LABEL);
            }
        });
        labelSelector.setAddrClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureAddTagActivity.startActivityForResult(PhotoProcessActivity.this,"",8, ACTION_EDIT_LABEL_POI);
            }
        });
        mImageView.setOnDrawableEventListener(wpEditListener);
        mImageView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                emptyLabelView.updateLocation((int) mImageView.getmLastMotionScrollX(),
                        (int) mImageView.getmLastMotionScrollY());
                emptyLabelView.setVisibility(View.VISIBLE);

                labelSelector.showToTop();
                drawArea.postInvalidate();
            }
        });
        labelSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelSelector.hide();
                emptyLabelView.updateLocation((int) labelSelector.getmLastTouchX(),
                        (int) labelSelector.getmLastTouchY());
                emptyLabelView.setVisibility(View.VISIBLE);
            }
        });


        /*titleBar.setRightBtnOnclickListener(v -> {
            savePicture();
        });*/

        initStickerToolBar();

        FrescoUtils.clearCatcheByUri(getIntent().getData());
        FrescoUtils.getBitmapWithProcessor(getIntent().getData(), this, 0, 0, null, new FrescoUtils.BitmapListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                currentBitmap = bitmap;
                mGPUImageView.setImage(currentBitmap);
                smallImageBackgroud = currentBitmap;
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void tagClick(View v){
        TextView textView = (TextView)v;
        TagItem tagItem = new TagItem(POST_TYPE_TAG,textView.getText().toString());
        addLabel(tagItem);
    }

    private MyImageViewDrawableOverlay.OnDrawableEventListener wpEditListener   = new MyImageViewDrawableOverlay.OnDrawableEventListener() {
        @Override
        public void onMove(MyHighlightView view) {
        }

        @Override
        public void onFocusChange(MyHighlightView newFocus, MyHighlightView oldFocus) {
        }

        @Override
        public void onDown(MyHighlightView view) {

        }

        @Override
        public void onClick(MyHighlightView view) {
            labelSelector.hide();
        }

        @Override
        public void onClick(final LabelView label) {
            if (label.equals(emptyLabelView)) {
                return;
            }
            new MaterialDialog.Builder(PhotoProcessActivity.this)
                    .title("提示")
                    .content("是否需要删除该标签！")
                    .positiveText("确定")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            DynamicUtil.removeLabelEditable(mImageView, drawArea, label);
                            labels.remove(label);
                        }
                    })
                    .show();
        }
    };

    private boolean setCurrentBtn(TextView btn) {
        if (currentBtn == null) {
            currentBtn = btn;
        } else if (currentBtn.equals(btn)) {
            return false;
        } else {
            currentBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        //btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, myImage);
        currentBtn = btn;
        return true;
    }

    //初始化贴图
    private void initStickerToolBar(){

        bottomToolBar.setAdapter(new StickerToolAdapter(PhotoProcessActivity.this, DynamicUtil.addonList));
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0,
                                    View arg1, int arg2, long arg3) {
                labelSelector.hide();
                Addon sticker = DynamicUtil.addonList.get(arg2);
                DynamicUtil.addStickerImage(mImageView, PhotoProcessActivity.this, sticker,
                        new DynamicUtil.StickerCallback() {
                            @Override
                            public void onRemoveSticker(Addon sticker) {
                                labelSelector.hide();
                            }
                        });
            }
        });
        setCurrentBtn(stickerBtn);
    }


    //初始化滤镜
    private void initFilterToolBar(){
        final List<FilterEffect> filters = EffectService.getInst().getLocalFilters();
        final FilterAdapter adapter = new FilterAdapter(PhotoProcessActivity.this, filters, smallImageBackgroud);
        bottomToolBar.setAdapter(adapter);
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                labelSelector.hide();
                if (adapter.getSelectFilter() != arg2) {
                    adapter.setSelectFilter(arg2);
                    GPUImageFilter filter = GPUImageFilterTools.createFilterForType(
                            PhotoProcessActivity.this, filters.get(arg2).getType());
                    mGPUImageView.setFilter(filter);
                    GPUImageFilterTools.FilterAdjuster mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
                    //可调节颜色的滤镜
                    if (mFilterAdjuster.canAdjust()) {
                        //mFilterAdjuster.adjust(100); 给可调节的滤镜选一个合适的值
                    }
                }
            }
        });
    }

    //添加标签
    private void addLabel(TagItem tagItem) {
        labelSelector.hide();
        emptyLabelView.setVisibility(View.INVISIBLE);
        if (labels.size() >= 5) {
            new MaterialDialog.Builder(this)
                    .title("提示")
                    .content("您只能添加5个标签！")
                    .positiveText("确定")
                    .show();
        } else {
            int left = emptyLabelView.getLeft();
            int top = emptyLabelView.getTop();
            if (labels.size() == 0 && left == 0 && top == 0) {
                left = mImageView.getWidth() / 2 - 10;
                top = mImageView.getWidth() / 2;
            }
            LabelView label = new LabelView(PhotoProcessActivity.this);
            label.init(tagItem);
            DynamicUtil.addLabelEditable(mImageView, drawArea, label, left, top);
            labels.add(label);
        }
    }

    public static void startActivity(Context context, Uri uri){
        Intent intent = new Intent(context, PhotoProcessActivity.class);
        intent.setData(uri);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        labelSelector.hide();
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTION_EDIT_LABEL== requestCode && data != null) {
            String text = data.getStringExtra(PictureAddTagActivity.PARAM_EDIT_TEXT);
            if(!StringUtils.isEmpty(text)){
                TagItem tagItem = new TagItem(POST_TYPE_TAG, text);
                addLabel(tagItem);
            }
        }else if(ACTION_EDIT_LABEL_POI== requestCode && data != null){
            String text = data.getStringExtra(PictureAddTagActivity.PARAM_EDIT_TEXT);
            if(!StringUtils.isEmpty(text)){
                TagItem tagItem = new TagItem(LabelView.POST_TYPE_POI,text);
                addLabel(tagItem);
            }
        }
    }
}
