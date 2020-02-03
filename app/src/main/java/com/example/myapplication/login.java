package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.myapplication.R.layout.activity_login;

public class login extends Fragment {


    private EditText useName;
    private EditText Password;
    private EditText Register;
    Button loginb;
    private static final String FILE_Name = "LogIn.txt";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(activity_login, container, false);

        Button loginb = rootView.findViewById(R.id.login);
        useName = (EditText) rootView.findViewById(R.id.editText2);
        Password = (EditText) rootView.findViewById(R.id.editText3);
        loginb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("fewvfsdbjkfhkjhjhjhjkh");

//                try {


                    Intent intent = new Intent(getActivity(),AdminCategory.class);
                    startActivity(intent);


//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }


            public void loginbtn() throws IOException {
                File file = new File("app/LogIn");
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;
                String userName;
                String password;
                while ((strLine = br.readLine()) != null) {
                    // Print the content on the console
                    //System.out.println (strLine);
                    String[] namesList = strLine.split(",");

                    userName = namesList[0];
                    password = namesList[2];
                    if (userName.trim().equals(useName.getText()) && !password.trim().equals(Password.getText())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Confirmation?");
                        builder.setMessage("User or pass wrong");
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


                    } else if (password.trim().equals(Password.getText()) && userName.trim().equals(useName.getText())) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Confirmation?");
                        builder.setMessage("User or pass matched");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();

                            }

                        }
                        );


                    }

                }
            }
        });
        return rootView;
    }
}