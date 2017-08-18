package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.FootballField;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/18.
 * 搜索场地
 */

public class SearchPlaceActivity extends BaseActivity{


    private SearchView mSearchView;
    private ListView mListView;
    private List<FootballField> mfields = new ArrayList<>();
    private PlaceAdapter mAdapter;

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
        getView(R.id.search_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPlaceActivity.this.finish();
            }
        });
        mSearchView = getView(R.id.search_place_view);
        mSearchView.onActionViewExpanded();
        SearchView.SearchAutoComplete textView = ( SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        textView.setTextColor(getResources().getColor(R.color.app_textcolor));
        textView.setTextSize(14, TypedValue.COMPLEX_UNIT_SP);
        mListView = getView(R.id.search_place_listview);
        mAdapter = new PlaceAdapter(mfields);
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText.trim());
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mfields.add(new FootballField("西安苔色特足球场", 34.2450847120, 108.8778162003));
        mfields.add(new FootballField("西安鹿鸣特足球场", 34.2551236671,108.8597488403));
        mfields.add(new FootballField("西安嘉州国际足球场", 34.2487740785,108.8961839676));
        mfields.add(new FootballField("西安辉度足球场", 34.2367830461,108.8873863220));
        mAdapter.notifyDataSetChanged();
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SearchPlaceActivity.class);
        context.startActivity(intent);
    }

    class PlaceAdapter extends BaseAdapter implements Filterable{

        private Filter mFilter;
        private List<FootballField> mFilterData = new ArrayList<>();

        public PlaceAdapter(List<FootballField> mfilterdata){
            mFilterData = mfilterdata;
        }

        @Override
        public int getCount() {
            return mFilterData.size();
        }

        @Override
        public Object getItem(int position) {
            return mFilterData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(SearchPlaceActivity.this).inflate(R.layout.item_search_place, null);
            }
            TextView placeName = (TextView) convertView.findViewById(R.id.item_search_place_name);
            placeName.setText(mFilterData.get(position).getLabel());
            return convertView;
        }


        @Override
        public Filter getFilter() {
            if (mFilter ==null){
                mFilter = new PlaceFilter();
            }
            return mFilter;
        }

        class PlaceFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                List<FootballField> list;
                if (TextUtils.isEmpty(constraint)){//当过滤的关键字为空的时候，我们则显示所有的数据
                    list = mfields;
                }else {//否则把符合条件的数据对象添加到集合中
                    list = new ArrayList<>();
                    for (FootballField field : mfields){
                        if (field.getLabel().contains(constraint)){
                            list.add(field);
                        }
                    }
                }
                result.values = list; //将得到的集合保存到FilterResults的value变量中
                result.count = list.size();//将集合的大小保存到FilterResults的count变量中
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterData = (List<FootballField>) results.values;
                if (results.count > 0){
                    notifyDataSetChanged();//通知数据发生了改变
                }else {
                    notifyDataSetInvalidated();//通知数据失效
                }
            }
        }
    }
}
