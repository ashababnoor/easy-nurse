package com.example.easynurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterUser extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView banner;
    private EditText et_register_name;
    private EditText et_register_email;
    private EditText et_register_pass;
    private EditText et_register_dob;
    private EditText et_register_phone;
    private RadioGroup register_radioGroup;
    private RadioButton register_gender;
    Button btn_signup, btn_goto_login;

    private ProgressBar progressBar;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        et_register_name = (EditText) findViewById(R.id.et_register_name);
        et_register_email = (EditText) findViewById(R.id.et_register_email);
        et_register_pass = (EditText) findViewById(R.id.et_register_pass);
        et_register_dob = (EditText) findViewById(R.id.et_register_dob);
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        register_radioGroup = (RadioGroup) findViewById(R.id.register_radioGroup);

        btn_signup = (Button) findViewById(R.id.btn_signup);

        progressBar = (ProgressBar) findViewById(R.id.pb_register);

        // int radioGroupSelectedId = register_radioGroup.getCheckedRadioButtonId();
        // register_gender = (RadioButton) findViewById(radioGroupSelectedId);

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewToMainActivityView();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_register_name.getText().toString().trim();
                String email = et_register_email.getText().toString().trim();
                String pass = et_register_pass.getText().toString().trim();
                String phone = et_register_phone.getText().toString().trim();
                String dob = et_register_dob.getText().toString().trim();
                String gender = "";

                if (name.isEmpty()){
                    et_register_name.setError("Full name is required!");
                    et_register_name.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    et_register_email.setError("Email is required!");
                    et_register_email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    et_register_email.setError("Please provide a valid email!");
                    et_register_email.requestFocus();
                    return;
                }

                if (pass.isEmpty()){
                    et_register_pass.setError("Password is required");
                    et_register_pass.requestFocus();
                    return;
                }

                if (pass.length() < 8){
                    et_register_pass.setError("Password must be at least 8 characters!");
                    et_register_pass.requestFocus();
                    return;
                }

                Log.d("RegisterFormInfo", "Name: "+ name + ", Email: " + email + ", Password: " + pass + ",  Phone: " + phone + ", DoB: " + dob);

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user = new User(name, email, pass, dob, phone, gender);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.VISIBLE);
                                                    }
                                                    else {
                                                        Toast.makeText(RegisterUser.this, "Failed to register user.", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });

        btn_goto_login = (Button) findViewById(R.id.btn_goto_login);
        btn_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewToMainActivityView();
            }
        });

        et_register_dob = findViewById(R.id.et_register_dob);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        et_register_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterUser.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        et_register_dob.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void radioButtonHandler(View view) {

    }

    private void registerViewToMainActivityView(){
        Intent changeToLogin = new Intent(RegisterUser.this, MainActivity.class);
        RegisterUser.this.startActivity(changeToLogin);
    }
}