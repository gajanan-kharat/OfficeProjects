package com.inetpsa.eds.application.actionbar.abort;

/**
 * This interface behaves as listener for discarding and restoring EDS.
 * 
 * @author Geometric Ltd.
 */
public interface I_Abortable {
    // PUBLIC
    /**
     * Abort EDS
     */
    public void abort();

    /**
     * Unabort EDS
     */
    public void unabort();
}
