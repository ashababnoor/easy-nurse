package com.example.easynurse;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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


public class nurse_home extends AppCompatActivity {
    //Array of titles


    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;
    ListView titleList;
    ArrayAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        firebaseDatabase = FirebaseDatabase.getInstance("https://easy-nurse-5c043-default-rtdb.firebaseio.com/");


        databaseReference = firebaseDatabase.getInstance().getReference("Job Posts/NCiArXmDLLhCXeb5Srz");


        titleList = findViewById(R.id.job_title_list);
        titleList.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(nurse_home.this, android.R.layout.simple_list_item_1, arrayList);
                titleList.setAdapter(arrayAdapter);
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