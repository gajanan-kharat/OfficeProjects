package com.inetpsa.poc00.infrastructure.jpa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.engine.EngineFinder;
import com.inetpsa.poc00.rest.engine.EngineRepresentation;

/**
 * The Class JPAEngineFinder.
 */
public class JPAEngineFinder implements EngineFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * @see com.inetpsa.poc00.rest.Engine.EngineFinder#findAllMotureData() get Engine Data
     */
    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#findAllMotureData()
     */
    @Override
    public List<EngineRepresentation> findAllMotureData() {

        logger.info("Inside Engine Resource , Method : findAllMotureData()");

        logger.info("Query Execution Started for getting Genome Engine Data, {}", new Date());

        Query query = entityManager
                .createNativeQuery("SELECT " + " VEN.BOF_LCDVCODEVALUE,VEN.DOC_LCDVCODEVALUE,VEN.KMAT,VEN.BOF_FRLABEL,VEN.DOC_FRLABEL"
                        + "  ,ENG1.LABEL,ENG1.POWER_KW,ENG1.POWER_CV,ENG1.TORQUE,ENG1.LABEL_DEROGATION,ENG1.ENGINE_ID,"
                        + " VEN.BOF_ENTITYID,VEN.DOC_ENTITYID,ENG1.FUELTYPE_ID,ENG1.FUELINJECTION_ID "
                        + " FROM COPQTVEN AS VEN LEFT OUTER JOIN COPQTENG AS ENG1 ON VEN.BOF_ENTITYID = ENG1.TVV_RULE_ID_B0C "
                        + " AND VEN.DOC_ENTITYID = ENG1.TVV_RULE_ID_DOC GROUP BY VEN.BOF_LCDVCODEVALUE,VEN.DOC_LCDVCODEVALUE,VEN.KMAT");

        List list = query.getResultList();

        logger.info("Query Execution Ended for getting Genome Engine Data, {}", new Date());

        List<EngineRepresentation> engineDataList = new ArrayList<EngineRepresentation>();

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {

            Object[] resultSet = (Object[]) iterator.next();
            EngineRepresentation engineData = new EngineRepresentation((String) resultSet[0], (String) resultSet[1], (String) resultSet[2],
                    (String) resultSet[3], (String) resultSet[4], (String) resultSet[5], (String) resultSet[6], (String) resultSet[7],
                    (String) resultSet[8], (String) resultSet[9], (BigInteger) resultSet[10], (BigInteger) resultSet[11], (BigInteger) resultSet[12],
                    (Integer) resultSet[13], (BigInteger) resultSet[14]);
            engineDataList.add(engineData);
        }

        logger.info("Wrapping the Data into Representation, {}", new Date());

        return engineDataList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#findEngineDataByLabel(java.lang.String)
     */
    @Override
    public List<EngineRepresentation> findEngineDataByLabel(String label) {

        logger.info("querry running to check if label present");

        TypedQuery<EngineRepresentation> query = entityManager.createQuery("select new " + EngineRepresentation.class.getName()
                + "(eng.engineLabel,eng.entityId) from Engine eng where eng.engineLabel='" + label + "'", EngineRepresentation.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#findAllEngineData()
     */
    @Override
    public List<EngineRepresentation> findAllEngineData() {
        TypedQuery<EngineRepresentation> query = entityManager.createQuery("select new " + EngineRepresentation.class.getName()
                + "(e.entityId,e.engineLabel,e.powerKw,e.powerCv,e.torque,e.genomeTvvRule.lcdvCodeValue,e.genomeTvvRuleDOC.lcdvCodeValue)"
                + " from Engine e", EngineRepresentation.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#findAllEngineNames()
     */
    @Override
    public List<String> findAllEngineNames() {
        TypedQuery<String> query = entityManager.createQuery("select distinct e.engineLabel" + " from Engine e", String.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#getAllEngineData()
     */
    @Override
    public List<EngineRepresentation> getAllEngineData() {
        TypedQuery<EngineRepresentation> query = entityManager.createQuery(
                "select new " + EngineRepresentation.class.getName()
                        + "(eng.engineLabel,eng.entityId,eng.genomeTvvRule.lcdvCodeValue,eng.genomeTvvRuleDOC.lcdvCodeValue) from Engine eng",
                EngineRepresentation.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.engine.EngineFinder#getAllEnginesForTvv(java.lang.String)
     */
    @Override
    public List<EngineRepresentation> getAllEnginesForTvv(List<String> kmatList) {
        TypedQuery<EngineRepresentation> query = entityManager.createQuery(
                "select new " + EngineRepresentation.class.getName()
                        + "(eng.entityId,eng.engineLabel,eng.powerKw,eng.powerCv,eng.torque,eng.genomeTvvRule.lcdvCodeValue,eng.genomeTvvRuleDOC.lcdvCodeValue) from Engine eng where eng.genomeTvvRule.kmat in (:kmatList)",
                EngineRepresentation.class);
        query.setParameter("kmatList", kmatList);
        List<EngineRepresentation> engineData = query.getResultList();

        if (engineData == null || engineData.isEmpty()) {

            TypedQuery<EngineRepresentation> engineQuery = entityManager.createQuery("select new " + EngineRepresentation.class.getName()
                    + "(eng.entityId,eng.engineLabel,eng.powerKw,eng.powerCv,eng.torque,eng.genomeTvvRule.lcdvCodeValue,eng.genomeTvvRuleDOC.lcdvCodeValue) FROM Engine eng "
                    + "WHERE eng.genomeTvvRule.entityId  IN (  SELECT gtv.entityId FROM GenomeTVVRule gtv "
                    + "WHERE gtv.lcdvCodeName = 'B0F' AND gtv.ruleId IN ( "
                    + "SELECT DISTINCT tvv2.ruleId FROM GenomeTVVRule tvv1, GenomeTVVRule tvv2 "
                    + "WHERE tvv1.lcdvCodeName = 'DKC' AND tvv1.kmat in (:kmatList) " + "AND tvv2.lcdvCodeName = 'DKC'  AND tvv2.kmat = 'ZZZZ' ))",
                    EngineRepresentation.class);

            engineQuery.setParameter("kmatList", kmatList);

            return engineQuery.getResultList();
        }

        return engineData;

    }
}
