/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.application.receptionfile;

import java.util.List;
import java.util.Set;

import org.seedstack.business.Service;

/**
 * The Interface ReceptionFileUtilityHelperService.
 */
@Service
public interface ReceptionFileUtilityHelperService {

	/**
	 * Generate pdf.
	 *
	 * @param publishingTeamUserdata the publishing team userdata
	 * @param tempPath the temp path
	 * @param numberOfvehicleFile the number ofvehicle file
	 * @param dataList the data list
	 * @param headerList the header list
	 */
	void generatePDF(String publishingTeamUserdata, String tempPath, int numberOfvehicleFile, List<String> dataList, List<String> headerList);

	/**
	 * Search user.
	 *
	 * @param emailOrIdOrName the email or id or name
	 * @return the sets the
	 */
	Set<String> searchUser(String emailOrIdOrName);

}
