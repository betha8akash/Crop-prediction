package com.example.farmwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HarvestDetails extends AppCompatActivity {
    private EditText editText7, editText8, editText9, editText10, editText111;
    private Button btnAdd;
    private DatabaseReference mDatabase;

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_harvest_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        username = i.getStringExtra("username");
        if (username == null) {
            // Handle the case where the username is not present in the intent extra
            // For example, you could show an error message and finish the activity
            Toast.makeText(this, "Username is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to UI elements
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);
        editText9 = findViewById(R.id.editText9);
        editText10 = findViewById(R.id.editText10);
        editText111 = findViewById(R.id.editText111);
        btnAdd = findViewById(R.id.btnadd);

        // Add a click listener to the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered data from the EditText fields
                String monthAndYear = editText7.getText().toString();
                String cropName = editText8.getText().toString();
                String approxProduction = editText9.getText().toString();
                String region = editText10.getText().toString();
                String areaUnderCultivation = editText111.getText().toString();

                // Create a HashMap to store the data
                HashMap<String, String> data = new HashMap<>();
                data.put("monthAndYear", monthAndYear);
                data.put("cropName", cropName);
                data.put("approxProduction", approxProduction);
                data.put("region", region);
                data.put("areaUnderCultivation", areaUnderCultivation);

                // Send the data to Firebase
                mDatabase.child("users").child(username).child("harvest_details").push().setValue(data);
                Toast.makeText(HarvestDetails.this,"Details added successfully",Toast.LENGTH_SHORT).show();
                editText7.setText("");
                editText8.setText("");
                editText9.setText("");
                editText10.setText("");
                editText111.setText("");
            }
        });
    }
}