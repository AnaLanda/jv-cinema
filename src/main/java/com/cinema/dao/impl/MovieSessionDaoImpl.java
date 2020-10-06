package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.MovieSession;
import com.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add " + movieSession + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> findSessionsByIdAndDate(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query =
                    session.createQuery("from MovieSession ms where ms.id = :movieId "
                    + "and ms.showTime = :date", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find available sessions", e);
        }
    }
}
