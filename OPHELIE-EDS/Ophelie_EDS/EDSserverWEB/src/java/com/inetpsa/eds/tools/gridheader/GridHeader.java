package com.inetpsa.eds.tools.gridheader;

import com.vaadin.ui.Label;

/**
 * This is Vaadin component. Header of table in a grid layout of Vaadin.
 * 
 * @author Geometric Ltd.
 */
public class GridHeader extends Label {
    // PUBLIC
    /**
     * Constant variable that hold int value for top position
     */
    public static final int TOP = 0;
    /**
     * Constant variable that hold int value for left position
     */
    public static final int LEFT = 1;

    /**
     * Parameterized constructor
     * 
     * @param caption Title of header
     */
    public GridHeader(String caption) {
        this(TOP, caption);
    }

    /**
     * Parameterized constructor
     * 
     * @param position Position of header
     * @param caption Title of header
     */
    public GridHeader(int position, String caption) {
        super(caption);
        this.position = position;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param position Position of header
     * @param caption Title of header
     * @param pixelWidth width in Pixel
     */
    public GridHeader(int position, String caption, int pixelWidth) {
        super(caption);
        this.position = position;
        this.pixelWidth = pixelWidth;
        init();
    }

    // PROTECTED
    /**
     * Variable that hold position of header value
     */
    protected int position;
    // PRIVATE
    /**
     * Variable that hold pixel width of header value
     */
    private int pixelWidth = -1;

    /**
     * Initialize header of table in grid layout.
     */
    private void init() {
        setStyleName("validation-header");

        switch (position) {
        case TOP:
            addStyleName("validation-header-top");
            break;
        case LEFT:
            addStyleName("validation-header-left");
            break;
        }

        if (pixelWidth == -1) {
            setSizeFull();
        } else {
            setWidth(pixelWidth, UNITS_PIXELS);
            setHeight("100%");
        }
    }
}
