package com.example.pkutoolman.ui.orderspace;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pkutoolman.LoginActivity;
import com.example.pkutoolman.R;

public class OrderCreateFragment extends Fragment {

    private OrderCreateViewModel mViewModel;
    private RadioGroup rg;
    private ImageView btnBack;
    private EditText txt_place1, txt_place2, txt_day, txt_hour, txt_minute, txt_description;
    private Button btn_create;
    private String st_type;

    public static OrderCreateFragment newInstance() {
        return new OrderCreateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_order_create, container, false);
        View root = inflater.inflate(R.layout.fragment_order_create, container, false);
        //获得组件
        btnBack = root.findViewById(R.id.btn_back_ordercreate);
        rg = root.findViewById(R.id.orderType_rg);
        btn_create = root.findViewById(R.id.create_button);

        txt_place1 = root.findViewById(R.id.create_order_place1);
        txt_place2 = root.findViewById(R.id.create_order_place2);
        txt_day = root.findViewById(R.id.text_create_day);
        txt_hour = root.findViewById(R.id.text_create_hour);
        txt_minute = root.findViewById(R.id.text_create_minute);
        txt_description = root.findViewById(R.id.create_order_describe);

        st_type = null;

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                RadioButton rb_temp = root.findViewById(radioGroup.getCheckedRadioButtonId());
                st_type = rb_temp.getText().toString().trim();
                switch (checkid){
                    case R.id.orderType_rbt1:
                        Toast.makeText(getContext(),"取快递", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.orderType_rbt2:
                        Toast.makeText(getContext(), rb_temp.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.orderType_rbt3:
                        Toast.makeText(getContext(), "带饭",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        setButtonListener();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrderCreateViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setButtonListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回上一界面
                Navigation.findNavController(v).navigateUp();
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = st_type;
                String place1 = txt_place1.getText().toString().trim();
                String place2 = txt_place2.getText().toString().trim();
                String st_day = txt_day.getText().toString().trim();
                String st_hour = txt_hour.getText().toString().trim();
                String st_minute = txt_minute.getText().toString().trim();
                String description = txt_description.getText().toString().trim();

                if(create_check(type,place1,place2,st_day,st_hour,st_minute,description))
                    Navigation.findNavController(v).navigateUp();
            }
        });
    }
    private boolean create_check(String type, String place1, String place2, String st_day, String st_hour, String st_minute, String description){
        if (TextUtils.isEmpty(type)||TextUtils.isEmpty(place1)||TextUtils.isEmpty(place2)
                ||TextUtils.isEmpty(st_day)||TextUtils.isEmpty(st_hour)||TextUtils.isEmpty(st_minute)
                ||TextUtils.isEmpty(description)) {
            Toast.makeText(getContext(), "请填入完整信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        int day = Integer.parseInt(txt_day.getText().toString().trim());
        int hour = Integer.parseInt(txt_hour.getText().toString().trim());
        int minute = Integer.parseInt(txt_minute.getText().toString().trim());
        if (day>9||day<0) {
            Toast.makeText(getContext(), "天数范围为0~9", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hour>23||hour<0) {
            Toast.makeText(getContext(), "小时范围为0~23", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (minute>59||minute<0) {
            Toast.makeText(getContext(), "分钟范围为0~59", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (day==0&&hour==0&&minute<30) {
            Toast.makeText(getContext(), "订单最短时限为30分钟", Toast.LENGTH_SHORT).show();
            return false;
        }

        System.out.println(type+" "+day+" "+hour+" "+minute);
        return true;
    }
}