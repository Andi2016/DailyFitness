package com.android.andi.dailyfitness3;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;

public class SearchActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, OnInfoWindowClickListener, InfoWindowAdapter, OnMarkerClickListener{

    MapView mapView;
    AMap aMap;
    private LocationManagerProxy mLocationManagerProxy;
    private OnLocationChangedListener mListener;
    Marker Gym1;
    Marker Gym2;
    Marker Gym3;

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
        showMap();

    }

    /**
     * show map
     */
    private void showMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            findViewById(R.id.button_location).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUpMap();
                }

            });


        }
    }

    /**
     * location
     */
    private void setUpMap(){
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.strokeColor(Color.BLACK);
        locationStyle.strokeWidth(1.0f);
        locationStyle.radiusFillColor(0x8333);
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_camera));
        aMap.setMyLocationStyle(locationStyle);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);



        /**
         * insert markers
         */
        LatLng ll_gym1 = new LatLng(31.2734826252,120.7399154083);//XJTLU
        LatLng ll_gym2 = new LatLng(31.2968021849,120.7187859378);//link
        LatLng ll_gym3 = new LatLng(31.2627391399,120.7465801138);//blocl c

        Gym1 = aMap.addMarker(new MarkerOptions()
                .position(ll_gym1)
                .title("XJTLU")
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send)))
                .draggable(true));

        Gym2 = aMap.addMarker(new MarkerOptions()
                .position(ll_gym2)
                .title("Link")
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send)))
                .draggable(true));

        Gym3 = aMap.addMarker(new MarkerOptions()
                .position(ll_gym3)
                .title("Block C")
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send)))
                .draggable(true));

        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
        //Gym1.showInfoWindow();
       

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

    /**
     * start location
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener=onLocationChangedListener;
        if (mLocationManagerProxy==null) {
            mLocationManagerProxy=LocationManagerProxy.getInstance(this);

            mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    /**
     * stop location
     */
    @Override
    public void deactivate() {
        mListener = null;
        if(mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
            mLocationManagerProxy = null;
        }
    }

    /**
     * listen when location changed
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener!=null && aMapLocation!=null) {
            mListener.onLocationChanged(aMapLocation);
        }
    }

    /**
     * Not used yet
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * Not used yet
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Not used yet
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Not used yet
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.marker_infowindow, null);
        TextView title = ((TextView) view.findViewById(R.id.infowindow_title));
        title.setText(marker.getTitle());

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        startActivity(new Intent(SearchActivity.this, GymActivity.class));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

