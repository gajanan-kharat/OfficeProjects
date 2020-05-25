package com.inetpsa.eds.application.content;

import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.vaadin.ui.VerticalLayout;

/**
 * This object is used to define the content to display
 * 
 * @author Geometric Ltd.
 */
public class EdsContent extends VerticalLayout {
    // PUBLIC
    /**
     * Default constructor
     */
    public EdsContent() {
        super();
        init();
    }

    /**
     * This method set EDS read view
     * 
     * @param view EDS read form variable
     */
    public void setView(A_EdsReadForm view) {
        removeAllComponents();
        addComponent(view);
        currentView = view;
    }

    /**
     * This method returns EDS in read view
     * 
     * @return EDS read view
     */
    public A_EdsReadForm getView() {
        return currentView;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold A_EdaReadForm value
     */
    private A_EdsReadForm currentView;

    /**
     * Initialize EDS content
     */
    private void init() {
        this.setMargin(true);
    }
}
