package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Udetails extends AppCompatActivity {

    EditText period,crop_type,production,region,area;
    Button add;
    AGdatabase mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_udetails);

        period = (EditText) findViewById(R.id.editText7);
        crop_type = (EditText) findViewById(R.id.editText8);
        production = (EditText) findViewById(R.id.editText9);
        region = (EditText) findViewById(R.id.editText10);
        area = (EditText) findViewById(R.id.editText111);
        add = findViewById(R.id.btnadd);

        mDatabaseHelper = new AGdatabase(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String periodEntry = period.getText().toString();
                String crop_typeEntry = crop_type.getText().toString();
                String productionEntry = production.getText().toString();
                String regionEntry = region.getText().toString();
                String areaEntry = area.getText().toString();

                if (periodEntry.length() != 0 && crop_typeEntry.length() != 0 && productionEntry.length() != 0 && regionEntry.length() != 0 && areaEntry.length() != 0){
                    AddData(periodEntry,crop_typeEntry,productionEntry,regionEntry,areaEntry);
                    period.setText("");
                    crop_type.setText("");
                    production.setText("");
                    region.setText("");
                    area.setText("");

                    Intent i = new Intent(Udetails.this, Agropanel.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Udetails.this,"Plz enter the details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(String periodEntry,String crop_typeEntry,String productionEntry,String regionEntry,String areaEntry){
        boolean insertData = mDatabaseHelper.addData(periodEntry,crop_typeEntry,productionEntry,regionEntry,areaEntry);
        if (insertData == true){
            Toast.makeText(Udetails.this,"Details added successfully",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Udetails.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
}