package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.type.Type;
import org.seedstack.pv2.domain.type.TypeRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TypeJpaRepository.
 */
public class TypeJpaRepository extends BaseJpaRepository<Type, Long> implements TypeRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(TypeJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.type.TypeRepository#saveType(org.seedstack.pv2.domain.type.Type)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Type saveType(Type type) {
		return super.save(type);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.type.TypeRepository#persistType(org.seedstack.pv2.domain.type.Type)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistType(Type type) {
		super.persist(type);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.type.TypeRepository#findAllTypeByName(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Type findAllTypeByName(String Name) {

		Type typeEntity = null;
		String p_RefNumQuery = "select objectType from Type  objectType " + " where objectType.typeLabel = :type";
		try {
			TypedQuery<Type> p_TypedQuery = m_entityManager.createQuery(p_RefNumQuery, Type.class);

			p_TypedQuery.setParameter("type", Name);

			List<Type> p_ListType = p_TypedQuery.getResultList();

			if (p_ListType != null && p_ListType.size() >= 0) {
				for (Type l_Type : p_ListType) {

					typeEntity = l_Type;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while finding the Type Name:", e);
		}
		return typeEntity;

	}

}
