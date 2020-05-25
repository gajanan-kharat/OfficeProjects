package com.inetpsa.pv2.rest.pictoclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.image.ImageType;
import org.seedstack.pv2.domain.image.ImageTypeRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictoclient.PictoClientRepository;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserFactory;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.user.UserFinder;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * The Class PictoClientResource.
 */
@Path("/thickclients")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PictoClientResource {

    /** The m picto client finder. */
    @Inject
    private PictoClientFinder pictoClientFinder;

    /** The user repository. */
    @Inject
    private UserRepository userRepository;

    /** The image type repository. */
    @Inject
    private ImageTypeRepository imageTypeRepository;

    /** The picto repository. */
    @Inject
    private PictoRepository pictoRepository;

    /** The picto client repository. */
    @Inject
    private PictoClientRepository pictoClientRepository;

    /** The logger. */
    @Logging
    private static Logger logger = LoggerFactory.getLogger(PictoClientResource.class);

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    /** The m user factory. */
    @Inject
    UserFactory userFactory;

    /** The m user finder. */
    @Inject
    UserFinder userFinder;

    /**
     * Get the picto clients.
     * 
     * @param info the info
     * @return the picto by id
     */
    @GET
    @Path("/getAllPictoClient")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPictoById(@Context UriInfo info) {
        List<PictoClientRepresentation> listPicto = null;
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices.");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));

            listPicto = pictoClientFinder.getPictoById(userId);
            if (listPicto == null) {
                logger.debug("Null List in method getPictoById of PictoClientResource");

            } else {
                logger.debug("List of picto ids from DB." + listPicto.toString());
                logger.info("Setting Picto in getPictoById method  of PictoClientResource using Rest WebServices completed sucessfully...");

            }

        } catch (Exception e) {

            logger.error("Error in getting Pictos by ID in  method getPictoById of PictoClientResource class using Rest WebServices failed ", e);

        }
        return Response.ok(listPicto).build();
    }

    /**
     * Get all pictos.
     * 
     * @param info the info
     * @return the all picto list
     */
    @GET
    @Path("/getAllPictoList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPictoList(@Context UriInfo info) {
        List<PictoClientRepresentation> listPicto = null;
        try {
            logger.info("Start method getAllPictoList for setting Picto IDs in PictoClientResource class using Rest Webservices");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));

            listPicto = pictoClientFinder.getAllPictosById(userId);
            pictoClientRepository.resetOpenImgFlag(userId);
            if (listPicto == null) {
                logger.debug("Null List in method getAllPictoList of PictoClientResource");

            } else {
                logger.info("setting Picto IDs in getAllPictoList method  of PictoClientResource using Rest WebServices completed sucessfully...");

            }
        } catch (Exception e) {

            logger.error("Error in setting Picto IDs in  method getAllPictoList of PictoClientResource class using Rest WebServices failed ", e);

        }
        return Response.ok(listPicto).build();
    }

    /**
     * Get the picto clients ws.
     * 
     * @param info the info
     * @return the picto client
     */
    @GET
    @Path("/getPictoClient")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPictoClient(@Context UriInfo info) {
        List<PictoClientRepresentation> listPicto = null;
        try {
            logger.info("Start method getPictoClient for setting Picto Information in PictoClientResource class using Rest Webservices");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));
            Long pictoId = Long.parseLong(info.getQueryParameters().getFirst("picto_id"));
            listPicto = pictoClientFinder.getPictoClientInfo(userId, pictoId);
            if (listPicto == null) {
                logger.debug("Null List in method getPictoClient of PictoClientResource");

            } else {
                logger.info("Setting Picto Information in getPictoClient method  of PictoClientResource using Rest WebServices completed sucessfully...");

            }

        } catch (Exception e) {

            logger.error("Error in getting Pictos Information in  method getPictoClient of PictoClientResource class using Rest WebServices failed ",
                    e);

        }
        return Response.ok(listPicto).build();
    }

    /**
     * Gets the working admin list.
     * 
     * @param info the info
     * @return the working admin list
     */
    @GET
    @Path("/getWorkAdminList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkingAdminList(@Context UriInfo info) {
        List<String> workAdminList = null;
        try {
            logger.info("Start method getWorkingAdminList for setting Admin List in PictoClientResource class using Rest Webservices");
            Long pictoId = Long.parseLong(info.getQueryParameters().getFirst("picto_id"));
            workAdminList = pictoClientFinder.getWorkingAdminList(pictoId);

            if (workAdminList == null) {
                logger.debug("Null List in method getWorkingAdminList of PictoClientResource");

            } else {
                logger.info(
                        "Setting Admin List in getWorkingAdminList method  of PictoClientResource using Rest WebServices completed sucessfully...");

            }

        } catch (Exception e) {

            logger.error("Error in getting Admin List in  method getWorkingAdminList of PictoClientResource class using Rest WebServices failed ", e);

        }
        return Response.ok(workAdminList).build();
    }

    /**
     * Reset the download flags.
     * 
     * @param info the info
     * @return the response
     */

    @GET
    @Path("/resetFlag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetDownloadFlag(@Context UriInfo info) {
        try {
            logger.info("Start method resetDownloadFlag for setting Download Flag in PictoClientResource class using Rest Webservices");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));
            Long pictoId = Long.parseLong(info.getQueryParameters().getFirst("picto_id"));

            User user = userRepository.getUserById(userId);
            Picto picto = pictoRepository.load(pictoId);
            pictoClientRepository.resetDownloadFlag(user, picto);
            logger.debug("resetFlag finished.");
            logger.info("Setting Download Flag in resetDownloadFlag method  of PictoClientResource using Rest WebServices completed sucessfully...");

        } catch (Exception e) {
            logger.error("Error in getting Pictos by ID in  method resetDownloadFlag of PictoClientResource class using Rest WebServices failed ", e);
        }
        return Response.ok().build();
    }

    /**
     * Delete the pictos.
     * 
     * @param info the info
     * @return the response
     */

    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePicto(@Context UriInfo info) {
        try {
            logger.info(" Start calling the service delete picto client ");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));
            Long pictoId = Long.parseLong(info.getQueryParameters().getFirst("picto_id"));
            User user = userRepository.getUserById(userId);
            Picto picto = pictoRepository.load(pictoId);
            pictoClientRepository.deletePictoClient(user, picto);
            logger.debug("Picto Deleted.");
            logger.info("Deleting Pictos in deletePicto method  of PictoClientResource using Rest WebServices completed sucessfully...");

        } catch (Exception e) {
            logger.error("Error in deleting Pictos by method deletePicto of PictoClientResource class using Rest WebServices failed ", e);
        }
        return Response.ok().build();
    }

    /**
     * Update the AI files.
     * 
     * @param info the info
     * @return the response
     */

    @GET
    @Path("/updateAIFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAIFileInfo(@Context UriInfo info) {
        try {
            logger.info("Start method updateAIFileInfo for updating AI File Info in PictoClientResource class using Rest Webservices");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));
            Long pictoId = Long.parseLong(info.getQueryParameters().getFirst("picto_id"));
            User user = userRepository.getUserById(userId);
            Picto picto = pictoRepository.load(pictoId);
            pictoRepository.updateAIFileInfo(user, picto);
            pictoClientRepository.deletePictoClient(user, picto);
            logger.debug("updateAIFile finished .");
            logger.info("Updating AI File Info in updateAIFileInfo method  of PictoClientResource using Rest WebServices completed sucessfully...");
        } catch (Exception e) {
            logger.error("Error in Updating AI File  method updateAIFileInfo of PictoClientResource class using Rest WebServices failed ", e);
        }
        return Response.ok().build();
    }

    /**
     * Set the thick client flag.
     * 
     * @param info the info
     * @return the response
     */

    @GET
    @Path("/launchFlag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setThickClientLaunchFlag(@Context UriInfo info) {
        try {
            logger.info("Start method setThickClientLaunchFlag for setting Thick Client Launch Flag in PictoClientResource class using Rest Webservices");
            logger.debug(" Start calling the service set thick client flag ");
            Long userId = Long.parseLong(info.getQueryParameters().getFirst("user_id"));
            boolean isThickClient = Boolean.valueOf(info.getQueryParameters().getFirst("is_thickClient"));
            User user = userRepository.getUserById(userId);
            userRepository.setThickClientLaunchFlag(user, isThickClient);
            logger.debug("launchFlag finished .");
            logger.info("Setting Thick Client Launch Flag  in setThickClientLaunchFlag method  of PictoClientResource using Rest WebServices completed sucessfully...");
        } catch (Exception e) {
            logger.error(    "Error in getting Thick Client Launch Flag in  method setThickClientLaunchFlag of PictoClientResource class using Rest WebServices failed ",
                    e);

        }
        return Response.ok().build();
    }

    /**
     * Login Authentication.
     * 
     * @param info the info
     * @return the response
     */
    @GET
    @Path("/loginAuthentication")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userAuthentication() {
        User loggedInUser;
        Long loggedInUserId;
        logger.info("Start method userAuthentication using LDAP in PictoClientResource class using Rest Webservices");

        loggedInUser = userFinder.getUser();
        loggedInUserId = loggedInUser.getId();

        logger.info(
                "LDAP Authentication in userAuthentication method completed of PictoClientResource using Rest WebServices completed sucessfully..."
                        + loggedInUserId);
        return Response.ok(loggedInUserId).build();
    }

    /**
     * Upload the file.
     * 
     * @param form the form
     * @param fileName the file name
     * @param pictoId the picto id
     * @return the response
     */
    @POST
    @Path("/uploadFile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImageFile(FormDataMultiPart form, @QueryParam("name") String fileName, @QueryParam("picto_id") String pictoId) {

        try {
            logger.info("Start method uploadImageFile for Uploading Picto Images in PictoClientResource class using Rest Webservices");
            FormDataBodyPart filePart = form.getField("file");
            ContentDisposition contentDisposition = filePart.getContentDisposition();
            InputStream uploadedInputStream = filePart.getValueAs(InputStream.class);
            String baseFileName = FilenameUtils.getBaseName(contentDisposition.getFileName());
            int indexn = StringUtils.ordinalIndexOf(baseFileName, "_", 2);
            String folderName = baseFileName.substring(0, indexn);
            String uploadedFileLocation = tempFileDirectory + folderName + File.separator + baseFileName + File.separator
                    + contentDisposition.getFileName();
            // Write the zip file to directory
            writeToFile(uploadedInputStream, uploadedFileLocation);
            // unzip the file
            unZipIt(uploadedFileLocation, tempFileDirectory + folderName + File.separator + baseFileName, pictoId);

            // Clean temp server
            try {
                logger.info("Uploading Picto Images in uploadImageFile for setting Picto in PictoClientResource class using Rest Webservices");

                File uploadFile = new File(uploadedFileLocation);
                delete(uploadFile);
            } catch (Exception e) {
                logger.error("Upload : delete " + uploadedFileLocation + " : " + e);
            }

            logger.info("Uploading Picto Images in  method  uploadImageFile of PictoClientResource using Rest WebServices completed sucessfully...");

        } catch (Exception e) {

            logger.error("Error in uploading Pictos Images in  method uploadImageFile of PictoClientResource class using Rest WebServices failed ",
                    e);

        }
        return Response.ok(Status.OK).build();
    }

    /**
     * Write to file.
     * 
     * @param uploadedInputStream the uploaded input stream
     * @param uploadedFileLocation the uploaded file location
     */
    public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        OutputStream out = null;
        try {
            logger.info("Start method writeToFile for writing Picto Information in PictoClientResource class using Rest Webservices");
            try {
                out = new FileOutputStream(new File(uploadedFileLocation));
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = uploadedInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                uploadedInputStream.close();
                logger.info(
                        "Writing Picto Information  in writeToFile method  of PictoClientResource using Rest WebServices completed sucessfully...");
            } catch (IOException e) {
                logger.error("Error while uploading in writeToFile method of writeToFile of PictoClient Resource File", e);

            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error(
                                "Error in writing Pictos Images in  method writeToFile of PictoClientResource class using Rest WebServices failed ",
                                e);

                    }
                }
            }

        } catch (Exception e) {

            logger.error("Exception occured in writing to file in writeToFile method of Picto Client Resource", e);
        }

    }

    /**
     * Unzip it.
     * 
     * @param zipFile input zip file
     * @param outputFolder the output folder
     * @param pictoId the picto id
     */
    public void unZipIt(String zipFile, String outputFolder, String pictoId) {
        ImageType imageType = new ImageType();
        Picto picto = pictoRepository.load(Long.parseLong(pictoId));
        imageType.setPictoId(picto);

        byte[] buffer = new byte[1024];

        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");

            // create output directory is not exists
            File folder = new File(tempFileDirectory);
            if (!folder.exists()) {
                folder.mkdir();
            }

            // get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

                if (ext != null) {
                    if (ext.trim().equals(PictoConstants.AI_IMG.trim())) {
                        imageType.setImageAIWork(true);
                    } else if (ext.trim().equals(PictoConstants.JPG_IMG.trim())) {
                        imageType.setImageJpg(true);
                    } else if (ext.trim().equals(PictoConstants.PNG_IMG.trim())) {
                        imageType.setImagePng(true);
                    } else if (ext.trim().equals(PictoConstants.IGS_IMG.trim())) {
                        imageType.setImageIgs(true);
                    } else if (ext.trim().equals(PictoConstants.AI_PUBLIC_FILE.trim())) {
                        imageType.setImageAIPublic(true);
                    }

                } else {
                    logger.debug("No image file in unZipIt.");
                }

                logger.info("File unzip :" + newFile.getAbsoluteFile());
                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                if (newFile.isDirectory()) {
                    if (!newFile.exists()) {
                        newFile.mkdir();
                    }
                } else {
                    new File(newFile.getParent()).mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }
            imageTypeRepository.updateImageType(imageType);
            zis.closeEntry();
            zis.close();
            logger.info("End unzip : ");
        } catch (IOException ex) {
            logger.error("Error in unZipIt: ", ex);
        }

    }

    /**
     * Delete the file or directory given by the supplied reference. This method works on a directory that is not empty, unlike the
     * {@link File#delete()} method.
     * 
     * @param fileOrDirectory the reference to the Java File object that is to be deleted
     * @return true if the supplied file or directory existed and was successfully deleted, or false otherwise
     */
    public static boolean delete(File fileOrDirectory) {
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");
            if (fileOrDirectory == null)
                return false;
            if (!fileOrDirectory.exists())
                return false;

            // The file/directory exists, so if a directory delete all of the
            // contents ...
            if (fileOrDirectory.isDirectory()) {
                for (File childFile : fileOrDirectory.listFiles()) {
                    delete(childFile); // recursive call (good enough for now until
                }
                // Now an empty directory ...
            }
            logger.info("Setting Picto in getPictoById method completed of PictoClientResource using Rest WebServices completed sucessfully");
            // Whether this is a file or empty directory, just delete it ...

        } catch (Exception e) {

            logger.error(
                    "Error in getting Pictos by ID in  method getPictoById of PictoClientResource class using Rest WebServices completed sucessfully ",
                    e);

        }
        return fileOrDirectory.delete();
    }

    /**
     * Download the file.
     * 
     * @param pictoName the picto name
     * @return the response
     * @throws FileNotFoundException the file not found exception
     */
    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downLoadFile(@QueryParam("name") String pictoName) throws FileNotFoundException {
        String url = null;
        File directoryToZip = null;
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");
            int indexn = StringUtils.ordinalIndexOf(pictoName, "_", 2);
            String folderName = pictoName.substring(0, indexn);
            File file = new File(pictoName);
            url = tempFileDirectory + folderName;
            File imageFile = new File(url);
            directoryToZip = new File(url + File.separator + file.getName());
            List<File> result = searchImage(url, pictoName);

            if (result.isEmpty()) {
                logger.info("**** There is no images at given path " + imageFile);
            } else {
                writeZipFile(directoryToZip, result);
            }

            logger.info("Setting Picto in getPictoById method completed of PictoClientResource using Rest WebServices completed sucessfully");

        } catch (Exception e) {
            logger.error("Exception Occurred while download files:" + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(new FileInputStream(directoryToZip + ".zip"), "application/zip")
                .header("Content-Disposition", "attachment; filename=\"" + directoryToZip + ".zip").build();
    }

    /**
     * Search Image in directory.
     * 
     * @param dir the dir
     * @param fileName the file name
     * @return the list
     */
    public List<File> searchImage(String dir, String fileName) {
        List<File> result = null;
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");
            String extFilename = dir + File.separator + fileName + File.separator + fileName + PictoConstants.FILE_AI;
            File file = new File(extFilename);
            result = new ArrayList<File>();

            if (file.exists()) {
                result.add(file);
                extFilename = dir + File.separator + fileName + File.separator + fileName + PictoConstants.FILE_JPG;
                file = new File(extFilename);
                if (file.exists()) {
                    result.add(file);
                }
            }
            logger.info("Setting Picto in getPictoById method completed of PictoClientResource using Rest WebServices completed sucessfully.");

        } catch (Exception e) {

            logger.error(
                    "Error in getting Pictos by ID in  method getPictoById of PictoClientResource class using Rest WebServices completed sucessfully ",
                    e);

        }
        return result;
    }

    /**
     * writeZipFile.
     * 
     * @param directoryToZip the directory to zip
     * @param fileList the file list
     */
    private static void writeZipFile(File directoryToZip, List<File> fileList) {

        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");

            /* If already exist delete file. */
            File zipFile = new File(directoryToZip.getAbsoluteFile() + PictoConstants.ZIP_FILE);

            if (zipFile.exists()) {
                zipFile.delete();
            }
            fos = new FileOutputStream(directoryToZip.getAbsolutePath() + PictoConstants.ZIP_FILE);

            zos = new ZipOutputStream(fos);

            for (File file : fileList) {
                // we only zip files, not directories
                if (!file.isDirectory()) {
                    addToZip(directoryToZip, file, zos);
                }
            }
            logger.info("Setting Picto in getPictoById method completed of PictoClientResource using Rest WebServices completed sucessfully...");
        } catch (FileNotFoundException e) {
            logger.error(" Exception while write to zip file ", e);

        } catch (IOException e) {
            logger.error(" Exception while write to zip file ", e);

        } finally {
            try {
                if (zos != null)
                    zos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                logger.error(" Exception while closing the file streams  in method writeZipFile of PictoClientResource", e);
            }

        }

    }

    /**
     * Add to zip.
     * 
     * @param directoryToZip the directory to zip
     * @param file the file
     * @param zos the zos
     * @throws FileNotFoundException the file not found exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

        FileInputStream fis = null;
        try {
            logger.info("Start method getPictoById for setting Picto in PictoClientResource class using Rest Webservices");
            fis = new FileInputStream(file);

            String zipFilePath = file.getName();
            ZipEntry zipEntry = new ZipEntry(zipFilePath);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            logger.info("Setting Picto in getPictoById method completed of PictoClientResource using Rest WebServices completed sucessfully...");
        } catch (Exception e) {

            logger.error("Error in getting Pictos by ID in  method getPictoById of PictoClientResource class using Rest WebServices failed ", e);

        }

        finally {

            if (zos != null) {
                zos.closeEntry();
            }

            if (fis != null) {
                fis.close();
            }

        }
    }

    /**
     * Delete the pictos.
     * 
     * @param info the info
     * @return the response
     */
    @GET
    @Path("/deleteZipFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteZipFile(@Context UriInfo info) {
        logger.info(" Start calling the service delete zip file from server ");
        String fileName = info.getQueryParameters().getFirst("name");
        String folderName = fileName.substring(0, StringUtils.ordinalIndexOf(fileName, "_", 2));
        String zipFilePath = tempFileDirectory + folderName + File.separator + fileName + ".zip";
        ZipFile zFile;

        try {
            zFile = new ZipFile(zipFilePath);
            zFile.close();
            File file = new File(zipFilePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            logger.error("Erro in deleteZipFile of PictoClientResource", e);

        }

        return Response.ok().build();

    }

}
