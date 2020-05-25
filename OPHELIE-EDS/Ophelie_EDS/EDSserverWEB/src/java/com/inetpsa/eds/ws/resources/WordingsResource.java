package com.inetpsa.eds.ws.resources;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;
import com.inetpsa.eds.ws.model.Wording;
import com.inetpsa.eds.ws.model.WordingsResponse;

/**
 * Ws ressource for getting projects list RESSOURCE FOR OPHELIE GS
 * 
 * @author ALTER-FRAME - Clément Hémidy <chemidy@alter-frame.com>
 */
@Path("/wordings")
public class WordingsResource {

    /**
     * Get projects list
     * 
     * @param tokenId Token id for authentification
     * @param request Request object
     * @return ProjectsResponse object
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public WordingsResponse getWordingData(@QueryParam("token-id") String tokenId, @Context HttpServletRequest request) {

        WordingsResponse response = null;

        if (tokenId != null) {
            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Contrôle du token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().after(new Date())
                    && token.getWstSource().equals(EdsWsSessionToken.SOURCE_GS)) {
                Date startDate = new Date();
                response = getWordingsResponseFrom();
                ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                        "EDS_WS_GS");
            }
        }

        return response;
    }

    /**
     * Get Projects response Construct response
     * 
     * @return ProjectsResponse object
     */
    private WordingsResponse getWordingsResponseFrom() {
        WordingsResponse response = new WordingsResponse();

        List<EdsWording> edsWordings;
        edsWordings = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SF_ALTERNANTEUR);
        for (EdsWording edsWording : edsWordings) {
            Wording w = new Wording();
            w.setWId(edsWording.getWId());
            w.setWValue(edsWording.getValueByLocale(Locale.FRENCH));
            response.getSubAltFct().add(w);
        }

        edsWordings = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SF_BATTERIE);
        for (EdsWording edsWording : edsWordings) {
            Wording w = new Wording();
            w.setWId(edsWording.getWId());
            w.setWValue(edsWording.getValueByLocale(Locale.FRENCH));
            response.getSubBatFct().add(w);
        }

        edsWordings = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SS_ALTERNANTEUR);
        for (EdsWording edsWording : edsWordings) {
            Wording w = new Wording();
            w.setWId(edsWording.getWId());
            w.setWValue(edsWording.getValueByLocale(Locale.FRENCH));
            response.getSubAltSys().add(w);
        }

        edsWordings = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SS_BATTERIE);
        for (EdsWording edsWording : edsWordings) {
            Wording w = new Wording();
            w.setWId(edsWording.getWId());
            w.setWValue(edsWording.getValueByLocale(Locale.FRENCH));
            response.getSubBatSys().add(w);
        }

        return response;
    }
}
