/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.infrastructure.data.category;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.superCategory.SuperCategoryDTO;

/**
 * @author sanjayme
 */
public class CategoryDTO {
    private Long id;
    private String name;
    private SuperCategoryDTO superCategory;

    /**
     * 
     * @return
     */
    @MatchingEntityId
    @MatchingFactoryParameter(index = 0)
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    @MatchingFactoryParameter(index = 1)
    public String getName() {
        return name;
    }
	/**
	 * 
	 * @param name
	 */
    public void setName(String name) {
        this.name = name;
    }


	/**
	 * @return the superCategory
	 */
    @MatchingFactoryParameter(index = 2)
	public SuperCategoryDTO getSuperCategory() {
		return superCategory;
	}

	/**
	 * @param superCategory the superCategory to set
	 */
	public void setSuperCategory(SuperCategoryDTO superCategory) {
		this.superCategory = superCategory;
	}
}
