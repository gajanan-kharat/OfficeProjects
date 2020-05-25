package com.inetpsa.eds.application.actionbar;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import java.util.Collection;

/**
 * This container is the action bar. It allows to set the buttons to display.
 * 
 * @author Geometric Ltd.
 */
public class EdsActionBar extends HorizontalLayout {
    // PUBLIC
    /**
     * Constant variable to hold value of ACTION_BAR_BUTTON_STYLE.
     */
    public static final String ACTION_BAR_BUTTON_STYLE = "eds-action-bar";

    /**
     * Public parameterized constructor
     * 
     * @param controller Eds Application Controller
     */
    public EdsActionBar(EdsApplicationController controller) {
        init();
    }

    /**
     * This method set left buttons.
     * 
     * @param buttons variable that holds collection of Button Class
     */
    public void setLeftButtons(Collection<Button> buttons) {
        for (Button button : buttons) {
            addLeftButton(button);
        }
    }

    /**
     * This method add left buttons.
     * 
     * @param button Variable of Button class
     */

    public void addLeftButton(Button button) {
        leftLayout.addComponent(button);
    }

    /**
     * This method add left buttons with index.
     * 
     * @param button Variable of Button class
     * @param index Position of button
     */

    public void addLeftButton(Button button, int index) {
        leftLayout.addComponent(button, index);
    }

    /**
     * This method set right button.
     * 
     * @param buttons variable that holds Collection of Button Class
     */
    public void setRightButtons(Collection<Button> buttons) {
        for (Button button : buttons) {
            addRightButton(button);
        }
    }

    /**
     * This method add right button.
     * 
     * @param button Variable of Button class
     */
    public void addRightButton(Button button) {
        rightLayout.addComponent(button);
        rightLayout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
    }

    /**
     * This method add right button with index.
     * 
     * @param button Variable of Button class
     * @param index position of button
     */
    public void addRightButton(Button button, int index) {
        rightLayout.addComponent(button, index);
        rightLayout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
    }

    /**
     * This method clear the window
     */
    public void clear() {
        leftLayout.removeAllComponents();
        rightLayout.removeAllComponents();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable that holds left HorizontalLayout value
     */
    private HorizontalLayout leftLayout;
    /**
     * Variable that holds right HorizontalLayout value
     */
    private HorizontalLayout rightLayout;

    /**
     * initialize Eds action bar
     */
    private void init() {
        setWidth("100%");
        this.leftLayout = new HorizontalLayout();
        leftLayout.setSpacing(true);
        leftLayout.setHeight("100%");
        this.rightLayout = new HorizontalLayout();
        rightLayout.setSpacing(true);
        rightLayout.setSizeFull();
        this.addComponent(leftLayout);
        this.addComponent(rightLayout);
        setStyleName(EdsActionBar.ACTION_BAR_BUTTON_STYLE);
        setMargin(true, true, false, true);
    }
}
