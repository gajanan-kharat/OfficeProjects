package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.finalreduction.FinalReductionFinder;
import com.inetpsa.poc00.rest.finalreduction.FinalReductionRepresentation;

/**
 * The Class JPAFinalReductionFinder.
 */
public class JPAFinalReductionFinder implements FinalReductionFinder {

	  /** The entity manager. */
    @Inject
    private EntityManager entityManager;
	
	/** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.finalreduction.FinalReductionFinder#findAllFinalReductionData()
     */
    /*
     * @see com.inetpsa.poc00.rest.FinalReduction.FinalReductionFinder#findAllFinalReductionData() get FinalReduction Data
     */
    @Override
    public List<FinalReductionRepresentation> findAllFinalReductionData() {

        logger.info("Fetching All Final Reduction Ration");
        TypedQuery<FinalReductionRepresentation> query = entityManager.createQuery(
                "select new " + FinalReductionRepresentation.class.getName()
                        + "(t1.lcdvCodeValue,t1.kmat,t1.entityId,t1.genomeLcdvCodeValue.frLabel,fr.label,fr.entityId)"
                        + " from GenomeTVVRule t1 left join t1.finalReductionRatio fr"
                        + " where t1.lcdvCodeName='DCW' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc",
                FinalReductionRepresentation.class);

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.finalreduction.FinalReductionFinder#findFinalReductionRatioDataByLabel(java.lang.String)
     */
    @Override
    public List<FinalReductionRepresentation> findFinalReductionRatioDataByLabel(String finalReductionlabel) {
   
    	logger.info("Fetching Final Reduction Ration by Label");

        TypedQuery<FinalReductionRepresentation> query = entityManager.createQuery("select new " + FinalReductionRepresentation.class.getName()
                + "(fr.entityId) from FinalReductionRatio fr where fr.label='" + finalReductionlabel + "'", FinalReductionRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.finalreduction.FinalReductionFinder#FinalReductionfromKmat(java.util.List)
     */
    @Override
    public List<FinalReductionRepresentation> getAllFinalReductionForTvv(List<String> kmatList) {
        logger.info("Fetching All FRR for TVV");
        TypedQuery<FinalReductionRepresentation> query = entityManager.createQuery(
                "select new " + FinalReductionRepresentation.class.getName()
                        + "(fr.label,fr.entityId) from FinalReductionRatio fr  where fr.genomeTvvRule.kmat in (:kmatList)",
                FinalReductionRepresentation.class);
        query.setParameter("kmatList", kmatList);
        return query.getResultList();
    }

    @Override
    public List<FinalReductionRepresentation> getAllFinalReductionData() {
        logger.info("Get All FRR Data");
        TypedQuery<FinalReductionRepresentation> query = entityManager.createQuery("select new " + FinalReductionRepresentation.class.getName()
                + "(fr.label,fr.entityId) from FinalReductionRatio fr ORDER BY fr.label asc ", FinalReductionRepresentation.class);

        return query.getResultList();
    }
}