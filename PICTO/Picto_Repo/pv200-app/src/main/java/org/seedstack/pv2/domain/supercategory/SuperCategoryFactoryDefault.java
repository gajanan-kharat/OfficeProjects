/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.supercategory;

import org.seedstack.business.domain.BaseFactory;

/**
 * SuperCategory Factory implementation.
 */
public class SuperCategoryFactoryDefault extends BaseFactory<SuperCategory>
		implements SuperCategoryFactory {

	@Override
	public SuperCategory createSuperCategory(Long id, String name) {
		return new SuperCategory(id, name);
	}
}
