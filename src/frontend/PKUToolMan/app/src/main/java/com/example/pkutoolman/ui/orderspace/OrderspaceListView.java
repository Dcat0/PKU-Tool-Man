package com.example.pkutoolman.ui.orderspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.pkutoolman.R;

public class OrderspaceListView extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public OrderspaceListView(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public TextView order_uid, order_state, order_time, order_class;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        System.out.println(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.order_published, null);
            System.out.println(convertView);
            holder = new ViewHolder();
            holder.order_uid = convertView.findViewById(R.id.order_uid);
            holder.order_state = convertView.findViewById(R.id.order_state);
            holder.order_time = convertView.findViewById(R.id.order_time);
            holder.order_class = convertView.findViewById(R.id.order_class);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        holder.order_uid.setText("12124");
        holder.order_state.setText("完成");
        holder.order_class.setText("取快递");
        holder.order_time.setText("2020-12-13");
        return convertView;
    }
}
