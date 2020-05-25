package com.inetpsa.pv2.infrastructure.finders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.pictoclient.PictoClientAssembler;
import com.inetpsa.pv2.rest.pictoclient.PictoClientFinder;
import com.inetpsa.pv2.rest.pictoclient.PictoClientRepresentation;

/**
 * The Class JpaPictoClientFinder.
 */
public class JpaPictoClientFinder implements PictoClientFinder {

    /** Injecting EntityManager. */
    @Inject
    private EntityManager entityManager;

    /** Injecting PictoClientAssembler. */
    @Inject
    private PictoClientAssembler pictoClientAssembler;

    /** LOGGERS for JpaPictoClientFinder Class. */

    @Logging
    private final Logger logger = LoggerFactory.getLogger(JpaPictoClientFinder.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getPictoById(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PictoClientRepresentation> getPictoById(Long adminId) {
        logger.info("Start ... Getting Pictos By ID in getPictoById method in JpaPictoClientFinder class");
        String contentOfJpqlQuery = "select distinct objectClient from PictoClient  objectClient"
                + " where objectClient.userId.id = :id and objectClient.downloadFlag =0";
        List<PictoClientRepresentation> listPictoClient = new ArrayList<PictoClientRepresentation>();
        logger.info("==========================================================================================================");
        logger.info("######### Start: getting Pictos by ID in getPictoById method of JpaPictoClientFinder class ############ ");
        logger.info("==========================================================================================================");

        try {
            TypedQuery<PictoClient> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
            typedQuery.setParameter("id", adminId);
            List<PictoClient> listPictoId = typedQuery.getResultList();
            if (listPictoId != null && listPictoId.size() >= 0) {
                for (PictoClient pictoClient : listPictoId) {
                    PictoClientRepresentation thickClientRepresentation = new PictoClientRepresentation();
                    pictoClientAssembler.doAssembleDtoFromAggregate(thickClientRepresentation, pictoClient);
                    listPictoClient.add(thickClientRepresentation);
                }
            } else {
                logger.debug("Null List in getPictoById of JPAPictoFinder Class");
            }
            logger.info("Getting Pictos By ID in getPictoById method in JpaPictoClientFinder class completed sucessfully...");

        } catch (Exception e) {
            logger.error("Error in getting Pictos By ID in method doAssembleDtoFromAggregate of JpaPictoClientFinder class ", e);
        }
        return listPictoClient;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getPictoClientInfo(java.lang.Long, java.lang.Long)
     */
    @Override
    @JpaUnit(Config.JPA_UNIT)
    public List<PictoClientRepresentation> getPictoClientInfo(Long userId, Long pictoId) {
        logger.info("Start .. Getting PictoClient in getPictoClientInfo method in JpaPictoClientFinder class ");
        String contentOfJpqlQuery = "select objectClient from PictoClient  objectClient"
                + " where objectClient.userId.id = :usrId and objectClient.pictoId.pictoId= :picId ";

        List<PictoClientRepresentation> listPictoClient = new ArrayList<PictoClientRepresentation>();
        try {
            TypedQuery<PictoClient> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
            typedQuery.setParameter("usrId", userId);
            typedQuery.setParameter("picId", pictoId);
            List<PictoClient> listPictoId = typedQuery.getResultList();
            if (listPictoId != null && listPictoId.size() >= 0) {
                for (PictoClient pictoClient : listPictoId) {
                    PictoClientRepresentation thickClientRepresentation = new PictoClientRepresentation();
                    pictoClientAssembler.doAssembleDtoFromAggregate(thickClientRepresentation, pictoClient);
                    listPictoClient.add(thickClientRepresentation);
                }
            } else {
                logger.debug("Null List in getPictoClientInfo of JPA Picto Class Finder");
            }
            logger.info("Get Picto Info in getPictoClientInfo method in JpaPictoClientFinder class completed sucessfully...");
        } catch (Exception e) {

            logger.error("Error in getting Picto Info in method getPictoClientInfo of JpaPictoClientFinder class ", e);
        }
        return listPictoClient;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getDownloadedPictoInfo(java.lang.Long, java.lang.Long)
     */
    @Override
    @JpaUnit(Config.JPA_UNIT)
    public List<Long> getDownloadedPictoInfo(Long userId, Long pictoId) {

        String contentOfJpqlQuery = "select objectClient from PictoClient  objectClient"
                + " where objectClient.userId.id = :usrId and objectClient.pictoId.pictoId= :picId and objectClient.downloadFlag = :downloadFlag";

        List<Long> entityIdList = new ArrayList<Long>();
        try {

            TypedQuery<PictoClient> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
            typedQuery.setParameter("usrId", userId);
            typedQuery.setParameter("picId", pictoId);
            typedQuery.setParameter("downloadFlag", (byte) 1);
            List<PictoClient> listPictoId = typedQuery.getResultList();
            if (listPictoId != null && listPictoId.size() >= 0) {
                for (PictoClient pictoClient : listPictoId) {
                    entityIdList.add(pictoClient.getEntityId());
                }
            } else {
                logger.debug("Null List in getPictoClientInfo of JPA Picto Class Finder");
            }
            logger.info("Get Picto Info in getPictoClientInfo method in JpaPictoClientFinder class completed sucessfully...");
        } catch (Exception e) {
            logger.error("Error in getting Picto Info in method getPictoClientInfo of JpaPictoClientFinder class ", e);
        }
        return entityIdList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getWorkingAdminList(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<String> getWorkingAdminList(long pictoId) {
        List<String> userIds = null;
        if (pictoId != 0) {
            try {
                logger.info("Start method getWorkingAdminList for getting Admin List in JpaPictoClientFinder class...");

                String contentOfJpqlQuery = "SELECT pictoClient.userId.firstName " + "FROM PictoClient  pictoClient "
                        + "WHERE pictoClient.pictoId.pictoId = ?1";

                Query typedQuery = entityManager.createQuery(contentOfJpqlQuery);
                typedQuery.setParameter(1, pictoId);
                userIds = typedQuery.getResultList();
                return userIds;
            } catch (Exception e) {
                logger.error("Error in getting Admin List in method getWorkingAdminList of JpaPictoClientFinder class ", e);
            }
        }
        return userIds;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getAllPictosById(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PictoClientRepresentation> getAllPictosById(Long userId) {
        String contentOfJpqlQuery = "select distinct objectClient from PictoClient  objectClient" + " where objectClient.userId.id = :id";
        List<PictoClientRepresentation> listPictoClient = new ArrayList<PictoClientRepresentation>();
        if (userId != null) {
            try {
                logger.info("Start method getAllPictosById for getting all Pictos By ID in JpaPictoClientFinder class...");
                TypedQuery<PictoClient> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
                typedQuery.setParameter("id", userId);
                List<PictoClient> listPictoId = typedQuery.getResultList();
                for (PictoClient pictoClient : listPictoId) {
                    PictoClientRepresentation thickClientRepresentation = new PictoClientRepresentation();
                    pictoClientAssembler.doAssembleDtoFromAggregate(thickClientRepresentation, pictoClient);
                    listPictoClient.add(thickClientRepresentation);
                }
                logger.info("Getting all Pictos By ID in getAllPictosById method in JpaPictoClientFinder class completed sucessfully...");
            } catch (Exception e) {
                logger.error("Error in getting all Pictos in method getAllPictosById of JpaPictoClientFinder class ", e);

            }
        } else {
            logger.debug("Null User ID in method argument getAllPictosById  of JpaPictoClientFinder class");
        }
        return listPictoClient;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictoclient.PictoClientFinder#getDownloadAIList()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PictoClientRepresentation> getDownloadAIList() {
        List<PictoClientRepresentation> pictoClient = new ArrayList<PictoClientRepresentation>();
        try {
            logger.info("Start method getWorkingAdminList for getting Admin List in JpaPictoClientFinder class...");

            String contentOfJpqlQuery = "SELECT pictoClient  FROM PictoClient  pictoClient ";
            TypedQuery<PictoClient> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
            List<PictoClient> listPictoId = typedQuery.getResultList();
            if (listPictoId != null && listPictoId.size() >= 0) {
                for (PictoClient pictoClient1 : listPictoId) {
                    PictoClientRepresentation thickClientRepresentation = new PictoClientRepresentation();
                    pictoClientAssembler.doAssembleDtoFromAggregate(thickClientRepresentation, pictoClient1);
                    pictoClient.add(thickClientRepresentation);
                }
            }
        } catch (Exception e) {
            logger.error("Error in while getting Download AI file ", e);
        }
        return pictoClient;
    }
}
