package com.example.masscollegemaps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    MediaPlayer mediaPlayer;

    private GoogleMap mMap;
    private FrameLayout map;

    String  selectedName;
    int     selectedImage;
    int     selectedAnthem;
    double  selectedLong;
    double  selectedLat;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = null;

        setContentView(R.layout.activity_maps);
        map = findViewById(R.id.map);
        Intent intent = getIntent();

        if (intent.getExtras() != null){
            selectedName      = intent.getStringExtra("name");
            selectedImage     = intent.getIntExtra("image", 0);
            selectedAnthem    = intent.getIntExtra("anthem", 0);
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

        playAnthem();
        setTitle(selectedName);
        mMap = googleMap;
        LatLng location = new LatLng(selectedLat, selectedLong);
        mMap.addMarker(new MarkerOptions().position(location).title(selectedName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private void playAnthem() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, selectedAnthem);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
            }
        });
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

         super.onStop();
    }
    @Override
    protected void onPause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
          }
        super.onPause();
    }
}