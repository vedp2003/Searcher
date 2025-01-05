package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.MovieFavViewHolder> {
    private ArrayList<MovieInfo> movieInfoArrayList;
    private Context parentContext;
    private String dataString;
    OnBtnClickListener onBtnClickListener;

    public MovieFavAdapter(ArrayList<MovieInfo> movieInfoArrayList, Context parentContext, OnBtnClickListener onBtnClickListener) {
        this.movieInfoArrayList = movieInfoArrayList;
        this.parentContext = parentContext;
        this.onBtnClickListener = onBtnClickListener;
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviefavitems, parent, false);
        return new MovieFavViewHolder(view, onBtnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavViewHolder holder, int position) {
        MovieInfo movieInfo = movieInfoArrayList.get(position);

        Log.d("1", "Received");
        //From dataString, extact Title and Author
        holder.titleCard.setText("" + movieInfo.getTitle());
        holder.authorCard.setText("Released On: " + movieInfo.getYear());
        holder.bookImageCard.setImageResource(R.drawable.ic_launcher_background);
        Picasso.get().load(movieInfo.getImage()).into(holder.bookImageCard);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(parentContext, movieFavDetails.class);

                i.putExtra("title", movieInfo.getTitle());
                i.putExtra("year", movieInfo.getYear());
                i.putExtra("actor", movieInfo.getActor());
                i.putExtra("country", movieInfo.getCountry());
                i.putExtra("plot", movieInfo.getPlot());
                i.putExtra("image", movieInfo.getImage());
                i.putExtra("rating", movieInfo.getRating());
                i.putExtra("genre", movieInfo.getGenre());
                i.putExtra("runtime", movieInfo.getRuntime());
                i.putExtra("director", movieInfo.getDirector());
                i.putExtra("writer", movieInfo.getWriter());
                i.putExtra("imdbrating", movieInfo.getImdbrating());


                parentContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieInfoArrayList.size();
        //return 0;
    }

    public class MovieFavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleCard, authorCard;
        ImageView bookImageCard;
        Button remove;
        OnBtnClickListener onBtnClickListener;

        public MovieFavViewHolder(@NonNull View itemView, final OnBtnClickListener onBtnClickListener) {
            super(itemView);
            this.onBtnClickListener = onBtnClickListener;
            titleCard = itemView.findViewById(R.id.favMovieTitle);
            authorCard = itemView.findViewById(R.id.favMovieAuthor);
            remove = itemView.findViewById(R.id.favMovieDelete);
            bookImageCard = itemView.findViewById(R.id.favMovieImage);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBtnClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onBtnClickListener.onDeleteBtnClick(position);
                        }
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
        }
    }

    public interface OnBtnClickListener {
        void onDeleteBtnClick(int position);
    }
}