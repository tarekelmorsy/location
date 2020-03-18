package com.example.gpslocation.ui;
import android.Manifest;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.BasketLocation;
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
    public static ViewModell viewModell;
    private Button location;
    boolean mPremissionGranted = false;
    boolean mGpsEnabled = false;
    private View mapView;


    String supportwdLocation1, country;
    boolean issuppotted = false;
    boolean gg = false;
    boolean closedialog = true;
    boolean booleanPermission, l;
    boolean booleanDeni = true;
    public static boolean checkLocationSupport = false;
    DialogFragmenttt dialogFragment;
    Bundle bundle = new Bundle();



    static SupportwdLocationDetails supportwdLocationDetails = null;

    static  String MODE_PRIVATE="lll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iGps = (ImageView) findViewById(R.id.ic_gps);
        txsearch = findViewById(R.id.input_search);
        txsuccessful = findViewById(R.id.txSuccessful);
        gg = true;


        String languageToLoad = "ar";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        setupobserve();
        location = findViewById(R.id.location);
        initMap();
        setPlacee();
        Permission();

        iGps.setOnClickListener(v -> {

            booleanPermission = false;
            gg = false;
            booleanDeni = false;
            closedialog = false;
            Permission();

            getDeviceLocation();
        });
        location.setOnClickListener(v -> {


            if (issuppotted) {
                getdialogFragment(issuppotted, country, "نوصل لك عالموقع الحالي؟");
            } else {
                getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


            }


        });
        if (l) {
            if (issuppotted) {
                getdialogFragment(issuppotted, country, "نوصل لك عالموقع الحالي؟");
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


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                autocompleteFragment.a.extendSelection(0);
                placee = place;


                String languageToLoad = "ar";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
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

                                String languageToLoad = "ar";
                                Locale locale = new Locale(languageToLoad);
                                Locale.setDefault(locale);
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

                                if (gg || booleanPermission) {

                                    if (issuppotted) {
                                        getdialogFragment(issuppotted, country, "نوصل لك عالموقع الحالي؟");
                                    } else {
                                        getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


                                    }
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
                LatLng latLng = new LatLng(26.2561426, 50.1820928);
                if (booleanDeni) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

                Geocoder geocoder;
                String country1;
                String languageToLoad = "ar";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);

                List<Address> addresses = new ArrayList<>();
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0) {
                        country1 = addresses.get(0).getAddressLine(0);
                        Log.d("TAG", "onCameraMove/: " + country1);
                        // searchText.setText(country);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                country1 = addresses.get(0).getAddressLine(0);

                l = true;
                if (issuppotted || closedialog == false) {
                    //  getdialogFragment(issuppotted, supportwdLocation1, "نوصل لك عالموقع الحالي؟");
                } else {
                    getdialogFragment(issuppotted, country1, "للأسف،  مكانك برا التغطية");


                }

                // Toast.makeText(MapsActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    public String lll() {
        //Bundle bundle = new Bundle();
        MapsActivity.checkLocationSupport = true;
        final BasketLocation[] basketLocationn = {new BasketLocation()};

        MapsActivity.viewModell.getBasketMutableLiveData().observe(MapsActivity.this, new Observer<BasketLocation>() {
            @Override
            public void onChanged(BasketLocation basketLocation) {


                bundle.putString("userr", basketLocation.toString());

                basketLocationn[0] = basketLocation;
                Log.d("pppppppp", basketLocation.getMessage());

            }
        });

        return (bundle.getString("userr"));
    }

    public void setupobserve() {
        viewModell = new ViewModelProvider(this).get(ViewModell.class);
        viewModell.getMutableLiveDataSupportwdLocation().observe(this, new Observer<SupportwdLocation>() {
            @Override
            public void onChanged(SupportwdLocation supportwdLocation) {
                issuppotted = true;
                checkLocationSupport = false;

                txsuccessful.setText(supportwdLocation.getResult().getCity().getNameAr() + "," + supportwdLocation.getResult().getNameAr());
                // txsearch.setText(supportwdLocation.getResult().getName() + supportwdLocation.getResult().getCity().getName());
                location.setText(R.string.next);
                txsuccessful.setBackgroundResource(R.drawable.bgtxissuccessful);
                Log.d("tttttttt", supportwdLocation.getResult().getName());

            }
        });


        viewModell.getMutableLiveDataError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                txsuccessful.setText(errorResponse.getMessageAr());
                location.setText(R.string.bt_suggest_this_location);
                issuppotted = false;
                checkLocationSupport = false;

                txsuccessful.setBackgroundResource(R.drawable.bgtxnotsuccessful);
            }
        });
    }

    public void location_setting(double lat, double lan) {
        viewModell.getretrofit(lat, lan);
        // viewModell.getDetail(supportwdLocationDetails);

    }

    public boolean checkPermission() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGpsEnabled = false;
            //showGPSDisabledAlertToUser();
            String languageToLoad = "ar";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            booleanDeni = false;

            // LatLng latLng = new LatLng(26.2561426, 50.1820928);
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            if (issuppotted) {
                getdialogFragment(issuppotted, country, "نوصل لك عالموقع الحالي؟");
            } else {
                getdialogFragment(issuppotted, country, "للأسف،  مكانك برا التغطية");


            }


            Toast.makeText(this, "من فضلك قم بتفعيل GPS لنتمكن من تحديد موقعك ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            mGpsEnabled = true;
            return true;
        }
    }

    public void getdialogFragment(boolean b, String s, String s2) {

        dialogFragment = new DialogFragmenttt();
        dialogFragment.show(getSupportFragmentManager(), null);
        bundle.putString("country", s);
        //viewModell.setString(s2);

        bundle.putString("supported", s2);
        bundle.putBoolean("Status", b);


        dialogFragment.setArguments(bundle);
        // dialogFragment.dismiss();


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        String languageToLoad = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                cameraPosition = mMap.getCameraPosition();
                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
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

                   // viewModell.setBasketMutableLiveData(supportwdLocationDetails);


                    supportwdLocationDetails.setAddressTitle(addressTitle);
                    bundle.putString("ll", supportwdLocationDetails.toString());
                    if (checkLocationSupport) {


                    }
                    location_setting(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    txsearch.setText(address2.getAddressLine(0));
                    autocompleteFragment.setText("");

                    // supportwdLocation3 = address2.getFeatureName() + address2.getCountryName() + address2.getLocality();
                    country = address2.getAddressLine(0);

                }
                viewModell.setBasketMutableLiveData(supportwdLocationDetails);


            }

        });



    }
    public static SupportwdLocationDetails getSupportwdLocationDetails() {
        return supportwdLocationDetails;
    }

    public static void setSupportwdLocationDetails(SupportwdLocationDetails supportwdLocationDetails) {
        MapsActivity.supportwdLocationDetails = supportwdLocationDetails;
    }


}