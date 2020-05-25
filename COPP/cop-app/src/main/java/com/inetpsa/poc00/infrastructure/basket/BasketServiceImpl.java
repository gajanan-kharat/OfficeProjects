/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.infrastructure.basket;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.basket.BasketService;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.basket.BasketFactory;
import com.inetpsa.poc00.domain.basket.BasketRepository;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserFactory;
import com.inetpsa.poc00.domain.user.UserRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class BasketServiceImpl.
 */
public class BasketServiceImpl implements BasketService {

	/** The vehicle file repo. */
	@Inject
	private VehicleFileRepository vehicleFileRepo;

	/** The basket repo. */
	@Inject
	private BasketRepository basketRepo;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

	/** The user repository. */
	@Inject
	private UserRepository userRepository;

	/** The basket factory. */
	@Inject
	private BasketFactory basketFactory;

	/** The user factory. */
	@Inject
	private UserFactory userFactory;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.basket.BasketService#deleteBasket(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteBasket(Long vehicleFileId) {
		logger.info("Loading Basket Details for User Id : {}", vehicleFileId);

		VehicleFile vehicleFile = vehicleFileRepo.load(vehicleFileId);

		vehicleFile.setBasket(null);

		vehicleFileRepo.saveVehicle(vehicleFile);

		logger.info("Vehicle File is removed from the basket, vehicle File id : {} ", vehicleFileId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.basket.BasketService#getBasketForUser(java.lang.String)
	 */
	@Override
	public Basket getBasketForUser(String userId) {
		Basket basket = basketRepo.getBasketByUserId(userId);

		if (basket == null) {
			logger.info("Basket does not exists for the User, User Id : {}", userId);

			createBasket(userId);
			return null;
		}
		logger.info("Loading vehicle file detail for basketId : {}", basket.getEntityId());
		return basket;

	}

	/**
	 * Creates the basket.
	 *
	 * @param userId the user id
	 * @return
	 */
	private Basket createBasket(String userId) {

		User userObj = userRepository.getUserById(userId);

		if (userObj == null) {

			logger.info("User does not Exists, User ID : {} ", userId);

			User userToSave = userFactory.createUser();
			userToSave.setUserId(userId);
			userObj = userRepository.saveUser(userToSave);

			logger.info("User created successfully, User Id : {}, Entity Id : {}", userId, userObj.getEntityId());

		}

		logger.info("Creating basket for User Id : {}", userId);

		Basket basketToSave = basketFactory.createBasket();

		basketToSave.setUser(userObj);

		Basket basket = basketRepo.saveBasket(basketToSave);
		logger.info("Basket Created for UserId : {}", userId);
		return basket;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 * 
	 * @see com.inetpsa.poc00.application.basket.BasketService#getVehicleFilesCountForBasket(java.lang.String)
	 */
	@Override
	public Long getVehicleFilesCountForBasket(String userId) {
		Long vehicleFileCount;

		Basket basketobj = basketRepo.getBasketByUserId(userId);

		if (basketobj != null) {
			vehicleFileCount = vehicleFileRepo.getVehicleFileCount(basketobj.getEntityId());
			return vehicleFileCount;

		}
		createBasket(userId);
		return (long) 0;

	}

}
