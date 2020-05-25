package com.inetpsa.eds.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.inetpsa.eds.dao.model.Chs;

/**
 * Class to access CHS in the database
 * 
 * @author Joao Costa @ Alter Frame
 */
public class CHSdao {
    // PUBLIC

    private final int MAX_RESULTS = 100;

    public synchronized static CHSdao getInstance() {
        if (singleton == null) {
            singleton = new CHSdao();
        }

        return singleton;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestion de session">
    // public void openDBSession() { }

    public Session getCurrentSession() {
        return HibernateUtil.getSession();
    }

    public Chs getChsByRef(String ref) {
        logger.log(Level.INFO, String.format("[SERVER] getChsByRef() : ref=%s", ref));

        if (ref == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from Chs e" + " where e.edsRef = ?");
        q.setMaxResults(MAX_RESULTS);
        q.setParameter(0, ref);

        return executeSingletonQuery(q, Chs.class);
    }

    public List<Chs> getAllConnectedChs() {
        logger.log(Level.INFO, "[SERVER] getAllChs()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from Chs e where e.edsChs is not empty");
        q.setMaxResults(MAX_RESULTS);

        return executeListQuery(q, Chs.class);
    }

    public List<Chs> getAllChs() {
        logger.log(Level.INFO, "[SERVER] getAllChs()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from Chs e");
        q.setMaxResults(MAX_RESULTS);

        return executeListQuery(q, Chs.class);
    }

    // PACKAGE
    static void queryInterval(Query q, int intervalFrom, int intervalSize) {
        q.setFirstResult(intervalFrom);
        q.setMaxResults(intervalSize);
    }

    // PROTECTED
    // PRIVATE
    private final static Logger logger = Logger.getLogger(CHSdao.class.getName());
    private final static SimpleDateFormat DF = new SimpleDateFormat("yyMMdd");
    private static CHSdao singleton = null;
    // private Session HibernateUtil.getSession();
    private HashMap<AccessToken.AccessTokenId, AccessToken> accessTokens;

    private void init() {

    }

    private CHSdao() {
        init();
    }

    private void checkSession() {
        if (HibernateUtil.getSession() == null) {
            throw new RuntimeException("DB session not opened");
        }
    }

    private <T> T executeGet(String key, Class<T> returnClass) {
        if (key == null) {
            throw new NullPointerException();
        }

        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                T value = (T) HibernateUtil.getSession().get(returnClass, key);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();

                return value;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> void executeCreate(T object) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().save(object);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> T executeSingletonQuery(Query query, Class<T> returnClass) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                T value = (T) query.uniqueResult();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return value;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> List<T> executeListQuery(Query query, Class<T> returnClass) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                List<T> values = query.list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return values;
            } catch (RuntimeException th) {
                HibernateUtil.commitTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private int executeCountQuery(Query query) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                int count = ((Long) query.iterate().next()).intValue();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();

                return count;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Object> excuteQuery(String requete) {
        List<Object> formsData = new ArrayList<Object>();

        checkSession();

        logger.log(Level.INFO, "[SERVER] Execution requete", new Object[] { requete });

        try {
            try {
                HibernateUtil.beginTransaction();

                Query q = HibernateUtil.getSession().createQuery(requete);
                formsData.addAll(q.list());

                HibernateUtil.commitTransaction();

            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return formsData;
    }

    public List<Chs> executeEdsCriteria(DetachedCriteria criteria) {
        logger.log(Level.INFO, String.format("[SERVER] executeEdsCriteria : criteria=%s", criteria));

        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                List<Chs> values = criteria.getExecutableCriteria(HibernateUtil.getSession()).setMaxResults(MAX_RESULTS).list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return values;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
