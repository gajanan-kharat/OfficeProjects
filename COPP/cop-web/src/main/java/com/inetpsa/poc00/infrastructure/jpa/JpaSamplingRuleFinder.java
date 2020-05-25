package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.rest.sampling.SamplingRuleFinder;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;

/**
 * The Class JpaSamplingRuleFinder.
 */
public class JpaSamplingRuleFinder implements SamplingRuleFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /**
     * Function returning list of SamplingRuleRepresentation.
     *
     * @return the all sampling rule
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<SamplingRuleRepresentation> getAllSamplingRule() {

        TypedQuery<SamplingRuleRepresentation> query = entityManager.createQuery("select new " + SamplingRuleRepresentation.class.getName()
                + "(s.entityId, s.amtOrPercent,s.amtType,s.description,s.frequency,s.higherLimit,s.higherSymbol,s.lowerLimit,s.lowerSymbol,s.neededAmt,s.label,s.version)"
                + " from SamplingRule s ", SamplingRuleRepresentation.class);

        return query.getResultList();

    }

}