package com.example.gpslocation.ui;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
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
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private AutocompleteSupportFragment autocompleteFragment;
    static Place placee;
    private ImageView iGps;
    public TextView txsuccessful, txsearch;
    private LatLng latLng, newLocation;
    private Address address, address2;
    public CameraPosition cameraPosition;
    private ViewModell viewModell;
    private Button location;
    boolean mPremissionGranted = false;
    boolean mGpsEnabled = false;
    private View mapView;
    String supportwdLocation1,country;
    boolean issuppotted = false;
    boolean gg= false;
    boolean booleanPermission,l;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Permission();
        iGps = (ImageView) findViewById(R.id.ic_gps);
        txsearch = findViewById(R.id.input_search);


        txsuccessful = findViewById(R.id.txSuccessful);
        gg=true;


        setupobserve();
        location = findViewById(R.id.location);
        initMap();
        setPlacee();

        iGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                booleanPermission =false;
                gg=false;




                    Permission();




                getDeviceLocation();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (issuppotted) {
                    getdialogFragment(issuppotted, supportwdLocation1, "نوصل لك عالموقع الحالي؟");
                } else {
                    getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


                }


            }
        });
        if (l){
            if (issuppotted) {
                getdialogFragment(issuppotted, supportwdLocation1, "نوصل لك عالموقع الحالي؟");
            } else {
                getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


            }
        }

    }



    public void setPlacee() {
        final String TAG = "placeautocomplete";
        Places.initialize(getApplicationContext(), "AIzaSyDZBBzdhWw64zTG3F-sHCyrsdMTL_zvtXE");
        PlacesClient placesClient = Places.createClient(this);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        int AUTOCOMPLETE_REQUEST_CODE = 1;
       // int AUTOCOMPLETE_REQUEST_CODE = 1;

// Set the fields to specify which types of place data to
// return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Start the autocomplete intent.



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                //   KeyboardView.OnClickListener(new );

                autocompleteFragment.a.extendSelection(0);
                placee = place;
                //  autocompleteFragment.a;

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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    txsearch.setText(place.getName());


                }

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }

        });


    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
    }

    public void getDeviceLocation() {
        String sss;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mPremissionGranted) {
                final Task locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location location = (Location) task.getResult();
                            if (location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                                //   supportwdLocation3 = latLng.toString();
                                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                        latLng, 14);
                                mMap.animateCamera(cameraUpdate);
                                double lat = location.getLatitude();
                                double lan = location.getLongitude();
                                Geocoder geocoder;
                                List<Address> addresses = new ArrayList<>();
                                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                try {
                                    addresses = geocoder.getFromLocation(lat, lan, 1);
                                    if (addresses.size() > 0) {
                                        country = addresses.get(0).getAddressLine(0);
                                        Log.d("TAG", "onCameraMove/: " + country);
                                        // searchText.setText(country);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                country = addresses.get(0).getAddressLine(0);

                                if (gg || booleanPermission){

                                    if (issuppotted ) {
                                        getdialogFragment(issuppotted, supportwdLocation1, "نوصل لك عالموقع الحالي؟");
                                    } else {
                                        getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


                                    }}
                            }
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());

        }



    }

    public void Permission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                mPremissionGranted = true;

                initMap();
                if (checkPermission()) {
                    getDeviceLocation();
                    //Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                mPremissionGranted = false;
l=true;
                if (issuppotted) {
                    getdialogFragment(issuppotted, supportwdLocation1, "نوصل لك عالموقع الحالي؟");
                } else {
                    getdialogFragment(issuppotted, "قاعدة الملك عبد العزيز الجوية، الظهران السعودية", "للأسف،  مكانك برا التغطية");


                }
                LatLng latLng= new LatLng(26.2907608,50.1757243);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));



               // Toast.makeText(MapsActivity.this, "Permisllllllllllllllsion Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    public void setupobserve() {
        viewModell = new ViewModelProvider(this).get(ViewModell.class);
        viewModell.getMutableLiveDataSupportwdLocation().observe(this, new Observer<SupportwdLocation>() {
            @Override
            public void onChanged(SupportwdLocation supportwdLocation) {
                //supportwdLocation1.equals(supportwdLocation);
                issuppotted = true;
                supportwdLocation1 = (supportwdLocation.getResult().getCity().getNameAr() + "," + supportwdLocation.getResult().getNameAr()).toString();
                txsuccessful.setText(supportwdLocation.getResult().getCity().getNameAr() + "," + supportwdLocation.getResult().getNameAr());
                // txsearch.setText(supportwdLocation.getResult().getName() + supportwdLocation.getResult().getCity().getName());
                location.setText(R.string.next);
                //dialogFragment.getDialog().setCanceledOnTouchOutside(true);
                txsuccessful.setBackgroundResource(R.drawable.bgtxissuccessful);

                //  getdialogFragment(supportwdLocation.getStatus(), (supportwdLocation.getResult().getCity().getNameAr() + "," + supportwdLocation.getResult().getNameAr()).toString(), "نوصل لك عالموقع الحالي؟");


            }
        });

        viewModell.getMutableLiveDataError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                txsuccessful.setText(errorResponse.getMessageAr());
                location.setText(R.string.bt_suggest_this_location);
                issuppotted = false;

                txsuccessful.setBackgroundResource(R.drawable.bgtxnotsuccessful);
            }
        });
    }

    public void location_setting(double lat, double lan, final String name) {
        viewModell.getretrofit(lat, lan);
    }

    public boolean checkPermission() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGpsEnabled = false;
            //showGPSDisabledAlertToUser();
            Toast.makeText(this, "من فضلك قم بتفعيل GPS لنتمكن من تحديد موقعك ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            mGpsEnabled = true;
            return true;
        }
    }

    public void getdialogFragment(boolean b, String s, String s2) {

        DialogFragmenttt dialogFragment = new DialogFragmenttt();
        dialogFragment.show(getSupportFragmentManager(), null);
        Bundle bundle = new Bundle();
        bundle.putString("user", s);
        bundle.putString("place", s2);
        bundle.putBoolean("Status", b);

        dialogFragment.setArguments(bundle);
        // dialogFragment.dismiss();


    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

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
                    location_setting(cameraPosition.target.latitude, cameraPosition.target.longitude, address2.getFeatureName());
                    txsearch.setText(address2.getFeatureName() + address2.getCountryName() + address2.getLocality());
                    autocompleteFragment.setText("");

                   // supportwdLocation3 = address2.getFeatureName() + address2.getCountryName() + address2.getLocality();
                    country =  address2.getAddressLine(0);

                }

            }

        });
    }
}