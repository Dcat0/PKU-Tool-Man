package com.example.pkutoolman.ui.orderspace;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pkutoolman.R;

public class OrderCreateFragment extends Fragment {

    private OrderCreateViewModel mViewModel;
    private RadioGroup rg;
    private ImageView btnBack;
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
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                RadioButton rb_temp = root.findViewById(radioGroup.getCheckedRadioButtonId());
                switch (checkid){
                    case R.id.orderType_rbt1:
                        Toast.makeText(getContext(),"取快递", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.orderType_rbt2:
                        Toast.makeText(getContext(), rb_temp.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.orderType_rbt3:
                        Toast.makeText(getContext(), rb_temp.getText(),Toast.LENGTH_SHORT).show();
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
    }
}