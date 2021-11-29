package com.example.habot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.habot.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HabitDetailMaps extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap mMap;
    private ActivityMapsBinding binding;
    Button add_button;
    List<Address> addresses;
    Bundle bundle;
    Double address[] = new Double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        String addr = bundle.getString("Address");
        int index = addr.indexOf(",");
        Log.d("TAG", "onCreate: -----------!!!"+addr.substring(0,index)+"9999"+addr.substring(index));
        if(index != -1) {
            address[0] = Double.parseDouble(addr.substring(0, index));
            address[1] = Double.parseDouble(addr.substring(index+1));
        }
        add_button = findViewById(R.id.add_button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(HabitDetailMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            // Add a marker in Sydney and move the camera
//            LatLng Edmonton = new LatLng(53.631611, -113.323975);
//            address[0] = Edmonton.latitude;
//            address[1] = Edmonton.longitude;
//            try {
//                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
//                addresses = geocoder.getFromLocation(
//                        Edmonton.latitude, Edmonton.longitude, 1
//                );
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mMap.addMarker(new MarkerOptions().position(Edmonton).title("Marker in Edmonton"));
            LatLng newplace = new LatLng(address[0],address[1]);
            mMap.addMarker(new MarkerOptions().position(newplace).title(address[0]+","+address[1]));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newplace));

        } else {
            ActivityCompat.requestPermissions(HabitDetailMaps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
//                Log.d("TAG", "onMapClick: 0000000000000000"+latLng.longitude+"iiiiiiiiiii"+latLng.latitude);
//
//                try {
//                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
//                    addresses = geocoder.getFromLocation(
//                            latLng.latitude, latLng.longitude, 1
//                    );
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.d("TAG", "onMapClick: 00000000000"+ addresses.get(0).getAddressLine(0));

                address[0] = latLng.latitude;
                address[1] = latLng.longitude;
//                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getAddressLine(0)));
                mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.latitude+","+latLng.longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is designed when the someone clicked the add button,
             * the app will get the location's latitude and longitude and jump back to the HabitEventDetailActivity.
             */
            @Override
            public void onClick(View v) {

                bundle.putString("Address", address[0]+","+address[1]);
                bundle.putBoolean("maps",true);
//                bundle.putString("Address", addresses.get(0).getAddressLine(0));
//                bundle.putDouble("longtitude",address[1]);
//                bundle.putDouble("latitude",address[0]);
                Intent Jump = new Intent();
                Jump.setClass(HabitDetailMaps.this, HabitEventDetailActivity.class);
                Jump.putExtras(bundle);
                startActivity(Jump);

            }
        });

    }
}
