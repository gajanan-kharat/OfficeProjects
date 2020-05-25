/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.category;


import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of category.
 */
public interface CategoryRepository extends GenericRepository<Category, Long> {

    /**
     * Saves the category.
     *
     * @param category the category to save
     * @return the category
     */
    Category saveCategory(Category category);

    /**
     * Persists the category.
     *
     * @param category the category to persist
     */
    void persistCategory(Category category);
    
    
    List<Category> getAllCategories();
    
    /**
     * Get the category by ID
     * @param id
     * @return
     */
    Category getCategoryById(Long id);

}
