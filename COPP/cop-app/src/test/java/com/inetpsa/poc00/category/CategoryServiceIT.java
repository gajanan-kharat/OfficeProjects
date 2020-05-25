/*
 * Creation : Jan 30, 2017
 */
package com.inetpsa.poc00.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.category.CategoryService;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;

@RunWith(SeedITRunner.class)
public class CategoryServiceIT {
    @Inject
    CategoryRepository categoryRepo;

    @Inject
    CategoryFactory categoryFactory;

    @Inject
    CategoryService categoryService;

    @Inject
    ClaszRepository claszRepo;

    @Inject
    ClaszFactory claszFactory;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveCategory() {
        Category category = categoryFactory.createCategory();
        Category savedCategory = categoryService.saveCategory(category);
        assertNotNull(savedCategory.getEntityId());
        Category updatedCategory = categoryService.saveCategory(savedCategory);
        assertNotNull(updatedCategory.getEntityId());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteCategory() {
        Category category = categoryFactory.createCategory();
        Clasz clasz = claszFactory.createClasz();
        clasz.setLabel("Clasz");
        Clasz savedClasz = claszRepo.saveClasz(clasz);
        Set<Clasz> claszSet = new HashSet<Clasz>();
        claszSet.add(savedClasz);
        category.setClasz(claszSet);
        Category savedCategory = categoryService.saveCategory(category);
        categoryService.deleteCategory(savedCategory.getEntityId(), savedClasz.getLabel());
        Category loadedCategory = categoryRepo.loadCategory(savedCategory.getEntityId());
        assertEquals(loadedCategory, null);

    }

}
