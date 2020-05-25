package com.inetpsa.eds.ws.eds;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.ws.model.Eds;
import com.inetpsa.eds.ws.model.EdsesResponse;
import com.inetpsa.eds.ws.model.ProjectsResponse;
import com.inetpsa.eds.ws.resources.GsEdsesRessource;

/**
 * A rest endpoint for EDS projects and sheet. This endpoint is state-less, and credentials needs to be included at each request in the headers, with
 * the values 'login' and 'password'.
 */
// The endpoint is used as follow :
// /projects => Get all the projects
// /projects/my-project => Get the EDSes of the project 'my-project'
// /projects/my-project/my-eds => Get the EDS 'my-eds' of the project 'my-project'
// /projects/my-project/my-eds/1.2 => Get the version 1.2 EDS 'my-eds' of the project 'my-project'
@Path("/projects")
public class EdsWsEndpoint {

    static {
        // ATI monitoring initialization
        try {
            // Initialize the ATI monitoring
            if (!ATIMonitoring.isInitialized())
                ATIMonitoring.initialize(new File(GestionConnector.getResoucePath("atimonitoring.properties").toURI()),
                        new File("OPHELIE/WS_EDS_" + new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date()) + ".appli"));

        } catch (URISyntaxException e) {
        }
    }

    /**
     * Gets the projects.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @return the projects
     */
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_XML)
    public ProjectsResponse getProjects(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            // Get the list of projects
            ProjectsResponse presponse = new ProjectsResponse();

            List<String> strings = new ArrayList<String>();
            for (EdsProject project : EDSdao.getInstance().getAllProjects()) {
                strings.add(project.getPName());
            }
            presponse.setProjectsName(strings);

            // ATI monitoring
            ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                    new Date(), "eds-ws/projects");

            return presponse;

        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

    /**
     * Gets the project.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @param projectRef the project ref
     * @return the project
     */
    @GET
    @Path("/{project-ref}")
    @Produces(MediaType.TEXT_XML)
    public EdsesResponse getProject(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders,
            @PathParam("project-ref") String projectRef) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            // Get the EDS project
            EdsProject project = EDSdao.getInstance().getProjectbyName(projectRef);

            if (project == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds project found with the name '" + projectRef + "'");

            Map<String, Eds> edsProject = new HashMap<String, Eds>();
            for (EdsEds eds : EDSdao.getInstance().getAllEDSByProject(project)) {
                edsProject.put(eds.getEdsRef(), new GsEdsesRessource().getEdsFrom(eds));
            }

            EdsesResponse edsResponse = new EdsesResponse();
            edsResponse.getEdses().addAll(edsProject.values());

            // ATI monitoring
            ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                    new Date(), "eds-ws/projects/project-ref");

            return edsResponse;
        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

    /**
     * Gets the eds.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @param projectRef the project ref
     * @param edsRef the eds ref
     * @return the eds
     */
    @GET
    @Path("/eds/{eds-ref}")
    @Produces(MediaType.TEXT_XML)
    public Eds getEds(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders, @PathParam("eds-ref") String edsRef) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            EdsEds eds = EDSdao.getInstance().getEdsByRef(edsRef);

            if (eds == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds found with the name '" + edsRef + "'");

            Eds edsResponse = new GsEdsesRessource().getEdsFrom(eds);

            // ATI monitoring
            ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                    new Date(), "eds-ws/projects/eds/eds-ref");

            return edsResponse;
        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

    /**
     * Gets the eds version.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @param projectRef the project ref
     * @param edsRef the eds ref
     * @param edsVersion the eds version
     * @return the eds version
     */
    @GET
    @Path("/eds/{eds-ref}/{eds-version}")
    @Produces(MediaType.TEXT_XML)
    public Eds getEdsVersion(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders, @PathParam("eds-ref") String edsRef,
            @PathParam("eds-version") String edsVersion) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            // Get the version
            if (edsVersion.length() < 3 && !edsVersion.contains("."))
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds found with the version '" + edsVersion + "'");

            String[] values = edsVersion.split("\\.");
            // VCOAMS-111 GL code Modification START. Date:22-February-2017
            // Get the EDS
            Eds edsResponse = null;
            EdsEds eds = EDSdao.getInstance().getEdsByRef(edsRef, new Integer(values[0]), new Integer(values[1]));
            if (eds != null) {
                edsResponse = new GsEdsesRessource().getEdsFrom(eds);
                ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                        new Date(), "eds-ws/projects/eds/eds-ref/eds-version");
            }
            // VCOAMS-111 GL code Modification END. Date:22-February-2017
            return edsResponse;

        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

    /**
     * Gets the eds.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @param projectRef the project ref
     * @param edsRef the eds ref
     * @return the eds
     */
    @GET
    @Path("/{project-ref}/{eds-ref}")
    @Produces(MediaType.TEXT_XML)
    public Eds getEds(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders, @PathParam("project-ref") String projectRef,
            @PathParam("eds-ref") String edsRef) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            // Get the EDS project
            EdsProject project = EDSdao.getInstance().getProjectbyName(projectRef);

            if (project == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds project found with the name '" + projectRef + "'");

            EdsEds eds = EDSdao.getInstance().getEdsByRef(edsRef);

            if (eds == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds found with the name '" + edsRef + "'");

            Eds edsResponse = new GsEdsesRessource().getEdsFrom(eds);

            // ATI monitoring
            ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                    new Date(), "eds-ws/projects/project-ref/eds-ref");

            return edsResponse;
        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

    /**
     * Gets the eds version.
     * 
     * @param response the response
     * @param httpHeaders the http headers
     * @param projectRef the project ref
     * @param edsRef the eds ref
     * @param edsVersion the eds version
     * @return the eds version
     */
    @GET
    @Path("/{project-ref}/{eds-ref}/{eds-version}")
    @Produces(MediaType.TEXT_XML)
    public Eds getEdsVersion(@Context HttpServletResponse response, @Context HttpHeaders httpHeaders, @PathParam("project-ref") String projectRef,
            @PathParam("eds-ref") String edsRef, @PathParam("eds-version") String edsVersion) {

        try {
            Date startDate = new Date();
            EdsUser user;

            // Test login
            if ((user = EdsWsLogin.checkCredentials(httpHeaders)) == null) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found with the provided login/password");
            }

            // Get the EDS project
            EdsProject project = EDSdao.getInstance().getProjectbyName(projectRef);

            if (project == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds project found with the name '" + projectRef + "'");

            // Get the version
            if (edsVersion.length() < 3 && !edsVersion.contains("."))
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND, "No eds found with the version '" + edsVersion + "'");

            String[] values = edsVersion.split("\\.");

            // Get the EDS
            EdsEds eds = EDSdao.getInstance().getEdsByRef(edsRef, new Integer(values[0]), new Integer(values[1]));

            if (eds == null)
                throw new EdsWsException(HttpServletResponse.SC_NOT_FOUND,
                        "No eds found with the name '" + edsRef + "' and the version '" + edsVersion + "'");

            Eds edsResponse = new GsEdsesRessource().getEdsFrom(eds);

            // ATI monitoring
            ATIMonitoring.log(EdsWsLogin.getHostname(httpHeaders), user.getUPsaId(), EdsWsLogin.getAppID(httpHeaders), "", "", "", startDate,
                    new Date(), "eds-ws/projects/project-ref/eds-ref/eds-version");

            return edsResponse;
        } catch (EdsWsException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }

        return null;
    }

}
