/*
 * Creation : Feb 18, 2016
 */
package com.inetpsa.pv2.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Picto POJO class
 * 
 * @author mehaj
 */
public class PictoInformation implements Serializable {
    /*
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Picto Id
     */
    private long pictoId;
    /**
     * Picto Name
     */
    private String pictoName;
    /**
     * Local Image name
     */
    private String picNameInLocalDir;
    /*
     * Last update date for picto
     */
    private String updateDate;
    /**
     * Last updated admin first name
     */
    private String lastUpdateAdmin;
    /**
     * Last updated admin Id
     */
    private String lastUpdateAdminId;
    /**
     * Modified Date
     */
    private String modDate;
    /**
     * Modified Admin Id
     */
    private String lastModAdmin;
    /**
     * picto FamRefNum
     */
    private String picFamRefNum;
    /**
     * pict FamName
     */
    private String picFamName;
    /**
     * variante
     */
    private String variante;
    /**
     * ref Charte
     */
    private String refCharte;

    /**
     * List of admins working on Picto
     */
    private List<String> workingAdminList;
    /**
     * Picto Download date
     */
    private String downloadDate;

    private byte downloadFlag;

    private boolean isOpenLocalImg;
    private String pictoNameExRefCharte;

    /*
     * Picto Selected by Admin Check
     */
    private Boolean isSelected;

    public long getPictoId() {
        return pictoId;
    }

    public void setPictoId(final long pictoId) {
        this.pictoId = pictoId;
    }

    public String getPictoName() {
        return pictoName;
    }

    public void setPictoName(final String pictoName) {
        this.pictoName = pictoName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLastUpdateAdmin() {
        return lastUpdateAdmin;
    }

    public void setLastUpdateAdmin(final String lastUpdateAdmin) {
        this.lastUpdateAdmin = lastUpdateAdmin;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(final String modDate) {
        this.modDate = modDate;
    }

    public String getLastModAdmin() {
        return lastModAdmin;
    }

    public void setLastModAdmin(final String lastModAdmin) {
        this.lastModAdmin = lastModAdmin;
    }

    public List<String> getWorkingAdminList() {
        return workingAdminList;
    }

    public void setWorkingAdminList(final List<String> workingAdminList) {
        this.workingAdminList = workingAdminList;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(final Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(final String downloadDate) {
        this.downloadDate = downloadDate;
    }

    /**
     * Getter picFamRefNum
     * 
     * @return the picFamRefNum
     */
    public String getPicFamRefNum() {
        return picFamRefNum;
    }

    /**
     * Setter picFamRefNum
     * 
     * @param picFamRefNum the picFamRefNum to set
     */
    public void setPicFamRefNum(String picFamRefNum) {
        this.picFamRefNum = picFamRefNum;
    }

    /**
     * Getter picFamName
     * 
     * @return the picFamName
     */
    public String getPicFamName() {
        return picFamName;
    }

    /**
     * Setter picFamName
     * 
     * @param picFamName the picFamName to set
     */
    public void setPicFamName(String picFamName) {
        this.picFamName = picFamName;
    }

    /**
     * Getter picNameInLocalDir
     * 
     * @return the picNameInLocalDir
     */
    public String getPicNameInLocalDir() {
        return picNameInLocalDir;
    }

    /**
     * Setter picNameInLocalDir
     * 
     * @param picNameInLocalDir the picNameInLocalDir to set
     */
    public void setPicNameInLocalDir(String picNameInLocalDir) {
        this.picNameInLocalDir = picNameInLocalDir;
    }

    /**
     * Getter lastUpdateAdminId
     * 
     * @return the lastUpdateAdminId
     */
    public String getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * Setter lastUpdateAdminId
     * 
     * @param lastUpdateAdminId the lastUpdateAdminId to set
     */
    public void setLastUpdateAdminId(String lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }

    /**
     * Getter variante
     * 
     * @return the variante
     */
    public String getVariante() {
        return variante;
    }

    /**
     * Setter variante
     * 
     * @param variante the variante to set
     */
    public void setVariante(String variante) {
        this.variante = variante;
    }

    /**
     * Getter downloadFlag
     * 
     * @return the downloadFlag
     */
    public byte getDownloadFlag() {
        return downloadFlag;
    }

    /**
     * Setter downloadFlag
     * 
     * @param downloadFlag the downloadFlag to set
     */
    public void setDownloadFlag(byte downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    /**
     * Getter isOpenLocalImg
     * 
     * @return the isOpenLocalImg
     */
    public boolean isOpenLocalImg() {
        return isOpenLocalImg;
    }

    /**
     * Setter isOpenLocalImg
     * 
     * @param isOpenLocalImg the isOpenLocalImg to set
     */
    public void setOpenLocalImg(boolean isOpenLocalImg) {
        this.isOpenLocalImg = isOpenLocalImg;
    }

    /**
     * Getter refCharte
     * 
     * @return the refCharte
     */
    public String getRefCharte() {
        return refCharte;
    }

    /**
     * Setter refCharte
     * 
     * @param refCharte the refCharte to set
     */
    public void setRefCharte(String refCharte) {
        this.refCharte = refCharte;
    }

    /**
     * Getter pictoNameExRefCharte
     * 
     * @return the pictoNameExRefCharte
     */
    public String getPictoNameExRefCharte() {
        return pictoNameExRefCharte;
    }

    /**
     * Setter pictoNameExRefCharte
     * 
     * @param pictoNameExRefCharte the pictoNameExRefCharte to set
     */
    public void setPictoNameExRefCharte(String pictoNameExRefCharte) {
        this.pictoNameExRefCharte = pictoNameExRefCharte;
    }

}
