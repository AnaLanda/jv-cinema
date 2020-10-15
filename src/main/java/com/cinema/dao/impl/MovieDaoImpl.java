package com.cinema.dao.impl;

import com.cinema.dao.MovieDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Movie;
import com.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieDaoImpl implements MovieDao {
    private static final Logger log = Logger.getLogger(MovieDaoImpl.class);

    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the movie " + movie + " to the DB.");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
            log.info("Successfully added the movie " + movie + " to the DB.");
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the movie "
                    + movie + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Movie> getAll() {
        log.info("Trying to get all movies...");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("from Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all movies from the DB.", e);
        }
    }
}
