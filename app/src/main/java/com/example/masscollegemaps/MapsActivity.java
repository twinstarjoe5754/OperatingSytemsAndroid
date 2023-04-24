package com.example.masscollegemaps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.masscollegemaps.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FrameLayout map;

    String selectedName;
    int selectedImage;
    double selectedLong;
    double selectedLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        map = findViewById(R.id.map);
        Intent intent = getIntent();

        if (intent.getExtras() != null){
            selectedName      = intent.getStringExtra("name");
            selectedImage     = intent.getIntExtra("image", 0);
            selectedLong      = intent.getDoubleExtra("longitude", 0);
            selectedLat       = intent.getDoubleExtra("latitude", 0);

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        setTitle(selectedName);
        mMap = googleMap;

        LatLng location = new LatLng(selectedLat, selectedLong);
        mMap.addMarker(new MarkerOptions().position(location).title(selectedName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}