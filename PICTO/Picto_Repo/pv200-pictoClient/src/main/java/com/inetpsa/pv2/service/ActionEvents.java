/*
 * Creation : Mar 11, 2016
 */
package com.inetpsa.pv2.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.batch.ThickClient;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.props.PropertiesLang;
import com.inetpsa.pv2.rest.RestService;

/**
 * ActionEvents class to call appropriate methods on any event
 * 
 * @author mehaj
 */
public class ActionEvents implements EventHandler<ActionEvent> {
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(ActionEvents.class);
    RestService restService = new RestService();
    /**
     * loading properties file into a variable
     */

    PropertiesCache propertiesCache = PropertiesCache.getInstance();
    /**
     * to retrieve picto details when single picto selected
     */
    private PictoInformation pictoInformation;
    /**
     * row number of picto to verify pictos checkbox weather it is selected or not
     */
    private int pictoRowNumber;
    /**
     * Grid
     */
    private GridPane thickClientUIGrid;
    /**
     * Abandon and submit changes button box for Multi selection
     */
    private HBox multipleActionMenu;
    /**
     * String variable having picto name
     */
    String imgFileName;
    private Long pictoId;
    /**
     * Stage variable to refresh data after update
     */
    private Stage primaryStage;

    /**
     * set of all pictos downloaded by logged in admin
     */
    TreeSet<PictoInformation> allPictoInformation;

    /**
     * Logged in admin Id
     */
    long loggedInAdmin = AdminDetails.getInstance().getLoggedInAdmin();

    public ActionEvents() {

    }

    /**
     * class to handle all on click action Events
     * 
     * @param pictoImageName :String having picto name
     * @param primaryStage : Stage variable to refresh data after update
     * @param pictoDetails : Picto Details
     * @param rowNumber : row number of picto
     * @param grid : grid
     * @param multipleActionMenu : Abandon and submit changes button box for Multi selection
     */
    public ActionEvents(Long pictoId, Stage primaryStage, PictoInformation pictoInformation, int rowNumber, GridPane grid, HBox multipleActionMenu,
            String imageFileName) {
        this.imgFileName = imageFileName;
        this.pictoId = pictoId;
        this.primaryStage = primaryStage;
        this.pictoInformation = pictoInformation;
        this.pictoRowNumber = rowNumber;
        this.thickClientUIGrid = grid;
        this.multipleActionMenu = multipleActionMenu;
        allPictoInformation = PictoDetails.getInstance().getAllPictos();

    }

    @Override
    public void handle(ActionEvent event) {

        Button button = new Button();
        if (event.getSource().getClass() == button.getClass()) {
            if (((Button) event.getSource()).getId().equals("refreshButton")) {
                try {
                    refreshMethod(primaryStage);
                } catch (IOException e) {
                    logger.error(" Exception occure while handle the events ", e);
                }
            }
        } else {
            if (((CheckBox) event.getSource()).getId().equals("singleSelection" + pictoRowNumber)) {
                if (((CheckBox) thickClientUIGrid.lookup("#singleSelection" + pictoRowNumber)).isSelected()) {
                    PictoDetails.getInstance().setselectedPictos(pictoId);
                } else {
                    PictoDetails.getInstance().getselectedPictos().remove(pictoId);
                }
                int selectedPictoCount = selectedCheckboxCnt(allPictoInformation, thickClientUIGrid);
                if (selectedPictoCount > 1) {
                    multipleActionMenu.setVisible(true);
                } else {
                    multipleActionMenu.setVisible(false);
                }
            } else if (((CheckBox) event.getSource()).getId().equals("selectAllPicto")) {
                try {
                    logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_ALLPICTOS_BY_USRID_URL);
                    List<Long> listAllPictosInLocal = PictoDetails.getInstance().listOfAllLocalDirPictos();
                    for (int i = 0; i < listAllPictosInLocal.size(); i++) {
                        PictoDetails.getInstance().setselectedPictos(listAllPictosInLocal.get(i));
                        selectAllPictoClick(thickClientUIGrid);
                    }
                } catch (Exception e) {
                    logger.error(" Exception occure while handle the events ", e);
                }

            }
        }
    }

    /**
     * to refresh data inside grid
     * 
     * @param primaryStage : to refresh grid inside Stage
     * @throws IOException
     */
    public void refreshMethod(Stage primaryStage) throws IOException {

        ThickClient thickClient = new ThickClient();
        thickClient.start(primaryStage);
        PictoDetails.getInstance().getselectedPictos().clear();
    }

    /**
     * method to set multi action menu buttons visible on select all
     * 
     * @param grid : to look up for multi action menu box and set it to visible
     */
    public void selectAllPictoClick(GridPane grid) {
        for (int i = 0; i < allPictoInformation.size(); i++) {
            if (((CheckBox) grid.lookup("#selectAllPicto")).isSelected()) {

                ((CheckBox) grid.lookup("#singleSelection" + i)).setSelected(true);

            } else {
                ((CheckBox) grid.lookup("#singleSelection" + i)).setSelected(false);
                PictoDetails.getInstance().getselectedPictos().clear();
            }
        }
        int c = selectedCheckboxCnt(allPictoInformation, grid);
        if (c > 1) {
            grid.lookup("#multiActionMenu").setVisible(true);
        } else {
            grid.lookup("#multiActionMenu").setVisible(false);
        }
    }

    /**
     * To check count of selected pictos
     * 
     * @param pictoInformation : PictoDetail set having all pictos details displaying on screen
     * @param grid : Gridpane to check picto selection checkbox is selected or not
     * @return selected pictos count
     */

    public int selectedCheckboxCnt(Set<PictoInformation> pictoInfoSet, GridPane grid) {
        int count = 0;
        for (int i = 0; i < pictoInfoSet.size(); i++) {
            if (((CheckBox) grid.lookup("#singleSelection" + i)).isSelected()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Popup Box To create confirmation box for submit and delete
     * 
     * @return dialog box
     * @param yesButton : Confirmation button
     * @param noButton : Confirmation button
     */

    public Stage createConfirmationPopup(Button yesButton, Button noButton, String msgForUsr, String titleForBox) {
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);
        Button close = new Button("X");
        close.setId("close");
        dialogStage.setTitle(titleForBox);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        HBox confirmationBox = new HBox();
        confirmationBox.setAlignment(Pos.CENTER);
        yesButton.setText(PropertiesLang.getInstance().getProperty("YES"));
        yesButton.setId("confirmationButton");
        noButton.setId("confirmationButton");
        noButton.setText(PropertiesLang.getInstance().getProperty("NO"));
        confirmationBox.getChildren().addAll(yesButton, noButton);
        confirmationBox.setId("confirmationBox");
        Text text = new Text(msgForUsr);
        text.setId("confirmationMsg");
        text.setWrappingWidth(315);
        VBox popupBox = new VBox();
        popupBox.setId("popupBox");
        popupBox.getChildren().addAll(close, text, confirmationBox);

        Scene scene = new Scene(VBoxBuilder.create().children(popupBox).alignment(Pos.TOP_CENTER).build());
        scene.getStylesheets().add("META-INF/css/style.css");
        dialogStage.setScene(scene);

        dialogStage.show();
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();

            }
        });
        return dialogStage;
    }

    /**
     * Popup Box To create confirmation box for submit and delete
     * 
     * @return dialog box
     * @param yesButton : Confirmation button
     * @param noButton : Confirmation button
     */

    public Stage errorMsgPopup() {
        final Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        Button okButton = new Button(PropertiesLang.getInstance().getProperty("OK"));
        HBox confirmationBox = new HBox();
        confirmationBox.setAlignment(Pos.CENTER);
        confirmationBox.getChildren().addAll(okButton);
        confirmationBox.setId("okButtonBox");
        okButton.setId("deleteSuccessOk");
        dialogStage.setTitle(PropertiesLang.getInstance().getProperty("picto.err.box.title"));
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        Text text = new Text(PropertiesLang.getInstance().getProperty("picto.download.dir.err.msg"));
        text.setId("errorMsg");
        text.setWrappingWidth(315);
        VBox popupBox = new VBox();
        popupBox.setId("errorPopupBox");
        popupBox.getChildren().addAll(text, confirmationBox);

        Scene scene = new Scene(VBoxBuilder.create().children(popupBox).alignment(Pos.TOP_CENTER).build());
        scene.getStylesheets().add("META-INF/css/style.css");
        dialogStage.setScene(scene);
        dialogStage.show();
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();
                try {
                    refreshMethod(primaryStage);
                } catch (IOException e) {
                    logger.error("Error in creating error dialog box.", e);

                }
            }
        });
        return dialogStage;
    }
}
