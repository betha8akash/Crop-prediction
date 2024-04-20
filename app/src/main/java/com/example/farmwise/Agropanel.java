package com.example.farmwise;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;

public class Agropanel extends AppCompatActivity {

    private LanguageHelper languageHelper;

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().show();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agropanel);
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, mOnBackPressedCallback);

        username = getIntent().getStringExtra("username");
        if (username == null) {
            // Handle the case where the username is not present in the intent extra
            // For example, you could show an error message and finish the activity
            Toast.makeText(this, "Username is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        languageHelper = new LanguageHelper(this);
        String selectedLanguage = languageHelper.getLanguage();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, Agropanel.class));
                break;
            case R.id.action_settings:
//                startActivity(new Intent(this, Login.class));
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("EXIT",true);
                startActivity(i);
                finish();
                System.exit(0);
                break;
            case R.id.Weather:
                startActivity(new Intent(this, Analysis.class));
                break;
            case R.id.Schemes:
                startActivity(new Intent(this, Schemes.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
    public void predict(View view) {
//        Intent i = new Intent(Agropanel.this, ScrollingActivity.class);
        Intent i = new Intent(Agropanel.this, Image.class);
        startActivity(i);
    }
    public void analise(View view) {
        Intent i = new Intent(Agropanel.this, Analysis.class);
        startActivity(i);
    }
    public void add(View view) {
        Intent i = new Intent(Agropanel.this, HarvestDetails.class);
        i.putExtra("username",username);
        startActivity(i);
    }
    public void details(View view) {
        Intent i = new Intent(Agropanel.this, ViewDetails.class);
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
    public void Language(View view){
        showLanguageDialog();
    }
    private void showLanguageDialog() {
        final String[] languages = {"English", "Telugu", "Hindi", "Tamil"}; // List of languages
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language")
                .setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked on a language
                        String selectedLanguage = languages[which];
                        // Change language based on selection
                        changeLanguage(selectedLanguage);
                    }
                })
                .setNegativeButton("Cancel", null); // Cancel button

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeLanguage(String language) {
        Locale newLocale;
        switch (language) {
            case "English":
                newLocale = new Locale("en");
                break;
            case "Telugu":
                newLocale = new Locale("te");
                break;
            case "Hindi":
                newLocale = new Locale("hi");
                break;
            case "Tamil":
                newLocale = new Locale("ta");
                break;
            default:
                newLocale = Locale.getDefault(); // Default to device language
        }

        Configuration config = getResources().getConfiguration();
        Locale currentLocale = config.locale;

        // Check if the new locale is different from the current locale
        if (!newLocale.equals(currentLocale)) {
            config.setLocale(newLocale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            // Restart the activity to apply changes
            recreate();
        }
    }

    public void logout(View view) {
        Intent i = new Intent(getApplicationContext(), Login.class);
    }
    private OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(Agropanel.this);
            builder.setMessage("Are you sure you want to leave the app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };
}