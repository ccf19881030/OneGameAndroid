package com.guohe.onegame.view.circle;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.wou.commonutils.StringUtils;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/15.
 * 图片添加标签
 */

public class PictureAddTagActivity extends BaseActivity{

    public static final String PARAM_MAX_SIZE = "PARAM_MAX_SIZE";
    public static final String PARAM_EDIT_TEXT = "PARAM_EDIT_TEXT";

    private final static int MAX = 10;
    private int maxlength = MAX;

    private EditText contentView;
    private TextView numberTips;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("添加标签");
        toolbarMenu.setVisibility(View.VISIBLE);
        toolbarMenu.setText("保存");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String inputTxt = contentView.getText().toString();
                intent.putExtra(PARAM_EDIT_TEXT, inputTxt);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_picture_addtag;
    }

    @Override
    protected void initView() {
        contentView = getView(R.id.text_input);
        numberTips = getView(R.id.tag_input_tips);
        maxlength = getIntent().getIntExtra(PARAM_MAX_SIZE, MAX);
        String defaultStr = getIntent().getStringExtra(PARAM_EDIT_TEXT);
        if (!StringUtils.isEmpty(defaultStr)) {
            contentView.setText(defaultStr);
            if (defaultStr.length() <= maxlength) {
                numberTips.setText("你还可以输入" + (maxlength - defaultStr.length()) + "个字  ("
                        + defaultStr.length() + "/" + maxlength + ")");
            }
        }
        contentView.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = contentView.getSelectionStart();
            editEnd = contentView.getSelectionEnd();
            if (s.toString().length() > maxlength) {
                ToastUtil.showToast("你输入的字数已经超过了限制！");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                contentView.setText(s);
                contentView.setSelection(tempSelection);
            }
            numberTips.setText("你还可以输入"
                    + (maxlength - s.toString().length())
                    + "个字  (" + s.toString().length() + "/"
                    + maxlength + ")");
        }
    };

    @Override
    protected void initData() {

    }

    public static void startActivityForResult(Activity activity, String defaultStr,int maxLength, int reqCode){
        Intent intent = new Intent(activity, PictureAddTagActivity.class);
        intent.putExtra(PARAM_EDIT_TEXT, defaultStr);
        if (maxLength != 0) {
            intent.putExtra(PARAM_MAX_SIZE, maxLength);
        }
        activity.startActivityForResult(intent, reqCode);
    }
}
