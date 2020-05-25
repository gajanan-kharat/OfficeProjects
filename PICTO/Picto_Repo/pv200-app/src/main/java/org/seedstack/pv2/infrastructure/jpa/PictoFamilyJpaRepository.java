package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PictoFamilyJpaRepository.
 */
public class PictoFamilyJpaRepository extends BaseJpaRepository<PictoFamily, Long> implements PictoFamilyRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(PictoFamilyJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository#savePictoFamily(org.seedstack.pv2.domain.pictofamily.PictoFamily)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PictoFamily savePictoFamily(PictoFamily picFam) {

		return super.save(picFam);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository#persistPictoFamily(org.seedstack.pv2.domain.pictofamily.PictoFamily)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistPictoFamily(PictoFamily picFam) {
		super.persist(picFam);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository#findAllPictoFamilyByRefNumber(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PictoFamily findAllPictoFamilyByRefNumber(String referenceNum) {

		PictoFamily pictoFamily = null;
		String p_RefNumQuery = "select distinct objecPictoFamily from PictoFamily  objecPictoFamily " + " where objecPictoFamily.referenceNum = :ref_num ";
		try {
			TypedQuery<PictoFamily> p_TypedQuery = m_entityManager.createQuery(p_RefNumQuery, PictoFamily.class);
			p_TypedQuery.setParameter("ref_num", referenceNum);
			List<PictoFamily> p_ListPictoFamily = p_TypedQuery.getResultList();
			if (p_ListPictoFamily != null && p_ListPictoFamily.size() >= 0) {
				for (PictoFamily l_PictoFamily : p_ListPictoFamily) {
					pictoFamily = l_PictoFamily;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the Reference Number:", e);
		}
		return pictoFamily;

	}

}
