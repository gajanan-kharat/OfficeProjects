/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.pv2.service;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.props.PropertiesLang;
import com.inetpsa.pv2.rest.RestService;

public class SubmitPictoEvent implements EventHandler<ActionEvent> {
    /**
     * Logger log4j to write messages
     */
    private static final Logger logger = LoggerFactory.getLogger(SubmitPictoEvent.class);
    /**
     * Logged in admin Id
     */
    long loggedInAdmin = AdminDetails.getInstance().getLoggedInAdmin();
    ActionEvents actionEvents = new ActionEvents();
    DeleteEvent deleteEvent = new DeleteEvent();
    /**
     * to retrieve picto details when single picto selected
     */
    private PictoInformation pictoInformation;

    /**
     * loading properties file into a variable
     */

    PropertiesCache propertiesCache = PropertiesCache.getInstance();

    /**
     * Logger log4j to write messages
     */
    private static Logger m_logger = LoggerFactory.getLogger(SubmitPictoEvent.class);

    /**
     * Stage variable to refresh data after update
     */
    private Stage primaryStage;

    /**
     * Application's Service class instance
     */
    RestService restService = new RestService();
    /**
     * Temp directory to move selected file for conversion
     */
    File tempFolder = new File(propertiesCache.getProperty("temp.folder.path"));
    File localDownloadDir = new File(propertiesCache.getProperty("picto.img.path"));

    /**
     * class to handle all on click action Events
     * 
     * @param pictoImageName :String having picto name
     * @param primaryStage : Stage variable to refresh data after update
     */
    public SubmitPictoEvent(Stage primaryStage, PictoInformation pictoInformation) {

        this.primaryStage = primaryStage;
        this.pictoInformation = pictoInformation;
    }

    @Override
    public void handle(ActionEvent event) {
        if (((Button) event.getSource()).getId().equals("submitAll")) {
            Boolean isMultipleselected = true;
            submitChanges(pictoInformation, isMultipleselected);
        } else if (((Button) event.getSource()).getId().equals("submitButton")) {
            Boolean isMultipleselected = false;
            submitChanges(pictoInformation, isMultipleselected);
        }
    }

    /**
     * Submit changes method to launch conversion for selected pictos
     * 
     * @param pictoDetails picto details
     * @param grid : to lookup elements
     * @param isMultiple : single picto selected or more then one
     * @param yesButton : Confirmation button
     * @param noButton : Confirmation button
     */
    public void submitChanges(final PictoInformation pictoInformation, final Boolean isMultiple) {
        /**
         * Confirmation Box Buttons
         */

        Button yesButton = new Button();
        /**
         * Confirmation box buttons
         */

        Button noButton = new Button();
        String msgForUsr = null;
        try {
            msgForUsr = URLDecoder.decode(PropertiesLang.getInstance().getProperty("picto.confirm.msg"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            m_logger.error(" Exception occure while submit changes to launch the conversion ", e);
        }

        String titleForBox = PropertiesLang.getInstance().getProperty("confrm.submit.title");
        final Stage dialogStage = actionEvents.createConfirmationPopup(yesButton, noButton, msgForUsr, titleForBox);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                launchConversion(pictoInformation, isMultiple);
                dialogStage.close();
                try {
                    actionEvents.refreshMethod(primaryStage);
                } catch (IOException e) {
                    m_logger.error(" Exception occure while refresh screen", e);
                }
            }
        });
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();
            }
        });
    }

    /**
     * To open file in Image viewer
     * 
     * @param pictoDetails : to get pictoname on single selection
     * @param pictoInformation : to get list of picto on multi selection
     * @param grid : to check which all pictos are selected
     * @param isMultipleSelected : Multiple selection or single selection
     */
    public void launchConversion(PictoInformation pictoInformation, boolean isMultipleSelected) {
        final Stage stage = primaryStage;
        if (!isMultipleSelected) {
            submitSinglePicto(pictoInformation, stage);
        } else {
            submitMultiplePictos(stage);

        }

    }

    /**
     * @param stage
     */
    private void submitMultiplePictos(final Stage stage) {
        int i;
        List<Long> selectedPictoList = PictoDetails.getInstance().getselectedPictos();
        List<PictoInformation> listSelectedPictos = new ArrayList<PictoInformation>();
        File jpgImgFile = null;
        try {
            /* MJ Added 25 July 2016 START */
            boolean copySuccess = true;
            int countFilesInTemp = 0;
            for (i = 0; i < selectedPictoList.size(); i++) {
                PictoInformation pictoInfo = restService.getPictoClient(propertiesCache.getProperty("picto.domain.name")
                        + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL, AdminDetails.getInstance().getLoggedInAdmin(), selectedPictoList.get(i));
                String aiFileInTempName = tempFolder + File.separator + pictoInfo.getPicNameInLocalDir().trim() + PictoConstants.FILE_AI;

                String aiFileName = pictoInfo.getPicNameInLocalDir() + PictoConstants.FILE_AI;

                File localImgDir = new File(propertiesCache.getProperty("picto.img.path") + aiFileName);

                try {
                    if (tempFolder.isDirectory()) {
                        FileUtils.copyFileToDirectory(localImgDir, tempFolder);

                        (new File(aiFileInTempName)).renameTo(new File(tempFolder + File.separator + pictoInfo.getPictoName().trim()
                                + PictoConstants.FILE_AI));
                        logger.info("AI file copied inside WORKING_DIRECTORY : " + pictoInfo.getPictoName());
                        localImgDir.delete();
                    } else {
                        tempFolder.mkdir();
                        FileUtils.copyFileToDirectory(localImgDir, tempFolder);
                        (new File(aiFileInTempName)).renameTo(new File(tempFolder + File.separator + pictoInfo.getPictoName().trim()
                                + PictoConstants.FILE_AI));
                        logger.info("AI file copied inside WORKING_DIRECTORY : " + pictoInfo.getPictoName());
                        localImgDir.delete();
                    }
                    if ((new File(tempFolder + File.separator + pictoInfo.getPictoName().trim() + PictoConstants.FILE_AI)).exists()) {
                        countFilesInTemp++;
                    }
                } catch (IOException e) {
                    logger.error("Error in copying file from downloaded path to Temp Folder.", e);
                }
                listSelectedPictos.add(pictoInfo);
            }
            while (copySuccess) {
                if (countFilesInTemp == selectedPictoList.size()) {
                    logger.info("All AI files copied successfully inside WORKING_DIRECTORY");
                    copySuccess = false;
                }
            }
            /* MJ Added 25 July 2016 ENDS */
            logger.info("========= START ================.");
            logger.info("Launching conversion batch file .");
            logger.info("================================.");
            Process process = Runtime.getRuntime().exec(
                    PictoConstants.RUN_CONVERSION_BATCH_COMMAND + propertiesCache.getProperty("picto.cnvrsn.script.bat.path"));
            if (process != null && process.waitFor() == 0) {
                logger.info("========= ENDS ================.");
                logger.info("Conversion Process finished .");
                logger.info("================================.");
                boolean isFailure = false;
                String namesFailedCnvrsnPictos = "";
                for (i = 0; i < listSelectedPictos.size(); i++) {

                    PictoInformation picInfo = listSelectedPictos.get(i);
                    String pictoImgDir = picInfo.getPictoName().trim();
                    String pictoNameInTemp = picInfo.getPictoName();
                    File imgDir = new File(tempFolder + File.separator + pictoImgDir);
                    File aiworkFile = new File(tempFolder + File.separator + pictoNameInTemp + PictoConstants.FILE_AI);

                    if (imgDir.isDirectory()) {
                        logger.info("Folder created successfully with converted files inside each for picto : " + pictoNameInTemp);
                        logger.info("AI Work file moved to Picto image directory, created after conversion.");
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_UPLOAD_URL);
                        restService.uploadFile(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_UPLOAD_URL, pictoImgDir,
                                picInfo.getPictoId());
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_LASTUPDATE_URL);
                        restService.updateAIFileUpdateInfo(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_LASTUPDATE_URL,
                                loggedInAdmin, picInfo.getPictoId());
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_DELETE_URL);
                        jpgImgFile = new File(propertiesCache.getProperty("picto.img.path") + picInfo.getPicNameInLocalDir()
                                + PictoConstants.FILE_JPG);
                        jpgImgFile.delete();
                    }
                    if (!imgDir.isDirectory()) {
                        logger.info(" ************** Conversion failed for picto : " + pictoNameInTemp);
                        FileUtils.copyFileToDirectory(aiworkFile, localDownloadDir);
                        (new File(propertiesCache.getProperty("picto.img.path") + pictoNameInTemp + PictoConstants.FILE_AI)).renameTo(new File(
                                propertiesCache.getProperty("picto.img.path") + picInfo.getPicNameInLocalDir() + PictoConstants.FILE_AI));
                        aiworkFile.delete();
                        namesFailedCnvrsnPictos = pictoNameInTemp + "\n \n" + namesFailedCnvrsnPictos;
                        logger.info("AI Work file moved to Local image directory, conversion failure for picto.." + pictoImgDir);
                        isFailure = true;
                    }
                    ZipFile zFile;
                    if (imgDir.exists() && imgDir.isDirectory()) {
                        try {
                            FileUtils.deleteDirectory(imgDir);
                            imgDir.delete();
                            zFile = new ZipFile(tempFolder + File.separator + pictoImgDir + PictoConstants.ZIP_FILE);
                            zFile.close();
                            File file = new File(tempFolder + File.separator + pictoImgDir + PictoConstants.ZIP_FILE);
                            file.delete();
                        } catch (IOException e) {
                            logger.error("Exception occurred while deleting files from local directory.", e);
                        }

                    }

                }
                if (isFailure) {                	
                    Stage deleteSuccess = cnvrsnSuccess(stage, PropertiesLang.getInstance().getProperty("picto.cnvrsn.fail.msg") + namesFailedCnvrsnPictos);
                    deleteSuccess.show();
                } else {
                    Stage deleteSuccess = cnvrsnSuccess(stage, PropertiesLang.getInstance().getProperty("picto.submit.success.msg"));
                    deleteSuccess.show();
                }
            }

            // }

        } catch (Exception e) {
            logger.error(" Exception occure while get all pictos ", e);
        }
    }

    /**
     * @param pictoInformation
     * @param stage
     */
    private void submitSinglePicto(PictoInformation pictoInformation, final Stage stage) {
        try {
            File aiFileInTemp = new File(tempFolder + File.separator + pictoInformation.getPicNameInLocalDir().trim() + PictoConstants.FILE_AI);
            File jpgImgFile = new File(propertiesCache.getProperty("picto.img.path") + pictoInformation.getPicNameInLocalDir()
                    + PictoConstants.FILE_JPG);
            String aiFileName = pictoInformation.getPicNameInLocalDir() + PictoConstants.FILE_AI;
            File localAiImg = new File(propertiesCache.getProperty("picto.img.path") + aiFileName);

            try {
                /* MJ Added 25 July 2016 START */
                boolean copySuccess = true;
                if (tempFolder.isDirectory()) {
                    FileUtils.copyFileToDirectory(localAiImg, tempFolder);
                    aiFileInTemp.renameTo(new File(tempFolder + File.separator + pictoInformation.getPictoName().trim() + PictoConstants.FILE_AI));
                    logger.info("AI Work file moved to WORKING_DIRECTORY .");
                    localAiImg.delete();
                } else {
                    tempFolder.mkdir();
                    FileUtils.copyFileToDirectory(localAiImg, tempFolder);
                    aiFileInTemp.renameTo(new File(tempFolder + File.separator + pictoInformation.getPictoName().trim() + PictoConstants.FILE_AI));
                    logger.info("AI Work file moved to WORKING_DIRECTORY .");
                    localAiImg.delete();
                }
                while (copySuccess) {
                    if (new File(tempFolder + File.separator + pictoInformation.getPictoName().trim() + PictoConstants.FILE_AI).exists()) {
                        copySuccess = false;
                        logger.info("************* AI Work file moved to WORKING_DIRECTORY successfully.");
                    }
                }
                /* MJ Added 25 July 2016 ENDS */
                logger.info("========= START ================.");
                logger.info("Launching conversion batch file .");
                logger.info("================================.");
                Process process = Runtime.getRuntime().exec(
                        PictoConstants.RUN_CONVERSION_BATCH_COMMAND + propertiesCache.getProperty("picto.cnvrsn.script.bat.path"));
                if (process != null && process.waitFor() == 0) {
                    logger.info("========= ENDS ================.");
                    logger.info("Conversion Process finished .");
                    logger.info("================================.");
                    //String pictoImgNameLocal = pictoInformation.getPicNameInLocalDir().trim();
                    String pictoImgName = pictoInformation.getPictoName().trim();
                    File imgDir = new File(tempFolder + File.separator + pictoImgName);
                    File aiworkFile = new File(tempFolder + File.separator + pictoImgName + PictoConstants.FILE_AI);

                    if (imgDir.isDirectory()) {
                        logger.info("Picto conversion successful for Picto  : " + pictoImgName);
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_UPLOAD_URL);
                        restService.uploadFile(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_UPLOAD_URL, pictoImgName,
                                pictoInformation.getPictoId());
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_LASTUPDATE_URL);
                        restService.updateAIFileUpdateInfo(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_LASTUPDATE_URL,
                                loggedInAdmin, pictoInformation.getPictoId());
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_DELETE_URL);
                        jpgImgFile.delete();
                        Stage deleteSuccess = cnvrsnSuccess(stage, PropertiesLang.getInstance().getProperty("picto.submit.success.msg"));
                        deleteSuccess.show();
                    }
                    if (!imgDir.isDirectory()) {
                        logger.info("********** Conversion failed for picto.." + pictoImgName);
                        FileUtils.copyFileToDirectory(aiworkFile, localDownloadDir);
                        (new File(propertiesCache.getProperty("picto.img.path") + pictoInformation.getPictoName() + PictoConstants.FILE_AI))
                                .renameTo(new File(propertiesCache.getProperty("picto.img.path") + aiFileName));
                        aiworkFile.delete();
                        logger.info("AI Work file moved to Local image directory, conversion failure for picto.." + pictoImgName);
                        Stage deleteSuccess = cnvrsnSuccess(stage,
                        		PropertiesLang.getInstance().getProperty("picto.cnvrsn.fail.msg") + pictoInformation.getPictoName());
                        deleteSuccess.show();
                    }

                    ZipFile zFile;

                    if (imgDir.exists() && imgDir.isDirectory()) {
                        try {
                            FileUtils.deleteDirectory(imgDir);
                            imgDir.delete();
                            zFile = new ZipFile(tempFolder + File.separator + pictoImgName + PictoConstants.ZIP_FILE);
                            zFile.close();
                            File file = new File(tempFolder + File.separator + pictoImgName + PictoConstants.ZIP_FILE);
                            file.delete();
                        } catch (IOException e) {
                            logger.error("Exception occurred while deleting files from local directory.", e);
                        }

                    }

                }

            } catch (IOException e) {
                logger.error("Error in copying file from downloaded path to Temp Folder.", e);
            }
        } catch (Exception e) {
            m_logger.error(" Exception occure while launch conversion", e);
        }
    }

    /**
     * Method to create popup after successful deletion
     * 
     * @param primaryStage : Primary stage argument to refresh its data
     * @return success delete message popup
     */
    public Stage cnvrsnSuccess(final Stage primaryStage, String responseMsg) {

        final Stage dialogStage = new Stage();
        try {

            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setTitle(PropertiesLang.getInstance().getProperty("dlt.success.title"));
            Button close = new Button("X");
            close.setId("close");
            Label submitSuccessMessage = new Label(responseMsg);
            submitSuccessMessage.setId("deleteSuccessMessage");
            Button okButton = new Button(PropertiesLang.getInstance().getProperty("OK"));
            HBox confirmationBox = new HBox();
            confirmationBox.setAlignment(Pos.CENTER);
            confirmationBox.getChildren().addAll(okButton);
            confirmationBox.setId("okButtonBox");
            okButton.setId("deleteSuccessOk");
            VBox successBox = new VBox();
            successBox.getChildren().addAll(close, submitSuccessMessage, confirmationBox);
            successBox.setId("deleteSuccessBox");
            Scene scene = new Scene(VBoxBuilder.create().children(successBox).alignment(Pos.TOP_CENTER).build());
            dialogStage.setScene(scene);

            scene.getStylesheets().add("META-INF/css/style.css");
            okButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                    try {
                        actionEvents.refreshMethod(primaryStage);
                    } catch (IOException e) {
                        logger.error("Error in creating conversion dialog box.", e);

                    }
                }
            });
            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();

                }
            });

        } catch (Exception e) {
            logger.error("Error in creating conversion dialog box.", e);
        }
        return dialogStage;
    }

    
}
