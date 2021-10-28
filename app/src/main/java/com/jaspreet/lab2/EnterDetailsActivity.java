package com.jaspreet.lab2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jaspreet.firebaselogin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EnterDetailsActivity extends AppCompatActivity implements SubmitDialogListener , DatePickerDialog.OnDateSetListener {

    private static final String TAG = "Get Info Activity";

    TextView tv_get_info_heading, tvDate , tv_address;
    EditText et_enter_name, et_quantity, et_phone_number;
    Spinner dropDown;
    Button btn_reset, btn_submit, btPickDate , btn_update_location;
    String foodType;
    DatabaseHelper databaseHelper;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    public static final int FAST_UPDATE_INTERVAL = 3;
    int count;
    public static Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_details_layout);
        Log.d(TAG, "onCreate: Second Activity");

        tv_get_info_heading = findViewById(R.id.tv_get_info_heading);
        et_enter_name = findViewById(R.id.et_enter_name);
        et_quantity = findViewById(R.id.et_quantity);
        tvDate = findViewById(R.id.tvDate);
        btPickDate = findViewById(R.id.btPickDate);
        btn_reset = findViewById(R.id.btn_reset);
        btn_submit = findViewById(R.id.btn_submit);
        btn_update_location = findViewById(R.id.btn_update_location);
        et_phone_number = findViewById(R.id.et_phone_number);
        dropDown = findViewById(R.id.spinner1);
        tv_address = findViewById(R.id.tv_address);
        tvDate.setText(currentDate());
        setupDropDown();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        btn_update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentAddress();
            }
        });

        databaseHelper = new DatabaseHelper(EnterDetailsActivity.this);
        count = 0;
    }

    private void getCurrentAddress() {
     //   getLastLocation();
        if(currentLocation!=null){
            Geocoder geocoder = new Geocoder(EnterDetailsActivity.this);
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);
                tv_address.setText(addresses.get(0).getAddressLine(0));
            }
            catch (Exception e) {
                Toast.makeText(this,"Error Getting Current Location", Toast.LENGTH_SHORT).show();
            }
        } else{
            tv_address.setText("Address Unavailable");
        }
    }

    private void reset(){
        et_enter_name.setText("");
        et_quantity.setText("");
        et_phone_number.setText("");
        tvDate.setText(currentDate());
        et_enter_name.setHint("Enter Your Name");
        et_quantity.setHint("Enter Food Quantity");
        et_phone_number.setHint("Enter Phone Number");
    }

    private void submit(){
        try{
            DialogFragment submitDialog = new SubmitDialog();
            submitDialog.show(getSupportFragmentManager(),"submit");
        } catch (Exception e){
            Toast.makeText(EnterDetailsActivity.this,"Error while submission" , Toast.LENGTH_SHORT).show();
        }
    }

    private void setupDropDown(){
        String[] dishTypes = FoodType.names();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dishTypes);
        dropDown.setAdapter(adapter);
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                foodType = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                foodType = FoodType.OTHER.toString();
            }
        });
    }

    private String currentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
        return dt.format(calendar.getTime());
    }

    @Override
    public void onPositiveClick(DialogInterface dialogInterface) {
        String name = et_enter_name.getText().toString();

        String qty = et_quantity.getText().toString();
        int quantity = 0;
        if(qty!=null && qty!="")
            quantity= Integer.parseInt(qty);

        String phoneNumber = et_phone_number.getText().toString();
        if(!isValidPhoneNumber(phoneNumber))
            phoneNumber = "Not Available";

        String pickupDate = tvDate.getText().toString();

        String address = tv_address.getText().toString();

        ElementModel newElement;
        try{
            newElement = new ElementModel(-1, name, foodType, quantity, pickupDate,phoneNumber , address, currentLocation);
            databaseHelper.addOne(newElement);
            Toast.makeText(this, "Submission Successful",Toast.LENGTH_SHORT).show();
            count++;
            reset();
        }
        catch (Exception e){
            Toast.makeText(this, "Error while submitting",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeClick(DialogInterface dialog) {
        Toast.makeText(this,"Clicked on Cancelled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("count", String.valueOf(count));
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = dt.format(mCalendar.getTime());
        Calendar calendar = Calendar.getInstance();
        tvDate.setText(selectedDate);
    }

    private void setDate() {
        com.jaspreet.lab2.DatePicker mDatePickerDialogFragment;
        mDatePickerDialogFragment = new com.jaspreet.lab2.DatePicker();
        mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        requestNewLocationData();
                        Location location = task.getResult();
                    //    currentLocation = location;
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
       // mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            currentLocation = mLastLocation;
            getCurrentAddress();

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

}