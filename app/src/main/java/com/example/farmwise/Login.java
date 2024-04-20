package com.example.farmwise;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText username,password;

    Button login,direct;
    AGdatabase mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.ed1);
        password = findViewById(R.id.ed2);
        login = findViewById(R.id.btnlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()){
                    
                } else{
                    checkUser();
                }
            }
        });

    }

    public Boolean validateUsername(){
        String val = username.getText().toString();
        if (val.isEmpty()){
            username.setError("Username cannot be empty");
            return false;
        }else{
            username.setError(null);
            return true;
        }
    }
    public void Language(View view){
        showLanguageDialog();
    }
    private void showLanguageDialog() {
        final String[] languages = {"English", "Telugu","Hindi","Tamil"}; // List of languages
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
    public Boolean validatePassword(){
        String val = password.getText().toString();
        if (val.isEmpty()){
            username.setError("Password cannot be empty");
            return false;
        }else{
            username.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String name = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(name);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    username.setError(null);
                    String passwordFromDB = snapshot.child(name).child("pass").getValue(String.class);
                    if(pass.equals(passwordFromDB)){
                        username.setError(null);
                        Intent intent = new Intent(Login.this,Agropanel.class);
                        intent.putExtra("username",name);
                        startActivity(intent);
                    } else {
                        password.setError("Invalid credentials");
                        password.requestFocus();
                    }
                } else {
                    username.setError("User does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void valid(String userEntry,String passwordEntry){
        boolean insertData = mDatabaseHelper.valid(userEntry,passwordEntry);
        if (insertData == true){
            Toast.makeText(Login.this,"Welcome 👌",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, Agropanel.class);
            startActivity(i);
        }else {
            Toast.makeText(Login.this,"You have not registered yet !!",Toast.LENGTH_SHORT).show();
        }
    }
    //==Registration
    public void reg(View view) {
        Intent i = new Intent(Login.this, Registration.class);
        startActivity(i);
    }
}