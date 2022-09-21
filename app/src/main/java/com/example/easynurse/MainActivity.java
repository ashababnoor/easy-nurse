package com.example.easynurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_goto_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_goto_signup = (Button) findViewById(R.id.btn_goto_signup);

        btn_goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeToSignup = new Intent(MainActivity.this, RegisterUser.class);
                MainActivity.this.startActivity(changeToSignup);
            }
        });
    }

    public void radioButtonHandler(View view) {
    }
}