package com.inetpsa.poc00.rest.country;

/**
 * The Class CountryRepresentation.
 */
public class CountryRepresentation {

    /** The country lable. */
    private String countryLable;

    /** The entity id. */
    private Long entityId;

    private boolean duplicate;
    /**
     * Flag for saving data
     */
    private boolean edited = false;
    
    /**
     * Instantiates a new country representation.
     */
    public CountryRepresentation() {
    	super();
    }

    /**
     * Instantiates a new country representation.
     * 
     * @param lable the lable
     * @param tvvRuleId the tvv rule id
     * @param kmat the kmat
     * @param frLabel the FR lable
     * @param countryLable the country lable
     * @param entityId the entity id
     */
    public CountryRepresentation(String countryLable, Long entityId) {

        this.countryLable = countryLable;
        this.entityId = entityId;
    }

    /**
     * Instantiates a new country representation.
     * 
     * @param entityId the entity id
     * @param label the label
     */
    public CountryRepresentation(Long entityId, String label) {

        this.entityId = entityId;

        this.countryLable = label;

    }

    /**
     * Instantiates a new country representation.
     * 
     * @param entityId the entity id
     */
    public CountryRepresentation(Long entityId) {

        this.entityId = entityId;

    }

    /**
     * Gets the country lable.
     * 
     * @return the country lable
     */
    public String getCountryLable() {
        return countryLable;
    }

    /**
     * Sets the country lable.
     * 
     * @param countryLable the new country lable
     */
    public void setCountryLable(String countryLable) {
        this.countryLable = countryLable;
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

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

}
