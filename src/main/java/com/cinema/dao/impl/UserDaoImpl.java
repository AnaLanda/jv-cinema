package com.cinema.dao.impl;

import com.cinema.dao.UserDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.User;
import com.cinema.util.HibernateUtil;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User "
                    + "where email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find a user with the email "
                    + email, e);
        }
    }
}
