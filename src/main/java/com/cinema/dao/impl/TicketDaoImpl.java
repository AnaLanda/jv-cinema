package com.cinema.dao.impl;

import com.cinema.dao.TicketDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Ticket;
import com.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class TicketDaoImpl implements TicketDao {
    private static final Logger log = Logger.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket add(Ticket ticket) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the ticket " + ticket + " to the DB.");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
            log.info("Successfully added the ticket " + ticket + " to the DB.");
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the ticket "
                    + ticket + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
