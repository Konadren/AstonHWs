package com.example.astonhomeworks.hw2.models;

import java.util.List;

public class Director {

    private int id;
    private String name;
    private Integer age;
    private List<Movie> movieList;

    public Director() {
    }

    public Director(int id, String name, Integer age, List<Movie> movieList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
