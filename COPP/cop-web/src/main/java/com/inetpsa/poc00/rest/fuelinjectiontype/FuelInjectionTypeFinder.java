package com.inetpsa.poc00.rest.fuelinjectiontype;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;

/**
 * The Interface FuelInjectionTypeFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FuelInjectionTypeFinder {

	/**
	 * Gets the all fuel injection types.
	 *
	 * @return the all fuel injection types
	 */
	public List<FuelInjectionTypeRepresentation> getAllFuelInjectionTypes();

	/**
	 * Gets the all fi types for es.
	 *
	 * @param id the id
	 * @return the all fi types for es
	 */
	public List<FuelInjectionTypeRepresentation> getAllFITypesForES(long id);

	/**
	 * Find all fuel injection type data.
	 *
	 * @return the list
	 */
	List<FuelInjectionTypeRepresentation> findAllFuelInjectionTypeData();

	/**
	 * Gets the all fi types for copied es.
	 *
	 * @param entityId the entity id
	 * @return the all fi types for copied es
	 */
	public List<FuelInjectionType> getAllFITypesForCopiedES(Long entityId);

	/**
	 * Gets the all fi type for copied pg list.
	 *
	 * @param entityId the entity id
	 * @return the all fi type for copied pg list
	 */
	public List<FuelInjectionType> getAllFITypeForCopiedPGList(Long entityId);

	/**
	 * Find fuel injection type data by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	public List<FuelInjectionTypeRepresentation> findFuelInjectionTypeDataByLabel(String label);

	/**
	 * Gets the all fi type for copied fc list.
	 *
	 * @param entityId the entity id
	 * @return the all fi type for copied fc list
	 */
	public List<FuelInjectionType> getAllFITypeForCopiedFCList(Long entityId);

}
