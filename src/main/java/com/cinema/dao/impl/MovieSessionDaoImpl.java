package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger log = Logger.getLogger(MovieSessionDaoImpl.class);
    private final SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the movie session " + movieSession + " to the DB.");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            log.info("Successfully added the movie session " + movieSession + " to the DB.");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the movie session "
                    + movieSession + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> findById(Long id) {
        log.info("Trying to get the movie session with the ID " + id);
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find a movie session with the ID "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findSessionsByIdAndDate(Long movieId, LocalDate date) {
        log.info("Trying to get all movie sessions for the movie with ID " + movieId
                + " for the date " + date);
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                    + "join fetch ms.movie m "
                    + "join fetch ms.cinemaHall ch "
                    + "where m.id = :movieId "
                    + "and ms.showTime between :start AND :end", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("start", date.atTime(LocalTime.MIN));
            query.setParameter("end", date.atTime(LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find available sessions with the movie "
                    + "with ID " + movieId + " for the date " + date, e);
        }
    }
}
