package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    EditText name,mob,adharno,place,pin,pass;
    Button register;
    AGdatabase mDatabaseHelper;

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


        String url = "https://api.ipdata.co/?api-key=741dca17556e27ff1f82c4b6f6f183fc9a15ee5edb46809d9b07d273";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String city;
                int postal;
                try {
                    city = response.getString("city");
                    postal = response.getInt("postal");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                place.setText(city + "");
                pin.setText(postal+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            place.setText("");
            pin.setText("");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);

        name = (EditText) findViewById(R.id.editText1);
        mob = (EditText) findViewById(R.id.editText2);
        adharno = (EditText) findViewById(R.id.editText3);
        place = (EditText) findViewById(R.id.editText4);
        pin = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        register = findViewById(R.id.btnreg);

//        mDatabaseHelper = new AGdatabase(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String nameEntry = name.getText().toString();
//                String mobEntry = mob.getText().toString();
//                String adharnoEntry = adharno.getText().toString();
//                String placeEntry = place.getText().toString();
//                String pinEntry = pin.getText().toString();
//                String passEntry = pass.getText().toString();
//
//                if (nameEntry.length() != 0 && mobEntry.length() == 10 && adharnoEntry.length() == 12 && placeEntry.length() != 0 && pinEntry.length() == 6 && passEntry.length() != 6){
//                    AddData(nameEntry,mobEntry,adharnoEntry,placeEntry,pinEntry,passEntry);
//                    name.setText("");
//                    mob.setText("");
//                    adharno.setText("");
//                    place.setText("");
//                    pin.setText("");
//                    pass.setText("");
//                } else {
//                    Toast.makeText(Registration.this,"Plz enter valid credentials",Toast.LENGTH_SHORT).show();
//                }
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

    public void AddData(String nameEntry,String mobEntry,String adharnoEntry,String placeEntry,String pinEntry,String passEntry){
        boolean insertData = mDatabaseHelper.addData(nameEntry,mobEntry,adharnoEntry,placeEntry,pinEntry,passEntry);
        if (insertData == true){
            Toast.makeText(Registration.this,"Registration successful 👍",Toast.LENGTH_SHORT).show();
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