package com.example.easynurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends ArrayAdapter<Jobs> {
    public JobListAdapter(@NonNull Context context, ArrayList<Jobs> jobs_for_nurse) {
        super(context, R.layout.job_item_details, jobs_for_nurse);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Jobs jobs = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_item_details, parent, false);
        }

        TextView jobTitle = convertView.findViewById(R.id.job_title);

        jobTitle.setText(jobs.jobTitle);

        return super.getView(position, convertView, parent);
    }
}

