/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.supercategory;

import org.seedstack.business.domain.GenericFactory;

/**
 * SuperCategory Factory interface.
 */
public interface SuperCategoryFactory extends GenericFactory<SuperCategory> {
	/**
	 * Factory create method.
	 *
	 * @param id
	 *            the category id
	 * @param name
	 *            the category name
	 * @return the Super Category
	 */
	SuperCategory createSuperCategory(Long id, String name);

}
