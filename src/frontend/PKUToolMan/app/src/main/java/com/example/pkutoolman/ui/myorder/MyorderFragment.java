package com.example.pkutoolman.ui.myorder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pkutoolman.R;

public class MyorderFragment extends Fragment {

    private MyorderViewModel myorderViewModel;
    private ListView mLv;
    private Button bt1,bt2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myorderViewModel =
                new ViewModelProvider(this).get(MyorderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myorder, container, false);
        mLv = root.findViewById(R.id.lv);
        bt1 = root.findViewById(R.id.bt_1);
        bt2 = root.findViewById(R.id.bt_2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLv.setAdapter(new MyListAdaptorPublished(getContext()));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLv.setAdapter(new MyListAdaptorReceived(getContext()));
            }
        });

        /*final TextView textView = root.findViewById(R.id.text_myorder);
        myorderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }
}