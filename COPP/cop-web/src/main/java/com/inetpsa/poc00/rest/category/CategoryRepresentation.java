package com.inetpsa.poc00.rest.category;

import java.util.HashSet;
import java.util.Set;

import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;

/**
 * The Class CategoryRepresentation.
 * 
 * @author ankurp
 */
public class CategoryRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The clasz. */
	private Clasz clasz;

	/** The clasz_ id. */
	private Long clasz_Id;

	/** The clasz_label. */
	private String clasz_label;

	/** The edited. */
	private boolean edited = false;

	private Set<ClaszRepresentation> claszRepresentation = new HashSet<ClaszRepresentation>();

	/**
	 * Instantiates a new category representation.
	 * 
	 */

	public CategoryRepresentation() {
		// Default Constructor
	}

	/**
	 * Instantiates a new category representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 * @param clasz the clasz
	 */
	public CategoryRepresentation(Long entityId, String description, String label, Clasz clasz) {

		this.entityId = entityId;
		this.description = description;
		this.label = label;
		this.clasz = clasz;
	}

	/**
	 * Instantiates a new category representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 */
	public CategoryRepresentation(Long entityId, String description, String label) {

		this.entityId = entityId;
		this.description = description;
		this.label = label;

	}

	/**
	 * Instantiates a new category representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 * @param claszId the clasz id
	 * @param claszlabel the claszlabel
	 */
	public CategoryRepresentation(Long entityId, String description, String label, Long claszId, String claszlabel) {

		this.entityId = entityId;
		this.description = description;
		this.label = label;
		this.clasz = new Clasz(claszId, claszlabel);
		this.clasz_Id = claszId;
		this.clasz_label = claszlabel;
	}

	/**
	 * Instantiates a new category representation.
	 * 
	 * @param entityId the entity id
	 */
	public CategoryRepresentation(Long entityId) {

		this.entityId = entityId;

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
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the clasz.
	 * 
	 * @return the clasz
	 */
	public Clasz getClasz() {
		return clasz;
	}

	/**
	 * Sets the clasz.
	 * 
	 * @param clasz the new clasz
	 */
	public void setClasz(Clasz clasz) {
		this.clasz = clasz;
	}

	/**
	 * Gets the clasz_ id.
	 * 
	 * @return the clasz_ id
	 */
	public Long getClasz_Id() {
		return clasz_Id;
	}

	/**
	 * Sets the clasz_ id.
	 * 
	 * @param clasz_Id the new clasz_ id
	 */
	public void setClasz_Id(Long clasz_Id) {
		this.clasz_Id = clasz_Id;
	}

	/**
	 * Gets the clasz_label.
	 * 
	 * @return the clasz_label
	 */
	public String getClasz_label() {
		return clasz_label;
	}

	/**
	 * Sets the clasz_label.
	 * 
	 * @param clasz_label the new clasz_label
	 */
	public void setClasz_label(String clasz_label) {
		this.clasz_label = clasz_label;
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

	public Set<ClaszRepresentation> getClaszRepresentation() {
		return claszRepresentation;
	}

	public void setClaszRepresentation(Set<ClaszRepresentation> claszRepresentation) {
		this.claszRepresentation = claszRepresentation;
	}

}
