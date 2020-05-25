package com.inetpsa.poc00.domain.preparationresult;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;

/**
 * The Class PreparationResult.
 */
@Entity
@Table(name = "COPQTPRT")
public class PreparationResult extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The conformity. */
	@Column(name = "CONFORMITY")
	private Boolean conformity;

	/** The value. */
	@Column(name = "VALUE")
	private String value;

	/** The comment. */
	@Column(name = "COMMENT")
	private String comment;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The data type. */
	@Column(name = "DATA_TYPE")
	private String dataType;

	/** The unit. */
	@Column(name = "UNIT")
	private String unit;

	/** The order. */
	@Column(name = "ORDER_NUMBER")
	private int order;

	/** The help text. */
	@Column(name = "HELP_TEXT")
	private String helpText;

	/** The mandatory. */
	@Column(name = "MANDATORY")
	private Boolean mandatory;

	/** The authorized comment. */
	@Column(name = "AUTHORIZED_COMMENT")
	private Boolean authorizedComment;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATION_DATE")
	private Date updateDate = new Date();

	/** The genome lcdv code. */
	@ManyToOne
	@JoinColumn(name = "PREP_CHECKLIST_ID")
	private PreparationCheckList preparationCheckList;

	/**
	 * Instantiates a new preparation result.
	 */
	public PreparationResult() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
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
	 * Gets the conformity.
	 * 
	 * @return the conformity
	 */
	public Boolean getConformity() {
		return conformity;
	}

	/**
	 * Sets the conformity.
	 * 
	 * @param conformity the new conformity
	 */
	public void setConformity(Boolean conformity) {
		this.conformity = conformity;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the comment.
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 * 
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
     * Sets the last update.
     */
    @PreUpdate
    public void setLastUpdate() {
        this.updateDate = new Date();
    }

	/**
	 * Gets the update date.
	 * 
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return updateDate;
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
	 * Gets the data type.
	 * 
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 * 
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the unit.
	 * 
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Gets the order.
	 * 
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order the new order
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Gets the help text.
	 * 
	 * @return the help text
	 */
	public String getHelpText() {
		return helpText;
	}

	/**
	 * Sets the help text.
	 * 
	 * @param helpText the new help text
	 */
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	/**
	 * Gets the mandatory.
	 * 
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * Sets the mandatory.
	 * 
	 * @param mandatory the new mandatory
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * Gets the authorized comment.
	 * 
	 * @return the authorized comment
	 */
	public Boolean getAuthorizedComment() {
		return authorizedComment;
	}

	/**
	 * Sets the authorized comment.
	 * 
	 * @param authorizedComment the new authorized comment
	 */
	public void setAuthorizedComment(Boolean authorizedComment) {
		this.authorizedComment = authorizedComment;
	}

	/**
	 * Gets the preparation check list.
	 * 
	 * @return the preparation check list
	 */
	public PreparationCheckList getPreparationCheckList() {
		return preparationCheckList;
	}

	/**
	 * Sets the preparation check list.
	 * 
	 * @param preparationCheckList the new preparation check list
	 */
	public void setPreparationCheckList(PreparationCheckList preparationCheckList) {
		this.preparationCheckList = preparationCheckList;
	}

}
