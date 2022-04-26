package com.example.popnwatch;

import android.content.Context;
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

public class SelectedMovieRecyclerViewAdapter extends RecyclerView.Adapter<SelectedMovieRecyclerViewAdapter.ViewHolder> {
    List<NewMovieDataDetail> movieData;
    List<SelectedMovie> selectedMovies;
    Context context;
    LayoutInflater layoutInflater;

    public SelectedMovieRecyclerViewAdapter(List<NewMovieDataDetail> movieData, List<SelectedMovie> selectedMovies, Context context) {
        this.movieData = movieData;
        this.selectedMovies = selectedMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.admin_selected_movie_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedMovie movie = selectedMovies.get(position);
        NewMovieDataDetail movieDataDetail = new NewMovieDataDetail();

        for (NewMovieDataDetail movieDetail : movieData) {
            if (movieDetail.getId().equals(movie.getApiId())) {
                movieDataDetail = movieDetail;
            }
        }

        //setting image using url via glide
        Glide.with(context).asBitmap().load(movieDataDetail.getImage()).into(holder.movieImageView);

        holder.movieTitleTextView.setText(movieDataDetail.getTitle());
        holder.movieScreenTextView.setText("Screen " + movie.getScreen());
        holder.movieTimeTextView.setText(movie.getTime());
        holder.movieRuntimeTextView.setText(movieDataDetail.getRuntimeMins() + " minutes");

        if (movieDataDetail.getContentRating() == null || movieDataDetail.getContentRating().isEmpty()) {
            holder.contentRatingTextView.setText("No rating");
        }
        else {
            holder.contentRatingTextView.setText("Rated " + movieDataDetail.getContentRating());
        }
    }

    @Override
    public int getItemCount() {
        return selectedMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;
        TextView movieTitleTextView;
        TextView movieScreenTextView;
        TextView movieTimeTextView;
        TextView movieRuntimeTextView;
        TextView contentRatingTextView;
        Button deleteMovieButton;
        Button editMovieButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImageView = itemView.findViewById(R.id.movieImageView);
            movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            movieScreenTextView = itemView.findViewById(R.id.movieScreenTextView);
            movieTimeTextView = itemView.findViewById(R.id.movieTimeTextView);
            movieRuntimeTextView = itemView.findViewById(R.id.movieRuntimeTextView);
            contentRatingTextView = itemView.findViewById(R.id.contentRatingTextView);
            deleteMovieButton = itemView.findViewById(R.id.deleteMovieButton);
            editMovieButton = itemView.findViewById(R.id.editMovieButton);
        }
    }
}
