package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.example.myapplication.R.layout.activity_login;

public class Login extends Fragment {


    private EditText useName;
    private EditText password;
    private TextView register;
    Button loginb;
    private static final String FILE_Name = "Login.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;
    final static String TAG = Login.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(activity_login, container, false);


        Button loginb = rootView.findViewById(R.id.login);
        useName = (EditText) rootView.findViewById(R.id.usernameField);
        password = (EditText) rootView.findViewById(R.id.confirmPasswordField);
        register = (TextView) rootView.findViewById(R.id.registerTextview);

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Registration.class);
                startActivity(intent);
            }
        });

        loginb.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                try {
                    loginbtn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void loginbtn() throws IOException {

                // File file = new File("Login.txt");
                //  FileInputStream fstream = new FileInputStream("Login");
                InputStream inputStream = getContext().getAssets().open("login");
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String strLine;
                String userName;
                String password;
                if (inputStream != null) {
                    while ((strLine = br.readLine()) != null) {
                        // Print the content on the console
                        System.out.println ("wrong passowrd");
                        String[] namesList = strLine.split(",");

                        userName = namesList[0];
                        password = namesList[2];

                        System.out.println(userName);
                        System.out.println(password);
                        System.out.println(strLine);

                        if (userName.trim().contentEquals(useName.getText()) && !password.trim().contentEquals(Login.this.password.getText())) {

                            System.out.print("shuy up");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                            builder.setTitle("Confirmation?");
                            builder.setMessage("User or pass wrong");
                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();


                        } else if (password.trim().contentEquals(Login.this.password.getText()) && userName.trim().contentEquals(useName.getText())) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                            builder.setTitle("Confirmation?");
                            builder.setMessage("User or pass matched");
                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(getActivity(),AdminCategory.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();


                        }
                    }
                }inputStream.close();
            }
        });
        return rootView;
    }
}
