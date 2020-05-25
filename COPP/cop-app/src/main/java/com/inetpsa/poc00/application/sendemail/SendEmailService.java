/*
 * Creation : Dec 2, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.sendemail;

import org.seedstack.business.Service;

import com.inetpsa.poc00.application.email.Email;

/**
 * The Interface ReceptionFileService.
 */
@Service
public interface SendEmailService {

	/**
	 * Send email.
	 *
	 * @param email the email
	 */
	void sendEmail(Email email);
}
