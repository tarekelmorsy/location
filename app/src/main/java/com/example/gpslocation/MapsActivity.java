package com.example.gpslocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private TextView mSearchText;
    AutocompleteSupportFragment autocompleteFragment;
    static Place placee;
    TextView textView;
    ImageView iGps;
    Button button;
    TextView textView1,textView2;
    ImageView imageView;
    LatLng egypt = new LatLng( 35.3626542,26.8450586)
, latLng ;
    Address address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iGps = (ImageView) findViewById(R.id.ic_gps);
        mSearchText = findViewById(R.id.input_search);
        textView = findViewById(R.id.txtInfo);

        button = findViewById(R.id.location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final String TAG = "placeautocomplete";



        Places.initialize(getApplicationContext(), "AIzaSyDZBBzdhWw64zTG3F-sHCyrsdMTL_zvtXE");
        PlacesClient placesClient = Places.createClient(this);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        button= findViewById(R.id.location);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placee = place;
                String searchString = placee.getName();

                mSearchText.setText(searchString);

                Geocoder geocoder = new Geocoder(MapsActivity.this);
                List<Address> addressList = new ArrayList<>();

                try {
                    addressList = geocoder.getFromLocationName(searchString, 1);


                } catch (IOException e) {
                    Log.d(TAG, "geoLocate: " + e.getMessage());
                }

                if (addressList.size() > 0) {
                     address = addressList.get(0);
                    addressList.get(0).toString();
                    Log.i("PlaceInfo", addressList.toString());
                     latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address.getCountryName())).showInfoWindow();
                    location_setting(latLng,address.getCountryName());
                    mSearchText.setText(place.getName());

                }
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void location_setting(LatLng latLng1, String name){



        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapsActivity.this,R.style.ButtonSheetDialog);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                (LinearLayout)findViewById(R.id.layoutcontener));
        textView1 = view.findViewById(R.id.sorrytx);
        textView2 = view.findViewById(R.id.locationtx);


        imageView = view.findViewById(R.id.imageView2);
        if (latLng1.longitude != egypt.longitude &&latLng1.latitude!=egypt.latitude){
            String sorry = getString(R.string.sorry).toString();
            textView1.setText(sorry);
           textView2.setText(name);
            imageView.setBackgroundResource(R.drawable.imag);
        }else {

            imageView.setBackgroundResource(R.drawable.imaggre);
            textView1.setText(R.string.deliver);
            textView2.setText(name);
            Button set =view.findViewById(R.id.letyourbt);
            set.setText("Let's Go");



        }
        view.findViewById(R.id.letyourbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapsActivity.this,Phone.class);
                startActivity(intent);


                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

    }




        @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    mMap.addMarker(new MarkerOptions().position(marker.getPosition()).title("I Am Here ")).showInfoWindow();
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                    return false;
                }});

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                LatLng here = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(here).title("I Am Here ")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here,13));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                location_setting(here,getString(R.string.locationnotsupported).toString());


                iGps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatLng here2 = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(here2).title("I Am Here Hello ")).showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here2,13));
                        mMap.getUiSettings().setZoomControlsEnabled(true);

                        mSearchText.setText("I Am Here Hello ");
                        location_setting(here2,getString(R.string.locationnotsupported).toString());

                    }
                });}

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }}}
