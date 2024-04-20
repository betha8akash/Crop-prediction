package com.example.farmwise;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDetails extends AppCompatActivity {
    private RecyclerView harvestDetailsRecyclerView;
    private HarvestDetailsAdapter adapter;
    private List<HarvestDetail> harvestDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        harvestDetailsRecyclerView = findViewById(R.id.harvest_details_recycler_view);
        harvestDetailsList = new ArrayList<>();

        // Retrieve harvest details from Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot harvestDetailSnapshot : userSnapshot.child("harvest_details").getChildren()) {
                        HarvestDetail harvestDetail = new HarvestDetail();
                        harvestDetail.setMonthAndYear(harvestDetailSnapshot.child("monthAndYear").getValue(String.class));
                        harvestDetail.setCropName(harvestDetailSnapshot.child("cropName").getValue(String.class));
                        harvestDetail.setApproxProduction(harvestDetailSnapshot.child("approxProduction").getValue(String.class));
                        harvestDetail.setRegion(harvestDetailSnapshot.child("region").getValue(String.class));
                        harvestDetail.setAreaUnderCultivation(harvestDetailSnapshot.child("areaUnderCultivation").getValue(String.class));
                        harvestDetailsList.add(harvestDetail);
                    }
                }

                // Set up the RecyclerView
                adapter = new HarvestDetailsAdapter(harvestDetailsList);
                harvestDetailsRecyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ViewDetails.this);
                harvestDetailsRecyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ViewDetails", "Error retrieving harvest details", databaseError.toException());
            }
        });
    }
}