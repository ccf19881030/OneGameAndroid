package com.guohe.onegame.view.fragment;

import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.mine.PersonalPageActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment2 extends BaseMainFragment {

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        getView(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalPageActivity.startActivity(MainFragment2.this.getContext());
            }
        });
    }
}
