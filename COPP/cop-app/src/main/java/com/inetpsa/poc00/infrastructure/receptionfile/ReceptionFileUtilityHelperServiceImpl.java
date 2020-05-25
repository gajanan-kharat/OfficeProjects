package com.inetpsa.poc00.infrastructure.receptionfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.LdapUserSearchCriteria;
import com.inetpsa.clp.core.ILdapSearchCriteria;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.poc00.application.receptionfile.ReceptionFileUtilityHelperService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class ReceptionFileUtilityHelper.
 */
public class ReceptionFileUtilityHelperServiceImpl extends PdfPageEventHelper implements ReceptionFileUtilityHelperService {

	/** The Constant EMAIL_ID_UID_FIRST_NAME. */
	private static final String EMAIL_ID_UID_FIRST_NAME = "EmailID	:{}, uid	:{},	first Name	:{}";

	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	/** The Constant NUMBER_OF_COLUMN. */
	private static final int NUMBER_OF_COLUMN = 7;

	/** The Constant LDAP_SEARCH_ERROR. */
	private static final String LDAP_SEARCH_ERROR = "Exception While Searching User in ldap : ";

	/** The pdf path. */
	protected static String pdfPath;

	/** The pdf template. */
	private static PdfTemplate pdfTemplate;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(ReceptionFileUtilityHelperServiceImpl.class);

	/** The search. */
	private static LdapUserSearchCriteria search;

	/**
	 * Generate pdf.
	 *
	 * @param publishingTeamUserdata the publishing team userdata
	 * @param pdfPath the pdf path
	 * @param numberOfvehicleFile the number ofvehicle file
	 * @param dataList the data list
	 * @param headerList the header list
	 */
	@Override
	public void generatePDF(String publishingTeamUserdata, String pdfPath, int numberOfvehicleFile, List<String> dataList, List<String> headerList) {

		SimpleDateFormat outFormat = new SimpleDateFormat(DATE_FORMAT);

		Document document = new Document();

		try {
			File file = new File(pdfPath);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			Rectangle rect = new Rectangle(36, 54, 559, 788);
			writer.setBoxSize("art", rect);
			ReceptionFileUtilityHelperServiceImpl event = new ReceptionFileUtilityHelperServiceImpl();
			writer.setPageEvent(event);

			pdfTemplate = writer.getDirectContent().createTemplate(30, 16);

			PdfPTable masterTable = new PdfPTable(1);
			masterTable.setWidthPercentage(100);

			PdfPTable mainTable = new PdfPTable(NUMBER_OF_COLUMN);
			mainTable.setWidthPercentage(100);

			PdfPCell headerCell = new PdfPCell(new Phrase("MOUVEMENT DES VEHICULES ANTIPOLLUTION", getFont(10, Font.BOLD)));

			headerCell.setColspan(NUMBER_OF_COLUMN);
			setHeaderCellAlignment(headerCell);
			headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			mainTable.addCell(headerCell);

			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(2);
			setHeaderCellAlignment(cell1);
			cell1.setPhrase(new Phrase(outFormat.format(new Date()), getFont(8, Font.BOLD)));
			mainTable.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Phrase("RECEPTION DE : ", getFont(10, Font.BOLD)));
			cell2.setColspan(2);
			setHeaderCellAlignment(cell2);
			mainTable.addCell(cell2);

			PdfPCell teamNameCell = new PdfPCell(new Phrase(String.valueOf(publishingTeamUserdata), getFont(8, Font.BOLD)));
			teamNameCell.setColspan(2);
			setHeaderCellAlignment(teamNameCell);
			mainTable.addCell(teamNameCell);

			PdfPCell vehicle = new PdfPCell(new Phrase("Nb. Véhicules \n\n " + numberOfvehicleFile, getFont(8, Font.BOLD)));
			setHeaderCellAlignment(vehicle);
			mainTable.addCell(vehicle);

			for (String string : headerList) {
				mainTable.addCell(new Phrase(string, getFont(8, Font.BOLD)));
			}

			for (String data : dataList) {
				mainTable.addCell(new Phrase(data, getFont(8, Font.NORMAL)));
			}

			masterTable.addCell(mainTable);
			document.add(masterTable);

			document.close();

		} catch (DocumentException e) {
			logger.error("Document Exception , {}", e);
		} catch (FileNotFoundException e) {
			logger.error("File not found Exception , {}", e);
		}

		logger.info("******************  : {}", pdfPath);

	}

	/**
	 * Gets the font.
	 * 
	 * @param size the size
	 * @param characterStyle the character style
	 * @return the font
	 */
	private static Font getFont(int size, int characterStyle) {
		return new Font(FontFamily.TIMES_ROMAN, size, characterStyle);
	}

	/**
	 * Sets the header cell alignment.
	 * 
	 * @param cell the new header cell alignment
	 */
	private static void setHeaderCellAlignment(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	}

	/**
	 * Search user.
	 * 
	 * @param emailOrIdOrName the email
	 * @return the list
	 */
	@Override
	public Set<String> searchUser(String emailOrIdOrName) {
		Set<String> validUserSet;
		validUserSet = searchEmail(emailOrIdOrName);
		if (!validUserSet.isEmpty()) {
			return validUserSet;

		}

		validUserSet = searchUid(emailOrIdOrName);
		if (!validUserSet.isEmpty()) {
			return validUserSet;

		}
		validUserSet = searchName(emailOrIdOrName);
		if (!validUserSet.isEmpty()) {
			return validUserSet;

		}
		return validUserSet;

	}

	/**
	 * Search email.
	 *
	 * @param email the email
	 * @return the sets the
	 */
	private static Set<String> searchEmail(String email) {

		Set<String> emailSet = new HashSet<>();

		logger.info("Seaching email - {} in the Ldap", email);

		try {
			search = new LdapUserSearchCriteria();

			if (email != null && !"".equals(email)) {
				if (email.startsWith("*")) {
					email = email.replaceAll("\\*", "");
					search.setEmail(email);
				} else if (email.endsWith("*")) {
					email = email.replaceAll("\\*", "");
					search.setEmail(email);
				} else {
					search.setEmail(email);
				}
			}

			LDAPUser ldapUser = new LDAPUser();

			Enumeration users = ldapUser.findUserBySearchCriteria(search);

			logger.debug("No of user searched from this email id : {}", users);

			while (users.hasMoreElements()) {
				ldapUser = (LDAPUser) users.nextElement();
				if (ldapUser.getEmail() != null)
					emailSet.add(ldapUser.getEmail());

			}

		} catch (LDAPException exception) {

			logger.error(LDAP_SEARCH_ERROR + exception);
		}

		return emailSet;
	}

	/**
	 * Search uid.
	 *
	 * @param uid the uid
	 * @return the sets the
	 */
	private static Set<String> searchUid(String uid) {
		Set<String> uidSet = new HashSet<>();

		logger.info("Seaching uid - {} in the Ldap", uid);

		try {
			search = new LdapUserSearchCriteria();

			if (uid != null && !"".equals(uid)) {
				if (uid.startsWith("*")) {
					uid = uid.replaceAll("\\*", "");
					search.setUid(ILdapSearchCriteria.END_WITH, uid);
				} else if (uid.endsWith("*")) {
					uid = uid.replaceAll("\\*", "");
					search.setUid(ILdapSearchCriteria.START_WITH, uid);
				} else {
					search.setUid(uid);
				}
			}
			LDAPUser ldapUser = new LDAPUser();

			Enumeration users = ldapUser.findUserBySearchCriteria(search);

			logger.debug("No of user searched from this uid  : {}", users);

			while (users.hasMoreElements()) {
				ldapUser = (LDAPUser) users.nextElement();
				if (ldapUser.getEmail() != null)
					uidSet.add(ldapUser.getEmail());

			}

		} catch (LDAPException exception) {

			logger.error(LDAP_SEARCH_ERROR + exception);
		}

		return uidSet;
	}

	/**
	 * Search name.
	 *
	 * @param firstName the first name
	 * @return the sets the
	 */
	private static Set<String> searchName(String firstName) {
		Set<String> nameSet = new HashSet<>();

		logger.info("Seaching name - {} in the Ldap", firstName);

		try {
			search = new LdapUserSearchCriteria();

			if (firstName != null && !"".equals(firstName)) {
				if (firstName.startsWith("*")) {
					firstName = firstName.replaceAll("\\*", "");
					search.setFirstname(ILdapSearchCriteria.END_WITH, firstName);
				} else if (firstName.endsWith("*")) {
					firstName = firstName.replaceAll("\\*", "");
					search.setFirstname(ILdapSearchCriteria.START_WITH, firstName);
				} else {
					search.setFirstname(firstName);
				}
			}
			LDAPUser ldapUser = new LDAPUser();

			Enumeration users = ldapUser.findUserBySearchCriteria(search);

			logger.debug("No of user searched from this name  : {}", users);

			while (users.hasMoreElements()) {
				ldapUser = (LDAPUser) users.nextElement();
				if (ldapUser.getEmail() != null)
					nameSet.add(ldapUser.getEmail());

			}

		} catch (LDAPException exception) {
			logger.error(LDAP_SEARCH_ERROR + exception);
		}

		return nameSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter,
	 *      com.itextpdf.text.Document)
	 */
	/* (non-Javadoc)
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		PdfPTable table = new PdfPTable(2);

		try {
			table.setWidths(new int[] { 24, 2 });
			table.setTotalWidth(527);
			table.setLockedWidth(true);
			table.getDefaultCell().setFixedHeight(20);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(String.format("Page %d / ", writer.getPageNumber()));

			PdfPCell cell = new PdfPCell(Image.getInstance(pdfTemplate));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			table.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());

		} catch (DocumentException e) {
			logger.error("Issue in OnEndPage", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
	 *      com.itextpdf.text.Document)
	 */
	/* (non-Javadoc)
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		ColumnText.showTextAligned(pdfTemplate, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
	}

}