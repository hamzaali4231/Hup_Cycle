package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    private Button login;
    private static final String FILE_Name="LogIn.txt";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(activity_login,container,false);


        final Button loginb = (Button) findViewById(R.id.login);
        useName = (EditText) findViewById(R.id.editText2);
        Password = (EditText) findViewById(R.id.editText3);
        loginb.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          System.out.println("fewvfsdbjkfh");
                                          try {
                                              loginbtn();
                                          } catch (IOException e) {
                                              e.printStackTrace();
                                          }
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
               final AlertDialog.Builder builder = new AlertDialog.Builder(com.example.myapplication.login.this);
               builder.setTitle("Confirmation?");
               builder.setMessage("User or pass wrong");
               builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               AlertDialog dialog= builder.create();
               dialog.show();


        }
            else if(password.trim().equals(Password.getText()) && userName.trim().equals(useName.getText()))
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(com.example.myapplication.login.this);
                builder.setTitle("Confirmation?");
                builder.setMessage("User or pass matched");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();



            }
}
}
});
}
}