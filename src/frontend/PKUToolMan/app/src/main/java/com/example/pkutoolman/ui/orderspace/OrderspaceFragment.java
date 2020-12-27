package com.example.pkutoolman.ui.orderspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.ui.orderinfo.OrderinfoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderspaceFragment extends Fragment {

    private OrderspaceViewModel orderspaceViewModel;
    private ListView lv;
    private Button bt_create;
    private SimpleAdapter saPublish;
    private SwipeRefreshLayout mSrl;
    private ArrayList<Map<String, Object>> messageList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderspaceViewModel =
                new ViewModelProvider(this).get(OrderspaceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orderspace, container, false);
        lv = root.findViewById(R.id.orderspace_lv);
        bt_create = root.findViewById(R.id.modifyinfo_button);
        mSrl = root.findViewById(R.id.orderspace_swipeLayout);

        /*
        final TextView textView = root.findViewById(R.id.text_orderspace);
        orderspaceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        refresh();
        lv.setAdapter(saPublish);
        InitListener();

        return root;
    }
    private void InitListener(){
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                mSrl.setRefreshing(false);
            }
        });

        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "发布订单", Toast.LENGTH_LONG).show();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.navigation_ordercreate);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id)
            {
                System.out.println(position);
                // 每个Item跳转的时候需要用Navigate,并通过Buddle向orderInfo的Fragment中传递信息
                Intent intent = new Intent();
                intent.putExtra("orderID", Integer.valueOf( ((TextView)view.findViewById(R.id.order_uid)).getText().toString()) );
                intent.setClass(getActivity(), OrderinfoActivity.class);
                startActivity(intent);
            }
        });

    }
    private void refresh(){
        ArrayList<Order> publishOrderList = new ArrayList<>();
        GetPublishedOrder.getOrder(Data.getUserID(), publishOrderList);
        messageList.clear();
        // 准备放到页面中
        for (Order o : publishOrderList) {
            Map<String, Object> m = new HashMap<>();
            m.put("uid", o.id);
            m.put("ddtime", o.endTime);
            m.put("class", "拿快递");
            m.put("name", o.userID);
            //if (o.state != 0) continue;

            messageList.add(m);
        }
        if (saPublish == null)
            saPublish = new SimpleAdapter(getContext(),
                    messageList,
                    R.layout.order_published,
                    new String[] {"uid", "name", "ddtime", "class"},
                    new int[] {R.id.order_uid,R.id.order_publisher,
                            R.id.order_time, R.id.order_class}
            );
        saPublish.notifyDataSetChanged();
    }
}