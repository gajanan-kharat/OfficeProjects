/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.factororcoeffiecient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Class FacorOrCoefficientListJpaRepository.
 */
public class FactorOrCoefficientListJpaRepository extends BaseJpaRepository<FactorCoefficentList, Long> implements FactorCoeffListRepository {

    /** The Constant LOGGER. */
    private static final Logger logger = LoggerFactory.getLogger(FactorOrCoefficientListJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#saveFactorCoeffList(com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList saveFactorCoeffList(FactorCoefficentList fcObj) {

        if (fcObj.getEntityId() != null && fcObj.getEntityId() != 0)
            return super.save(fcObj);

        super.persist(fcObj);
        entityManager.flush();

        logger.info("Factor Coefficient List Saved");

        return this.load(fcObj.getEntityId());

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#persistFactorCoeffList(com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList)
     */
    @Override
    public void persistFactorCoeffList(FactorCoefficentList object) {
        super.persist(object);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#deletefactorCoefficientList(long)
     */
    @Override
    public void deletefactorCoefficientList(long entityId) {
        super.delete(entityId);
    }

    /**
     * Gets the latest emission standard dep lists.
     * 
     * @param emsId the ems id
     * @return the latest emission standard dep lists
     */
    @Override
    public List<FactorCoefficentList> getLatestEmissionStandardDepLists(Long emsId) {

        List<FactorCoefficentList> resultList = new ArrayList<>();
        Query idSubQuery = entityManager.createQuery(
                "select MAX(t1.entityId) from FactorCoefficentList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
        List<Long> idList = idSubQuery.getResultList();
        for (Long id : idList) {
            TypedQuery<FactorCoefficentList> query = entityManager.createQuery("select t from FactorCoefficentList t where t.entityId =" + id,
                    FactorCoefficentList.class);
            resultList.add(query.getSingleResult());
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#getEmissionStandardDepLists(java.lang.Long)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#getEmissionStandardDepLists(java.lang.Long)
     */
    @Override
    public List<FactorCoefficentList> getEmissionStandardDepLists(Long esEntityId) {
        TypedQuery<FactorCoefficentList> query = entityManager
                .createQuery("select t   from FactorCoefficentList t where t.emissionStandard.entityId= " + esEntityId, FactorCoefficentList.class);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getMaxVersionForLabel(java.lang.String)
     */
    @Override
    public Double getMaxVersionForLabel(String label) {

        Query query = entityManager.createNativeQuery("SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM COPQTFLS T WHERE T.LABEL = ?");

        query.setParameter(1, label);

        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    /**
     * Gets the all used factor coefficents.
     *
     * @param esEntityId the es entity id
     * @return the all used factor coefficents
     */
    public List<PollutantGasLabel> getAllUsedFactorCoefficents(Long esEntityId) {

        logger.info("getUsedPollutantLabels==============> emissionStdId	====>" + esEntityId);

        List<FactorCoefficentList> fCofListCollection = getLatestEmissionStandardDepLists(esEntityId);
        List<PollutantGasLabel> pglabelList = new ArrayList<>();

        for (FactorCoefficentList fCofList : nullGuard(fCofListCollection)) {

            for (FactorCoefficents fCofs : nullGuard(fCofList.getFactorOrCoeffiecients())) {

                pglabelList.add(fCofs.getPgLabel());

            }
        }

        return pglabelList;

    }

    /**
     * Null guard.
     * 
     * @param <T> the generic type
     * @param iterable the iterable
     * @return the iterable
     */
    private static <T> Iterable<T> nullGuard(Iterable<T> iterable) {

        if (iterable == null) {
            return Collections.<T> emptyList();
        }

        return iterable;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#getAllUsedPgLabels(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId) {
        logger.info("getUsedPollutantLabels==============> emissionStdId	====>" + emissionStdId);

        List<PollutantGasLabel> pglabelList = new ArrayList<>();
        List<FactorCoefficentList> fCofListCollection = getLatestEmissionStandardDepLists(emissionStdId);

        for (FactorCoefficentList fcCofList : nullGuard(fCofListCollection)) {

            for (FactorCoefficents fcCof : nullGuard(fcCofList.getFactorOrCoeffiecients())) {

                pglabelList.add(fcCof.getPgLabel());

            }
        }

        return pglabelList;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository#loadFactorCoeffList(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList loadFactorCoeffList(long factorCoeffListId) {

        return super.load(factorCoeffListId);
    }

}
