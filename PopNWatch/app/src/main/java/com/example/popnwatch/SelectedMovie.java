package com.example.popnwatch;

public class SelectedMovie {
    String id;
    String apiId;
    int screen;
    String time;

    public SelectedMovie() {
    }

    public SelectedMovie(String id, String apiId, int screen, String time) {
        this.id = id;
        this.apiId = apiId;
        this.screen = screen;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public int getScreen() {
        return screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
