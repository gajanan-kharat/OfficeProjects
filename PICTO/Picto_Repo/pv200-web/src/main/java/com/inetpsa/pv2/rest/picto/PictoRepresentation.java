package com.inetpsa.pv2.rest.picto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inetpsa.pv2.rest.imagetype.ImageTypeRepresentation;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;
import com.inetpsa.pv2.rest.user.UserRepresentation;

/**
 * The Class PictoRepresentation.
 */
public class PictoRepresentation {

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
    private PictoFamilyRepresentation familyID;

    /** The create date. */
    private Date createDate;

    /** The modify date. */
    private Date modifyDate;

    /** The image location. */
    private String imageLocation;

    /** The version. */
    private String version;

    /** The image types. */
    private ImageTypeRepresentation imageTypes;

    /** The last updated usr. */
    private UserRepresentation lastUpdatedUsr;

    /** The last modified usr. */
    private UserRepresentation lastModifiedUsr;

    /** The last update date. */
    private Date lastUpdateDate;

    /** The picto name. */
    private String pictoName;

    /** The image url. */
    private String imageUrl;

    /** The color flag. */
    private String colorFlag;

    /** The is visible lable. */
    private String isVisibleLable;

    /** The admin name. */
    private String adminName;

    /** The ai file path. */
    private List<Object> aiFilePath = new ArrayList<Object>();

    /** The cart flag. */
    private Boolean cartFlag = false;

    /**
     * Instantiates a new picto representation.
     */
    public PictoRepresentation() {
        // Default Constructor
    }


    /**
     * Instantiates a new picto representation.
     *
     * @param id the id
     * @param variantType the variant type
     * @param isFrontagePicto the is frontage picto
     * @param isVisible the is visible
     * @param imageLocation the image location
     * @param version the version
     * @param picfamilyId the picfamily id
     * @param referenceNum the reference num
     * @param name the name
     * @param informationType the information type
     * @param validationLevel the validation level
     * @param refCharte the ref charte
     */
    public PictoRepresentation(Long id, String variantType, Boolean isFrontagePicto, Boolean isVisible, String imageLocation, String version, Long picfamilyId, String referenceNum,
            String name, String informationType, String validationLevel, String refCharte) {
        this.id = id;
        this.variantType = variantType;
        this.isFrontagePicto=isFrontagePicto;
        this.isVisible = isVisible;
        this.imageLocation = imageLocation;
        this.version=version;
        this.familyID = new PictoFamilyRepresentation(picfamilyId, referenceNum, name, informationType, validationLevel, refCharte);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
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
    public PictoFamilyRepresentation getFamilyID() {
        return familyID;
    }

    /**
     * Sets the family ID.
     *
     * @param familyID the familyID to set
     */
    public void setFamilyID(PictoFamilyRepresentation familyID) {
        this.familyID = familyID;
    }

    /**
     * Gets the creates the date.
     *
     * @return the createDate
     */
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
     * @return the version
     */
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
    public ImageTypeRepresentation getImageTypes() {
        return imageTypes;
    }

    /**
     * Sets the image types.
     *
     * @param imageTypes the imageTypes to set
     */
    public void setImageTypes(ImageTypeRepresentation imageTypes) {
        this.imageTypes = imageTypes;
    }

    /**
     * Gets the last updated usr.
     *
     * @return the last updated usr
     */
    public UserRepresentation getLastUpdatedUsr() {
        return lastUpdatedUsr;
    }

    /**
     * Sets the last updated usr.
     *
     * @param lastUpdatedUsr the new last updated usr
     */
    public void setLastUpdatedUsr(UserRepresentation lastUpdatedUsr) {
        this.lastUpdatedUsr = lastUpdatedUsr;
    }

    /**
     * Gets the last modified usr.
     *
     * @return the last modified usr
     */
    public UserRepresentation getLastModifiedUsr() {
        return lastModifiedUsr;
    }

    /**
     * Sets the last modified usr.
     *
     * @param lastModifiedUsr the new last modified usr
     */
    public void setLastModifiedUsr(UserRepresentation lastModifiedUsr) {
        this.lastModifiedUsr = lastModifiedUsr;
    }

    /**
     * Gets the last update date.
     *
     * @return the last update date
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the last update date.
     *
     * @param lastUpdateDate the new last update date
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getter pictoName.
     *
     * @return the pictoName
     */
    public String getPictoName() {
        return pictoName;
    }

    /**
     * Setter pictoName.
     *
     * @param pictoName the pictoName to set
     */
    public void setPictoName(String pictoName) {
        this.pictoName = pictoName;
    }

    /**
     * Gets the image url.
     *
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image url.
     *
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the color flag.
     *
     * @return the color flag
     */
    public String getColorFlag() {
        return colorFlag;
    }

    /**
     * Sets the color flag.
     *
     * @param colorFlag the new color flag
     */
    public void setColorFlag(String colorFlag) {
        this.colorFlag = colorFlag;
    }

    /**
     * Gets the checks if is visible lable.
     *
     * @return the isVisibleLable
     */
    public String getIsVisibleLable() {
        return isVisibleLable;
    }

    /**
     * Sets the checks if is visible lable.
     *
     * @param isVisibleLable the isVisibleLable to set
     */
    public void setIsVisibleLable(String isVisibleLable) {
        this.isVisibleLable = isVisibleLable;
    }

    /**
     * Gets the admin name.
     *
     * @return the adminName
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * Sets the admin name.
     *
     * @param adminName the adminName to set
     */
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    /**
     * Gets the ai file path.
     *
     * @return the aiFilePath
     */
    public List<Object> getAiFilePath() {
        return aiFilePath;
    }

    /**
     * Sets the ai file path.
     *
     * @param aiFilePath the aiFilePath to set
     */
    public void setAiFilePath(List<Object> aiFilePath) {
        this.aiFilePath = aiFilePath;
    }

    /**
     * Gets the cart flag.
     *
     * @return the cartFlag
     */
    public Boolean getCartFlag() {
        return cartFlag;
    }

    /**
     * Sets the cart flag.
     *
     * @param cartFlag the cartFlag to set
     */
    public void setCartFlag(Boolean cartFlag) {
        this.cartFlag = cartFlag;
    }

}
