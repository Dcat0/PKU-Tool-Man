package com.example.pkutoolman;


import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View;
import android.content.Intent;
import android.widget.TextView;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.example.pkutoolman.ui.orderinfo.OrderinfoFragment;

public class MapActivity extends AppCompatActivity {


    private MapView mMapView = null;
    public BaiduMap mBaiduMap;
    public double latitude;
    public double longitude;
    private GeoCoder mCoder;
    private LocationClient mLocationClient;
    private String start, dest;
    public LatLng startLL=null, destLL=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true); //开启地图的定位图层
        mCoder = GeoCoder.newInstance(); //用于位置信息和经纬度的转换

        setListener();
        drawStartAndDest(); // 绘制取货点和送货点
        zoomToSpan();
        //getLocation(); //绘制我的位置
    }

    private void setListener() {
        mCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                    if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                        //没有检索到结果
                        finish(); // 如果有错直接返回
                        return;
                    } else {
                        if (geoCodeResult.getAddress() == start) {
                            startLL = new LatLng(geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude);
                            show(geoCodeResult.getLocation(), R.layout.startpoint);
                        } else {
                            destLL = new LatLng(geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude);
                            show(geoCodeResult.getLocation(), R.layout.destpoint);
                        }
                    }
                }
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    return;
                }
            }
        });

    }
    private void drawStartAndDest() {
        // 获得传入的起点和终点的内容
        //Intent intent = getIntent();
        //String start = intent.getStringExtra("start");
        //String dest = intent.getStringExtra("dest");
        start = "北京大学理科教学楼";
        dest = "北京大学45乙";
        mCoder.geocode(new GeoCodeOption().city("北京").address(start));
        //show(new LatLng(latitude, longitude));
        mCoder.geocode(new GeoCodeOption().city("北京").address(dest));
        //show(new LatLng(latitude, longitude));
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

    public void getLocation() {
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }


    //在地图上展示标点信息
    public void show(LatLng point, int layout){
        System.out.println("show");
        /*if (layout == R.layout.startpoint) {
            startLL = point;
        } else destLL = point;*/
        System.out.println(startLL);
        System.out.println(destLL);
        //定义Maker坐标点
        //构建Marker图标
        System.out.println(point);
        View addViewContent = this.getLayoutInflater().inflate(layout, null);
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromBitmap(bitmap);
        //BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmapDesc);
        //在地图上添加Marker，并显示
        //mBaiduMap.clear();
        mBaiduMap.addOverlay(option);
    }

    //进行比例尺的缩放
    private void zoomToSpan() {
        /* LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(destLL);
        builder.include(startLL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory
                .newLatLngBounds(builder.build())); */
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(39.998511,116.316494));
        builder.zoom(17.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //mLocationClient.stop();
        System.out.println("destroy map");
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mCoder.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        super.onDestroy();
    }

    public void MapBack(View v){
        finish();
    }
}
