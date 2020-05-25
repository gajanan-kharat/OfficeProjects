/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

/**
 * The Interface TvvDepTCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvDepTCLFinder {

	/**
	 * Gets the all tvv dep tcl.
	 * 
	 * @return the all tvv dep tcl
	 */
	public List<TvvDepTCLRepresentation> getAllTvvDepTCL();

	/**
	 * Gets the all tvv dependent lists.
	 * 
	 * @return the all tvv dependent lists
	 */
	List<TvvDepTCL> getAllTvvDependentLists();

	/**
	 * Gets the tvv dep tcl label.
	 * 
	 * @param label the label
	 * @return the tvv dep tcl label
	 */
	List<TvvDepTCLRepresentation> getTvvDepTCLLabel(String label);

	/**
	 * Gets the all tvv dependent tc lists.
	 * 
	 * @return the all tvv dependent tc lists
	 */
	List<TvvStructureRepresentation> getAllTvvDependentTCLLists();

}