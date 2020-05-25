/*
 * Creation : May 31, 2016
 */
package com.inetpsa.pv2.rest.picto;

import java.util.List;

import org.seedstack.pv2.domain.user.User;

/**
 * The Class PictoFilterRepresentation.
 */
public class PictoFilterRepresentation {

    /** The category. */
    private String category;

    /** The date span. */
    private String dateSpan;

    /** The image type. */
    private List<Long> imageType;

    /** The icontheque. */
    private boolean icontheque;

    /** The before date. */
    private boolean beforeDate;

    /** The after date. */
    private boolean afterDate;

    /** The date filter. */
    private String dateFilter;

    /** The fav pictos. */
    private boolean favPictos;

    /** The except fav pictos. */
    private boolean exceptFavPictos;

    /** The visible picto. */
    private boolean visiblePicto;

    /** The invisible pictos. */
    private boolean invisiblePictos;

    /** The in modification pictos. */
    private boolean inModificationPictos;

    /** The except in modification. */
    private boolean exceptInModification;

    /** The picto info. */
    private boolean pictoInfo;

    /** The info in progress. */
    private boolean infoInProgress;

    /** The validated. */
    private boolean validated;

    /** The in progress. */
    private boolean inProgress;

    /** The default filter. */
    private boolean defaultFilter;

    /** The user id. */
    private Long userId;

    /** The user. */
    private User user;

    /** The sort by parameter. */
    private String sortByParameter;

    /** The null type selected. */
    private boolean nullTypeSelected;

    /** The search text. */
    private String searchText;

    /**
     * Sets the category.
     *
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter icontheque.
     *
     * @return the icontheque
     */
    public boolean isIcontheque() {
        return icontheque;
    }

    /**
     * Setter icontheque.
     *
     * @param icontheque the icontheque to set
     */
    public void setIcontheque(boolean icontheque) {
        this.icontheque = icontheque;
    }

    /**
     * Getter dateFilter.
     *
     * @return the dateFilter
     */
    public String getDateFilter() {
        return dateFilter;
    }

    /**
     * Setter dateFilter.
     *
     * @param dateFilter the dateFilter to set
     */
    public void setDateFilter(String dateFilter) {
        this.dateFilter = dateFilter;
    }

    /**
     * Getter favPictos.
     *
     * @return the favPictos
     */
    public boolean isFavPictos() {
        return favPictos;
    }

    /**
     * Setter favPictos.
     *
     * @param favPictos the favPictos to set
     */
    public void setFavPictos(boolean favPictos) {
        this.favPictos = favPictos;
    }

    /**
     * Gets the image type.
     *
     * @return the image type
     */
    public List<Long> getImageType() {
        return imageType;
    }

    /**
     * Getter beforeDate.
     *
     * @return the beforeDate
     */
    public boolean isBeforeDate() {
        return beforeDate;
    }

    /**
     * Setter beforeDate.
     *
     * @param beforeDate the beforeDate to set
     */
    public void setBeforeDate(boolean beforeDate) {
        this.beforeDate = beforeDate;
    }

    /**
     * Getter afterDate.
     *
     * @return the afterDate
     */
    public boolean isAfterDate() {
        return afterDate;
    }

    /**
     * Setter afterDate.
     *
     * @param afterDate the afterDate to set
     */
    public void setAfterDate(boolean afterDate) {
        this.afterDate = afterDate;
    }

    /**
     * Getter exceptFavPictos.
     *
     * @return the exceptFavPictos
     */
    public boolean isExceptFavPictos() {
        return exceptFavPictos;
    }

    /**
     * Setter exceptFavPictos.
     *
     * @param exceptFavPictos the exceptFavPictos to set
     */
    public void setExceptFavPictos(boolean exceptFavPictos) {
        this.exceptFavPictos = exceptFavPictos;
    }

    /**
     * Getter visiblePicto.
     *
     * @return the visiblePicto
     */
    public boolean isVisiblePicto() {
        return visiblePicto;
    }

    /**
     * Setter visiblePicto.
     *
     * @param visiblePicto the visiblePicto to set
     */
    public void setVisiblePicto(boolean visiblePicto) {
        this.visiblePicto = visiblePicto;
    }

    /**
     * Getter invisiblePictos.
     *
     * @return the invisiblePictos
     */
    public boolean isInvisiblePictos() {
        return invisiblePictos;
    }

    /**
     * Setter invisiblePictos.
     *
     * @param invisiblePictos the invisiblePictos to set
     */
    public void setInvisiblePictos(boolean invisiblePictos) {
        this.invisiblePictos = invisiblePictos;
    }

    /**
     * Getter inModificationPictos.
     *
     * @return the inModificationPictos
     */
    public boolean isInModificationPictos() {
        return inModificationPictos;
    }

    /**
     * Setter inModificationPictos.
     *
     * @param inModificationPictos the inModificationPictos to set
     */
    public void setInModificationPictos(boolean inModificationPictos) {
        this.inModificationPictos = inModificationPictos;
    }

    /**
     * Getter exceptInModification.
     *
     * @return the exceptInModification
     */
    public boolean isExceptInModification() {
        return exceptInModification;
    }

    /**
     * Setter exceptInModification.
     *
     * @param exceptInModification the exceptInModification to set
     */
    public void setExceptInModification(boolean exceptInModification) {
        this.exceptInModification = exceptInModification;
    }

    /**
     * Getter pictoInfo.
     *
     * @return the pictoInfo
     */
    public boolean isPictoInfo() {
        return pictoInfo;
    }

    /**
     * Setter pictoInfo.
     *
     * @param pictoInfo the pictoInfo to set
     */
    public void setPictoInfo(boolean pictoInfo) {
        this.pictoInfo = pictoInfo;
    }

    /**
     * Getter infoInProgress.
     *
     * @return the infoInProgress
     */
    public boolean isInfoInProgress() {
        return infoInProgress;
    }

    /**
     * Setter infoInProgress.
     *
     * @param infoInProgress the infoInProgress to set
     */
    public void setInfoInProgress(boolean infoInProgress) {
        this.infoInProgress = infoInProgress;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Getter searchText.
     *
     * @return the searchText
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Setter searchText.
     *
     * @param searchText the searchText to set
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Checks if is null type selected.
     *
     * @return true, if is null type selected
     */
    public boolean isNullTypeSelected() {
        return nullTypeSelected;
    }

    /**
     * Sets the null type selected.
     *
     * @param nullTypeSelected the new null type selected
     */
    public void setNullTypeSelected(boolean nullTypeSelected) {
        this.nullTypeSelected = nullTypeSelected;
    }

    /**
     * Getter validated.
     *
     * @return the validated
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Setter validated.
     *
     * @param validated the validated to set
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    /**
     * Getter inProgress.
     *
     * @return the inProgress
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * Setter inProgress.
     *
     * @param inProgress the inProgress to set
     */
    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    /**
     * Sets the image type.
     *
     * @param imageType the new image type
     */
    public void setImageType(List<Long> imageType) {
        this.imageType = imageType;
    }

    /**
     * Getter defaultFilter.
     *
     * @return the defaultFilter
     */
    public boolean isDefaultFilter() {
        return defaultFilter;
    }

    /**
     * Setter defaultFilter.
     *
     * @param defaultFilter the defaultFilter to set
     */
    public void setDefaultFilter(boolean defaultFilter) {
        this.defaultFilter = defaultFilter;
    }

    /**
     * Gets the date span.
     *
     * @return the date span
     */
    public String getDateSpan() {
        return dateSpan;
    }

    /**
     * Sets the date span.
     *
     * @param dateSpan the new date span
     */
    public void setDateSpan(String dateSpan) {
        this.dateSpan = dateSpan;
    }

    /**
     * Gets the sort by parameter.
     *
     * @return the sort by parameter
     */
    public String getSortByParameter() {
        return sortByParameter;
    }

    /**
     * Sets the sort by parameter.
     *
     * @param sortByParameter the new sort by parameter
     */
    public void setSortByParameter(String sortByParameter) {
        this.sortByParameter = sortByParameter;
    }

}
