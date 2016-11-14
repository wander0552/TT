package com.wander.tt.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wander.tt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wander on 2016/11/14.
 */
public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private double currentPosition = -1;
    private int lastPosition = -1;

    public MainAdapter(Main2Activity main2Activity) {
        mContext = main2Activity;
        initList();
    }

    private void initList() {
        mList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mList.add("展开的都是妹子" + i);

        }


    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.item_title);
            holder.extend = (TextView) convertView.findViewById(R.id.item_contain);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText("点击");
        holder.extend.setText(mList.get(position));
        if (position == currentPosition) {
            holder.extend.setVisibility(View.VISIBLE);
        } else {
            holder.extend.setVisibility(View.GONE);
        }
        return convertView;
    }

    public double getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(double currentPosition) {
        this.currentPosition = currentPosition;
    }

    class ViewHolder {
        TextView title;
        TextView extend;
    }
}
