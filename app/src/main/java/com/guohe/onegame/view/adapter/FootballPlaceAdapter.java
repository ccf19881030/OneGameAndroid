package com.guohe.onegame.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.guohe.onegame.R;
import com.guohe.onegame.model.entry.FootballField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 水寒 on 2017/8/19.
 */

public class FootballPlaceAdapter extends BaseAdapter implements Filterable {

    private Filter mFilter;
    private List<FootballField> mfields = new ArrayList<>();
    private List<FootballField> mFilterData = new ArrayList<>();
    private Context mContext;

    public FootballPlaceAdapter(Context context,  List<FootballField> mfilterdata){
        mFilterData.addAll(mfilterdata);
        mfields = mfilterdata;
        mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_place, null);
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
