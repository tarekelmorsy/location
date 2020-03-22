package com.example.gpslocation.ui;
import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;
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
    private AutocompleteSupportFragment autocompleteFragment;
    static Place placee;
    private ImageView iGps;
    public TextView txsuccessful, txsearch;
    private LatLng latLng;
    private Address address, address2;
    public CameraPosition cameraPosition;
    public static ViewModellIsSupportedLocation viewModellIsSupportedLocation;
    private Button location;
    boolean mPremissionGranted = false;
    boolean mGpsEnabled = false;
    private View mapView;
    boolean closedialog = true;

    String  country;
    boolean isSupported = false;
    boolean iGpsShowFragment = false;
    boolean booleanPermission;
    boolean booleanDeni = true;
    public static boolean checkLocationSupport = false;
    DialogIsSupported dialogFragment;
    Bundle bundle = new Bundle();



    private SupportwdLocationDetails supportwdLocationDetails = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iGps = (ImageView) findViewById(R.id.ic_gps);
        txsearch = findViewById(R.id.input_search);
        txsuccessful = findViewById(R.id.txSuccessful);
        iGpsShowFragment = true;


        setupobserve();
        location = findViewById(R.id.location);
        initMap();
        setPlacee();
        Permission();

        iGps.setOnClickListener(v -> {

            booleanPermission = false;
            iGpsShowFragment = false;
            booleanDeni = false;
            closedialog = false;
            Permission();

            getDeviceLocation();
        });
        location.setOnClickListener(v -> {
                getdialogFragment(isSupported, country);
        });
    }

    /**
      * Get the address of the country the camera is on
     * @param country1
     * @param lat
     * @param lng
     * @return
     */
    public String getCountry( String country1,double lat,double lng){
        ArabicTranslation();

        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat,lng , 1);

            if (addresses.size() > 0) {
                country1 = addresses.get(0).getAddressLine(0);
                Log.d("TAG", "onCameraMove/: " + country1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        country1 = addresses.get(0).getAddressLine(0);
        return  country1;

    }
    /**
     * Search for places on the map
     */

    public void setPlacee() {
        final String TAG = "placeautocomplete";
        Places.initialize(getApplicationContext(), "AIzaSyDZBBzdhWw64zTG3F-sHCyrsdMTL_zvtXE");
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                autocompleteFragment.a.extendSelection(0);
                placee = place;

                String searchString = placee.getName();
                txsearch.setText(searchString);
                ArabicTranslation();

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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    txsearch.setText(place.getName());


                }

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }

        });

/**
 *Translate the address to Arabic
 */
    }
    public void ArabicTranslation(){
        String languageToLoad = "ar";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

    }

    /**
     * get user Location
     *
     */
    public void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mPremissionGranted) {
                final Task locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location location = (Location) task.getResult();
                            if (location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                                ArabicTranslation();
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                        latLng, 14);
                                mMap.animateCamera(cameraUpdate);
                                double lat = location.getLatitude();
                                double lan = location.getLongitude();
                                getCountry(country ,lat,lan);

                                if (iGpsShowFragment || booleanPermission) {

                                    getdialogFragment(isSupported, getCountry(country,latLng.latitude,latLng.longitude));

                                }
                            }
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());

        }
    }

    /**
     * Check the location's permission to the user
     * @return
     */
    public void Permission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                mPremissionGranted = true;

                initMap();
                if (checkPermission()) {
                    getDeviceLocation();
                }
            }


            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                mPremissionGranted = false;
                ArabicTranslation();
                LatLng latLng = new LatLng(26.2561426, 50.1820928);
                if (booleanDeni) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

                String country1 = null;

                if (isSupported || closedialog == false) {

                } else {
                    getdialogFragment(isSupported, getCountry(country1,latLng.latitude,latLng.longitude));


                }

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }


    /**
     * setupobserve from view model
     */

    public void setupobserve() {
        viewModellIsSupportedLocation = new ViewModelProvider(this).get(ViewModellIsSupportedLocation.class);
        viewModellIsSupportedLocation.getMutableLiveDataSupportwdLocation().observe(this, new Observer<SupportwdLocation>() {
            @Override
            public void onChanged(SupportwdLocation supportwdLocation) {
                isSupported = true;
                checkLocationSupport = false;
                txsuccessful.setText(supportwdLocation.getResult().getCity().getNameAr() + "," + supportwdLocation.getResult().getNameAr());
                location.setText(R.string.next);
                txsuccessful.setBackgroundResource(R.drawable.bgtxissuccessful);

            }
        });


        viewModellIsSupportedLocation.getMutableLiveDataError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                txsuccessful.setText(errorResponse.getMessageAr());
                location.setText(R.string.bt_suggest_this_location);
                isSupported = false;
                checkLocationSupport = false;
                txsuccessful.setBackgroundResource(R.drawable.bgtxnotsuccessful);
            }
        });
    }


    /**
     * get location cameraPosition
     * @param lat
     * @param lan
     */
    public void location_setting(double lat, double lan) {
        viewModellIsSupportedLocation.getretrofit(lat, lan);

    }

    public boolean checkPermission() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGpsEnabled = false;
            ArabicTranslation();
            booleanDeni = false;
                getdialogFragment(isSupported, country);
            Toast.makeText(this, R.string.openGPS, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            mGpsEnabled = true;
            return true;
        }
    }


    /**
     * Show the dialog fragment supported
     * @param isSupported
     * @param country
     */
    public void getdialogFragment(boolean isSupported, String country) {

        dialogFragment = new DialogIsSupported();
        dialogFragment.show(getSupportFragmentManager(), null);
        viewModellIsSupportedLocation.setCountry(country);
        viewModellIsSupportedLocation.setIsSupported(isSupported);
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(() -> {


            Geocoder geocoder = new Geocoder(MapsActivity.this);
            cameraPosition = mMap.getCameraPosition();
            ArabicTranslation();

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
                String addressTitle;
                if (address2.getSubLocality() != null) {
                    addressTitle = address2.getSubLocality();
                } else {

                    if (address2.getLocality() != null)

                        addressTitle = address2.getLocality();

                    else {
                        String[] titel = address2.getAddressLine(0).split(",");
                        if (titel.length > 1) {

                            addressTitle = (address2.getAddressLine(0).split(","))[1];
                        } else
                            addressTitle = address2.getAddressLine(0);


                    }

                }

                supportwdLocationDetails = new SupportwdLocationDetails(address2.getSubLocality(), address2.getAddressLine(0), false, cameraPosition.target.latitude, cameraPosition.target.longitude, 4, "other");
                supportwdLocationDetails.setAddressTitle(addressTitle);
                location_setting(cameraPosition.target.latitude, cameraPosition.target.longitude);
                txsearch.setText(address2.getAddressLine(0));
                autocompleteFragment.setText("");
                country = address2.getAddressLine(0);

            }
            viewModellIsSupportedLocation.setSupportwdLocationDetails(supportwdLocationDetails);


        });



    }



}