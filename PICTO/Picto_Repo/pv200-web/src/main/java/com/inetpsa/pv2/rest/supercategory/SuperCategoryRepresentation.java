package com.inetpsa.pv2.rest.supercategory;

import java.util.List;
import com.inetpsa.pv2.rest.category.CategoryRepresentation;


/* class: SuperCategoryRepresentation */
public class SuperCategoryRepresentation {
	
	private Long superCategoryId;
	private String name;
	private List<CategoryRepresentation> categories;
	/**
	 * @return the superCategoryId
	 */
	public Long getSuperCategoryId() {
		return superCategoryId;
	}
	/**
	 * @param superCategoryId the superCategoryId to set
	 */
	public void setSuperCategoryId(Long superCategoryId) {
		this.superCategoryId = superCategoryId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the categories
	 */
	public List<CategoryRepresentation> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<CategoryRepresentation> categories) {
		this.categories = categories;
	}

}
