package com.inetpsa.poc00.rest.receptionfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.application.receptionfile.ReceptionFileUtilityHelperService;
import com.inetpsa.poc00.infrastructure.receptionfile.ReceptionFileUtilityHelperServiceImpl;
import com.itextpdf.text.pdf.PdfPageEventHelper;

/**
 * The Class ReceptionFileUtilityHelper.
 */
public class ReceptionFileUtilityHelper extends PdfPageEventHelper {

	/** The helper service. */
	@Inject
	ReceptionFileUtilityHelperService helperService;
	/** The Constant DATETIME_FORMAT_. */
	private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:MM";

	/** The pdf path. */
	protected static String pdfPath;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(ReceptionFileUtilityHelper.class);

	/**
	 * Generate pdf.
	 * 
	 * @param receptionFileList the reception file representation list
	 * @param tempPath the temp path
	 */
	public void generatePDF(List<ReceptionFileRepresentation> receptionFileList, String tempPath) {
		logger.info("*****generatePDF************ ");
		int numberOfvehicleFile = receptionFileList.size();

		pdfPath = tempPath + ReceptionFileRepresentation.getPublishingUserRepresentation().getUserId() + "_" + System.currentTimeMillis() + ".pdf";

		List<String> headerList = Arrays.asList("Contremarque", "N° DE CHASSIS", "Immatriculation", "N° Box", "Récéptionné le", "Réceptionné par", "Réserve");

		StringBuilder publishingTeamUserdata = new StringBuilder();
		publishingTeamUserdata.append(ReceptionFileRepresentation.getPublishingTeamRepresentation().getLabel()).append("\n\n");

		if (ReceptionFileRepresentation.getPublishingUserRepresentation().getFirstName() == null) {
			ReceptionFileRepresentation.getPublishingUserRepresentation().setFirstName("");
		}
		publishingTeamUserdata.append(ReceptionFileRepresentation.getPublishingUserRepresentation().getFirstName()).append(" ");

		if (ReceptionFileRepresentation.getPublishingUserRepresentation().getLastName() == null) {
			ReceptionFileRepresentation.getPublishingUserRepresentation().setLastName("");
		}

		publishingTeamUserdata.append(ReceptionFileRepresentation.getPublishingUserRepresentation().getLastName()).append(" ");
		publishingTeamUserdata.append(ReceptionFileRepresentation.getPublishingUserRepresentation().getUserId());

		List<String> dataList = new ArrayList<>();
		SimpleDateFormat arrivalDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		for (ReceptionFileRepresentation data : receptionFileList) {
			dataList.add(data.getCounterMark());
			dataList.add(data.getChasisNumber());
			dataList.add(data.getRegistration());
			dataList.add(data.getParkingNumber());
			dataList.add(data.getArrivalDate() == null ? "" : arrivalDateFormat.format(data.getArrivalDate()));
			dataList.add(data.getUserIdLastNameFirstName());
			dataList.add(data.getReservation());
		}
		helperService = new ReceptionFileUtilityHelperServiceImpl();
		helperService.generatePDF(String.valueOf(publishingTeamUserdata), pdfPath, numberOfvehicleFile, dataList, headerList);

		logger.info("******************  : {}", pdfPath);

	}

	/**
	 * Search user.
	 * 
	 * @param emailOrIdOrName the email
	 * @return the list
	 */
	public Set<String> searchUser(String emailOrIdOrName) {
		logger.info("*****searchUser************");
		helperService = new ReceptionFileUtilityHelperServiceImpl();
		return helperService.searchUser(emailOrIdOrName);
	}

}