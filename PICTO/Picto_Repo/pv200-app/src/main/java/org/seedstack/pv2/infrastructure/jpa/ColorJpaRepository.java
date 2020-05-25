package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.color.ColorRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class: Color Jpa Repository.
 */
public class ColorJpaRepository extends BaseJpaRepository<Color, Long> implements ColorRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;

	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(ColorJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.color.ColorRepository#saveColor(org.seedstack.pv2.domain.color.Color)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Color saveColor(Color color) {
		return super.save(color);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.color.ColorRepository#persistColor(org.seedstack.pv2.domain.color.Color)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistColor(Color color) {
		super.persist(color);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.color.ColorRepository#findColorByLable(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Color findColorByLable(String color) {
		Color colorEntity = null;

		String p_ColorQuery = "select distinct c from Color  c " + " where c.color = :col ";
		try {
			TypedQuery<Color> p_TypedQuery = m_entityManager.createQuery(p_ColorQuery, Color.class);
			p_TypedQuery.setParameter("col", color);
			List<Color> p_ListOfColor = p_TypedQuery.getResultList();

			if (p_ListOfColor != null && p_ListOfColor.size() >= 0) {
				for (Color l_Color : p_ListOfColor) {
					colorEntity = l_Color;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the color", e);
		}
		return colorEntity;
	}

}
