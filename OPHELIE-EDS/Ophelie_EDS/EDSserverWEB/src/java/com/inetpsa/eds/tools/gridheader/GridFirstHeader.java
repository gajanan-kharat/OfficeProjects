package com.inetpsa.eds.tools.gridheader;

/**
 * This is Vaadin component .First header table in a grid layout of Vaadin.
 * 
 * @author Geometric Ltd.
 */
public class GridFirstHeader extends GridHeader {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param caption Title of header
     */
    public GridFirstHeader(String caption) {
        this(TOP, caption);
    }

    /**
     * Parameterized constructor
     * 
     * @param position Position of header
     * @param caption Title of header
     */
    public GridFirstHeader(int position, String caption) {
        super(position, caption);
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param position Position of header
     * @param caption Title of header
     * @param pixelWidth Width of header in pixel
     */
    public GridFirstHeader(int position, String caption, int pixelWidth) {
        super(position, caption, pixelWidth);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Grid first header
     */
    private void init() {
        switch (position) {
        case TOP:
            addStyleName("validation-header-top-first");
            break;
        case LEFT:
            addStyleName("validation-header-left-first");
            break;
        }
    }
}
