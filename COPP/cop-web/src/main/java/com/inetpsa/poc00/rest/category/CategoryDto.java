package com.inetpsa.poc00.rest.category;

import java.util.List;

/**
 * The Class CategoryDto.
 */
public class CategoryDto {

	/** The category representation list. */
	List<CategoryRepresentation> categoryRepresentationList;

	/**
	 * Gets the category representation list.
	 * 
	 * @return the category representation list
	 */
	public List<CategoryRepresentation> getCategoryRepresentationList() {
		return categoryRepresentationList;
	}

	/**
	 * Sets the category representation list.
	 * 
	 * @param categoryRepresentationList the new category representation list
	 */
	public void setCategoryRepresentationList(List<CategoryRepresentation> categoryRepresentationList) {
		this.categoryRepresentationList = categoryRepresentationList;
	}

}
