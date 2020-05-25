package com.inetpsa.eds.ws.eds;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.digest.DigestUtils;

import com.inetpsa.clp.LDAPGroup;
import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.core.LdapAuthStatus;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.clp.exception.LDAPObjNotFoundException;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.gestion.network.ws.NLicence;
import com.inetpsa.gestion.network.ws.NSUser;
import com.inetpsa.gestion.sync.exceptions.E_Auth;

/**
 * This class authenticate user and then login to LDAP
 */
public class EdsWsLogin {

    /**
     * Check if the provided request headers authenticate a valid user.
     * 
     * @param httpHeaders
     * @return
     * @throws EdsWsException
     */
    public static EdsUser checkCredentials(HttpHeaders httpHeaders) throws EdsWsException {

        // Informations needed for the ATI monitoring
        if (!httpHeaders.getRequestHeaders().containsKey("hostname") || !httpHeaders.getRequestHeaders().containsKey("appid")) {
            throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No hostname or app ID provided");
        }

        List<String> idValues = httpHeaders.getRequestHeader("id");
        List<String> timestampValues = httpHeaders.getRequestHeader("timestamp");
        List<String> keyValues = httpHeaders.getRequestHeader("key");

        String id = null;

        if (idValues != null && !idValues.isEmpty())
            id = idValues.get(0);

        String key = null;

        if (keyValues != null && !keyValues.isEmpty())
            key = keyValues.get(0);

        String timestamp = null;

        if (timestampValues != null && !timestampValues.isEmpty())
            timestamp = timestampValues.get(0);

        if (id == null || key == null || timestamp == null) {
            throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No credentials provided");
        }

        // Get licence & user for the provided ID
        NLicence licence = null;
        NSUser user = null;
        try {
            List<NLicence> licences = GestionConnector.getDao().getNetworkDAO().getLicencesForUsers(Collections.singletonList(id));

            if (licences == null || licences.isEmpty())
                throw new E_Auth();

            licence = licences.get(0);
            user = licence.getUser();
        } catch (E_Auth e) {
            throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "No user found for the specified ID");
        } catch (GestionConnectorException e) {
            throw new EdsWsException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Gestion connection error : " + e.getLocalizedMessage());
        }

        try {
            // Test the provided key
            if (!DigestUtils.sha512Hex((user.getID() + ":" + user.getLogin() + ":" + user.getHashpass() + ":" + timestamp).getBytes("UTF-8")).equals(
                    key)) {
                throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "Authentication error");
            }
        } catch (Exception e) {
            if (e instanceof EdsWsException)
                throw (EdsWsException) e;

            // Mostly errors from invalid values
            throw new EdsWsException(HttpServletResponse.SC_FORBIDDEN, "Key error");
        }

        // Get EDS user using Gestion login
        return EDSdao.getInstance().getUserByPsaID(user.getLogin());
    }

    /**
     * Return the hostname indicated in the request.
     * 
     * @param httpHeaders The headers of the request.
     * @return The hostname.
     */
    public static String getHostname(HttpHeaders httpHeaders) {
        List<String> hostnameValues = httpHeaders.getRequestHeader("hostname");

        String hostname = null;

        if (hostnameValues != null && !hostnameValues.isEmpty())
            hostname = hostnameValues.get(0);

        return hostname != null ? hostname : "";
    }

    /**
     * Return the app ID indicated in the request.
     * 
     * @param httpHeaders The headers of the request.
     * @return The app ID.
     */
    public static String getAppID(HttpHeaders httpHeaders) {
        List<String> appIDValues = httpHeaders.getRequestHeader("appid");

        String appID = null;

        if (appIDValues != null && !appIDValues.isEmpty())
            appID = appIDValues.get(0);

        return appID != null ? appID : "";
    }

    /**
     * Performs authentication with the LDAP PSA
     * 
     * @param user The user to authenticate
     * @param password The password entered by the user
     * @return If the LDAP authentication worked
     */
    public static boolean checkLDAPAuthentication(String login, String password) {
        if (login == null || password == null) {
            throw new NullPointerException();
        }

        try {

            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUid(login);

            LdapAuthStatus status = ldapUser.authenticate(password);
            if (status.isError()) {
                return false;
            }
            LDAPGroup grp = new LDAPGroup();
            grp.setName("OPD.UTILISATEURS");

            return ldapUser.isMember(grp);
        } catch (LDAPObjNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (LDAPException ex) {
            throw new RuntimeException(ex);
        }

    }
}
