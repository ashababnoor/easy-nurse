package com.example.easynurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Array of titles
    String[] appTitleArray = {"Appointment 1", "Appointment 2", "Appointment 3", "Appointment 4", "Appointment 5", "Appointment 6", "Appointment 7", "Appointment 8", "Appointment 9"};


    ListView titleList;
    ArrayAdapter adapter;
    Button addJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.appointment_list_layout, R.id.card_text, appTitleArray);
        titleList = findViewById(R.id.job_title_list);
        titleList.setAdapter(adapter);





        //Add Jobs Button Click
        addJobs = findViewById(R.id.addJobs);
        addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JobPostingForm.class);
                startActivity(intent);
            }
        });






    }
}