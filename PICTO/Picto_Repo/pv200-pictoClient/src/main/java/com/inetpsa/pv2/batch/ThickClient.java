package com.inetpsa.pv2.batch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.constants.PictoClientConstants;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.props.PropertiesLang;
import com.inetpsa.pv2.rest.RestService;
import com.inetpsa.pv2.service.ActionEvents;
import com.inetpsa.pv2.service.AdminDetails;
import com.inetpsa.pv2.service.DeleteEvent;
import com.inetpsa.pv2.service.PictoDetails;
import com.inetpsa.pv2.service.SubmitPictoEvent;

/**
 * Thick Client UI
 * 
 * @author mehaj
 */
public class ThickClient extends Application {
    /**
     * Action Events class object to access its methods
     */
    ActionEvents actionEvents = new ActionEvents();
    /**
     * Logger log4j to write messages
     */
    private static final Logger logger = LoggerFactory.getLogger(ThickClient.class);
    /**
     * RestService class object to access rest call methods
     */
    RestService restService = new RestService();
    /**
     * Object for PictoInformation
     */
    private PictoInformation pictoInformation;
    /**
     * loading properties file
     */
    private static PropertiesCache propertiesCache = PropertiesCache.getInstance();

    /*
     * Screen height and width
     */
    final static double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    final static double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

    /**
     * Setting launch window in center.
     */
    /* GK - PSA - PRP024006-27 - 18-Jul-16 - Start */
    static int count;
    static {
        count = 1;
    }

    /* GK - PSA - PRP024006-27 - 18-Jul-16 - End */

    /**
     * Application Start method
     * 
     * @throws IOException
     */
    
    @Override
    public void start(final Stage primaryStage) throws IOException {
    	
        logger.info("==================================================================");
        logger.info("######### Start: Launch Thick Client in start method ############ ");
        logger.info("==================================================================");
        final long adminId = AdminDetails.getInstance().getLoggedInAdmin();
        File file = new File(propertiesCache.getProperty("picto.img.path"));
        if (file.exists() && file.isDirectory()) {
            logger.debug("Starting application for user in start , method of Thick Client Class:" + adminId);
            restService.setThickClientFlag(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_SET_LAUNCH_FLAG_URL,
                    PictoClientConstants.TRUE.returnVal(), adminId);
            logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_SET_LAUNCH_FLAG_URL);
            try {
                logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_PICTOS_BY_USRID_URL);
                List<PictoInformation> pictoClientList = restService.getAllPictosForUsrId(propertiesCache.getProperty("picto.domain.name")
                        + PictoConstants.GET_ALLPICTOS_BY_USRID_URL, adminId);
                logger.debug("List picto ids to download pictos." + pictoClientList);
                List<String> aiFileNames = new ArrayList<String>();

                if (pictoClientList != null) {
                    String aiFiles = "";
                    for (int i = 0; i < pictoClientList.size(); i++) {
                        final Long pictoId = pictoClientList.get(i).getPictoId();
                        if (pictoClientList.get(i).getDownloadFlag() == 0) {
                            logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_DWNLOAD_URL);
                            restService.downloadAiFile(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_IMG_DWNLOAD_URL,
                                    pictoClientList.get(i).getPictoName(), pictoClientList.get(i).getPicNameInLocalDir());
                            logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.PICTO_RESET_DWNLOAD_FLAG_URL);
                            restService.resetDownloadFlag(propertiesCache.getProperty("picto.domain.name")
                                    + PictoConstants.PICTO_RESET_DWNLOAD_FLAG_URL, adminId, pictoId);
                            aiFileNames.add(pictoClientList.get(i).getPicNameInLocalDir().trim() + PictoConstants.FILE_AI);
                            aiFiles = pictoClientList.get(i).getPicNameInLocalDir().trim() + PictoConstants.FILE_AI + " " + aiFiles;
                        } else if (pictoClientList.get(i).isOpenLocalImg()) {
                            aiFileNames.add(pictoClientList.get(i).getPicNameInLocalDir().trim() + PictoConstants.FILE_AI);
                            aiFiles = pictoClientList.get(i).getPicNameInLocalDir().trim() + PictoConstants.FILE_AI + " " + aiFiles;
                        }
                    }
                    restService.deleteLocalZipFiles(PropertiesCache.getInstance().getProperty("picto.img.path"));
                    if (aiFiles != "") {
                        logger.info("===================================================================================");
                        logger.info("******************AI IMG FILE NAMES TO OPEN IN ILLUSTRATOR************** " + aiFiles);
                        logger.info("====================================================================================");
                        Process process = Runtime.getRuntime().exec(
                                PictoConstants.RUN_BATCH_COMMAND + propertiesCache.getProperty("open.ai.bat.file.path") + " " + aiFiles);
                        process.waitFor();
                    }
                }

                else {

                    logger.debug("Null pictoClientList returned from Rest Services in start method of Thick Client class");
                }

                logger.info("==============================================================");
                logger.info("######### MIDWAY: Launch Thick Client  ############ ");
                logger.info("==============================================================");

            } catch (Exception e) {
                logger.error("No files found to download ", e);
            }
            try {
                final HBox multiActionMenu = new HBox();
                final GridPane grid = new GridPane();
                grid.getStyleClass().add(PropertiesLang.getInstance().getProperty("grid"));
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(grid);

                ScrollBar vScrollBar = new ScrollBar();
                vScrollBar.setOrientation(Orientation.VERTICAL);
                vScrollBar.minProperty().bind(scrollPane.vminProperty());
                vScrollBar.maxProperty().bind(scrollPane.vmaxProperty());
                vScrollBar.visibleAmountProperty().bind(scrollPane.heightProperty().divide(grid.heightProperty()));
                scrollPane.vvalueProperty().bindBidirectional(vScrollBar.valueProperty());
                TreeSet<PictoInformation> allPictos = PictoDetails.getInstance().getAllPictos();
                createHeader(primaryStage, multiActionMenu, grid, allPictos);
                if (allPictos != null) {
                    pictoDetailBarCreation(primaryStage, multiActionMenu, grid, allPictos);
                } else {
                    logger.info("List of picto downloded by Admin is empty. in start method of Thick Client Class");
                }
                if (screenWidth > 0.0D && screenHeight > 0.0D) {

                    primaryStagePos(primaryStage, scrollPane);
                } else {

                    logger.debug("Zero or less than Zero screenWidth and screenHeight in start method of ThickClient class");
                }

            }

            catch (Exception e1) {

                logger.error("Exception in creating Scrollbar pane and Stage in Thick Client start method", e1);
            }

        } else {
            // popup window
            actionEvents.errorMsgPopup();
        }

    }

    /**
     * @param primaryStage
     * @param scrollPane
     */
    private static void primaryStagePos(final Stage primaryStage, ScrollPane scrollPane) {
        final Scene scene = new Scene(scrollPane, screenWidth, screenHeight);
        scene.getStylesheets().add(propertiesCache.getProperty("picto.css.file.path"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(PictoConstants.SUCCESS);

        primaryStage.setMaxHeight(screenHeight);
        primaryStage.setMaxWidth(screenWidth);
        /* GK - PSA - PRP024006-27 - 18-Jul-16 - Start */
        if (count == 1) {
            primaryStage.centerOnScreen();
            count++;
        }
        /* GK - PSA - PRP024006-27 - 18-Jul-16 - End */
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowevent) {

                primaryStage.close();

                logger.info("=================================================================");
                logger.info("################### End: Thick Client Close #####################");
                logger.info("=================================================================");
            }
        });
    }

    /**
     * Creating rows for each picto
     * 
     * @param primaryStage : to refresh data
     * @param multipleActionMenu : Show and hide Abandon/Submit changes buttons
     * @param grid : To set header elements inside grid
     */
    public void pictoDetailBarCreation(final Stage primaryStage, final HBox multiActionMenu, final GridPane grid,
            final Set<PictoInformation> allPictos) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/mm/yyyy");

        if (primaryStage != null && multiActionMenu != null && grid != null && allPictos != null) {
            try {
                logger.info("Start Creating row for each picto downloaded by Admin in pictoDetailBarCreation method of Thick Client Class.");
                int rowNumber = 0;
                final Iterator<PictoInformation> pictoInformationItr = allPictos.iterator();
                while (pictoInformationItr.hasNext()) {
                    final PictoInformation pictoClient = pictoInformationItr.next();
                   // String pictoImgDir = pictoClient.getPictoName();

                    File aiImage = new File(propertiesCache.getProperty("picto.img.path") + pictoClient.getPicNameInLocalDir()
                            + PictoConstants.FILE_AI);

                    if (aiImage.exists() && !aiImage.isDirectory()) {
                        File pngImage = new File(propertiesCache.getProperty("picto.img.path") + pictoClient.getPicNameInLocalDir()
                                + PictoConstants.FILE_PNG);
                        File jpgImage = new File(propertiesCache.getProperty("picto.img.path") + pictoClient.getPicNameInLocalDir()
                                + PictoConstants.FILE_JPG);
                        Image image = new Image(propertiesCache.getProperty("picto.default.icon"));
                        if (pngImage.exists() && !pngImage.isDirectory()) {
                            image = new Image(pngImage.toURI().toString());
                        } else if (jpgImage.exists() && !jpgImage.isDirectory()) {
                            image = new Image(jpgImage.toURI().toString());
                        }
                        VBox actionButtonBox;
                        final CheckBox singleSelection = new CheckBox();
                        Tooltip checkboxTtip = new Tooltip("Click to Select Picto");
                        singleSelection.setTooltip(checkboxTtip);
                        checkboxTtip.setId("tooltip");

                        singleSelection.setId("singleSelection" + rowNumber);
                        singleSelection.getStyleClass().add(PropertiesLang.getInstance().getProperty("single.selection"));

                        final ImageView pictoimage = new ImageView();
                        pictoimage.setImage(image);
                        pictoimage.setId("pictoImg");
                        pictoimage.setFitHeight(80);
                        pictoimage.setFitWidth(80);
                        final BorderPane pictoimagepane = new BorderPane();
                        pictoimagepane.setCenter(pictoimage);
                        pictoimagepane.setId(PropertiesLang.getInstance().getProperty("picto.image.id"));
                        final String pictoImageName = pictoClient.getPictoName();
                        final Long pictoId = pictoClient.getPictoId();
                        List<String> adminList;
                        String workingAdminList = null;
                        if (pictoClient.getWorkingAdminList() != null) {
                            adminList = pictoClient.getWorkingAdminList();
                            workingAdminList = adminList.toString().replaceAll("\\[|\\]", " ");
                        }

                        final VBox informationVBox = new VBox();
                        informationVBox.setId("informationVBox");
                        informationVBox.setMinWidth(screenWidth - 420);
                        informationVBox.setMaxWidth(screenWidth - 420);
                        Label pictoImgName = new Label(PropertiesLang.getInstance().getProperty("picto.name.label") + pictoClient.getPictoName());
                        pictoImgName.setId(PropertiesLang.getInstance().getProperty("picto.name"));
                        Date dwnloadDate;
                        String formattedDwnldDate = null;
                        if (pictoClient.getDownloadDate() != null) {
                            dwnloadDate = formatter.parse(pictoClient.getDownloadDate());
                            formattedDwnldDate = outputFormatter.format(dwnloadDate);
                        }

                        Date updtDate;
                        String formattedUpdtDate = null;
                        if (pictoClient.getUpdateDate() != null) {
                            updtDate = formatter.parse(pictoClient.getUpdateDate());
                            formattedUpdtDate = outputFormatter.format(updtDate);
                        }
                        Date modifyDate;
                        String formattedModifyDate = null;
                        if (pictoClient.getModDate() != null) {
                            modifyDate = formatter.parse(pictoClient.getModDate());
                            formattedModifyDate = outputFormatter.format(modifyDate);
                        }
                        informationVBox.getChildren()
                                .addAll(pictoImgName,
                                        new Label(PropertiesLang.getInstance().getProperty("recovery.date") + formattedDwnldDate),
                                        new Label(PropertiesLang.getInstance().getProperty("update.date") + formattedUpdtDate + " Par "
                                                + pictoClient.getLastUpdateAdmin()),
                                        new Label(PropertiesLang.getInstance().getProperty("modification.date") + formattedModifyDate + " Par "
                                                + pictoClient.getLastModAdmin()),
                                        new Label(PropertiesLang.getInstance().getProperty("list.admin") + workingAdminList));
                        final Button deleteButton = new Button(PropertiesLang.getInstance().getProperty("delete"));
                        deleteButton.setMaxWidth(Double.MAX_VALUE);
                        deleteButton.setId(PropertiesLang.getInstance().getProperty("delete.button.id"));
                        Tooltip tooltip = new Tooltip(PropertiesLang.getInstance().getProperty("delete"));
                        deleteButton.setTooltip(tooltip);
                        tooltip.setId("tooltip");
                        deleteButton.setOnAction(new DeleteEvent(pictoId, primaryStage, pictoClient, pictoImageName));
                        final Button submitButton = new Button(PropertiesLang.getInstance().getProperty("submit"));
                        Tooltip submitTtip = new Tooltip(PropertiesLang.getInstance().getProperty("submit"));
                        submitButton.setTooltip(submitTtip);
                        submitTtip.setId("tooltip");

                        submitButton.setMaxWidth(Double.MAX_VALUE);
                        submitButton.setId(PropertiesLang.getInstance().getProperty("submit.button.id"));
                        submitButton.setOnAction(new SubmitPictoEvent(primaryStage, pictoClient));
                        /* MJ Added 18-Aug-2016 PSA PRP024006-78 Starts */
                        final Button display = new Button(PropertiesLang.getInstance().getProperty("display"));
                        Tooltip displayTooltip = new Tooltip(PropertiesLang.getInstance().getProperty("display"));
                        display.setTooltip(displayTooltip);
                        displayTooltip.setId("tooltip");
                        display.setId("display");
                        display.setMaxWidth(Double.MAX_VALUE);
                        display.setOnAction(new DeleteEvent(pictoId, primaryStage, pictoClient, pictoImageName));
                        /* MJ Added 18-Aug-2016 PSA PRP024006-78 Ends */
                        actionButtonBox = new VBox();
                        actionButtonBox.getChildren().addAll(display, deleteButton, submitButton);

                        actionButtonBox.setId(PropertiesLang.getInstance().getProperty("action.button.box"));
                        final HBox pictoHBox = new HBox();
                        pictoHBox.getChildren().addAll(singleSelection, pictoimagepane, actionButtonBox, informationVBox);

                        pictoHBox.getStyleClass().add(PropertiesLang.getInstance().getProperty("picto.hbox"));
                        grid.add(pictoHBox, 0, rowNumber + 4);

                        singleSelection.setOnAction(new ActionEvents(pictoId, primaryStage, pictoClient, rowNumber, grid, multiActionMenu,
                                pictoImageName));

                        logger.info("Row created for Downloaded Picto :" + pictoClient.getPictoName());
                    }
                    rowNumber++;
                }
                logger.info("Creating row for each picto downloaded by Admin Sucessfull in pictoDetailBarCreation method of pictoDetailBarCreation of Thick Client Class.");
            } catch (Exception e) {

                logger.error(
                        "Error in Creating row for each picto downloaded by Admin in pictoDetailBarCreation method of pictoDetailBarCreation of Thick Client Class.",
                        e);
            }
        } else {

            logger.debug("Null Arguments passed in method pictoDetailBarCreation of Thick Client Class " + primaryStage + " " + multiActionMenu + " "
                    + grid + " ");
        }
    }

    /**
     * To create header
     * 
     * @param primaryStage to add header to it
     * @param multiActionMenu : Show and hide Abandon/Submit changes buttons
     * @param grid : To set header elements inside grid
     * @param allPictos Set containing all pictos
     */
    public void createHeader(final Stage primaryStage, final HBox multiActionMenu, final GridPane grid, final Set<PictoInformation> allPictos) {
        logger.info("Start Creating Header in Thick Client Class in method createHeader");
        if (primaryStage != null && multiActionMenu != null && grid != null) {
            try {
                String pictoImageName = null;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = sdf.format(date);
                primaryStage.setTitle(" Version : " + propertiesCache.getProperty("application.version") + " " + formattedDate);
                final Long pictoId = null;
                final int rowNumber = 0; // integer for checkbox id creation
                final HBox upperHeader = new HBox();
                final Label title = new Label(PropertiesLang.getInstance().getProperty("title"));
                title.setId(PropertiesLang.getInstance().getProperty("title.id")); // adding
                                                                      // id
                                                                      // for
                                                                      // CSS
                final Button refreshBtn = new Button();
                String pictoImgName = null;
                Tooltip refreshBtnTtip = new Tooltip(PropertiesLang.getInstance().getProperty("picto.refresh"));
                refreshBtn.setTooltip(refreshBtnTtip);
                refreshBtnTtip.setId("tooltip");

                final Image refreshImage = new Image(propertiesCache.getProperty("picto.refresh.icon"));
                final ImageView refreshImgView = new ImageView();
                refreshImgView.setImage(refreshImage);
                refreshBtn.setId(PropertiesLang.getInstance().getProperty("refresh.btn.id"));
                refreshBtn.setGraphic(new ImageView(refreshImage));
                refreshBtn.setOnAction(new ActionEvents(pictoId, primaryStage, pictoInformation, rowNumber, grid, multiActionMenu, pictoImageName));
                upperHeader.getChildren().addAll(title, refreshBtn);
                grid.add(upperHeader, 0, 0);
                final Label header = new Label(PropertiesLang.getInstance().getProperty("ui.info.msg"));
                header.setId(PropertiesLang.getInstance().getProperty("header"));
                grid.add(header, 0, 1);
                final HBox multiMenuBar = new HBox();
                final CheckBox selectAllPicto = new CheckBox(PropertiesLang.getInstance().getProperty("select.deselect.all"));
                if (allPictos.size() == 0 || allPictos.isEmpty()) {
                    selectAllPicto.setDisable(true);
                }
                Tooltip checkboxTtip = new Tooltip("Click to Select All Pictos");

                selectAllPicto.setTooltip(checkboxTtip);
                checkboxTtip.setId("tooltip");

                selectAllPicto.setId(PropertiesLang.getInstance().getProperty("select.all.picto"));
                selectAllPicto
                        .setOnAction(new ActionEvents(pictoId, primaryStage, pictoInformation, rowNumber, grid, multiActionMenu, pictoImageName));
                final Button deleteAll = new Button(PropertiesLang.getInstance().getProperty("delete.all"));

                Tooltip deleteAllTtip = new Tooltip(PropertiesLang.getInstance().getProperty("delete.all"));
                deleteAll.setTooltip(deleteAllTtip);
                deleteAllTtip.setId("tooltip");
                deleteAll.setId(PropertiesLang.getInstance().getProperty("dlt.multiple.id"));
                deleteAll.setOnAction(new DeleteEvent(pictoId, primaryStage, pictoInformation, pictoImgName));
                /* MJ Added 18-Aug-2016 PSA PRP024006-78 Starts */
                final Button displayAll = new Button(PropertiesLang.getInstance().getProperty("display.all"));
                Tooltip displayAllTooltip = new Tooltip(PropertiesLang.getInstance().getProperty("display.all"));
                displayAll.setTooltip(displayAllTooltip);
                displayAllTooltip.setId("tooltip");
                displayAll.setId("displayAll");
                displayAll.setOnAction(new DeleteEvent(pictoId, primaryStage, pictoInformation, pictoImgName));
                /* MJ Added 18-Aug-2016 PSA PRP024006-78 Ends */
                final Button submitAll = new Button(PropertiesLang.getInstance().getProperty("submit.all"));
                Tooltip submitAllTtip = new Tooltip(PropertiesLang.getInstance().getProperty("submit.all"));
                submitAll.setTooltip(submitAllTtip);
                submitAllTtip.setId("tooltip");
                submitAll.setId(PropertiesLang.getInstance().getProperty("submit.multiple"));
                submitAll.setOnAction(new SubmitPictoEvent(primaryStage, pictoInformation));
                multiActionMenu.getChildren().addAll(displayAll, deleteAll, submitAll);
                multiActionMenu.setId(PropertiesLang.getInstance().getProperty("multi.action.box"));
                multiActionMenu.setVisible(false);
                multiMenuBar.getChildren().addAll(selectAllPicto, multiActionMenu);
                grid.add(multiMenuBar, 0, 2);
                logger.info(" Creating Header completed sucessfully in Thick Client Class in method createHeader");
            } catch (Exception e) {

                logger.error("Error in creating header in method createHeader of Thick Client Java Class", e);
            }
        } else {
            logger.debug("Null Arguments passed in method createHeader of Thick Client Class");
        }
    }

    /**
     * to get the selected row count
     * 
     * @return
     * @return integer numberOfRowsSelectedCount
     */
    public ScrollBar scrollBarCreation() {
        final ScrollBar scrollbar = new ScrollBar(); // for scroll
        scrollbar.setOrientation(Orientation.VERTICAL);
        return scrollbar;
    }

}
