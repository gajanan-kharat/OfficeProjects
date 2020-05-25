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

import com.inetpsa.poc00.rest.gearbox.GearBoxFinder;
import com.inetpsa.poc00.rest.gearbox.GearBoxRepresentation;

/**
 * The Class JPAGearBoxFinder.
 */
public class JPAGearBoxFinder implements GearBoxFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.gearbox.GearBoxFinder#findAllGearBoxData()
     */
    @Override
    public List<GearBoxRepresentation> findAllGearBoxData() {

        logger.info("Inside GearBox Resource , Method : findAllGearBoxData()");

        logger.info("Query Execution Started for getting Genome Gear Box Data, {}", new Date());

        Query query = entityManager.createNativeQuery(
                "SELECT VGB.DBM_LCDVCODEVALUE,VGB.BOG_LCDVCODEVALUE,VGB.KMAT,VGB.DBM_FRLABEL,VGB.BOG_FRLABEL,GBX.LABEL,GBX.TYPE,GBX.GEAR_BOX_ID,"
                        + " VGB.DBM_ENTITYID,VGB.BOG_ENTITYID"
                        + " FROM COPQTVGB VGB LEFT OUTER JOIN COPQTGBX AS GBX ON VGB.DBM_ENTITYID = GBX.TVV_RULE_ID_DBM GROUP BY VGB.DBM_LCDVCODEVALUE,VGB.BOG_LCDVCODEVALUE,VGB.KMAT");

        List list = query.getResultList();

        logger.info("Query Execution Ended for getting Genome Gear Box Data, {}", new Date());

        List<GearBoxRepresentation> gbDataList = new ArrayList<GearBoxRepresentation>();

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {

            Object[] resultSet = (Object[]) iterator.next();
            GearBoxRepresentation gearBoxData = new GearBoxRepresentation((String) resultSet[0], (String) resultSet[1], (String) resultSet[2],
                    (String) resultSet[3], (String) resultSet[4], (String) resultSet[5], (String) resultSet[6], (BigInteger) resultSet[7],
                    (BigInteger) resultSet[8], (BigInteger) resultSet[9]);
            gbDataList.add(gearBoxData);
        }

        logger.info("Wrapping the Data into Representation, {}", new Date());

        return gbDataList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.gearbox.GearBoxFinder#getAllGearBoxData()
     */
    @Override
    public List<GearBoxRepresentation> getAllGearBoxData() {
        TypedQuery<GearBoxRepresentation> query = entityManager.createQuery(
                "select new " + GearBoxRepresentation.class.getName()
                        + "(gb.entityId,gb.label,gb.type,gb.genomeTvvRuleDBM.lcdvCodeValue,gb.genomeTvvRuleB0G.lcdvCodeValue)" + " from GearBox gb",
                GearBoxRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.gearbox.GearBoxFinder#getAllGearBoxNames()
     */
    @Override
    public List<String> getAllGearBoxNames() {
        Query query = entityManager.createQuery("select DISTINCT gb.label from GearBox gb");

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.gearbox.GearBoxFinder#getAllGearBoxValuesForTvv(java.util.List)
     */
    @Override
    public List<GearBoxRepresentation> getAllGearBoxValuesForTvv(List<String> kmatList) {
        TypedQuery<GearBoxRepresentation> query = entityManager.createQuery(
                "select new " + GearBoxRepresentation.class.getName()
                        + "(gb.entityId,gb.label,gb.type,gb.genomeTvvRuleDBM.lcdvCodeValue,gb.genomeTvvRuleB0G.lcdvCodeValue)"
                        + " from GearBox gb where gb.genomeTvvRuleB0G.kmat in (:kmatList) AND gb.genomeTvvRuleDBM.lcdvCodeName = 'DBM' ",
                GearBoxRepresentation.class);
        query.setParameter("kmatList", kmatList);
        return query.getResultList();
    }

}
