package com.guohe.onegame.view.circle;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/14.
 * 更多按钮界面
 */

public class MoreMenuActivity extends BaseActivity implements View.OnClickListener {

    private boolean mIsMine;
    private int mUserId;
    private int mDynamicId;

    private ImageButton mJubaoButton;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_more_menu;
    }

    @Override
    protected void initView() {
        mUserId = getIntent().getIntExtra("userid", -1);
        mDynamicId = getIntent().getIntExtra("dynamicid", -1);
        mIsMine = mUserId == 1;
        getView(R.id.more_menu_close).setOnClickListener(this);
        getView(R.id.more_menu_wx_circle).setOnClickListener(this);
        getView(R.id.more_menu_wx_friend).setOnClickListener(this);
        getView(R.id.more_menu_weibo).setOnClickListener(this);
        getView(R.id.more_menu_qq).setOnClickListener(this);
        mJubaoButton = getView(R.id.more_menu_jubao);
        mJubaoButton.setOnClickListener(this);

        if(mIsMine){
            if(mDynamicId == -1){
                //mJubaoButton.setImageResource(R.m);
            }else {
                mJubaoButton.setImageResource(R.mipmap.icon_delete_img_black);
            }
        }else{
            mJubaoButton.setImageResource(R.mipmap.icon_menu_more_jubao);
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, int userid, int dynamicid){
        Intent intent = new Intent(context, MoreMenuActivity.class);
        intent.putExtra("userid", userid);
        intent.putExtra("dynamicid", dynamicid);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.more_menu_close:
                MoreMenuActivity.this.finish();
                break;
            case R.id.more_menu_wx_circle:

                break;
            case R.id.more_menu_wx_friend:

                break;
            case R.id.more_menu_weibo:

                break;
            case R.id.more_menu_qq:

                break;
            case R.id.more_menu_jubao:
                if(mIsMine){
                    if(mDynamicId == -1){

                    }else{

                    }
                }else {
                    new MaterialDialog.Builder(this)
                            .title("举报")
                            .content("感谢您的举报，请填写您的举报原因")
                            .inputRangeRes(4, 50, R.color.app_error)
                            .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                            .input("举报原因...", "", new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {

                                }
                            }).show();
                }
                break;
        }
    }
}
