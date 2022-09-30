package com.example.easynurse;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * A simple { Fragment} subclass.
 * Use the { RegisterFragmentAccountTypeSelection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragmentAccountTypeSelection extends Fragment {

    private FirebaseAuth mAuth;
    private Button btn_type_patient, btn_type_nurse, btn_goto_login;
    private TextView banner;
    private ProgressBar progressBar;

    private static final String USER_OBJECT = "user_object";
    private User mUser;

    View currentView;
    int desiredHeight;

    public RegisterFragmentAccountTypeSelection() {
        // Required empty public constructor
    }

    public static RegisterFragmentAccountTypeSelection newInstance(User user) {
        RegisterFragmentAccountTypeSelection fragment = new RegisterFragmentAccountTypeSelection();
        Bundle args = new Bundle();
        args.putParcelable(USER_OBJECT, (Parcelable) user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getParcelable(USER_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register_account_type_selection, container, false);
        desiredHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        currentView = rootView;


        Log.d("UserObject", "User object received from initial form fragment");
        Log.d("UserObject", "User --> name: "+mUser.name+", Email: " + mUser.email + ", Password: " + mUser.password + ",  Phone: " + mUser.phone + ", DoB: " + mUser.dob + ", Gender: " + mUser.gender);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) rootView.findViewById(R.id.banner);
        btn_type_nurse = (Button) rootView.findViewById(R.id.btn_type_nurse);
        btn_type_patient = (Button) rootView.findViewById(R.id.btn_type_patient);
        btn_goto_login = (Button) rootView.findViewById(R.id.btn_goto_login);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pb_select_account_type);

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewToMainActivityView();
            }
        });

        btn_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewToMainActivityView();
            }
        });

        btn_type_nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountType = btn_type_nurse.getText().toString().trim();
                mUser.setAccountType(accountType);
                registerUser(mUser);
            }
        });

        btn_type_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountType = btn_type_nurse.getText().toString().trim();
                mUser.setAccountType(accountType);
                registerUser(mUser);
            }
        });

        return rootView;
    }

    private void registerViewToMainActivityView(){
        Intent changeToLogin = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(changeToLogin);
        getActivity().finish();
    }

    private void registerUser(User user){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        registerViewToMainActivityView();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Failed to register user.", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                    }
                }
            });
    }
}