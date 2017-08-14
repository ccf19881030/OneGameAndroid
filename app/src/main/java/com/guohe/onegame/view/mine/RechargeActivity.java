package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.AutoLinkStyleTextView;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/13.
 * 充值界面
 */

public class RechargeActivity extends BaseActivity {

    private RadioGroup mRadioGroup1;
    private RadioGroup mRadioGroup2;
    private RadioGroup mRadioGroup3;

    private RadioButton mWxRadioButton;
    private RadioButton mHongbaoRadioButton;
    private RadioButton mZhifubaoRadioButton;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("钱包充值");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        mRadioGroup1 = getView(R.id.recharge_pay_radiogroup1);
        mRadioGroup2 = getView(R.id.recharge_pay_radiogroup2);
        mRadioGroup3 = getView(R.id.recharge_pay_radiogroup3);
        RadioGroupPayNumListener listener = new RadioGroupPayNumListener();
        mRadioGroup1.setOnCheckedChangeListener(listener);
        mRadioGroup2.setOnCheckedChangeListener(listener);
        mRadioGroup3.setOnCheckedChangeListener(listener);

        mWxRadioButton = getView(R.id.recharge_wx_radiobutton);
        mHongbaoRadioButton = getView(R.id.recharge_hongbao_radiobutton);
        mZhifubaoRadioButton = getView(R.id.recharge_zhifubao_radiobutton);

        mWxRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHongbaoRadioButton.setChecked(false);
                mZhifubaoRadioButton.setChecked(false);
            }
        });
        mHongbaoRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWxRadioButton.setChecked(false);
                mZhifubaoRadioButton.setChecked(false);
            }
        });

        mZhifubaoRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWxRadioButton.setChecked(false);
                mHongbaoRadioButton.setChecked(false);
            }
        });

        AutoLinkStyleTextView autoLinkTextView = getView(R.id.recharge_protocl);
        autoLinkTextView.setOnClickCallBack(new AutoLinkStyleTextView.ClickCallBack() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(RechargeActivity.this, "充值协议", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private boolean mChangeGroupNum = false;
    class RadioGroupPayNumListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if(!mChangeGroupNum) {
                mChangeGroupNum = true;
                if (group == mRadioGroup1) {
                    mRadioGroup2.clearCheck();
                    mRadioGroup3.clearCheck();
                } else if(group == mRadioGroup2){
                    mRadioGroup1.clearCheck();
                    mRadioGroup3.clearCheck();
                } else {
                    mRadioGroup1.clearCheck();
                    mRadioGroup2.clearCheck();
                }
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                int payNum = Integer.valueOf((String)radioButton.getTag());
                //ToastUtil.showToast("payNum == " + payNum);
                mChangeGroupNum = false;
            }
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }
}
