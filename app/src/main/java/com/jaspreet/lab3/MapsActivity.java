package com.jaspreet.lab3;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.jaspreet.lab2.DatabaseHelper;
import com.jaspreet.lab2.OrderWithDistance;
import com.jaspreet.firebaselogin.R;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   // private ActivityMapsBinding binding;
    DatabaseHelper databaseHelper;
    LatLng latLng;
    Button updateLocation, searchLocation, viewAsList;
    EditText searchBox;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    public static final int FAST_UPDATE_INTERVAL = 3;
    public static Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

   //     binding = ActivityMapsBinding.inflate(getLayoutInflater());
   //     setContentView(binding.getRoot());
        setContentView(R.layout.fragment_maps);
        updateLocation = findViewById(R.id.btn_update_location);
        searchLocation = findViewById(R.id.btn_search_location);
        viewAsList = findViewById(R.id.btn_view_list);
        searchBox = findViewById(R.id.search_box);
        databaseHelper = DatabaseHelper.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        updateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocationMarker();
            }
        });

        searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                searchBox.setText("");
                searchLocationMarker(query);
            }
        });

        viewAsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                searchBox.setText("");
                startViewAsListActivity(query);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseHelper.getAllData();

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Location loc = currentLocation;
        if(loc==null){
            showSnackBar("Location Unavailable, click on update location",true);
            return;
        }

        latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }

    public void updateLocationMarker() {
        mMap.clear();
        Location loc = currentLocation;
        if(loc==null || !isLocationEnabled()){
            showSnackBar("Location Unavailable, click on update location",true);
            return;
        }
        showSnackBar("Location Updated, shown with green pin",false);
        latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }

    public void searchLocationMarker(String query){
        if(!checkNetworkConnection()) {
            showSnackBar("No Network, please check network connection", true);
            return;
        }
        List<OrderWithDistance> orders = LoadData.getInstance().getOrders(databaseHelper.getAllData(),query, currentLocation);
        if(query.isEmpty())
            showSnackBar("Search Query is Empty, showing all results", true);
        else
            showSnackBar("Searching For " + query + ", showing top results", true);
        if(orders!=null && orders.size()>0)
            addMarkers(orders);
        else{
            showSnackBar("No Results found", true);
            mMap.clear();
        }

    }

    private void startViewAsListActivity(String query) {
        if(!checkNetworkConnection()) {
            showSnackBar("No Network, please check network connection", true);
            return;
        }
        List<OrderWithDistance> orders = LoadData.getInstance().getOrders(databaseHelper.getAllData(),query, currentLocation);
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void addMarkers(List<OrderWithDistance> orders) {
        mMap.clear();
        for (int i = 0; i<orders.size(); i++) {
            OrderWithDistance order = orders.get(i);
            Location loc = order.getOrder().getLocation();
            Long distance = Math.round((order.getDistance()*100))/100;
            String title = order.getOrder().getName()+ " , " + String.valueOf(distance) + " mi";
            if(i==0)
                mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(title));
            else
                mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        if (latLng != null) {
        //    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
       if (checkPermissions()) {
         if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        requestNewLocationData();
                        Location location = task.getResult();
                        //    currentLocation = location;
                    }
                });
            } else {
                showSnackBar("Please enable location and try again",true);
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        // mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            currentLocation = mLastLocation;
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    public void showSnackBar(String message, boolean error) {
        closeKeyboard(MapsActivity.this);
        View contextView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(contextView, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        if (!error)
            textView.setTextColor(Color.GREEN);
        else
            textView.setTextColor(Color.WHITE);

        snackbar.show();
    }

    public void closeKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }
}