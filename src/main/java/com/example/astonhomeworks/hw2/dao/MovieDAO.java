package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MovieDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public MovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
            // Насколько я понимаю, при этом запросе должны делаться ещё уйма других запросов
            // которые будут стягивать режиссёров
            // но из-за того, что у меня стоит FetchType.LAZY этого не происходит
            // можно было бы использовать JOIN FETCH, как в DirectorDAO, если бы заместо LAZY был EAGER

        //SELECT m FROM Movie m JOIN FETCH m.owner WHERE m.owner.id = :id
        return getCurrentSession().createQuery("SELECT m FROM Movie m JOIN FETCH m.owner", Movie.class).list();
    }

    @Transactional
    public void addMovie(Movie movie) {
        getCurrentSession().persist(movie);
    }

    @Transactional(readOnly = true)
    public Movie getMovieById(int movieId) {
        return getCurrentSession().get(Movie.class, movieId);
    }

    @Transactional
    public void updateMovie(Movie movie) {
        getCurrentSession().merge(movie);
    }

    @Transactional
    public void deleteMovie(int movieId) {
        Movie movie = getCurrentSession().get(Movie.class, movieId);
        if (movie == null) throw new NoSuchElementException("Фильма с айди = " + movieId + " нет");

        getCurrentSession().remove(movie);

    }

    @Transactional(readOnly = true)
    public List<Actor> getActorsByMovieId(int movieId) {
        // HQL запрос для получения фильмов по ID актера
        String hqlQuery = "SELECT a FROM Movie m JOIN m.actors a WHERE m.id = :movieId";
        Query<Actor> query = getCurrentSession().createQuery(hqlQuery, Actor.class);
        query.setParameter("movieId", movieId);

        return query.getResultList();
    }

    @Transactional
    public void addActorToMovie(int movieId, int actorId) {
        Movie movie = getCurrentSession().get(Movie.class, movieId);
        Actor actor = getCurrentSession().get(Actor.class, actorId);

        if (movie == null) {
            throw new RuntimeException("Movie with ID " + movieId + " not found.");
        }
        if (actor == null) {
            throw new RuntimeException("Actor with ID " + actorId + " not found.");
        }

        if (movie.getActors().isEmpty()) {
            movie.setActors(new ArrayList<>(List.of(actor)));
        } else {
            movie.getActors().add(actor);
        }

        if(actor.getMovies().isEmpty()) {
            actor.setMovies(new ArrayList<>(List.of(movie)));
        } else {
            actor.getMovies().add(movie);
        }

        getCurrentSession().persist(actor);
    }
}
