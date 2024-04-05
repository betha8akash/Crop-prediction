package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Blogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_blogs);
    }
    public void web1(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://krishijagran.com/"));
        startActivity(i);
    }
    public void web2(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mykisandost.com"));
        startActivity(i);
    }
    public void web3(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.agrifarming.in"));
        startActivity(i);
    }
    public void web4(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://https://www.roysfarm.com"));
        startActivity(i);
    }
}