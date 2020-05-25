package com.inetpsa.pv2.batch;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.rest.RestService;
import com.inetpsa.pv2.service.AdminDetails;
import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * TODO : Thick Client Login Page
 * 
 * @author mehaj
 */
public class ThickClientLogin extends Application {
    /**
     * Logger log4j to write messages
     */
    private static final Logger logger = LoggerFactory.getLogger(ThickClientLogin.class);
    /**
     * RestService class object to access rest call methods
     */
    RestService restService = new RestService();
    /**
     * Thick client UI
     */
    ThickClient thickClient = new ThickClient();
    String checkUser, checkPw, language;
    TextField txtUserName;
    Label lblPassword, lblMessage;
    PasswordField pf;
    final ComboBox<String> langComboBox = new ComboBox<String>();

    /**
     * Main method to start launching login page
     * 
     * @param args
     */

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            logger.error("Exception Occured in main method of Thick Client launch args", e);
        }
    }

    @Override
    public void start(final Stage primaryStage) {

        logger.info("==============================================================");
        logger.info("########### Start : Thick Client Login   #################### ");
        logger.info("==============================================================");
        try {
            primaryStage.setTitle("Pictotech V2 Thickclient Login");

            BorderPane bp = new BorderPane();
            bp.setPadding(new Insets(10, 50, 50, 50));

            // Adding HBox
            HBox hb = new HBox();
            hb.setPadding(new Insets(20, 20, 20, 30));

            // Adding GridPane
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(20, 20, 20, 20));
            gridPane.setHgap(5);
            gridPane.setVgap(5);

            // Implementing Nodes for GridPane
            Label lblUserName = new Label("Username:");
            lblUserName.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
            txtUserName = new TextField();
            txtUserName.setPromptText("Username");
            txtUserName.setMinWidth(210);
            lblPassword = new Label("Password:");
            lblPassword.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
            pf = new PasswordField();
            pf.setPromptText("********");
            txtUserName.setMaxWidth(210);
            
            Label lang = new Label("Language:");
            lang.setFont(Font.font("Courier New", FontWeight.BOLD, 16));          
            langComboBox.getItems().addAll("Fr",
            		"En"           
            );
            langComboBox.setValue("Fr");
            

            Button btnLogin = new Button("Login");
            btnLogin.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            btnLogin.setMinWidth(100);
            btnLogin.setMinHeight(30);
            lblMessage = new Label();

            Label lblStar = new Label("*");
            lblStar.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
            lblStar.setTextFill(Color.RED);

            Label lblStar1 = new Label("*");
            lblStar1.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
            lblStar1.setTextFill(Color.RED);

            gridPane.add(lblUserName, 0, 0);
            gridPane.add(txtUserName, 1, 0);
            gridPane.add(lblPassword, 0, 1);
            gridPane.add(pf, 1, 1);
            gridPane.add(btnLogin, 1, 3);
            gridPane.add(lblMessage, 1, 4);
            
            gridPane.add(lang, 0, 2);
            gridPane.add(langComboBox, 1, 2);

            gridPane.add(lblStar, 2, 0);
            gridPane.add(lblStar1, 2, 1);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(5);
            dropShadow.setOffsetY(5);

            Text text = new Text("Pictotech V2 Thickclient Login");
            text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
            text.setEffect(dropShadow);

            hb.getChildren().add(text);

            bp.setId("bp");
            gridPane.setId("root");
            btnLogin.setId("btnLogin");
            text.setId("text");

            // Action for Login Button
            btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loginThickClient(primaryStage);
                }
            }); 
            
            pf.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        loginThickClient(primaryStage);
                    }
                }
            });
            txtUserName.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        loginThickClient(primaryStage);
                    }
                }
            });

            // Add HBox and GridPane layout to BorderPane Layout
            bp.setTop(hb);
            bp.setCenter(gridPane);
            // Adding BorderPane to the scene and loading CSS
            Scene scene = new Scene(bp);

            primaryStage.setScene(scene);

            primaryStage.setResizable(false);
            primaryStage.show();
            logger.info("=====================================================================");
            logger.info("########### Start : Thick Client Login Page loaded ################# ");
            logger.info("=====================================================================");

        } catch (Exception e) {

            logger.error("Exception occured launching thick client in start method of ThickClientLogin class....", e);
        }
    }

    private void loginThickClient(final Stage primaryStage) {

        if (primaryStage != null) {
            checkUser = txtUserName.getText();
            checkPw = pf.getText();
            language=langComboBox.getValue();

            if (checkUser != null && !checkUser.isEmpty() && checkPw != null && !checkPw.isEmpty()) {

                logger.info("USer Id: " + checkUser + " Password " + checkPw);
                String authString = checkUser + ":" + checkPw;
                String authStringEnc = new Base64Encoder().encode(authString.getBytes());
                AdminDetails.getInstance().setAuthStringEnc(authStringEnc);
                AdminDetails.getInstance().setUserName(checkUser);
                AdminDetails.getInstance().setPassword(checkPw);
                AdminDetails.getInstance().setLanguage(language);

                String adminId = restService.authenticateUsr(PropertiesCache.getInstance().getProperty("picto.domain.name")
                        + PictoConstants.PICTO_LDAP_AUTHENTICATION_URL, checkUser, checkPw);

                if (!adminId.isEmpty()) {

                    AdminDetails.getInstance().setLoggedInAdmin(adminId);
                    logger.info("Start Loading Thick Client Application.");
                    try {
                        thickClient.start(primaryStage);

                    } catch (Exception e) {
                        logger.error("Error loading Thick Client Application ", e);
                    }
                } else {
                    logger.debug("===========================================");
                    logger.debug("########### Invalid user ################# ");
                    logger.debug("===========================================");
                    lblMessage.setText("Incorrect Username or Password.");
                    lblMessage.setTextFill(Color.RED);
                }

                txtUserName.setText("");
                pf.setText("");
                lblMessage.setText("");

            } else {
                logger.debug("=========== User name or Password is empty =========");
                lblMessage.setText("Username and Pasword is mandatory !");
                lblMessage.setTextFill(Color.RED);

            }
        } else {
            logger.debug("primaryStage is null in loginThickClient method of ThickClientLogin class");
        }

    }
}