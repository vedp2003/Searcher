package com.example.finalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfile extends AppCompatActivity {

    TextView fullName, email, phone, header;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resetPassword, changeProfile, deleteUser;
    FirebaseUser user;
    DocumentReference documentReference;
    String na, ph, em;
    Toolbar toolbar;
    ImageView imageView;
    CollectionReference collectionReference1;
    CollectionReference collectionReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageView = findViewById(R.id.profilepic);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        resetPassword = findViewById(R.id.resetPassword);
        changeProfile = findViewById(R.id.changeProfile);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        header = findViewById(R.id.textView4);
        deleteUser = findViewById(R.id.deleteUser);

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        collectionReference1 = fStore.collection("books").document("Users").collection(userId);
        collectionReference2 = fStore.collection("movies").document("Users").collection(userId);

        YoYo.with(Techniques.Flash).repeat(YoYo.INFINITE).playOn(header);
        RotateAnimation rotate1 = new RotateAnimation(-40, 40, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate1.setDuration(1);
        rotate1.setFillAfter(true);
        rotate1.setRepeatCount(1);
        rotate1.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(rotate1);

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popup = new AlertDialog.Builder(UserProfile.this);
                popup.setTitle("Are You Sure?");
                popup.setMessage("Deleting this account will result in completely removing your account from the system and all data from your account will be deleted.");
                popup.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                        Log.d("first", snapshot.getId());
                                        collectionReference1.document(snapshot.getId()).delete();
                                    }
                                }
                            }
                        });
                        collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                        Log.d("second", snapshot.getId());
                                        collectionReference2.document(snapshot.getId()).delete();
                                    }
                                }
                            }
                        });
                        fStore.collection("users").document(userId).delete();

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserProfile.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                }
                            }
                        });
                    }
                }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                popup.create().show();

            }
        });

        /*
        documentReference = fStore.collection("users").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    phone.setText("Phone: " +documentSnapshot.getString("phone"));
                    fullName.setText("Name: " +documentSnapshot.getString("fName"));
                    email.setText("Email: " +documentSnapshot.getString("email"));
                }else{
                    Log.d("Tagg", "Document does not exist");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Taggg", "Error. " + e.getMessage());
            }
        });

         */

        /*
        documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    phone.setText("Phone: " +value.getString("phone"));
                    fullName.setText("Name: " +value.getString("fName"));
                    email.setText("Email: " +value.getString("email"));
                } else {
                    Log.d("Tagg", "onEvent: DocumentSnapshot does not exist");

                }
            }
        });

         */

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeProfile.class);
                intent.putExtra("em", em);
                intent.putExtra("na", na);
                intent.putExtra("ph", ph);
                getApplicationContext().startActivity(intent);

            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPass = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetShower = new AlertDialog.Builder(v.getContext());
                passwordResetShower.setTitle("Reset User Password?");
                passwordResetShower.setMessage("Enter A New Password");
                passwordResetShower.setView(resetPass);

                passwordResetShower.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPassword = resetPass.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UserProfile.this, "Password Reset Successful", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserProfile.this, "Password Reset Failed. " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passwordResetShower.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                passwordResetShower.create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TaAgg", error.toString());
                    return;
                }
                if (value.exists()) {
                    na = value.getString("fName");
                    ph = value.getString("phone");
                    em = value.getString("email");
                    phone.setText("Phone: " + value.getString("phone"));
                    fullName.setText("Name: " + value.getString("fName"));
                    email.setText("Email: " + value.getString("email"));
                }
            }
        });
    }









    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moviemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BookFav:
                startActivity(new Intent(getApplicationContext(), BookFavorites.class));
                finish();
                return true;
            case R.id.MovieFav:
                Intent intent = new Intent(this, MovieFavorites.class);
                this.startActivity(intent);
                finish();
                return true;
            case R.id.SearchBooks:
                startActivity(new Intent(getApplicationContext(), BookSearch.class));
                finish();
                return true;
            case R.id.SearchMovies:
                startActivity(new Intent(getApplicationContext(), MovieSearch.class));
                finish();
                return true;
            case R.id.userProfile:
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                finish();
                return true;
            case R.id.lg:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
