package com.example.easynurse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AllDetailsAboutJob extends AppCompatActivity {
    TextView illness;
    TextView description;
    TextView phone;
    TextView address;

    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details_about_job);

        illness = findViewById(R.id.illness);
        description = findViewById(R.id.description);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);


        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getInstance().getReference("Job Posts");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String phoneNo=snapshot.child("phone").getValue(String.class).toString();
                phone.setText(phoneNo);

                String illnessstr = snapshot.child("illness").getValue(String.class).toString();
                illness.setText(illnessstr);

                String descriptionstr = snapshot.child("description").getValue(String.class).toString();
                description.setText(descriptionstr);
                String addressstr = snapshot.child("address").getValue(String.class).toString();
                address.setText(addressstr);




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}