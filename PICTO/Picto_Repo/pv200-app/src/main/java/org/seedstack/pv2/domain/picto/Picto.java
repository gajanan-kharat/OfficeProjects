package org.seedstack.pv2.domain.picto;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
/**
 * @author shrinathn
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.image.ImageType;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;

/**
 * The Class Picto.
 */
@Entity
@Table(name = "PV2QTPIC")
public class Picto extends BaseAggregateRoot<Long> {

    /** The picto id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long pictoId;

    /** Picto Variant. */
    @Column(name = "VARIANT")
    private String variantType;

    /** Picto URL. */
    @Column(name = "URL")
    private String pictoUrl;

    /** Frontage Picto. */
    @Column(name = "IS_FRONTAGE_PICTO")
    private Boolean isFrontagePicto;

    /** Picto Visibility. */
    @Column(name = "IS_VISIBLE")
    private Boolean isVisible;

    /** The picto family ID. */
    /*
     * Picto Family ID
     */
    @ManyToOne
    @JoinColumn(name = "PFM_ID")
    private PictoFamily pictoFamilyID;

    /** Picto Create date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    /** The last update date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    /** Picto Modify date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /** The image location. */
    @Column(name = "IMAGE_LOCATION")
    private String imageLocation;

    /** The version. */
    /*
     * Picto version/suffix
     */
    @Column(name = "VERSION")
    private String version;

    /** The picto client list. */
    // bi-directional many-to-one association to Pv2qtdi
    @OneToMany(mappedBy = "pictoId")
    private List<PictoClient> pictoClientList;

    /** The last modified usr. */
    // bi-directional many-to-one association to Pv2qtusr
    @ManyToOne
    @JoinColumn(name = "LAST_MODIFIED_USR")
    private User lastModifiedUsr;

    /** The last updated usr. */
    // bi-directional many-to-one association to Pv2qtusr
    @ManyToOne
    @JoinColumn(name = "LAST_UPDATED_USR")
    private User lastUpdatedUsr;

    /** The users list shop carts. */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersListShopCarts")
    private List<User> usersListShopCarts;

    /** The image types. */
    @OneToOne(mappedBy = "pictoId", targetEntity = ImageType.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ImageType imageTypes;

    /**
     * Constructor.
     */
    public Picto() {
        // Default constructor
    }

    /**
     * Constructor.
     * 
     * @param pictoID the Picto ID
     * @param pictoFamilyID the PictoFamily
     */

    public Picto(Long pictoID, PictoFamily pictoFamilyID) {
        this.pictoId = pictoID;
        this.pictoFamilyID = pictoFamilyID;
    }

    /**
     * Constructor.
     *
     * @param pictoID the Picto ID
     * @param pictoVariantType the Picto Variant
     * @param pictoUrl the URL
     * @param isFrontagePicto the is frontage picto
     * @param isVisible the is visible
     * @param pictoFamilyID the PictoFamily
     * @param createDate the create date
     * @param modifyDate the modify date
     */

    public Picto(Long pictoID, String pictoVariantType, String pictoUrl, Boolean isFrontagePicto, Boolean isVisible, PictoFamily pictoFamilyID, Date createDate,
            Date modifyDate) {
        super();
        this.pictoId = pictoID;
        this.variantType = pictoVariantType;
        this.pictoUrl = pictoUrl;
        this.isFrontagePicto = isFrontagePicto;
        this.isVisible = isVisible;
        this.pictoFamilyID = pictoFamilyID;
        this.createDate = createDate;
        this.modifyDate = modifyDate;

    }

    /**
     * Gets the entity id.
     *
     * @return the pictoId
     */
    public Long getEntityId() {
        return pictoId;
    }

    /**
     * Sets the entity id.
     *
     * @param pictoId the pictoId to set
     */
    public void setEntityId(Long pictoId) {
        this.pictoId = pictoId;
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
     * Gets the last update date.
     *
     * @return the last update date
     */
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
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
     * Gets the picto family ID.
     *
     * @return the pictoFamilyID
     */
    public PictoFamily getPictoFamilyID() {
        return pictoFamilyID;
    }

    /**
     * Setter familyID.
     *
     * @param pictoFamilyID the new picto family ID
     */

    public void setPictoFamilyID(PictoFamily pictoFamilyID) {
        this.pictoFamilyID = pictoFamilyID;
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
     * Gets the users list shop carts.
     *
     * @return the usersListShopCarts
     */
    public List<User> getUsersListShopCarts() {
        return usersListShopCarts;
    }

    /**
     * Sets the users list shop carts.
     *
     * @param usersListShopCarts the usersListShopCarts to set
     */
    public void setUsersListShopCarts(List<User> usersListShopCarts) {
        this.usersListShopCarts = usersListShopCarts;
    }

    /**
     * Gets the picto client list.
     *
     * @return the picto client list
     */
    public List<PictoClient> getPictoClientList() {
        return this.pictoClientList;
    }

    /**
     * Sets the pv 2 qtdis.
     *
     * @param pictoClientList the new pv 2 qtdis
     */
    public void setPv2qtdis(List<PictoClient> pictoClientList) {
        this.pictoClientList = pictoClientList;
    }

    public User getLastModifiedUsr() {
        return this.lastModifiedUsr;
    }

    /**
     * Sets the last modified usr.
     *
     * @param lastModifiedUsr the new last modified usr
     */
    public void setLastModifiedUsr(User lastModifiedUsr) {
        this.lastModifiedUsr = lastModifiedUsr;
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
    public ImageType getImageTypes() {
        return imageTypes;
    }

    /**
     * Sets the image types.
     *
     * @param imageTypes the imageTypes to set
     */
    public void setImageTypes(ImageType imageTypes) {
        this.imageTypes = imageTypes;
    }

    /**
     * Gets the last updated usr.
     *
     * @return the last updated usr
     */
    public User getLastUpdatedUsr() {
        return this.lastUpdatedUsr;
    }

    /**
     * Sets the last updated usr.
     *
     * @param lastUpdatedUsr the new last updated usr
     */
    public void setLastUpdatedUsr(User lastUpdatedUsr) {
        this.lastUpdatedUsr = lastUpdatedUsr;
    }
}
