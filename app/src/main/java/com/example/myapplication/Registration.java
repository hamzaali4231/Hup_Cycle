package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    //int userID = ThreadLocalRandom.current().nextInt();

    EditText userName, fPassword,confirmPassword;
    Button btnRegister;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName=(EditText) findViewById(R.id.usernameField);
        fPassword=(EditText) findViewById(R.id.passwordField);
        confirmPassword=(EditText) findViewById(R.id.confirmPasswordField);
        btnRegister=(Button) findViewById(R.id.registerButton);
        databaseReference= FirebaseDatabase.getInstance().getReference("User_Login");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void  registerUser(){
        final String username = userName.getText().toString().trim();
        final String password =fPassword.getText().toString().trim();
        String comfirmpassword =confirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            userName.setError("Please enter the username");
        }else if(TextUtils.isEmpty(password)){
            fPassword.setError("Please enter the password");
        }else if(!password.equals(comfirmpassword)){
            confirmPassword.setError("Passwords do not match");
        }else{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(username).exists()){
                        Toast.makeText(Registration.this,"User already exists",Toast.LENGTH_LONG).show();
                    } else {
//                        HashMap<String,Object> userMap= new HashMap<>();
//                        userMap.put("password",password);
//                        userMap.put("username",username);
                        //databaseReference.child(String.valueOf(userID + username.charAt(0) + username.charAt(username.length()-1))).updateChildren(userMap);

                        databaseReference.child(username).child("username").setValue(username);
                        databaseReference.child(username).child("password").setValue(password);
                        Toast.makeText(Registration.this,"User added",Toast.LENGTH_LONG).show();
                        Cleartxt();
                        Intent intphto =new Intent(getApplicationContext(),Login.class);
                        startActivity(intphto);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
    private void Cleartxt(){
        userName.setText("");
        fPassword.setText("");
        confirmPassword.setText("");
    }
}

