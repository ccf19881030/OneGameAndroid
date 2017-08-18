package com.guohe.onegame.view.dialog;

import android.util.DisplayMetrics;
import android.view.View;

import com.guohe.onegame.R;

/**
 * Created by 水寒 on 2017/8/17.
 */

public class DialogChooseMapService extends BaseDialogFragment {

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_choose_map_service;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.dialog_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        view.findViewById(R.id.dialog_service_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mErrorListener != null){
                    mErrorListener.onClick(v);
                }
                close();
            }
        });
        view.findViewById(R.id.dialog_servie_contanct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceListener != null){
                    mServiceListener.onClick(v);
                }
                close();
            }
        });
    }

    private View.OnClickListener mErrorListener;
    private View.OnClickListener mServiceListener;
    public DialogChooseMapService setOnMenuError(View.OnClickListener listener){
        mErrorListener = listener;
        return this;
    }

    public DialogChooseMapService setOnMenuService(View.OnClickListener listener){
        mServiceListener = listener;
        return this;
    }
}
