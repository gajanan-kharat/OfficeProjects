package com.inetpsa.poc00.rest.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserFactory;
import com.inetpsa.poc00.domain.user.UserRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class UserResource.
 */
@Path("/UserReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class UserResource {

	/** The user finder. */
	@Inject
	UserFinder userFinder;

	/** The user factory. */
	@Inject
	UserFactory userFactory;

	/** The user assembler. */
	@Inject
	UserAssembler userAssembler;

	/** The user repository. */
	@Inject
	UserRepository userRepository;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@GET
	@Path("/AllUsers")
	public Response getAllUsers() {

		List<UserRepresentation> userRepresentationsList;
		logger.info("To get value from User");
		List<User> userEntityList = userFinder.getAllUsers();
		userRepresentationsList = doAssembleDtoFromAggregate(userEntityList);
		logger.info("sending UserRepresentation value to UI");
		return Response.ok(userRepresentationsList).build();

	}

	/**
	 * Do assemble dto from aggregate.
	 *
	 * @param sourceList the source list
	 * @return the list
	 */
	private List<UserRepresentation> doAssembleDtoFromAggregate(List<User> sourceList) {
		List<UserRepresentation> targetList = new ArrayList<UserRepresentation>();

		for (User tb : sourceList) {
			targetList.add(userAssembler.assembleDtoFromAggregate(tb));
		}
		return targetList;

	}

}
