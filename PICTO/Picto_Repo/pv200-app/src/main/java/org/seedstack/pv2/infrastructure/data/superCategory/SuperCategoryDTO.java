/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.infrastructure.data.superCategory;

import java.util.HashSet;
import java.util.Set;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.category.CategoryDTO;


/**
 * The Class SuperCategoryDTO. *
 * 
 */
public class SuperCategoryDTO {
	
	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The categories. */
	private Set<CategoryDTO> categories = new HashSet<CategoryDTO>();

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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@MatchingFactoryParameter(index = 1)
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public Set<CategoryDTO> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the categories to set
	 */
	public void setCategories(Set<CategoryDTO> categories) {
		this.categories = categories;
	}

}
