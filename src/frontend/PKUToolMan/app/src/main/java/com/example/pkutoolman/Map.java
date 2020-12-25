package com.example.pkutoolman;

import android.app.Application;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.pkutoolman.baseclass.Post;
import com.example.pkutoolman.ui.orderinfo.OrderinfoFragment;

public class Map extends AppCompatActivity {
    private MapView mMapView = null;
    public BaiduMap mBaiduMap;
    public double latitude;
    public double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        GeoCoder mCoder = GeoCoder.newInstance();

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                    if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                        //没有检索到结果
                        System.out.println("fail");
                        return;
                    } else {
                        System.out.println("success");
                        latitude = geoCodeResult.getLocation().latitude;
                        longitude = geoCodeResult.getLocation().longitude;
                        System.out.println(latitude);
                        System.out.println(longitude);
                        show(longitude,latitude);
                    }
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                System.out.println("123");
            }
        };

        mCoder.setOnGetGeoCodeResultListener(listener);
        mCoder.geocode(new GeoCodeOption()
                .city("北京")
                .address("北京大学45乙"));
        mCoder.destroy();
    }

    public void show(double longitude,double latitude){
        //定义Maker坐标点
        System.out.println("show");
        System.out.println(latitude);
        System.out.println(longitude);
        LatLng point = new LatLng(latitude,longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(20);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    public void  MapBack(View v){
        Intent intent = new Intent();
        intent.setClass(Map.this, OrderinfoFragment.class);
        startActivity(intent);
    }
}
