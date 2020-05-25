package com.inetpsa.eds.ws.sample;

import com.inetpsa.eds.ws.model.UserInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import javax.ws.rs.core.MediaType;

/**
 * This class login to Web service using specified url , then return token and check logout is successful or failed.
 * 
 * @author Geometric Ltd.
 */
public class TokenUseSample {
    /**
     * Constant that hold value of URI
     */
    private static final String BASE_URI = "http://localhost:8080/EDSserverWEB/resources";

    /**
     * Method to start web service
     * 
     * @param args
     */
    public static void main(String[] args) {

        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(BASE_URI).path("authenticate");

        WebResource loginResource = webResource.path("login");
        WebResource logoutResource = webResource.path("logout");

        String token = loginResource.type(MediaType.APPLICATION_JSON).post(String.class, new UserInfo("u123456", "u123456"));
        System.out.println("Le token reçu est : " + token);

        if ("0".equals(logoutResource.queryParam("token-id", token).get(String.class))) {
            System.out.println("Echec du logout !");
        } else {
            System.out.println("Logout réussi !");
        }
        if ("0".equals(logoutResource.queryParam("token-id", token).get(String.class))) {
            System.out.println("Echec du logout !");
        } else {
            System.out.println("Logout réussi !");
        }

        client.destroy();
    }
}
