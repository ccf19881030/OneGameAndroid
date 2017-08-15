package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.entry.ThumbnailItem;
import com.guohe.onegame.manage.ThumbnailsManager;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.view.adapter.ThumbnailCallback;
import com.guohe.onegame.view.adapter.ThumbnailsAdapter;
import com.guohe.onegame.view.base.BaseActivity;
import com.wou.commonutils.ImageUtils;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/15.
 */

public class ImageFilterActivity extends BaseActivity implements ThumbnailCallback{

    private RecyclerView mThumbListView;
    private ImageView mPlacHolderImageView;
    private String mImagePath;
    private Bitmap mOrignBitmap;
    private Bitmap mFilterBitmap;
    private int mScreenWidth;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, final ImageButton moreButton) {
        titleText.setText("滤镜");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("下一步");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = mOrignBitmap;
                if(mFilterBitmap != null){
                    bitmap = mFilterBitmap;
                }
                String filterPath = ImageUtils.saveBitmap(
                        bitmap, Environment.getExternalStorageDirectory() + "/temp/");
                PublishDynamicActivity.startActivity(ImageFilterActivity.this, filterPath);
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_image_filter;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mImagePath = intent.getStringExtra("imagePath");
        mScreenWidth = GlobalConfigManage.getInstance().getScreenWidth();
        mThumbListView = getView(R.id.thumbnails);
        mPlacHolderImageView = getView(R.id.place_holder_imageview);
        ViewGroup.LayoutParams params = mPlacHolderImageView.getLayoutParams();
        params.height = mScreenWidth;
        mPlacHolderImageView.setLayoutParams(params);
    }

    @Override
    protected void initData() {
        mOrignBitmap = BitmapFactory.decodeFile(mImagePath);
        //mPlacHolderImageView.setImageBitmap(Bitmap.createScaledBitmap(mOrignBitmap, mScreenWidth, mScreenWidth, false));
        mPlacHolderImageView.setImageBitmap(mOrignBitmap);
        initHorizontalList();
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        mThumbListView.setLayoutManager(layoutManager);
        mThumbListView.setHasFixedSize(true);
        bindDataToAdapter();
    }

    public static void startActivity(Context context, String imagePath){
        Intent intent = new Intent(context, ImageFilterActivity.class);
        intent.putExtra("imagePath", imagePath);
        context.startActivity(intent);
    }

    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                int[] size = getChangeSize(DimenUtil.dp2px(80));
                Bitmap thumbImage = Bitmap.createScaledBitmap(
                        mOrignBitmap, size[0], size[1], false);
                ThumbnailItem t1 = new ThumbnailItem();
                ThumbnailItem t2 = new ThumbnailItem();
                ThumbnailItem t3 = new ThumbnailItem();
                ThumbnailItem t4 = new ThumbnailItem();
                ThumbnailItem t5 = new ThumbnailItem();
                ThumbnailItem t6 = new ThumbnailItem();

                t1.image = thumbImage;
                t2.image = thumbImage;
                t3.image = thumbImage;
                t4.image = thumbImage;
                t5.image = thumbImage;
                t6.image = thumbImage;
                ThumbnailsManager.clearThumbs();
                ThumbnailsManager.addThumb(t1); // Original Image

                t2.filter = SampleFilters.getStarLitFilter();
                ThumbnailsManager.addThumb(t2);

                t3.filter = SampleFilters.getBlueMessFilter();
                ThumbnailsManager.addThumb(t3);

                t4.filter = SampleFilters.getAweStruckVibeFilter();
                ThumbnailsManager.addThumb(t4);

                t5.filter = SampleFilters.getLimeStutterFilter();
                ThumbnailsManager.addThumb(t5);

                t6.filter = SampleFilters.getNightWhisperFilter();
                ThumbnailsManager.addThumb(t6);

                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, ImageFilterActivity.this);
                mThumbListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    private int[] getChangeSize(int minSize){
        int[] size = new int[2];
        if(mOrignBitmap.getWidth() < mOrignBitmap.getHeight()){
            size[0] = minSize;
            size[1] = (int)((float)mOrignBitmap.getHeight() * size[0] / mOrignBitmap.getWidth());
        }else{
            size[1] = minSize;
            size[0] = (int)((float)mOrignBitmap.getWidth() * size[1] / mOrignBitmap.getHeight());
        }
        return size;
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        int[] size = getChangeSize(mScreenWidth);
        mFilterBitmap = filter.processFilter(Bitmap.createScaledBitmap(mOrignBitmap, size[0], size[1], false));
        mPlacHolderImageView.setImageBitmap(mFilterBitmap);
    }
}
