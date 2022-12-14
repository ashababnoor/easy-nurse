package com.example.easynurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button btn_goto_signup;
    private Button btn_login;
    private EditText et_login_email;
    private EditText et_login_pass;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference dbReference;
    private String userID;
    private String userName;
    private String userEmail;
    private String userDOB;
    private String userPhone;

    public static final String MyPREFERENCES = "MyPreferences";
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String DOB = "dobKey";

    private String userAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_pass = (EditText) findViewById(R.id.et_login_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_goto_signup = (Button) findViewById(R.id.btn_goto_signup);
        progressBar = (ProgressBar) findViewById(R.id.pb_login);

        btn_goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeToSignup = new Intent(MainActivity.this, RegisterUser.class);
                MainActivity.this.startActivity(changeToSignup);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_login_email.getText().toString().trim();
                String pass = et_login_pass.getText().toString().trim();

                if (email.isEmpty()){
                    et_login_email.setError("Email is required!");
                    et_login_email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    et_login_email.setError("Please provide a valid email!");
                    et_login_email.requestFocus();
                    return;
                }

                if (pass.isEmpty()){
                    et_login_pass.setError("Password is required");
                    et_login_pass.requestFocus();
                    return;
                }

                if (pass.length() < 8){
                    et_login_pass.setError("Password must be at least 8 characters!");
                    et_login_pass.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            dbReference = FirebaseDatabase.getInstance().getReference("Users");
                            userID = firebaseUser.getUid();

                            dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);

                                    if (user != null){
                                        userName = user.name;
                                        userEmail = user.email;
                                        userDOB = user.dob;
                                        userPhone = user.phone;
                                        userAccountType = user.accountType;
                                    }

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Name, userName);
                                    editor.putString(Phone, userPhone);
                                    editor.putString(Email, userEmail);
                                    editor.putString(DOB, userDOB);
                                    editor.apply();

                                    goToHomePage(userAccountType);

                                    String title = "EasyNurse";
                                    String message = "Dear " + userName + ", you are now successfully logged in!";

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                            MainActivity.this
                                    )
                                            .setSmallIcon(R.drawable.ic_baseline_message_24)
                                            .setContentTitle(title)
                                            .setContentText(message)
                                            .setAutoCancel(true);

                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("message", message);

                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(pendingIntent);

                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, builder.build());


                                    Toast.makeText(MainActivity.this, "Welcome " + userName +"!", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed to log in. Please check your email and password.", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void radioButtonHandler(View view) {
    }

    private void goToHomePage(String userAccountType){
        Intent changeToHomePage;
        switch (userAccountType) {
            case "Patient":
                changeToHomePage = new Intent(MainActivity.this, patient_home.class);
                MainActivity.this.startActivity(changeToHomePage);
                MainActivity.this.finish();
                break;
            case "Nurse":
                changeToHomePage = new Intent(MainActivity.this, nurse_home.class);
                MainActivity.this.startActivity(changeToHomePage);
                MainActivity.this.finish();
                break;
        }
    }

//    private void addNotification(String title, String message) {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
//                        .setContentTitle(title)
//                        .setContentText(message);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_IMMUTABLE);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
}
