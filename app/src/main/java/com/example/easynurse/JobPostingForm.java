package com.example.easynurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobPostingForm extends AppCompatActivity {
    EditText illness , description, address,phone;
    Button post;
    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;
    jobDetails jobdetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_form);
        illness =findViewById(R.id.illness);
        description =findViewById(R.id.description);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.extraPhone);
        post = findViewById(R.id.button);
        jobdetails =new jobDetails();

        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getInstance().getReference("Job Posts");



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Job Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        post.setOnClickListener(view->{
            postJob();
        });

    }

    private void postJob() {
        String illnessstr = illness.getText().toString();
        String descriptionstr = description.getText().toString();
        String addressstr = address.getText().toString();
        String phonestr = phone.getText().toString();
        jobdetails.setAddress(addressstr);
        jobdetails.setDescription(descriptionstr);
        jobdetails.setIllness(illnessstr);
        jobdetails.setPhone(phonestr);




        databaseReference.push().setValue(jobdetails);
        Toast.makeText(JobPostingForm.this, "data added", Toast.LENGTH_SHORT).show();


    }
}