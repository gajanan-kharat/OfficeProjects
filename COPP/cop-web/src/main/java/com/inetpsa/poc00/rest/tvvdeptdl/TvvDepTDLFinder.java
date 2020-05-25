/*
 * Creation : Mar 28, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;

/**
 * The Interface TvvDepTDLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvDepTDLFinder {

	/**
	 * Gets the all tvv dep tdl.
	 * 
	 * @return the all tvv dep tdl
	 */
	List<TvvDepTDLRepresentation> getAllTvvDepTDL();

	/**
	 * Gets the all tvv dependent lists.
	 * 
	 * @return the all tvv dependent lists
	 */
	List<TvvDepTDL> getAllTvvDependentLists();

	/**
	 * Gets the all tvv dependent td lists.
	 * 
	 * @return the all tvv dependent td lists
	 */
	List<TvvStructureRepresentation> getAllTvvDependentTDLLists();

	/**
	 * Gets the tvv dep tdl label.
	 * 
	 * @param label the label
	 * @return the tvv dep tdl label
	 */
	List<TvvDepTDLRepresentation> getTvvDepTDLLabel(String label);

}
