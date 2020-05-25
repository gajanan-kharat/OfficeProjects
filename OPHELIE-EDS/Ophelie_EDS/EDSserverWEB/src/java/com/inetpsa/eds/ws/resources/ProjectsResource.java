package com.inetpsa.eds.ws.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;
import com.inetpsa.eds.ws.model.ProjectsResponse;

/**
 * Ws ressource for getting projects list RESSOURCE FOR OPHELIE GS
 * 
 * @author ALTER-FRAME - Clément Hémidy <chemidy@alter-frame.com>
 */
@Path("/projects")
public class ProjectsResource {

    /**
     * Get projects list
     * 
     * @param tokenId Token id for autentication
     * @param request Request object
     * @return ProjectsResponse object
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public ProjectsResponse getProjectData(@QueryParam("token-id") String tokenId, @Context HttpServletRequest request) {
        ProjectsResponse response = null;
        if (tokenId != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Contrôle du token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().after(new Date())
                    && token.getWstSource().equals(EdsWsSessionToken.SOURCE_GS)) {
                Date startDate = new Date();
                response = getProjectsResponseFrom();
                ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                        "EDS_WS_" + token.getWstSource());
            }

        }
        return response;
    }

    /**
     * Get Projects response Construct response
     * 
     * @return ProjectsResponse object
     */
    private ProjectsResponse getProjectsResponseFrom() {
        ProjectsResponse response = new ProjectsResponse();

        List<String> strings = new ArrayList<String>();
        for (EdsProject project : EDSdao.getInstance().getAllProjects()) {
            strings.add(project.getPName());
        }
        response.setProjectsName(strings);

        return response;
    }
}
