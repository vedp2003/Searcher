package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class BookFavDetails extends AppCompatActivity {

    String title, subtitle, publisher, publishedDate, description, thumbnail, author, category, pageCount;

    TextView titleTV, publisherTV, descTV, pageTV, publishDateTV, authorTV, typeTV;
    ImageView bookIV;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_fav_details);

        titleTV = findViewById(R.id.bookNameFav);
        publisherTV = findViewById(R.id.bookPublisherFav);
        descTV = findViewById(R.id.BookDescriptionFav);
        pageTV = findViewById(R.id.bookPagesFav);
        publishDateTV = findViewById(R.id.bookPublishDateFav);
        bookIV = findViewById(R.id.bookImageFav);
        authorTV = findViewById(R.id.bookAuthorFav);
        typeTV = findViewById(R.id.bookCategoryFav);

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        collectionReference = fStore.collection("books").document("Users").collection(userID);

        title = getIntent().getStringExtra("datitle");
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

        titleTV.setText("Title: " + title + ". ");
        publisherTV.setText("Published By: " + publisher);
        publishDateTV.setText("Published On : " + publishedDate);
        descTV.setText("Description: " + description);
        pageTV.setText("Page Count: " + pageCount);
        authorTV.setText("Author(s): " + author);
        typeTV.setText("Category: " + category);
        Picasso.get().load(thumbnail).into(bookIV);

    }
}