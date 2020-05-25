package com.inetpsa.eds.ws.resources;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.clp.LDAPGroup;
import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.core.LdapAuthStatus;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.clp.exception.LDAPObjNotFoundException;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;
import com.inetpsa.eds.ws.model.UserInfo;

/**
 * This class authenticate user and then login to LDAP
 * 
 * @author Geometric Ltd.
 */
public class LoginResource {

    private String source = EdsWsSessionToken.SOURCE_DEFAULT;
    private String SA = null;
    private boolean checkEdsLogin = true;

    public LoginResource() {
    }

    public LoginResource(String source, String SA, boolean checkEdsLogin) {
        this.source = source;
        this.SA = SA;
        this.checkEdsLogin = checkEdsLogin;
    }

    // PUBLIC
    /**
     * Login to LDAP
     * 
     * @param user Name and Password of User
     * @param request Http servlet request
     * @return token after successfully login to LDAP
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(UserInfo user, @Context HttpServletRequest request) {
        String token = "";
        Date startDate = new Date();

        EdsUser edsUser = null;
        if (checkEdsLogin) {
            edsUser = EDSdao.getInstance().getUserByPsaID(user.getLogin());

            if (edsUser == null) {
                throw new RuntimeException("Error - login or password not reconized !");
            }
        }

        if (checkLDAPAuthentication(user.getLogin(), user.getPassword())) {
            EdsWsSessionToken wsToken = new EdsWsSessionToken(UUID.randomUUID().toString(), user.getLogin(), new Date(
                    System.currentTimeMillis() + 3600000), request.getRemoteAddr(), this.source); // Token with a lifetime of 1h, because some EDS
                                                                                                  // requests may take 30+ minutes
            wsToken.setEdsUser(edsUser); // When the WS is used with a required user, save it

            token = wsToken.getWstId();

            EDSdao.getInstance().saveDetachedDBObject(wsToken);
            ATIMonitoring.log(request.getHeader("hostname"), user.getLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                    "EDS_WS_" + this.source);
        } else {
            throw new RuntimeException("Error - account is not on the LDAP !");
        }

        return token;
    }

    /**
     * Performs authentication with the LDAP PSA
     * 
     * @param user The user to authenticate
     * @param password The password entered by the user
     * @return If the LDAP authentication worked
     */
    private boolean checkLDAPAuthentication(String login, String password) {
        if (login == null || password == null) {
            throw new NullPointerException();
        }

        try {

            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUid(login);

            LdapAuthStatus status = ldapUser.authenticate(password);
            if (status.isError()) {
                return false;
            } else {
                if (this.SA != null) {
                    LDAPGroup grp = new LDAPGroup();
                    grp.setName(this.SA);

                    return ldapUser.isMember(grp);
                } else {
                    return true;
                }
            }
        } catch (LDAPObjNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (LDAPException ex) {
            throw new RuntimeException(ex);
        }

    }
}
