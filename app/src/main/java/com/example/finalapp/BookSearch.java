package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookSearch extends AppCompatActivity {

    RequestQueue requestQueue;
    ArrayList<BookInfo> info;
    EditText searchText;
    Button searchButton;
    BookInfo bookInfo;
    BookAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String apiKey, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        searchText = findViewById(R.id.bookSearcheditText);
        searchButton = findViewById(R.id.bookSearchButton);
        recyclerView = findViewById(R.id.bookRecycler);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText().toString().isEmpty()) {
                    searchText.setError("Search Field Required");
                    return;
                }
                getBooksInfo(searchText.getText().toString());
            }
        });
    }

    private void getBooksInfo(String query) {

        info = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(BookSearch.this);
        requestQueue.getCache().clear();
        apiKey = "AIzaSyDP68QHe56aRK729L2IqrFCVByRrHXsncQ";
        url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;
        RequestQueue queue = Volley.newRequestQueue(BookSearch.this);
        Log.d("tag1", url);

        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("items");

                    for (int i = 0; i < object.length(); i++) {
                        JSONObject items = object.getJSONObject(i);
                        JSONObject volume = items.getJSONObject("volumeInfo");
                        String title = volume.optString("title");
                        String subtitle = volume.optString("subtitle");
                        String author = volume.optString("authors");
                        String publisher = volume.optString("publisher");
                        String publishedDate = volume.optString("publishedDate");
                        String description = volume.optString("description");
                        int pageCount = volume.optInt("pageCount");
                        JSONObject image = volume.optJSONObject("imageLinks");
                        String thumbnail = image.optString("thumbnail");
                        String category = volume.optString("categories");

                        bookInfo = new BookInfo(title, subtitle, author, publisher, publishedDate, description, "" + pageCount, thumbnail, category);
                        info.add(bookInfo);
                        adapter = new BookAdapter(info, BookSearch.this);
                        linearLayoutManager = new LinearLayoutManager(BookSearch.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(BookSearch.this, "No Data was Found. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookSearch.this, "Error. " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requestJson);
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
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }
}