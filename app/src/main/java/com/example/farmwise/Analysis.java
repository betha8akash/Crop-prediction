package com.example.farmwise;

import static android.content.ContentValues.TAG;

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
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;

public class Analysis extends AppCompatActivity{


    FusedLocationProviderClient mFusedLocationClient;

    TextView cityAndStateTextView,result;

    ImageView imageView;

    double latitude,longitude;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);


        cityAndStateTextView = findViewById(R.id.location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        fetchWeatherData(latitude,longitude);
    }
    private Weather[] weatherData;

    private void fetchWeatherData(double latitude, double longitude) {
        String api = "3205087d3c48f27c0133a27d37a165d1";
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + api;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray forecastArray = response.getJSONArray("list");
                    List<Weather> weatherList = new ArrayList<>();
                    String currentDate = "";

                    // Iterate through each forecast object
                    for (int i = 0; i < forecastArray.length(); i++) {
                        JSONObject forecastObject = forecastArray.getJSONObject(i);
                        String date = forecastObject.getString("dt_txt").substring(0, 10); // Extract date part
                        // Check if it's a new date
                        if (!date.equals(currentDate)) {
                            currentDate = date;
                            // Extract necessary information
                            String time = forecastObject.getString("dt_txt").substring(11, 16); // get the time from the datetime string
                            JSONObject weatherObject = forecastObject.getJSONArray("weather").getJSONObject(0);
                            String description = weatherObject.getString("description");
                            JSONObject mainObject = forecastObject.getJSONObject("main");
                            double temperature = mainObject.getDouble("temp") - 273.15; // convert from Kelvin to Celsius
                            int humidity = mainObject.getInt("humidity");
                            JSONObject windObject = forecastObject.getJSONObject("wind");
                            double windSpeed = windObject.getDouble("speed");

                            // save the data in a Weather object
                            Weather weather = new Weather(date, time, description, temperature, humidity, windSpeed);
                            weatherList.add(weather);
                        }
                    }

                    // Convert list to array
                    weatherData = weatherList.toArray(new Weather[0]);

                    // Update UI here using the weatherData array
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI(weatherData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });

        // add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private void updateUI(Weather[] weatherData) {
        Weather dayWeather = weatherData[0];

        // Get references to the TextViews and ImageView for the current day
        TextView dateTextView = findViewById(R.id.date_day1);
        TextView humidityTextView = findViewById(R.id.humidity_day1);
        TextView windTextView = findViewById(R.id.wind_day1);
        TextView temperatureTextView = findViewById(R.id.temperatureText_day1);
        TextView descriptionTextView = findViewById(R.id.description_day1);
        ImageView imageView = findViewById(R.id.day1_image);

        // Set values to TextViews for the current day
        dateTextView.setText(dayWeather.getDay() + ""); // Assuming dayWeather has a method called 'getDay'
        humidityTextView.setText("Humidity " + dayWeather.getHumidityValue() + "%"); // Assuming dayWeather has a method called 'getHumidityValue'

        // Convert wind speed from m/s to km/h
        int windSpeedKmH = (int) Math.round(dayWeather.getWindSpeedValue() * 3.6);

        windTextView.setText("Wind " + windSpeedKmH + " km/h"); // Assuming dayWeather has a method called 'getWindSpeedValue'

        // Convert temperature from Kelvin to Celsius
        int temperatureCelsius = (int) Math.round(dayWeather.getTemperatureValue());
        temperatureTextView.setText(Integer.toString(temperatureCelsius) + "°C"); // Assuming dayWeather has a method called 'getTemperatureValue'

        descriptionTextView.setText(dayWeather.getDescriptionValue() + "");
        Weather dayWeather1 = weatherData[1];

        // Get references to the TextViews and ImageView for the current day
        TextView dateTextView1 = findViewById(R.id.date_day2);
        TextView humidityTextView1 = findViewById(R.id.humidity_day2);
        TextView windTextView1 = findViewById(R.id.wind_day2);
        TextView temperatureTextView1 = findViewById(R.id.temperatureText_day2);
        TextView descriptionTextView1 = findViewById(R.id.description_day2);
        ImageView imageView1 = findViewById(R.id.day2_image);

        // Set values to TextViews for the current day
        dateTextView1.setText(dayWeather1.getDay() + ""); // Assuming dayWeather has a method called 'getDay'
        humidityTextView1.setText("Humidity " + dayWeather1.getHumidityValue() + "%"); // Assuming dayWeather has a method called 'getHumidityValue'

        // Convert wind speed from m/s to km/h
        int windSpeedKmH1 = (int) Math.round(dayWeather1.getWindSpeedValue() * 3.6);

        windTextView1.setText("Wind " + windSpeedKmH1 + " km/h"); // Assuming dayWeather has a method called 'getWindSpeedValue'

        // Convert temperature from Kelvin to Celsius
        int temperatureCelsius1 = (int) Math.round(dayWeather1.getTemperatureValue());
        temperatureTextView1.setText(Integer.toString(temperatureCelsius1) + "°C"); // Assuming dayWeather has a method called 'getTemperatureValue'

        descriptionTextView1.setText(dayWeather1.getDescriptionValue() + "");

        Weather dayWeather2 = weatherData[2];

        // Get references to the TextViews and ImageView for the current day
        TextView dateTextView2 = findViewById(R.id.date_day3);
        TextView humidityTextView2 = findViewById(R.id.humidity_day3);
        TextView windTextView2 = findViewById(R.id.wind_day3);
        TextView temperatureTextView2 = findViewById(R.id.temperatureText_day3);
        TextView descriptionTextView2 = findViewById(R.id.description_day3);
        ImageView imageView2 = findViewById(R.id.day3_image);

        // Set values to TextViews for the current day
        dateTextView2.setText(dayWeather2.getDay() + ""); // Assuming dayWeather has a method called 'getDay'
        humidityTextView2.setText("Humidity " + dayWeather2.getHumidityValue() + "%"); // Assuming dayWeather has a method called 'getHumidityValue'

        // Convert wind speed from m/s to km/h
        int windSpeedKmH2 = (int) Math.round(dayWeather2.getWindSpeedValue() * 3.6);

        windTextView2.setText("Wind " + windSpeedKmH2 + " km/h"); // Assuming dayWeather has a method called 'getWindSpeedValue'

        // Convert temperature from Kelvin to Celsius
        int temperatureCelsius2 = (int) Math.round(dayWeather2.getTemperatureValue());
        temperatureTextView2.setText(Integer.toString(temperatureCelsius2) + "°C"); // Assuming dayWeather has a method called 'getTemperatureValue'

        descriptionTextView2.setText(dayWeather2.getDescriptionValue() + "");

        Weather dayWeather3 = weatherData[3];

        // Get references to the TextViews and ImageView for the current day
        TextView dateTextView3 = findViewById(R.id.date_day4);
        TextView humidityTextView3 = findViewById(R.id.humidity_day4);
        TextView windTextView3 = findViewById(R.id.wind_day4);
        TextView temperatureTextView3 = findViewById(R.id.temperatureText_day4);
        TextView descriptionTextView3 = findViewById(R.id.description_day4);
        ImageView imageView3 = findViewById(R.id.day4_image);

        // Set values to TextViews for the current day
        dateTextView3.setText(dayWeather3.getDay() + ""); // Assuming dayWeather has a method called 'getDay'
        humidityTextView3.setText("Humidity " + dayWeather3.getHumidityValue() + "%"); // Assuming dayWeather has a method called 'getHumidityValue'

        // Convert wind speed from m/s to km/h
        int windSpeedKmH3 = (int) Math.round(dayWeather3.getWindSpeedValue() * 3.6);

        windTextView3.setText("Wind " + windSpeedKmH3 + " km/h"); // Assuming dayWeather has a method called 'getWindSpeedValue'

        // Convert temperature from Kelvin to Celsius
        int temperatureCelsius3 = (int) Math.round(dayWeather3.getTemperatureValue());
        temperatureTextView3.setText(Integer.toString(temperatureCelsius3) + "°C"); // Assuming dayWeather has a method called 'getTemperatureValue'

        descriptionTextView3.setText(dayWeather3.getDescriptionValue() + "");

        Weather dayWeather4 = weatherData[4];

        // Get references to the TextViews and ImageView for the current day
        TextView dateTextView4 = findViewById(R.id.date_day5);
        TextView humidityTextView4 = findViewById(R.id.humidity_day5);
        TextView windTextView4 = findViewById(R.id.wind_day5);
        TextView temperatureTextView4 = findViewById(R.id.temperatureText_day5);
        TextView descriptionTextView4 = findViewById(R.id.description_day5);
        ImageView imageView4 = findViewById(R.id.day5_image);

        // Set values to TextViews for the current day
        dateTextView4.setText(dayWeather4.getDay() + ""); // Assuming dayWeather has a method called 'getDay'
        humidityTextView4.setText("Humidity " + dayWeather4.getHumidityValue() + "%"); // Assuming dayWeather has a method called 'getHumidityValue'

        // Convert wind speed from m/s to km/h
        int windSpeedKmH4 = (int) Math.round(dayWeather4.getWindSpeedValue() * 3.6);

        windTextView4.setText("Wind " + windSpeedKmH4 + " km/h"); // Assuming dayWeather has a method called 'getWindSpeedValue'

        // Convert temperature from Kelvin to Celsius
        int temperatureCelsius4 = (int) Math.round(dayWeather4.getTemperatureValue());
        temperatureTextView4.setText(Integer.toString(temperatureCelsius4) + "°C"); // Assuming dayWeather has a method called 'getTemperatureValue'

        descriptionTextView4.setText(dayWeather4.getDescriptionValue() + "");

        setImageForWeatherDescription(imageView, dayWeather.getDescriptionValue());
        setImageForWeatherDescription(imageView1, dayWeather1.getDescriptionValue());
        setImageForWeatherDescription(imageView2, dayWeather2.getDescriptionValue());
        setImageForWeatherDescription(imageView3, dayWeather3.getDescriptionValue());
        setImageForWeatherDescription(imageView4, dayWeather4.getDescriptionValue());

    }
    private void setImageForWeatherDescription(ImageView imageView, String weatherDescription) {
        // Get current hour
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // Modify resource suffix based on current hour
        String resourceSuffix = (currentHour >= 19 || currentHour < 6) ? "_n" : "_d";

        if (weatherDescription != null) {
            if (weatherDescription.toLowerCase().contains("rain")) {
                imageView.setImageResource(getResourceId("rain", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("sky")) {
                imageView.setImageResource(getResourceId("clearsky", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("thunderstorm")) {
                imageView.setImageResource(getResourceId("thunderstorm", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("few clouds")) {
                imageView.setImageResource(getResourceId("fewclouds", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("broken clouds")) {
                imageView.setImageResource(getResourceId("brokenclouds", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("scattered clouds")) {
                imageView.setImageResource(getResourceId("scatteredclouds", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("mist")) {
                imageView.setImageResource(getResourceId("mist", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("snow")) {
                imageView.setImageResource(getResourceId("snow", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("drizzle")) {
                imageView.setImageResource(getResourceId("showerrain", resourceSuffix));
            } else if (weatherDescription.toLowerCase().contains("sleet")) {
                imageView.setImageResource(getResourceId("snow", resourceSuffix));
            } else {
                imageView.setImageResource(getResourceId("clearsky", resourceSuffix));
            }
        }
    }

    // Helper method to get resource id dynamically
    private int getResourceId(String resourceName, String suffix) {
        return getResources().getIdentifier(resourceName + suffix, "drawable", getPackageName());
    }

    //    private Weather[] weatherData;
//    private void fetchWeatherData(double latitude, double longitude) {
//        String api="3205087d3c48f27c0133a27d37a165d1";
//        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + api;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                String s_response = response.toString();
//                try {
//                    JSONArray forecastArray = response.getJSONArray("list");
//                    weatherData = new Weather[forecastArray.length()];
//                    for (int i = 0; i < forecastArray.length(); i++) {
//                        JSONObject forecastObject = forecastArray.getJSONObject(i);
//                        String date = forecastObject.getString("dt_txt");
//                        String time = date.substring(11, 16); // get the time from the datetime string
//                        JSONObject weatherObject = forecastObject.getJSONArray("weather").getJSONObject(0);
//                        String description = weatherObject.getString("description");
//                        JSONObject mainObject = forecastObject.getJSONObject("main");
//                        double temperature = mainObject.getDouble("temp") - 273.15; // convert from Kelvin to Celsius
//                        int humidity = mainObject.getInt("humidity");
//
//                        // save the data in a Weather object
//                        Weather weather = new Weather(date, time, description, temperature, humidity);
//                        weatherData[i] = weather;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "onErrorResponse: ", error);
//            }
//        });
//
//        // add the request to the RequestQueue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonObjectRequest);
//    }
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
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            getAddressFromLocation(location);
                        }
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

    private void getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                CityAndState address = new CityAndState();
                address.city = addresses.get(0).getLocality();
                address.state = addresses.get(0).getAdminArea();

                // Update the UI
                cityAndStateTextView.setText(address.getFullAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
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
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
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

    private class CityAndState {
        public String city;
        public String state;

        public String getFullAddress() {
            return city + ", " + state;
        }
    }
}