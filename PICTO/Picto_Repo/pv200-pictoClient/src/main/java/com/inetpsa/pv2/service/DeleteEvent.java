/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.pv2.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.props.PropertiesLang;
import com.inetpsa.pv2.rest.RestService;

public class DeleteEvent implements EventHandler<ActionEvent> {
    ActionEvents actionEvents = new ActionEvents();
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(DeleteEvent.class);
    /**
     * String variable having picto name
     */
    private String fileName;
    private Long pictoId;
    /**
     * Stage variable to refresh data after update
     */
    private Stage primaryStage;

    /**
     * to retrieve picto details when single picto selected
     */
    private PictoInformation pictoInformation;
    /**
     * loading properties file into a variable
     */

    PropertiesCache propertiesCache = PropertiesCache.getInstance();
    /**
     * Logged in admin Id
     */
    long loggedInAdmin = AdminDetails.getInstance().getLoggedInAdmin();

    RestService restService = new RestService();

    /**
     * class to handle all on click action Events
     * 
     * @param pictoImageName :String having picto name
     * @param primaryStage : Stage variable to refresh data after update
     */
    public DeleteEvent(Long pictoId, Stage primaryStage, PictoInformation pictoInformation, String pictoImgName) {
        this.pictoInformation = pictoInformation;
        this.fileName = pictoImgName;
        this.primaryStage = primaryStage;
        this.pictoId = pictoId;
    }

    public DeleteEvent() {
    }

    @Override
    public void handle(ActionEvent event) {
        if (((Button) event.getSource()).getId().equals("deleteButton")) {
            abandoned(primaryStage, fileName);
        } else if (((Button) event.getSource()).getId().equals("deleteAll")) {
            deleteAllPicto();
        } else if (((Button) event.getSource()).getId().equals("displayAll")) {
            displayAllPicto();
        } else if (((Button) event.getSource()).getId().equals("display")) {
            displayPicto();
        }
    }

    public void displayPicto() {
        PictoInformation pictoInfo = restService.getPictoClient(propertiesCache.getProperty("picto.domain.name")
                + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL, AdminDetails.getInstance().getLoggedInAdmin(), pictoId);
        String aiFileInTempName = pictoInfo.getPicNameInLocalDir().trim() + PictoConstants.FILE_AI;

        if (aiFileInTempName != "") {
            logger.info("===================================================================================");
            logger.info("******************AI IMG FILE NAMES TO OPEN IN ILLUSTRATOR************** " + aiFileInTempName);
            logger.info("====================================================================================");
            Process process;
            try {
                process = Runtime.getRuntime().exec(
                        PictoConstants.RUN_BATCH_COMMAND + propertiesCache.getProperty("open.ai.bat.file.path") + " " + aiFileInTempName);               
                process.waitFor();                
            }
            catch (Exception e) {
                logger.error("Error in executing batch file.", e);
            } 

        }

    }

    public void displayAllPicto() {
        List<Long> selectedPictoList = PictoDetails.getInstance().getselectedPictos();
        String aiFiles = "";
        for (int i = 0; i < selectedPictoList.size(); i++) {
            PictoInformation pictoInfo = restService.getPictoClient(propertiesCache.getProperty("picto.domain.name")
                    + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL, AdminDetails.getInstance().getLoggedInAdmin(), selectedPictoList.get(i));
            String aiFileInTempName = pictoInfo.getPicNameInLocalDir().trim() + PictoConstants.FILE_AI;

            aiFiles = aiFileInTempName + " " + aiFiles;

        }
        if (aiFiles != "") {
            logger.info("===================================================================================");
            logger.info("******************AI IMG FILE NAMES TO OPEN IN ILLUSTRATOR************** " + aiFiles);
            logger.info("====================================================================================");
            Process process;
            try {
                process = Runtime.getRuntime().exec(
                        PictoConstants.RUN_BATCH_COMMAND + propertiesCache.getProperty("open.ai.bat.file.path") + " " + aiFiles);
                process.waitFor();
            } 
            catch (Exception e) {
                logger.error("Error in executing batch file.", e);
            }            
        }
    }

    /**
     * To Check picto selected for deletion and calling abandoned() method
     * 
     * @param primaryStage : to refresh data
     * @param grid : to verify all selected pictos
     * @param yesButton : Confirmation button
     * @param noButton : Confirmation button
     */
    public void deleteAllPicto() {
        final Stage stage = primaryStage;
        /**
         * Confirmation Box Buttons
         */

        Button yesButton = new Button();
        /**
         * Confirmation box buttons
         */

        Button noButton = new Button();
        String msgForUsr = PropertiesLang.getInstance().getProperty("picto.cnfrmn.delte.msg");
        String titleForBox = PropertiesLang.getInstance().getProperty("dlt.confrm.popup.title");
        final Stage dialogStage = actionEvents.createConfirmationPopup(yesButton, noButton, msgForUsr, titleForBox);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Long> selectedPictoList = PictoDetails.getInstance().getselectedPictos();
                try {

                    for (int i = 0; i < selectedPictoList.size(); i++) {
                        logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL);
                        PictoInformation pictoInfo = restService.getPictoClient(propertiesCache.getProperty("picto.domain.name")
                                + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL, AdminDetails.getInstance().getLoggedInAdmin(),
                                selectedPictoList.get(i));
                        final String pictoImgDir = pictoInfo.getPictoName().trim();
                        ZipFile zFile;
                        final File localImgDir = new File(propertiesCache.getProperty("picto.img.path"));
                        String zipFilePath = propertiesCache.getProperty("picto.img.path") + pictoImgDir + PictoConstants.ZIP_FILE;
                        final File[] files = localImgDir.listFiles(new FilenameFilter() {
                            @Override
                            public boolean accept(final File dir, final String name) {
                                return name.startsWith(pictoImgDir);
                            }
                        });
                        for (final File file : files) {
                            if (!file.delete()) {
                                logger.error("Can't remove " + file.getAbsolutePath());
                                System.out.println("Can't remove " + file.getAbsolutePath());
                            }
                        }
                        try {
                            zFile = new ZipFile(zipFilePath);
                            zFile.close();
                            File file = new File(zipFilePath);
                            file.delete();
                        } catch (IOException e) {
                            logger.error("Exception occurred while deleting files from local directory.", e);
                        }
                        restService.deletePicto(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_DELETE_URL, loggedInAdmin,
                                selectedPictoList.get(i));
                        logger.info(PropertiesLang.getInstance().getProperty("picto.delete.msg"));

                    }
                } catch (Exception e) {
                    logger.error("Exception occurred while deleting multiple pictos.", e);
                }
                Stage deleteSuccess = deleteSuccess(stage);
                deleteSuccess.show();
                dialogStage.close();
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
     * To Delete Single Picto from Local directory
     * 
     * @param primaryStage : to refresh data
     * @param filename : picto name
     * @param yesButton : Confirmation button
     * @param noButton : Confirmation button
     */
    public void abandoned(final Stage primaryStage, final String filename) {
        /**
         * Confirmation Box Buttons
         */

        Button yesButton = new Button();
        /**
         * Confirmation box buttons
         */

        Button noButton = new Button();
        String msgForUsr = PropertiesLang.getInstance().getProperty("picto.cnfrmn.delte.msg");
        String titleForDleteWndw = (PropertiesLang.getInstance().getProperty("dlt.confrm.popup.title"));
        final Stage dialogStage = actionEvents.createConfirmationPopup(yesButton, noButton, msgForUsr, titleForDleteWndw);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                final String pictoImgDir = pictoInformation.getPictoName().trim();
                ZipFile zFile;
                final File localImgDir = new File(propertiesCache.getProperty("picto.img.path"));
                String zipFilePath = propertiesCache.getProperty("picto.img.path") + pictoImgDir + PictoConstants.ZIP_FILE;
                final File[] files = localImgDir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(final File dir, final String name) {
                        return name.startsWith(pictoImgDir);
                    }
                });
                for (final File file : files) {
                    if (!file.delete()) {
                        logger.error("Can't remove " + file.getAbsolutePath());
                        System.out.println("Can't remove " + file.getAbsolutePath());
                    }
                }
                try {
                    zFile = new ZipFile(zipFilePath);
                    zFile.close();
                    File file = new File(zipFilePath);
                    file.delete();
                } catch (IOException e) {
                    logger.error("Exception occurred while deleting files from local directory.", e);
                }
                logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_DELETE_URL);
                restService.deletePicto(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_DELETE_URL, loggedInAdmin, pictoId);
                dialogStage.close();
                Stage deleteSuccess = deleteSuccess(primaryStage);
                deleteSuccess.show();
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
     * Method to create popup after successful deletion
     * 
     * @param primaryStage : Primary stage argument to refresh its data
     * @return success delete message popup
     */
    public Stage deleteSuccess(final Stage primaryStage) {
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Button close = new Button("X");
        close.setId("close");
        dialogStage.setTitle(PropertiesLang.getInstance().getProperty("dlt.success.title"));
        Label deleteSuccessMessage = new Label(PropertiesLang.getInstance().getProperty("picto.delete.msg"));
        deleteSuccessMessage.setId("deleteSuccessMessage");
        Button okButton = new Button(PropertiesLang.getInstance().getProperty("OK"));
        HBox confirmationBox = new HBox();
        confirmationBox.setAlignment(Pos.CENTER);
        confirmationBox.getChildren().addAll(okButton);
        confirmationBox.setId("okButtonBox");
        okButton.setId("deleteSuccessOk");
        VBox successBox = new VBox();
        successBox.getChildren().addAll(close, deleteSuccessMessage, confirmationBox);
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
                    logger.error(" Exception occure while creating delete popup. ", e);
                }
            }
        });
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();

            }
        });
        return dialogStage;
    }
}
