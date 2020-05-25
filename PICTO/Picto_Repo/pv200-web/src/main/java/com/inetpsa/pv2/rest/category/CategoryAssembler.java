package com.inetpsa.pv2.rest.category;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.supercategory.SuperCategory;

import com.inetpsa.pv2.rest.supercategory.SuperCategoryAssembler;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryRepresentation;

/**
 * The Class CategoryAssembler.
 */
public class CategoryAssembler extends BaseAssembler<Category, CategoryRepresentation> {

    /** The super category assembler. */
    @Inject
    private SuperCategoryAssembler superCategoryAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(CategoryRepresentation targetDto, Category sourceEntity) {
        targetDto.setCategoryId(sourceEntity.getEntityId());
        targetDto.setName(sourceEntity.getName());
        targetDto.setCategoryEn(sourceEntity.getCategoryEn());
        targetDto.setCategoryFr(sourceEntity.getCategoryFr());
        SuperCategory superCat = sourceEntity.getSuperCategory();
        if (superCat != null) {
            SuperCategoryRepresentation superCatRep = new SuperCategoryRepresentation();
            superCategoryAssembler.doAssembleDtoFromAggregate(superCatRep, superCat);
            targetDto.setSuperCategory(superCatRep);
        }
    }

    /**
     * Do merge aggregate with dto.
     *
     * @param targetEntity the p target entity
     * @param sourceDto the p source dto
     */
    @Override
    public void doMergeAggregateWithDto(Category targetEntity, CategoryRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getCategoryId());
        targetEntity.setName(sourceDto.getName());
        targetEntity.setCategoryEn(sourceDto.getCategoryEn());
        targetEntity.setCategoryFr(sourceDto.getCategoryFr());
    }
}
