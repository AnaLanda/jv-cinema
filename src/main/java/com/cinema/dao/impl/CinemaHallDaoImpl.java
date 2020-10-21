package com.cinema.dao.impl;

import com.cinema.dao.CinemaHallDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.model.CinemaHall;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger log = Logger.getLogger(CinemaHallDaoImpl.class);
    private final SessionFactory sessionFactory;

    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the cinema hall " + cinemaHall + " to the DB.");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
            log.info("Successfully added a cinema hall to the DB");
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the cinema hall " + cinemaHall
                    + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        log.info("Trying to get all cinema halls...");
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> getAllHallsQuery =
                    session.createQuery("from CinemaHall", CinemaHall.class);
            return getAllHallsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all cinema halls from the DB.", e);
        }
    }
}
