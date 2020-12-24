package com.example.pkutoolman.ui.myinfo;

import androidx.lifecycle.ViewModelProvider;

import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pkutoolman.R;

public class ModifyInfoFragment extends Fragment {

    private ModifyInfoViewModel mViewModel;
    private Button btModify,btReturn;

    public static ModifyInfoFragment newInstance() {
        return new ModifyInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modifymyinfo, container, false);
        btModify = root.findViewById(R.id.modifyinfo_button);
        btReturn = root.findViewById(R.id.returnfrommodify_button);
        Fragment ptThis = this;
        btModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.navigation_myinfo);
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.navigation_myinfo);
            }
        });

        return root;
    }

}