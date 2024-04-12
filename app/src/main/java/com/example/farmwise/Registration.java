package com.example.farmwise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Registration extends AppCompatActivity {

    EditText name,mob,adharno,place,pin,pass;
    Button register;
    AGdatabase mDatabaseHelper;

    private static final int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location updates
        getLastLocation();

        name = (EditText) findViewById(R.id.editText1);
        mob = (EditText) findViewById(R.id.editText2);
        adharno = (EditText) findViewById(R.id.editText3);
        place = (EditText) findViewById(R.id.editText4);
        pin = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        register = findViewById(R.id.btnreg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users");

                String nameEntry = name.getText().toString();
                String mobEntry = mob.getText().toString();
                String adharnoEntry = adharno.getText().toString();
                String placeEntry = place.getText().toString();
                String pinEntry = pin.getText().toString();
                String passEntry = pass.getText().toString();

                HelperClass helperclass = new HelperClass(nameEntry,mobEntry,adharnoEntry,placeEntry,pinEntry,passEntry);
                reference.child(nameEntry).setValue(helperclass);

                Toast.makeText(Registration.this,"Signed up successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);

            }
        });

    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        getAddressFromLocation(location);
                    } else {
                        requestNewLocationData();
                    }
                }
            });
        } else {
            requestPermissions();
        }
    }
    private void getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                String postalCode = addresses.get(0).getPostalCode();

                // Set the city to the cityTextView
                if (city != null) {
                    place.setText(city);
                } else {
                    place.setText("");
                }

                // Set the postal code to the pinTextView
                if (postalCode != null) {
                    pin.setText(postalCode);
                } else {
                    pin.setText("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        // Implement this method if you want to request new location data
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void AddData(String nameEntry,String mobEntry,String adharnoEntry,String placeEntry,String pinEntry,String passEntry){
        boolean insertData = mDatabaseHelper.addData(nameEntry,mobEntry,adharnoEntry,placeEntry,pinEntry,passEntry);
        if (insertData == true){
            Toast.makeText(Registration.this,"Registration successful ",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Registration.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
    //==AGROPANEL
    public void backtologin(View view) {
        Intent i = new Intent(Registration.this, Login.class);
        startActivity(i);
    }
}