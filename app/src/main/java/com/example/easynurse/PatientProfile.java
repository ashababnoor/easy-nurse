package com.example.easynurse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class PatientProfile<FirebaseUser> extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;


//    private String userID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
    }
}