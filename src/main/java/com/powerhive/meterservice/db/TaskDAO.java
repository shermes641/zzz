/*
 * 
 */
package com.powerhive.meterservice.db;

import com.powerhive.meterservice.models.UsageData;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.dispatch.RequestDispatcher;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskDAO.
 */
public class TaskDAO implements RequestDispatcher {
    
    /** The session factory. */
    private final SessionFactory sessionFactory;

    /**
     * Instantiates a new task dao.
     *
     * @param sessionFactory the session factory
     * @param ud the ud
     */
    public TaskDAO(SessionFactory sessionFactory, UsageData[] ud) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the session factory.
     *
     * @return the session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /* (non-Javadoc)
     * @see com.sun.jersey.spi.dispatch.RequestDispatcher#dispatch(java.lang.Object, com.sun.jersey.api.core.HttpContext)
     */
    @Override
    public void dispatch(Object resource, HttpContext context) {
        final Session session = sessionFactory.openSession();
        try {
            configureSession(session);
            ManagedSessionContext.bind(session);
            beginTransaction(session);
            try {
                
                
                
                
                commitTransaction(session);
            } catch (Exception e) {
                rollbackTransaction(session);
                this.<RuntimeException>rethrow(e);
            }
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }
    }

    /**
     * Begin transaction.
     *
     * @param session the session
     */
    private void beginTransaction(Session session) {
            session.beginTransaction();
    }

    /**
     * Configure session.
     *
     * @param session the session
     */
    private void configureSession(Session session) {
        session.setDefaultReadOnly(false);
        session.setCacheMode(null);
        session.setFlushMode(null);
    }

    /**
     * Rollback transaction.
     *
     * @param session the session
     */
    private void rollbackTransaction(Session session) {
            final Transaction txn = session.getTransaction();
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
    }

    /**
     * Commit transaction.
     *
     * @param session the session
     */
    private void commitTransaction(Session session) {
            final Transaction txn = session.getTransaction();
            if (txn != null && txn.isActive()) {
                txn.commit();
            }
    }

    /**
     * Rethrow.
     *
     * @param <E> the element type
     * @param e the e
     * @throws E the e
     */
    @SuppressWarnings("unchecked")
    private <E extends Exception> void rethrow(Exception e) throws E {
        throw (E) e;
    }
}
