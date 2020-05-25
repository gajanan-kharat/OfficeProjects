/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.application.category;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.category.Category;

/**
 * The Interface CategoryService.
 */
@Service
public interface CategoryService {

	/**
	 * Save category.
	 *
	 * @param category the category
	 * @return the category
	 */
	public Category saveCategory(Category category);

	/**
	 * Delete category.
	 *
	 * @param categoryId the category id
	 * @param claszLabel the clasz label
	 * @return true, if successful
	 */
	public boolean deleteCategory(Long categoryId, String claszLabel);

}
