/*
 * Creation : Sep 21, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.domain.typeoftest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@Entity
@Table(name = "COPQTTOT")
public class TypeOfTest extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The test nature id */
	@ManyToOne
	@JoinColumn(name = "TEST_NATURE_ID")
	private TestNature testNature;

	/** The vehicle file list. */
	@OneToMany(mappedBy = "typeOfTest")
	private List<VehicleFile> vehicleFileList;

	/** The archive box list. */
	@OneToMany(mappedBy = "typeOfTest")
	private List<ArchiveBox> archiveBoxList = new ArrayList<>();

	/**
	 * Instantiates a new typeOfTest.
	 */
	public TypeOfTest() {
		// Default Constructor
	}

	/**
	 * Instantiates a new typeOfTest.
	 * 
	 * @param label the label
	 */
	public TypeOfTest(String label) {
		this.label = label;
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
	 * Getter label
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter label
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Getter testNature
	 * 
	 * @return the testNature
	 */
	public TestNature getTestNature() {
		return testNature;
	}

	/**
	 * Setter testNature
	 * 
	 * @param testNature the testNature to set
	 */
	public void setTestNature(TestNature testNature) {
		this.testNature = testNature;
	}

	/**
	 * Getter vehicleFileList
	 * 
	 * @return the vehicleFileList
	 */
	public List<VehicleFile> getVehicleFileList() {
		return vehicleFileList;
	}

	/**
	 * Setter vehicleFileList
	 * 
	 * @param vehicleFileList the vehicleFileList to set
	 */
	public void setVehicleFileList(List<VehicleFile> vehicleFileList) {
		this.vehicleFileList = vehicleFileList;
	}

	/**
	 * Getter archiveBoxList
	 * 
	 * @return the archiveBoxList
	 */
	public List<ArchiveBox> getArchiveBoxList() {
		return archiveBoxList;
	}

	/**
	 * Setter archiveBoxList
	 * 
	 * @param archiveBoxList the archiveBoxList to set
	 */
	public void setArchiveBoxList(List<ArchiveBox> archiveBoxList) {
		this.archiveBoxList = archiveBoxList;
	}

}
