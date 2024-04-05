package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Agropanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agropanel);
    }
    public void predict(View view) {
        Intent i = new Intent(Agropanel.this, ScrollingActivity.class);
        startActivity(i);
    }
    public void analise(View view) {
        Intent i = new Intent(Agropanel.this, Analysis.class);
        startActivity(i);
    }
    public void add(View view) {
        Intent i = new Intent(Agropanel.this, Udetails.class);
        startActivity(i);
    }
    public void details(View view) {
        Intent i = new Intent(Agropanel.this, Ydetails.class);
        startActivity(i);
    }
    public void blogs(View view) {
        Intent i = new Intent(Agropanel.this, Blogs.class);
        startActivity(i);
    }
    public void gov(View view) {
        Intent i = new Intent(Agropanel.this, Schemes.class);
        startActivity(i);
    }
    public void logout(View view) {
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("EXIT",true);
        startActivity(i);
        finish();
        System.exit(0);
    }
}