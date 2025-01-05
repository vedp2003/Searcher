package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class movieFavDetails extends AppCompatActivity {

    TextView favMovieTitle, favMovieReleased, favMovieActor, favMovieCountry, favMoviePlot, favMovieRating, favMovieGenre, favMovieRuntime, favMovieDirector, favMovieWriter, favMovieIMDB;
    ImageView favMovieImage;
    String data;

    String title, year, actor, country, plot, image, rating, genre, runtime, director, writer, imdbrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favmoviedetails);

        data = "";
        favMovieImage = findViewById(R.id.favmovieimageView);
        favMovieTitle = findViewById(R.id.daTitle);
        favMovieReleased = findViewById(R.id.daReleased);
        favMovieActor = findViewById(R.id.daActors);
        favMovieCountry = findViewById(R.id.daCountry);
        favMoviePlot = findViewById(R.id.daPlot);
        favMovieRating = findViewById(R.id.daRating);
        favMovieGenre = findViewById(R.id.daGenre);
        favMovieRuntime = findViewById(R.id.daRuntime);
        favMovieDirector = findViewById(R.id.daDirector);
        favMovieWriter = findViewById(R.id.daWriter);
        favMovieIMDB = findViewById(R.id.daIMDB);

        title = getIntent().getStringExtra("title");
        year = getIntent().getStringExtra("year");
        actor = getIntent().getStringExtra("actor");
        country = getIntent().getStringExtra("country");
        plot = getIntent().getStringExtra("plot");
        image = getIntent().getStringExtra("image");
        rating = getIntent().getStringExtra("rating");
        genre = getIntent().getStringExtra("genre");
        runtime = getIntent().getStringExtra("runtime");
        director = getIntent().getStringExtra("director");
        writer = getIntent().getStringExtra("writer");
        imdbrating = getIntent().getStringExtra("imdbrating");

        favMovieTitle.setText("Movie/Show Name: " + title);
        favMovieReleased.setText("Released In: " + year);
        favMovieActor.setText("Actors: " + actor);
        favMovieCountry.setText("Country: " + country);
        favMoviePlot.setText("Plot: " + plot);
        favMovieRating.setText("Rated: " + rating);
        favMovieGenre.setText("Genre: " + genre);
        favMovieRuntime.setText("Runtime: " + runtime);
        favMovieDirector.setText("Director: " + director);
        favMovieWriter.setText("Writer: " + writer);
        favMovieIMDB.setText("Imdb Rating: " + imdbrating);
        Glide.with(movieFavDetails.this).load(image).into(favMovieImage);

    }
}