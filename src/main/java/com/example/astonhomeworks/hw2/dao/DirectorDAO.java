package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Director;
import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.NoSuchElementException;

public class DirectorDAO {

    public List<Movie> getAllDirectorMovies(int id) {

        List<Movie> movies = null;
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Query<Movie> query = session.createQuery(
                    "SELECT m FROM Movie m JOIN FETCH m.owner WHERE m.owner.id = :id", Movie.class);
            query.setParameter("id", id);

            movies = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        // Если заместо JOIN FETCH в запросе напишем  "FROM Movie WHERE owner.id = :id", то
        // вот тут попадаемся на ошибку LazyInitializationException (пришлось прописать FetchType.Lazy в Director и Movie)
        for (Movie movie: movies) {
            System.out.println(movie.getOwner().getName()); // всё прекрасно в консоли выводится
        }


        return movies;
    }

    public void addMovieToDirector(Movie movie, int directorId)  {

        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Director director = session.get(Director.class, directorId);
            if (director == null) throw new NoSuchElementException("Режиссёра с айди = " + directorId + " нет");

            movie.setOwner(director);
            session.persist(movie);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }

    public List<Director> getDirectors() {
        Transaction transaction = null;
        List<Director> directors = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            directors = session.createQuery("FROM Director", Director.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return directors;
    }

    public void addDirector(Director director) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.persist(director);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateDirector(Director director) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.merge(director);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Director getDirectorById(int id) {
        Transaction transaction = null;
        Director director = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            director = session.get(Director.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return director;
    }

    public void deleteDirector(int directorId)  {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Director director = session.get(Director.class, directorId);
            if (director == null) throw new NoSuchElementException("Режиссёра с айди = " + directorId + " нет");

            session.remove(director);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
