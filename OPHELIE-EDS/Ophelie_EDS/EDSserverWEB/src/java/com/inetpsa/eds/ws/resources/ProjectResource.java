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
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.ws.model.EdsProjectResponse;
import com.inetpsa.eds.ws.model.EdsResponse;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;

/**
 * This class get all EDS details for given project in XML format. It uses web services to get the details.
 * 
 * @author Geometric Ltd.
 */
@Path("/project")
public class ProjectResource {

    // PUBLIC

    /**
     * This method returns the project Details
     * 
     * @param tokenId Token Id
     * @param projectName Name of Project
     * @param request Http Servlet Request
     * @return {@link EdsProjectResponse}
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public EdsProjectResponse getProjectData(@QueryParam("token-id") String tokenId, @QueryParam("project-name") String projectName,
            @Context HttpServletRequest request) {
        EdsProjectResponse response = null;
        if (tokenId != null && projectName != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Control Token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().after(new Date()))
                ;
            {
                Date startDate = new Date();
                response = getEdsProjectResponseFrom(EDSdao.getInstance().getProjectbyName(projectName), token);
                ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                        "EDS_WS_GS");
            }

        }
        return response;
    }

    // PRIVATE
    /**
     * This method return Eds project response
     * 
     * @param project Eds Project details
     * @param token Web service session token
     * @return {@link EdsProjectResponse}
     */
    private EdsProjectResponse getEdsProjectResponseFrom(EdsProject project, EdsWsSessionToken token) {
        EdsProjectResponse response = new EdsProjectResponse();
        if (project != null) {
            response.setProjectName(project.getPName());
            List<EdsResponse> edsResponses = new ArrayList<EdsResponse>();
            for (EdsEds eds : EDSdao.getInstance().getAllEDSByProject(project)) {
                edsResponses.add(EdsResource.getEdsResponseFrom(eds, token));
            }
            response.setEdses(edsResponses);
        }

        return response;
    }
}
