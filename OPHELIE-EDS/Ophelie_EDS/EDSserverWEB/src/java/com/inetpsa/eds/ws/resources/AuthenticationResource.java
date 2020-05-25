package com.inetpsa.eds.ws.resources;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;

/**
 * This class provide entry(login) and exit(logout) point for resource.
 * 
 * @author Geometric Ltd.
 */
@Path("/authenticate")
public class AuthenticationResource {

    static {
        // ATI monitoring initialization
        try {
            // Initialize the ATI monitoring
            if (!ATIMonitoring.isInitialized())
                ATIMonitoring.initialize(new File(GestionConnector.getResoucePath("atimonitoring.properties").toURI()), new File("OPHELIE/WS_EDS_"
                        + new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date()) + ".appli"));

        } catch (URISyntaxException e) {
        }
    }

    // PUBLIC
    /**
     * This method login to Web service
     * 
     * @return new login resource
     */
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public LoginResource login() {
        return new LoginResource();
    }

    /**
     * This method login to Web service for GS BILAN application
     * 
     * @return new login resource
     */
    @Path("/login/gs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public LoginResource logings() {
        return new LoginResource(EdsWsSessionToken.SOURCE_GS, "ROLE.OPN.CONC_BILAN", false);
    }

    /**
     * This method logout of web service
     * 
     * @return new logout resource
     */
    @Path("/logout")
    @Consumes(MediaType.TEXT_PLAIN)
    public LogoutResource logout() {
        return new LogoutResource();
    }

    /**
     * This method says hello
     * 
     * @return String holding message
     */
    @Path("/hello")
    @GET
    public String hello() {
        return "hello world";
    }
}
