package com.example.easynurse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Array of titles
    String[] appTitleArray = {"Appointment 1", "Appointment 2", "Appointment 3", "Appointment 4", "Appointment 5", "Appointment 6", "Appointment 7", "Appointment 8", "Appointment 9"};


    ListView titleList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.appointment_list_layout, R.id.card_text, appTitleArray);
        titleList = findViewById(R.id.job_title_list);
        titleList.setAdapter(adapter);





    }
}