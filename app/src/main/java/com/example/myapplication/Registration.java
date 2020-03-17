package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    EditText userName, userEmail, fPassword,confirmPassword;
    Button btnRegister;
    DatabaseReference databaseReference;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName=(EditText) findViewById(R.id.usernameField);
        userEmail = (EditText) findViewById(R.id.emailField);
        fPassword=(EditText) findViewById(R.id.passwordField);
        confirmPassword=(EditText) findViewById(R.id.confirmPasswordField);
        btnRegister=(Button) findViewById(R.id.registerButton);
        databaseReference= FirebaseDatabase.getInstance().getReference("User_Login");
        checkBox = (CheckBox) findViewById(R.id.showPassword);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    fPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void  registerUser(){
        final String username = userName.getText().toString().trim();
        final String email =userEmail.getText().toString().trim();
        final String password =fPassword.getText().toString().trim();
        final String confirmpassword =confirmPassword.getText().toString().trim();


        // Validation
        if(TextUtils.isEmpty(username)){
            userName.setError("Please enter the username");
        } else if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            userEmail.setError("Invalid Email address");
        } else if (TextUtils.isEmpty(email)){
            userEmail.setError("Please enter the email");
        } else if(TextUtils.isEmpty(password)){
            fPassword.setError("Please enter the password");
        } else if(!password.equals(confirmpassword)){
            confirmPassword.setError("Passwords do not match");
        } else{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(username).exists()){
                        Toast.makeText(Registration.this,"User already exists",Toast.LENGTH_LONG).show();
                    } else {

                        // Gets the database name reference and adds the data to fields
                        databaseReference.child(username).child("username").setValue(username);
                        databaseReference.child(username).child("email").setValue(email);
                        databaseReference.child(username).child("password").setValue(password);
                        Toast.makeText(Registration.this,"User added",Toast.LENGTH_LONG).show();
                        Cleartxt();
                        finish();
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
        userEmail.setText("");
        fPassword.setText("");
        confirmPassword.setText("");
    }
}

