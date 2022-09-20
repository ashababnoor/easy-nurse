package com.example.easynurse;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class MainAdapter extends BaseAdapter{
    MainActivity mainActivity;
    String[] appointment_title;
    Animation animation;


    public MainAdapter(MainActivity mainActivity, String[] appointment_title) {
        this.mainActivity = mainActivity;
        this.appointment_title = appointment_title;
    }

//    random gradiant generator
    public static int getRandom(int max){
        return (int) (Math.random()*max);
    }

    @Override
    public int getCount() {
        return appointment_title.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mainActivity).inflate(R.layout.new_item_layout, viewGroup, false);
        animation = AnimationUtils.loadAnimation(mainActivity, R.anim.animation1);

        TextView textView;
        LinearLayout ll_bg;
        ll_bg = view.findViewById(R.id.ll_bg);
        textView = view.findViewById(R.id.textView);

        int number = getRandom(8);
        if(number == 1){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_1));
        }
        else if(number == 2){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_2));
        }
        else if(number == 3){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_3));
        }
        else if(number == 4){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_4));
        }
        else if(number == 5){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_5));
        }
        else if(number == 6){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_6));
        }
        else if(number == 7){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_7));
        }
        else if(number == 8){
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.gradient_8));
        }
        else{
            ll_bg.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.snow_bg));
        }

        textView.setText(appointment_title[i]);
        textView.setAnimation(animation);


        return view;
    }
}


