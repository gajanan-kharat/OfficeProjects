package com.inetpsa.poc00.domain.genomelcdvcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;

/**
 * The Class GenomeLCDVCode.
 */
@Entity
@Table(name = "COPQTGCO")
public class GenomeLCDVCode extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LCDV_CODE_ID")
	private Long entityId;

	/** The fr label. */
	@Column(name = "FR_LABEL")
	private String frLabel;

	/** The z2 label. */
	@Column(name = "Z2_LABEL")
	private String z2Label;

	/** The code name. */
	@Column(name = "LCDV_CODE_NAME")
	private String codeName;

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate = new Date();

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The genome lcdv code value list. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "genomeLcdvCode")
	private List<GenomeLCDVCodeValue> genomeLcdvCodeValueList = new ArrayList<GenomeLCDVCodeValue>();

	/** The genome lcdv dictionary. */
	@ManyToOne
	@JoinColumn(name = "LCDV_DICTIONARY_ID")
	private GenomeLCDVDictionary genomeLCDVDictionary;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * 
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeLCDVCode() {
		super();
	}

	/**
	 * Instantiates a new genome lcdv code.
	 * 
	 * @param genomeLCDVDictionary the genome lcdv dictionary
	 */
	/*
	* Constructor 
	 * 
	 * @param genomeLCDVDictionary
	 */
	public GenomeLCDVCode(GenomeLCDVDictionary genomeLCDVDictionary) {
		this.genomeLCDVDictionary = genomeLCDVDictionary;
	}

	/**
	 * Modify the last Update Date for all existing Row while updating.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
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
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
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
	 * Gets the fr label.
	 * 
	 * @return the fr label
	 */
	public String getFrLabel() {
		return frLabel;
	}

	/**
	 * Sets the fr label.
	 * 
	 * @param frLabel the new fr label
	 */
	public void setFrLabel(String frLabel) {
		this.frLabel = frLabel;
	}

	/**
	 * Gets the z2 label.
	 * 
	 * @return the z2 label
	 */
	public String getZ2Label() {
		return z2Label;
	}

	/**
	 * Sets the z2 label.
	 * 
	 * @param z2Label the new z2 label
	 */
	public void setZ2Label(String z2Label) {
		this.z2Label = z2Label;
	}

	/**
	 * Gets the code name.
	 * 
	 * @return the code name
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * Sets the code name.
	 * 
	 * @param codeName the new code name
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * Gets the genome lcdv dictionary.
	 * 
	 * @return the genome lcdv dictionary
	 */
	public GenomeLCDVDictionary getGenomeLCDVDictionary() {
		return genomeLCDVDictionary;
	}

	/**
	 * Sets the genome lcdv dictionary.
	 * 
	 * @param genomeLCDVDictionary the new genome lcdv dictionary
	 */
	public void setGenomeLCDVDictionary(GenomeLCDVDictionary genomeLCDVDictionary) {
		this.genomeLCDVDictionary = genomeLCDVDictionary;
	}

	/**
	 * Gets the genome lcdv code value list.
	 * 
	 * @return the genome lcdv code value list
	 */
	public List<GenomeLCDVCodeValue> getGenomeLcdvCodeValueList() {
		return genomeLcdvCodeValueList;
	}

	/**
	 * Sets the genome lcdv code value list.
	 * 
	 * @param genomeLcdvCodeValueList the new genome lcdv code value list
	 */
	public void setGenomeLcdvCodeValueList(List<GenomeLCDVCodeValue> genomeLcdvCodeValueList) {
		this.genomeLcdvCodeValueList = genomeLcdvCodeValueList;
	}

}
