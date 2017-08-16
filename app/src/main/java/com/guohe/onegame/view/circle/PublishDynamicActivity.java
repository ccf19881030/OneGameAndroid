package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.imageFilter.LabelView;
import com.guohe.onegame.entry.TagItem;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.base.BaseActivity;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 发布动态
 */

public class PublishDynamicActivity extends BaseActivity implements InvokeListener {

    private String mFilterImagePath;
    private SimpleDraweeView mImageView;
    private InvokeParam mInvokeParam;
    private RelativeLayout mImageOutLayout;
    private List<TagItem> mTagItems;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("发布预览");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("发布");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    protected void initView() {
        mFilterImagePath = getIntent().getStringExtra("filterImagePath");
        mTagItems = (List<TagItem>) getIntent().getSerializableExtra("tagItems");
        mImageView = getView(R.id.publish_dynamic_image);
        mImageOutLayout = getView(R.id.publish_dynamic_image_outlayout);
        ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        int width = GlobalConfigManage.getInstance().getScreenWidth();
        params.height = width;
        mImageView.setLayoutParams(params);
        FrescoUtils.loadFile(mImageView, mFilterImagePath, null, width, width, null);
    }

    @Override
    protected void initData() {
        if(mTagItems != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (TagItem feedImageTag : mTagItems) {
                        LabelView tagView = new LabelView(PublishDynamicActivity.this);
                        tagView.init(feedImageTag);
                        tagView.draw(mImageOutLayout,
                                (int) (feedImageTag.getX() * ((double) mImageOutLayout.getWidth() / (double) 1242)),
                                (int) (feedImageTag.getY() * ((double) mImageOutLayout.getWidth() / (double) 1242)),
                                feedImageTag.isLeft());
                        tagView.wave();
                    }
                }
            }, 200);
        }
    }

    public static void startActivity(Context context, String filterImagePath, ArrayList<TagItem> tagItems){
        Intent intent = new Intent(context, PublishDynamicActivity.class);
        intent.putExtra("filterImagePath", filterImagePath);
        intent.putExtra("tagItems", tagItems);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
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
