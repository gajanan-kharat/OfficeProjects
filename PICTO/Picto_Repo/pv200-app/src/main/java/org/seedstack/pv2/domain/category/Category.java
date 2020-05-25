/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.category;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.supercategory.SuperCategory;

/**
 * The Class Category.
 */
@Entity
@Table(name = "PV2QTCAT")
public class Category extends BaseAggregateRoot<Long> {

    /** The category id. */
    @Id
    @Column(name = "ID")
    private Long categoryId;

    /** category Name. */
    @Column(name = "CATEGORY")
    private String name;

    /** parent Category. */
    @ManyToOne
    @JoinColumn(name = "SUPER_CAT_ID")
    private SuperCategory superCategory;

    /** Category type name in English. */
    @Column(name = "CATEGORY_EN")
    private String categoryEn;

    /** Category type name in French. */
    @Column(name = "CATEGORY_FR")
    private String categoryFr;

    /** The picto family category. */
    @OneToMany(mappedBy = "categoryID", targetEntity = PictoFamily.class)
    private List<PictoFamily> pictoFamilyCategory;

    /**
     * Constructor in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public Category() {
        // Default constructor
    }

    /**
     * Constructor.
     *
     * @param name the category name
     * @param superCategory the superCategory
     */
    public Category(String name, SuperCategory superCategory) {
        this.name = name;
        this.superCategory = superCategory;
    }

    /**
     * Constructor.
     *
     * @param categoryId the category id
     * @param name the category name
     * @param superCategory the superCategory
     */
    public Category(Long categoryId, String name, SuperCategory superCategory) {
        super();
        this.categoryId = categoryId;
        this.name = name;
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
     * Sets the category eng.
     *
     * @param categoryEn the new category eng
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
     * @param categoryFr the new category fr
     */
    public void setCategoryFr(String categoryFr) {
        this.categoryFr = categoryFr;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
    public Long getEntityId() {
        return categoryId;
    }

    /**
     * Setter entityId.
     *
     * @param entityId the entityId to set
     */
    public void setEntityId(Long entityId) {
        this.categoryId = entityId;
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
    public SuperCategory getSuperCategory() {
        return superCategory;
    }

    /**
     * Sets the super category.
     *
     * @param superCategory the superCategory to set
     */
    public void setSuperCategory(SuperCategory superCategory) {
        this.superCategory = superCategory;
    }

    /**
     * Gets the picto family category.
     *
     * @return the pictoFamilyCategory
     */
    public List<PictoFamily> getPictoFamilyCategory() {
        return pictoFamilyCategory;
    }

    /**
     * Sets the picto family category.
     *
     * @param pictoFamilyCategory the pictoFamilyCategory to set
     */
    public void setPictoFamilyCategory(List<PictoFamily> pictoFamilyCategory) {
        this.pictoFamilyCategory = pictoFamilyCategory;
    }
}
