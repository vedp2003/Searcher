package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieSearch extends AppCompatActivity {

    EditText editTextName;
    Button btnSearch, movieFav;
    ImageView imageView;
    TextView tvTitle, tvYear, tvActor, tvCountry, tvPlot, tvRating, tvGenre, tvRuntime, tvDirector, tvWriter, tvIMDB;
    RequestQueue requestQueue;
    String url, name, apiID;
    Toolbar toolbar;
    JSONObject object = new JSONObject();
    String title, year, actor, country, plot, image, rating, genre, runtime, director, writer, imdbrating;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;
    String data;
    String moviedata;
    boolean checker = false;
    ArrayList<String> plotchecker;
    ArrayList<String> linkchecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        editTextName = findViewById(R.id.editName);
        btnSearch = findViewById(R.id.btnSearch);
        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvReleased);
        tvActor = findViewById(R.id.tvActors);
        tvCountry = findViewById(R.id.tvCountry);
        tvPlot = findViewById(R.id.tvPlot);
        tvRating = findViewById(R.id.tvRating);
        tvGenre = findViewById(R.id.tvGenre);
        tvRuntime = findViewById(R.id.tvRuntime);
        tvDirector = findViewById(R.id.tvDirector);
        tvWriter = findViewById(R.id.tvWriter);
        tvIMDB = findViewById(R.id.tvIMDB);
        movieFav = findViewById(R.id.movieFavButton);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        plotchecker = new ArrayList<>();
        linkchecker = new ArrayList<>();

        //collectionReference = fStore.collection("movies");
        collectionReference = fStore.collection("movies").document("Users").collection(userID);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().isEmpty()) {
                    editTextName.setError("Search Field Required");
                    return;
                }

                parseJsonData();
            }
        });
        movieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Fav Movie to FireBase database
                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                checker = false;
                                plotchecker.add(document.get("Plot").toString());
                                linkchecker.add(document.get("Image").toString());
                                /*
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

                                 */


                            }
                        } else {
                            Log.d("TAGGNe1", "Error getting documents: ", task.getException());
                        }

                        for (int i = 0; i < linkchecker.size(); i++) {
                            if (linkchecker.get(i).equals(image)) {
                                checker = true;
                            }
                        }
                        for (int i = 0; i < plotchecker.size(); i++) {
                            if (plotchecker.get(i).equals(plot)) {
                                checker = true;
                            }
                        }
                        if (checker) {
                            Toast.makeText(MovieSearch.this, "Movie/Show Already In Your Favorites", Toast.LENGTH_SHORT).show();
                        }
                        if (checker == false) {
                            Map<String, Object> movie = new HashMap<>();
                            movie.put("Title", title);
                            movie.put("Year", year);
                            movie.put("Actor", actor);
                            movie.put("Country", country);
                            movie.put("Plot", plot);
                            movie.put("Image", image);
                            movie.put("Rating", rating);
                            movie.put("Genre", genre);
                            movie.put("Runtime", runtime);
                            movie.put("Director", director);
                            movie.put("Writer", writer);
                            movie.put("Imdbrating", imdbrating);

                            collectionReference.add(movie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MovieSearch.this, "Movie/Show Added To Your Favorites", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Tag4", "Error. " + e.getMessage());
                                }
                            });
                        }
                    }
                });

                /*
                Map<String, Object> movie = new HashMap<>();
                movie.put("Title", title);
                movie.put("Year", year);
                movie.put("Actor", actor);
                movie.put("Country", country);
                movie.put("Plot", plot);
                movie.put("Image", image);
                movie.put("Rating", rating);
                movie.put("Genre", genre);
                movie.put("Runtime", runtime);
                movie.put("Director", director);
                movie.put("Writer", writer);
                movie.put("Imdbrating", imdbrating);

                collectionReference.add(movie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MovieSearch.this, "Added To Your Favorites ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Tag4", "Error. " + e.getMessage());
                    }
                });

                 */
            }
        });


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

    private void parseJsonData() {
        requestQueue = Volley.newRequestQueue(this);
        name = editTextName.getText().toString();
        apiID = "85218b64";
        url = "https://www.omdbapi.com/?t=" + name + "&plot=full&apikey=" + apiID;
        StringRequest request = new StringRequest((Request.Method.GET), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    object = new JSONObject(response);
                    title = object.getString("Title");
                    year = object.getString("Released");
                    actor = object.getString("Actors");
                    country = object.getString("Country");
                    plot = object.getString("Plot");
                    image = object.getString("Poster");
                    rating = object.getString("Rated");
                    genre = object.getString("Genre");
                    runtime = object.getString("Runtime");
                    director = object.getString("Director");
                    writer = object.getString("Writer");
                    imdbrating = object.getString("imdbRating");
                    Glide.with(MovieSearch.this).load(image).into(imageView);
                    tvTitle.setText("Movie/Show Name: " + title);
                    tvYear.setText("Released In: " + year);
                    tvActor.setText("Actors: " + actor);
                    tvCountry.setText("Country: " + country);
                    tvPlot.setText("Plot: " + plot);
                    tvRating.setText("Rated: " + rating);
                    tvGenre.setText("Genre: " + genre);
                    tvRuntime.setText("Runtime: " + runtime);
                    tvDirector.setText("Director: " + director);
                    tvWriter.setText("Writer: " + writer);
                    tvIMDB.setText("Imdb Rating: " + imdbrating);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MovieSearch.this, "No Data was Found. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieSearch.this, "Error. " + error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }
}