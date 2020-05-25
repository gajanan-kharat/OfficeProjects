package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.supercategory.SuperCategory;
import org.seedstack.pv2.domain.supercategory.SuperCategoryRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class SuperCategoryJpaRepository.
 */
public class SuperCategoryJpaRepository extends BaseJpaRepository<SuperCategory, Long> implements SuperCategoryRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(SuperCategoryJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.supercategory.SuperCategoryRepository#saveCategory(org.seedstack.pv2.domain.supercategory.SuperCategory)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SuperCategory saveCategory(SuperCategory sup) {
		return super.save(sup);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.supercategory.SuperCategoryRepository#persistCategory(org.seedstack.pv2.domain.supercategory.SuperCategory)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistCategory(SuperCategory sup) {
		super.persist(sup);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.supercategory.SuperCategoryRepository#findAllSuperCategoryForName(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SuperCategory findAllSuperCategoryForName(String name) {
		SuperCategory superCategory = null;
		String p_PictoNameQuery = "select distinct objecSuperCategory from SuperCategory  objecSuperCategory " + " where objecSuperCategory.name = :p_name ";
		try {
			TypedQuery<SuperCategory> p_TypedQuery = m_entityManager.createQuery(p_PictoNameQuery, SuperCategory.class);

			p_TypedQuery.setParameter("p_name", name);

			List<SuperCategory> p_ListSuperCategory = p_TypedQuery.getResultList();

			if (p_ListSuperCategory != null && p_ListSuperCategory.size() >= 0) {
				for (SuperCategory l_SuperCategory : p_ListSuperCategory) {
					superCategory = l_SuperCategory;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the super category ", e);
		}

		return superCategory;
	}
}
