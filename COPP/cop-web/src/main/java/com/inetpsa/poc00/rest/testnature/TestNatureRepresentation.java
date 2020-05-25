package com.inetpsa.poc00.rest.testnature;

/**
 * The Class TestNatureRepresentation.
 */
public class TestNatureRepresentation {

    /** The entity id. */
    protected long entityId;

    /** The label. */
    private String label;

    /** The type. */
    private String type;

    /** The hierarchy. */
    /* The hierarchy */
    private int hierarchy;

    /**
     * Instantiates a new test nature representation.
     */
    public TestNatureRepresentation() {
    	super();
    }

    /**
     * Instantiates a new test nature representation.
     *
     * @param testNatureId the test nature id
     * @param label the label
     */
    public TestNatureRepresentation(Long testNatureId, String label) {
        this.label = label;
        this.entityId = testNatureId;

    }

    /**
     * Instantiates a new test nature representation.
     *
     * @param entityId the entity id
     * @param label the label
     * @param type the type
     * @param hierarchy the hierarchy
     */
    public TestNatureRepresentation(long entityId, String label, String type, int hierarchy) {

        this.entityId = entityId;
        this.label = label;
        this.type = type;
        this.hierarchy = hierarchy;
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
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter hierarchy.
     *
     * @return the hierarchy
     */
    public int getHierarchy() {
        return this.hierarchy;
    }

    /**
     * Setter hierarchy.
     *
     * @param hierarchy the hierarchy to set
     */
    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

}
