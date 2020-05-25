package com.inetpsa.eds.application.content.eds.genericdata;

import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * This class is used to create for list of 96k part-numbers.
 * 
 * @author Geometric Ltd.
 */
public class EdsNumber96kEditView extends HorizontalLayout /* implements Button.ClickListener */{

    /**
     * Parameterized constructor.
     * 
     * @param number96k 96k part number to be added.
     */
    public EdsNumber96kEditView(EdsNumber96k number96k) {
        this.number96k = number96k;
        init();
    }

    // PRIVE
    /**
     * Variable to store the part number details.
     */
    private EdsNumber96k number96k;
    /**
     * Label for '96k Part-Number'.
     */
    private Label number96kName;

    /**
     * Button to remove part number.
     */
    // private Button number96kRemove;

    /**
     * Initialization method.
     */
    private void init() {
        setSpacing(true);
        number96kName = new Label(number96k.getNValue());
        addComponent(number96kName);

        /*
         * number96kRemove = new Button(); number96kRemove.setImmediate(true);
         * number96kRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png "));
         * number96kRemove.setStyleName(BaseTheme.BUTTON_LINK); number96kRemove.addListener(this); addComponent(number96kRemove);
         */
    }

    /**
     * This method is used to get the 96k part number details.
     * 
     * @return Part number details.
     */
    public EdsNumber96k getNumber96k() {
        return number96k;
    }

    /**
     * This method is used to set the 96k part number details.
     * 
     * @param number96k part number to be set.
     */
    public void setNumber96k(EdsNumber96k number96k) {
        this.number96k = number96k;
    }
}
