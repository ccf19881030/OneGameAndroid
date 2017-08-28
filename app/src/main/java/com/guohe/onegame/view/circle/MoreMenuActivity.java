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

    public static final int TYPE_PERSOANL = 1;   //个人
    public static final int TYPE_DYNAMIC = 2;    //动态
    public static final int TYPE_TEAM = 3;        //球队

    private int mMoreId;
    private int mMoreType;
    private int mUserId;

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
        mMoreType = getIntent().getIntExtra("type", -1);
        mMoreId = getIntent().getIntExtra("id", -1);
        mUserId = getIntent().getIntExtra("userid", -1);

        getView(R.id.more_menu_close).setOnClickListener(this);
        getView(R.id.more_menu_wx_circle).setOnClickListener(this);
        getView(R.id.more_menu_wx_friend).setOnClickListener(this);
        getView(R.id.more_menu_weibo).setOnClickListener(this);
        getView(R.id.more_menu_qq).setOnClickListener(this);
        mJubaoButton = getView(R.id.more_menu_jubao);
        mJubaoButton.setOnClickListener(this);

        switch (mMoreType){
            case TYPE_PERSOANL:
                mJubaoButton.setImageResource(R.mipmap.icon_menu_more_jubao);
                break;
            case TYPE_DYNAMIC:
               //if(mUserId == Mine id)
                //TODO 如果是我的id则删除，否则则是举报
                mJubaoButton.setImageResource(R.mipmap.icon_menu_more_delete);
                break;
            case TYPE_TEAM:
                mJubaoButton.setImageResource(R.mipmap.icon_menu_more_jubao);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, int userid, int type, int id){
        Intent intent = new Intent(context, MoreMenuActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("userid", userid);
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
                switch (mMoreType){
                    case TYPE_PERSOANL:
                        new MaterialDialog.Builder(this)
                                .title("举报")
                                .items(R.array.report_dynamic_type)
                                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                        return true;
                                    }
                                })
                                .positiveText("提交")
                                .show();
                        break;
                    case TYPE_DYNAMIC:
                        //TODO 如果不是我则举报，否则删除
                        new MaterialDialog.Builder(this)
                                .title("举报")
                                .content("感谢您，请填写您对该用户的举报原因")
                                .inputRangeRes(4, 50, R.color.app_error)
                                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                                .input("举报原因...", "", new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(MaterialDialog dialog, CharSequence input) {

                                    }
                                }).show();
                        break;
                    case TYPE_TEAM:

                        break;
                }
        }
    }
}
