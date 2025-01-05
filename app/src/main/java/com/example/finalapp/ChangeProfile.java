package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class ChangeProfile extends AppCompatActivity {

    EditText editName, editPhone, editEmail;
    Button update;
    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    FirebaseUser user;
    String email, phone, name;
    String afteremail, afterphone, aftername;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        editName = findViewById(R.id.profileName);
        editEmail = findViewById(R.id.profileEmail);
        editPhone = findViewById(R.id.profilePhone);
        update = findViewById(R.id.updateprofbutton);
        header = findViewById(R.id.header);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        YoYo.with(Techniques.Flash).repeat(YoYo.INFINITE).playOn(header);
        email = getIntent().getStringExtra("em");
        phone = getIntent().getStringExtra("ph");
        name = getIntent().getStringExtra("na");

        editPhone.setText(phone);
        editName.setText(name);
        editEmail.setText(email);
        afteremail = email;
        aftername = name;
        afterphone = phone;
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                aftername = s.toString();
            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                afteremail = s.toString();
            }
        });

        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                afterphone = s.toString();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.getText().toString().equals("")) {
                    Log.d("T1", "reached");
                    editName.setError("Name Required");
                    editName.requestFocus();
                    return;
                } else if (editEmail.getText().toString().equals("")) {
                    editEmail.setError("Email Required");
                    editEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()) {
                    editEmail.setError("Valid Email Needed");
                    editEmail.requestFocus();
                    return;
                } else if (editPhone.getText().toString().equals("")) {
                    editPhone.setError("Phone Number Required");
                    editPhone.requestFocus();
                    return;
                } else if (!digits(editPhone.getText().toString())) {
                    editPhone.setError("Valid Phone Number Needed");
                    editPhone.requestFocus();
                    return;
                }
                user.updateEmail(editEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        documentReference = firebaseFirestore.collection("users").document(userID);
                        Map<String, Object> changed = new HashMap<>();
                        changed.put("email", editEmail.getText().toString());
                        changed.put("fName", editName.getText().toString());
                        changed.put("phone", editPhone.getText().toString());
                        documentReference.update(changed).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(ChangeProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                                finish();
                            }
                        });
                        //documentReference.update("email", email);
                        //documentReference.update("fName", editName.getText().toString());
                        //documentReference.update("phone", editPhone.getText().toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangeProfile.this, "Error. " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                //Toast.makeText(profile.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(getApplicationContext(), MainActivity.class));

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