package com.inetpsa.pv2.rest.pictofamily;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.notificationContrib.NotificationContrib;
import org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictoclient.PictoClientRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.pv2.utils.PictoUtils;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.color.ColorRepresentation;
import com.inetpsa.pv2.rest.picto.PictoFinder;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.pictoclient.PictoClientFinder;
import com.inetpsa.pv2.rest.pictoclient.PictoClientResource;
import com.inetpsa.pv2.rest.ruleofuses.RuleOfUsesRepresentation;
import com.inetpsa.pv2.rest.specificdrawing.SpecificDrawingRepresentation;
import com.inetpsa.pv2.rest.type.TypeRepresentation;
import com.inetpsa.pv2.rest.user.UserAssembler;
import com.inetpsa.pv2.rest.user.UserFinder;
import com.inetpsa.pv2.rest.user.UserRepresentation;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * The Class PictoFamilyResource.
 */
@Path("/pictoFamily")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PictoFamilyResource {

    /** The picto family finder. */
    @Inject
    private PictoFamilyFinder pictoFamilyFinder;

    /** The m picto family assembler. */
    @Inject
    private PictoFamilyAssembler pictoFamilyAssembler;

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /** The m user finder. */
    @Inject
    private UserFinder userFinder;

    /** The m user repository. */
    @Inject
    private UserRepository userRepository;

    /** The m picto family repository. */
    @Inject
    private PictoFamilyRepository pictoFamilyRepository;

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    /** The notification contrib repository. */
    @Inject
    NotificationContribRepository notificationContribRepository;

    /** The logger. */
    @Logging
    private Logger logger = LoggerFactory.getLogger(PictoFamilyResource.class);

    /** The singlepdf dirpath. */
    @Configuration("com.inetpsa.pv2.web.singlepdf.spath")
    private String singlePdfPath;

    /** The multipdf dirpath. */
    @Configuration("com.inetpsa.pv2.web.multipdf.mpath")
    private String multiPdfPath;

    /** The web urlhosppath. */
    @Configuration("com.inetpsa.pv2.host.hostUrl")
    private String webUrlhosppath;

    /** The single pdfname. */
    @Configuration("com.inetpsa.pv2.web.singlepdf.sname")
    private String singlePdfname;

    /** The muli pdfname. */
    @Configuration("com.inetpsa.pv2.web.multipdf.mname")
    private String muliPdfname;

    /** The m picto client finder. */
    @Inject
    private PictoClientFinder pictoClientFinder;

    /** The m picto finder. */
    @Inject
    private PictoFinder pictoFinder;

    /** The m user assembler. */
    @Inject
    private UserAssembler userAssembler;

    /** The m picto repository. */
    @Inject
    private PictoRepository pictoRepository;

    /** The m picto client repository. */
    @Inject
    private PictoClientRepository pictoClientRepository;

    /** The admin 1 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole1")
    private String admin1Role;

    /** The admin 2 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole2")
    private String admin2Role;

    /** The contributor role. */
    @Configuration("com.inetpsa.pv2.role.contributorRole")
    private String contributorRole;

    /**
     * Get picto family info.
     *
     * @param info the info
     * @return the family info by refnum
     */
    @GET
    @Path("/getPictoInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamilyInfoByRefnum(@Context UriInfo info) {

        User user = userFinder.getUser();
        String referenceNum = info.getQueryParameters().getFirst("refnum");
        PictoFamilyRepresentation picto = pictoFamilyFinder.getFamilyInfoByRefnum(referenceNum, user);
        if (picto != null) {
            List<BigInteger> pictosFavList = pictoFamilyFinder.getFavPictoIDbyUser(user.getId());
            List<BigInteger> pictosCartList = pictoFamilyFinder.getCartPictoIDbyUser(user.getId());
            Long id = picto.getFamilyId();
            BigInteger bi = BigInteger.valueOf(id.intValue());
            if (pictosFavList.contains(bi)) {
                picto.setFavoriteFlag(Boolean.TRUE);
            }

            for (int i = 0; i < picto.getPictos().size(); i++) {
                Long pictoId = picto.getPictos().get(i).getId();
                BigInteger picId = BigInteger.valueOf(pictoId.intValue());

                if (pictosCartList.contains(picId)) {
                    picto.getPictos().get(i).setCartFlag(Boolean.TRUE);
                }
            }
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(picto).build();

    }

    /**
     * Download PDF by getting refnum for Picto Family.
     *
     * @param info the info
     * @return the response
     */
    @GET
    @Path("/downloadPictoPDF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadPictoPDF(@Context UriInfo info) {
        logger.info("Start downloadPictoPDF In PictoFamilyResource Class");
        String refnum = info.getQueryParameters().getFirst("refnum");
        String language = info.getQueryParameters().getFirst("language");
        List<String> singlePdf = null;
        String result = null;
        PictoFamilyRepresentation picto = null;
        if (refnum != null) {
            picto = pictoFamilyFinder.downloadSinglePDF(refnum);
            result = generateSinglePDF(picto, language.trim());
            if ("ERROR".equals(result)) {
                return Response.status(500).build();
            }
        } else {
            logger.debug("Null Reference Number / Picto Name Passed in downloadPictoPDF of PictoFamilyResource Class");
        }
        if (result != null) {
            if (PictoConstants.MESSAGE_SUCCESS.trim().equals(result.trim())) {

                singlePdf = new ArrayList<String>();
                singlePdf.add(webUrlhosppath.trim() + PictoConstants.SINGLE_PDF_DOWNLOAD.trim());

            } else {
                logger.debug("PDF genration Failure in method downloadPictoPDF of PictFamilyResource Class");
            }
        }

        if (picto == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(singlePdf).build();

    }

    /* GK - PSA - Export functionality - 22-July-16 - Start */
    /**
     * To generate single PDF export for single PICTO family.
     *
     * @author gajanank
     * @param refnum the refnum
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return String
     */
    public String generateSinglePDF(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        logger.info("Gentare Single PDF for Pictos started in generateSinglePDF method of PictoFamilyResource Class");
        String result = null;
        FileOutputStream fileout = null;
        File file = null;
        Document document = new Document();
        if (pictoFamilyRepresentation == null || singlePdfPath == null || singlePdfPath.isEmpty()) {
            logger.info("Either pictoFamilyRepresentation or singlepdf_Dirpath is empty, In generateSinglePDF method of PictoFamilyResource Class");
            return "ERROR";
        }
        String singlePDFDir = singlePdfPath;
        singlePDFDir = singlePDFDir.substring(0, singlePDFDir.lastIndexOf(PictoConstants.FILE_SEPRATOR.trim()));
        File singlePdffile = new File(singlePDFDir);
        if (!singlePdffile.exists()) {
            singlePdffile.mkdir();
        }
        file = new File(singlePdfPath);
        try {
            fileout = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileout);
            document.open();
            Paragraph paragraph = new Paragraph();

            // Add image
            Paragraph addImage = addImage(pictoFamilyRepresentation);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.setIndentationLeft(100);
            paragraph.add(addImage);
            // Add Title
            Paragraph addTitle = addTitle(pictoFamilyRepresentation);
            if (addTitle != null && !addTitle.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addTitle);
            }
            // Add Notification
            Paragraph addNotification = addNotification(pictoFamilyRepresentation, lang);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.add(addNotification);

            // Add Regelment
            Paragraph addsettlement = addsettlement(pictoFamilyRepresentation, lang);
            if (addsettlement != null && !addsettlement.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addsettlement);
            }

            // Add CASD_USAGE
            Paragraph addUsage = addUsage(pictoFamilyRepresentation, lang);
            if (addUsage != null && !addUsage.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addUsage);
            }

            Paragraph adminAndLabel = addAdminAndLabel(pictoFamilyRepresentation);
            if (adminAndLabel != null && !adminAndLabel.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(adminAndLabel);
            }

            // Add NOM_CHARTE
            Paragraph addRefCharte = addRefCharte(pictoFamilyRepresentation);
            if (addRefCharte != null && !addRefCharte.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addRefCharte);
            }

            boolean bEclairee = false;
            boolean bColors = false;
            // Add ECLAIREE
            Paragraph addEclairee = addEclairee(pictoFamilyRepresentation, lang);
            if (addEclairee != null && !addEclairee.isEmpty()) {
                bEclairee = true;
            }
            // To add all control types
            Paragraph addControlTypes = addControlTypes(pictoFamilyRepresentation, lang);
            if (addControlTypes != null && !addControlTypes.isEmpty()) {
                bColors = true;
            }
            if (bEclairee || bColors) {
                // Add RHN
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_LEFT);
                if (bEclairee) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addEclairee);
                }
                if (bColors) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addControlTypes);
                } else {
                    paragraph.add(Chunk.NEWLINE);
                }
            }

            Paragraph addInformationRHN = addInformationRHN(pictoFamilyRepresentation, lang);
            if (addInformationRHN != null && !addInformationRHN.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addInformationRHN);
            }

            // Add keywords
            Paragraph addKeywords = addKeywords(lang, pictoFamilyRepresentation);
            if (addKeywords != null && !addKeywords.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addKeywords);
            }

            // To add categories
            Paragraph addSuperAndSubCategories = addSuperAndSubCategories(pictoFamilyRepresentation, lang);
            if (addSuperAndSubCategories != null && !addSuperAndSubCategories.isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addSuperAndSubCategories);
            }
            document.add(paragraph);
            logger.info("Single PDF Generated sucessfully");
            result = PictoConstants.MESSAGE_SUCCESS;
        } catch (DocumentException e1) {
            logger.error("Document Exception Occurred in generateSinglePDF method of PictoFamilyResource Class", e1);
        } catch (FileNotFoundException e1) {
            logger.error("Exception Occurred while Output Streaming File in generateSinglePDF method of PictoFamilyResource Class", e1);
        } finally {
            try {
                document.close();
                if (fileout != null)
                    fileout.close();
            } catch (IOException e) {
                result = PictoConstants.MESSAGE_FAILURE;
                logger.error("IO Exception Occurred in generateSinglePDF method of PictoFamilyResource Class", e);
            }
        }
        return result;
    }

    /**
     * To get settlement for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return paragraph
     */
    private Paragraph addsettlement(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
        List<RuleOfUsesRepresentation> ruleList = pictoFamilyRepresentation.getRules();

        if (!ruleList.isEmpty() && !PictoConstants.TYPE_ICON.equals(pictoFamilyRepresentation.getTypeID().getTypeLabel())) {
            if (PictoConstants.FR_LANG.equals(lang)) {
                paragraph.add(PictoConstants.REGLEMENT);
            } else {
                paragraph.add(PictoConstants.REGLEMENT_ENG);
            }
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            for (int i = 0; i < ruleList.size(); i++) {
                paragraph.add(pictoFamilyRepresentation.getRules().get(i).getName());
                paragraph.add(Chunk.NEWLINE);
            }
        }

        return paragraph;
    }

    /**
     * To get Admin and Label for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @return paragraph
     */
    private Paragraph addAdminAndLabel(PictoFamilyRepresentation pictoFamilyRepresentation) {
        Paragraph paragraph = new Paragraph();
        TypeRepresentation typeID = pictoFamilyRepresentation.getTypeID();
        if (typeID != null && typeID.getTypeLabel() != null) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.TYPE);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.add(typeID.getTypeLabel());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            paragraph.add(Chunk.NEWLINE);

        } else {
            logger.debug("Null TypeId in method generateSinglePDF of PictoFamilyResource class");
            return null;
        }
        return paragraph;
    }

    /**
     * To get image for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @return paragraph
     */
    private Paragraph addImage(PictoFamilyRepresentation pictoFamilyRepresentation) {
        Image image;
        int imgCount = 0;
        Paragraph paragraph = new Paragraph();
        List<PictoRepresentation> getpictos = pictoFamilyRepresentation.getPictos();
        if (getpictos != null && !getpictos.isEmpty()) {
            for (PictoRepresentation picRepresentation : getpictos) {
                if (picRepresentation.getImageLocation() == null) {
                    logger.debug("Null Image Location in method generateSinglePDF of PictoFamilyResource Class");
                } else {
                    if (imgCount == 0) {
                        String imagePath;
                        imagePath = picRepresentation.getImageUrl();
                        imagePath = imagePath.substring(imagePath.lastIndexOf('=') + 1);
                        if (imagePath != null) {
                            try {
                                try {
                                    image = Image.getInstance(imagePath);
                                    if (image != null) {
                                        paragraph.add(new Chunk());
                                        paragraph.add(new Chunk());
                                        image.setAlignment(Image.ALIGN_LEFT);
                                        paragraph.add(new Chunk(image, -80, -67));
                                        paragraph.add(new Chunk());
                                    }
                                    imgCount++;
                                } catch (BadElementException e) {
                                    logger.error(
                                            "MalformedURLException Exception Occurred while reading Image in generateSinglePDF method of PictoFamilyResource Class",
                                            e);
                                }
                            } catch (IOException e) {
                                logger.error("IO  Exception Occurred while reading Image in generateSinglePDF method of PictoFamilyResource Class",
                                        e);
                            }
                        }
                    }
                }
            }
        }
        return paragraph;
    }

    /**
     * To get title for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @return paragraph
     */
    private Paragraph addTitle(PictoFamilyRepresentation pictoFamilyRepresentation) {
        Paragraph paragraph = new Paragraph();
        if ((pictoFamilyRepresentation.getName() != null) && (pictoFamilyRepresentation.getReferenceNum() != null)) {
            try {
                String exportTitle;
                if (pictoFamilyRepresentation.getRefCharte() != null) {
                    exportTitle = PictoConstants.REFERENCE_TITLE + pictoFamilyRepresentation.getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                            + pictoFamilyRepresentation.getRefCharte() + pictoFamilyRepresentation.getName();
                } else {
                    exportTitle = PictoConstants.REFERENCE_TITLE + pictoFamilyRepresentation.getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                            + pictoFamilyRepresentation.getName();
                }
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, 15, Font.BOLD));
                paragraph.add(exportTitle);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.add(Chunk.NEWLINE);
            } catch (Exception e) {
                logger.error("DocumentException Occurred while adding paragraph in generateSinglePDF method of PictoFamilyResource Class", e);
            }
        } else {
            logger.debug("Null Name or ReferenceNumber in method generateSinglePDF of PictoFamilyResource Class");
            return null;
        }
        return paragraph;
    }

    /**
     * To get casd the usage for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return paragraph
     */
    private Paragraph addUsage(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        Paragraph paragraph = new Paragraph();

        if (PictoConstants.FR_LANG.equals(lang) && (pictoFamilyRepresentation.getFunctionFR() != null)
                && !pictoFamilyRepresentation.getFunctionFR().isEmpty()) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.CASD_USAGE);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.add(pictoFamilyRepresentation.getFunctionFR());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            paragraph.add(Chunk.NEWLINE);
        }
        if (PictoConstants.EN_LANG.equals(lang)
                && (pictoFamilyRepresentation.getFunctionEN() != null && !pictoFamilyRepresentation.getFunctionEN().isEmpty())) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.CASD_USAGE_ENG);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.add(pictoFamilyRepresentation.getFunctionEN());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            paragraph.add(Chunk.NEWLINE);
        }
        return paragraph;
    }

    /**
     * To get Eclairée for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return paragraph
     */
    private Paragraph addEclairee(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));

        if (pictoFamilyRepresentation.getCommandInformation() != null || pictoFamilyRepresentation.getIsRHNActive()
                || pictoFamilyRepresentation.getIsRHNAlert() || pictoFamilyRepresentation.getIsRHNDefault()) {
            if (PictoConstants.FR_LANG.equals(lang)) {
                paragraph.add(PictoConstants.COMMANDANDTELLFR);
            } else {
                paragraph.add(PictoConstants.COMMANDANDTELLEN);
            }
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            if (pictoFamilyRepresentation.getCommand() && pictoFamilyRepresentation.getCommandInformation() != null
                    && !pictoFamilyRepresentation.getCommandInformation().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(Chunk.NEWLINE);
                if (PictoConstants.FR_LANG.equals(lang)) {
                    paragraph.add(PictoConstants.ECLAIREE);
                } else {
                    paragraph.add(PictoConstants.ECLAIREE_ENG);
                }
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(pictoFamilyRepresentation.getCommandInformation());
                paragraph.setAlignment(Element.ALIGN_LEFT);

            } else {
                logger.debug("Command is unchecked ID in generateSinglePDF method of PictoFamilyResource Class");
            }
        }
        return paragraph;
    }

    /**
     * To add Super and sunb categories in the paragraph.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return paragraph
     */
    private Paragraph addSuperAndSubCategories(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        CategoryRepresentation categoryID = pictoFamilyRepresentation.getCategoryID();
        Paragraph paragraph = new Paragraph();
        if (categoryID != null) {
            if (categoryID.getName() == null || categoryID.getName() == "") {
                logger.debug("Null Category ID in generateSinglePDF method of PictoFamilyResource Class");
            } else {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                if (PictoConstants.FR_LANG.equals(lang)) {
                    paragraph.add(PictoConstants.CATEGORIES);
                } else {
                    paragraph.add(PictoConstants.CATEGORIES_ENG);
                }
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.setIndentationLeft(100);
                paragraph.add(Chunk.NEWLINE);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                if (lang.equals(PictoConstants.FR_LANG)) {
                    paragraph.add(categoryID.getCategoryFr());
                } else {
                    paragraph.add(categoryID.getCategoryEn());
                }
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.setIndentationLeft(100);
                paragraph.add(Chunk.NEWLINE);
            }
        } else {
            logger.debug("Null Category ID in generateSinglePDF method of PictoFamilyResource Class");
            return null;
        }
        return paragraph;
    }

    /**
     * To get all control type values.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @param lang the lang
     * @return paragraph
     */
    private Paragraph addControlTypes(PictoFamilyRepresentation pictoFamilyRepresentation, String lang) {
        String activation;
        String alert;
        String default1;
        boolean bActivation = false;
        boolean bAlert = false;
        boolean bDefault = false;
        StringBuilder activeColor = new StringBuilder("");
        StringBuilder alertColor = new StringBuilder("");
        StringBuilder failureColor = new StringBuilder("");

        Paragraph paragraph = new Paragraph();
        if (pictoFamilyRepresentation.getIsRHNWitness()) {
            try {
                List<ColorRepresentation> witnessActive = pictoFamilyRepresentation.getWitnessActive();
                if (witnessActive != null && !witnessActive.isEmpty()) {
                    bActivation = true;
                    for (ColorRepresentation color : witnessActive) {
                        if (activeColor.length() == 0) {
                            activeColor.append(color.getColor());
                        } else {
                            activeColor.append(", ").append(color.getColor());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Exception occuredNull Color in method generateSinglePDF of PictoFamily Resource Class", e);
            }
            try {
                List<ColorRepresentation> witnessAlert = pictoFamilyRepresentation.getWitnessAlert();
                if (witnessAlert != null && !witnessAlert.isEmpty()) {
                    bAlert = true;
                    for (ColorRepresentation color : witnessAlert) {
                        if (alertColor.length() == 0) {
                            alertColor.append(color.getColor());
                        } else {
                            alertColor.append(", ").append(color.getColor());
                        }
                    }

                }
            } catch (Exception e) {
                logger.error(" Exceptipon occured due to Null Witness Alert in method generateSinglePDF of Pictoresource class", e);
            }
            try {
                List<ColorRepresentation> witnessFailure = pictoFamilyRepresentation.getWitnessFailure();
                if (witnessFailure != null && !witnessFailure.isEmpty()) {
                    bDefault = true;
                    for (ColorRepresentation color : witnessFailure) {
                        if (failureColor.length() == 0) {
                            failureColor.append(color.getColor());
                        } else {
                            failureColor.append(", ").append(color.getColor());
                        }
                    }

                }
            } catch (Exception e) {
                logger.error(" Exceptipon occured due to Null Witness Alert in method generateSinglePDF of Pictoresource class", e);
            }

            if (bActivation || bAlert || bDefault) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                if (PictoConstants.FR_LANG.equals(lang)) {
                    activation = PictoConstants.ACTIVATION_FR + " " + activeColor.toString();
                    alert = PictoConstants.ALERTE_FR + " " + alertColor;
                    default1 = PictoConstants.DEFIANCE_FR + " " + failureColor;
                } else {
                    activation = PictoConstants.ACTIVATION_EN + " " + activeColor.toString();
                    alert = PictoConstants.ALERTE_EN + " " + alertColor;
                    default1 = PictoConstants.DEFIANCE_EN + " " + failureColor;
                }
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.setIndentationLeft(100);

                if (bActivation) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(activation);
                    paragraph.add(Chunk.NEWLINE);
                }
                if (bAlert) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(alert);
                    paragraph.add(Chunk.NEWLINE);
                }
                if (bDefault) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(default1);
                    paragraph.add(Chunk.NEWLINE);
                }
            } else {
                logger.debug("Null Witness Alert in method generateSinglePDF of Pictoresource class");
            }
        } else {
            logger.debug("Null Witness Alert in method generateSinglePDF of Pictoresource class");
        }
        return paragraph;
    }

    /**
     * To get reference number for paragraph to export in pdf.
     *
     * @author gajanank
     * @param pictoFamilyRepresentation the picto family representation
     * @return paragraph
     */
    private Paragraph addRefCharte(PictoFamilyRepresentation pictoFamilyRepresentation) {
        Paragraph paragraph = new Paragraph();
        if (pictoFamilyRepresentation.getRefCharte() != null && !pictoFamilyRepresentation.getRefCharte().isEmpty()) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.NOM_CHARTE);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(100);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
            paragraph.add(pictoFamilyRepresentation.getRefCharte());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.add(Chunk.NEWLINE);
            paragraph.setIndentationLeft(100);
        } else {
            logger.debug("Null RefCharte in method generateSinglePDF of PictoFamilyResource Class");
            return null;
        }
        return paragraph;
    }

    /* GK - PSA - Export functionality - 22-July-16 - End */

    /**
     * Download Multiple PDF by getting refnum.pictoIds for PICTO Family.
     *
     * @param info the info
     * @param listpictoIds the listpicto ids
     * @return Response
     */
    @GET
    @Path("/downloadMultiPictoPDF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadMultiPictoPDF(@Context UriInfo info, @QueryParam("reflistnum") List<String> listpictoIds) {
        String language = info.getQueryParameters().getFirst("language");

        List<String> mutiPdfrl = null;
        String exportPdfPath;
        if (listpictoIds != null) {
            try {
                String seprator = "'";
                StringBuilder sbPictoIds = new StringBuilder();
                String multiPictoIds;
                for (String multipictoIds : listpictoIds) {
                    sbPictoIds.append(seprator);
                    sbPictoIds.append(multipictoIds);
                    seprator = "','";
                }
                multiPictoIds = sbPictoIds.append("'").toString();
                List<PictoFamilyRepresentation> pictoFamily = pictoFamilyFinder.downloadMultiPDF(multiPictoIds);
                /* GK - PSA - Export functionality - 25-July-16 - Start */
                String result = generateMultiplePDF(pictoFamily, language.trim());
                if (result != null) {
                    if (PictoConstants.MESSAGE_SUCCESS.trim().equals(result.trim())) {
                        // Method call to add Page number on export PDF file.
                        mutiPdfrl = new ArrayList<String>();
                        exportPdfPath = addPageNumberToExportPdf(multiPdfPath);
                        if (exportPdfPath.isEmpty() || exportPdfPath.equals(PictoConstants.EMPTY_STRING)) {
                            return Response.ok(PictoConstants.MESSAGE_FAILURE).build();
                        }
                        if (PictoConstants.MESSAGE_FAILURE.trim().equals(exportPdfPath.trim())) {
                            return Response.ok(PictoConstants.MESSAGE_FAILURE).build();
                        }
                        mutiPdfrl.add(webUrlhosppath.trim() + PictoConstants.MULTI_PDF_DOWNLOAD.trim() + exportPdfPath);
                        /* GK - PSA - Export functionality - 25-July-16 - Start */
                    }
                }
            } catch (Exception e) {
                logger.error("Null List of PictIds in method argument in method downloadMultiPictoPDF of PictoFamilyResource Class", e);
            }
        }
        return Response.ok(mutiPdfrl).build();
    }

    /**
     * Gets the multipdf.
     *
     * @param url the url
     * @return the multipdf
     */
    @GET
    @Path("/GetMultiplePDF")
    @Produces("application/pdf")
    public Response getMultipdf(@QueryParam("name") String url) {
        File file = new File(url);
        return Response.ok(file, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
    }

    /**
     * Gets the singlepdf.
     *
     * @return the singlepdf
     */
    @GET
    @Path("/GetSinglePDF")
    @Produces("application/pdf")
    public Response getSinglepdf() {
        File file = new File(singlePdfPath);
        return Response.ok(file, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
    }

    /* GK - PSA - Export functionality - 22-July-16 - Start */
    /**
     * To add page number to exported PDF file.
     *
     * @param multipdfDirpath the multipdf dirpath
     * @return String
     */
    private String addPageNumberToExportPdf(String multipdfDir) {
        String pdfPath = "";
        String fileFullPath = "";
        String fileNameToCreate = "";
        String multipdfDirpath = multipdfDir.trim();
        try {
            PdfReader reader = new PdfReader(multipdfDirpath);
            int n = reader.getNumberOfPages();
            String fileBaseName = FilenameUtils.getBaseName(multipdfDirpath);
            fileBaseName = fileBaseName + ".";
            fileFullPath = FilenameUtils.getFullPath(multipdfDirpath);
            fileNameToCreate = fileFullPath + fileBaseName + PictoConstants.PDF_FILE;
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileNameToCreate));
            FilenameUtils.getBaseName(pdfPath);
            FilenameUtils.getPath(pdfPath);
            int i = 0;
            PdfContentByte over;
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            while (i < n) {
                i++;
                over = stamp.getOverContent(i);
                over.beginText();
                over.setFontAndSize(bf, 14);
                over.setTextMatrix(30, 30);
                over.setFontAndSize(bf, PictoConstants.PDF_FONTSIZE);
                over.showTextAligned(Element.ALIGN_RIGHT, "Page " + i, 560, 30, 0);
                over.endText();
            }
            stamp.close();
            logger.debug("Succesfully added page number to export: " + fileNameToCreate);
            return fileNameToCreate;

        } catch (Exception e) {
            logger.error("IO Exception occured while PDF Numbering in addPageNumberToExportPdf method of class PictoFamilyResource", e);
            return PictoConstants.MESSAGE_FAILURE;
        }
    }

    /* GK - PSA - Export functionality - 22-July-16 - Start */
    /**
     * Method to get paragraph to add into export PDF.
     *
     * @author gajanank
     * @param lang the lang
     * @param picfmly the picfmly
     * @return Paragraph
     */
    /* GK - PSA - Export functionality - 22-July-16 - Start */
    private Paragraph getPdfParagraph(String lang, PictoFamilyRepresentation picfmly) {
        Paragraph paragraph = new Paragraph();
        // Add image
        Paragraph addImage = addImageForMultiPdf(picfmly);
        paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
        paragraph.setIndentationLeft(100);
        paragraph.add(addImage);
        try {
            if (picfmly.getName() != null && picfmly.getReferenceNum() != null) {
                // Add title
                Paragraph addTitle = addTitle(picfmly);
                if (addTitle != null && !addTitle.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addTitle);
                }
                // Add Notification
                Paragraph addNotification = addNotification(picfmly, lang);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addNotification);

                // Add Regelment
                Paragraph addsettlement = addsettlement(picfmly, lang);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(addsettlement);

                // Add CASD_USAGE
                Paragraph addUsage = addUsage(picfmly, lang);
                if (addUsage != null && !addUsage.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addUsage);
                }

                Paragraph adminAndLabel = addAdminAndLabel(picfmly);
                if (adminAndLabel != null && !adminAndLabel.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(adminAndLabel);
                }

                // Add NOM_CHARTE
                Paragraph addRefCharte = addRefCharte(picfmly);
                if (addRefCharte != null && !addRefCharte.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addRefCharte);
                }

                // Add ECLAIREE
                Paragraph addEclairee = addEclairee(picfmly, lang);
                if (addEclairee != null && !addEclairee.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addEclairee);
                }
                // To add all control types
                Paragraph addControlTypes = addControlTypes(picfmly, lang);
                if (addControlTypes != null && !addControlTypes.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addControlTypes);
                }

                Paragraph addInformationRHN = addInformationRHN(picfmly, lang);
                if (addInformationRHN != null && !addInformationRHN.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addInformationRHN);
                }

                // Add keywords
                Paragraph addKeywords = addKeywords(lang, picfmly);
                if (addKeywords != null && !addKeywords.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addKeywords);
                }

                // To add categories
                Paragraph addSuperAndSubCategories = addSuperAndSubCategories(picfmly, lang);
                if (addSuperAndSubCategories != null && !addSuperAndSubCategories.isEmpty()) {
                    paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                    paragraph.add(addSuperAndSubCategories);
                }

            } else {
                logger.debug("Null Name or ReferenceNumber in method getPdfParagraph of PictoFamilyResource Class");
            }
        } catch (Exception e) {
            logger.error("IO Exception Occured while reading Image in method getPdfParagraph of PictoFamilyResource Class", e);
        }
        return paragraph;
    }

    /**
     * To get image for paragraph to export in pdf.
     *
     * @author gajanank
     * @param picfmly the picfmly
     * @return paragraph
     */
    private Paragraph addImageForMultiPdf(PictoFamilyRepresentation picfmly) {
        Image image = null;
        String pictoImgpath = null;
        Paragraph paragraph = new Paragraph();
        try {
            // Add PICTO Family image
            List<PictoRepresentation> getpictos = picfmly.getPictos();
            if (getpictos != null && !getpictos.isEmpty()) {
                for (PictoRepresentation picto : getpictos) {
                    if (picto.getIsFrontagePicto()) {
                        pictoImgpath = picto.getImageUrl();
                        pictoImgpath = pictoImgpath.substring(pictoImgpath.lastIndexOf('=') + 1);
                    }
                }
            }
            if (pictoImgpath != null) {
                image = Image.getInstance(pictoImgpath);
                if (image != null) {
                    paragraph.add(new Chunk());
                    paragraph.add(new Chunk());
                    image.setAlignment(Image.ALIGN_LEFT);
                    paragraph.add(new Chunk(image, -80, -67));
                    paragraph.add(new Chunk());
                }
            }
        } catch (BadElementException e) {
            logger.error("BadElement Exception Occured while reading Image in method getPdfParagraph of PictoFamilyResource Class", e);
        } catch (MalformedURLException e) {
            logger.error("Malformed URLException Occured while reading Image in method getPdfParagraph of PictoFamilyResource Class", e);
        } catch (IOException e) {
            logger.error("IO Exception Occured while reading Image in method getPdfParagraph of PictoFamilyResource Class", e);
        }
        return paragraph;
    }

    /**
     * To get keywords the usage for paragraph to export in pdf.
     *
     * @author gajanank
     * @param lang the lang
     * @param picfmly the picfmly
     * @return paragraph
     */
    private Paragraph addKeywords(String lang, PictoFamilyRepresentation picfmly) {
        Paragraph paragraph = new Paragraph();
        if ((picfmly.getKeywordFR() != null && !picfmly.getKeywordFR().isEmpty())
                && (picfmly.getKeywordEN() != null && !picfmly.getKeywordEN().isEmpty())) {
            if (PictoConstants.FR_LANG.equals(lang) && picfmly.getKeywordFR() != null && !picfmly.getKeywordFR().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                paragraph.add(PictoConstants.MOTS_CLES);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.add(Chunk.NEWLINE);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(picfmly.getKeywordFR());
            }
            if (PictoConstants.EN_LANG.equals(lang) && picfmly.getKeywordEN() != null && !picfmly.getKeywordEN().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                paragraph.add(PictoConstants.MOTS_CLES_ENG);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.add(Chunk.NEWLINE);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(picfmly.getKeywordEN());
            }
            paragraph.add(Chunk.NEWLINE);
        } else {
            logger.debug("Admin is NULL in method generateSinglePDF of PictoFamilyResource Class");
            return null;
        }
        return paragraph;
    }

    /**
     * Adds the information RHN.
     *
     * @param pictoFamily the picto family
     * @param lang the lang
     * @return the paragraph
     */
    private Paragraph addInformationRHN(PictoFamilyRepresentation pictoFamily, String lang) {
        Paragraph paragraph = new Paragraph();
        if ((pictoFamily.getRhnInfoFR() != null) && (pictoFamily.getRhnInfoEN() != null)) {
            if (PictoConstants.FR_LANG.equals(lang) && pictoFamily.getRhnInfoFR() != null && !pictoFamily.getRhnInfoFR().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                paragraph.add(PictoConstants.RHNINFOFR);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.add(Chunk.NEWLINE);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(pictoFamily.getRhnInfoFR());
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.EN_LANG.equals(lang) && pictoFamily.getRhnInfoEN() != null && !pictoFamily.getRhnInfoEN().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
                paragraph.add(PictoConstants.RHNINFOEN);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.add(Chunk.NEWLINE);
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(pictoFamily.getRhnInfoEN());
                paragraph.add(Chunk.NEWLINE);
            }

        } else {
            logger.debug("Admin is NULL in method generateSinglePDF of PictoFamilyResource Class");
            return null;
        }
        return paragraph;
    }

    /**
     * Adds the notification.
     *
     * @param pictoFamily the picto family
     * @param lang the lang
     * @return the paragraph
     */
    private Paragraph addNotification(PictoFamilyRepresentation pictoFamily, String lang) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        if (PictoConstants.EN_LANG.equals(lang)) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.INFORMATION.toUpperCase());
            paragraph.add(Chunk.NEWLINE);
            if (PictoConstants.TYPE_INFO.equals(pictoFamily.getInformationTypeLabel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFINFOEN);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.TYPE_WARNING.equals(pictoFamily.getInformationTypeLabel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFWARNEN);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.LEVEL_VALID.equals(pictoFamily.getValidationLevel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFVALIDEN);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.LEVEL_INPROGRESS.equals(pictoFamily.getValidationLevel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFWIPEN);
                paragraph.add(Chunk.NEWLINE);
            }
            if (pictoFamily.getInformationEN() != null && !pictoFamily.getInformationEN().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(pictoFamily.getInformationEN());
                paragraph.add(Chunk.NEWLINE);
            }
        }

        if (PictoConstants.FR_LANG.equals(lang)) {
            paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.BOLD));
            paragraph.add(PictoConstants.INFORMATION.toUpperCase());
            paragraph.add(Chunk.NEWLINE);
            if (PictoConstants.TYPE_INFO.equals(pictoFamily.getInformationTypeLabel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFINFOFR);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.TYPE_WARNING.equals(pictoFamily.getInformationTypeLabel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFWARNFR);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.LEVEL_VALID.equals(pictoFamily.getValidationLevel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFVALIDFR);
                paragraph.add(Chunk.NEWLINE);
            }
            if (PictoConstants.LEVEL_INPROGRESS.equals(pictoFamily.getValidationLevel())) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(PictoConstants.PDFWIPFR);
                paragraph.add(Chunk.NEWLINE);
            }
            if (pictoFamily.getInformationEN() != null && !pictoFamily.getInformationEN().isEmpty()) {
                paragraph.setFont(FontFactory.getFont(PictoConstants.CALIBRI, PictoConstants.PDF_FONTSIZE, Font.NORMAL));
                paragraph.add(pictoFamily.getInformationEN());
                paragraph.add(Chunk.NEWLINE);
            }
        }

        return paragraph;
    }

    /* GK - PSA - Export functionality - 22-July-16 - End */

    /**
     * To generate multiple PDF for PICTO family list.
     *
     * @param pictoFamily the picto family
     * @param lang the lang
     * @return String
     */
    public String generateMultiplePDF(List<PictoFamilyRepresentation> pictoFamily, String lang) {

        logger.info("Genrate Multiple PDF for Pictos started in generateMultiplePDF method of PictoFamilyResource Class");
        String result = null;
        FileOutputStream fileout = null;
        File file = null;
        Document document = null;
        try {
            file = new File(multiPdfPath);
            String multiPDFDir = multiPdfPath;
            multiPDFDir = multiPDFDir.substring(0, multiPDFDir.lastIndexOf(PictoConstants.FILE_SEPRATOR.trim()));
            File multiPdffile = new File(multiPDFDir);
            if (!multiPdffile.exists()) {
                multiPdffile.mkdir();
            }
            fileout = new FileOutputStream(file);
            document = new Document();
            PdfWriter.getInstance(document, fileout);
            document.addAuthor(PictoConstants.AUTHOR_TITLE);
            document.addTitle(PictoConstants.AUTHOR_TITLE);
            document.open();
            for (PictoFamilyRepresentation picfmly : pictoFamily) {
                if (picfmly != null) {
                    // Call getPdfParagraph method to add paragraph in PDF.
                    Paragraph paragraphToAdd = getPdfParagraph(lang, picfmly);
                    boolean addStatus = document.add(paragraphToAdd);
                    if (!addStatus) {
                        result = PictoConstants.MESSAGE_FAILURE;
                        logger.debug("Failed to add Paragraph in generateMultiplePDF method of PictoFamilyResource Class");
                    } else {
                        logger.debug("Successfully added Paragraph in generateMultiplePDF method of PictoFamilyResource Class");
                        document.newPage();
                        result = PictoConstants.MESSAGE_SUCCESS;
                    }
                }
            }
        } catch (Exception e1) {
            logger.error("Document Exception Occurred in generateMultiplePDF method of PictoFamilyResource Class", e1);
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (fileout != null)
                    fileout.close();
                result = PictoConstants.MESSAGE_SUCCESS;
            } catch (Exception e) {
                result = PictoConstants.MESSAGE_FAILURE;
                logger.error("Exception Occurred in generateMultiplePDF method of PictoFamilyResource Class", e);
            }
        }
        return result;
    }

    /**
     * update the picto family.
     *
     * @param pictoFamilyRepresentation the picto family representation
     * @return the response
     */
    @POST
    @Path("/updatePictoFamily")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePictoFamily(PictoFamilyRepresentation pictoFamilyRepresentation) {
        try {
            logger.info("Start of updating picto Family ");
            PictoFamily pictoFamily = new PictoFamily();
            PictoFamily temppictoFamily = new PictoFamily();
            UserRepresentation userRepresentaion = new UserRepresentation();
            User user = userFinder.getUser();
            String orgImageLocation = null;
            if (pictoFamilyRepresentation.getFamilyId() != null) {
                pictoFamily = pictoFamilyRepository.load(pictoFamilyRepresentation.getFamilyId());

                BeanUtils.copyProperties(temppictoFamily, pictoFamily);
                orgImageLocation = temppictoFamily.getPictos().get(0).getImageLocation();

                boolean isContributor = securitySupport.hasRole(contributorRole);

                boolean isAdmin = securitySupport.hasRole(admin1Role);
                boolean isDev = securitySupport.hasRole(admin2Role);
                if (isAdmin || isDev) {
                    NotificationContrib notificationObj = notificationContribRepository.getAllNotificationbyFamilyId(pictoFamily.getEntityId());
                    if (notificationObj != null) {
                        notificationContribRepository.delete(notificationObj.getEntityId());
                    }
                }
                if (isContributor) {
                    NotificationContrib notificationObj = notificationContribRepository.getAllNotificationbyUser(user.getId(),
                            pictoFamily.getEntityId());
                    if (notificationObj == null) {
                        NotificationContrib notification = new NotificationContrib();
                        notification.setFamilyID(pictoFamily);
                        notification.setUserId(user);
                        notificationContribRepository.save(notification);
                    }
                }

            }
            /* SN - GL - 318 - 19-Jul-16 - Start */
            userAssembler.doAssembleDtoFromAggregate(userRepresentaion, user);
            pictoFamilyRepresentation.setUser(userRepresentaion);
            /* SN - GL - 318 - 19-Jul-16 - End */
            pictoFamilyAssembler.doMergeAggregateWithDto(pictoFamily, pictoFamilyRepresentation);
            pictoFamilyRepository.savePictoFamily(pictoFamily);
            List<PictoRepresentation> deleteList = pictoFamilyRepresentation.getDeletedPictosList();
            for (PictoRepresentation pictolist : deleteList) {
                String pictoPath;
                if (pictolist.getVersion() == null) {
                    pictoPath = tempFileDirectory + pictolist.getImageLocation() + File.separator + pictolist.getImageLocation() + "_"
                            + pictolist.getVariantType();
                } else {
                    pictoPath = tempFileDirectory + pictolist.getImageLocation() + File.separator + pictolist.getImageLocation() + "_"
                            + pictolist.getVariantType() + "_" + pictolist.getVersion();
                }
                /* GK - GL - To delete variant - 22-Aug-16 - Start */
                File deleteVarType = new File(pictoPath);
                if (deleteVarType.exists()) {
                    FileUtils.forceDelete(deleteVarType);
                } else {
                    logger.debug("Directory to pursue delete does not exist: " + pictoPath);
                }
                /* GK - GL - To delete variant - 22-Aug-16 - End */
            }

            List<SpecificDrawingRepresentation> deleteSpecificDrawList = pictoFamilyRepresentation.getDeletedSpecDrawList();
            String imageLoc = pictoFamily.getPictos().get(0).getImageLocation();
            for (SpecificDrawingRepresentation specificDraw : deleteSpecificDrawList) {
                String specificDrawPath = tempFileDirectory + imageLoc + File.separator + PictoConstants.SPECIFICDRAW + File.separator
                        + specificDraw.getSpecificDrawFile();

                File specDrawFile = new File(specificDrawPath);
                if (specDrawFile.exists()) {
                    FileUtils.forceDelete(specDrawFile);
                }
            }
            if (!pictoFamily.getPictos().get(0).getImageLocation().equals(orgImageLocation)) {
                updateFile(pictoFamily, orgImageLocation);
            }

            PictoFamilyRepresentation updatedPictoFamily = pictoFamilyFinder.getFamilyInfoByRefnum(pictoFamily.getReferenceNum(), user);
            PictoRepresentation updatedPicto = new PictoRepresentation();
            for (int i = 0; i < updatedPictoFamily.getPictos().size(); i++) {
                if (updatedPictoFamily.getPictos().get(i).getIsFrontagePicto())
                    updatedPicto = updatedPictoFamily.getPictos().get(i);
            }

            logger.info("End of updating picto Family ");
            return Response.ok(updatedPicto).build();
        } catch (Exception e) {
            logger.error("Exception while updating picto family ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * create the picto family.
     *
     * @param createPicto the create picto
     * @return the response
     */
    @POST
    @Path("/createPictoFamily")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPictoFamily(CreatePicFamilyRepresentation createPicto) {
        try {
            logger.info("Start of creating picto Family ");
            Long refnum = pictoFamilyFinder.getMaxVersionForReference() + 1;
            createPicto.setReferenceNum(refnum.toString());
            PictoFamily pictoFamily = new PictoFamily();
            User user = userFinder.getUser();
            pictoFamilyAssembler.doMergeAggregateWithDtoCreate(pictoFamily, createPicto);
            PictoFamily pictoFamilyDetails = pictoFamilyRepository.savePictoFamily(pictoFamily);

            List<Picto> pictoList = pictoFamilyDetails.getPictos();
            for (Picto picto : pictoList) {
                Picto pictoDetails = pictoRepository.load(picto.getEntityId());
                pictoClientRepository.addPicto(pictoDetails, user);

            }
            PictoFamilyRepresentation pictoFamilyRepresentation = pictoFamilyFinder.getFamilyInfoByRefnum(refnum.toString(), user);
            logger.info("End of creating picto Family ");
            return Response.ok(pictoFamilyRepresentation).build();
        } catch (Exception e) {
            logger.error("Exception while creating picto family ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Delete the picto family.
     *
     * @param uriInfo the p uri info
     * @return the response
     */

    @POST
    @Path("/deletePictoFamily")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePictoFamily(@Context UriInfo uriInfo) {
        try {
            logger.info("Start of delete picto Family ");
            String refnum = uriInfo.getQueryParameters().getFirst("refnum");
            PictoFamily pictoFamily = pictoFamilyRepository.findAllPictoFamilyByRefNumber(refnum);
            pictoFamilyRepository.delete(pictoFamily);

            String imageLoc = pictoFamily.getPictos().get(0).getImageLocation();

            File file = new File(tempFileDirectory + imageLoc);
            pictoFamilyRepository.delete(pictoFamily);
            logger.info("delete File  : " + file);
            boolean success = PictoClientResource.delete(file);
            if (!success)
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();

            logger.info("End of delete picto Family ");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while deleting picto family ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Delete the picto family.
     *
     * @param uriInfo the p uri info
     * @return the response
     */

    @POST
    @Path("/deletePictoValidation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePictoFamilyValidation(@Context UriInfo uriInfo) {
        try {
            String refnum = uriInfo.getQueryParameters().getFirst("refnum");
            PictoFamily pictoFamily = pictoFamilyRepository.findAllPictoFamilyByRefNumber(refnum);
            List<String> workAdminList;
            List<String> workingAI = new ArrayList<String>();

            if (pictoFamily != null) {
                List<Long> pictoIdList = pictoFinder.getAllPictoId(pictoFamily.getEntityId());
                for (Long picId : pictoIdList) {
                    workAdminList = pictoClientFinder.getWorkingAdminList(picId);
                    workingAI.addAll(workAdminList);
                }
            }
            if (!workingAI.isEmpty()) {
                return Response.ok(workingAI).build();
            }
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while validating delete picto family ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets the single file in PDF format.
     *
     * @return the single file in PDF format
     */
    @GET
    @Path("/getSinglePdf")
    @Produces("application/pdf")
    public Response getSingleFileInPDFFormat() {
        logger.info("Export Single PDF Started in method getSingleFileInPDFFormat Picto Family Resouce Class");
        ResponseBuilder response = null;
        try {
            String fileName = singlePdfname;
            if (fileName == null || fileName.isEmpty()) {
                response = Response.status(Status.BAD_REQUEST);
                return response.build();
            }
            File file = new File(singlePdfPath);
            response = Response.ok(file);
            response.header("Content-Disposition", "attachment; filename=".trim() + singlePdfname.trim());
            logger.info("Export Single PDF Successful in in method getSingleFileInPDFFormat Picto Family Resouce Class");
            return response.build();
        } catch (Exception e) {
            logger.error("Exception in exporting Single PDF File in method getSingleFileInPDFFormat Picto Family Resouce Class", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Gets the multi ple file in PDF format.
     *
     * @return the multi ple file in PDF format
     */
    /*
     * Download Multiple PDF in Browser using REST
     */
    @GET
    @Path("/getMultiplePdf")
    @Produces("application/pdf")
    public Response getMultiPleFileInPDFFormat() {
        logger.info("Export Multiple PDF Started in method getSingleFileInPDFFormat Picto Family Resouce Class");
        ResponseBuilder response = null;
        try {
            String fileName = muliPdfname;
            if (fileName == null || fileName.isEmpty()) {
                response = Response.status(Status.BAD_REQUEST);
                return response.build();
            }
            File file = new File(multiPdfPath);
            response = Response.ok(file);
            response.header("Content-Disposition", "attachment; filename=".trim() + muliPdfname.trim());
            logger.info("Export Multiple PDF Successful in in method getSingleFileInPDFFormat Picto Family Resouce Class");
            return response.build();
        } catch (Exception e) {
            logger.error("Exception in exporting Multiple PDF File in method getSingleFileInPDFFormat Picto Family Resouce Class", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Add to favorite.
     *
     * @param favorite the favorite
     * @return the response
     */
    @POST
    @Path("/addFavoritePicto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response favoritePictoFamily(FavoriteData favorite) {
        try {
            logger.info("Start of adding picto Family in favorite ");
            User user = userFinder.getUser();

            List<String> refNumberList = favorite.getPictoFamily();
            List<PictoFamily> tempPicFamily = new ArrayList<PictoFamily>();
            for (String tempRefNum : refNumberList) {

                PictoFamily picto = pictoFamilyRepository.findAllPictoFamilyByRefNumber(tempRefNum);
                if (picto != null) {
                    tempPicFamily.add(picto);
                }

            }

            List<PictoFamily> listFavorites = user.getUsersListFavorites();
            for (int i = 0; i < tempPicFamily.size(); i++) {
                if (!listFavorites.contains(tempPicFamily.get(i))) {
                    listFavorites.add(tempPicFamily.get(i));
                }
            }

            user.setUsersListFavorites(listFavorites);
            userRepository.saveUser(user);
            logger.info("End of adding picto Family in favorite ");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while updating picto family ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Get all types list.
     *
     * @return the all types
     */
    @GET
    @Path("/getAllTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTypes() {
        List<TypeRepresentation> typeList = pictoFamilyFinder.getAllTypes();
        if (typeList == null || typeList.isEmpty())
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(typeList).build();

    }

    /**
     * Get all types list.
     *
     * @return the all information
     */
    @GET
    @Path("/getAllInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInformation() {
        List<String> informationList = new ArrayList<String>();
        informationList.add(PictoConstants.TYPE_NOTHING);
        informationList.add(PictoConstants.TYPE_VALID);
        informationList.add(PictoConstants.TYPE_INFO);
        informationList.add(PictoConstants.TYPE_WARNING);

        return Response.ok(informationList).build();

    }

    /* SN - GL - 112 - 17-Jul-16 - Start */

    /**
     * upload ai image in file server directory.
     *
     * @param form the form
     * @param refNum the p ref num
     * @param name the p name
     * @param variant the p variant
     * @param version the p version
     * @param refCharte the p ref charte
     * @return the response
     */
    @POST
    @Path("/uploadNewFile/{refNum}/{name}/{variant}/{version}/{refCharte}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadNewFile(FormDataMultiPart form, @PathParam("refNum") String refNum, @PathParam("name") String name,
            @PathParam("variant") String variant, @PathParam("version") String version, @PathParam("refCharte") String refCharte) {
        logger.info("Start upload Image in directory ");
        try {
            FormDataBodyPart filePart = form.getField("file");
            String uploadedFileLocation;
            File baseDirectory;

            InputStream uploadedInputStream = filePart.getValueAs(InputStream.class);
            if ("null".equalsIgnoreCase(refCharte)) {
                baseDirectory = new File(tempFileDirectory + File.separator + refNum + "_" + name);
            } else {
                baseDirectory = new File(tempFileDirectory + File.separator + refNum + "_" + refCharte + name);
            }
            PictoUtils.makeDirectory(baseDirectory);

            StringBuilder sb = new StringBuilder(baseDirectory + File.separator + refNum);
            if (!"null".equalsIgnoreCase(refCharte)) {
                sb.append("_").append(refCharte).append(name).append("_").append(variant);
            } else {
                sb.append("_").append(name).append("_").append(variant);
            }

            if (!"null".equalsIgnoreCase(version)) {
                sb.append("_").append(version);
            }

            File directory = new File(sb.toString());
            PictoUtils.makeDirectory(directory);
            String baseName = directory.getName();
            uploadedFileLocation = directory.getAbsolutePath() + File.separator + baseName + PictoConstants.FILE_AI;
            File uploadedFile = new File(uploadedFileLocation);

            // save it
            PictoClientResource.writeToFile(uploadedInputStream, uploadedFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Error in uploading Pictos Images in  file server", e);
        }
        logger.info("End upload Image in directory ");
        return Response.ok(Status.OK).build();

    }

    /* SN - GL - 112 - 17-Jul-16 - End */

    /* SN - PSA - PRP024006-61 - 17-Aug-16 - Start */

    /**
     * Upload specific draw file.
     *
     * @param form the form
     * @param refNum the p_ref_num
     * @param name the p_name
     * @param refCharte the p ref charte
     * @return the response
     */
    @POST
    @Path("/uploadSpecificDraw/{refNum}/{name}/{refCharte}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSpecificDrawFile(FormDataMultiPart form, @PathParam("refNum") String refNum, @PathParam("name") String name,
            @PathParam("refCharte") String refCharte) {
        logger.info("Start upload Specific Drawing file ----------------------------------: ");
        try {
            FormDataBodyPart filePart = form.getField("file");
            String uploadedFileLocation;
            String filname = filePart.getHeaders().getFirst("Content-Disposition");
            String[] filnameSplitted = filname.split("filename=");
            filname = filnameSplitted[filnameSplitted.length - 1].replace("\"", "");

            InputStream uploadedInputStream = filePart.getValueAs(InputStream.class);

            String fileBaseName = FilenameUtils.getName(filname);
            File dir;
            if ("null".equalsIgnoreCase(refCharte)) {
                dir = new File(tempFileDirectory + File.separator + refNum + "_" + name + File.separatorChar + PictoConstants.SPECIFICDRAW);
            } else {
                dir = new File(
                        tempFileDirectory + File.separator + refNum + "_" + refCharte + name + File.separatorChar + PictoConstants.SPECIFICDRAW);
            }
            PictoUtils.makeDirectory(dir);
            uploadedFileLocation = dir.getAbsolutePath() + File.separatorChar + fileBaseName;

            File uploadedFile = new File(uploadedFileLocation);
            PictoClientResource.writeToFile(uploadedInputStream, uploadedFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Error in uploading Specific Drawing file in  file server", e);
        }

        return Response.ok(Status.OK).build();

    }

    /* SN - PSA - PRP024006-61 - 17-Aug-16 - End */

    /**
     * Update file.
     *
     * @param pictoFamily the picto family
     * @param tempPictoFamily the temp picto family
     * @param orgImageLocation the org image location
     */
    public void updateFile(PictoFamily pictoFamily, String orgImageLocation) {
        StringBuilder oldPath = new StringBuilder();
        StringBuilder newPath = new StringBuilder();
        oldPath.append(tempFileDirectory).append(orgImageLocation);

        if (pictoFamily.getRefCharte() == null) {
            newPath.append(tempFileDirectory).append(pictoFamily.getReferenceNum()).append("_").append(pictoFamily.getName());
        } else {
            newPath.append(tempFileDirectory).append(pictoFamily.getReferenceNum()).append("_").append(pictoFamily.getRefCharte())
                    .append(pictoFamily.getName());
        }

        File baseDir = new File(oldPath.toString());
        PictoUtils.changeFilesOfFolder(baseDir, pictoFamily.getRefCharte(), pictoFamily.getName());
        PictoUtils.changeSubFolder(baseDir, pictoFamily.getRefCharte(), pictoFamily.getName());
        baseDir.renameTo(new File(newPath.toString()));

    }

    /**
     * Removes the shop cart picto.
     *
     * @param favorite the favorite
     * @return the response
     */
    @POST
    @Path("/removeFavoritePicto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFavoritePicto(FavoriteData favorite) {
        try {
            logger.info("Start remove favorite picto ");
            User user = userFinder.getUser();
            List<String> refNumberList = favorite.getPictoFamily();
            List<PictoFamily> tempPictoFamily = new ArrayList<PictoFamily>();

            for (String tempRefNum : refNumberList) {
                PictoFamily pictoFamily = pictoFamilyRepository.findAllPictoFamilyByRefNumber(tempRefNum);
                if (pictoFamily != null) {
                    tempPictoFamily.add(pictoFamily);
                    pictoFamilyFinder.removeFavorite(user.getId(), pictoFamily.getEntityId());
                }
            }
            logger.info("End remove favorite picto ");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while remove item from cart ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

}
