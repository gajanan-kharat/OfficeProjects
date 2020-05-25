package com.inetpsa.poc00.rest.receptionfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.email.Email;
import com.inetpsa.poc00.application.receptionfile.ReceptionFileService;
import com.inetpsa.poc00.application.sendemail.SendEmailService;
import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.application.vehiclefile.VehicleFileService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFileFactory;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFileRepository;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserRepository;
import com.inetpsa.poc00.rest.team.TeamAssembler;
import com.inetpsa.poc00.rest.team.TeamRepresentation;
import com.inetpsa.poc00.rest.user.UserAssembler;
import com.inetpsa.poc00.rest.user.UserFinder;
import com.inetpsa.poc00.rest.user.UserRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * The Class ReceptionFileResource.
 */
@Path("/ReceptionFileReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ReceptionFileResource {

	/** The reception file finder. */
	@Inject
	ReceptionFileFinder receptionFileFinder;

	/** The reception file repository. */
	@Inject
	ReceptionFileRepository receptionFileRepository;

	/** The vehicle repo. */
	@Inject
	VehicleFileService vfService;

	/** The v service. */
	@Inject
	VehicleService vService;

	/** The rf service. */
	@Inject
	ReceptionFileService rfService;

	/** The reception file factory. */
	@Inject
	ReceptionFileFactory receptionFileFactory;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The userfinder. */
	@Inject
	UserFinder userfinder;

	/** The user assembler. */
	@Inject
	UserAssembler userAssembler;

	/** The vehicle file finder. */
	@Inject
	VehicleFileFinder vehicleFileFinder;

	/** The team assembler. */
	@Inject
	TeamAssembler teamAssembler;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The pdf generation temp path. */
	@Configuration("com.inetpsa.cop.receptionFile.pdf.tempfiledirectory")
	private String pdfGenerationTempPath;

	/** The subject. */
	@Configuration("com.inetpsa.cop.receptionFile.pdf.email.subject")
	private String subject;

	/** The from. */
	@Configuration("com.inetpsa.cop.receptionFile.pdf.email.from")
	private String from;

	/** The host. */
	@Configuration("com.inetpsa.cop.email.host")
	private String host;

	/** The port. */
	@Configuration("com.inetpsa.cop.email.port")
	private String port;

	/** The user name. */
	@Configuration("com.inetpsa.cop.email.userName")
	private String userName;

	/** The password. */
	@Configuration("com.inetpsa.cop.email.password")
	private String password;

	@Inject
	SendEmailService sendService;

	/** The security support. */

	@Inject

	private SecuritySupport securitySupport;

	/** The user repo. */

	@Inject

	private UserRepository userRepo;

	/**
	 * Gets the all reception files.
	 * 
	 * @param userId the user id
	 * @return the all reception files
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllReceptionFile/{entityId}")
	public Response getAllReceptionFiles(@PathParam("entityId") String userId) {

		List<ReceptionFileRepresentation> receptionFileRepresentationsList;

		logger.info("Reception File Resource, Start Loading Reception File Data");

		receptionFileRepresentationsList = receptionFileFinder.getAllReceptionFiles(userId);

		logger.info("Length of Reception File size : {}", receptionFileRepresentationsList.size());

		return Response.ok(receptionFileRepresentationsList).build();

	}

	/**
	 * Save reception file.
	 * 
	 * @param manageReceptionFileRequestDto the manage reception file request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ReceptionFile")
	public Response saveReceptionFile(ManageReceptionFileRequestDto manageReceptionFileRequestDto) {

		for (ReceptionFileRepresentation rfRep : manageReceptionFileRequestDto.getReceptionFileRepresentationsList()) {
			ReceptionFile rf = receptionFileFactory.createReceptionFile();
			rf.setEntityId(rfRep.getEntityId());
			rf.setReservation(rfRep.getReservation());
			rf.setUserRcp(userRepo.getUserById(securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal()));
			rf.setRequester(securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal());
			rfService.saveReceptionFile(rf, rfRep.getVehicleFileEntityId(), rfRep.getParkingLotId(), rfRep.getParkingNumber());
		}

		logger.info("Sucessfully saved ReceptionFile data");

		return Response.ok().build();
	}

	/**
	 * Gets the user representation.
	 * 
	 * @param userId the user id
	 * @return the user representation
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/UserRepresentation/{entityId}")
	public Response getUserRepresentation(@PathParam("entityId") String userId) {

		logger.info("Loading UserRepresentation Details for User Id : {}---------->", userId);

		User user = userfinder.getUserById(userId);

		UserTeamRepresentation userTeamRepresentation = null;

		if (user == null) {
			logger.info("user does not exists for the User Id : {}----------------->", userId);

		} else {

			UserRepresentation userRepresentation = new UserRepresentation();
			userAssembler.doAssembleDtoFromAggregate(userRepresentation, user);

			Team team = user.getTeam();

			TeamRepresentation teamRepresentation = null;

			if (team != null) {
				teamRepresentation = teamAssembler.assembleDtoFromAggregate(team);
			}

			userTeamRepresentation = new UserTeamRepresentation(userRepresentation, teamRepresentation);
			ReceptionFileRepresentation.setPublishingUserRepresentation(userRepresentation);
			ReceptionFileRepresentation.setPublishingTeamRepresentation(teamRepresentation);
		}

		logger.info("Sucessfully sent UserRepresentation data");
		return Response.ok(userTeamRepresentation).build();

	}

	/**
	 * Genreate pdf of reception files.
	 * 
	 * @param manageReceptionFileRequestDto the manage reception file request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ReceptionFilePdfGenerate")
	public Response genreatePdfOfReceptionFiles(ManageReceptionFileRequestDto manageReceptionFileRequestDto) {

		List<ReceptionFileRepresentation> recepFileRepreList = manageReceptionFileRequestDto.getReceptionFileRepresentationsList();

		new ReceptionFileUtilityHelper().generatePDF(recepFileRepreList, pdfGenerationTempPath);

		return Response.ok().build();
	}

	/**
	 * Send reception file pdf email.
	 *
	 * @param manageReceptionFileRequestDto the manage reception file request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ReceptionFilePdfEmail")
	public Response sendReceptionFilePdfEmail(ManageReceptionFileRequestDto manageReceptionFileRequestDto) {

		if (manageReceptionFileRequestDto != null && !manageReceptionFileRequestDto.getReceptionFileRepresentationsList().isEmpty()) {

			List<String> emailIds = Arrays.asList(manageReceptionFileRequestDto.getReceptionFileRepresentationsList().get(0).getEmailIdList());

			String fileName = "Reception_File.pdf";

			if (subject == null) {
				subject = "";
			}

			subject = subject + " " + new SimpleDateFormat(Constants.EMAIL_DATE_FORMAT).format(new Date());

            Email emailRep = new Email.EmailBuilder(userName, password, fileName).setFrom(from).setHost(host)
                    .setPdfPath(ReceptionFileUtilityHelper.pdfPath).setPort(port).setSubject(subject).setTo(emailIds).build();

			sendService.sendEmail(emailRep);
		}

		return Response.ok().build();

	}

	/**
	 * Search user.
	 * 
	 * @param emailId the email id
	 * @return the response
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/LDAPUserSearch/{emailId}")
	public Response searchUser(@PathParam("emailId") String emailId) {

		logger.info("Loading sendReceptionFilePdfEmail Details for User Id : {}---------->", emailId);

		Set<String> email = new ReceptionFileUtilityHelper().searchUser(emailId);

		return Response.ok(email).build();

	}

    /**
     * Gets the vehicle file data.
     *
     * @param vehicleFileId the vehicle file id
     * @return the vehicle file data
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/VehicleFileReceptionFile/{entityId}")
    public Response getVehicleFileData(@PathParam("entityId") String vehicleFileId) {

        ReceptionFileRepresentation receptionFileRepresentation;

        logger.info("getVehicleFileData============>");

        receptionFileRepresentation = receptionFileFinder.getVehicleFileData(vehicleFileId);

        return Response.ok(receptionFileRepresentation).build();

    }

}