package com.example.pkutoolman.ui.orderspace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pkutoolman.R;

public class OrderspaceFragment extends Fragment {

    private OrderspaceViewModel orderspaceViewModel;
    private ListView lv;
    private Button bt_publish;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderspaceViewModel =
                new ViewModelProvider(this).get(OrderspaceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orderspace, container, false);
        lv = root.findViewById(R.id.orderspace_lv);
        bt_publish = root.findViewById(R.id.btn_publish);

        final TextView textView = root.findViewById(R.id.text_orderspace);
        orderspaceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        bt_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "发布订单", Toast.LENGTH_LONG).show();
            }
        });
        lv.setAdapter(new OrderspaceListView(getContext()));
        return root;
    }
}