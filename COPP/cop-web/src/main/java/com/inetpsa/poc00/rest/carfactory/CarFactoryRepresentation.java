package com.inetpsa.poc00.rest.carfactory;

/**
 * The Class CarFactoryRepresentation.
 */
public class CarFactoryRepresentation {

    /** The duplicate. */
    private boolean duplicate;
    
    /** The car factory label. */
    private String carFactoryLabel;

    /** The entity id. */
    private Long entityId;

    /** Flag for saving data. */
    private boolean edited = false;
    
    /**
     * Instantiates a new car factory representation.
     */
    public CarFactoryRepresentation() {
    	super();
    }


    /**
     * Instantiates a new car factory representation.
     * 
     * @param tvvRuleId the tvv rule id
     * @param lable the lable
     * @param kmat the kmat
     * @param frLabel the FR lable
     * @param carFactoryLabel the car factory label
     * @param entityId the entity id
     */
    public CarFactoryRepresentation(String carFactoryLabel, Long entityId) {

        this.carFactoryLabel = carFactoryLabel;
        this.entityId = entityId;
    }

    /**
     * Instantiates a new car factory representation.
     * 
     * @param entityId the entity id
     */
    public CarFactoryRepresentation(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new car factory representation.
     *
     * @param carFactoryLabel the car factory label
     */
    public CarFactoryRepresentation(String carFactoryLabel) {
        this.carFactoryLabel = carFactoryLabel;
    }

    /**
     * Gets the car factory label.
     * 
     * @return the car factory label
     */
    public String getCarFactoryLabel() {
        return carFactoryLabel;
    }

    /**
     * Sets the car factory label.
     * 
     * @param carFactoryLabel the new car factory label
     */
    public void setCarFactoryLabel(String carFactoryLabel) {
        this.carFactoryLabel = carFactoryLabel;
    }

    /**
     * Gets the entity id.
     * 
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Checks if is edited.
     * 
     * @return true, if is edited
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * Sets the edited.
     * 
     * @param edited the new edited
     */
    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    /**
     * Checks if is duplicate.
     *
     * @return true, if is duplicate
     */
    public boolean isDuplicate() {
        return duplicate;
    }

    /**
     * Sets the duplicate.
     *
     * @param duplicate the new duplicate
     */
    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

}
