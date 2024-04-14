package com.example.farmwise;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ResultActivity extends AppCompatActivity {
    private static final int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView locationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        String[] topThreeCropNames = getIntent().getStringArrayExtra("topThreeCrops");
        setCropDetails(topThreeCropNames);
        locationTextView = findViewById(R.id.locationTextView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location updates
        getLastLocation();
    }

    public void setCropDetails(String[] cropNames) {
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        if (cropNames.length > 0) {
            textView1.setText(cropNames[0]);
            setImageForCrop(imageView1, cropNames[0]);
        }
        if (cropNames.length > 1) {
            textView2.setText(cropNames[1]);
            setImageForCrop(imageView2, cropNames[1]);
        }
        if (cropNames.length > 2) {
            textView3.setText(cropNames[2]);
            setImageForCrop(imageView3, cropNames[2]);
        }
    }

    private void setImageForCrop(ImageView imageView, String cropName) {
        // Determine the image resource ID based on the crop name
        switch (cropName) {
            case "apple":
                imageView.setImageResource(R.drawable.apple);
                break;
            case "banana":
                imageView.setImageResource(R.drawable.banana);
                break;
            case "blackgram":
                imageView.setImageResource(R.drawable.blackgram);
                break;
            case "chickpea":
                imageView.setImageResource(R.drawable.chickpea);
                break;
            case "coconut":
                imageView.setImageResource(R.drawable.coconut);
                break;
            case "coffee":
                imageView.setImageResource(R.drawable.coffee);
                break;
            case "cotton":
                imageView.setImageResource(R.drawable.cotton);
                break;
            case "grapes":
                imageView.setImageResource(R.drawable.grapes);
                break;
            case "jute":
                imageView.setImageResource(R.drawable.jute);
                break;
            case "kidneybeans":
                imageView.setImageResource(R.drawable.kidneybeans);
                break;
            case "lentil":
                imageView.setImageResource(R.drawable.lentil);
                break;
            case "maize":
                imageView.setImageResource(R.drawable.maize);
                break;
            case "mango":
                imageView.setImageResource(R.drawable.mango);
                break;
            case "mothbeans":
                imageView.setImageResource(R.drawable.mothbeans);
                break;
            case "mungbean":
                imageView.setImageResource(R.drawable.mungbean);
                break;
            case "muskmelon":
                imageView.setImageResource(R.drawable.muskmelon);
                break;
            case "orange":
                imageView.setImageResource(R.drawable.orange);
                break;
            case "papaya":
                imageView.setImageResource(R.drawable.papaya);
                break;
            case "pigeonpeas":
                imageView.setImageResource(R.drawable.pigeonpeas);
                break;
            case "pomegranate":
                imageView.setImageResource(R.drawable.pomegranate);
                break;
            case "rice":
                imageView.setImageResource(R.drawable.rice);
                break;
            case "watermelon":
                imageView.setImageResource(R.drawable.watermelon);
                break;
            default:
                // Use a default image if the crop name doesn't match any known crops
                imageView.setImageResource(R.drawable.cloudy);
                break;
        }
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
                String address = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
                locationTextView.setText(address);
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
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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
}