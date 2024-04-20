package com.example.farmwise;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import com.android.volley.Request;
public class ScrollingActivity extends AppCompatActivity {

    private EditText editTextN, editTextP, editTextK, editTextTemperature, editTextHumidity, editTextPh, editTextRainfall;
    private TextView textViewResult;

    private Interpreter interpreter;

    private double lat,lon;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scrolling);
            try {
                interpreter = new Interpreter(loadModelFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            editTextN = findViewById(R.id.editTextN);
            editTextP = findViewById(R.id.editTextP);
            editTextK = findViewById(R.id.editTextK);
            editTextTemperature = findViewById(R.id.editTextTemperature);
            editTextHumidity = findViewById(R.id.editTextHumidity);
            editTextPh = findViewById(R.id.editTextPh);
            editTextRainfall = findViewById(R.id.editTextRainfall);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.ipdata.co/?api-key=741dca17556e27ff1f82c4b6f6f183fc9a15ee5edb46809d9b07d273";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        double lat = response.getDouble("latitude");
                        double lon = response.getDouble("longitude");
                        // Make the second API request inside the onResponse method of the first request
                        String API = "3205087d3c48f27c0133a27d37a165d1";
                        String url1 = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+API;

                        JsonObjectRequest weather = new JsonObjectRequest(url1, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                double temp, humidity;
                                try {
                                    JSONObject main = response.getJSONObject("main");
                                    temp = main.getInt("temp_max");
                                    temp = temp - 273.15 ;
                                    humidity = main.getInt("humidity");
                                    editTextHumidity.setText(String.valueOf(humidity));
                                    editTextTemperature.setText(String.valueOf(temp));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error Occurred",String.valueOf(error));
                            }
                        });

                        requestQueue.add(weather);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error occurred", String.valueOf(error));
                }
            });
            requestQueue.add(jsonObjectRequest);


            Button buttonPredict = findViewById(R.id.buttonPredict);
            Button buttonReset = findViewById(R.id.buttonReset);
            textViewResult = findViewById(R.id.textViewResult);




            buttonPredict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequestWithVolley();
                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetFields();
                }
            });
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("label")){
                String label = intent.getStringExtra("label");
                String n = getNValue(label);
                String p = getPValue(label);
                String k = getKValue(label);
                String ph = getPhValue(label);
                editTextK.setText(n+"");
                editTextP.setText(p+"");
                editTextN.setText(k+"");
                editTextPh.setText(ph+"");

            }else{
                editTextK.setText("");
                editTextP.setText("");
                editTextN.setText("");
                editTextPh.setText("");
            }
        }

    public void sendRequestWithVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Construct the URL with query parameters
        String url = "https://akashbetha.pythonanywhere.com/predict" +
                "?nitrogen=" + editTextN.getText().toString() +
                "&phosphorous=" + editTextP.getText().toString() +
                "&potassium=" + editTextK.getText().toString() +
                "&temperature=" + editTextTemperature.getText().toString() +
                "&humidity=" + editTextHumidity.getText().toString() +
                "&ph=" + editTextPh.getText().toString() +
                "&rainfall=" + editTextRainfall.getText().toString();

        // Create a JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the top_three_crops array from the response
                            JSONArray topThreeCropsArray = response.getJSONArray("top_three_crops");

                            // Convert JSONArray to a String array
                            String[] topThreeCrops = new String[topThreeCropsArray.length()];
                            for (int i = 0; i < topThreeCropsArray.length(); i++) {
                                topThreeCrops[i] = topThreeCropsArray.getString(i);
                            }

                            // Create an Intent to start the ResultActivity
                            Intent intent = new Intent(ScrollingActivity.this, ResultActivity.class);

                            // Add the topThreeCrops array as an extra to the Intent
                            intent.putExtra("topThreeCrops", topThreeCrops);

                            // Start the ResultActivity
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.e("Volley", "Error: " + error.getMessage());
                    }
                }
        );

        // Add the request to the RequestQueue
        queue.add(request);
    }


    private ByteBuffer loadModelFile() throws IOException {
        // Load the model file from assets
        ByteBuffer modelBuffer;
        Context context = getApplicationContext();
        try (InputStream inputStream = context.getAssets().open("crop_model.tflite")) {
            int size = inputStream.available();
            modelBuffer = ByteBuffer.allocateDirect(size);
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            modelBuffer.put(buffer);
        }
        return modelBuffer;
    }

    private int[] getTopThreePredictedClasses(float[] outputValues) {
        // Sort the output values and get the indices of the top three values
        int[] topThreeIndices = new int[3];
        float[] sortedValues = outputValues.clone();
        Arrays.sort(sortedValues);
        for (int i = 0; i < 3; i++) {
            float topValue = sortedValues[sortedValues.length - 1 - i];
            for (int j = 0; j < outputValues.length; j++) {
                if (outputValues[j] == topValue && !contains(topThreeIndices, j)) {
                    topThreeIndices[i] = j;
                    break;
                }
            }
        }
        return topThreeIndices;
    }

    private boolean contains(int[] array, int key) {
        for (int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    private String[] getTopThreeClassNames(int[] classIndices) {
        // Map class indices to class names based on your label list
        String[] classNames = {"apple", "banana", "blackgram", "chickpea", "coconut", "coffee", "cotton", "grapes", "jute", "kidneybeans", "lentil", "maize", "mango", "mothbeans", "mungbean", "muskmelon", "orange", "papaya", "pigeonpeas", "pomegranate", "rice", "watermelon"};
        String[] topThreeClassNames = new String[3];
        for (int i = 0; i < 3; i++) {
            topThreeClassNames[i] = classNames[classIndices[i]];
        }
        return topThreeClassNames;
    }

    private void makePrediction() {
        try {
            // Get input values
            float[] inputValues = getInputValues();

            // Run model inference
            float[][] outputValues = new float[1][22];

            interpreter.run(inputValues, outputValues);

            // Get the top three predicted classes
            int[] topThreeIndices = getTopThreePredictedClasses(outputValues[0]);
            String[] topThreeClassNames = getTopThreeClassNames(topThreeIndices);

            // Create a string array to hold the top three crop names
            String[] topThreeCropNames = new String[3];
            for (int i = 0; i < 3; i++) {
                topThreeCropNames[i] = topThreeClassNames[i];
            }

            // Create an Intent and put the string array as an extra
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("topThreeCropNames", topThreeCropNames);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    private void makePrediction() {
//        try {
//            // Get input values
//            float[] inputValues = getInputValues();
//
//            // Run model inference
//            float[][] outputValues = new float[1][22];
//
//            interpreter.run(inputValues, outputValues);
//
//            // Display the prediction
//            int predictedClass = getPredictedClass(outputValues[0]);
//            String resultText = "Predicted Crop: " + getClassName(predictedClass);
//            textViewResult.setText(resultText);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private float[] getInputValues() {
        // Get input values from EditText fields and convert to float array
        float[] inputValues = new float[7];
        inputValues[0] = Float.parseFloat(editTextN.getText().toString());
        inputValues[1] = Float.parseFloat(editTextP.getText().toString());
        inputValues[2] = Float.parseFloat(editTextK.getText().toString());
        inputValues[3] = Float.parseFloat(editTextTemperature.getText().toString());
        inputValues[4] = Float.parseFloat(editTextHumidity.getText().toString());
        inputValues[5] = Float.parseFloat(editTextPh.getText().toString());
        inputValues[6] = Float.parseFloat(editTextRainfall.getText().toString());
        return inputValues;
    }

    private int getPredictedClass(float[] outputValues) {
        // Find the index of the maximum value in the output array
        int maxIndex = 0;
        for (int i = 1; i < outputValues.length; i++) {
            if (outputValues[i] > outputValues[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private String getClassName(int classIndex) {
        // Map class index to class name based on your label list
        String[] classNames = {"apple", "banana", "blackgram", "chickpea", "coconut", "coffee", "cotton", "grapes", "jute", "kidneybeans", "lentil", "maize", "mango", "mothbeans", "mungbean", "muskmelon", "orange", "papaya", "pigeonpeas", "pomegranate", "rice", "watermelon"};
        return classNames[classIndex];
    }


    private void resetFields() {
        // Clear the values in all EditText fields
        editTextN.setText("");
        editTextP.setText("");
        editTextK.setText("");
        editTextTemperature.setText("");
        editTextHumidity.setText("");
        editTextPh.setText("");
        editTextRainfall.setText("");
        textViewResult.setText("");
    }
    private String getNValue(String soilType) {
        switch (soilType) {
            case "Alluvial":
                return "170";
            case "Black":
                return "150";
            case "Red":
                return "150";
            case "Clay":
                return "100";
            case "Laterite":
                return "100";
            case "Loamy":
                return "100";
            case "Sandy":
                return "100";
            default:
                return "0";
        }
    }
    private String getPValue(String soilType) {
        switch (soilType) {
            case "Alluvial":
                return "22.5";
            case "Black":
                return "22.5";
            case "Red":
                return "22.5";
            case "Clay":
                return "17.5";
            case "Laterite":
                return "17.5";
            case "Loamy":
                return "17.5";
            case "Sandy":
                return "17.5";
            default:
                return "0";
        }
    }
    private String getKValue(String soilType) {
        switch (soilType) {
            case "Alluvial":
                return "200";
            case "Black":
                return "200";
            case "Red":
                return "180";
            case "Clay":
                return "280";
            case "Laterite":
                return "250";
            case "Loamy":
                return "200";
            case "Sandy":
                return "200";
            default:
                return "0";
        }
    }
    private String getPhValue(String soilType) {
        switch (soilType) {
            case "Alluvial":
                return "7.25";
            case "Black":
                return "7.75";
            case "Red":
                return "6.5";
            case "Clay":
                return "6.75";
            case "Laterite":
                return "5.75";
            case "Loamy":
                return "6.75";
            case "Sandy":
                return "6.25";
            default:
                return "0";
        }
    }
}