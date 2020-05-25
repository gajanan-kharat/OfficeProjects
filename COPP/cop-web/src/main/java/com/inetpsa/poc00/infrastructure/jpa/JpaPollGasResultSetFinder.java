/*
 * Creation : Dec 8, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetAssembler;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetFinder;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetRepresentation;

/**
 * The JpaPollGasResultSetFinder
 * 
 * @author mehaj
 */
public class JpaPollGasResultSetFinder implements PollGasResultSetFinder {
	/** The entity manager. */
	@Inject
	private EntityManager entityManager;
	@Inject
	PollGasResultSetAssembler pollGasResultSetAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.resultset.PollGasResultSetFinder#getPollutantGasResultSetsRep(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollGasResultSetRepresentation> getPollutantGasResultSetsRep(Long vehicleFileId) {
		List<PollGasResultSetRepresentation> listPollGasResultSetRepresentation = new ArrayList<>();
		TypedQuery<PollutantGasResultSet> query = entityManager.createQuery("select t from PollutantGasResultSet t where t.vehicleFile.entityId =" + vehicleFileId, PollutantGasResultSet.class);
		if (!query.getResultList().isEmpty()) {
			for (PollutantGasResultSet pollutantGasResultSet : query.getResultList()) {
				PollGasResultSetRepresentation pollGasResultSetRepresentation = new PollGasResultSetRepresentation();
				pollGasResultSetAssembler.doAssembleDtoFromAggregate(pollGasResultSetRepresentation, pollutantGasResultSet);
				listPollGasResultSetRepresentation.add(pollGasResultSetRepresentation);
			}
		}
		return listPollGasResultSetRepresentation;
	}
}
