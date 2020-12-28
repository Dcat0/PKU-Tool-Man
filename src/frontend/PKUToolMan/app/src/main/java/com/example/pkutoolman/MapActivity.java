package com.example.pkutoolman;


import android.content.Context;
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
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;

public class MapActivity extends AppCompatActivity {


    private MapView mMapView = null;
    public BaiduMap mBaiduMap;
    public double latitude;
    public double longitude;
    private GeoCoder mCoder;
    private LocationClient mLocationClient;
    private String start, dest;
    public LatLng startLL=null, destLL=null;
    private RoutePlanSearch mSearch;

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
        mSearch = RoutePlanSearch.newInstance(); //用于路线规划

        setListener();
        drawStartAndDest(); // 绘制取货点和送货点
        //zoomToSpan();
        getLocation(); //绘制我的位置
    }

    private void setListener() {
        mCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                    if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                        //没有检索到结果
                        Toast.makeText(getApplicationContext(), "地图定位错误", Toast.LENGTH_LONG).show();
                        System.out.println("地图定位错误");
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
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //创建WalkingRouteOverlay实例
                if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    System.out.println("路线规划失败");
                    Toast.makeText(getApplicationContext(), "地图路线规划失败", Toast.LENGTH_LONG).show();
                    return;
                }
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }
    private void drawStartAndDest() {
        // 获得传入的起点和终点的内容
        Intent intent = getIntent();
        start = "北京大学" + intent.getStringExtra("start");
        dest = "北京大学" + intent.getStringExtra("dest");
        System.out.println(start);
        System.out.println(dest);
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
        if (startLL != null && destLL != null) {
            zoomToSpan(); //缩放地图比例
            System.out.println("开始路线规划");
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(PlanNode.withLocation(startLL))
                    .to(PlanNode.withLocation(destLL))); //进行路线规划
        }

    }

    //进行比例尺的缩放
    private void zoomToSpan() {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng((startLL.latitude + destLL.latitude)/2, (startLL.longitude + destLL.longitude)/2));
        double maxdis= DistanceUtil.getDistance(startLL, destLL);
        int [] zoomSize={10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        int [] zoomlevel={20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3};
        double mapWidth = maxdis/9;
        int dx=0;
        for(int i=0;i<zoomSize.length;i++){
            if(mapWidth < zoomSize[i]) {
                dx = i;
                break;
            }
        }
        builder.zoom(zoomlevel[dx]);
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
        mLocationClient.stop();
        System.out.println("destroy map");
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mCoder.destroy();
        mSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        super.onDestroy();
    }

    public void MapBack(View v){
        finish();
    }
}
