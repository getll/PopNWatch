package com.example.popnwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {
    List<NewMovieDataDetail> movieData;
    Context context;
    LayoutInflater layoutInflater;

    public MovieRecyclerAdapter(List<NewMovieDataDetail> movieData, Context context) {
        this.movieData = movieData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.admin_movie_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewMovieDataDetail movie = movieData.get(position);

        //setting image using url via glide
        Glide.with(context).asBitmap().load(movie.getImage()).into(holder.movieImageView);

        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieRuntimeTextView.setText(movie.getRuntimeMins() + " minutes");
        holder.movieGenreTextView.setText(movie.getGenres());
        holder.movieRatingTextView.setText(movie.getPlot());

        if (movie.getContentRating().equals("")) {
            holder.contentRatingTextView.setText("No rating");
        }
        else {
            holder.contentRatingTextView.setText("Rated " + movie.getContentRating());
        }
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;
        TextView movieTitleTextView;
        TextView movieRuntimeTextView;
        TextView movieGenreTextView;
        TextView movieRatingTextView;
        TextView contentRatingTextView;
        Button addToMovieSelectionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImageView = itemView.findViewById(R.id.movieImageView);
            movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            movieRuntimeTextView = itemView.findViewById(R.id.movieStarsTextView);
            movieGenreTextView = itemView.findViewById(R.id.movieTimeTextView);
            movieGenreTextView = itemView.findViewById(R.id.movieTimeTextView);
            movieRatingTextView = itemView.findViewById(R.id.movieRuntimeTextView);
            contentRatingTextView = itemView.findViewById(R.id.contentRatingTextView);
            addToMovieSelectionButton = itemView.findViewById(R.id.addToMovieSelectionButton);

            addToMovieSelectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddMovieActivity.class);

                    //sending over the id and title of the movie
                    intent.putExtra("id", movieData.get(getAbsoluteAdapterPosition()).getId());
                    intent.putExtra("title", movieData.get(getAbsoluteAdapterPosition()).getTitle());

                    context.startActivity(intent);
                }
            });
        }
    }
}
