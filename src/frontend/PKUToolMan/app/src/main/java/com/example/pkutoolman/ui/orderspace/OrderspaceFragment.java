package com.example.pkutoolman.ui.orderspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import com.example.pkutoolman.ui.myorder.GetMyOrder;
import com.example.pkutoolman.ui.orderinfo.OrderinfoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderspaceFragment extends Fragment {

    private OrderspaceViewModel orderspaceViewModel;
    private ListView lv;
    private Button bt_create;
    private SimpleAdapter saPublish;
    //private SwipeRefreshLayout mSrl;
    private FloatingActionButton freshButton;
    private ArrayList<Map<String, Object>> messageList = new ArrayList<>();
    private static String[] _selectType = {"全部", "取快递", "购物", "带饭"};
    volatile private String selectType;
    private Spinner sn;
    private ArrayAdapter snAdp;
    private TextView hint;
    ArrayList<Order> publishOrderList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderspaceViewModel =
                new ViewModelProvider(this).get(OrderspaceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orderspace, container, false);
        lv = root.findViewById(R.id.orderspace_lv);
        bt_create = root.findViewById(R.id.modifyinfo_button);
        freshButton = root.findViewById(R.id.orderspace_refresh_button);
        sn = root.findViewById(R.id.orderspace_type_selector);
        hint = root.findViewById(R.id.orderspace_no_order);
        snAdp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"全部", "取快递", "购物", "带饭"});

        /*
        final TextView textView = root.findViewById(R.id.text_orderspace);
        orderspaceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        refresh(true);
        lv.setAdapter(saPublish);
        sn.setAdapter(snAdp);

        InitListener();

        return root;
    }
    private void InitListener(){

        sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = _selectType[position];
                refresh(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        freshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("refresh");
                refresh(true);
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
    private void refresh(boolean flag){

        if (flag) {
            publishOrderList.clear();
            GetPublishedOrder.getOrder(getContext(), Data.getUserID(), publishOrderList);
        }
        messageList.clear();
        // 准备放到页面中
        for (Order o : publishOrderList)
            if(o.type.equals(selectType) || selectType == "全部"){
                Map<String, Object> m = new HashMap<>();
                m.put("uid", o.id);
                m.put("ddtime", o.endTime);
                m.put("class", o.type);
                m.put("name", o.userID);
                m.put("place1", o.place);
                m.put("place2", o.destination);
                //if (o.state != 0) continue;
                messageList.add(m);
            }
        if (messageList.size() == 0) hint.setVisibility(View.VISIBLE);
            else hint.setVisibility(View.GONE);

        if (saPublish == null)
            saPublish = new SimpleAdapter(getContext(),
                    messageList,
                    R.layout.order_published,
                    new String[] {"uid", "name", "ddtime", "class", "place1", "place2"},
                    new int[] {R.id.order_uid,R.id.order_publisher,
                            R.id.order_time, R.id.order_class, R.id.order_place1, R.id.order_place2}
            );
        saPublish.notifyDataSetChanged();
    }
}