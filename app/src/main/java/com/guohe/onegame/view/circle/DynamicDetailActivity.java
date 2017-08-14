package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.mine.PersonalPageActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 动态详情
 */

public class DynamicDetailActivity extends BaseActivity {

    private SimpleDraweeView mPicture;
    private LinearLayout mFollowdArea;
    private ImageButton mMoreButton;
    private SimpleDraweeView mHead;

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

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    protected void initView() {
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
    }

    @Override
    protected void initData() {
        FrescoUtils.loadRes(mPicture, R.mipmap.test_img1, null, 0, 0, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.dp2px(15), DimenUtil.dp2px(15));
        params.setMargins(0, 0, DimenUtil.dp2px(3), 0);
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
}
