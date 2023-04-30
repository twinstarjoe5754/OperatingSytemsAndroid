package com.example.masscollegemaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    GridView gridview;

    String[] names = new String[]{
            "Antigua",
            "Australia",
            "Barbados",
            "Brazil",
            "Canada",
            "Denmark",
            "Egypt",
            "France",
            "Germany",
            "Greece",
            "Iceland",
            "Ireland",
            "Italy",
            "Jamaica",
            "Mexico",
            "St. Kitts and Nevis",
            "United Kingdom",
            "United States",
    };
    int[] images = new int[]{
            R.drawable.flag_of_antigua_and_barbuda,
            R.drawable.flag_of_australia,
            R.drawable.flag_of_barbados,
            R.drawable.flag_of_brazil,
            R.drawable.flag_of_canada,
            R.drawable.flag_of_denmark,
            R.drawable.flag_of_egypt,
            R.drawable.flag_of_france,
            R.drawable.flag_of_germany,
            R.drawable.flag_of_greece,
            R.drawable.flag_of_iceland,
            R.drawable.flag_of_ireland,
            R.drawable.flag_of_italy,
            R.drawable.flag_of_jamaica,
            R.drawable.flag_of_mexico,
            R.drawable.flag_of_saint_kitts_and_nevis,
            R.drawable.flag_of_the_united_kingdom,
            R.drawable.flag_of_the_united_states
    };
    int[] anthems = new int[]{
            R.raw.ag,
            R.raw.au,
            R.raw.bb,
            R.raw.br,
            R.raw.ca,
            R.raw.dk,
            R.raw.eg,
            R.raw.fr,
            R.raw.ar,
            R.raw.gr,
            R.raw.is,
            R.raw.ie,
            R.raw.it,
            R.raw.jm,
            R.raw.mx,
            R.raw.kn,
            R.raw.gb,
            R.raw.us
    };

    LatLng[] selectedCoordinates = new LatLng[]{
            new LatLng(17.080817709376724, -61.82930247684425), // antigua
            new LatLng(-33.855619455344026, 151.1788475689928), // australia sydney
            new LatLng(13.102379216842754, -59.59302847298943), // Barbados
            new LatLng(-15.78333333, -47.916667),               // Brazil (Brazilia)
            new LatLng(45.50627310620357,  -73.61642962581581), // Montreal Canada
            new LatLng(55.66666667,     12.583333),             // Denmark( Copenhagen)
            new LatLng(30.05,           31.25),                 // Egypt (cairo )
            new LatLng(48.86666667,  2.333333),                 // france (Paris)
            new LatLng(52.51666667,  13.4),                     // germany (Berlin)
            new LatLng(37.98333333,  23.733333),                // greece (Athens)
            new LatLng(64.15,  -21.95),                         // Iceland (Reykjavik )
            new LatLng(53.329609113076934, -6.280091935482406), // Ireland Dublin
            new LatLng(41.9, 12.483333),                        // Italy Rome
            new LatLng(18.0491678267187,   -76.82120673837873), // Kingston Jamaica
            new LatLng(19.446155649781648, -99.10935478364311), // Mexico City
            new LatLng(17.140667016809264, -62.62301457927994), // St. Kitts and Nevis
            new LatLng(51.521674461220506, -0.15950330376990177), // London
            new LatLng(38.91714994898418, -77.03176074214656)    // Washington DC
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter(names, images, this);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                int selectedImage = images[position];
                int selectedAnthem = anthems[position];
                LatLng selectedGPS = selectedCoordinates[position];
                startActivity(new Intent(MainActivity.this, MapsActivity.class).
                        putExtra("name", selectedName).
                        putExtra("image", selectedImage).
                        putExtra("anthem", selectedAnthem).
                        putExtra("latitude", selectedGPS.latitude).
                        putExtra("longitude", selectedGPS.longitude));
            }
        });
    }

    public class CustomAdapter extends BaseAdapter {

        private Context context;
        private String[] imageNames;
        private int[] imagesPhoto;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagesPhoto, Context context) {
            this.imageNames = imageNames;
            this.imagesPhoto = imagesPhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesPhoto.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);
                TextView tvName = view.findViewById(R.id.tvName);
                ImageView imageView = view.findViewById(R.id.imageView);

                tvName.setText(imageNames[i]);
                imageView.setImageResource(imagesPhoto[i]);
            }
            return view;
        }
    }
}