package com.inetpsa.poc00.rest.category;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.category.Category;

/**
 * The Interface CategoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CategoryFinder {

	/**
	 * Gets the all category.
	 * 
	 * @return the all category
	 */
	public List<CategoryRepresentation> getAllCategory();

	/**
	 * Gets the all categories.
	 * 
	 * @return the all categories
	 */
	public List<CategoryRepresentation> getAllCategories();

	/**
	 * Gets the all categories for es.
	 * 
	 * @param id the id
	 * @return the all categories for es
	 */
	public List<Category> getAllCategoriesForES(long id);

	/**
	 * Gets the all category for fc list.
	 * 
	 * @param entityId
	 *            the entity id
	 * @return the all category for fc list
	 */
	public List<CategoryRepresentation> getAllCategoryForFCList(long entityId);

	/**
	 * Gets the all categories for copied es.
	 * 
	 * @param emsId
	 *            the ems id
	 * @return the all categories for copied es
	 */
	List<Category> getAllCategoriesForCopiedES(long emsId);

	/**
	 * Gets the all categories for copied fc list.
	 * 
	 * @param entityId
	 *            the entity id
	 * @return the all categories for copied fc list
	 */
	List<Category> getAllCategoriesForCopiedFCList(long entityId);

	/**
	 * Gets the all categories for copied pg list.
	 * 
	 * @param entityId
	 *            the entity id
	 * @return the all categories for copied pg list
	 */
	List<Category> getAllCategoriesForCopiedPGList(long entityId);

	/**
	 * Find category data by label.
	 *
	 * @param label            the label
	 * @return the list
	 */
	public List<CategoryRepresentation> findCategoryDataByLabel(String label);

	/**
	 * Find category by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	public List<Category> findCategoryByLabel(String label);

	
	/**
	 * Find category by id.
	 *
	 * @param entityId the entity id
	 * @return the category
	 */
	public Category findCategoryById(Long entityId);

	
	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<Category> getCategories();

	
	/**
	 * Gets the category for tvv.
	 *
	 * @return the category for tvv
	 */
	public List<Category> getCategoryForTvv();

}
