package com.example.finalapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<BookInfo> bookInfoArrayList;
    private Context parentContext;

    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context parentContext) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.parentContext = parentContext;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookInfo bookInfo = bookInfoArrayList.get(position);
        holder.namee.setText(bookInfo.getTitle());
        holder.publisherr.setText("Published By: " + bookInfo.getPublisher());
        holder.pageCount.setText("Page Count: " + bookInfo.getPageCount());
        holder.datee.setText("Published On: " + bookInfo.getPublishedDate());
        Picasso.get().load(bookInfo.getThumbnail()).into(holder.bookPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(parentContext, BookDetails.class);
                i.putExtra("title", bookInfo.getTitle());
                i.putExtra("subtitle", bookInfo.getSubtitle());
                i.putExtra("authors", bookInfo.getAuthors());
                i.putExtra("publisher", bookInfo.getPublisher());
                i.putExtra("publishedDate", bookInfo.getPublishedDate());
                i.putExtra("description", bookInfo.getDescription());
                i.putExtra("pageCount", bookInfo.getPageCount());
                i.putExtra("thumbnail", bookInfo.getThumbnail());
                i.putExtra("categories", bookInfo.getCategory());

                parentContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView namee, publisherr, pageCount, datee;
        ImageView bookPic;

        public BookViewHolder(View itemView) {
            super(itemView);
            namee = itemView.findViewById(R.id.recyclerBookTitle);
            publisherr = itemView.findViewById(R.id.recyclerBookPublisher);
            pageCount = itemView.findViewById(R.id.recyclerBookPage);
            datee = itemView.findViewById(R.id.recyclerBookDate);
            bookPic = itemView.findViewById(R.id.recyclerBookImage);

        }
    }
}
