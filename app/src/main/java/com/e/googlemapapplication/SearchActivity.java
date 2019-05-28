package com.e.googlemapapplication;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import model.LatitudeLongitude;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button Btnsearch;
    private List<LatitudeLongitude> Latitduelongitudelist;
    Marker markerName;
    CameraUpdate center,zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etCity=findViewById(R.id.etCity);
        Btnsearch=findViewById(R.id.Btnsearch);

        fillArrayListAndSetAdapter();

        Btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCity.getText().toString()))
                {
                    etCity.setError("Please Enter a Place Name");
                    return;
                }

                int position =SearchArrayList(etCity.getText().toString());
                if (position> -1)
                    loadMap(position);
                else
                    Toast.makeText(SearchActivity.this, "Location not found by name:"+etCity.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        center=CameraUpdateFactory.newLatLng(new LatLng(27.7172453,85.3239605));
        zoom=CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }
    private void loadMap(int position) {
        if(markerName!=null){
            markerName.remove();
        }
        double latitude=Latitduelongitudelist.get(position).getLat();
        double longitude=Latitduelongitudelist.get(position).getLon();
        String marker=Latitduelongitudelist.get(position).getMarker();
        center=CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
        zoom=CameraUpdateFactory.zoomTo(17);
        markerName=mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(marker));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }


    private void fillArrayListAndSetAdapter() {

        Latitduelongitudelist=new ArrayList<>();
        Latitduelongitudelist.add(new LatitudeLongitude(27.7134481,85.3241922,"Naagpokhari"));
        Latitduelongitudelist.add(new LatitudeLongitude(27.7181749,85.3173212,"Narayanhiti Palace Muesueum"));
        Latitduelongitudelist.add(new LatitudeLongitude(27.7127827,85.3265391,"Hotel Brishaspti"));

        String[] data=new String[Latitduelongitudelist.size()];

        for(int i=0;i<data.length;i++){
            data[i]=Latitduelongitudelist.get(i).getMarker();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                data
        );

        etCity.setAdapter(adapter);
        etCity.setThreshold(1);
    }
    private int SearchArrayList(String name) {
        for(int i=0; i<Latitduelongitudelist.size();i++){
            if (Latitduelongitudelist.get(i).getMarker().contains(name)){
                return i;
            }
        }
        return -1;
    }



}
