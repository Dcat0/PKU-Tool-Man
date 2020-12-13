package com.example.pkutoolman.ui.myorder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyorderFragment extends Fragment {

    private MyorderViewModel myorderViewModel;
    private ListView mLv;
    private Button bt1,bt2;
    JSONObject jsonObject = null;

    private void getMyOrderInfo(int userID) {
        // 向后端获取订单内容 存入Data中

        /*String url = "192.168.1.1/order/myorderlist?userID=" + String.valueOf(userID);
        StringBuffer buffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");

            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) buffer.append(str);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();

            jsonObject = new JSONObject(buffer.toString());

        } catch (Exception e)
        {
            e.printStackTrace();
        }*/

        try {
            String test = "[{\"id\":123,\"userID\":3214},{\"id\":3213,\"userID\":2464}]";
            JSONArray a = new JSONArray(test);
            System.out.println(a.length());
            for (int i=0; i<a.length(); ++i) {
                JSONObject o = a.getJSONObject(i);
                System.out.println(o.get("id"));
                System.out.println(o.get("userID"));
            }
            List<Order> tempOrderInfo = new ArrayList<Order>();
            tempOrderInfo.add(new Order(123,231,23,"45乙","45甲","搞快点"));
            tempOrderInfo.add(new Order(312,2233,2333,"45","47","搞快点"));
            Data.setMyOrderInfo(tempOrderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("on create view");
        getMyOrderInfo(Data.getUserID());
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
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id)
            {
                System.out.println(position);
                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

}