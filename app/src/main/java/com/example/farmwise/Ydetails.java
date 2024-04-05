package com.example.farmwise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class Ydetails extends AppCompatActivity {

    RecyclerView recyclerView;
    AGdatabase dbopenhelper;
    List<ViewModel> viewModelList;
    ViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ydetails);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewModelList = new ArrayList<>();

        dbopenhelper = new AGdatabase(this);
        viewModelList = dbopenhelper.getalldata();

        adapter = new ViewAdapter(this,viewModelList);
        recyclerView.setAdapter(adapter);
    }
}