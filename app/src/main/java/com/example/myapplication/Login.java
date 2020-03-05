package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private TextView register;
    Button loginb;

    DatabaseReference logindatabaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginb = findViewById(R.id.login);
        userName = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.confirmPasswordField);
        register = (TextView) findViewById(R.id.registerTextview);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(userName.getText().toString(), password.getText().toString());
            }
        });
    }

    private void logIn(final String username,final String password) {
        logindatabaseReference=FirebaseDatabase.getInstance().getReference().child("User_Login");

        logindatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.child(username).getValue(User.class);
                if(dataSnapshot.child(username).exists()){

                    if (!username.isEmpty()){
                        if (user.getPassword().equals(password)){
                            Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
                            Intent intphto =new Intent(getApplicationContext(),AdminCategory.class);
                            startActivity(intphto);
                        }else {
                            Toast.makeText(Login.this,"Password is Incorrect",Toast.LENGTH_LONG).show();
                        }
                    }
//                    else {
//
//                        Toast.makeText(Login.this,"User is not registered",Toast.LENGTH_LONG).show();
//                    }


                }
                else {
//                    System.out.println("username is "+username);
//                    System.out.println("Password is "+password);
                    Toast.makeText(Login.this,"User is not registered",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

