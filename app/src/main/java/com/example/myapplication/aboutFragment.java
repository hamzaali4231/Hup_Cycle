package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class aboutFragment extends AppCompatActivity {

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.fragment_about);
    }
}
