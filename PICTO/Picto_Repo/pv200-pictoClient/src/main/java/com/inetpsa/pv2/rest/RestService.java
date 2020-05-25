package com.inetpsa.pv2.rest;

//Creation : Apr 28, 2016

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.service.AdminDetails;
import com.inetpsa.pv2.service.CreateZipFile;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class RestService {
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(RestService.class);
    PropertiesCache propertiesCache = PropertiesCache.getInstance();

    CreateZipFile createZip = new CreateZipFile();

    public RestService() {
    }

    public static final String DOWNLOAD_FILE_LOCATION = PropertiesCache.getInstance().getProperty("picto.img.path");
    Client client = Client.create();

    public void setThickClientFlag(String url, boolean flagValue, long adminId) {
        WebResource webResource = null;

        logger.debug("Calling RestFul Web Service in method  setThickClientFlag of RestServiceImpl class ");

        logger.info("Start  setting Thick Client Flag using Restful Web Service  in method setThickClientFlag of RestServiceImpl class...");

        try {
            webResource = client.resource(url + "?user_id=" + adminId + "&is_thickClient=" + flagValue);

            webResource.accept(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc())
                    .get(ClientResponse.class);

        } catch (Exception e) {

            logger.error("Error in getting webResource in method setThickClientFlag of RestServiceImpl class", e);
        }

    }

    public void updateAIFileUpdateInfo(String url, long adminId, long pictoId) {
        logger.debug("Calling Rest ful web service for updateAIFileUpdateInfo");
        logger.info("Start updating AI File using Restful Web Service  in method updateAIFileUpdateInfo of class RestServiceImpl class...");
        WebResource webResource = null;

        try {
            webResource = client.resource(url + "?user_id=" + adminId + "&picto_id=" + pictoId);

            webResource.accept(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc())
                    .get(ClientResponse.class);

        } catch (Exception e) {

            logger.error("Error in getting webResource in method setThickClientFlag of RestServiceImpl class", e);
        }

    }

    public void deletePicto(String url, long adminId, long pictoId) {
        logger.info("Start method deletePicto for deleting Pictos using Restful Web Service  in RestServiceImpl class...");

        try {
            WebResource webResource = client.resource(url + "?user_id=" + adminId + "&picto_id=" + pictoId);

            webResource.accept(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc())
                    .get(ClientResponse.class);

        } catch (Exception e) {

            logger.error("Error in getting webResource in method deletePicto of RestServiceImpl class", e);
        }

    }

    public void resetDownloadFlag(String url, long adminId, long pictoId) throws RestResponseFailure {
        logger.info("Start method resetDownloadFlag for Reseting Download Flag using Restful Web Service  in RestServiceImpl class...");

        try {
            WebResource webResource = client.resource(url + "?user_id=" + adminId + "&picto_id=" + pictoId);

            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

        } catch (Exception e) {

            logger.error("Error in reseting download flag in method resetDownloadFlag of RestServiceImpl class", e);
        }

    }

    public List<PictoInformation> getPictosForUsrId(String url, long adminId) {
        logger.debug("Calling Rest ful web service for get PictoClient");
        logger.info("Start method getPictoClient for getting Picto Information using Restful Web Service  in RestServiceImpl class...");
        Object obj = null;
        JSONObject jsonObject = null;
        List<PictoInformation> pictoInformationList = new ArrayList<PictoInformation>();

        WebResource webResource = null;
        ClientResponse response = null;
        @SuppressWarnings("rawtypes")
        Iterator i = null;
        JSONParser parser = null;
        JSONArray jsonArray = null;
        String pictoName = null;
        String variante = null;
        String pictoImgName = null;
        try {

            try {
                webResource = client.resource(url + "?user_id=" + adminId);

                response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

            } catch (Exception e) {

                logger.error("Error in fetching Picto Client in method getPictoClient of RestServiceImpl class", e);
            }

            if (response == null) {
                logger.debug("Null Response in  method getPictoClient of RestServiceImpl class");
            }

            else {
                obj = response.getEntity(String.class);
                parser = new JSONParser();
                jsonArray = (JSONArray) parser.parse((String) obj);
                if (jsonArray != null) {
                    i = jsonArray.iterator();
                    if (i != null) {
                        while (i.hasNext()) {
                            PictoInformation pictoInformation = new PictoInformation();
                            jsonObject = (JSONObject) i.next();
                            JSONObject jsonPictoObject = (JSONObject) jsonObject.get("pictoId");
                            JSONObject jsonPictoFamilyObject = (JSONObject) jsonPictoObject.get("familyID");
                            JSONObject lastModUsrObject = (JSONObject) jsonPictoObject.get("lastModifiedUsr");
                            JSONObject lastUpdateUsrObject = (JSONObject) jsonPictoObject.get("lastUpdatedUsr");
                            Object picId = jsonPictoObject.get("id");
                            pictoInformation.setPicFamName(jsonPictoFamilyObject.get("name").toString());
                            String imgLocation = jsonPictoObject.get("imageLocation").toString();
                            pictoInformation.setPicFamRefNum(jsonPictoFamilyObject.get("referenceNum").toString().trim());
                            pictoInformation.setDownloadDate(jsonObject.get("downloadDate").toString());
                            if (lastModUsrObject != null) {
                                pictoInformation
                                        .setLastModAdmin(lastModUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                                + lastModUsrObject.get("firstName").toString() + "-"
                                                + lastModUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {
                                logger.debug("Null value for lastModUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (lastUpdateUsrObject != null) {
                                pictoInformation.setLastUpdateAdmin(lastUpdateUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                        + lastUpdateUsrObject.get("firstName").toString() + "-"
                                        + lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                                pictoInformation.setLastUpdateAdminId(lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {

                                logger.debug("Null value for lastUpdateUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (jsonPictoObject.get("lastUpdateDate") != null) {
                                pictoInformation.setUpdateDate(jsonPictoObject.get("lastUpdateDate").toString());
                            }
                            if (jsonPictoObject.get("modifyDate") != null) {
                                pictoInformation.setModDate(jsonPictoObject.get("modifyDate").toString());
                            }
                            pictoInformation.setPictoId((Long) picId);
                            pictoName = jsonPictoFamilyObject.get("name").toString().trim();
                            String version = null;
                            pictoImgName = null;
                            variante = jsonPictoObject.get("variantType").toString();
                            if (jsonPictoFamilyObject.get("refCharte") != null) {
                                pictoInformation.setRefCharte(jsonPictoFamilyObject.get("refCharte").toString());
                            }
                            if (jsonPictoObject.get("version") != null) {
                                version = jsonPictoObject.get("version").toString();
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    pictoImgName = imgLocation + "_" + variante + "_" + version;
                                } else {
                                    pictoImgName = imgLocation + "_" + variante + "_" + version;
                                }
                            } else {
                                if (pictoInformation.getRefCharte() != null) {
                                    pictoImgName = imgLocation + "_" + variante;
                                } else {
                                    pictoImgName = imgLocation + "_" + variante;
                                }
                            }

                            pictoInformation.setVariante(variante);
                            String localDirImgName = null;
                            if (jsonPictoObject.get("version") != null) {
                                version = jsonPictoObject.get("version").toString();
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();
                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();
                                }
                            } else {
                                if (jsonPictoFamilyObject.get("refCharte") != null) {

                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();

                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();
                                }
                            }
                            pictoInformation.setPicNameInLocalDir(localDirImgName);
                            pictoInformation.setPictoName(pictoImgName);
                            pictoInformationList.add(pictoInformation);
                        }

                        logger.info("Fetching Picto Client Information Successful in method getPictoClient using RestFul WebService in  RestServiceImpl class ");
                    }
                } else {
                    logger.debug("Null Json Array in method getPictoClient of RestServiceImpl class");

                }
            }
        } catch (Exception e) {
            logger.error("Error in fetching Picto Client Information in method getPictoClient using RestFul WebService in  RestServiceImpl class ", e);

        }
        return pictoInformationList;
    }

    public PictoInformation getPictoClient(String url, long adminId, long pictoId) {
        logger.debug("Calling Rest ful web service for get PictoClient");
        logger.info("Start method getPictoClient for getting Picto Information using Restful Web Service  in RestServiceImpl class...");
        Object obj = null;
        JSONObject jsonObject = null;
        PictoInformation pictoInformation = new PictoInformation();
        WebResource webResource = null;
        ClientResponse response = null;
        @SuppressWarnings("rawtypes")
        Iterator i = null;
        JSONParser parser = null;
        JSONArray jsonArray = null;
        String pictoName = null;
        String variante = null;
        String pictoImgName = null;
        try {

            try {
                webResource = client.resource(url + "?user_id=" + adminId + "&picto_id=" + pictoId);

                response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

            } catch (Exception e) {

                logger.error("Error in fetching Picto Client in method getPictoClient of RestServiceImpl class", e);
            }

            if (response == null) {
                logger.debug("Null Response in  method getPictoClient of RestServiceImpl class");
            }

            else {
                obj = response.getEntity(String.class);
                parser = new JSONParser();
                jsonArray = (JSONArray) parser.parse((String) obj);
                if (jsonArray != null) {
                    i = jsonArray.iterator();
                    if (i != null) {
                        while (i.hasNext()) {
                            jsonObject = (JSONObject) i.next();
                            JSONObject jsonPictoObject = (JSONObject) jsonObject.get("pictoId");
                            JSONObject jsonPictoFamilyObject = (JSONObject) jsonPictoObject.get("familyID");
                            JSONObject lastModUsrObject = (JSONObject) jsonPictoObject.get("lastModifiedUsr");
                            JSONObject lastUpdateUsrObject = (JSONObject) jsonPictoObject.get("lastUpdatedUsr");
                            Object picId = jsonPictoObject.get("id");
                            pictoInformation.setPicFamName(jsonPictoFamilyObject.get("name").toString());
                            String imgLocation = jsonPictoObject.get("imageLocation").toString();
                            pictoInformation.setPicFamRefNum(jsonPictoFamilyObject.get("referenceNum").toString().trim());
                            pictoInformation.setDownloadDate(jsonObject.get("downloadDate").toString());
                            if (lastModUsrObject != null) {
                                pictoInformation
                                        .setLastModAdmin(lastModUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                                + lastModUsrObject.get("firstName").toString() + "-"
                                                + lastModUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {
                                logger.debug("Null value for lastModUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (lastUpdateUsrObject != null) {
                                pictoInformation.setLastUpdateAdmin(lastUpdateUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                        + lastUpdateUsrObject.get("firstName").toString() + "-"
                                        + lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                                pictoInformation.setLastUpdateAdminId(lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {

                                logger.debug("Null value for lastUpdateUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (jsonPictoObject.get("lastUpdateDate") != null) {
                                pictoInformation.setUpdateDate(jsonPictoObject.get("lastUpdateDate").toString());
                            }
                            if (jsonPictoObject.get("modifyDate") != null) {
                                pictoInformation.setModDate(jsonPictoObject.get("modifyDate").toString());
                            }
                            pictoInformation.setPictoId((Long) picId);
                            pictoName = jsonPictoFamilyObject.get("name").toString().trim();
                            String version = null;
                            pictoImgName = null;
                            variante = jsonPictoObject.get("variantType").toString();
                            if (jsonPictoFamilyObject.get("refCharte") != null) {
                                pictoInformation.setRefCharte(jsonPictoFamilyObject.get("refCharte").toString());
                            }
                            if (jsonPictoObject.get("version") != null) {
                                version = jsonPictoObject.get("version").toString();
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    pictoImgName = imgLocation + "_" + variante + "_" + version;
                                } else {
                                    pictoImgName = imgLocation + "_" + variante + "_" + version;
                                }
                            } else {
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    pictoImgName = imgLocation + "_" + variante;
                                } else {
                                    pictoImgName = imgLocation + "_" + variante;
                                }
                            }

                            pictoInformation.setVariante(variante);
                            String localDirImgName = null;
                            if (jsonPictoObject.get("version") != null) {
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();

                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();
                                }
                            } else {
                                if (jsonPictoFamilyObject.get("refCharte") != null) {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();
                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();
                                }
                            }
                            pictoInformation.setPicNameInLocalDir(localDirImgName);
                            pictoInformation.setPictoName(pictoImgName);

                        }

                        logger.info("Fetching Picto Client Information Successful in method getPictoClient using RestFul WebService in  RestServiceImpl class ");
                    }
                } else {
                    logger.debug("Null Json Array in method getPictoClient of RestServiceImpl class");

                }
            }
        } catch (Exception e) {
            logger.error("Error in fetching Picto Client Information in method getPictoClient using RestFul WebService in  RestServiceImpl class ", e);

        }
        return pictoInformation;
    }

    public List<String> getWorkAdminList(String url, long pictoId) {
        logger.debug("Calling Rest ful web service for getWorkAdminList");
        logger.info("Start method getWorkAdminList for Admin Working List using Restful Web Service  in RestServiceImpl class...");
        Object obj = null;
        WebResource webResource = null;
        ClientResponse response = null;
        List<String> workAdminList = new ArrayList<String>();
        @SuppressWarnings("rawtypes")
        Iterator i = null;
        JSONParser parser = null;
        JSONArray jsonArray = null;
        try {
            try {
                webResource = client.resource(url + "?picto_id=" + pictoId);

                response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

            } catch (Exception e) {

                logger.error("Error in getting webResource in method getWorkAdminList of RestServiceImpl class", e);
            }
            if (response != null) {
                obj = response.getEntity(String.class);
            }

            parser = new JSONParser();

            jsonArray = (JSONArray) parser.parse((String) obj);
            i = jsonArray.iterator();

            while (i.hasNext()) {
                String adminId = (String) i.next();
                workAdminList.add(adminId);

            }

        } catch (Exception e) {
            logger.error("Error in fetching Working Admin List in method getWorkAdminList using RestFul WebService in  RestServiceImpl class ", e);

        }
        return workAdminList;
    }

    public String authenticateUsr(String url, String userId, String pwd) {
        logger.debug("Calling Rest ful web service for authenticateUsr");
        logger.info("Start method getPictosForUsrId for getting Picto List using Restful Web Service  in RestServiceImpl class...");
        String authResponse = null;
        WebResource webResource = null;
        ClientResponse response = null;
        Object obj = null;
        JSONParser parser = null;
        JSONArray jsonArray = null;

        String adminId = null;
        try {

            try {
                webResource = client.resource(url + "?userId=" + userId + "&pwd=" + pwd);

                response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);
                if (response != null) {
                    obj = response.getEntity(String.class);
                }
                System.out.println("user  id : " + obj);
                if (obj != null) {
                    adminId = obj.toString();
                }

            } catch (Exception e) {

                logger.error("Error in getting webResource in  method authenticateUsr of RestServiceImpl class", e);
            }

            logger.info("Fetching Picto Client Information Successful in method getPictoClient using RestFul WebService in  RestServiceImpl class ");

        } catch (Exception e) {
            logger.error("Error in Authenicating User in method authenticateUsr using RestFul WebService in  RestServiceImpl class ", e);
        }
        return adminId;
    }

    public void downloadAiFile(String url, String fileName, String localImgName) {
        logger.debug("Calling Rest ful web service for downloadAiFile");
        logger.info("Starting Download AI File in  method downloadAiFile using Restful Web Service  in RestServiceImpl class...");
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        ClientResponse clientResponse = null;
        Client client = null;
        Builder wb = null;
        String qualifiedDownloadFilePath = null;
        String zipFolderPath = null;
        client = Client.create();

        WebResource wr = client.resource(url + fileName);

        logger.debug("Null WebResource in method downloadAiFile of RestServiceImpl class");

        if (AdminDetails.getInstance().getUserName() != null && AdminDetails.getInstance().getPassword() != null) {
            client.addFilter(new HTTPBasicAuthFilter(AdminDetails.getInstance().getUserName(), AdminDetails.getInstance().getPassword()));
        } else {
            logger.debug("Null values of name/password in method downloadAiFile of RestServiceImpl class");
        }
        wb = wr.accept("application/zip,application/octet-stream");
        clientResponse = wr.get(ClientResponse.class);
        try {

            inputStream = clientResponse.getEntityInputStream();
            qualifiedDownloadFilePath = DOWNLOAD_FILE_LOCATION + fileName + PictoConstants.ZIP_FILE;

            outputStream = new FileOutputStream(qualifiedDownloadFilePath);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            deleteServerZipFile(fileName);

            // Local folder path for unzip.
            zipFolderPath = DOWNLOAD_FILE_LOCATION;

            unZip(qualifiedDownloadFilePath, zipFolderPath, localImgName);

            // delete zip files after unzip from local dir.

            deleteLocalZipFiles(DOWNLOAD_FILE_LOCATION);

            logger.debug("Files sucessfully downloaded at " + qualifiedDownloadFilePath + "in method downloadAiFile of RestServiceImpl class");
            logger.info("Downloading AI File successful in method downloadAiFile using Restful Web Service  in RestServiceImpl class...");

        } catch (Exception e) {
            logger.error("Error in Downloading AI File in method downloadAiFile using RestFul WebService in  RestServiceImpl class ", e);

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (Exception e) {
                logger.error("Error in closing output Stream in  RestServiceImpl class ", e);
            }
            try {
                if (clientResponse != null) {
                    clientResponse.close();
                }
            } catch (Exception e) {
                logger.error("Error in closing output Stream in  RestServiceImpl class ", e);
            }
        }

    }

    /**
     * After unzip, delete ziped files from local directory.
     * 
     * @param zipFolderPath : zip folder local path to delete zip files.
     */
    public void deleteLocalZipFiles(String zipFolderPath) {
        logger.info("Start: Deleting zip files from local in deleteLocalZipFiles method of RestServiceImpl class ");
        File localZipFilePath = new File(zipFolderPath);
        try {
            File[] listFiles = localZipFilePath.listFiles();
            for (File file : listFiles) {
                String fileExtension = FilenameUtils.getExtension("" + file);
                String zipExtension = PictoConstants.ZIP_FILE;
                String zipFileExt = zipExtension.substring(1);
                if (fileExtension.equals(zipFileExt)) {
                    boolean deleteFlag = file.delete();
                    if (deleteFlag) {
                        logger.info("Successfully deleted zip file: " + file);
                    } else {
                        logger.info("Failed to delete zip file: " + file);
                    }
                }
            }
            logger.info("Finish: Deleting zip files from local in deleteLocalZipFiles method of RestServiceImpl class ");
        } catch (Exception e) {
            logger.error("Exception in deleting zip files in deleteLocalZipFiles method of RestServiceImpl class", e);
        }

    }

    /**
     * delete zip file created at server
     */
    private void deleteServerZipFile(String fileName) {
        String zipFileUrl = propertiesCache.getProperty("picto.domain.name").trim() + PictoConstants.DLT_SRVR_ZIP_FILE_URL + fileName;
        ClientResponse response = null;
        try {
            WebResource webResource = client.resource(zipFileUrl);

            response = webResource.accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

        } catch (Exception e) {
            logger.error(" Exception occured while authentication user ws ", e);
        }
    }

    /**
     * Unzip the files
     * 
     * @param zipFile
     * @param outputFolder
     * @param localImgName
     * @throws IOException
     */
    private void unZip(String zipFile, String outputFolder, String localImgName) throws IOException {
        if (zipFile != null && outputFolder != null && localImgName != null) {
            logger.info("Start UnZip in method unZip for getting Picto List using Restful Web Service  in RestServiceImpl class...");
            byte[] buffer = new byte[1024];
            ZipInputStream zis = null;
            ZipEntry ze = null;
            FileOutputStream fos = null;
            FileInputStream fileIs = null;
            try {
                // create output directory is not exists
                File folder = new File(outputFolder);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                // create FileInputStream object and use it,it can be closed in finally then
                // get the zip file content
                fileIs = new FileInputStream(zipFile);
                zis = new ZipInputStream(fileIs);

                // get the zipped file list entry
                ze = zis.getNextEntry();
                while (ze != null) {

                    String fileName = ze.getName();
                    String ext = FilenameUtils.getExtension(fileName);

                    File newFile = new File(outputFolder.trim() + File.separator.trim() + localImgName.trim() + "." + ext.trim());
                    // create all non exists folders
                    // else you will hit FileNotFoundException for
                    // compressed folder
                    new File(newFile.getParent()).mkdirs();
                    fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    ze = zis.getNextEntry();

                }
            }

            catch (Exception e) {
                logger.error("Error in Unziping Files in method unZip using RestFul WebService in  RestServiceImpl class ", e);
            }

            finally {
                try {

                    if (zis != null) {
                        zis.closeEntry();
                        zis.close();
                    }
                    if (fileIs != null) {
                        fileIs.close();
                    }
                } catch (Exception e) {
                    logger.error("Error in closing  ZipInputStream in d unZip using  in  RestServiceImpl class " + e);
                }

            }
        } else {

            logger.debug("Null Parameters passed in method argument of unZip method of RestServiceImpl class");
        }
    }

    /**
     * Upload the files
     * 
     * @param url
     * @param fileToUpload
     */
    public void uploadFile(String url, String fileToUpload, Long pictoId) {
        try {
            logger.debug("Calling Rest ful web service for uploadFile");
            logger.info("Start Upload File in method uploadFile for Uploading File using Restful Web Service  in RestServiceImpl class...");
            FormDataContentDisposition formDataContentDisposition = null;
            String zipFilePath = propertiesCache.getProperty("temp.folder.path") + File.separator + fileToUpload.trim() + PictoConstants.ZIP_FILE;

            boolean zipCreated = createZip.createZipFile(propertiesCache.getProperty("temp.folder.path"), fileToUpload.trim());
            if (zipCreated) {
                Client client = Client.create();

                client.setFollowRedirects(false);
                WebResource webResource = client.resource(url + fileToUpload + "&picto_id=" + pictoId);

                // Ask here for user name and password using pop up window.
                client.addFilter(new HTTPBasicAuthFilter(AdminDetails.getInstance().getUserName(), AdminDetails.getInstance().getPassword()));
                File file = new File(zipFilePath);

                formDataContentDisposition = FormDataContentDisposition.name("fileDetail").fileName(file.getName()).size(file.length()).build();
                FileDataBodyPart fileDataBodyPart = new FileDataBodyPart();

                fileDataBodyPart.setFormDataContentDisposition(formDataContentDisposition);
                fileDataBodyPart.setName(PictoConstants.FILE_SETTER);
                fileDataBodyPart.setFileEntity(file, MediaType.MULTIPART_FORM_DATA_TYPE);

                MultiPart multiPart = new MultiPart();
                multiPart.bodyPart(fileDataBodyPart);
                webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, multiPart);
            } else {
                logger.error("Error in creating ZIP file .");
            }
        } catch (Exception e) {

            logger.error("Error in uploadFile using RestFul WebService in method uploadFile of RestServiceImpl class ", e);
        }
    }

    public List<PictoInformation> getAllPictosForUsrId(String url, long adminId) {
        logger.debug("Calling Rest ful web service for get PictoClient");
        logger.info("Start method getPictoClient for getting Picto Information using Restful Web Service  in RestServiceImpl class...");
        Object obj = null;
        JSONObject jsonObject = null;
        List<PictoInformation> pictoInformationList = new ArrayList<PictoInformation>();

        WebResource webResource = null;
        ClientResponse response = null;
        @SuppressWarnings("rawtypes")
        Iterator i = null;
        JSONParser parser = null;
        JSONArray jsonArray = null;
        String pictoName = null;
        String variante = null;
        String pictoImgName = null;
        try {

            try {
                webResource = client.resource(url + "?user_id=" + adminId);

                response = webResource.accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + AdminDetails.getInstance().getAuthStringEnc()).get(ClientResponse.class);

            } catch (Exception e) {

                logger.error("Error in fetching Picto Client in method getPictoClient of RestServiceImpl class", e);
            }

            if (response == null) {
                logger.debug("Null Response in  method getPictoClient of RestServiceImpl class");
            }

            else {
                obj = response.getEntity(String.class);
                parser = new JSONParser();
                jsonArray = (JSONArray) parser.parse((String) obj);
                if (jsonArray != null) {
                    i = jsonArray.iterator();
                    if (i != null) {
                        while (i.hasNext()) {
                            PictoInformation pictoInformation = new PictoInformation();
                            jsonObject = (JSONObject) i.next();
                            JSONObject jsonPictoObject = (JSONObject) jsonObject.get("pictoId");
                            JSONObject jsonPictoFamilyObject = (JSONObject) jsonPictoObject.get("familyID");
                            JSONObject lastModUsrObject = (JSONObject) jsonPictoObject.get("lastModifiedUsr");
                            JSONObject lastUpdateUsrObject = (JSONObject) jsonPictoObject.get("lastUpdatedUsr");
                            Object picId = jsonPictoObject.get("id");
                            pictoInformation.setPicFamName(jsonPictoFamilyObject.get("name").toString());
                            String imgLocation = jsonPictoObject.get("imageLocation").toString();
                            if (jsonPictoFamilyObject.get("refCharte") != null) {
                                pictoInformation.setRefCharte(jsonPictoFamilyObject.get("refCharte").toString());
                            }
                            pictoInformation.setPicFamRefNum(jsonPictoFamilyObject.get("referenceNum").toString().trim());
                            pictoInformation.setDownloadDate(jsonObject.get("downloadDate").toString());
                            String downloadFlagVal = jsonObject.get("downloadFlag").toString();
                            if (downloadFlagVal.equalsIgnoreCase("1")) {
                                pictoInformation.setDownloadFlag((byte) 1);
                            } else if (downloadFlagVal.equalsIgnoreCase("0")) {
                                pictoInformation.setDownloadFlag((byte) 0);
                            }
                            String isOpenLocalImgVal = jsonObject.get("isOpenLocalImg").toString();

                            if (isOpenLocalImgVal.equalsIgnoreCase("true")) {
                                pictoInformation.setOpenLocalImg(true);
                            } else if (isOpenLocalImgVal.equalsIgnoreCase("false")) {
                                pictoInformation.setOpenLocalImg(false);
                            }

                            if (lastModUsrObject != null) {
                                pictoInformation
                                        .setLastModAdmin(lastModUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                                + lastModUsrObject.get("firstName").toString() + "-"
                                                + lastModUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {
                                logger.debug("Null value for lastModUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (lastUpdateUsrObject != null) {
                                pictoInformation.setLastUpdateAdmin(lastUpdateUsrObject.get(PictoConstants.USR_FIRST_NAME).toString() + " "
                                        + lastUpdateUsrObject.get("firstName").toString() + "-"
                                        + lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                                pictoInformation.setLastUpdateAdminId(lastUpdateUsrObject.get(PictoConstants.USR_ID).toString());
                            } else {

                                logger.debug("Null value for lastUpdateUsrObject in getPictoClient method of RestServiceImpl class");
                            }
                            if (jsonPictoObject.get("lastUpdateDate") != null) {
                                pictoInformation.setUpdateDate(jsonPictoObject.get("lastUpdateDate").toString());
                            }
                            if (jsonPictoObject.get("modifyDate") != null) {
                                pictoInformation.setModDate(jsonPictoObject.get("modifyDate").toString());
                            }
                            pictoInformation.setPictoId((Long) picId);
                            pictoName = jsonPictoFamilyObject.get("name").toString().trim();
                            String version = null;
                            pictoImgName = null;
                            variante = jsonPictoObject.get("variantType").toString();
                            if (jsonPictoObject.get("version") != null) {
                                version = jsonPictoObject.get("version").toString();

                                pictoImgName = imgLocation + "_" + variante + "_" + version;

                            } else {
                                if (pictoInformation.getRefCharte() != null) {
                                    pictoImgName = imgLocation + "_" + variante;
                                } else {
                                    pictoImgName = imgLocation + "_" + variante;
                                }
                            }

                            pictoInformation.setVariante(variante);
                            String localDirImgName = null;
                            if (version != null) {
                                if (pictoInformation.getRefCharte() != null) {

                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();

                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "_" + version + "."
                                            + pictoInformation.getUpdateDate() + "." + pictoInformation.getLastUpdateAdminId() + "."
                                            + pictoInformation.getDownloadDate();
                                }
                            } else {
                                if (pictoInformation.getRefCharte() != null) {

                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();

                                } else {
                                    localDirImgName = imgLocation + "_" + pictoInformation.getVariante() + "." + pictoInformation.getUpdateDate()
                                            + "." + pictoInformation.getLastUpdateAdminId() + "." + pictoInformation.getDownloadDate();
                                }
                            }
                            pictoInformation.setPicNameInLocalDir(localDirImgName);
                            pictoInformation.setPictoName(pictoImgName);
                            pictoInformationList.add(pictoInformation);
                        }

                        logger.info("Fetching Picto Client Information Successful in method getPictoClient using RestFul WebService in  RestServiceImpl class ");
                    }
                } else {
                    logger.debug("Null Json Array in method getPictoClient of RestServiceImpl class");

                }
            }
        } catch (Exception e) {
            logger.error("Error in fetching Picto Client Information in method getPictoClient using RestFul WebService in  RestServiceImpl class ", e);

        }
        return pictoInformationList;
    }
}
