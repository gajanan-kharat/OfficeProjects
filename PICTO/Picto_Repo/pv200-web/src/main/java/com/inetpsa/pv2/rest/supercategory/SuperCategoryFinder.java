package com.inetpsa.pv2.rest.supercategory;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.supercategory.SuperCategory;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;

/**
 * The Interface SuperCategoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface SuperCategoryFinder {

	/**
	 * Gets the super category.
	 *
	 * @return the super category
	 */
	List<SuperCategoryRepresentation> getSuperCategory();

	/**
	 * Gets the all category.
	 *
	 * @param id the id
	 * @return the all category
	 */
	List<CategoryRepresentation> getAllCategory(SuperCategory id);

	/**
	 * Gets the categories by super category name.
	 *
	 * @param superCategory the super category
	 * @return the categories by super category name
	 */
	List<CategoryRepresentation> getCategoriesBySuperCategoryName(String superCategory);
}
