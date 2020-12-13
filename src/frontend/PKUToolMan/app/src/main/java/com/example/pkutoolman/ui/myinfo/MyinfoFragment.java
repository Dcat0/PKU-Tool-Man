package com.example.pkutoolman.ui.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pkutoolman.R;

public class MyinfoFragment extends Fragment {

    private MyinfoViewModel myinfoViewModel;
    private Button btModify;
    private int Flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myinfoViewModel =
                new ViewModelProvider(this).get(MyinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_myinfo);
        myinfoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        Fragment ptThis = this;
        Flag = 0;
        btModify = root.findViewById(R.id.gotomodify_button);
        btModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fg = new ModifyInfoFragment();
                fm.beginTransaction().addToBackStack(null).replace(R.id.container,fg).commit();
            }
        });


        return root;
    }
}