package com.inetpsa.poc00.domain.preparationfilestructure;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklist;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;

/**
 * The Class PreparationFileStructure.
 * 
 * @author snehalwa
 * @version 1.0
 * @created 10-Oct-2016 5:15:53 PM
 */
@Entity
@Table(name = "COPQTPFS")
public class PreparationFileStructure extends BaseAggregateRoot<Long> {

	/**
	 * The entity id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	Long entityId;

	/**
	 * The modification date.
	 */
	@Column(name = "MODIFICATION_DATE")
	Date modificationDate;

	/**
	 * The version.
	 */
	@Column(name = "VERSION")
	String version;

	/**
	 * The preparation checklists.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "preparationFileStructure")
	private List<GenericPreparationChecklist> preparationCheckLists = new ArrayList<GenericPreparationChecklist>();

	/** The preparation file list. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "prepFileStructure")
	private List<PreparationFile> preparationFileList = new ArrayList<>();

	/**
	 * Instantiates a new preparation file structure.
	 */
	public PreparationFileStructure() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the preparation check lists.
	 * 
	 * @return the preparation check lists
	 */
	public List<GenericPreparationChecklist> getPreparationCheckLists() {
		return preparationCheckLists;
	}

	/**
	 * Sets the preparation check lists.
	 * 
	 * @param preparationCheckLists the new preparation check lists
	 */
	public void setPreparationCheckLists(List<GenericPreparationChecklist> preparationCheckLists) {
		this.preparationCheckLists = preparationCheckLists;
	}

	/**
	 * Gets the preparation file list.
	 * 
	 * @return the preparation file list
	 */
	public List<PreparationFile> getPreparationFileList() {
		return preparationFileList;
	}

	/**
	 * Sets the preparation file list.
	 * 
	 * @param preparationFileList the new preparation file list
	 */
	public void setPreparationFileList(List<PreparationFile> preparationFileList) {
		this.preparationFileList = preparationFileList;
	}

}