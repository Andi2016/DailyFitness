package com.android.andi.dailyfitness3;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;

public class SearchActivity extends AppCompatActivity implements LocationSource, AMapLocationListener{

    MapView mapView;
    AMap aMap;
    private LocationManagerProxy mLocationManagerProxy;
    private OnLocationChangedListener mListener;

    private static final String TAG = "LocationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.button_fitness_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this, FitnessActivity.class));
                finish();
            }

        });

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            //initLocation();
            setUpMap();

        }
    }

    private void initLocation(){
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1,15,this);

        mLocationManagerProxy.setGpsEnable(false);
    }

    private void setUpMap(){
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.strokeColor(Color.BLACK);
        locationStyle.strokeWidth(1.0f);
        locationStyle.radiusFillColor(0x8333);
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_camera));
        aMap.setMyLocationStyle(locationStyle);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
    }
    

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener arg0) {
        mListener=arg0;
        if (mLocationManagerProxy==null) {
            mLocationManagerProxy=LocationManagerProxy.getInstance(this);
            //调用控制 每2秒更新1次，其主要的实时更改在最后一个参数AMapLocationListener
            mLocationManagerProxy.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if(mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
            mLocationManagerProxy = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation arg0) {
        if (mListener!=null && arg0!=null) {
            mListener.onLocationChanged(arg0);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


