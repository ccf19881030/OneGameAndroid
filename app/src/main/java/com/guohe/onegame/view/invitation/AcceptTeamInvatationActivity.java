package com.guohe.onegame.view.invitation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.team.PosterListActivity;

import java.util.List;

import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_QUTIQIU;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_XUETIQIU;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_YUECAIPAN;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_YUEZHAN;

/**
 * Created by 水寒 on 2017/8/20.
 * 接受应站结果
 */

public class AcceptTeamInvatationActivity extends BaseActivity{

    private TextView mDescript;
    private TextView mCharege;
    private TextView mFollowWorke;

    private int mHomeType;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("订单成功");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_accept_team_invatation;
    }

    @Override
    protected void initView() {
        mHomeType = getIntent().getIntExtra("homeType", 0);
        mDescript = getView(R.id.accept_invatation_descript);
        mCharege = getView(R.id.accept_invatation_charge);
        mFollowWorke = getView(R.id.accept_invatation_follow_work);
        getView(R.id.accept_team_invatation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PosterListActivity.startActivity(AcceptTeamInvatationActivity.this);
                AcceptTeamInvatationActivity.this.finish();
            }
        });
    }

    @Override
    protected void initData() {
        switch (mHomeType){
            case HOME_TYPE_YUEZHAN:

                break;
            case HOME_TYPE_YUECAIPAN:
                mDescript.setText("执法抢单成功");
                mCharege.setText("预计获得￥ 100.0");
                mFollowWorke.setVisibility(View.GONE);
                break;
            case HOME_TYPE_QUTIQIU:

                break;
            case HOME_TYPE_XUETIQIU:
                mDescript.setText("订单报名成功");
                mFollowWorke.setVisibility(View.GONE);
                break;
        }
    }

    public static void startActivity(Context context, int homeType){
        Intent intent = new Intent(context, AcceptTeamInvatationActivity.class);
        intent.putExtra("homeType", homeType);
        context.startActivity(intent);
    }
}
