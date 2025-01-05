package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fullNameText, emailText, passwordText, phoneText, confirmPassText;
    Button registerButton;
    TextView loginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameText = findViewById(R.id.fullName);
        emailText = findViewById(R.id.Email);
        passwordText = findViewById(R.id.password);
        confirmPassText = findViewById(R.id.confirmpassword);
        phoneText = findViewById(R.id.phone);
        registerButton = findViewById(R.id.registerBtn);
        loginButton = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String fullName = fullNameText.getText().toString();
                String phone = phoneText.getText().toString();
                String confirmPassword = confirmPassText.getText().toString();

                if (fullName.equals("")) {
                    //Log.d("T1", "reached");
                    fullNameText.setError("Name Required");
                    fullNameText.requestFocus();
                    return;
                } else if (phone.equals("")) {
                    phoneText.setError("Phone Number Required");
                    phoneText.requestFocus();
                    return;
                } else if (!digits(phone)) {
                    phoneText.setError("Valid Phone Number Needed");
                    phoneText.requestFocus();
                    return;
                } else if (email.equals("")) {
                    emailText.setError("Email Required");
                    emailText.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailText.setError("Valid Email Needed");
                    emailText.requestFocus();
                    return;
                } else if (password.equals("")) {
                    passwordText.setError("Password Required");
                    passwordText.requestFocus();
                    return;
                } else if (password.length() < 6) {
                    passwordText.setError("Password Must Be Atleast 6 Characters Long");
                    passwordText.requestFocus();
                    return;
                } else if (confirmPassword.equals("")) {
                    confirmPassText.setError("Confirm Password Required");
                    confirmPassText.requestFocus();
                    return;
                } else if (!password.equals(confirmPassword)) {
                    confirmPassText.setError("Password Does Not Match");
                    confirmPassText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            //DocumentReference documentReference = fStore.collection("users").document(userID).set(user);
                            fStore.collection("users").document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Log.d("TAGG", "onSuccess: user Profile is created for " +userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        } else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public static boolean digits(String str) {
        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
