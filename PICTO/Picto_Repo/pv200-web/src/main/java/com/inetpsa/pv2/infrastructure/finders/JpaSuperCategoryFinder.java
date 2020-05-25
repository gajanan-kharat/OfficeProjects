package com.inetpsa.pv2.infrastructure.finders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.supercategory.SuperCategory;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.category.CategoryAssembler;
import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryAssembler;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryFinder;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryRepresentation;

/**
 * The JpaSuperCategoryFinder program implements SuperCategoryFinder that fetches data from Database based on query.
 *
 * @author Geometric Limited
 * @version 1.0
 * @since 2014-03-31
 */

public class JpaSuperCategoryFinder implements SuperCategoryFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(JpaSuperCategoryFinder.class);

    /** The super cat assmblr. */
    @Inject
    private SuperCategoryAssembler superCatAssmblr;

    /** The category assembler. */
    @Inject
    private CategoryAssembler categoryAssembler;

    /*
     * (non-Javadoc) Method to fetch Super Category from Database
     */

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.supercategory.SuperCategoryFinder#getSuperCategory()
     */
    @Override
    public List<SuperCategoryRepresentation> getSuperCategory() {
        logger.info("Start: Query to fetch  Super Category in method getSuperCategory of JpaSuperCategoryFinder class");

        String cntntJplQury = "select distinct objectPic from SuperCategory objectPic";

        List<SuperCategoryRepresentation> listSuperCat = new ArrayList<SuperCategoryRepresentation>();
        try {
            TypedQuery<SuperCategory> typedQuery = entityManager.createQuery(cntntJplQury, SuperCategory.class);
            List<SuperCategory> listSuperCat1 = typedQuery.getResultList();
            if (listSuperCat1 != null && !listSuperCat1.isEmpty()) {
                for (SuperCategory superCat : listSuperCat1) {
                    SuperCategoryRepresentation superCatRepstn = new SuperCategoryRepresentation();
                    superCatAssmblr.doAssembleDtoFromAggregate(superCatRepstn, superCat);
                    listSuperCat.add(superCatRepstn);
                }
            }
            logger.info("Finish: Query to fetch  Super Category in method getSuperCategory of JpaSuperCategoryFinder class");
        } catch (Exception e) {
            logger.error("Exception occure while fetching the super category:", e);
        }
        logger.info(" Finish: return the all super category");
        return listSuperCat;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.supercategory.SuperCategoryFinder#getAllCategory(org.seedstack.pv2.domain.supercategory.SuperCategory)
     */
    @Override
    public List<CategoryRepresentation> getAllCategory(SuperCategory superCategory) {

        logger.info("Start: Query to fetch Category in method getAllCategory of JpaSuperCategoryFinder class");
        String cntntJplQury;
        if (superCategory == null) {
            cntntJplQury = "select distinct objectPic from Category objectPic ";
        } else {
            cntntJplQury = "select distinct objectPic from Category objectPic where objectPic.superCategory= :superCategoryId";
        }
        List<CategoryRepresentation> listCategory = new ArrayList<CategoryRepresentation>();
        try {
            TypedQuery<Category> typedQuery = entityManager.createQuery(cntntJplQury, Category.class);
            if (superCategory != null) {
                typedQuery.setParameter("superCategoryId", superCategory);
            }

            List<Category> listCat = typedQuery.getResultList();
            for (Category category : listCat) {
                CategoryRepresentation categryRepstn = new CategoryRepresentation();
                categoryAssembler.doAssembleDtoFromAggregate(categryRepstn, category);
                listCategory.add(categryRepstn);
            }
            logger.info("Finish: Query to fetch Category in method getAllCategory of JpaSuperCategoryFinder class");
        } catch (Exception e) {
            logger.error("Exception occure while fetching the Category:", e);
        }
        logger.info(" Finish: return the all Category");
        return listCategory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.supercategory.SuperCategoryFinder#getCategoriesBySuperCategoryName(java.lang.String)
     */
    @Override
    public List<CategoryRepresentation> getCategoriesBySuperCategoryName(String superCategory) {

        logger.info("Start: Query to fetch CategoryRepresentation List in method getCategoriesBySuperCategoryName of JpaSuperCategoryFinder class");
        String cntntJplQury;
        if (superCategory == null) {
            cntntJplQury = "select distinct objectPic from Category objectPic ";
        } else {
            cntntJplQury = "select distinct objectPic from Category objectPic where objectPic.superCategory.name= :superCategory";
        }
        List<CategoryRepresentation> listCategory = new ArrayList<CategoryRepresentation>();
        List<Category> listCategory1 = null;
        try {
            TypedQuery<Category> typedQuery = entityManager.createQuery(cntntJplQury, Category.class);
            if (superCategory != null) {
                typedQuery.setParameter("superCategory", superCategory);
            }
            listCategory1 = typedQuery.getResultList();
            if (listCategory1 != null && !listCategory1.isEmpty()) {
                for (Category category : listCategory1) {
                    CategoryRepresentation catgryResrstn = new CategoryRepresentation();
                    categoryAssembler.doAssembleDtoFromAggregate(catgryResrstn, category);
                    listCategory.add(catgryResrstn);
                }
            }
            logger.info(
                    "Finish: Query to fetch CategoryRepresentation List in method getCategoriesBySuperCategoryName of JpaSuperCategoryFinder class");
        } catch (Exception e) {
            logger.error("Exception occure while fetching the Category:", e);
        }
        return listCategory;
    }
}
