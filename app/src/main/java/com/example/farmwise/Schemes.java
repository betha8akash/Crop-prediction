package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Schemes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_schemes);
    }
    public void web5(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pmfby.gov.in"));
        startActivity(i);
    }
    public void web6(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pmkmy.gov.in"));
        startActivity(i);
    }
    public void web7(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pmkisan.gov.in"));
        startActivity(i);
    }
    public void web8(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.enam.gov.in/web/"));
        startActivity(i);
    }
}