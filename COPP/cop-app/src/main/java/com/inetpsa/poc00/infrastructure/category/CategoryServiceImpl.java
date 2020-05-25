/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.infrastructure.category;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.category.CategoryService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVRepository;

/**
 * The Class CategoryServiceImpl.
 */
public class CategoryServiceImpl implements CategoryService {

    /** The category repo. */
    @Inject
    CategoryRepository categoryRepo;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The tvv repo. */
    @Inject
    TVVRepository tvvRepo;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.category.CategoryService#saveCategory(com.inetpsa.poc00.domain.category.Category)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Category saveCategory(Category category) {

        if (category.getEntityId() != null) {
            Category categoryOld = categoryRepo.load(category.getEntityId());
            traceService.historyForUpdate(categoryOld, category, ConstantsApp.SPECIFICCOP_SCREEN_ID);
        } else {
            traceService.historyForSave(category, ConstantsApp.SPECIFICCOP_SCREEN_ID);
        }
        return categoryRepo.saveCategory(category);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.category.CategoryService#deleteCategory(java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteCategory(Long categoryId, String claszLabel) {
        Category objToDelete = categoryRepo.load(categoryId);
        Boolean catExistinTvv = false;
        List<TVV> tvvList = tvvRepo.findTvvByCategoryId(objToDelete.getEntityId());
        if (!tvvList.isEmpty())
            catExistinTvv = Boolean.TRUE;

        Set<EmissionStandard> ems = objToDelete.getEmissionStandards();
        Iterator<EmissionStandard> itrEs = ems.iterator();
        Boolean catInEMS = Boolean.FALSE;

        while (itrEs.hasNext()) {
            EmissionStandard temp = itrEs.next();

            for (Category cat : temp.getCategories()) {
                if (cat.getLabel().equalsIgnoreCase(objToDelete.getLabel())) {
                    catInEMS = Boolean.TRUE;
                    break;
                }
            }

            if (catInEMS) {
                break;
            }
        }

        if (!catExistinTvv && !catInEMS) {
            int deletedRows = 0;
            Category objectToDelete = categoryRepo.load(categoryId);
            if (objectToDelete.getClasz().size() > 1) {
                Iterator iterator = objToDelete.getClasz().iterator();
                while (iterator.hasNext()) {
                    Clasz obj = (Clasz) iterator.next();
                    if (obj.getLabel().equalsIgnoreCase(claszLabel)) {
                        iterator.remove();
                    }

                }
                categoryRepo.saveCategory(objToDelete);
            } else {
                categoryRepo.deleteCategory(categoryId);
            }
            return true;
        }

        Iterator<FactorCoefficentList> itr = objToDelete.getFactorCoefficentList().iterator();

        Boolean claszExistsInFCL = false;
        Boolean claszExistsInPGL = false;

        while (catInEMS && itr.hasNext()) {
            FactorCoefficentList obj = itr.next();

            Boolean categoryExists = false;
            Iterator<Category> catIterator = obj.getCategories().iterator();
            while (catIterator.hasNext()) {
                Category c = catIterator.next();
                if (c.getLabel().equalsIgnoreCase(objToDelete.getLabel())) {
                    categoryExists = Boolean.TRUE;
                    break;
                }
            }

            Iterator<Clasz> clzIterator = obj.getClasses().iterator();
            while (categoryExists && clzIterator.hasNext()) {
                Clasz c = clzIterator.next();
                if (c.getLabel().equalsIgnoreCase(claszLabel)) {
                    claszExistsInFCL = Boolean.TRUE;
                    break;
                }
            }

            if (claszExistsInFCL) {
                break;
            }
        }

        Iterator<PollutantGasLimitList> pglItr = objToDelete.getPollutantGasLimitList().iterator();

        while (!claszExistsInFCL && pglItr.hasNext()) {
            PollutantGasLimitList obj = pglItr.next();

            Boolean categoryExists = false;
            Iterator<Category> catIterator = obj.getCategories().iterator();
            while (catIterator.hasNext()) {
                Category c = catIterator.next();
                if (c.getLabel().equalsIgnoreCase(objToDelete.getLabel())) {
                    categoryExists = Boolean.TRUE;
                    break;
                }
            }

            Iterator<Clasz> clzIterator = obj.getClasses().iterator();
            while (categoryExists && clzIterator.hasNext()) {
                Clasz clasz = clzIterator.next();
                if (clasz.getLabel().equalsIgnoreCase(claszLabel)) {
                    claszExistsInPGL = Boolean.TRUE;
                    break;
                }
            }

            if (claszExistsInPGL) {
                break;
            }
        }

        if (!catExistinTvv && !claszExistsInPGL && !claszExistsInFCL) {
            int deletedRows = 0;
            Category objectToDelete = categoryRepo.load(categoryId);
            if (objectToDelete.getClasz().size() > 1) {
                Iterator iterator = objToDelete.getClasz().iterator();
                while (iterator.hasNext()) {
                    Clasz obj = (Clasz) iterator.next();
                    if (obj.getLabel().equalsIgnoreCase(claszLabel)) {
                        iterator.remove();
                    }

                }
                categoryRepo.saveCategory(objToDelete);

            } else {
                deletedRows = categoryRepo.deleteCategory(categoryId);
            }
            if (deletedRows > 0) {

                traceService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                return true;
            }

        }

        return false;
    }

}
