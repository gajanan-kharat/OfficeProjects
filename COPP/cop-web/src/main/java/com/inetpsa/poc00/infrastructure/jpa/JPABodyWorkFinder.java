package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.bodywork.BodyWorkFinder;
import com.inetpsa.poc00.rest.bodywork.BodyWorkRepresentation;

/**
 * The Class JPABodyWorkFinder.
 */
public class JPABodyWorkFinder implements BodyWorkFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.bodywork.BodyWorkFinder#findAllSilhouetteData()
     */
    @Override
    public List<BodyWorkRepresentation> findAllSilhouetteData() {

        logger.info("querry running to get BodyWork value");
        TypedQuery<BodyWorkRepresentation> query = entityManager.createQuery(
                "select new " + BodyWorkRepresentation.class.getName()
                        + "(t1.lcdvCodeValue,t1.kmat,t1.genomeLcdvCodeValue.frLabel,t1.entityId,bw.label,bw.entityId) from GenomeTVVRule t1 left join t1.bodywork bw where t1.lcdvCodeName='B0D' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc",
                BodyWorkRepresentation.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.bodywork.BodyWorkFinder#findAllBodyWorkData()
     */

    @Override
    public List<BodyWorkRepresentation> findAllBodyWorkData() {
        TypedQuery<BodyWorkRepresentation> query = entityManager.createQuery("select new " + BodyWorkRepresentation.class.getName()
                + "(bw.entityId,bw.label,bw.genomeTvvRule.lcdvCodeValue)" + " from Bodywork bw", BodyWorkRepresentation.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.bodywork.BodyWorkFinder#getAllBodyWorkNames()
     */

    @Override
    public List<String> getAllBodyWorkNames() {
        TypedQuery<String> query = entityManager.createQuery("select DISTINCT bw.label from Bodywork bw", String.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.bodywork.BodyWorkFinder#findBodyWorkDataByLabel(java.lang.String)
     */

    @Override
    public List<BodyWorkRepresentation> findBodyWorkDataByLabel(String silhoutteLable) {
        logger.info("Query running to check if label present");

        TypedQuery<BodyWorkRepresentation> query = entityManager.createQuery(
                "select new " + BodyWorkRepresentation.class.getName() + "(bw.entityId) from Bodywork bw where bw.label='" + silhoutteLable + "'",
                BodyWorkRepresentation.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.bodywork.BodyWorkFinder#getAllBodyWorkDataForTvv(java.lang.String)
     */
    @Override
    public List<BodyWorkRepresentation> getAllBodyWorkDataForTvv(List<String> kmatList) {
        TypedQuery<BodyWorkRepresentation> query = entityManager.createQuery(
                "select  new " + BodyWorkRepresentation.class.getName()
                        + "(bw.entityId,bw.label,bw.genomeTvvRule.lcdvCodeValue) from Bodywork bw where bw.genomeTvvRule.kmat in (:kmatList) AND bw.genomeTvvRule.lcdvCodeName = 'B0D' ",
                BodyWorkRepresentation.class);
        query.setParameter("kmatList", kmatList);
        return query.getResultList();
    }

}
