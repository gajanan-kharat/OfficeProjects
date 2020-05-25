/*
 * Creation : Apr 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.poc00.rest.tvvdeptdl.GenericDataMandatoryFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechDataMandatoryRepresentation;

/**
 * The Class JpaGenericDataMandatoryFinder.
 */
public class JpaGenericDataMandatoryFinder implements GenericDataMandatoryFinder {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericDataMandatoryFinder#getMandatoryListForData(long)
	 */
	@Override
	public List<GenericTechDataMandatoryRepresentation> getMandatoryListForData(long dataId) {
		List<GenericTechDataMandatoryRepresentation> gentechmand = new ArrayList<GenericTechDataMandatoryRepresentation>();
		return gentechmand;
	}

}
