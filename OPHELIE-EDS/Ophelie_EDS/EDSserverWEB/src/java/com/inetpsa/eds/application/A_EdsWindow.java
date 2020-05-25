package com.inetpsa.eds.application;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

/**
 * This is abstract class to inherit all the main pages of the application.
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EdsWindow extends Window {

    /**
     * PUBLIC default constructor
     */
    public A_EdsWindow() {
        super();
        init();
    }

    /**
     * PUBLIC parameterized constructor
     * 
     * @param caption Caption for Window
     */
    public A_EdsWindow(String caption) {
        super(caption);
        init();
    }

    /**
     * PUBLIC parameterized constructor
     * 
     * @param caption Caption for Window
     * @param content Holds the element which has child elements
     */
    public A_EdsWindow(String caption, ComponentContainer content) {
        super(caption, content);
        init();
    }

    /**
     * This method show warning message.
     * 
     * @param message Warning description
     */
    public void showWarning(String message) {
        showNotification(message, Notification.TYPE_WARNING_MESSAGE);
    }

    /**
     * This method shows warning message with title.
     * 
     * @param title Warning title
     * @param message Warning description
     */
    public void showWarning(String title, String message) {
        showNotification(title, message, Notification.TYPE_WARNING_MESSAGE);
    }

    /**
     * This method shows error message.
     * 
     * @param message error description
     */
    public void showError(String message) {
        showNotification(message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method shows error message with title.
     * 
     * @param title error title
     * @param message error description
     */
    public void showError(String title, String message) {
        showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    // PROTECTED
    // PRIVATE
    /**
     * This method initialize Eds window.
     */
    private void init() {

    }

}
