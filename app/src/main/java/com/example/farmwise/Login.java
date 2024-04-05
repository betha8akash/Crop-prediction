package com.example.farmwise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        mDatabaseHelper = new AGdatabase(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String userEntry = username.getText().toString();
//                String passwordEntry = password.getText().toString();
//
//                if (userEntry.length() != 0 && passwordEntry.length() != 0){
//                    valid(userEntry,passwordEntry);
//                    username.setText("");
//                    password.setText("");
//                }else {
//                    Toast.makeText(Login.this,"Plz enter the details",Toast.LENGTH_SHORT).show();
//                }
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