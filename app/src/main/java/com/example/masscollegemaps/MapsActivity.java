package com.example.masscollegemaps;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import androidx.fragment.app.FragmentActivity;

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

    String selectedName;
    int selectedImage;
    int selectedAnthem;
    double selectedLong;
    double selectedLat;

    ToggleButton zoomer;
    ToggleButton pauseResume;
    SeekBar seekBar;

    Boolean bPlaying = false;
    Boolean bZoomed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = null;

        setContentView(R.layout.fragment_maps);
        initTools();
        map = findViewById(R.id.map);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            selectedName = intent.getStringExtra("name");
            selectedImage = intent.getIntExtra("image", 0);
            selectedAnthem = intent.getIntExtra("anthem", 0);
            selectedLong = intent.getDoubleExtra("longitude", 0);
            selectedLat = intent.getDoubleExtra("latitude", 0);

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initTools() {
        pauseResume = findViewById(R.id.pauseresume);
        pauseResume.setOnClickListener(v -> {
            if (bPlaying)
                setPlay();
            else
                setPause();
        });
        zoomer = findViewById(R.id.zoomer);
        LatLng location = new LatLng(selectedLat, selectedLong);
        zoomer.setOnClickListener(v -> {
            int zoomLevel;
            if (bZoomed) {
                moveCamera(10);
                if (seekBar != null)
                    seekBar.setProgress(10);
            }
            else {
                    moveCamera(2);
                    if (seekBar != null)
                        seekBar.setProgress(2);
                }
            bZoomed = !bZoomed;
        });
        seekBar = findViewById(R.id.seekBar);
        seekBar.setVisibility(SeekBar.VISIBLE);
        seekBar.setProgress(0);
        seekBar.setMax(21);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int zoomLevel = progress;
                LatLng location = new LatLng(selectedLat, selectedLong);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        playAnthem();
        setTitle(selectedName);
        mMap = googleMap;
        moveCamera(10);
    }

    void moveCamera(int bZoom) {
        LatLng location = new LatLng(selectedLat, selectedLong);
        mMap.addMarker(new MarkerOptions().position(location).title(selectedName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, bZoom));
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

    protected void setPlay() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null)
                    mediaPlayer.reset();
                mediaPlayer = null;
                playAnthem();
                bPlaying = false;
            }
        });
    }

    protected void setPause() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
                bPlaying = true;
            }
        });
    }
}