/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.category;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.supercategory.SuperCategory;

/**
 * Category Factory implementation.
 */
public class CategoryFactoryDefault extends BaseFactory<Category> implements
		CategoryFactory {

	@Override
	public Category createCategory(Long id, String name,
			SuperCategory superCategory) {
		return new Category(id, name, superCategory);
	}
}
