/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.category;

import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.supercategory.SuperCategory;

/**
 * Category Factory interface.
 */
public interface CategoryFactory extends GenericFactory<Category> {
	/**
	 * Factory create method.
	 *
	 * @param id
	 *            the category id
	 * @param name
	 *            the category name
	 * @param superCategory
	 *            the superCategory URL
	 * @return the category
	 */
	Category createCategory(Long id, String name, SuperCategory superCategory);

}
