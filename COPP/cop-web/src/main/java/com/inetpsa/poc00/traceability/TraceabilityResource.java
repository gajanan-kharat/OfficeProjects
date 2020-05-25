package com.inetpsa.poc00.traceability;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.Role;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.traceability.Traceability;
import com.inetpsa.poc00.domain.traceability.TraceabilityFactory;
import com.inetpsa.poc00.domain.traceability.TraceabilityRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class TraceabilityResource.
 */
@Path("/traceabilityReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TraceabilityResource {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TraceabilityResource.class);

	/** The trace repo. */
	@Inject
	private TraceabilityRepository traceRepo;

	/** The trace factory. */
	@Inject
	private TraceabilityFactory traceFactory;

	/** The trace finder. */
	@Inject
	private TraceabilityFinder traceFinder;

	/** The security support. */
	@Inject
	private SecuritySupport securitySupport;

	/** The Constant HISTORYCHARLENGTH. */
	private static final int HISTORY_CHAR_LENGTH = 3500;

	/**
	 * This method makes an entry at Traceability table while saving new data to any table.
	 * 
	 * @param obj the obj
	 * @param screenId the screen id
	 * @return the traceability
	 */
	public Traceability historyForSave(Object obj, String screenId) {

		Traceability trace = traceFactory.createTraceability();

		StringBuilder newValue = new StringBuilder();

		try {

			StringBuilder description = new StringBuilder(Constants.HISTORY_MODIFICATION_TYPE_CREATE + " , ");

			if (obj != null && obj!= null) {
				description.append(obj.getClass().getSimpleName()).append(", ").append(obj.toString());
			}

			createHistoryData(obj, newValue);

			trace.setDescription(limitHistoryData(description.toString()));

			trace.setNewValue(limitHistoryData(newValue.toString()));

			setHistoryUserDetails(trace);

			trace.setScreenId(screenId);

			trace.setObjectId(getObjectId(obj));

		} catch (IllegalAccessException e) {
			logger.error("Illegal Access Exception while saving history for new object , Method : HistoryForSave()", e);
		} catch (InvocationTargetException e) {
			logger.error("Invocation Target Exeption while saving history for new object, Method : HistoryForSave()", e);
		} catch (NoSuchMethodException e) {
			logger.error("Issue in saving history for new object, Method : HistoryForSave()", e);
		}

		return traceRepo.saveTraceability(trace);

	}

	/**
	 * Gets the object id.
	 *
	 * @param object the object
	 * @return the object id
	 */
	private Long getObjectId(Object object) {

		BeanMap map = new BeanMap(object);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();

		try {

			if (map.containsKey(Constants.ENTITY_ID)) {
				return (Long) propUtils.getProperty(object, Constants.ENTITY_ID);

			} else if (map.containsKey("entityId")) {
				return (Long) propUtils.getProperty(object, "entityId");

			}

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error("Exception While fetching ", e);
		}

		return null;
	}

	/**
	 * Sets the history user details.
	 * 
	 * @param history the new history user details
	 */
	private void setHistoryUserDetails(Traceability history) {

		String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();
		Set<Role> roles = securitySupport.getRoles();
		String profile = null;
		for (Role role : roles) {
			if (role.getName().startsWith(Constants.LDAP_ROLE_PREFIX)) {
				profile = role.getName();
				break;
			}

		}

		history.setUserId(userId);
		history.setUserProfile(profile);
	}

	/**
	 * This method makes an entry at Traceability table while deleting row.
	 * 
	 * @param obj the obj
	 * @param screenId the screen id
	 */
	public void historyForDelete(Object obj, String screenId) {

		Traceability trace = traceFactory.createTraceability();
		StringBuilder oldValue = new StringBuilder();
		StringBuilder description = new StringBuilder(Constants.HISTORY_MODIFICATION_TYPE_DELETE + " , ");

		try {

			if (obj != null) {
				description.append(obj.getClass().getSimpleName()).append(", ").append(obj.toString());
			}

			createHistoryData(obj, oldValue);

			trace.setDescription(limitHistoryData(description.toString()));

			trace.setNewValue(limitHistoryData(oldValue.toString()));

			setHistoryUserDetails(trace);

			trace.setScreenId(screenId);

			trace.setObjectId(getObjectId(obj));

			traceRepo.saveTraceability(trace);

		} catch (IllegalAccessException e) {
			logger.error("Illegal Access Exceptio while saving history for object deletion , Method : HistoryForSave()", e);
		} catch (InvocationTargetException e) {
			logger.error("Invocation Target Exeption while saving history for object deletion, Method : HistoryForSave()", e);
		} catch (NoSuchMethodException e) {
			logger.error("Issue in saving history for object deletion, Method : HistoryForSave()", e);
		}

	}

	/**
	 * Compare objects.
	 * 
	 * @param object the object
	 * @param newValue the new value
	 * @return the string builder
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	public static StringBuilder createHistoryData(Object object, StringBuilder newValue) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		BeanMap map = new BeanMap(object);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();

		for (Object propNameObject : map.keySet()) {

			String propertyName = (String) propNameObject;
			Object value = propUtils.getProperty(object, propertyName);

			if (!Constants.CLASS.equalsIgnoreCase(propertyName) && !Constants.ENTITY_ID.equalsIgnoreCase(propertyName) && value != null)
				newValue.append(propertyName).append(" : ").append(value).append(" ; ");

		}

		return newValue;
	}

	/**
	 * This method makes an entry at Traceability table change in existing row.
	 * 
	 * @param oldObject the old object
	 * @param updatedObject the updated object
	 * @param screenId the screen id
	 * @return the traceability
	 */
	public Traceability historyForUpdate(Object oldObject, Object updatedObject, String screenId) {
		return historyForUpdate(oldObject, updatedObject, screenId, true);
	}

	/**
	 * This method makes an entry at Traceability table change in existing row.
	 * 
	 * @param oldObject the old object
	 * @param updatedObject the updated object
	 * @param screenId the screen id
	 * @param ignoreChildCollections the ignore child collections
	 * @return the traceability
	 */
	public Traceability historyForUpdate(Object oldObject, Object updatedObject, String screenId, boolean ignoreChildCollections) {

		if (oldObject != null) {
			if (oldObject.getClass().equals(updatedObject.getClass())) {

				Traceability trace = traceFactory.createTraceability();
				StringBuilder newValue = new StringBuilder();
				StringBuilder oldValue = new StringBuilder();

				try {

					// CLASS NAME, ID
					StringBuilder description = new StringBuilder(Constants.HISTORY_MODIFICATION_TYPE_MODIFIED + " , ");

					description.append(updatedObject.getClass().getSimpleName()).append(", ").append(oldObject.toString());

					compareObjects(oldObject, updatedObject, oldValue, newValue, ignoreChildCollections);

					trace.setDescription(limitHistoryData(description.toString()));

					trace.setOldValue(limitHistoryData(oldValue.toString()));

					trace.setNewValue(limitHistoryData(newValue.toString()));

					setHistoryUserDetails(trace);

					trace.setScreenId(screenId);

					trace.setObjectId(getObjectId(updatedObject));

					return traceRepo.saveTraceability(trace);

				} catch (IllegalAccessException e) {
					logger.error("Illegal Access Exception while saving history while updating object , Method : HistoryForSave()", e);
				} catch (InvocationTargetException e) {
					logger.error("Invocation Target Exeption while saving history while updating object, Method : HistoryForSave()", e);
				} catch (NoSuchMethodException e) {
					logger.error("Issue in saving history while updating object, Method : HistoryForSave()", e);
				}
			} else {
				logger.error("Log the type of both object and throw error");
				return null;
			}
		}
		return null;
	}

	/**
	 * Compare objects.
	 * 
	 * @param oldObject the old object
	 * @param newObject the new object
	 * @param oldValue the old value
	 * @param newValue the new value
	 * @param ignoreChildCollections the ignore child collections
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	public static void compareObjects(Object oldObject, Object newObject, StringBuilder oldValue, StringBuilder newValue, boolean ignoreChildCollections) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		BeanMap map = new BeanMap(oldObject);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();

		for (Object propNameObject : map.keySet()) {

			String propertyName = (String) propNameObject;

			if (!Constants.CLASS.equalsIgnoreCase(propertyName) && !Constants.ENTITY_ID.equalsIgnoreCase(propertyName)) {

				Object oldObjectPropertyValue = propUtils.getProperty(oldObject, propertyName);
				Object newObjectPropertyValue = propUtils.getProperty(newObject, propertyName);
				if (ignoreChildCollections && (oldObjectPropertyValue instanceof List || oldObjectPropertyValue instanceof Set)) {
					continue;
				}

				if (!(oldObjectPropertyValue instanceof Date) && !isObjectsEquals(oldObjectPropertyValue, newObjectPropertyValue)) {

					if (oldObjectPropertyValue != null)
						oldValue.append(propertyName).append(" : ").append(oldObjectPropertyValue).append("; ");

					if (newObjectPropertyValue != null)
						newValue.append(propertyName).append(" : ").append(newObjectPropertyValue).append("; ");
				}
			}
		}
	}

	/**
	 * Equals.
	 * 
	 * @param oldObj the old obj
	 * @param newObj the new obj
	 * @return true, if successful
	 */
	public static boolean isObjectsEquals(Object oldObj, Object newObj) {

		if (oldObj == null && newObj == null) {
			return Boolean.TRUE;

		} else {

			if (oldObj instanceof List || oldObj instanceof Set) {

				Collection oldCollectionObj = (Collection) oldObj;
				Collection newCollectionObj = (Collection) newObj;

				if ((oldCollectionObj.isEmpty() && !newCollectionObj.isEmpty()) || (!oldCollectionObj.isEmpty() && newCollectionObj.isEmpty())) {
					return Boolean.FALSE;
				}
			}
		}

		if (oldObj != null) {
			return oldObj.equals(newObj);
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * History for delete.
	 * 
	 * @param objectTypeId the object type id
	 * @param screenId the screen id
	 * @return the traceability
	 */
	public Traceability historyForDelete(String objectTypeId, String screenId) {

		Traceability trace = traceFactory.createTraceability();

		trace.setDescription(Constants.HISTORY_MODIFICATION_TYPE_DELETE + "," + objectTypeId);

		trace.setScreenId(screenId);
		setHistoryUserDetails(trace);

		return traceRepo.saveTraceability(trace);
	}

	/**
	 * History data.
	 * 
	 * @param historyRepresentation the history representation
	 * @return the response
	 */
	@POST
	@Path("/historyData")
	public Response historyData(TraceabilityRepresentation historyRepresentation) {

		logger.info("Getting history data for screen : {}", historyRepresentation.getScreenId());

		List<TraceabilityRepresentation> historyList;

		if (Constants.VEHICLEFILE_SCREEN_ID.equalsIgnoreCase(historyRepresentation.getScreenId())) {
			historyList = traceFinder.getVehicleFileHistory(historyRepresentation.getVehicleFileId());
		} else if (Constants.TVV_SCREEN_ID.equalsIgnoreCase(historyRepresentation.getScreenId())) {
			historyList = traceFinder.getTvvHistory(historyRepresentation.getTvvEntityId());

		} else {
			historyList = traceFinder.getAllHistory(historyRepresentation.getScreenId());
		}

		logger.info("History list size : {}", historyList.size());

		return Response.ok(historyList).build();
	}

	/**
	 * Save vehicle file history.
	 * 
	 * @param vehicleFile the obj
	 * @param newValue the new value
	 * @param description the description
	 */
	public void saveVehicleFileHistory(VehicleFile vehicleFile, String newValue, String description) {

		logger.info("Saving History For Vehicle File ");

		Traceability history = traceFactory.createTraceability();

		history.setVehicleFileHistory(vehicleFile);
		history.setScreenId(Constants.VEHICLEFILE_SCREEN_ID);

		history.setDescription(limitHistoryData(description));

		history.setNewValue(limitHistoryData(newValue));

		StringBuilder vfUserId = prepareUserDetails();

		Set<Role> roles = securitySupport.getRoles();

		String profile = null;

		for (Role role : roles) {
			if (role.getName().startsWith(Constants.LDAP_ROLE_PREFIX)) {
				profile = role.getName();
				break;
			}

		}

		history.setUserId(vfUserId.toString());
		history.setUserProfile(profile);

		traceRepo.saveTraceability(history);

	}

	/**
	 * Prepare user details.
	 *
	 * @return the string builder
	 */
	private StringBuilder prepareUserDetails() {

		String userId = "";
		String userFirstName = "";
		String userLastName = "";

		if (securitySupport.getSimplePrincipalByName(Principals.IDENTITY) != null && securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal() != null) {
			userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();
		}

		if (securitySupport.getSimplePrincipalByName(Principals.FIRST_NAME) != null && securitySupport.getSimplePrincipalByName(Principals.FIRST_NAME).getPrincipal() != null) {
			userFirstName = securitySupport.getSimplePrincipalByName(Principals.FIRST_NAME).getPrincipal();
		}

		if (securitySupport.getSimplePrincipalByName(Principals.LAST_NAME) != null && securitySupport.getSimplePrincipalByName(Principals.LAST_NAME).getPrincipal() != null) {
			userLastName = securitySupport.getSimplePrincipalByName(Principals.LAST_NAME).getPrincipal();
		}

		return new StringBuilder(userFirstName).append(" ").append(userLastName).append(" (").append(userId).append(")");

	}

	/**
	 * Limit history data.
	 * 
	 * @param value the value
	 * @return the string
	 */
	public String limitHistoryData(String value) {

		if (value != null && value.length() > HISTORY_CHAR_LENGTH) {
			return value.substring(0, HISTORY_CHAR_LENGTH);
		}

		return value;
	}

}
