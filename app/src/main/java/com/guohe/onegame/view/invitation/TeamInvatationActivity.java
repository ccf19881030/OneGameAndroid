package com.guohe.onegame.view.invitation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.mine.RechargeActivity;

import java.util.List;

import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_QUTIQIU;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_XUETIQIU;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_YUECAIPAN;
import static com.guohe.onegame.view.fragment.HomeFragment.HOME_TYPE_YUEZHAN;

/**
 * Created by 水寒 on 2017/8/20.
 * 约战
 */

public class TeamInvatationActivity extends BaseActivity{

    private int mHomeType;

    private TextView mTeamName;
    private TextView mTeamNum;
    private TextView mTeamInfo;
    private View mAgeGroupView;
    private View mCaipanView;
    private View mQiuyiView;
    private View mAddressView;
    private View mTimeView;
    private View mChargeView;
    private View mNotEnough;
    private TextView mChargetValue;
    private Button mAgreeButton;
    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_invatation;
    }

    @Override
    protected void initView() {
        mHomeType = getIntent().getIntExtra("homeType", 0);
        mTeamName = getView(R.id.team_invatation_teamname);
        mTeamNum = getView(R.id.team_invatation_teamnum);
        mTeamInfo = getView(R.id.team_invatation_descript);
        mAgeGroupView = getView(R.id.team_invatation_agegroup);
        mCaipanView = getView(R.id.team_invatation_caipan);
        mQiuyiView = getView(R.id.team_invatation_qiuyi);
        mAddressView = getView(R.id.team_invatation_address);
        mNotEnough = getView(R.id.team_invatation_not_enough);
        mTimeView = getView(R.id.team_invatation_time);
        mChargeView = getView(R.id.team_invatation_charge);
        mChargetValue = getView(R.id.team_invatation_charge_value);
        mAgreeButton = getView(R.id.team_invatation_button);
        mAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptTeamInvatationActivity.startActivity(TeamInvatationActivity.this, mHomeType);
            }
        });
        getView(R.id.team_invatation_torecharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeActivity.startActivity(TeamInvatationActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        switch (mHomeType){
            case HOME_TYPE_YUEZHAN:

                break;
            case HOME_TYPE_YUECAIPAN:
                mCaipanView.setVisibility(View.VISIBLE);
                mQiuyiView.setVisibility(View.GONE);
                mTeamInfo.setText("确认抢单后，对方可以看到您的手机号码\n" +
                        "确认后取消或者放鸽子，您球队的信用分将会下降");
                mNotEnough.setVisibility(View.GONE);
                mAgreeButton.setText("确认抢单");
                break;
            case HOME_TYPE_QUTIQIU:
                mAgreeButton.setText("确认报名");
                break;
            case HOME_TYPE_XUETIQIU:
                mTeamName.setText("张教练周末小场");
                mTeamNum.setText("8人");
                mAgeGroupView.setVisibility(View.VISIBLE);
                mQiuyiView.setVisibility(View.GONE);
                mTeamInfo.setText("确认报名后，对方可以看到您的手机号码\n" +
                        "确认后取消或者放鸽子，您的信用积分将会下降");
                mChargetValue.setText("80元/人");
                mAgreeButton.setText("确认报名");
                break;
        }
    }

    public static void startActivity(Context context, int homeType){
        Intent intent = new Intent(context, TeamInvatationActivity.class);
        intent.putExtra("homeType", homeType);
        context.startActivity(intent);
    }
}
