package org.seedstack.pv2.infrastructure.jpa;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class PictoJpaRepository.
 */
public class PictoJpaRepository extends BaseJpaRepository<Picto, Long> implements PictoRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;

	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(PictoJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#savePicto(org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Picto savePicto(Picto picto) {
		return super.save(picto);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#persistPicto(org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistPicto(Picto picto) {
		super.persist(picto);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#findAllPictoByFamilyID(org.seedstack.pv2.domain.pictofamily.PictoFamily, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Picto findAllPictoByFamilyID(PictoFamily pictoFamilyID, String variant, String version) {
		Picto pictoEntity = null;
		String p_PictoQuery = null;
		if (version == null) {
			p_PictoQuery = "select distinct p from Picto  p " + " where p.pictoFamilyID = :picto_id  and p.variantType= :var and p.version = null";
		} else {
			p_PictoQuery = "select distinct p from Picto  p " + " where p.pictoFamilyID = :picto_id  and p.variantType= :var and p.version= :ver";
		}

		try {
			TypedQuery<Picto> p_TypedQuery = m_entityManager.createQuery(p_PictoQuery, Picto.class);
			p_TypedQuery.setParameter("picto_id", pictoFamilyID);
			p_TypedQuery.setParameter("var", variant);
			if (version != null) {
				p_TypedQuery.setParameter("ver", version);
			}

			List<Picto> p_ListOfPicto = p_TypedQuery.getResultList();

			if (p_ListOfPicto != null && p_ListOfPicto.size() >= 0) {
				for (Picto l_Picto : p_ListOfPicto) {
					pictoEntity = l_Picto;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the Picto ", e);
		}
		return pictoEntity;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#getAllPictos()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<Picto> getAllPictos() {
		List<Picto> allPictos = null;
	
		String l_ContentOfJpqlQuery = "select distinct objectPicto from Picto  objectPicto ";

		try {
			TypedQuery<Picto> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, Picto.class);

			allPictos = l_TypedQuery.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception occure get all pictos ", e);
			allPictos = null;
		}
		return allPictos;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#updateAIFileInfo(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void updateAIFileInfo(User user, Picto picto) {
		// Picto picto = getPictoById(pictoId);
		if (picto != null) {
			picto.setLastUpdatedUsr(user);
			picto.setLastUpdateDate(new Date());
			entityManager.merge(picto).getEntityId();
		}
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.picto.PictoRepository#getPictoById(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Picto getPictoById(Long pictoId) {
		Picto picto = null;
		String l_ContentOfJpqlQuery = "SELECT distinct picto " + "FROM Picto  picto " + "WHERE picto.pictoId = ?1";

		try {
			TypedQuery<Picto> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, Picto.class);
			l_TypedQuery.setParameter(1, pictoId);
			picto = l_TypedQuery.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Exception occure get pictos by id ", e);
			picto = null;
		}
		return picto;
	}
}
