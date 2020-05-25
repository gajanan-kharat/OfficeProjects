package com.inetpsa.pv2.rest.color;

/**
 * Class: ColorRepresentation.
 */
public class ColorRepresentation {

    /** The id. */
    private Long id;

    /** The color. */
    private String color;

    /** The flag. */
    private boolean flag;

    /** The Order id. */
    private Long orderId;

    /**
     * Instantiates a new color representation.
     */
    public ColorRepresentation() {
        // default constructor
    }

    /**
     * Instantiates a new color representation.
     *
     * @param flag the flag
     */
    public ColorRepresentation(boolean flag) {
        this.flag = flag;
    }

    /**
     * Instantiates a new color representation.
     *
     * @param temp the temp
     */
    public ColorRepresentation(ColorRepresentation temp) {

        this.id = temp.id;
        this.color = temp.color;
        this.flag = Boolean.FALSE;
        this.orderId = temp.orderId;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Checks if is flag.
     *
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * Sets the flag.
     *
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Gets the order id.
     *
     * @return the orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Sets the order id.
     *
     * @param orderId the orderId to set
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}