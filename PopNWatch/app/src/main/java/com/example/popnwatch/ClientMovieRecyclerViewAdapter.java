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

public class ClientMovieRecyclerViewAdapter extends RecyclerView.Adapter<ClientMovieRecyclerViewAdapter.ViewHolder> {
    List<NewMovieDataDetail> movieData;
    List<SelectedMovie> selectedMovies;
    Context context;
    LayoutInflater layoutInflater;

    public ClientMovieRecyclerViewAdapter(List<NewMovieDataDetail> movieData, List<SelectedMovie> selectedMovies, Context context) {
        this.movieData = movieData;
        this.selectedMovies = selectedMovies;

        this.context = context;
    }

    @NonNull
    @Override
    public ClientMovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.client_movie_row, parent, false);

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

        //adding the information into it
        Glide.with(context).asBitmap().load(movieDataDetail.getImage()).into(holder.movieImageView);

        holder.movieTitleTextView.setText(movieDataDetail.getTitle());
        holder.movieTimeTextView.setText(movie.getTime());
        holder.movieRuntimeTextView.setText(movieDataDetail.getRuntimeMins() + " minutes");

        if (movieDataDetail.getContentRating() == null || movieDataDetail.getContentRating().isEmpty())
            holder.contentRatingTextView.setText("No rating");
        else
            holder.contentRatingTextView.setText("Rated " + movieDataDetail.getContentRating());
    }

    @Override
    public int getItemCount() {
        return selectedMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitleTextView, movieTimeTextView, movieRuntimeTextView, contentRatingTextView;
        ImageView movieImageView;
        Button movieDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            movieTimeTextView = itemView.findViewById(R.id.movieTimeTextView);
            movieRuntimeTextView = itemView.findViewById(R.id.movieRuntimeTextView);
            contentRatingTextView = itemView.findViewById(R.id.contentRatingTextView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
            movieDetailsButton = itemView.findViewById(R.id.movieDetailsButton);

            movieDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectedMovie movie = selectedMovies.get(getAbsoluteAdapterPosition());
                    NewMovieDataDetail movieDataDetail = new NewMovieDataDetail();

                    for (NewMovieDataDetail movieDetail : movieData) {
                        if (movieDetail.getId().equals(movie.getApiId())) {
                            movieDataDetail = movieDetail;
                        }
                    }

                    Intent intent = new Intent(context, MovieDetailsActivity.class);

                    intent.putExtra("title", movieDataDetail.getTitle());
                    intent.putExtra("img", movieDataDetail.getImage());
                    intent.putExtra("plot", movieDataDetail.getPlot());
                    intent.putExtra("time", movie.getTime());
                    intent.putExtra("runtime", movieDataDetail.getRuntimeMins());

                    context.startActivity(intent);
                }
            });
        }
    }
}
