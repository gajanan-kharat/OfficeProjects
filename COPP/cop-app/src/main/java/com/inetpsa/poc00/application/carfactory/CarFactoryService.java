/*
 * Creation : Dec 29, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.carfactory;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.factory.CarFactory;

/**
 * CarFactoryService Interface.
 * 
 * @author mehaj
 */
@Service
public interface CarFactoryService {

	/**
	 * Persist factory object.
	 * 
	 * @param carFactory the car factory
	 * @return the car factory
	 */
	public CarFactory persistFactoryObject(CarFactory carFactory);

	/**
	 * Save factory object.
	 * 
	 * @param carFactory the car factory
	 * @return the string
	 */
	public String saveFactoryObject(CarFactory carFactory);

	/**
	 * Delete car factory.
	 * 
	 * @param carFactoryId the car factory id
	 * @return the string
	 */
	String deleteCarFactory(long carFactoryId);
}
