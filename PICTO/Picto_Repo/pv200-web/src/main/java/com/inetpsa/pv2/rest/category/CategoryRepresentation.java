package com.inetpsa.pv2.rest.category;

import java.util.List;

import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryRepresentation;

/**
 * The Class CategoryRepresentation.
 */
public class CategoryRepresentation {

    /** The category id. */
    private Long categoryId;

    /** The name. */
    private String name;

    /** The super category. */
    private SuperCategoryRepresentation superCategory;

    /** The category eng. */
    private String categoryEn;

    /** The category fr. */
    private String categoryFr;

    /** The picto family category. */
    private List<PictoFamilyRepresentation> pictoFamilyCategory;

    /**
     * Gets the category id.
     *
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category id.
     *
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the super category.
     *
     * @return the superCategory
     */
    public SuperCategoryRepresentation getSuperCategory() {
        return superCategory;
    }

    /**
     * Sets the super category.
     *
     * @param superCategory the superCategory to set
     */
    public void setSuperCategory(SuperCategoryRepresentation superCategory) {
        this.superCategory = superCategory;
    }

    /**
     * Gets the category English.
     *
     * @return the category English
     */
    public String getCategoryEn() {
        return categoryEn;
    }

    /**
     * Sets the category English.
     *
     * @param categoryEn the new category English
     */
    public void setCategoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
    }

    /**
     * Gets the category French.
     *
     * @return the category French
     */
    public String getCategoryFr() {
        return categoryFr;
    }

    /**
     * Sets the category French.
     *
     * @param categoryFr the new category French
     */
    public void setCategoryFr(String categoryFr) {
        this.categoryFr = categoryFr;
    }

    /**
     * Gets the picto family category.
     *
     * @return the pictoFamilyCategory
     */
    public List<PictoFamilyRepresentation> getPictoFamilyCategory() {
        return pictoFamilyCategory;
    }

    /**
     * Sets the picto family category.
     *
     * @param pictoFamilyCategory the pictoFamilyCategory to set
     */
    public void setPictoFamilyCategory(List<PictoFamilyRepresentation> pictoFamilyCategory) {
        this.pictoFamilyCategory = pictoFamilyCategory;
    }

}
