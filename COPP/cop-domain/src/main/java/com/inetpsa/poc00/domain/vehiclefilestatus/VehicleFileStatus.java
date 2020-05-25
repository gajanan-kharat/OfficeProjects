package com.inetpsa.poc00.domain.vehiclefilestatus;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class VehicleFileStatus.
 */
@Entity
@Table(name = "COPQTVFS")
public class VehicleFileStatus extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The gui label. */
	@Column(name = "GUI_LABEL")
	private String guiLabel;

	/** The vehicle file. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFileStatus")
	private List<VehicleFile> vehicleFile;

	/** The vehicle file. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "previousVFStatus")
	private List<VehicleFile> previousVehicleFile;

	/**
	 * Gets the entity id.
	 * 
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the gui label.
	 * 
	 * @return the guiLabel
	 */
	public String getGuiLabel() {
		return guiLabel;
	}

	/**
	 * Sets the gui label.
	 * 
	 * @param guiLabel the guiLabel to set
	 */
	public void setGuiLabel(String guiLabel) {
		this.guiLabel = guiLabel;
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
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the vehicle file.
	 *
	 * @return the vehicle file
	 */
	public List<VehicleFile> getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Sets the vehicle file.
	 *
	 * @param vehicleFile the new vehicle file
	 */
	public void setVehicleFile(List<VehicleFile> vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * Getter previousVehicleFile.
	 *
	 * @return the previousVehicleFile
	 */
	public List<VehicleFile> getPreviousVehicleFile() {
		return previousVehicleFile;
	}

	/**
	 * Setter previousVehicleFile.
	 *
	 * @param previousVehicleFile the previousVehicleFile to set
	 */
	public void setPreviousVehicleFile(List<VehicleFile> previousVehicleFile) {
		this.previousVehicleFile = previousVehicleFile;
	}

}
