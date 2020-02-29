package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.example.myapplication.R.layout.activity_login;

public class Registration extends Fragment {


    private EditText username, pass, confirmPass;
    Button register;
    ProgressBar progressBar;

    FirebaseAuth fAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(activity_login, container, false);

        username = (EditText) rootView.findViewById(R.id.usernameField);
        pass = (EditText) rootView.findViewById(R.id.passwordField);
        confirmPass = (EditText) rootView.findViewById(R.id.confirmPasswordField);
        register = rootView.findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        progressBar = rootView.findViewById(R.id.progressRegBar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (TextUtils.isEmpty(userName)){
                    username.setError("Username is required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    pass.setError("Password is required");
                    return;
                }

                if (password.length() < 6){
                    pass.setError("Password must be equal to or greater than 6 chars");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(getContext(), Login.class));
                        } else  {
                            Toast.makeText(getActivity(), "Error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return rootView;
    }
}
