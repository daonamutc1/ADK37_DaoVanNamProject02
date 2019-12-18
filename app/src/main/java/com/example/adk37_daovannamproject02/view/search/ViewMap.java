package com.example.adk37_daovannamproject02.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.databinding.ActivityMapBinding;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewMap extends AppCompatActivity implements OnMapReadyCallback, InterfaceViewMap {
    GoogleMap map;
    PresenterViewMap presenterViewMap;
    ActivityMapBinding binding;
    double CorF = 0;
    int n = 0;
    static ArrayList<ObjectACity> objectViewMap;
    Double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        presenterViewMap = new PresenterViewMap(this, ViewMap.this);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fraMap);
        mapFragment.getMapAsync(ViewMap.this);
        presenterViewMap.progressBar = findViewById(R.id.prbViewMap);
        final Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        CorF = intent.getDoubleExtra("CorF", 0);
        objectViewMap = (ArrayList<ObjectACity>) intent.getSerializableExtra("objFull");
        presenterViewMap.loadBYLoacationClick(lat, lon, CorF);
        binding.tvAddSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterViewMap.loadBYLoacationAdd(lat, lon, CorF);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                presenterViewMap.loadBYLoacationClick(latLng.latitude, latLng.longitude, CorF);
                lat = latLng.latitude;
                lon = latLng.longitude;
            }
        });
    }

    @Override
    public void searchClick(ObjectACity objectACity) {
        binding.tvNameSearch.setText(objectACity.getNameCity() + "");
        Picasso.with(ViewMap.this).load(Defile.URL_HOMEICON + objectACity.getIconnow() + Defile.PNG).into(binding.imgClousSearch);
        binding.tvTempSearch.setText(Math.round(objectACity.getTeampnow() * 1) / 1 + "" + objectACity.getUnit());
        LatLng marker = new LatLng(objectACity.getLat(), objectACity.getLon());
        map.clear();
        map.addMarker(new MarkerOptions().title(objectACity.getNameCity()).snippet(objectACity.getStatusnow()).position(marker));
        if (n == 0) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 9));
            n = n + 1;
        }
    }

    @Override
    public void searchAdd(ObjectACity objectACity, int vitri) {
        if (vitri != -1) {
            Intent intent1 = new Intent();
            intent1.putExtra("vitri", vitri);
            intent1.putExtra("viewMapsend", objectViewMap);
            setResult(RESULT_OK, intent1);
            finish();
        } else {
            objectViewMap.add(objectACity);
            Intent intent1 = new Intent();
            intent1.putExtra("viewMapsend", objectViewMap);
            setResult(RESULT_OK, intent1);
            finish();
            presenterViewMap.progressBar.setVisibility(View.GONE);
        }
    }
}
