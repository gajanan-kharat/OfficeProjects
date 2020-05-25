/*
 * 
 */
package com.inetpsa.pv2.rest.picto;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
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
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictoclient.PictoClientRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.infrastructure.finders.DownloadAIWorkFinder;
import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.download.DownloadAIWorkRepresentation;
import com.inetpsa.pv2.rest.download.DownloadPictosRepresentation;
import com.inetpsa.pv2.rest.pictoclient.PictoClientFinder;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryFinder;
import com.inetpsa.pv2.rest.supercategory.SuperCategoryRepresentation;
import com.inetpsa.pv2.rest.user.UserFinder;

/**
 * Class: Picto Resource.
 */
@Path("/picto")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PictoResource {

    /** The picto repository. */
    @Inject
    private PictoRepository pictoRepository;

    /** The picto family repository. */
    @Inject
    private PictoFamilyRepository pictoFamilyRepository;

    /** The picto finder. */
    @Inject
    private PictoFinder pictoFinder;

    /** The user repository. */
    @Inject
    private UserRepository userRepository;

    /** The picto client repository. */
    @Inject
    private PictoClientRepository pictoClientRepository;

    /** The notification contrib repository. */
    @Inject
    private NotificationContribRepository notificationContribRepository;

    /** The m picto client finder. */
    @Inject
    private PictoClientFinder pictoClientFinder;

    /** The m user finder. */
    @Inject
    UserFinder userFinder;

    /** The super category finder. */
    @Inject
    SuperCategoryFinder superCategoryFinder;

    /** The picto family finder. */
    @Inject
    private PictoFamilyFinder pictoFamilyFinder;

    /** The logger. */
    @Logging
    private static Logger logger = LoggerFactory.getLogger(PictoResource.class);

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /** The web urlhosppath. */
    @Configuration("com.inetpsa.pv2.host.hostUrl")
    private String webUrlhosppath;

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;
    /** The admin 1 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole1")
    private String admin1Role;

    /** The admin 2 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole2")
    private String admin2Role;

    /**
     * Get all picto.
     *
     * @return the all pictos
     */
    @GET
    @Path("/getAllPictos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPictos() {
        User user = userFinder.getUser();
        List<PictoRepresentation> listPicto = pictoFinder.getAllPictos(user);
        if (listPicto == null || listPicto.isEmpty())
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(listPicto).build();

    }

    /**
     * Gets the shopping cart item.
     *
     * @param data the data
     * @return the shopping cart item
     */
    @POST
    @Path("/addCartPicto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShopCartPicto(CartData data) {
        try {
            User user = userFinder.getUser();
            List<Long> pictoIds = data.getPictoId();
            List<Picto> tempPictoList = new ArrayList<Picto>();
            List<Picto> listShopCart = user.getUsersListShopCarts();

            if (!pictoIds.isEmpty()) {
                for (Long picId : pictoIds) {
                    Picto picto = pictoRepository.getPictoById(picId);
                    if (picto != null) {
                        tempPictoList.add(picto);
                    }
                }
                for (int i = 0; i < tempPictoList.size(); i++) {
                    if (!listShopCart.contains(tempPictoList.get(i))) {
                        listShopCart.add(tempPictoList.get(i));
                    }
                }
            }
            user.setUsersListShopCarts(listShopCart);
            userRepository.saveUser(user);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while updating picto in Shopping cart ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Get list of pictos in cart for logged in user.
     *
     * @return the pictos in cart
     */
    @GET
    @Path("/getPictosInCart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPictosInCart() {
        User user = userFinder.getUser();
        List<PictoRepresentation> listPicto = pictoFinder.getPictosInCart(user);

        List<BigInteger> pictosFavList = pictoFamilyFinder.getFavPictoIDbyUser(user.getId());
        List<BigInteger> pictosCartList = pictoFamilyFinder.getCartPictoIDbyUser(user.getId());

        for (int i = 0; i < listPicto.size(); i++) {
            Long id = listPicto.get(i).getFamilyID().getFamilyId();
            BigInteger pictoFamilyId = BigInteger.valueOf(id.intValue());
            if (pictosFavList.contains(pictoFamilyId)) {
                listPicto.get(i).getFamilyID().setFavoriteFlag(Boolean.TRUE);
            }

            Long pictoId = listPicto.get(i).getId();
            BigInteger bi = BigInteger.valueOf(pictoId.intValue());
            if (pictosCartList.contains(bi)) {
                listPicto.get(i).setCartFlag(Boolean.TRUE);
            }
        }
        return Response.ok(listPicto).build();
    }

    /**
     * Get all contributor notification.
     *
     * @return the response
     */
    @GET
    @Path("/notificationContrib")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notification() {
        List<PictoRepresentation> listPicto = pictoFinder.getAllNotification();
        return Response.ok(listPicto).build();

    }

    /**
     * Download AI working file.
     *
     * @param pictoIds the picto ids
     * @return the response
     */
    @GET
    @Path("/downloadAIWork")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadAIWork(@QueryParam("pictoIds") List<Long> pictoIds) {
        logger.info("Start fetching AI work file information.");
        Picto picto;
        Long pictoId;
        User user = userFinder.getUser();
        List<DownloadAIWorkRepresentation> downloadAIWorkList = new ArrayList<DownloadAIWorkRepresentation>();
        List<Long> listPicto = new ArrayList<Long>();
        logger.info("Downloading AI work file for picto Id: " + pictoIds);
        if (user.getIsThickClient()) {
            if (pictoIds != null) {
                for (int i = 0; i < pictoIds.size(); i++) {
                    pictoId = pictoIds.get(i);
                    picto = pictoRepository.load(pictoId);
                    if (pictoId != null && user.getId() != null) {
                        listPicto = pictoClientFinder.getDownloadedPictoInfo(user.getId(), pictoId);
                    }
                    DownloadAIWorkRepresentation downloadAIWorkRepresentation = new DownloadAIWorkRepresentation();
                    downloadAIWorkRepresentation.setPictoId(pictoId);
                    if (picto.getVersion() != null) {
                        if (picto.getPictoFamilyID().getRefCharte() != null) {
                            downloadAIWorkRepresentation.setPictoName(picto.getPictoFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                    + picto.getPictoFamilyID().getRefCharte() + picto.getPictoFamilyID().getName()
                                    + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType() + PictoConstants.PICTO_NAME_SEPARATOR
                                    + picto.getVersion());
                        } else {
                            downloadAIWorkRepresentation.setPictoName(picto.getPictoFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                    + picto.getPictoFamilyID().getName() + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType()
                                    + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVersion());
                        }
                    } else {
                        if (picto.getPictoFamilyID().getRefCharte() != null) {
                            downloadAIWorkRepresentation.setPictoName(picto.getPictoFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                    + picto.getPictoFamilyID().getRefCharte() + picto.getPictoFamilyID().getName()
                                    + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType());
                        } else {
                            downloadAIWorkRepresentation.setPictoName(picto.getPictoFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                    + picto.getPictoFamilyID().getName() + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType());
                        }
                    }
                    try {
                        if (listPicto == null || listPicto.isEmpty()) {
                            downloadAIWorkRepresentation.setDisableLocalVersion(true);
                        } else {
                            downloadAIWorkRepresentation.setDisableLocalVersion(false);
                        }
                    } catch (Exception e) {
                        logger.error("Exception occurres while checking data for picto  ", e);
                    }
                    downloadAIWorkRepresentation
                            .setWorkAdminList(pictoClientFinder.getWorkingAdminList(picto.getEntityId()).toString().replaceAll("\\[|\\]", " "));
                    if (!pictoClientFinder.getWorkingAdminList(picto.getEntityId()).isEmpty()) {
                        downloadAIWorkRepresentation.setWorkAdmins(true);
                    }
                    downloadAIWorkList.add(downloadAIWorkRepresentation);
                }
            } else {
                logger.debug("List of selected picto Ids is null.");
            }
        } else {
            logger.error("User has to first launch Thick Client atleast once to download AI work file.");
            return Response.status(420).build();
        }
        return Response.ok(downloadAIWorkList).build();

    }

    /**
     * Insert data for AI file download in DB.
     * 
     * @param downloadAIWorkFinder the download AI work finder
     * @return the response
     */
    @POST
    @Path("/validateAIWorkDownload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateAIWorkDownload(DownloadAIWorkFinder downloadAIWorkFinder) {
        logger.info("Start updating AI work file download flag.");
        if (downloadAIWorkFinder != null) {
            List<DownloadAIWorkRepresentation> downloadAIWorkRepresentationList = downloadAIWorkFinder.getDownloadAIWorkRepresentation();
            DownloadAIWorkRepresentation downloadAIWorkRepresentation;
            Long pictoId;
            Picto picto;
            User user = userFinder.getUser();

            if (downloadAIWorkRepresentationList != null) {
                for (int i = 0; i < downloadAIWorkRepresentationList.size(); i++) {
                    downloadAIWorkRepresentation = downloadAIWorkRepresentationList.get(i);
                    pictoId = downloadAIWorkRepresentation.getPictoId();
                    if (downloadAIWorkRepresentation.isDownloadDBVersion() && pictoId != null) {
                        picto = pictoRepository.load(pictoId);
                        pictoClientRepository.addPicto(picto, user);
                    }
                    if (downloadAIWorkRepresentation.isOpenLocalVrsn() && pictoId != null) {
                        boolean isOpenLocalImgFlag = true;
                        picto = pictoRepository.load(pictoId);
                        pictoClientRepository.updateOpenLocalImgFlag(picto, user, isOpenLocalImgFlag);
                    }
                }
            }
        }
        return Response.ok().build();

    }

    /**
     * Download pictos and selected variants.
     * 
     * @param downloadPictosRepresentation the download pictos representation
     * @return the response
     */
    @POST
    @Path("/downloadPicto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadPicto(DownloadPictosRepresentation downloadPictosRepresentation) {
        logger.info("Inside Resource to Download Picto.");
        Long pictoId;
        List<File> result = new ArrayList<File>();
        List<String> l = new ArrayList<String>();
        File directoryToZip = new File(tempFileDirectory + PictoConstants.DOWNLOAD_ZIP_NAME.trim());
        if (downloadPictosRepresentation.getPictoIds() != null) {
            try {
                for (int p = 0; p < downloadPictosRepresentation.getPictoIds().size(); p++) {
                    pictoId = Long.parseLong(downloadPictosRepresentation.getPictoIds().get(p));
                    logger.info("Downloading picto for picto id : " + pictoId);
                    Picto picto = pictoRepository.load(pictoId);
                    if (downloadPictosRepresentation.getVariants() != null) {
                        for (int i = 0; i < downloadPictosRepresentation.getVariants().size(); i++) {
                            String variant = downloadPictosRepresentation.getVariants().get(i);
                            if (downloadPictosRepresentation.getImageType() != null) {
                                for (int j = 0; j < downloadPictosRepresentation.getImageType().size(); j++) {
                                    String imgType = downloadPictosRepresentation.getImageType().get(j);
                                    if (downloadPictosRepresentation.getPictoIds().size() > 1) {
                                        downloadMultipleImgs(result, picto, variant, imgType);
                                    } else {
                                        downloadPictoImg(result, picto, variant, imgType);
                                    }

                                }
                            }
                        }
                    } else {
                        logger.debug("No Picto variant selected to download Picto image.");
                    }
                }
                if (!result.isEmpty()) {
                    sendZipFileToBrowser(result, directoryToZip);
                } else {
                    logger.debug("There is no images to download ");
                    return Response.status(420).build();
                }
            } catch (Exception e) {
                logger.error("Exception in downloading pictos", e);
                return Response.status(501).build();
            }
            l.add(webUrlhosppath + PictoConstants.PICTO_ZIP_TO_DOWNLOAD);
        }
        return Response.ok(l).build();
    }

    /**
     * Send zip file to browser.
     * 
     * @param result the result
     * @param directoryToZip the directory to zip
     */
    private void sendZipFileToBrowser(List<File> result, File directoryToZip) {
        writeZipFile(directoryToZip, result);

    }

    /**
     * Create list of all pictos to be downloaded.
     * 
     * @param result the result
     * @param picto the picto
     * @param variant the variant
     * @param imgType the img type
     */
    private void downloadMultipleImgs(List<File> result, Picto picto, final String variant, String imgType) {
        final String folderPath = picto.getImageLocation();
        String filePath = null;

        File[] directories = new File(tempFileDirectory + folderPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        if (directories != null) {
            for (int x = 0; x < directories.length; x++) {
                if (directories[x].getAbsolutePath().contains(variant)) {
                    if (PictoConstants.AI_IMG.equals(imgType)) {
                        File directory = new File(directories[x].getAbsolutePath());
                        filePath = getFilePath(directory, folderPath, variant, imgType);

                    }
                    if (PictoConstants.PNG_IMG.equals(imgType)) {
                        File directory = new File(directories[x].getAbsolutePath());
                        filePath = getFilePath(directory, folderPath, variant, imgType);

                    }
                    if (PictoConstants.AIW_IMG.equals(imgType)) {
                        File directory = new File(directories[x].getAbsolutePath());
                        filePath = getFilePath(directory, folderPath, variant, imgType);
                    }
                    if (PictoConstants.JPG_IMG.equals(imgType)) {
                        File directory = new File(directories[x].getAbsolutePath());
                        filePath = getFilePath(directory, folderPath, variant, imgType);
                    }
                    if (PictoConstants.IGS_IMG.equals(imgType)) {
                        File directory = new File(directories[x].getAbsolutePath());
                        filePath = getFilePath(directory, folderPath, variant, imgType);
                    }
                }
                if (filePath != null) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        result.add(file);
                    }
                }
            }

        }
    }

    /**
     * Download picto img.
     * 
     * @param result the result
     * @param picto the picto
     * @param variant the variant
     * @param imgType the img type
     */
    private void downloadPictoImg(List<File> result, Picto picto, final String variant, String imgType) {
        final String folderPath;
        folderPath = picto.getImageLocation();
        String innerFolder = picto.getImageLocation() + PictoConstants.PICTO_NAME_SEPARATOR + variant;
        String fileNameToDwnld = folderPath + PictoConstants.PICTO_NAME_SEPARATOR + variant + "." + imgType;
        String filePath = tempFileDirectory + folderPath + File.separator + innerFolder + File.separator + fileNameToDwnld;

        if (PictoConstants.AI_IMG.equals(imgType)) {
            String newAIFile = fileNameToDwnld.substring(0, fileNameToDwnld.lastIndexOf('.')).concat(PictoConstants.AIP_EXT)
                    .concat(PictoConstants.FILE_AI);
            filePath = tempFileDirectory + folderPath + File.separator + innerFolder + File.separator + newAIFile;
        }

        if (PictoConstants.AIW_IMG.equals(imgType)) {
            String newAIFile = folderPath + PictoConstants.PICTO_NAME_SEPARATOR + variant + PictoConstants.FILE_AI;
            filePath = tempFileDirectory + folderPath + File.separator + innerFolder + File.separator + newAIFile;
        }

        File file = new File(filePath);
        if (file.exists()) {
            result.add(file);

        }
    }

    /**
     * Gets the file path.
     * 
     * @param directory the directory
     * @param folderPath the folder path
     * @param variant the variant
     * @param imgType the img type
     * @return the file path
     */
    public String getFilePath(File directory, final String folderPath, final String variant, String imgType) {
        String filePath = null;
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String imageName) {
                return imageName.startsWith(folderPath + PictoConstants.PICTO_NAME_SEPARATOR + variant);

            }
        });
        for (int k = 0; k < files.length; k++) {
            String ext = FilenameUtils.getExtension(files[k].getAbsolutePath());

            if (PictoConstants.AI_IMG.equals(imgType) && PictoConstants.AI_IMG.equals(ext) && files[k].getName().contains(PictoConstants.AIP_EXT)) {
                filePath = files[k].getAbsolutePath();

            }
            if (PictoConstants.AIW_IMG.equals(imgType) && PictoConstants.AI_IMG.equals(ext) && !files[k].getName().contains(PictoConstants.AIP_EXT)) {
                filePath = files[k].getAbsolutePath();
            }
            if ((PictoConstants.IGS_IMG.equals(imgType) && PictoConstants.IGS_IMG.equals(ext))
                    || (PictoConstants.PNG_IMG.equals(imgType) && PictoConstants.PNG_IMG.equals(ext))
                    || (PictoConstants.JPG_IMG.equals(imgType) && PictoConstants.JPG_IMG.equals(ext))) {
                filePath = files[k].getAbsolutePath();
            }
        }
        return filePath;

    }

    /**
     * writeZipFile.
     * 
     * @param directoryToZip the directory to zip
     * @param fileList the file list
     */
    public static void writeZipFile(File directoryToZip, List<File> fileList) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            logger.info("Start method Writezipfile");
            /* If already exist delete file. */
            File zipFile = new File(directoryToZip.getAbsoluteFile() + PictoConstants.ZIP_FILE);

            if (zipFile.exists()) {
                zipFile.delete();
            }
            fos = new FileOutputStream(directoryToZip.getAbsolutePath() + PictoConstants.ZIP_FILE);
            zos = new ZipOutputStream(fos);
            for (File file : fileList) {
                // only zip files, not directories
                if (!file.isDirectory()) {
                    addToZip(file, zos);
                }
            }
            logger.info("End of Writezipfile");
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
                logger.error(" Exception while closing the file streams  in method writeZipFile ", e);
            }

        }
    }

    /**
     * Add to zip.
     *
     * @param file the file
     * @param zos the zos
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void addToZip(File file, ZipOutputStream zos) throws IOException {

        FileInputStream fis = null;
        try {
            logger.info("Start method addtozip");
            fis = new FileInputStream(file);
            String zipFilePath = file.getName();
            ZipEntry zipEntry = new ZipEntry(zipFilePath);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            logger.info("End of addtozip");
        } catch (Exception e) {
            logger.error("Error while addto zip file ", e);

        } finally {
            if (zos != null) {
                zos.closeEntry();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * Contributor notification validation.
     * 
     * @param info the info
     * @return the response
     */
    @GET
    @Path("/validateNotification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateNotification(@Context UriInfo info) {
        Long familyId = Long.parseLong(info.getQueryParameters().getFirst("familyId"));
        notificationContribRepository.deleteNotification(familyId);
        return Response.ok().build();
    }

    /**
     * Get the image from server location.
     * 
     * @param pictoPath the picto path
     * @return the image
     */
    @GET
    @Path("/getImage")
    @Produces({ "image/jpg", "image/png", "image/igs", "application/postscript", "application/pdf", "text/plain" })
    public Response getImage(@QueryParam("name") String pictoPath) {
        File file = new File(pictoPath);
        return Response.ok(file, "image/jpg").header("Content-Disposition", "attachment; filename=\"" + file.getName()).build();
    }

    /**
     * Get all categories to display in filter.
     *
     * @return the all category
     */
    @GET
    @Path("/allCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategory() {
        List<String> categoryname = new ArrayList<String>();
        try {
            List<CategoryRepresentation> catList = pictoFinder.getAllCategories();
            for (CategoryRepresentation cat : catList) {
                categoryname.add(cat.getName());
            }
        } catch (Exception e) {
            logger.error("Exception in getting list of Categories from DB.", e);
        }
        return Response.ok(categoryname).build();
    }

    /**
     * Gets the super category.
     *
     * @return the super category
     */
    @GET
    @Path("/getSuperCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSuperCategory() {
        List<String> superCategoryName = new ArrayList<String>();
        try {
            List<SuperCategoryRepresentation> catList = superCategoryFinder.getSuperCategory();
            for (SuperCategoryRepresentation cat : catList) {
                superCategoryName.add(cat.getName());
            }
        } catch (Exception e) {
            logger.error("Exception in getting list of Categories from DB.", e);
        }
        return Response.ok(superCategoryName).build();
    }

    /**
     * Filter Pictos.
     * 
     * @param pictoFilterRepresentation the picto filter representation
     * @return the response
     */
    @POST
    @Path("/filterPicto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterPicto(PictoFilterRepresentation pictoFilterRepresentation) {
        logger.info("Start filter pictos");
        User user = userFinder.getUser();
        pictoFilterRepresentation.setUser(user);
        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();
        try {
            listPicto = pictoFinder.getFilterData(pictoFilterRepresentation);
        } catch (Exception e) {
            logger.error("ERROR executing search query", e);
            return Response.status(Status.PRECONDITION_FAILED).build();
        }

        List<BigInteger> pictosFavList = pictoFamilyFinder.getFavPictoIDbyUser(user.getId());
        List<BigInteger> pictosCartList = pictoFamilyFinder.getCartPictoIDbyUser(user.getId());

        for (int i = 0; i < listPicto.size(); i++) {
            Long id = listPicto.get(i).getFamilyID().getFamilyId();
            BigInteger pictoFamilyId = BigInteger.valueOf(id.intValue());

            if (pictosFavList.contains(pictoFamilyId)) {
                listPicto.get(i).getFamilyID().setFavoriteFlag(Boolean.TRUE);
            }

            Long pictoId = listPicto.get(i).getId();
            BigInteger bi = BigInteger.valueOf(pictoId.intValue());

            if (pictosCartList.contains(bi)) {
                listPicto.get(i).setCartFlag(Boolean.TRUE);
            }
        }

        logger.info("End filter pictos");
        return Response.ok(listPicto).build();
    }

    /**
     * Validation Level.
     *
     * @return the response
     */
    @GET
    @Path("/validation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validationLevel() {
        List<String> validationList = new ArrayList<String>();
        validationList.add(PictoConstants.LEVEL_VALID);
        validationList.add(PictoConstants.LEVEL_INPROGRESS);
        return Response.ok(validationList).build();
    }

    /**
     * Removes the shop cart picto.
     * 
     * @param cart the cart
     * @return the response
     */
    @POST
    @Path("/removeCartPicto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeShopCartPicto(CartData cart) {
        try {
            User user = userFinder.getUser();

            List<Long> pictoIdList = cart.getPictoId();
            List<Picto> tempPicto = new ArrayList<Picto>();
            for (Long tempPictoId : pictoIdList) {
                Picto picto = pictoRepository.load(tempPictoId);
                if (picto != null) {
                    tempPicto.add(picto);
                    pictoFinder.removeShopCart(user.getId(), picto.getEntityId());
                }
            }
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while remove item from cart ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Gets the all visible.
     *
     * @return the all visible
     */
    @GET
    @Path("/visible")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVisible() {
        List<String> visibleList = new ArrayList<String>();
        visibleList.add(PictoConstants.VISIBLE);
        visibleList.add(PictoConstants.INVISIBLE);
        return Response.ok(visibleList).build();
    }

    /**
     * Validation the picto family before delete call.
     * 
     * @param uriInfo the p uri info
     * @return the response
     */

    @POST
    @Path("/deletePictoVariantValidation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePictoFamilyValidation(@Context UriInfo uriInfo) {
        try {
            Long pictoId = Long.parseLong(uriInfo.getQueryParameters().getFirst("pictoId"));
            List<String> workAdminList = pictoClientFinder.getWorkingAdminList(pictoId);
            if (!workAdminList.isEmpty()) {
                return Response.ok(workAdminList).build();
            }
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while validating delete picto variant ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Send the Images to UI.
     * 
     * @return the response
     */
    @GET
    @Path("/DownloadImages")
    @Produces("application/zip")
    public Response downloadImages() {
        File file = new File(tempFileDirectory + PictoConstants.DOWNLOAD_ZIP_NAME.trim() + PictoConstants.ZIP_FILE);
        return Response.ok(file, "application/zip").header("Content-Disposition", "attachment; filename=\"" + file.getName()).build();
    }

    /**
     * To check thick client is installed or not.
     * 
     * @return the response
     */
    @GET
    @Path("/thickClientInstall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkThickClientInstall() {
        User user = userFinder.getUser();
        if (user.getIsThickClient()) {
            return Response.ok().build();
        }
        return Response.status(Status.PRECONDITION_FAILED).build();
    }

    /**
     * Gets the variants list.
     *
     * @param variantData the variant data
     * @return the variants list
     */
    @POST
    @Path("/getVariantsList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVariantsList(VariantData variantData) {
        logger.info("Inside Resource to Download Picto.");
        Long pictoId;
        List<Long> pictoIdList = variantData.getPictoId();
        Set<String> listVariantsTotal = new HashSet<String>();
        if (pictoIdList != null) {
            try {
                if (pictoIdList.size() > 1) {
                    for (int p = 0; p < pictoIdList.size(); p++) {
                        pictoId = pictoIdList.get(p);
                        logger.info("Validating available variants for Picto Id : " + pictoId);
                        Picto picto = pictoRepository.load(pictoId);
                        PictoFamily picFamily = pictoFamilyRepository.load(picto.getPictoFamilyID().getEntityId());
                        List<Picto> variantPictos = picFamily.getPictos();
                        if (variantPictos != null) {
                            /** PRP024006-135: GL modification STARTS Date:07/12/2016 */
                            if (securitySupport.hasRole(admin1Role) || securitySupport.hasRole(admin2Role)) {
                                listVariantsTotal.addAll(getAllVariantsForAdmin(variantPictos, PictoConstants.FAILURE));
                            } else {
                                listVariantsTotal.addAll(getVariantsForNonAdminRole(variantPictos, PictoConstants.FAILURE));
                            }
                            /** PRP024006-135: GL modification ENDS Date:07/12/2016 */
                        } else {
                            logger.info("No variants available for selected picto : " + pictoId);
                        }
                    }
                } else {
                    for (int p = 0; p < pictoIdList.size(); p++) {
                        pictoId = pictoIdList.get(p);
                        logger.info("Validating available variants for Picto Id : " + pictoId);
                        Picto picto = pictoRepository.load(pictoId);
                        PictoFamily picFamily = pictoFamilyRepository.load(picto.getPictoFamilyID().getEntityId());
                        List<Picto> variantPictos = picFamily.getPictos();
                        if (variantPictos != null) {
                            /** PRP024006-135: GL modification STARTS Date:07/12/2016 */
                            if (securitySupport.hasRole(admin1Role) || securitySupport.hasRole(admin2Role)) {
                                listVariantsTotal.addAll(getAllVariantsForAdmin(variantPictos, PictoConstants.SUCCESS));
                            } else {
                                listVariantsTotal.addAll(getVariantsForNonAdminRole(variantPictos, PictoConstants.SUCCESS));
                            }
                            /** PRP024006-135: GL modification ENDS Date:07/12/2016 */
                        } else {
                            logger.info("No variants available for selected picto : " + pictoId);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Exception in getting list of variants available. ", e);
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.ok(listVariantsTotal).build();
    }

    /**
     * To get the visible variants for user and contributor role.
     *
     * @param variantPictos the variant pictos
     * @param flag the flag
     * @return list of variants
     */
    private Set<String> getVariantsForNonAdminRole(List<Picto> variantPictos, boolean flag) {
        Set<String> listVariants = new HashSet<String>();
        for (Picto pictoVariants : variantPictos) {
            if (pictoVariants.getIsVisible()) {
                if (pictoVariants.getVersion() != null && flag) {
                    listVariants.add(pictoVariants.getVariantType() + "_" + pictoVariants.getVersion());
                } else {
                    listVariants.add(pictoVariants.getVariantType());
                }
            }
        }
        return listVariants;
    }

    /**
     * To get all variants for admin role.
     *
     * @param variantPictos the variant pictos
     * @param flag the flag
     * @return list of variants
     */
    private Set<String> getAllVariantsForAdmin(List<Picto> variantPictos, boolean flag) {
        Set<String> listVariants = new HashSet<String>();
        for (Picto pictoVariants : variantPictos) {
            if (pictoVariants.getVersion() != null && flag) {
                listVariants.add(pictoVariants.getVariantType() + "_" + pictoVariants.getVersion());
            } else {
                listVariants.add(pictoVariants.getVariantType());
            }
        }
        return listVariants;
    }

    /**
     * Import cart.
     *
     * @param data the data
     * @return the response
     */
    @POST
    @Path("/importCart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importCart(ManageImportDTO data) {
        try {
            User user = userFinder.getUser();
            List<Long> dataList = new ArrayList<Long>();
            List<ImportData> importData = data.getImportPicto();

            for (int i = 0; i < importData.size(); i++) {
                PictoFamilyRepresentation pictoFamily = pictoFamilyFinder.getFamilyInfoByRefnum(importData.get(i).getReferenceNum(), user);
                if (pictoFamily != null) {
                    for (int j = 0; j < pictoFamily.getPictos().size(); j++) {
                        if (importData.get(i).getName().equals(pictoFamily.getName())
                                && importData.get(i).getVariant().equals(pictoFamily.getPictos().get(j).getVariantType())) {
                            if (importData.get(i).getVersion() != null
                                    && importData.get(i).getVersion().equals(pictoFamily.getPictos().get(j).getVersion())) {
                                dataList.add(pictoFamily.getPictos().get(j).getId());
                                break;
                            } else if (importData.get(i).getVersion() == null && pictoFamily.getPictos().get(j).getVersion() == null) {
                                dataList.add(pictoFamily.getPictos().get(j).getId());

                            }
                        }
                    }
                }
            }
            CartData cartRepresenation = new CartData();
            cartRepresenation.setPictoId(dataList);
            addShopCartPicto(cartRepresenation);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Exception while updating picto in Shopping cart ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
