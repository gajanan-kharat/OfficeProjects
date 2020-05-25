package com.inetpsa.eds.application.content.eds;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This is abstract class for reading form
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EdsReadForm extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public A_EdsReadForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * Returns name of form
     * 
     * @return the name of the form that will be displayed in the navigation menu.
     */
    public abstract String getFormName();

    /**
     * Returns title of Form
     * 
     * @return the title of the form to be displayed in the central content.
     */
    public abstract String getFormTitle();

    /**
     * Returns Id of form
     * 
     * @return the unique identifier of the form.
     */
    public abstract String getID();

    /**
     * Returns Eds reference
     * 
     * @return Eds reference
     */
    public abstract String getEdsRef();

    /**
     * Returns Eds associated with form
     * 
     * @return Main Eds associated with the form.
     */
    public abstract EdsEds getEds();

    /**
     * Refreshes the view of DHS data.
     */
    public abstract void refreshViewData();

    /**
     * model form data to update.
     */
    public abstract void refreshItems();

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponentContainer#removeAllComponents()
     */
    @Override
    public void removeAllComponents() {
        super.removeAllComponents();

        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Read form
     */
    private void init() {
        if (EDSTools.convertEmptyStringToNull(getFormName()) != null && controller != null) {
            Label vLBLtitle = new Label(controller.getBundle().getString(getFormTitle()));
            vLBLtitle.setStyleName("h1");

            addComponent(vLBLtitle);
        }
    }
}
