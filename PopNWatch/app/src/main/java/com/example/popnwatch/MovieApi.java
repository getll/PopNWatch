package com.example.popnwatch;

//key k_5bgj25ib

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    String BASE_URL = "https://imdb-api.com/en/API/";

    @GET("InTheaters/k_5bgj25ib")
    Call<NewMovieData> getNewMovies();
}
