package com.inetpsa.poc00.domain.category;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Category.
 */

public interface CategoryRepository extends GenericRepository<Category, Long> {

    /**
     * Saves the Category.
     * 
     * @param object the Category to save
     * @return the Category
     */
    Category saveCategory(Category object);

    /**
     * Delete categories by Id.
     * 
     * @param id the id
     * @return voids
     */

    int deleteCategory(Long id);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return Category count
     */
    long count();

    /**
     * Gets the all categories.
     * 
     * @return the all categories
     */
    List<Category> getAllCategories();

    /**
     * Gets the all categories for copied es.
     * 
     * @param entityId the entity id
     * @return the all categories for copied es
     */
    List<Category> getAllCategoriesForCopiedES(Long entityId);

    /**
     * Gets the all categories for copied fc list.
     * 
     * @param entityId the entity id
     * @return the all categories for copied fc list
     */
    List<Category> getAllCategoriesForCopiedFCList(Long entityId);

    /**
     * Gets the all categories for copied pg list.
     * 
     * @param entityId the entity id
     * @return the all categories for copied pg list
     */
    List<Category> getAllCategoriesForCopiedPGList(Long entityId);

    /**
     * Loaded category.
     *
     * @param categoryId the category id
     * @return the category
     */
    public Category loadCategory(long categoryId);

}
