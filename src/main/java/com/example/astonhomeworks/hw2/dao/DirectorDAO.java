package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Director;
import com.example.astonhomeworks.hw2.models.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class DirectorDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public DirectorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllDirectorMovies(int id) {
        Query<Movie> query = getCurrentSession().createQuery(
                "SELECT m FROM Movie m JOIN FETCH m.owner WHERE m.owner.id = :id", Movie.class);

        query.setParameter("id", id);

        return query.getResultList();
    }

    @Transactional
    public void addMovieToDirector(Movie movie, int directorId) {
        Director director = getCurrentSession().get(Director.class, directorId);
        if (director == null) throw new NoSuchElementException("Режиссёра с айди = " + directorId + " нет");
        movie.setOwner(director);
        getCurrentSession().persist(movie);
    }

    @Transactional(readOnly = true)
    public List<Director> getDirectors() {
        return getCurrentSession().createQuery("FROM Director", Director.class).list();
    }

    @Transactional
    public void addDirector(Director director) {
        getCurrentSession().persist(director);
    }

    @Transactional
    public void updateDirector(Director director) {
        getCurrentSession().merge(director);
    }

    @Transactional(readOnly = true)
    public Director getDirectorById(int id) {
        return getCurrentSession().get(Director.class, id);
    }

    @Transactional
    public void deleteDirector(int directorId) {
        Director director = getCurrentSession().get(Director.class, directorId);
        if (director == null) throw new NoSuchElementException("Режиссёра с айди = " + directorId + " нет");

        getCurrentSession().remove(director);
    }
}