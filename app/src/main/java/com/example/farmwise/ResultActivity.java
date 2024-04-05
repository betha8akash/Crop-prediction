package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private TextView result, tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultText = getIntent().getStringExtra("resultText");

        TextView textViewResult = findViewById(R.id.result);

        TextView tv6 = findViewById(R.id.tv6);

        textViewResult.setText(resultText);

        String url = "https://api.ipdata.co/?api-key=741dca17556e27ff1f82c4b6f6f183fc9a15ee5edb46809d9b07d273";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String city;
                int postal;
                try {
                    city = response.getString("city");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                tv6.setText(city+" ");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Occurred",String.valueOf(error));
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);
    }
}