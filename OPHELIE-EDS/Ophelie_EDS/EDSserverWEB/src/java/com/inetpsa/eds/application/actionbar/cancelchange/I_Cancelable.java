package com.inetpsa.eds.application.actionbar.cancelchange;

/**
 * This interface acts as a listener for cancel change button.
 * 
 * @author Geometric Ltd.
 */
public interface I_Cancelable {
    // PUBLIC
    /**
     * This method should allow to switch between editing mode to read-only mode without saving changes.
     */
    public void cancel();
}
