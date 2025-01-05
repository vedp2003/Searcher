package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookDetails extends AppCompatActivity {

    String title, subtitle, publisher, publishedDate, description, thumbnail, author, category;
    String pageCount;

    TextView titleText, publisherText, descText, pageText, dateText, authorText, typeText;
    ImageView bookImage;
    Button favorites;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;
    boolean checker = false;
    ArrayList<String> descchecker;
    ArrayList<String> thumbchecker;
    int priority;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        titleText = findViewById(R.id.bookName);
        publisherText = findViewById(R.id.bookPublisher);
        descText = findViewById(R.id.BookDescription);
        pageText = findViewById(R.id.bookPages);
        dateText = findViewById(R.id.bookPublishDate);
        bookImage = findViewById(R.id.bookImage);
        authorText = findViewById(R.id.bookAuthor);
        favorites = findViewById(R.id.bookFavoriteButton);
        typeText = findViewById(R.id.bookCategory);

        thumbchecker = new ArrayList<>();
        descchecker = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        collectionReference = fStore.collection("books").document("Users").collection(userID);

        priority = 0;
        counter = 0;
        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        publisher = getIntent().getStringExtra("publisher");
        publishedDate = getIntent().getStringExtra("publishedDate");
        description = getIntent().getStringExtra("description");
        pageCount = getIntent().getStringExtra("pageCount");
        thumbnail = getIntent().getStringExtra("thumbnail");
        author = getIntent().getStringExtra("authors");
        category = getIntent().getStringExtra("categories");
        author = author.replace("\"", "").replace("[", "").replace("]", "").replace(",", ", ");
        category = category.replace("\"", "").replace("[", "").replace("]", "").replace(",", ", ");

        titleText.setText("Title: " + title + ". " + subtitle);
        publisherText.setText("Published By: " + publisher);
        dateText.setText("Published On : " + publishedDate);
        descText.setText("Description: " + description);
        pageText.setText("Page Count: " + pageCount);
        authorText.setText("Author(s): " + author);
        typeText.setText("Category: " + category);
        Picasso.get().load(thumbnail).into(bookImage);

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority++;
                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                checker = false;
                                descchecker.add(document.get("description").toString());
                                thumbchecker.add(document.get("thumbnail").toString());

                            }
                        } else {
                            Log.d("TAGGNe1", "Error getting documents: ", task.getException());
                        }

                        for (int i = 0; i < thumbchecker.size(); i++) {
                            if (thumbchecker.get(i).equals(thumbnail)) {
                                checker = true;
                            }
                        }
                        for (int i = 0; i < descchecker.size(); i++) {
                            if (descchecker.get(i).equals(description)) {
                                checker = true;
                            }
                        }
                        if (checker) {
                            Toast.makeText(BookDetails.this, "Book Already In Your Favorites", Toast.LENGTH_SHORT).show();
                        }
                        if (checker == false) {

                            Map<String, Object> book = new HashMap<>();
                            book.put("title", title);
                            book.put("subtitle", subtitle);
                            book.put("publisher", publisher);
                            book.put("publishedDate", publishedDate);
                            book.put("description", description);
                            book.put("pageCount", "" + pageCount);
                            book.put("thumbnail", thumbnail);
                            book.put("authors", author);
                            book.put("categories", category);
                            collectionReference.add(book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(BookDetails.this, "Book Added To Your Favorites", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Tag8", "Error. " + e.getMessage());
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}