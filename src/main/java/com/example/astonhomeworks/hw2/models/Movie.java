package com.example.astonhomeworks.hw2.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Movie")
public class Movie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "releaseYear")
    private int releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private Director owner;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String name, int releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
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

    public Director getOwner() {
        return owner;
    }

    public void setOwner(Director owner) {
        this.owner = owner;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
