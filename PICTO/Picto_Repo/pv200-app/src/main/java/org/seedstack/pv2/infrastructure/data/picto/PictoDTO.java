package org.seedstack.pv2.infrastructure.data.picto;

import java.util.Date;
import java.util.Set;
import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.image.ImageTypeDTO;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;


/**
 * The Class PictoDTO.
 */
public class PictoDTO {

	/** The id. */
	private Long id;

	/** The variant type. */
	private String variantType;

	/** The picto url. */
	private String pictoUrl;

	/** The is frontage picto. */
	private Boolean isFrontagePicto;

	/** The is visible. */
	private Boolean isVisible;

	/** The family ID. */
	private PictoFamilyDTO familyID;

	/** The create date. */
	private Date createDate;

	/** The modify date. */
	private Date modifyDate;

	/** The image location. */
	private String imageLocation;

	/** The version. */
	// private String validationLevel;
	private String version;

	/** The image types. */
	private Set<ImageTypeDTO> imageTypes;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */

	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the variant type.
	 *
	 * @return the variantType
	 */

	@MatchingFactoryParameter(index = 1)
	public String getVariantType() {
		return variantType;
	}

	/**
	 * Sets the variant type.
	 *
	 * @param variantType the variantType to set
	 */
	public void setVariantType(String variantType) {
		this.variantType = variantType;
	}

	/**
	 * Gets the picto url.
	 *
	 * @return the pictoUrl
	 */
	@MatchingFactoryParameter(index = 2)
	public String getPictoUrl() {
		return pictoUrl;
	}

	/**
	 * Sets the picto url.
	 *
	 * @param pictoUrl the pictoUrl to set
	 */
	public void setPictoUrl(String pictoUrl) {
		this.pictoUrl = pictoUrl;
	}

	/**
	 * Gets the checks if is frontage picto.
	 *
	 * @return the isFrontagePicto
	 */

	@MatchingFactoryParameter(index = 3)
	public Boolean getIsFrontagePicto() {
		return isFrontagePicto;
	}

	/**
	 * Sets the checks if is frontage picto.
	 *
	 * @param isFrontagePicto the isFrontagePicto to set
	 */
	public void setIsFrontagePicto(Boolean isFrontagePicto) {
		this.isFrontagePicto = isFrontagePicto;
	}

	/**
	 * Gets the checks if is visible.
	 *
	 * @return the isVisible
	 */

	@MatchingFactoryParameter(index = 4)
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * Sets the checks if is visible.
	 *
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Gets the family ID.
	 *
	 * @return the familyID
	 */

	@MatchingFactoryParameter(index = 5)
	public PictoFamilyDTO getFamilyID() {
		return familyID;
	}

	/**
	 * Sets the family ID.
	 *
	 * @param familyID the familyID to set
	 */
	public void setFamilyID(PictoFamilyDTO familyID) {
		this.familyID = familyID;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */

	@MatchingFactoryParameter(index = 6)
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the modify date.
	 *
	 * @return the modifyDate
	 */

	@MatchingFactoryParameter(index = 7)
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * Sets the modify date.
	 *
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * Gets the image location.
	 *
	 * @return the imageLocation
	 */
	@MatchingFactoryParameter(index = 9)
	public String getImageLocation() {
		return imageLocation;
	}

	/**
	 * Sets the image location.
	 *
	 * @param imageLocation the imageLocation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	/**
	 * Gets the version.
	 *
	 * @return the validationLevel
	 */
	/*	@MatchingFactoryParameter(index = 10)
		public String getValidationLevel() {
			return validationLevel;
		}*/

	/**
	 * @param validationLevel the validationLevel to set
	 */
	/*	public void setValidationLevel(String validationLevel) {
			this.validationLevel = validationLevel;
		}*/

	/**
	 * @return the version
	 */
	@MatchingFactoryParameter(index = 11)
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the image types.
	 *
	 * @return the imageTypes
	 */
	public Set<ImageTypeDTO> getImageTypes() {
		return imageTypes;
	}

	/**
	 * Sets the image types.
	 *
	 * @param imageTypes the imageTypes to set
	 */
	public void setImageTypes(Set<ImageTypeDTO> imageTypes) {
		this.imageTypes = imageTypes;
	}

}
