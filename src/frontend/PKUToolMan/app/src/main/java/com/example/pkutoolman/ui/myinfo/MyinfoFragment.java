package com.example.pkutoolman.ui.myinfo;

import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pkutoolman.LoginActivity;
import com.example.pkutoolman.MainActivity;
import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;

public class MyinfoFragment extends Fragment {

    MyinfoViewModel myinfoViewModel;
    Button btModify,btQuit;
    TextView nickname,Email,Phone,Credit;
    int Flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myinfoViewModel =
                new ViewModelProvider(this).get(MyinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);

        nickname = (TextView) root.findViewById(R.id.nicknameifo);
        nickname.setText(Data.getNickName());
        Email = (TextView) root.findViewById(R.id.emailinfo);
        Email.setText(Data.getEmail());
        Phone = (TextView) root.findViewById(R.id.phoneinfo);
        Phone.setText(Data.getPhoneNum());
//        Credit = (TextView) root.findViewById(R.id.creditinfo);
//        Credit.setText(Data.getCredit());

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
                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //Fragment fg = new ModifyInfoFragment();
                //fm.beginTransaction().addToBackStack(null).replace(R.id.container,fg).commit();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.navigation_modifyinfo);
            }
        });
        btQuit = root.findViewById(R.id.logout);
        btQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return root;
    }
}