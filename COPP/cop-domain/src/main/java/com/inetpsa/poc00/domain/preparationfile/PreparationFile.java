package com.inetpsa.poc00.domain.preparationfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class GenomeTVVRule.
 */
@Entity
@Table(name = "COPQTPRF")
public class PreparationFile extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The modification date. */
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate = new Date();

	/** The preparation file. */
	@OneToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFile;

	/** The genome lcdv code list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "preparationFile")
	private List<PreparationCheckList> preparationCheckList = new ArrayList<>();

	/** The prep file structure. */
	@ManyToOne
	@JoinColumn(name = "PREP_FILE_STRUCTURE_ID")
	private PreparationFileStructure prepFileStructure;

	/** The user prep file. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User userPrepFile;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public PreparationFile() {
		super();
	}

	/**
	 * Sets the last update.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.modificationDate = new Date();
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
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
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
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Gets the vehicle file.
	 * 
	 * @return the vehicle file
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Sets the vehicle file.
	 * 
	 * @param vehicleFile the new vehicle file
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * Gets the preparation check list.
	 * 
	 * @return the preparation check list
	 */
	public List<PreparationCheckList> getPreparationCheckList() {
		return preparationCheckList;
	}

	/**
	 * Sets the preparation check list.
	 * 
	 * @param preparationCheckList the new preparation check list
	 */
	public void setPreparationCheckList(List<PreparationCheckList> preparationCheckList) {
		this.preparationCheckList = preparationCheckList;
	}

	/**
	 * Gets the prep file structure.
	 *
	 * @return the prep file structure
	 */
	public PreparationFileStructure getPrepFileStructure() {
		return prepFileStructure;
	}

	/**
	 * Sets the prep file structure.
	 *
	 * @param prepFileStructure the new prep file structure
	 */
	public void setPrepFileStructure(PreparationFileStructure prepFileStructure) {
		this.prepFileStructure = prepFileStructure;
	}

	/**
	 * Getter userPrepFile.
	 *
	 * @return the userPrepFile
	 */
	public User getUserPrepFile() {
		return userPrepFile;
	}

	/**
	 * Setter userPrepFile.
	 *
	 * @param userPrepFile the userPrepFile to set
	 */
	public void setUserPrepFile(User userPrepFile) {
		this.userPrepFile = userPrepFile;
	}

}
