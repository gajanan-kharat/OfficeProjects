/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.pv2.infrastructure.data.category;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.infrastructure.data.superCategory.SuperCategoryDTO;
import org.seedstack.pv2.infrastructure.data.superCategory.SuperCategoryDTOAssembler;

/**
 * @author sanjayme
 */
public class CategoryDTOAssembler extends BaseAssembler<Category, CategoryDTO> {

	@Inject
	private SuperCategoryDTOAssembler m_SuperCategoryDTOAssembler;

	@Override
	protected void doAssembleDtoFromAggregate(CategoryDTO targetDto,
			Category sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		targetDto.setName(sourceAggregate.getName());
		SuperCategoryDTO superCategoryDTO = new SuperCategoryDTO();
		m_SuperCategoryDTOAssembler.assembleDtoFromAggregate(superCategoryDTO,
				sourceAggregate.getSuperCategory());
		targetDto.setSuperCategory(superCategoryDTO);
	}

	@Override
	protected void doMergeAggregateWithDto(Category targetAggregate,
			CategoryDTO sourceDto) {
		// targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setName(sourceDto.getName());

	}
}
