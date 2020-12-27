package com.example.pkutoolman.ui.orderspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;

public class OrderspaceListView extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public OrderspaceListView(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Data.getMyOrderInfo().size();
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
        public TextView order_uid,  order_time, order_class, order_publisher;
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
            holder.order_time = convertView.findViewById(R.id.order_time);
            holder.order_class = convertView.findViewById(R.id.order_class);
            holder.order_publisher = convertView.findViewById(R.id.order_publisher);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        holder.order_uid.setText("订单编号:2048");
        holder.order_class.setText("订单类型:取快递");
        holder.order_time.setText("截止时间:2020-12-17");
        holder.order_publisher.setText("发布者:&CGT");
        return convertView;
    }
}
