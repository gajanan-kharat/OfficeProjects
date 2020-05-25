/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.infrastructure.data.superCategory;

import java.util.Iterator;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.supercategory.SuperCategory;
import org.seedstack.pv2.infrastructure.data.category.CategoryDTO;
import org.seedstack.pv2.infrastructure.data.category.CategoryDTOAssembler;

/**
 * The Class SuperCategoryDTOAssembler.
 */
public class SuperCategoryDTOAssembler extends BaseAssembler<SuperCategory, SuperCategoryDTO> {

    /** The m category DTO assembler. */
    @Inject
    private CategoryDTOAssembler m_CategoryDTOAssembler;

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    protected void doAssembleDtoFromAggregate(SuperCategoryDTO targetDto, SuperCategory sourceAggregate) {
        targetDto.setId(sourceAggregate.getEntityId());
        targetDto.setName(sourceAggregate.getName());
        for (Iterator<Category> iterator = sourceAggregate.getCategories().iterator(); iterator.hasNext();) {
            Category category = iterator.next();
            CategoryDTO categoryDTO = new CategoryDTO();
            m_CategoryDTOAssembler.assembleDtoFromAggregate(categoryDTO, category);
            targetDto.getCategories().add(categoryDTO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(SuperCategory targetAggregate, SuperCategoryDTO sourceDto) {
        targetAggregate.setName(sourceDto.getName());

    }
}
