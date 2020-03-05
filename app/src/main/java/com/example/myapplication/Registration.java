package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    int userID = ThreadLocalRandom.current().nextInt();

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
                addArrayList();
                Intent intphto =new Intent(getApplicationContext(),Login.class);
                startActivity(intphto);

            }
        });
    }
    private void  addArrayList(){
        String username = userName.getText().toString().trim();
        String password =fPassword.getText().toString().trim();
        String comfirmpassword =confirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            userName.setError("Please enter your Username!");
        }else if(TextUtils.isEmpty(password)){
            fPassword.setError("Please enter your Password!");
        }else if(!password.equals(comfirmpassword)){
            confirmPassword.setError("Please put the same password");
        }else{

            //String id=  databaseReference.push().getKey();
           // User user = new User(username,pass,email);

            HashMap<String,Object> userMap= new HashMap<>();
            userMap.put("password",password);
            userMap.put("username",username);

            //databaseReference.child(String.valueOf(userID + username.charAt(0) + username.charAt(username.length()-1))).updateChildren(userMap);
            databaseReference.child(username).child("username").setValue(username);
            databaseReference.child(username).child("password").setValue(password);
            Toast.makeText(this,"User added",Toast.LENGTH_LONG).show();
            Cleartxt();

        }

    }
    private void Cleartxt(){
        userName.setText("");
        fPassword.setText("");
        confirmPassword.setText("");
    }
}
