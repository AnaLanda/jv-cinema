package com.cinema.dao.impl;

import com.cinema.dao.UserDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.model.User;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) {
        log.info("Trying to add the user " + user + " to the DB.");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("Successfully added the user " + user + " to the DB.");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the user "
                    + user + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Trying to get the user with the email " + email);
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User "
                    + "where email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find a user with the email "
                    + email, e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Trying to get the user with the ID " + id);
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find a user with the ID "
                    + id, e);
        }
    }
}
