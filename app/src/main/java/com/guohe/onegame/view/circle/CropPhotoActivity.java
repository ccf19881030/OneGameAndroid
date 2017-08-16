package com.guohe.onegame.view.circle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DynamicUtil;
import com.guohe.onegame.util.FileUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.imagezoom.ImageViewTouch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/15.
 */

public class CropPhotoActivity extends BaseActivity{

    private static final boolean IN_MEMORY_CROP = Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1;
    public static final int REQUEST_CROP = 6709;
    private Uri fileUri;
    private int mScreenWidth;
    private Bitmap mOriBitmap;
    private int initWidth, initHeight;
    private static final int MAX_WRAP_SIZE  = 2048;
    private MaterialDialog cropPhotoDialog;

    private ImageViewTouch mCropImage;
    private ViewGroup mDrawArea;
    private View mWrapImage;
    private View mBtnCropType;
    private ImageView mImageCenter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("裁剪图片");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("下一步");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropPhotoDialog = new MaterialDialog.Builder(CropPhotoActivity.this)
                        .title("图片处理中")
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                new Thread() {
                    public void run() {
                        /*if (mBtnCropType.isSelected()) {
                            wrapImage();
                        } else {
                            cropImage();
                        }*/
                        cropImage();
                        dismissProgressDialog();
                    }
                }.start();
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_crop_photo;
    }

    @Override
    protected void initView() {
        mScreenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        fileUri = getIntent().getData();
        LogUtil.d("fileUri == " + fileUri);
        mCropImage = getView(R.id.crop_image);
        mDrawArea = getView(R.id.draw_area);
        mImageCenter = getView(R.id.image_center);
        mWrapImage = getView(R.id.wrap_image);
        //mBtnCropType = getView(R.id.btn_crop_type);
        mDrawArea.getLayoutParams().height = mScreenWidth;
        try {
            //得到图片宽高比
            double rate = DynamicUtil.getImageRadio(getContentResolver(), fileUri);
            LogUtil.d("fileUri.getPath == " + fileUri.getPath());
            mOriBitmap = DynamicUtil.decodeBitmapWithOrientationMax(fileUri.getPath(), mScreenWidth, mScreenWidth);
            LogUtil.d("mOriBitmap == " + mOriBitmap);
            initWidth = mOriBitmap.getWidth();
            initHeight = mOriBitmap.getHeight();

            mCropImage.setImageBitmap(mOriBitmap, new Matrix(), (float) rate, 10);
            mImageCenter.setImageBitmap(mOriBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
       /* mBtnCropType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCropImage.getVisibility() == View.VISIBLE) {
                    mBtnCropType.setSelected(true);
                    mCropImage.setVisibility(View.GONE);
                    mWrapImage.setVisibility(View.VISIBLE);
                } else {
                    mBtnCropType.setSelected(false);
                    mCropImage.setVisibility(View.VISIBLE);
                    mWrapImage.setVisibility(View.GONE);
                }
            }
        });
        mImageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWrapImage.setSelected(!mWrapImage.isSelected());
            }
        });*/
    }

    protected void wrapImage() {
        int width = initWidth > initHeight ? initWidth : initHeight;
        int imageSize = width < MAX_WRAP_SIZE ? width : MAX_WRAP_SIZE;

        int move =  (int)((initHeight - initWidth) / 2 / (float)width * (float)imageSize);
        int moveX = initWidth < initHeight ? move : 0;
        int moveY = initHeight < initWidth ? -move : 0;
        Bitmap croppedImage = null;
        try {
            croppedImage = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(croppedImage);
            Paint p = new Paint();
            p.setColor(mWrapImage.isSelected() ? Color.BLACK : Color.WHITE);
            canvas.drawRect(0, 0, imageSize, imageSize, p);
            Matrix matrix = new Matrix();
            matrix.postScale((float) imageSize / (float) width, (float) imageSize / (float) width);
            matrix.postTranslate(moveX, moveY);
            canvas.drawBitmap(mOriBitmap, matrix, null);
        } catch (OutOfMemoryError e) {
            Log.e("OOM cropping image: " + e.getMessage(), e.toString());
            System.gc();
        }
        saveImageToCache(croppedImage);
    }

    private void cropImage() {
        Bitmap croppedImage;
        if (IN_MEMORY_CROP) {
            croppedImage = inMemoryCrop(mCropImage);
        } else {
            try {
                croppedImage = decodeRegionCrop(mCropImage);
            } catch (IllegalArgumentException e) {
                croppedImage = inMemoryCrop(mCropImage);
            }
        }
        saveImageToCache(croppedImage);
    }

    private void saveImageToCache(Bitmap croppedImage) {
        if (croppedImage != null) {
            try {
                DynamicUtil.saveToFile(FileUtils.getInst().getPhotoSavedPath() + "/croppedcache",
                        false, croppedImage);
                Intent intent = new Intent();
                intent.setData(Uri.parse("file://" + FileUtils.getInst().getPhotoSavedPath()
                        + "/croppedcache"));
                setResult(RESULT_OK, intent);
                dismissProgressDialog();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.showToast("裁剪图片异常，请稍后重试");
            }
        }
    }

    private void dismissProgressDialog(){
        if(cropPhotoDialog == null) return;
        cropPhotoDialog.dismiss();
    }

    @TargetApi(10)
    private Bitmap decodeRegionCrop(ImageViewTouch cropImage) {
        int width = initWidth > initHeight ? initHeight : initWidth;
        int screenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        float scale = cropImage.getScale() / getImageRadio();
        RectF rectf = cropImage.getBitmapRect();
        int left = -(int) (rectf.left * width / screenWidth / scale);
        int top = -(int) (rectf.top * width / screenWidth / scale);
        int right = left + (int) (width / scale);
        int bottom = top + (int) (width / scale);
        Rect rect = new Rect(left, top, right, bottom);
        InputStream is = null;
        System.gc();
        Bitmap croppedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mOriBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            is = new ByteArrayInputStream(baos.toByteArray());
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
            croppedImage = decoder.decodeRegion(rect, new BitmapFactory.Options());
        } catch (Throwable e) {

        } finally {
            DynamicUtil.closeStream(is);
        }
        return croppedImage;
    }

    private float getImageRadio() {
        return Math.max((float) initWidth, (float) initHeight)
                / Math.min((float) initWidth, (float) initHeight);
    }

    private Bitmap inMemoryCrop(ImageViewTouch cropImage) {
        int width = initWidth > initHeight ? initHeight : initWidth;
        int screenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        System.gc();
        Bitmap croppedImage = null;
        try {
            croppedImage = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(croppedImage);
            float scale = cropImage.getScale();
            RectF srcRect = cropImage.getBitmapRect();
            Matrix matrix = new Matrix();

            matrix.postScale(scale / getImageRadio(), scale / getImageRadio());
            matrix.postTranslate(srcRect.left * width / screenWidth, srcRect.top * width
                    / screenWidth);
            //matrix.mapRect(srcRect);
            canvas.drawBitmap(mOriBitmap, matrix, null);
        } catch (OutOfMemoryError e) {
            Log.e("OOM cropping image: " + e.getMessage(), e.toString());
            System.gc();
        }
        return croppedImage;
    }

    public static void startActivityForResult(Activity activity, Uri uri){
        Intent intent = new Intent(activity, CropPhotoActivity.class);
        intent.setData(uri);
        activity.startActivityForResult(intent, REQUEST_CROP);
    }
}
