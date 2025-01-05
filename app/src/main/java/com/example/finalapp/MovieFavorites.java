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
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MovieFavorites extends AppCompatActivity implements MovieFavAdapter.OnBtnClickListener {

    TextView textView;
    Toolbar toolbar;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;
    String moviedata = "";
    int counter = 0;
    ArrayList<MovieInfo> favMovieInfo;
    ArrayList<String> theData;
    MovieInfo movieInfo;
    MovieFavAdapter favMovieAdapter;
    LinearLayoutManager movielinearLayoutManager;
    RecyclerView movieRecyclerView;
    String title, year, actor, country, plot, image, rating, genre, runtime, director, writer, imdbrating;
    String ima, titl;
    int index;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorites);

        textView = findViewById(R.id.moviefavTextView);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        favMovieInfo = new ArrayList<>();
        theData = new ArrayList<>();
        movieRecyclerView = findViewById(R.id.movieFavRecycler);
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        YoYo.with(Techniques.Flash).repeat(YoYo.INFINITE).playOn(textView);

        //collectionReference = fStore.collection("movies");
        collectionReference = fStore.collection("movies").document("Users").collection(userID);

        collectionReference.orderBy("Title", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    moviedata = "";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        counter++;
                        Map<String, Object> map = new HashMap<String, Object>();
                        //map.put("data"+counter, document.getData());
                        map.put("data", document.getData());
                        moviedata += map.get("data").toString() + "\n";
                        //Log.d("DaDate", moviedata);
                        theData.add(document.getData().toString());
                        //textView.setText(moviedata);

                        title = document.get("Title").toString();
                        year = document.get("Year").toString();
                        actor = document.get("Actor").toString();
                        country = document.get("Country").toString();
                        plot = document.get("Plot").toString();
                        image = document.get("Image").toString();
                        rating = document.get("Rating").toString();
                        genre = document.get("Genre").toString();
                        runtime = document.get("Runtime").toString();
                        director = document.get("Director").toString();
                        writer = document.get("Writer").toString();
                        imdbrating = document.get("Imdbrating").toString();

                        movieInfo = new MovieInfo(title, year, actor, country, plot, image, rating, genre, runtime, director, writer, imdbrating);
                        favMovieInfo.add(movieInfo);
                        favMovieAdapter = new MovieFavAdapter(favMovieInfo, MovieFavorites.this, MovieFavorites.this);
                        movielinearLayoutManager = new LinearLayoutManager(MovieFavorites.this, RecyclerView.VERTICAL, false);
                        movieRecyclerView.setLayoutManager(movielinearLayoutManager);
                        movieRecyclerView.setAdapter(favMovieAdapter);

                    }
                } else {
                    Log.d("TAGGNew1", "Error getting documents: ", task.getException());
                }

                /*
                for(int i = 0; i < theData.size(); i++){
                    Log.d("For Loop: ", theData.get(i));
                }
               */
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(movieRecyclerView);
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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int pos = viewHolder.getAdapterPosition();
            //Toast.makeText(this,  "Button Clicked", Toast.LENGTH_SHORT).show();
            favMovieAdapter.notifyItemRemoved(pos);

            //Log.d("Pos Index 1", ""+position);

            ima = favMovieInfo.get(pos).getImage();
            titl = favMovieInfo.get(pos).getTitle();
            //Log.d("TitleMovie", favMovieInfo.get(pos).getTitle());
            favMovieInfo.remove(pos);

            ArrayList<String> imageChecker = new ArrayList<>();
            ArrayList<String> titleChecker = new ArrayList<>();
            ArrayList<String> docId = new ArrayList<>();

            index = 0;
            check = false;

            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            docId.add(document.getId());
                            titleChecker.add(document.get("Title").toString());

                        }
                    }
                    for (int i = 0; i < titleChecker.size(); i++) {
                        if (titleChecker.get(i).equals(titl)) {
                            //Log.d("Pos Index 2", ""+i);
                            index = i;
                            check = true;
                        }
                    }
                    String doc = docId.get(index);
                    Log.d("DeleteDocMovie", doc);
                    collectionReference.document(doc).delete();
                    return;
                }
            });
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MovieFavorites.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_swipe)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onDeleteBtnClick(int position) {

        //String link = "https://www.google.com/search?q=";
        //Uri uri = Uri.parse(link + "" + favMovieInfo.get(position).getTitle());
        Uri uri = Uri.parse("https://www.amazon.com/s?k="+favMovieInfo.get(position).getTitle()+"&i=movies-tv&ref=nb_sb_noss");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}