package com.example.popnwatch;

import java.util.List;

public class NewMovieData
{
    public List<NewMovieDataDetail> items;
    public String errorMessage;

    public NewMovieData() {
    }

    public NewMovieData(List<NewMovieDataDetail> items, String errorMessage) {
        this.items = items;
        this.errorMessage = errorMessage;
    }

    public List<NewMovieDataDetail> getItems() {
        return items;
    }

    public void setItems(List<NewMovieDataDetail> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


