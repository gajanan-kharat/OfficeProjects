package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "Category")
public class CategoryJpaRepository extends BaseJpaRepository<Category, Long> implements CategoryRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(CategoryJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#saveCategory(com.inetpsa.poc00.domain.category.Category)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#saveCategory(com.inetpsa.poc00.domain.category.Category)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Category saveCategory(Category catObj) {

        if (catObj.getEntityId() == null || catObj.getEntityId() == 0)
            return super.save(catObj);

        return entityManager.merge(catObj);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#deleteCategory(java.lang.Long)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#deleteCategory(java.lang.Long)
     */
    @Override
    public int deleteCategory(Long id) {
        try {
            return entityManager.createQuery("DELETE FROM Category c where c.entityId = " + id).executeUpdate();
        } catch (Exception e) {

            logger.error(" Error occured while deleting data ", e);
            return 0;
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#deleteAll()
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#count()
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#getAllCategories()
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.status.StatusRepository#getAllStatus()
     */
    @Override
    public List<Category> getAllCategories() {

        TypedQuery<Category> query = entityManager.createQuery("SELECT Cat FROM Category Cat ", Category.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#getAllCategoriesForCopiedES(java.lang.Long)
     */
    @Override
    public List<Category> getAllCategoriesForCopiedES(Long emsId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTACT C JOIN COPQTMEC L ON (L.CATEGORY_ID = C.ID) JOIN COPQTAES EM ON (EM.ID =  L.EMS_ID) WHERE EM.ID = ?1",
                Category.class);
        query.setParameter(1, emsId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#getAllCategoriesForCopiedFCList(java.lang.Long)
     */
    @Override
    public List<Category> getAllCategoriesForCopiedFCList(Long entityId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTACT C JOIN COPQTMFC L ON (L.CATEGORYID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID = L.FCLISTID) WHERE FCL.ID = ?1",
                Category.class);

        query.setParameter(1, entityId);

        List<Category> result = query.getResultList();

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#getAllCategoriesForCopiedPGList(java.lang.Long)
     */
    @Override
    public List<Category> getAllCategoriesForCopiedPGList(Long entityId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM COPQTACT C JOIN COPQTMPC L ON (L.CAT_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1",
                Category.class);

        query.setParameter(1, entityId);

        List<Category> temp = query.getResultList();

        return temp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.category.CategoryRepository#loadedCategory(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Category loadCategory(long categoryId) {

        return super.load(categoryId);
    }

}