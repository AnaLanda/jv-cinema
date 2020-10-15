package com.cinema.dao.impl;

import com.cinema.dao.OrderDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Order;
import com.cinema.model.User;
import com.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger log = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the order " + order + " to the DB.");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            log.info("Successfully added the order " + order + " to the DB.");
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the order "
                    + order + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getByUser(User user) {
        log.info("Trying to get all orders of the user " + user);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery("SELECT DISTINCT o FROM Order o "
                    + "JOIN FETCH o.user u "
                    + "JOIN FETCH o.tickets t "
                    + "JOIN FETCH t.movieSession ms "
                    + "JOIN FETCH ms.cinemaHall ch "
                    + "JOIN FETCH ms.movie m "
                    + "WHERE o.user = :user", Order.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to find the orders "
                    + "of the user " + user, e);
        }
    }
}
