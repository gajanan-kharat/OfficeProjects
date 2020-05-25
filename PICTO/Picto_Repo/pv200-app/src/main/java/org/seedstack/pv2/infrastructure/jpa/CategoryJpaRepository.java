/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.category.CategoryRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA Repository Implementation.
 */

public class CategoryJpaRepository extends BaseJpaRepository<Category, Long> implements CategoryRepository {


	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(CategoryJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.category.CategoryRepository#saveCategory(org.seedstack.pv2.domain.category.Category)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Category saveCategory(Category category) {
		return super.save(category);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.category.CategoryRepository#persistCategory(org.seedstack.pv2.domain.category.Category)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistCategory(Category category) {
		super.persist(category);
	}
	

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.category.CategoryRepository#getAllCategories()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Category> getAllCategories() {
		List<Category> allCategories = null;

		LOGGER.info("Get All Categories");
		String l_ContentOfJpqlQuery = "select distinct objectCategory from Category  objectCategory ";

		try {
			TypedQuery<Category> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, Category.class);

			allCategories = l_TypedQuery.getResultList();

		} catch (Exception e) {
			LOGGER.error(" Exception occured while finding the categories ", e);
			allCategories = null;
		}
		return allCategories;
	}
	

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.category.CategoryRepository#getCategoryById(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Category getCategoryById(Long id) {
		Category category = null;

		LOGGER.info("Get All Categories");
		String l_ContentOfJpqlQuery = "select distinct objectCategory from Category  objectCategory where objectCategory.categoryId = :catId";

		try {
			TypedQuery<Category> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, Category.class);
			l_TypedQuery.setParameter("catId", id);

			category = l_TypedQuery.getSingleResult();

		} catch (Exception e) {
			LOGGER.error(" Exception occured while finding the categories ", e);
			category = null;
		}
		return category;
	}

}
