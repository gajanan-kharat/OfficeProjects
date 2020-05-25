/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.supercategory;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of category.
 */
public interface SuperCategoryRepository extends
		GenericRepository<SuperCategory, Long> {

	/**
	 * Saves the category.
	 *
	 * @param superCategory
	 *            the superCategory to save
	 * @return the category
	 */
	SuperCategory saveCategory(SuperCategory superCategory);

	/**
	 * Persists the category.
	 *
	 * @param superCategory
	 *            the superCategory to persist
	 */
	void persistCategory(SuperCategory superCategory);

	public SuperCategory findAllSuperCategoryForName(String name);

}
