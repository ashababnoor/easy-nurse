package com.example.easynurse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple { Fragment} subclass.
 * Use the { RegisterFragmentInitialForm#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RegisterFragmentInitialForm extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register_initial_form, container, false);

        banner = (TextView) rootView.findViewById(R.id.banner);
        et_register_name = (EditText) rootView.findViewById(R.id.et_register_name);
        et_register_email = (EditText) rootView.findViewById(R.id.et_register_email);
        et_register_pass = (EditText) rootView.findViewById(R.id.et_register_pass);
        et_register_dob = (EditText) rootView.findViewById(R.id.et_register_dob);
        et_register_phone = (EditText) rootView.findViewById(R.id.et_register_phone);
        register_radioGroup = (RadioGroup) rootView.findViewById(R.id.register_radioGroup);

        btn_signup = (Button) rootView.findViewById(R.id.btn_signup);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pb_register);

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

                if (dob.isEmpty()){
                    Toast.makeText(getActivity(), "Date of Birth is required!", Toast.LENGTH_SHORT).show();
                    et_register_dob.requestFocus();
                    return;
                }

                if (phone.isEmpty()){
                    et_register_phone.setError("Phone number is required!");
                    et_register_phone.requestFocus();
                    return;
                }
                RadioButton checkedButton;

                if (register_radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Gender is required!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int radioGroupSelectedId = register_radioGroup.getCheckedRadioButtonId();
                    checkedButton = (RadioButton) rootView.findViewById(radioGroupSelectedId);
                    gender = checkedButton.getText().toString().trim();
                }

                Log.d("RegisterFormInfo", "Name: "+ name + ", Email: " + email + ", Password: " + pass + ",  Phone: " + phone + ", DoB: " + dob + ", Gender: " + gender);

                String finalGender = gender;
                User user = new User(name, email, pass, dob, phone, finalGender);

                Fragment fragment = RegisterFragmentAccountTypeSelection.newInstance(user);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.register_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_goto_login = (Button) rootView.findViewById(R.id.btn_goto_login);
        btn_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewToMainActivityView();
            }
        });

        et_register_dob = rootView.findViewById(R.id.et_register_dob);
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
                new DatePickerDialog(getActivity() ,date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        et_register_dob.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void radioButtonHandler(View view) {

    }

    private void registerViewToMainActivityView(){
        Intent changeToLogin = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(changeToLogin);
        getActivity().finish();
    }

}