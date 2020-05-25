/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.application.actionbar.subscribe;

/**
 * This interface acts as a listener to subcribe and unsubscribe the form.
 * 
 * @author Geometric Ltd.
 */
public interface I_Subscribable {
    // PUBLIC
    /**
     * This method must subscribe to a EDS card.
     */
    public void subscribe();

    /**
     * This method must unsubscribe to a EDS card
     */
    public void unsubscribe();
}
