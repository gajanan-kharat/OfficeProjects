package com.inetpsa.eds.ws.resources;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;

/**
 * This class detach database objects associated with token after logging out
 * 
 * @author Geometric Ltd.
 */
public class LogoutResource {
    // PUBLIC
    /**
     * This method logout of the resource
     * 
     * @param tokenID Token ID
     * @param request Http servlet request
     * @return check if logout is successful
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@QueryParam("token-id") String tokenID, @Context HttpServletRequest request) {
        Integer succeed = 0;

        EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenID);
        if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress())) {
            succeed = 1;

            Date startDate = new Date();
            EDSdao.getInstance().deleteDetachedDBObject(token);
            ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                    "EDS_WS_" + token.getWstSource());
        }

        return succeed.toString();
    }
}
