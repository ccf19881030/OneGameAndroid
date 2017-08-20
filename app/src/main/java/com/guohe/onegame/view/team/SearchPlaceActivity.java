package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.manage.config.GlobalConfigManage;
import com.guohe.onegame.model.entry.FootballField;
import com.guohe.onegame.util.ToastUtil;
import com.guohe.onegame.view.adapter.FootballPlaceAdapter;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/18.
 * 搜索场地
 */

public class SearchPlaceActivity extends BaseActivity{


    private EditText mSearchEdit;
    private ListView mListView;
    private ArrayList<FootballField> mfields = new ArrayList<>();
    private FootballPlaceAdapter mAdapter;
    private int mSetCollectionNum;
    private ImageView mCollection1Icon;
    private ImageView mCollection2Icon;
    private TextView mCollection1Name;
    private TextView mCollection2Name;
    private TextView mCollection1Descript;
    private TextView mCollection2Descript;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_place;
    }

    @Override
    protected void initView() {
        List<FootballField> fields = (List<FootballField>) getIntent().getSerializableExtra("footballFields");
        if(fields != null){
            mfields.addAll(fields);
        }
        getView(R.id.search_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPlaceActivity.this.finish();
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

        getView(R.id.search_place_frequent1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalConfigManage.getInstance().getFbPlaceCollection1() == null) {
                    FootballPlaceListActivity.startActivityForResult(SearchPlaceActivity.this, mfields);
                    mSetCollectionNum = 1;
                }else{
                    FootballPlaceActivity.startActivity(SearchPlaceActivity.this);
                    SearchPlaceActivity.this.finish();
                }
            }
        });

        getView(R.id.search_place_frequent1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FootballPlaceListActivity.startActivityForResult(SearchPlaceActivity.this, mfields);
                mSetCollectionNum = 1;
                return true;
            }
        });

        getView(R.id.search_place_frequent2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FootballPlaceListActivity.startActivityForResult(SearchPlaceActivity.this, mfields);
                mSetCollectionNum = 2;
                return true;
            }
        });
        getView(R.id.search_place_frequent2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalConfigManage.getInstance().getFbPlaceCollection2() == null) {
                    FootballPlaceListActivity.startActivityForResult(SearchPlaceActivity.this, mfields);
                    mSetCollectionNum = 2;
                }else{
                    FootballPlaceActivity.startActivity(SearchPlaceActivity.this);
                    SearchPlaceActivity.this.finish();
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FootballPlaceActivity.startActivity(SearchPlaceActivity.this);
                SearchPlaceActivity.this.finish();
            }
        });

        mCollection1Icon = getView(R.id.search_collection1_icon);
        mCollection1Name = getView(R.id.search_collection1_name);
        mCollection1Descript = getView(R.id.search_collection1_descript);
        mCollection2Icon = getView(R.id.search_collection2_icon);
        mCollection2Name = getView(R.id.search_collection2_name);
        mCollection2Descript = getView(R.id.search_collection2_descript);
        setCollectionPlace();
    }

    private void setCollectionPlace(){
        if(GlobalConfigManage.getInstance().getFbPlaceCollection1() != null){
            mCollection1Icon.setImageResource(R.mipmap.icon_map_place_collectioned);
            mCollection1Name.setText(GlobalConfigManage.getInstance().getFbPlaceCollection1().split("#")[1]);
            mCollection1Descript.setVisibility(View.GONE);
        }else{
            mCollection1Icon.setImageResource(R.mipmap.icon_map_place_collection);
            mCollection1Name.setText("常用球场1");
            mCollection1Descript.setVisibility(View.VISIBLE);
            mCollection1Descript.setText("点击设置常用球场");
        }
        if(GlobalConfigManage.getInstance().getFbPlaceCollection2() != null){
            mCollection2Icon.setImageResource(R.mipmap.icon_map_place_collectioned);
            mCollection2Name.setText(GlobalConfigManage.getInstance().getFbPlaceCollection2().split("#")[1]);
            mCollection2Descript.setVisibility(View.GONE);
        }else{
            mCollection2Icon.setImageResource(R.mipmap.icon_map_place_collection);
            mCollection2Name.setText("常用球场2");
            mCollection2Descript.setVisibility(View.VISIBLE);
            mCollection2Descript.setText("点击设置常用球场");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == FootballPlaceListActivity.REQUEST_CODE){
                FootballField footballField = (FootballField) data.getSerializableExtra("field");
                if(mSetCollectionNum == 1){
                    GlobalConfigManage.getInstance().setFbPlaceCollection1(
                            footballField.getId() + "#" + footballField.getLabel());
                }else if(mSetCollectionNum == 2){
                    GlobalConfigManage.getInstance().setFbPlaceCollection2(
                            footballField.getId() + "#" + footballField.getLabel());
                }
                setCollectionPlace();
            }
        }
    }

    public static void startActivity(Context context, ArrayList<FootballField> footballFields){
        if(footballFields == null || footballFields.isEmpty()) {
            ToastUtil.showToast("当前地区没有足球场地可搜索");
            return;
        }
        Intent intent = new Intent(context, SearchPlaceActivity.class);
        intent.putExtra("footballFields", footballFields);
        context.startActivity(intent);
    }
}
