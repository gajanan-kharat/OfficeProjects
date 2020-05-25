/*
 * Creation : 30 juil. 2015
 */
package com.inetpsa.eds.dao;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.cfg.Environment;

import com.inetpsa.bsxdesigner.BSXdesignerController;
import com.inetpsa.bsxmanager.BSXBoxController;
import com.inetpsa.bsxmanager.BSXmanagerController;
import com.inetpsa.dbelec.DBelecControllerDAO;
import com.inetpsa.gestion.network.conn.ServerConnection;
import com.inetpsa.gestion.sync.dao.GestionDAO;
import com.inetpsa.gestion.sync.exceptions.E_AlreadyExists;
import com.inetpsa.gestion.sync.exceptions.E_Auth;
import com.inetpsa.gestion.sync.exceptions.E_Forbidden;
import com.inetpsa.gestion.sync.exceptions.E_NoElement;
import com.inetpsa.gestion.sync.exceptions.E_NoRight;
import com.inetpsa.gestion.sync.transaction.TransactionRead;
import com.inetpsa.outils.configuration.ConfigurationManager;
import com.inetpsa.outils.exceptions.E_Configuration;
import com.inetpsa.outils.properties.definition.PropertiesDefinitionManager;
import com.inetpsa.outils.properties.styles.StylesManager;

/**
 * Utils class used to get containers from Gestion database.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public class GestionConnector {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GestionConnector.class);

    // PRIVATE
    /** Resources path of configuration-definition.xml file for bdd gestion connection */
    private static final String CONFIGURATION_DEFINITION = "/OPHGserver/configuration-definition.xml";

    /** Resources path of configuration-definition.xml file for bdd gestion connection */
    private static final String CONFIGURATION_CONTENT = "/OPHGserver/configuration-content.xml";

    /** Resources path of properties-definition.xml used to load BSX containers */
    private static final String PROPERTIES_DEFINITION = "/OPHGserver/properties-definition.xml";

    /** Resources path of properties-styles.xml used to load BSX containers */
    private static final String PROPERTIES_STYLE = "/OPHGserver/properties-styles.xml";

    /** Gestion DAO shared. */
    private static GestionDAO dao = null;

    /** Gestion DAO lock */
    private static Lock daoLock = new ReentrantLock();

    /** Gestion connector logger */
    private final static Logger logger = Logger.getLogger(GestionConnector.class.getName());

    /**
     * Private empty constructor
     */
    private GestionConnector() {
    }

    /**
     * Get an URL for a local resource.
     * 
     * @param resource The resource.
     * @return The URL to the resource, or null if no resource could be found.
     */
    public static URL getResoucePath(String resource) {
        String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
        File f = new File(stripped);

        if (f.exists())
            try {
                return f.toURI().toURL();
            } catch (MalformedURLException e) {
                // Ignore on error
            }

        URL url = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(stripped);
        }
        if (url == null) {
            url = Environment.class.getResource(resource);
        }
        if (url == null) {
            url = Environment.class.getClassLoader().getResource(stripped);
        }

        return url;
    }

    /**
     * Initialize the Gestion DAO connector. This DAO is common to all the web server, and shared among all the users.
     * 
     * @throws E_Configuration
     * @throws IOException
     * @throws E_Auth
     * @throws E_AlreadyExists
     * @throws GestionConnectorException
     */
    private static void initGestionConnector() throws E_Configuration, IOException, E_Auth, E_AlreadyExists, GestionConnectorException {

        URL configDefinitionUrl = getResoucePath(CONFIGURATION_DEFINITION);
        URL configContentUrl = getResoucePath(CONFIGURATION_CONTENT);
        URL propsDefinitionUrl = getResoucePath(PROPERTIES_DEFINITION);
        URL propsStyleUrl = getResoucePath(PROPERTIES_STYLE);

        // Load configuration & update values of the configuration
        try {
            ConfigurationManager.load(new File(configDefinitionUrl.toURI()), new File(configContentUrl.toURI()));
        } catch (Exception e) {
            logger.log(Level.WARNING, "[SERVER] initGestionConnector() : {0}", e.getMessage());
            throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-config", e);
        }

        // Load properties
        try {
            PropertiesDefinitionManager.load(new File(propsDefinitionUrl.toURI()));
            StylesManager.load(new File(propsStyleUrl.toURI()));
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "[SERVER] initGestionConnector() : {0}", e.getMessage());
            throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-config", e);
        }

        // Create the Gestion Server DAO & connect
        dao = new GestionDAO();
        connectGestionConnector();
    }

    /**
     * Try to reconnect the existing Gestion DAO connector.
     * 
     * @throws E_Auth
     * @throws E_AlreadyExists
     */
    private static void connectGestionConnector() throws E_Auth, E_AlreadyExists {
        String login = ConfigurationManager.get().getString("gestion-login");
        String password = ConfigurationManager.get().getString("gestion-password");
        dao.authenticate(login, password);
    }

    /**
     * Get a transaction read from the server. This method should only be called in a thread safe context.
     * 
     * @param id The ID of the transaction read to get.
     * @return The loaded transaction read.
     * @throws GestionConnectorException Thrown on error.
     */
    private static TransactionRead loadTransactionRead(String id) throws GestionConnectorException {

        try {
            if (id == null || id.isEmpty()) // No ID set
                throw new GestionConnectorException("No database selected", "gestion-conn-error-no-selected", null);

            // Get the transaction reader from the container's ID
            // No date is provided, so the loaded value will be in read only
            TransactionRead tx = dao.readContainer(id, null);

            // Select the last snapshot
            tx.selectLastSnapshot();

            return tx;
        } catch (E_Auth | E_NoRight | E_Forbidden e) {
            throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-right", e);
        } catch (E_NoElement e) {
            throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-element", e);
        } catch (GestionConnectorException e) {
            throw e;
        } catch (Exception e) {
            throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-generic", e);
        }
    }

    // PUBLIC
    /**
     * Get the selected container ID, or null if not set.
     * 
     * @return The selected container ID.
     */
    public static String getDbElecSelectedID() {
        return ConfigurationManager.get().getString("gestion-container-id");
    }

    /**
     * Return the BSXmanagerController from Gestion server. Each call is synchronous and technically synchronized with. Each DAO returned is new and
     * thread-safe to use.
     * 
     * @return A new BSXmanagerController ready to use.
     * @throws GestionConnectorException Thrown on error.
     */
    public static BSXdesignerController loadBSXDatabase(String id) throws GestionConnectorException {

        try {
            daoLock.lock();

            try {
                // Initialize the DAO if null
                getDao();

                BSXdesignerController controller = new BSXdesignerController(dao, "load", "partnumber");
                controller.load(loadTransactionRead(id));

                return controller;
            } catch (GestionConnectorException e) {
                throw e;
            } catch (E_NoRight e) {
                throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-right", e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-generic", e);
            } finally {
                daoLock.unlock();
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "[SERVER] loadGSDatabase() : {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Return the DBelecControllerDAO from Gestion server. Each call is synchronous and technically synchronized with. Each DAO returned is new and
     * thread-safe to use.
     * 
     * @return A new DBelecControllerDAO ready to use.
     * @throws GestionConnectorException
     */
    public static DBelecControllerDAO loadDbElecDatabase() throws GestionConnectorException {

        try {
            daoLock.lock();

            try {
                // Initialize the DAO if null
                getDao();

                // Load the database from the controller
                DBelecControllerDAO elecDado = new DBelecControllerDAO();
                elecDado.load(loadTransactionRead(ConfigurationManager.get().getString("gestion-container-id")));

                return elecDado;
            } catch (GestionConnectorException e) {
                throw e;
            } catch (Exception e) {
                throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-generic", e);
            } finally {
                daoLock.unlock();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "[SERVER] loadGestionDatabase() : {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Return the Gestion server DAO used to connect to the database. Will throw an exception if any error occurred.
     * 
     * @return
     * @throws GestionConnectorException
     */
    public static GestionDAO getDao() throws GestionConnectorException {
        try {
            if (dao == null) {
                try {
                    initGestionConnector();
                } catch (E_Configuration | IOException | E_Auth | E_AlreadyExists e) {
                    throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-connect", e);
                }
            } else {
                if (dao.getNetworkDAO().getConnection().getStatus() != ServerConnection.STATUS_CONNECTED) {
                    // Not connected, try to reconnect
                    try {
                        connectGestionConnector();
                    } catch (E_AlreadyExists e) {
                        throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-reconnect", e);
                    } catch (E_Auth e) {
                        throw new GestionConnectorException(e.getMessage(), "gestion-conn-error-no-right", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "[SERVER] getDao() : {0}", e.getMessage());
            throw e;
        }

        return dao;
    }

    // GL code addition start for CUG-0225
    /**
     * @param containerID
     * @return
     * @throws GestionConnectorException
     */
    public static BSXmanagerController loadGestionContainer(String containerID) throws GestionConnectorException {

        try {
            daoLock.lock();
            // Initialize the DAO if null
            getDao();
            BSXmanagerController bsXmanagerController = new BSXmanagerController();
            bsXmanagerController.load(loadTransactionRead(containerID));

            return bsXmanagerController;

        } catch (GestionConnectorException ex) {
            log.error("Exception occured while adding BSX node", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occured while adding BSX node", ex);
            throw new GestionConnectorException(ex.getMessage(), "gestion-conn-error-generic", ex);
        } finally {
            daoLock.unlock();
        }

    }

    /**
     * @param containerID
     * @return
     * @throws GestionConnectorException
     */
    public static BSXBoxController loadBSXBoxDistribContainer(String containerID) throws GestionConnectorException {

        try {
            daoLock.lock();
            // Initialize the DAO if null
            getDao();
            BSXBoxController bsxBoxController = new BSXBoxController(dao);
            TransactionRead transactionRead = loadTransactionRead(containerID);
            bsxBoxController.load(transactionRead, transactionRead.getContainer().getMaxOrder());

            return bsxBoxController;

        } catch (GestionConnectorException ex) {
            log.error("Exception occured while loading disrtib container", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occured while loading disrtib container", ex);
            throw new GestionConnectorException(ex.getMessage(), "gestion-conn-error-generic", ex);
        } finally {
            daoLock.unlock();
        }
    }
    // GL code addition ends for CUG-0225
}
