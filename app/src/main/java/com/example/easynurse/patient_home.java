package com.example.easynurse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class patient_home extends AppCompatActivity {
    //Array of titles


    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;
    ListView titleList;
    ArrayAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button addJobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getInstance().getReference("Job Posts");


        titleList = findViewById(R.id.job_title_list);
        titleList.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.child("phone").getValue(String.class).toString();
                arrayList.add(value);

                arrayAdapter = new ArrayAdapter<String>(patient_home.this, android.R.layout.simple_list_item_1, arrayList);
                titleList.setAdapter(arrayAdapter);
                Log.d("array", String.valueOf(arrayList));

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
        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 if (position == 0) {

                                                     startActivity(new Intent(patient_home.this, AllDetailsAboutJob.class));

                                                 }
                                             }
        });


            //Add Jobs Button Click
        addJobs = findViewById(R.id.addJobs);
        addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_home.this, JobPostingForm.class);
                startActivity(intent);
            }
        });






    }
}