package com.example.farmwise;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        String[] topThreeCropNames = getIntent().getStringArrayExtra("topThreeCropNames");
        setCropDetails(topThreeCropNames);
    }

    public void setCropDetails(String[] cropNames) {
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        if (cropNames.length > 0) {
            textView1.setText(cropNames[0]);
            setImageForCrop(imageView1, cropNames[0]);
        }
        if (cropNames.length > 1) {
            textView2.setText(cropNames[1]);
            setImageForCrop(imageView2, cropNames[1]);
        }
        if (cropNames.length > 2) {
            textView3.setText(cropNames[2]);
            setImageForCrop(imageView3, cropNames[2]);
        }
    }

    private void setImageForCrop(ImageView imageView, String cropName) {
        // Determine the image resource ID based on the crop name
        switch (cropName) {
            case "apple":
                imageView.setImageResource(R.drawable.apple);
                break;
            case "banana":
                imageView.setImageResource(R.drawable.banana);
                break;
            // Add cases for other crop types as needed
            default:
                // Use a default image if the crop name doesn't match any known crops
                imageView.setImageResource(R.drawable.cloudy);
                break;
        }
    }
}