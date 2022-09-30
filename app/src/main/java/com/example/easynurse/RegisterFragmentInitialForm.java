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

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public RegisterFragmentInitialForm() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RegisterFragmentInitialForm.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RegisterFragmentInitialForm newInstance(String param1, String param2) {
//        RegisterFragmentInitialForm fragment = new RegisterFragmentInitialForm();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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

//                progressBar.setVisibility(View.VISIBLE);
//                mAuth.createUserWithEmailAndPassword(email, pass)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    User user = new User(name, email, pass, dob, phone, finalGender);
//
//                                    FirebaseDatabase.getInstance().getReference("Users")
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()){
//                                                        Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();
//                                                        progressBar.setVisibility(View.GONE);
//                                                        registerViewToMainActivityView();
//                                                    }
//                                                    else {
//                                                        Toast.makeText(getActivity(), "Failed to register user.", Toast.LENGTH_LONG).show();
//                                                        progressBar.setVisibility(View.GONE);
//                                                    }
//                                                }
//                                            });
//                                }
//                            }
//                        });
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