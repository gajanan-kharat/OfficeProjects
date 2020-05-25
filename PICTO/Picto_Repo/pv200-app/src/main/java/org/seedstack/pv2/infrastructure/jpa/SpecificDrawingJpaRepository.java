package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawingRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class SpecificDrawingJpaRepository.
 */
public class SpecificDrawingJpaRepository extends BaseJpaRepository<SpecificDrawing, Long> implements SpecificDrawingRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(SpecificDrawingJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.specificdrawing.SpecificDrawingRepository#saveSpecificDrawing(org.seedstack.pv2.domain.specificdrawing.SpecificDrawing)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SpecificDrawing saveSpecificDrawing(SpecificDrawing shop) {
		return super.save(shop);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.specificdrawing.SpecificDrawingRepository#persistSpecificDrawing(org.seedstack.pv2.domain.specificdrawing.SpecificDrawing)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistSpecificDrawing(SpecificDrawing spDrwaing) {
		super.persist(spDrwaing);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.specificdrawing.SpecificDrawingRepository#findSpeDrawingByFamilyId(org.seedstack.pv2.domain.pictofamily.PictoFamily, java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SpecificDrawing findSpeDrawingByFamilyId(PictoFamily pictoFamilyID, String name) {
		SpecificDrawing speDrawingEntity = null;

		String p_speDrawingQuery = "select distinct s from SpecificDrawing  s " + " where s.familyId = :drawing_id and s.name=:name";
		try {
			TypedQuery<SpecificDrawing> p_TypedQuery = m_entityManager.createQuery(p_speDrawingQuery, SpecificDrawing.class);
			p_TypedQuery.setParameter("drawing_id", pictoFamilyID);
			p_TypedQuery.setParameter("name", name);
			List<SpecificDrawing> p_ListOfSpdDrawing = p_TypedQuery.getResultList();

			if (p_ListOfSpdDrawing != null && p_ListOfSpdDrawing.size() >= 0) {
				for (SpecificDrawing l_spdDrawing : p_ListOfSpdDrawing) {
					speDrawingEntity = l_spdDrawing;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the SpecificDrawing ", e);
		}
		return speDrawingEntity;
	}
}
