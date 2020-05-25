package com.inetpsa.eds.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Satya Das
 */
public class HibernateUtil {
    private final static Logger logger = Logger.getLogger(HibernateUtil.class.getName());
    public static String appHome = "No";

    private static SessionFactory sessionFactory;

    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();

    /**
     * Initialize Hibernate Configuration
     */
    public static void initMonitor() {
        logger.info("Hibernate configure");
        try {
            sessionFactory = new AnnotationConfiguration().configure("/hibernate-eds.cfg.xml").buildSessionFactory();

        } catch (Throwable ex) {
            logger.log(Level.INFO, "[SERVER] (static) HibernateUtil.init()", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * @return a Session Factory Object
     */
    public static SessionFactory getSessionFactory() {
        // logger.info("Inside getSessionFactory method");
        try {

            if (sessionFactory == null) {
                initMonitor();
            } else {

                // sessionFactory.getStatistics().logSummary();
            }

        } catch (Exception e) {
            logger.log(Level.INFO, "[SERVER] (static) HibernateUtil.init()", e);
        }

        return sessionFactory;
    }

    /**
     * @return Session . Start a Session
     */
    public static Session getSession() {

        Session s = (Session) threadSession.get();
        // logger.debug("session"+s);
        if (s == null) {

            s = getSessionFactory().openSession();
            threadSession.set(s);
            // logger.debug("session 1 $"+s);
        }
        return s;
    }

    /**
     * Close Session
     */
    public static void closeSession() {

        Session s = (Session) threadSession.get();
        threadSession.set(null);
        if (s != null && s.isOpen()) {
            s.flush();
            s.close();
        }
    }

    /**
     * Start a new database transaction.
     */
    public static void beginTransaction() {
        Transaction tx = null;

        if (tx == null) {
            tx = getSession().beginTransaction();
            threadTransaction.set(tx);
        }
    }

    /**
     * Commit the database transaction.
     */
    public static void commitTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx != null) {
                tx.commit();
            }

            threadTransaction.set(null);

        } catch (HibernateException ex) {
            rollbackTransaction();

            throw ex;
        }
    }

    /**
     * Rollback the database transaction.
     */
    public static void rollbackTransaction() {

        Transaction tx = (Transaction) threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();
            }
        } finally {
            closeSession();
        }
    }

}
