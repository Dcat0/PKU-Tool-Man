package com.example.pkutoolman.ui.myorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;

public class MyListAdaptorPublished extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MyListAdaptorPublished(Context context) {
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
        public TextView order_uid, order_state, order_time, order_class;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        System.out.println(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.myorder_published, null);
            System.out.println(convertView);
            holder = new ViewHolder();
            holder.order_uid = convertView.findViewById(R.id.order_uid);
            holder.order_state = convertView.findViewById(R.id.order_state);
            holder.order_time = convertView.findViewById(R.id.order_time);
            holder.order_class = convertView.findViewById(R.id.order_class);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        Order o = Data.getMyOrderInfo().get(position);
        System.out.printf("position %d  id %d\n", position, o.id);
        holder.order_uid.setText(String.valueOf(o.id));
        holder.order_state.setText(String.valueOf(o.state));
        holder.order_class.setText("取快递");
        holder.order_time.setText("2020-12-13");
        return convertView;
    }
}
