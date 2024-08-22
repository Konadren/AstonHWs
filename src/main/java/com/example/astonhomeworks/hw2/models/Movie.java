package com.example.astonhomeworks.hw2.models;

public class Movie {

    private int id;
    private String name;
    private int releaseYear;
    private int owner;

    public Movie() {
    }

    public Movie(String name, int releaseYear, int owner, int id) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
