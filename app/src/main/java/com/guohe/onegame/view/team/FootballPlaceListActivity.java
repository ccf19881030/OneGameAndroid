package com.guohe.onegame.view.team;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.FootballField;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.adapter.FootballPlaceAdapter;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/19.
 * 足球场选择
 */

public class FootballPlaceListActivity extends BaseActivity{

    public static final int REQUEST_CODE = 0x0023;

    private EditText mSearchEdit;
    private ListView mListView;
    private List<FootballField> mfields = new ArrayList<>();
    private FootballPlaceAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_football_place_list;
    }

    @Override
    protected void initView() {
        setResult(RESULT_CANCELED);
        List<FootballField> fields = (List<FootballField>) getIntent().getSerializableExtra("footballFields");
        if(fields != null){
            mfields.addAll(fields);
        }
        getView(R.id.search_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FootballPlaceListActivity.this.finish();
            }
        });
        mSearchEdit = getView(R.id.search_place_searchedit);
        mListView = getView(R.id.search_place_listview);
        mAdapter = new FootballPlaceAdapter(this, mfields);
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText.toString().trim());
                }else{
                    mListView.clearTextFilter();
                }
            }

            @Override
            public void afterTextChanged(Editable newText) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("field", mfields.get(position));
                setResult(RESULT_OK, intent);
                FootballPlaceListActivity.this.finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startActivityForResult(Activity context, ArrayList<FootballField> footballFields){
        if(footballFields == null || footballFields.isEmpty()) {
            ToastUtil.showToast("当前地区没有足球场地");
            return;
        }
        Intent intent = new Intent(context, FootballPlaceListActivity.class);
        intent.putExtra("footballFields", footballFields);
        context.startActivityForResult(intent, REQUEST_CODE);
    }
}
