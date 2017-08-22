package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.view.base.BaseActivity;
import com.guohe.onegame.view.dialog.DialogChoosePlace;
import com.guohe.onegame.view.dialog.DialogChoosePlaceTime;
import com.guohe.onegame.view.dialog.SublimePickerFragment;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/18.
 * 足球场
 */

public class FootballPlaceActivity extends BaseActivity implements View.OnClickListener{

    private SimpleDraweeView mPlaceBg;
    private TextView mPlaceDate;

    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            //ToastUtil.showToast("onCancelled()");
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            mPlaceDate.setText(DateFormat.getDateInstance().format(selectedDate.getFirstDate().getTime()));
        }
    };

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_football_place;
    }

    @Override
    protected void initView() {
        mPlaceBg = getView(R.id.football_place_bg);
        FrescoUtils.loadRes(mPlaceBg, R.mipmap.test_football_place_bg, null, 0, 0, null);

        findViewById(R.id.football_place_date_area).setOnClickListener(this);
        findViewById(R.id.football_place_number).setOnClickListener(this);
        findViewById(R.id.football_place_time).setOnClickListener(this);
        mPlaceDate = getView(R.id.football_place_date);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, FootballPlaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.football_place_date_area:
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);
                SublimeOptions options = new SublimeOptions();
                options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
                options.setDisplayOptions(SublimeOptions.ACTIVATE_DATE_PICKER);
                Pair<Boolean, SublimeOptions> optionsPair =
                        new Pair<>(SublimeOptions.ACTIVATE_DATE_PICKER != 0
                                ? Boolean.TRUE : Boolean.FALSE, options);
                // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");
                break;
            case R.id.football_place_number:
                new DialogChoosePlace().show(FootballPlaceActivity.this.getFragmentManager());
                break;
            case R.id.football_place_time:
                new DialogChoosePlaceTime().show(FootballPlaceActivity.this.getFragmentManager());
                break;
        }
    }
}
