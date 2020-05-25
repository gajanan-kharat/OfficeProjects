/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.rest.category.CategoryFinder;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;

/**
 * The Class JpaCategoryFinder.
 */
public class JpaCategoryFinder implements CategoryFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The category repository. */
	@Inject
	private CategoryRepository categoryRepository;

	/*
	 * @see com.inetpsa.poc00.rest.Category.CategoryFinder#getAllCategories()
	 */
	@Override
	public List<CategoryRepresentation> getAllCategories() {
		TypedQuery<CategoryRepresentation> query = entityManager.createQuery("select new " + CategoryRepresentation.class.getName() + "(t.entityId, t.description,t.label)" + " from Category t ", CategoryRepresentation.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#findCategoryDataByLabel(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.inetpsa.poc00.rest.category.CategoryFinder#findCategoryDataByLabel
	 * (java.lang.String)
	 */
	@Override
	public List<CategoryRepresentation> findCategoryDataByLabel(String label) {

		TypedQuery<CategoryRepresentation> query = entityManager.createQuery("select new " + CategoryRepresentation.class.getName() + "(f.entityId) from Category f where f.label='" + label + "'", CategoryRepresentation.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForES(
	 * long)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForES(long)
	 */
	/*
	 * @see
	 * com.inetpsa.poc00.rest.Category.CategoryFinder#getAllCategoriesForES(
	 * long)
	 */
	@Override
	public List<Category> getAllCategoriesForES(long emsId) {
		TypedQuery<Category> query = entityManager.createQuery("select t from Category t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, Category.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoryForFCList
	 * (long)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoryForFCList(long)
	 */
	/*
	 * @see
	 * com.inetpsa.poc00.rest.Category.CategoryFinder#getAllCategoryForFCList
	 * (long)
	 */
	@Override
	public List<CategoryRepresentation> getAllCategoryForFCList(long entityId) {
		TypedQuery<CategoryRepresentation> query = entityManager.createQuery("select new " + CategoryRepresentation.class.getName() + "(t.entityId, t.description,t.label,t.clasz.entityId,t.clasz.label)" + " from Category t INNER JOIN t.factorCoefficentList f where f.entityId = " + entityId, CategoryRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForCopiedFCList(long)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#
	 * getAllCategoriesForCopiedFCList(long)
	 */
	@Override
	public List<Category> getAllCategoriesForCopiedFCList(long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTACT C JOIN COPQTMFC L ON (L.CATEGORYID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID = L.FCLISTID) WHERE FCL.ID = ?1", Category.class);

		query.setParameter(1, entityId);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForCopiedPGList(long)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#
	 * getAllCategoriesForCopiedPGList(long)
	 */
	@Override
	public List<Category> getAllCategoriesForCopiedPGList(long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTACT C JOIN COPQTMPC L ON (L.CAT_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1", Category.class);

		query.setParameter(1, entityId);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategory()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategory()
	 */
	/*
	 * @see com.inetpsa.poc00.rest.Category.CategoryFinder#getAllCategory()
	 */
	@Override
	public List<CategoryRepresentation> getAllCategory() {

		TypedQuery<CategoryRepresentation> query = entityManager.createQuery("SELECT  new " + CategoryRepresentation.class.getName() + "(ctg.entityId,ctg.description,ctg.label,c.entityId,c.label) from Category ctg left join ctg.clasz c ", CategoryRepresentation.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForCopiedES(long)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.inetpsa.poc00.rest.category.CategoryFinder#getAllCategoriesForCopiedES
	 * (long)
	 */
	@Override
	public List<Category> getAllCategoriesForCopiedES(long emsId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTACT C JOIN COPQTMEC L ON (L.CATEGORY_ID = C.ID) JOIN COPQTAES EM ON (EM.ID =  L.EMS_ID) WHERE EM.ID = ?1", Category.class);
		query.setParameter(1, emsId);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#findCategoryByLabel(java.lang.String)
	 */
	@Override
	public List<Category> findCategoryByLabel(String label) {

		TypedQuery<Category> query = entityManager.createQuery("select category from Category category where category.label='" + label + "'", Category.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#findCategoryById(java.lang.Long)
	 */
	@Override
	public Category findCategoryById(Long entityId) {
		TypedQuery<Category> query = entityManager.createQuery("select category from Category category where category.entityId=" + entityId, Category.class);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getCategories()
	 */
	@Override
	public List<Category> getCategories() {

		TypedQuery<Category> query = entityManager.createQuery("select category from Category category", Category.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.category.CategoryFinder#getCategoryForTvv()
	 */
	@Override
	public List<Category> getCategoryForTvv() {
		TypedQuery<Category> query = entityManager.createQuery("select category from Category category INNER JOIN category.clasz", Category.class);

		return query.getResultList();
	}

}