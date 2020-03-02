package com.example.gpslocation.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private AutocompleteSupportFragment autocompleteFragment;
    static Place placee;
    private ImageView iGps;
    public  TextView txsuccessful, txsearch;
    private LatLng  latLng, newLocation;
    private Address address  , address2;
    public  CameraPosition cameraPosition;
    private ViewModell viewModell;
    private Button location;
    boolean aBoolean = false,isaBoolean=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_maps);
        Permission();
        iGps = (ImageView) findViewById(R.id.ic_gps);
        txsearch = findViewById(R.id.input_search);
        txsuccessful = findViewById(R.id.txSuccessful);
        setupobserve();
        location=findViewById(R.id.location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setPlacee();

        iGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // LatLng here2 = new LatLng(location1.getLatitude(), location.getLongitude());
                // mMap.addMarker(new MarkerOptions().position(here2).title("I Am Here Hello ")).setVisible(false);

                Permission();
                getlooo();/*if (newLocation != null){

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation,13));
                if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }else {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }}else  checkPermission();
*/
            }
        });


    }
    public void setPlacee (){

        final String TAG = "placeautocomplete";

        Places.initialize(getApplicationContext(), "AIzaSyDZBBzdhWw64zTG3F-sHCyrsdMTL_zvtXE");
        PlacesClient placesClient = Places.createClient(this);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placee = place;
                String searchString = placee.getName();
                txsearch.setText(searchString);

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
                    //  mMap.getUiSettings().setZoomControlsEnabled(true);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    txsearch.setText(place.getName());


                }
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);


            }
        });





    }
    public void    getlooo(){

        try {
            if (aBoolean) {
               final Task locationResult = mFusedLocationClient.getLastLocation();

                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location location= (Location) task.getResult();
                            if (location!=null){

                                LatLng latLng = new LatLng( location.getLatitude(),location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            }


                        } /*else {
                            Log.d("TAG", "Current location is null. Using defaults.");
                            Log.e("TAG", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }*/
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }




    }

    public  void Permission(){

        PermissionListener permissionlistener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {
                aBoolean=true;
                if (isaBoolean){
                    getlooo();
                Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }}

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                aBoolean=false;

                Toast.makeText(MapsActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();




    }
    public void setupobserve(){

        viewModell = new ViewModelProvider(this).get(ViewModell.class);
        viewModell.getMutableLiveDataSupportwdLocation().observe(this, new Observer<SupportwdLocation>() {
            @Override
            public void onChanged(SupportwdLocation supportwdLocation) {
                txsuccessful.setText(supportwdLocation.getResult().getCity().getNameAr()+","+supportwdLocation.getResult().getNameAr());

               // txsearch.setText(supportwdLocation.getResult().getName() + supportwdLocation.getResult().getCity().getName());
                location.setText(R.string.next);

                txsuccessful.setBackgroundResource(R.drawable.bgtxissuccessful);
            }
        });

        viewModell.getMutableLiveDataError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                txsuccessful.setText(errorResponse.getMessageAr());
                location.setText(R.string.bt_suggest_this_location);

              //  txsearch.setText(name);

                txsuccessful.setBackgroundResource(R.drawable.bgtxnotsuccessful);

            }
        });



    }

    public void location_setting(double lat, double lan, final String name) {

        viewModell.getretrofit(lat,lan);


    }
    public void checkPermission(){


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){


            isaBoolean=true;
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();


        }else{
            showGPSDisabledAlertToUser();
            isaBoolean=false;
          //  getlocation();

        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();



    }
    /*
    public void getlocation(){

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                LatLng here = new LatLng(location.getLatitude(), location.getLongitude());

                newLocation =here;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14));
                mMap.getUiSettings().setZoomControlsEnabled(true);

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
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }*/
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                cameraPosition = mMap.getCameraPosition();
                //    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition.target,14));

                List<Address> addressList = new ArrayList<>();
                try {

                    addressList = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);

                } catch (IOException e) {
                    e.getMessage();
                }

                if (addressList.size() > 0) {
                     address2 = addressList.get(0);
                    addressList.get(0).toString();
                    Log.i("PlaceInfo", addressList.toString());
                    latLng = new LatLng(address2.getLatitude(), address2.getLongitude());
                    location_setting(cameraPosition.target.latitude, cameraPosition.target.longitude,address2.getFeatureName());

                    txsearch.setText(address2.getFeatureName()+address2.getCountryName()+address2.getLocality());

                }
            }
        });


         //  getlocation();
    }
}
