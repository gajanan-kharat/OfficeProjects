package com.inetpsa.eds.ws.sample;

import com.inetpsa.eds.ws.model.EdsRequest;
import com.inetpsa.eds.ws.model.UserInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;

/**
 * This class login to web service located at specified url , request Eds data and store the response in xml file and then log out.
 * 
 * @author Geometric Ltd.
 */
public class EdsUseSample {

    /**
     * Constant that hold value of URI
     */
    private static final String BASE_URI = "http://localhost:8080/EDSserverWEB/resources";

    /**
     * This method login to web service , query for EDS data, store response in xml and then log out of web service
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource edsResource = client.resource(BASE_URI).path("eds");
        String tokenID = login(new UserInfo("u123456", "u123456"));
        String response = edsResource.queryParam("token-id", tokenID).queryParam("eds-ref", "CMM_1").get(String.class);
        logout(tokenID);
        OutputStreamWriter writer = null;
        try {
            File f = new File("test.xml");
            writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            writer.write(response);
        } catch (IOException e) {
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(EdsUseSample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method login to Web service located at specified URL and return token
     * 
     * @param userInfo User details of user to be logged in.
     * @return token unique token id per user.
     */
    private static String login(UserInfo userInfo) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(BASE_URI).path("authenticate");

        WebResource loginResource = webResource.path("login");

        String token = loginResource.type(MediaType.APPLICATION_JSON).post(String.class, userInfo);
        client.destroy();
        return token;
    }

    /**
     * This method logout of the web service
     * 
     * @param tokenID
     */
    private static void logout(String tokenID) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(BASE_URI).path("authenticate");

        WebResource logoutResource = webResource.path("logout");

        logoutResource.queryParam("token-id", tokenID).get(String.class);
        client.destroy();
    }
}
