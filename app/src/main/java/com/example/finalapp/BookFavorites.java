package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookFavorites extends AppCompatActivity implements BookFavAdapter.OnBtnClickListener {

    TextView textView;
    Toolbar toolbar;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;
    ArrayList<BookInfoTwo> favBookInfo;
    ArrayList<String> theData;
    BookInfoTwo bookInfo;
    BookFavAdapter favBookAdapter;
    LinearLayoutManager booklinearLayoutManager;
    RecyclerView bookRecyclerView;
    String title, subtitle, publisher, publishedDate, description, thumbnail, author, category, pageCount, prev;
    String imageChecker;
    int indexNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_favorites);

        textView = findViewById(R.id.bookfavTextView);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        favBookInfo = new ArrayList<>();
        theData = new ArrayList<>();
        bookRecyclerView = findViewById(R.id.bookFavRecycler);
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        collectionReference = fStore.collection("books").document("Users").collection(userID);

        YoYo.with(Techniques.Flash).repeat(YoYo.INFINITE).playOn(textView);

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        title = document.get("title").toString();
                        subtitle = document.get("subtitle").toString();
                        publisher = document.get("publisher").toString();
                        publishedDate = document.get("publishedDate").toString();
                        description = document.get("description").toString();
                        thumbnail = document.get("thumbnail").toString();
                        author = document.get("authors").toString();
                        category = document.get("categories").toString();
                        pageCount = document.get("pageCount").toString();


                        Log.d("UUTt", "Rec");

                        bookInfo = new BookInfoTwo(title, subtitle, publisher, publishedDate, description, thumbnail, author, category, pageCount);
                        favBookInfo.add(bookInfo);
                        favBookAdapter = new BookFavAdapter(favBookInfo, BookFavorites.this, BookFavorites.this);
                        booklinearLayoutManager = new LinearLayoutManager(BookFavorites.this, RecyclerView.VERTICAL, false);
                        bookRecyclerView.setLayoutManager(booklinearLayoutManager);
                        bookRecyclerView.setAdapter(favBookAdapter);

                    }
                } else {
                    Log.d("TAGGNew2", "Error getting documents: ", task.getException());
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(bookRecyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int pos = viewHolder.getAdapterPosition();
            favBookAdapter.notifyItemRemoved(pos);
            imageChecker = favBookInfo.get(pos).getThumbnail();
            //Log.d("TitleBook", favBookInfo.get(pos).getTitle());
            favBookInfo.remove(pos);
            ArrayList<String> imageList = new ArrayList<>();
            ArrayList<String> docIdList = new ArrayList<>();

            indexNum = 0;

            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            docIdList.add(document.getId());
                            imageList.add(document.get("thumbnail").toString());

                        }
                    }
                    for (int i = 0; i < imageList.size(); i++) {
                        if (imageList.get(i).equals(imageChecker)) {
                            indexNum = i;
                        }
                    }
                    String doc = docIdList.get(indexNum);
                    Log.d("DeleteDocBook", doc);
                    collectionReference.document(doc).delete();
                    return;
                }
            });
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(BookFavorites.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_swipe)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

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
                startActivity(new Intent(getApplicationContext(), MovieFavorites.class));
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
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }

    @Override
    public void onDeleteBtn(int position) {

        //String link = "https://www.google.com/search?q=";
        //Uri uri = Uri.parse(link + "" + favBookInfo.get(position).getTitle());
        Uri uri = Uri.parse("https://www.amazon.com/s?k="+favBookInfo.get(position).getTitle()+"&i=stripbooks&ref=nb_sb_noss");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
}