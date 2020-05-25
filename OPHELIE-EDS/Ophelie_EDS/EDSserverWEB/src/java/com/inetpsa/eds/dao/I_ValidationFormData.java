/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

/**
 * Interface for validation of form data
 * 
 * @author Geometric Ltd.
 */
public interface I_ValidationFormData extends I_FormData {
    // PUBLIC
    /**
     * This method returns status of the preliminary stage
     * 
     * @return the status of the preliminary stage
     */
    public int getPreliminaryStatus();

    /**
     * This method returns status of the robust stage
     * 
     * @return the status of the robust stage
     */
    public int getRobustStatus();

    /**
     * This method returns status of the consolidate stage
     * 
     * @return the status of the consolidate stage
     */
    public int getConsolidatedStatus();

    /**
     * This method returns status of the closed stage
     * 
     * @return the status of the closed stage
     */
    public int getClosedStatus();

    /**
     * This method returns status of the validation stage
     * 
     * @return the status of the validation stage
     */
    public int getValidationStage();

    /**
     * Method to invalidate the stage fenced. Used when switching provider validated uncommitted data.
     */
    public void resetClosedStatus();
    // PROTECTED
    // PRIVATE
}
