package com.example.farmwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Prediction extends AppCompatActivity {

    Button yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        yes = findViewById(R.id.button1);
        no = findViewById(R.id.button2);

    }
    public void yes(View view){
        Intent i = new Intent(Prediction.this,ScrollingActivity.class);
        startActivity(i);
    }
    public void no(View view){
        Intent i = new Intent(Prediction.this,Image.class);
        startActivity(i);
    }
}