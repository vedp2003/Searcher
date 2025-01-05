package com.example.finalapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookFavAdapter extends RecyclerView.Adapter<BookFavAdapter.BookFavViewHolder> {

    private ArrayList<BookInfoTwo> bookFavInfoArrayList;
    private Context parentContext;
    OnBtnClickListener onBtnClickListener;


    public BookFavAdapter(ArrayList<BookInfoTwo> bookFavInfoArrayList, Context parentContext, OnBtnClickListener onBtnClickListener) {
        this.bookFavInfoArrayList = bookFavInfoArrayList;
        this.parentContext = parentContext;
        this.onBtnClickListener = onBtnClickListener;
    }

    @NonNull
    @Override
    public BookFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookfav_rv_item, parent, false);
        return new BookFavViewHolder(view, onBtnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookFavViewHolder holder, int position) {
        Log.d("UUT", "Rec");
        BookInfoTwo bookFavInfo = bookFavInfoArrayList.get(position);
        holder.nameFav.setText(bookFavInfo.getTitle());
        holder.publisherFav.setText("Published By: " + bookFavInfo.getPublisher());
        holder.pageCountFav.setText("Page Count: " + bookFavInfo.getPageCount());
        holder.dateFav.setText("Published On: " + bookFavInfo.getPublishedDate());
        Picasso.get().load(bookFavInfo.getThumbnail()).into(holder.bookImageFav);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(parentContext, BookFavDetails.class);
                i.putExtra("datitle", bookFavInfo.getTitle());
                i.putExtra("subtitle", bookFavInfo.getSubtitle());
                i.putExtra("authors", bookFavInfo.getAuthor());
                i.putExtra("publisher", bookFavInfo.getPublisher());
                i.putExtra("publishedDate", bookFavInfo.getPublishedDate());
                i.putExtra("description", bookFavInfo.getDescription());
                i.putExtra("pageCount", bookFavInfo.getPageCount());
                i.putExtra("thumbnail", bookFavInfo.getThumbnail());
                i.putExtra("categories", bookFavInfo.getCategory());

                parentContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookFavInfoArrayList.size();
    }

    public class BookFavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameFav, publisherFav, pageCountFav, dateFav;
        ImageView bookImageFav;
        Button remove;
        OnBtnClickListener onBtnClickListener;

        public BookFavViewHolder(@NonNull View itemView, final BookFavAdapter.OnBtnClickListener onBtnClickListener) {
            super(itemView);
            this.onBtnClickListener = onBtnClickListener;
            nameFav = itemView.findViewById(R.id.recyclerFavBookTitle);
            publisherFav = itemView.findViewById(R.id.recyclerFavBookPublisher);
            pageCountFav = itemView.findViewById(R.id.recyclerFavBookPage);
            dateFav = itemView.findViewById(R.id.recyclerFavBookDate);
            bookImageFav = itemView.findViewById(R.id.recyclerFavBookImage);
            remove = itemView.findViewById(R.id.recyclerFavDelete);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBtnClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onBtnClickListener.onDeleteBtn(position);
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
        void onDeleteBtn(int position);
    }
}
