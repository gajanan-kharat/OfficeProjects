/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.DetachedCriteria;

import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.EdsAttachmentFormData;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsChs;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsComportementRobusteFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidatedSupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsCseFormData;
import com.inetpsa.eds.dao.model.EdsDefaillanceVeilleReveilFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsMissionActivationFormData;
import com.inetpsa.eds.dao.model.EdsMissionProfilFormData;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsPinTypeComment;
import com.inetpsa.eds.dao.model.EdsPreliminarySupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsProjectNode;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.dao.model.EdsSAPReference;
import com.inetpsa.eds.dao.model.EdsSubscription;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;

/**
 * Classe permettant l'accès aux données de l'application depuis la base de données via l'API Hibernate.
 * 
 * @author VAUSHELL - Fabien VAUCHELLES <fabien@vaushell.com>
 */
public class EDSdao {
    // PUBLIC
    public static final String MAIL_CONTENT_HTML = "text/html; charset=utf-8";
    public static final String MAIL_CONTENT_PLAIN_TEXT = "text/plain; charset=utf-8";
    public static final String CONF_ACCESS_LOCK_TIMEOUT = "access_lock_timeout";
    public static final String CONF_SERVER_IMAGE_PATH = "server_image_path";
    public static final String CONF_SERVER_FILE_PATH = "server_file_path";
    public static final String CONF_SERVER_BIND_ADRESS = "server_bind_adress";
    public static final String CONF_B2B_SERVER_BIND_ADRESS = "b2b_server_bind_adress";
    public static final String CONF_APP_IS_RUN = "app_is_run";

    public synchronized static EDSdao getInstance() {
        if (singleton == null) {
            singleton = new EDSdao();
        }

        return singleton;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestion de session">
    // public void openDBSession() { }

    public Session getCurrentSession() {
        return HibernateUtil.getSession();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Abonnements et mails">
    public void sendMail(InternetAddress[] to, String subject, String content, String contentMimeType) {

        Message message = new MimeMessage(mailSession);

        try {

            message.setRecipients(Message.RecipientType.BCC, to);
            message.setSubject(subject);
            logger.log(Level.INFO, "[SERVER] Objet du mail : {0}", subject);

            message.setContent(content, contentMimeType);

            Transport.send(message);
        } catch (AddressException e) {
            logger.log(Level.WARNING, "[SERVER] sendMail() : {0}", e.getMessage());
        } catch (MessagingException e) {
            logger.log(Level.WARNING, "[SERVER] sendMail() : {0}", e.getMessage());
        }
    }

    public void sendCDMail(InternetAddress[] to, String subject, String content, String contentMimeType, ArrayList<File> files) {

        Message message = new MimeMessage(mailSession);
        try {
            message.setRecipients(Message.RecipientType.BCC, to);

            message.setSubject(subject);

            // Partie 1: Le texte
            MimeBodyPart mbp1 = new MimeBodyPart();
            // mbp1.setText(content);
            mbp1.setContent(content, contentMimeType);

            logger.log(Level.INFO, "[SERVER] Contenu mail advice : {0}", content);

            // Partie 2: Le fichier joint
            MimeBodyPart mbp2 = new MimeBodyPart();
            for (File f : files) {
                if (f.exists()) {
                    mbp2.attachFile(f);
                }
            }

            // On regroupe les deux dans le message
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            message.setContent(mp, contentMimeType);

            Transport.send(message);
        } catch (AddressException e) {
            logger.log(Level.WARNING, "[SERVER] sendMail() : {0}", e.getMessage());
        } catch (MessagingException e) {
            logger.log(Level.WARNING, "[SERVER] sendMail() : {0}", e.getMessage());
        } catch (IOException e) {
            logger.log(Level.WARNING, "[SERVER] sendMail() : {0}", e.getMessage());
        }

    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Web service">
    public EdsWsSessionToken getEdsTokenByID(String key) {
        logger.log(Level.INFO, "[SERVER] getEdsTokenByID() : key={0}", new Object[] { key, });

        if (key == null) {
            throw new NullPointerException();
        }

        return executeGet(key, EdsWsSessionToken.class);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fiches">
    public EdsEds getEdsByRef(String ref) {
        logger.log(Level.INFO, String.format("[SERVER] getEDSByRef() : ref=%s", ref));

        if (ref == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsRef = ?" + " and e.edsState = ?");
        q.setParameter(0, ref);
        q.setParameter(1, EdsEds.DEFAULT_STATE);

        return executeSingletonQuery(q, EdsEds.class);
    }

    public EdsEds getEdsByRef(String ref, int majorVersion, int minorVersion) {
        logger.log(Level.INFO, String.format("[SERVER] getEDSByRef() : ref=%s majorVersion=%d minorVersion=%d", ref, majorVersion, minorVersion));

        if (ref == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession()
                .createQuery("from EdsEds e" + " where e.edsRef = ?" + " and e.edsMajorVersion = ?" + " and e.edsMinorVersion = ?");
        q.setParameter(0, ref);
        q.setParameter(1, majorVersion);
        q.setParameter(2, minorVersion);

        return executeSingletonQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllEdsVersionsByRef(String ref) {
        logger.log(Level.INFO, String.format("[SERVER] getAllEdsVersionsByRef() : ref=%s", ref));

        if (ref == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsRef = ?");
        q.setParameter(0, ref);

        return executeListQuery(q, EdsEds.class);
    }

    /**
     * Return, for a given EDS reference, the list of versions. Each version entry contains the major version, the minor version, and the state of the
     * version (all three integers).
     * 
     * @param ref The EDS reference.
     * @return The list of versions.
     */
    public List<Object[]> getAllEdsVersionsValuesByRef(String ref) {
        logger.log(Level.INFO, String.format("[SERVER] getAllEdsVersionsValuesByRef() : ref=%s", ref));

        if (ref == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery(
                "select e.edsMajorVersion, e.edsMinorVersion, e.edsState, highVal.hvfdStage from EdsEds e, EdsHighValidationFormData as highVal where e.edsRef = ? and highVal.edsEds.edsId = e.edsId");
        q.setParameter(0, ref);

        List<Object[]> values = executeListQuery(q, Object[].class);

        q = HibernateUtil.getSession().createQuery(
                "select e.edsMajorVersion, e.edsMinorVersion, e.edsState, lowVal.lvfdStage from EdsEds e, EdsLowValidationFormData as lowVal where e.edsRef = ? and lowVal.edsEds.edsId = e.edsId");
        q.setParameter(0, ref);

        values.addAll(executeListQuery(q, Object[].class));

        return values;
    }

    public EdsEds getEdsByID(String key) {
        logger.log(Level.INFO, String.format("[SERVER] getEDSByID() : key=%s", key));

        if (key == null) {
            throw new NullPointerException();
        }

        return executeGet(key, EdsEds.class);
    }

    public int getEDSCount() {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );
            int result = 0;

            try {
                HibernateUtil.beginTransaction();

                result = ((Integer) HibernateUtil.getSession().createQuery("select count(*) from EdsEds e").iterate().next()).intValue();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }

            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<EdsEds> getAllEDS() {
        logger.log(Level.INFO, "[SERVER] getAllEDS()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e");

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllEDSByProject(EdsProject project) {
        logger.log(Level.INFO, "[SERVER] getAllEDSByProject(): project={0}", new Object[] { project.getPId() });

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("select e" + " from EdsEds e" + " left join e.edsProjectEdses ee"
                + " left join ee.edsProject p" + " where (e.edsProject.PId = ?" + " or p.PId = ?)" + " and e.edsState = ?");
        q.setParameter(0, project.getPId());
        q.setParameter(1, project.getPId());
        q.setParameter(2, EdsEds.DEFAULT_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllUniqueEDSByProject(EdsProject project) {
        logger.log(Level.INFO, "[SERVER] getAllUniqueEDSByProject(): project={0}", new Object[] { project.getPId() });

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("select e from EdsEds e where e.edsProject.PId = ? and e.edsState = ?");
        q.setParameter(0, project.getPId());
        q.setParameter(1, EdsEds.DEFAULT_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllNormalEDS() {
        logger.log(Level.INFO, "[SERVER] getAllNormalEDS()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsState = ?");
        q.setParameter(0, EdsEds.DEFAULT_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllAbortedEDS() {
        logger.log(Level.INFO, "[SERVER] getAllAbortedEDS()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsState = ?");
        q.setParameter(0, EdsEds.ABORTED_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllEDSModifiedBetween(Date startDate, Date endDate) {
        logger.log(Level.INFO, String.format("[SERVER] getAllEDSModifiedBetween() : startDate=%s endDate=%s", startDate, endDate));

        checkSession();

        Query q;

        if (startDate == null) {
            q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsModifDate < ?");
            q.setParameter(0, endDate);
        } else if (endDate == null) {
            q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsModifDate >= ?");
            q.setParameter(0, startDate);
        } else {
            q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsModifDate between ? and ?");
            q.setParameter(0, startDate);
            q.setParameter(1, endDate);
        }

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllEDSUnmodifiedBetween(Date startDate, Date endDate) {
        logger.log(Level.INFO, "[SERVER] getAllEDSUnmodifiedBetween() : startDate=%s endDate=%s", new Object[] { startDate, endDate });

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where e.edsModifDate not between ? and ?");
        q.setParameter(0, startDate);
        q.setParameter(1, endDate);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllOwnerEDS(String userID) {
        logger.log(Level.INFO, String.format("[SERVER] getAllOwnerEDS() : userId=%s", userID));

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsEds e" + " where (e.edsUserByEdsManagerId.UId = ?"
                + " or e.edsUserByEdsOfficerId.UId = ?" + " or e.edsUserByEdsAdminId.UId = ?)" + " and e.edsState = ?");
        q.setParameter(0, userID);
        q.setParameter(1, userID);
        q.setParameter(2, userID);
        q.setParameter(3, EdsEds.DEFAULT_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public List<EdsEds> getAllSubscribedEDS(String userID) {
        logger.log(Level.INFO, String.format("[SERVER] getAllSubscribedEDS() : user.id=%s", userID));

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("select e from EdsEds e, EdsUser u" + " left join u.edsSubscriptions sub"
                + " where sub.edsUser.UId = ?" + " and sub.subEdsRef = e.edsRef" + " and e.edsState = ?");
        q.setParameter(0, userID);
        q.setParameter(1, EdsEds.DEFAULT_STATE);

        return executeListQuery(q, EdsEds.class);
    }

    public EdsSubscription getUserSubscriptionToEDS(String userID, String edsRef) {
        logger.log(Level.INFO, String.format("[SERVER] hasSubscribedToEDS() : user.id=%s & edsRef=%s", userID, edsRef));

        checkSession();

        Query q = HibernateUtil.getSession()
                .createQuery("select sub from EdsSubscription sub" + " where sub.edsUser.UId = ?" + " and sub.subEdsRef = ?");
        q.setParameter(0, userID);
        q.setParameter(1, edsRef);

        return executeSingletonQuery(q, EdsSubscription.class);
    }

    public EdsEds copyEdsForMinorVersion(EdsEds eds, EdsUser versionUser) {
        logger.log(Level.INFO,
                String.format("[SERVER] copyEdsForMinorVersion : eds.id=%s eds.modifUser=%s", eds.getEdsId(), versionUser.toPSAIdentity()));

        eds.setEdsState(EdsEds.FROZEN_STATE);
        eds.setEdsModifDate(new Date());
        eds.setEdsUserByEdsModifUserId(versionUser);

        mergeDetachedDBObject(eds);

        EdsEds edsDuplicata = versionMinorEdsInDB(eds);

        HashSet<EdsProject> projects = new HashSet<EdsProject>();
        projects.add(edsDuplicata.getEdsProject());

        for (EdsProjectEds edsProjectEds : edsDuplicata.getEdsProjectEdses()) {
            projects.add(edsProjectEds.getEdsProject());
        }

        List itemsToSave = new ArrayList();
        HashMap<String, Object> copiesMap = new HashMap<String, Object>();

        for (I_FormData formData : getAllFormsData(eds.getEdsId())) {
            if (formData instanceof I_ProjectSpecificData) {
                I_ProjectSpecificData projectSpecificData = (I_ProjectSpecificData) formData;
                itemsToSave.addAll(projectSpecificData.getCopy(edsDuplicata, copiesMap).getAllItemsToSave());
            } else {
                itemsToSave.addAll(formData.getCopy(edsDuplicata, copiesMap).getAllItemsToSave());
            }
        }
        itemsToSave.addAll(getChsBsxData(eds, edsDuplicata));

        for (Object object : itemsToSave) {
            saveDetachedDBObject(object);
        }

        return edsDuplicata;
    }

    private List<Object> getChsBsxData(EdsEds eds, EdsEds edsDuplicata) {
        List<Object> itemsToSave = new ArrayList<Object>();
        Set<EdsPinTypeComment> typeComment = eds.getEdsTypeComment();
        Set<EdsPinTypeComment> typeCommentDuplicate = new HashSet<EdsPinTypeComment>();
        EdsPinTypeComment typeItemClone;

        EdsChs connect;
        Set<Chs> chsClone = new HashSet<Chs>();
        for (Chs chs : eds.getEdsChs()) {
            connect = new EdsChs();
            connect.setEdsId(edsDuplicata.getEdsId());
            connect.setComponentId(chs.getComponentId());
            itemsToSave.add(connect);
            chsClone.add(chs);
        }
        edsDuplicata.setEdsChs(chsClone);

        for (EdsPinTypeComment item : typeComment) {
            typeItemClone = item.duplicate(edsDuplicata);
            itemsToSave.add(typeItemClone);
            typeCommentDuplicate.add(typeItemClone);
        }

        edsDuplicata.setEdsTypeComment(typeCommentDuplicate);

        Set<EdsPinConnect> pin = eds.getEdsPinConnect();
        Set<EdsPinConnect> pinClone = new HashSet<EdsPinConnect>();
        EdsPinConnect pinItemClone;
        for (EdsPinConnect item : pin) {
            if (pin != null) {
                pinItemClone = item.duplicate(edsDuplicata);
                itemsToSave.add(pinItemClone);
                pinClone.add(pinItemClone);
            }
        }
        edsDuplicata.setEdsPinConnect(pinClone);

        EdsBsx bsx = eds.getEdsBsxUnique();
        if (bsx != null) {
            EdsBsx bsxClone = bsx.duplicate(edsDuplicata);
            itemsToSave.add(bsxClone);

            for (EdsBsxPin bsxPin : bsxClone.getPin()) {
                itemsToSave.add(bsxPin);
            }
            edsDuplicata.setEdsBsxUnique(bsxClone);
        }
        return itemsToSave;
    }

    public EdsEds copyEdsForMajorVersion(EdsEds eds, EdsUser versionUser) {
        logger.log(Level.INFO,
                String.format("[SERVER] copyEdsForMajorVersion : eds.id=%s eds.modifUser=%s", eds.getEdsId(), versionUser.toPSAIdentity()));

        eds.setEdsState(EdsEds.FROZEN_STATE);
        eds.setEdsModifDate(new Date());
        eds.setEdsUserByEdsModifUserId(versionUser);

        mergeDetachedDBObject(eds);

        EdsEds edsDuplicata = versionMajorEdsInDB(eds);

        HashSet<EdsProject> projects = new HashSet<EdsProject>();
        projects.add(edsDuplicata.getEdsProject());

        for (EdsProjectEds edsProjectEds : edsDuplicata.getEdsProjectEdses()) {
            projects.add(edsProjectEds.getEdsProject());
        }

        List itemsToSave = new ArrayList();
        HashMap<String, Object> copiesMap = new HashMap<String, Object>();

        for (I_FormData formData : getAllFormsData(eds.getEdsId())) {
            if (formData instanceof I_ProjectSpecificData) {
                I_ProjectSpecificData projectSpecificData = (I_ProjectSpecificData) formData;
                itemsToSave.addAll(projectSpecificData.getCopy(edsDuplicata, copiesMap).getAllItemsToSave());
            } else {
                itemsToSave.addAll(formData.getCopy(edsDuplicata, copiesMap).getAllItemsToSave());
            }
        }

        itemsToSave.addAll(getChsBsxData(eds, edsDuplicata));

        for (Object object : itemsToSave) {
            saveDetachedDBObject(object);
        }

        return edsDuplicata;
    }

    public boolean isEdsIn(EdsEds eds, EdsPerimeter perimeter) {
        logger.log(Level.INFO, "[SERVER] inEdsIn() : eds.name={0} perimeter.name={1}", new Object[] { eds.getEdsName(), perimeter.getPeName() });

        if (eds == null || perimeter == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query perimeterQuery = HibernateUtil.getSession()
                .createQuery("select count(*) from EdsEds e" + " inner join e.edsPerimeters peri" + " where e.id = ?" + " and peri.id = ?");
        perimeterQuery.setParameter(0, eds.getEdsId());
        perimeterQuery.setParameter(1, perimeter.getPeId());

        int count = executeCountQuery(perimeterQuery);
        if (count > 0) {
            return true;
        }

        Query projectPerimeterQuery = HibernateUtil.getSession().createQuery("select count(*) from EdsEds e" + " inner join e.edsProject p"
                + " inner join p.edsPerimeters peri" + " where e.id = ?" + " and peri.id = ?");
        projectPerimeterQuery.setParameter(0, eds.getEdsId());
        projectPerimeterQuery.setParameter(1, perimeter.getPeId());

        count = executeCountQuery(projectPerimeterQuery);
        if (count > 0) {
            return true;
        }

        Query followerProjectsPerimeterQuery = HibernateUtil.getSession()
                .createQuery("select count(*) from EdsEds e" + " inner join e.edsProjectEdses epe" + " inner join epe.edsProject p"
                        + " inner join p.edsPerimeters peri" + " where e.id = ?" + " and peri.id = ?");
        followerProjectsPerimeterQuery.setParameter(0, eds.getEdsId());
        followerProjectsPerimeterQuery.setParameter(1, perimeter.getPeId());

        count = executeCountQuery(followerProjectsPerimeterQuery);
        if (count > 0) {
            return true;
        }

        return false;
    }

    public void createEDS(EdsEds eds) {
        logger.log(Level.INFO, "[SERVER] createEDS() : eds.ref={0}", new Object[] { eds.getEdsRef(), });

        if (eds == null) {
            throw new NullPointerException();
        }

        eds.setEdsCreaDate(new Date());
        eds.setEdsModifDate(new Date());

        executeCreate(eds);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Projets">
    public EdsProject getProjectbyID(String key) {
        logger.log(Level.INFO, "[SERVER] getProjectByID() : key={0}", new Object[] { key, });

        if (key == null) {
            throw new NullPointerException();
        }

        return executeGet(key, EdsProject.class);
    }

    public EdsProject getProjectbyName(String name) {
        logger.log(Level.INFO, "[SERVER] getProjectByName() : name={0}", new Object[] { name, });

        if (name == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsProject p" + " where p.PName = ?");
        q.setParameter(0, name);

        return executeSingletonQuery(q, EdsProject.class);
    }

    public List<EdsProject> getAllProjectsInNode(EdsProjectNode node) {
        return getAllProjectsInNode(node, false);
    }

    public List<EdsProject> getAllProjectsInNode(EdsProjectNode node, boolean includeDeactivated) {
        logger.log(Level.INFO, "[SERVER] getAllProjectsInNode() : includeDeactivated={0}", new Object[] { includeDeactivated });

        checkSession();

        String queryString;

        if (node != null)
            queryString = "from EdsProject p where p.edsNodeId = ?";
        else
            queryString = "from EdsProject p where p.edsNodeId is null";

        if (!includeDeactivated)
            queryString += " and p.PIndex > " + EdsProject.INACTIVE;
        else
            queryString += " or p.PIndex = " + EdsProject.INACTIVE;

        queryString += " order by p.PIndex asc";

        System.out.println(queryString);

        Query q = HibernateUtil.getSession().createQuery(queryString);

        if (node != null)
            q.setParameter(0, node.getEdspnId());

        return executeListQuery(q, EdsProject.class);
    }

    public List<EdsProject> getAllProjects() {
        return getAllProjects(false);
    }

    public List<EdsProject> getAllProjects(boolean includeDeactivated) {
        logger.log(Level.INFO, "[SERVER] getAllProjects() : includeDeactivated={0}", new Object[] { includeDeactivated });

        checkSession();

        String queryString = "from EdsProject p";
        if (!includeDeactivated) {
            queryString += " where p.PIndex > " + EdsProject.INACTIVE;
        }
        queryString += " order by p.PIndex asc";
        Query q = HibernateUtil.getSession().createQuery(queryString);

        return executeListQuery(q, EdsProject.class);
    }

    public List<String> listProjectIdsIn(EdsPerimeter perimeter) {
        logger.log(Level.INFO, "[SERVER] listProjectIdsIn() : perimeter.name={0}", new Object[] { perimeter.getPeName() });

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("select p.id from EdsProject p" + " inner join p.edsPerimeters pe" + " where pe.id = ?");
        q.setParameter(0, perimeter.getPeId());

        return executeListQuery(q, String.class);
    }

    public void createProject(EdsProject project) {
        logger.log(Level.INFO, "[SERVER] createProject() : eds={0}", new Object[] { project, });

        if (project == null) {
            throw new NullPointerException();
        }

        executeCreate(project);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Modèles de fiche">
    public List<EdsComponentType> getAllComponentTypes() {
        return getAllComponentTypes(false);
    }

    public List<EdsComponentType> getAllComponentTypes(boolean includeDeactivated) {
        logger.log(Level.INFO, String.format("[SERVER] getAllComponentTypes() : includeDeactivated=%b", includeDeactivated));

        checkSession();

        Query q = null;
        if (includeDeactivated) {
            q = HibernateUtil.getSession().createQuery("from EdsComponentType ct" + " order by ct.ctIndex asc");
        } else {
            q = HibernateUtil.getSession().createQuery("from EdsComponentType ct" + " where ct.ctIndex > ?" + " order by ct.ctIndex asc");
            q.setParameter(0, EdsComponentType.INACTIVE);
        }

        return executeListQuery(q, EdsComponentType.class);
    }

    public void createComponentType(EdsComponentType componentType) {
        logger.log(Level.INFO, "[SERVER] createComponentType componentType={0}", new Object[] { componentType });

        executeCreate(componentType);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Alimentations">
    public List<EdsSupply> getAllEdsSuppliesByEdsId(String edsId) {
        logger.log(Level.INFO, String.format("[SERVER] getAllEdsSuppliesByEdsId() : edsId=%s", edsId));

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsSupply s" + " where s.edsEds.edsId = ?");
        q.setParameter(0, edsId);

        return executeListQuery(q, EdsSupply.class);
    }

    /**
     * @param preliminarySupply alimentation préliminaire en question
     * @param supply l'alimentation contenant preliminarySupply
     * @return true si l'alimentation préliminaire est utilisée ailleurs que dans supply
     */
    public boolean isPreliminarySupplyReferenced(EdsPrimarySupply preliminarySupply, EdsSupply supply) {
        logger.log(Level.INFO, String.format("[SERVER] isPreliminarySupplyReferenced() : preliminarySupply=%s, supply=%s",
                preliminarySupply.getPsedsSupplyName(), supply.getSedsSupplyName()));

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsSupply s" + " where s.edsPrimarySupply.psedsId = ?" + " and s.sedsId <> ?");
        q.setParameter(0, preliminarySupply.getPsedsId());
        q.setParameter(1, supply.getSedsId());

        return !executeListQuery(q, EdsSupply.class).isEmpty();
    }

    /**
     * @param robustSupply alimentation robuste en question
     * @param supply l'alimentation contenant robustSupply
     * @return true si l'alimentation robuste est utilisée ailleurs que dans supply
     */
    public boolean isRobustSupplyReferenced(EdsRobustSupply robustSupply, EdsSupply supply) {
        logger.log(Level.INFO, String.format("[SERVER] isRobustSupplyReferenced() : robustSupply=%s, supply=%s", robustSupply.getRsedsSupplyName(),
                supply.getSedsSupplyName()));

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsSupply s" + " where s.edsRobustSupply.rsedsId = ?" + " and s.sedsId <> ?");
        q.setParameter(0, robustSupply.getRsedsId());
        q.setParameter(1, supply.getSedsId());

        return !executeListQuery(q, EdsSupply.class).isEmpty();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Périmètres">
    public List<EdsPerimeter> getAllPerimeters() {
        return getAllPerimeters(false);
    }

    public List<EdsPerimeter> getAllPerimeters(boolean includeDeactivated) {
        logger.log(Level.INFO, String.format("[SERVER] getAllPerimeters() : includeDeactivated=%b", includeDeactivated));

        checkSession();

        Query q;
        if (includeDeactivated) {
            q = HibernateUtil.getSession().createQuery("from EdsPerimeter ct where ct.peActive > ?");
            q.setParameter(0, EdsPerimeter.HIDDEN);
        } else {
            q = HibernateUtil.getSession().createQuery("from EdsPerimeter ct where ct.peActive = ?");
            q.setParameter(0, EdsPerimeter.ACTIVE);
        }

        return executeListQuery(q, EdsPerimeter.class);
    }

    public void createPerimeter(EdsPerimeter perimeter) {
        logger.log(Level.INFO, String.format("[SERVER] createPerimeter() : perimeter=%s", perimeter.toString()));

        if (perimeter == null) {
            throw new NullPointerException();
        }

        executeCreate(perimeter);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fournisseurs">
    public List<EdsSupplier> getAllSuppliers() {
        return getAllSuppliers(false);
    }

    public List<EdsSupplier> getAllSuppliers(boolean includeDeactivated) {
        logger.log(Level.INFO, "[SERVER] getAllSuppliers() : includeDeactivated=%b", includeDeactivated);

        checkSession();

        Query q;
        if (includeDeactivated) {
            q = HibernateUtil.getSession().createQuery("from EdsSupplier s where s.SActive > ?");
            q.setParameter(0, EdsSupplier.HIDDEN);
        } else {
            q = HibernateUtil.getSession().createQuery("from EdsSupplier s where s.SActive = ?");
            q.setParameter(0, EdsSupplier.ACTIVE);
        }
        return executeListQuery(q, EdsSupplier.class);
    }

    public void createSupplier(EdsSupplier supplier) {
        logger.log(Level.INFO, String.format("[SERVER] createSupplier() : supplier=%s", supplier.toString()));

        if (supplier == null) {
            throw new NullPointerException();
        }

        executeCreate(supplier);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Utilisateurs">
    public EdsUser getUserByID(String id) {
        logger.log(Level.INFO, String.format("[SERVER] getUserByID() : id=%s", id));

        return executeGet(id, EdsUser.class);
    }

    public List<EdsUser> getAllUsers() {
        logger.log(Level.INFO, "[SERVER] getAllUsers()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsUser u where u.UActive = ? order by u.ULastname asc");
        q.setParameter(0, 1);

        return executeListQuery(q, EdsUser.class);
    }

    public List<EdsUser> getAllSubscribersToEds(String edsRef) {
        logger.log(Level.INFO, String.format("[SERVER] getAllSubscribersToEds() : edsRef=%s", edsRef));

        checkSession();

        Query q = HibernateUtil.getSession()
                .createQuery("select u from EdsUser u" + " left join u.edsSubscriptions sub" + " where u.UActive = ?" + " and sub.subEdsRef = ?");
        q.setParameter(0, EdsUser.USER_ACTIVE);
        q.setParameter(1, edsRef);

        return executeListQuery(q, EdsUser.class);
    }

    public void createUser(EdsUser user) {
        logger.log(Level.INFO, String.format("[SERVER] createUser() : user=%s", user.toFullIdentity()));

        if (user == null) {
            throw new NullPointerException();
        }

        EdsUser dbUser = executeGet(user.getUId(), EdsUser.class);

        if (dbUser != null) {
            updateDetachedDBObject(user);
        } else {
            user.setUPsaId(user.getUPsaId().toUpperCase());
            executeCreate(user);
        }
    }

    public void deleteUserById(String userId) {
        logger.log(Level.INFO, String.format("[SERVER] deleteUserById() : user.id=%s", userId));

        if (userId == null) {
            throw new NullPointerException();
        }

        EdsUser dbUser = executeGet(userId, EdsUser.class);

        if (dbUser != null) {
            dbUser.setUActive(EdsUser.USER_DELETED);
            updateDetachedDBObject(dbUser);
        } else {
            logger.log(Level.WARNING, String.format("[SERVER] User %s doesn't exist in DB.", userId));
        }
    }

    public EdsUser getUserByPsaID(String psaID) {
        logger.log(Level.INFO, String.format("[SERVER] getUserByPsaID : psaID=%s", psaID));

        if (psaID == null) {
            throw new NullPointerException();
        }

        checkSession();

        psaID = psaID.toUpperCase();

        Query q = HibernateUtil.getSession().createQuery("from EdsUser u where u.UPsaId='" + psaID + "'");

        return executeSingletonQuery(q, EdsUser.class);
    }

    public void shiftOwnership(EdsUser fromUser, EdsUser toUser) {
        if (fromUser == null || toUser == null) {
            throw new NullPointerException();
        }

        logger.log(Level.INFO,
                String.format("[SERVER] shiftOwnership : fromUser.psaId=%s toUser.psaId=%s", fromUser.getUPsaId(), toUser.getUPsaId()));

        checkSession();

        Query updateAdminQuery = HibernateUtil.getSession().createSQLQuery("update OPLQTEDS set EDS_ADMIN_ID = ? where EDS_ADMIN_ID = ?");
        updateAdminQuery.setParameter(0, toUser.getUId());
        updateAdminQuery.setParameter(1, fromUser.getUId());
        updateAdminQuery.executeUpdate();

        Query updateManagerQuery = HibernateUtil.getSession().createSQLQuery("update OPLQTEDS set EDS_MANAGER_ID = ? where EDS_MANAGER_ID = ?");
        updateManagerQuery.setParameter(0, toUser.getUId());
        updateManagerQuery.setParameter(1, fromUser.getUId());
        updateManagerQuery.executeUpdate();

        Query updateOfficerQuery = HibernateUtil.getSession().createSQLQuery("update OPLQTEDS set EDS_OFFICER_ID = ? where EDS_OFFICER_ID = ?");
        updateOfficerQuery.setParameter(0, toUser.getUId());
        updateOfficerQuery.setParameter(1, fromUser.getUId());
        updateOfficerQuery.executeUpdate();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Rôles">
    public List<EdsRole> getAllRoles() {
        return getAllRoles(false);
    }

    /**
     * Get all the project node
     * 
     * @return All the project nodes
     */
    public List<EdsProjectNode> getAllProjectNodes() {
        logger.log(Level.INFO, "[SERVER] getAllProjectNodes");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsProjectNode u");
        return executeListQuery(q, EdsProjectNode.class);
    }

    public List<EdsProjectNode> getProjectNodeChild(EdsProjectNode projectNode) {
        logger.log(Level.INFO, "[SERVER] getProjectNodeChild");

        checkSession();

        Query q;

        if (projectNode == null)
            q = HibernateUtil.getSession().createQuery("from EdsProjectNode u where u.edspnParentId = null");
        else {
            q = HibernateUtil.getSession().createQuery("from EdsProjectNode u where u.edspnParentId = ?");
            q.setParameter(0, projectNode.getEdspnId());
        }

        return executeListQuery(q, EdsProjectNode.class);
    }

    public List<EdsRole> getAllRoles(boolean includeDeactivated) {
        logger.log(Level.INFO, String.format("[SERVER] getAllRoles : includeDeactivated=%b", includeDeactivated));

        checkSession();

        Query q;
        if (includeDeactivated) {
            q = HibernateUtil.getSession().createQuery("from EdsRole u where u.roActive > ?");
            q.setParameter(0, EdsRole.HIDDEN);
        } else {
            q = HibernateUtil.getSession().createQuery("from EdsRole u where u.roActive = ?");
            q.setParameter(0, EdsRole.ACTIVE);
        }

        return executeListQuery(q, EdsRole.class);
    }

    public void createRole(EdsRole role) {
        logger.log(Level.INFO, String.format("[SERVER] createRole() : role=%s", role.toString()));

        if (role == null) {
            throw new NullPointerException();
        }

        executeCreate(role);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Libellés">
    public List<EdsWording> getAllActiveWordingsByType(String type) {
        logger.log(Level.INFO, "[SERVER] getAllActiveWordingsByType()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsWording w where w.WType = ? and w.WIndex > ? order by w.WIndex asc");
        q.setParameter(0, type);
        q.setParameter(1, EdsWording.INACTIVE);

        return executeListQuery(q, EdsWording.class);
    }

    public List<EdsWording> getAllInactiveWordingsByType(String type) {
        logger.log(Level.INFO, "[SERVER] getAllInactiveWordingsByType()");

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsWording w where w.WType = ? and w.WIndex = ?");
        q.setParameter(0, type);
        q.setParameter(1, EdsWording.INACTIVE);

        return executeListQuery(q, EdsWording.class);
    }

    public EdsWording getWordingByID(String ID) {
        logger.log(Level.INFO, String.format("[SERVER] getWordingByID : ID=%s", ID));

        if (ID == null) {
            throw new NullPointerException();
        }

        checkSession();

        Query q = HibernateUtil.getSession().createQuery("from EdsWording w where w.WId='" + ID + "'");

        return executeSingletonQuery(q, EdsWording.class);

    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Formulaires">
    public <T> T getFormData(String edsId, Class<T> formClass) {
        checkSession();

        logger.log(Level.INFO, "[SERVER] getFormData() : eds.id={0} formClass={1}", new Object[] { edsId, formClass.getName() });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                Query q = HibernateUtil.getSession().createQuery("select fd from " + formClass.getSimpleName() + " fd" + " where fd.edsEds.edsId=?");
                q.setParameter(0, edsId);
                T value = (T) q.uniqueResult();
                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return value;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Class<?>> getAllReconsultableFormDataClasses() {
        List<Class<?>> reconsultableDataClassesList = new ArrayList<Class<?>>();

        // Lister ici les modèles de données formulaires recopiés lors d'une reconsultation.
        reconsultableDataClassesList.add(EdsPrimaryCurent.class);
        reconsultableDataClassesList.add(EdsPreliminarySupplyVoltageFormData.class);
        reconsultableDataClassesList.add(EdsRobustCurentFormData.class);
        reconsultableDataClassesList.add(EdsMissionProfilFormData.class);
        reconsultableDataClassesList.add(EdsComportementRobusteFormData.class);
        // Conso de courant prelim, robuste et profil de mission.

        return reconsultableDataClassesList;
    }

    public static List<Class<? extends I_ProjectSpecificData>> getAllProjectSpecificFormDataClasses() {
        List<Class<? extends I_ProjectSpecificData>> projectSpecificDataClassesList = new ArrayList<Class<? extends I_ProjectSpecificData>>();

        // TODO : rajouter le formulaire mesure de données PSA.

        return projectSpecificDataClassesList;
    }

    public static List<Class<? extends I_FormData>> getAllFormDataClasses() {
        List<Class<? extends I_FormData>> dataClassesList = new ArrayList<Class<? extends I_FormData>>();

        dataClassesList.add(EdsPrimaryCurent.class);
        dataClassesList.add(EdsRobustCurentFormData.class);
        dataClassesList.add(EdsConsolidateCurentFormData.class);
        dataClassesList.add(EdsPreliminarySupplyVoltageFormData.class);
        dataClassesList.add(EdsConsolidatedSupplyVoltageFormData.class);
        dataClassesList.add(EdsDefaillanceVeilleReveilFormData.class);
        dataClassesList.add(EdsComportementConsolideFormData.class);
        dataClassesList.add(EdsComportementRobusteFormData.class);
        dataClassesList.add(EdsMissionProfilFormData.class);
        dataClassesList.add(EdsMissionActivationFormData.class);
        dataClassesList.add(EdsHighValidationFormData.class);
        dataClassesList.add(EdsLowValidationFormData.class);
        dataClassesList.add(EdsCseFormData.class);
        dataClassesList.add(EdsAttachmentFormData.class);

        return dataClassesList;
    }

    public String generateFormRef() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String generatedRef = "EDS-AAMMJJ-XX";
        try {
            File file = new File("resources/count");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            long time = 0;
            int count = -1;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            if ((line = reader.readLine()) != null) {
                time = Long.parseLong(line);
            }
            if ((line = reader.readLine()) != null) {
                count = Integer.parseInt(line);
            }
            reader.close();
            long now = System.currentTimeMillis();
            Date nowDate = new Date(now);
            if (!DF.format(new Date(time)).equals(DF.format(nowDate))) {
                count = 0;
            } else {
                count++;
            }
            generatedRef = "EDS-" + DF.format(nowDate) + "-" + count;

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(now + "\n");
            writer.write(count + "\n");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return generatedRef;
    }

    public String generateAlimRef() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String generatedRef = "AL-AAMMJJ-XX";
        try {
            File file = new File("resources/count-al");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            long time = 0;
            int count = -1;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            if ((line = reader.readLine()) != null) {
                time = Long.parseLong(line);
            }
            if ((line = reader.readLine()) != null) {
                count = Integer.parseInt(line);
            }
            reader.close();
            long now = System.currentTimeMillis();
            Date nowDate = new Date(now);
            if (!DF.format(new Date(time)).equals(DF.format(nowDate))) {
                count = 0;
            } else {
                count++;
            }
            generatedRef = "AL-" + DF.format(nowDate) + "-" + count;

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(now + "\n");
            writer.write(count + "\n");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return generatedRef;
    }

    public void deleteSupply(Object supply) {
        try {
            SessionFactory sessionFactory = new AnnotationConfiguration().configure("/hibernate.cfg_1.xml").buildSessionFactory();
            UserTransaction tx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            try {
                Session sess = sessionFactory.openSession();
                sess.delete(supply);
                sess.flush();
                try {
                    tx.commit();
                } catch (RollbackException ex) {
                    Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (HeuristicMixedException ex) {
                    Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (HeuristicRollbackException ex) {
                    Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (SystemException ex) {
                Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Autres fetching">
    public List<EdsNumber96k> getAll96kNumbers() {
        checkSession();

        logger.log(Level.INFO, "[SERVER] getAll96kNumbers()");

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");

            try {
                HibernateUtil.beginTransaction();

                Query q = HibernateUtil.getSession().createQuery("from EdsNumber96k n96");
                List<EdsNumber96k> values = q.list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();

                return values;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // Récupérer les utilisateurs PSA depuis le LDAP. À implémenter quand on aura un accès.
    static private Random gen = new Random();

    public EdsUser retrievePSAUserData(String psaId) {
        try {
            LDAPUser aPUser = new LDAPUser();
            aPUser.setUid(psaId);
            return new EdsUser(UUID.randomUUID().toString(), null, null, null, psaId, aPUser.getFirstName(), aPUser.getLastName(),
                    aPUser.getDirection(), 1, null, null, null, null, null);
        } catch (LDAPException ex) {
            return null;
        }

        // if ( gen.nextBoolean() )
        // {
        // return new EdsUser( UUID.randomUUID().toString() ,
        // null ,
        // null ,
        // null ,
        // psaId ,
        // "Bernard" ,
        // "Hatter" ,
        // "SSTD" ,
        // 1 ,
        // null ,
        // null ,
        // null ,
        // null ,
        // null );
        // }
        // return null;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Méthodes Hibernate">
    public void mergeDetachedDBObject(Object dbObject) {
        logger.log(Level.INFO, "[SERVER] mergePersistantDBObject() : object={0}", new Object[] { dbObject, });

        checkSession();

        if (dbObject == null) {
            return;
        }

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().merge(dbObject);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateDetachedDBObject(Object dbObject) {
        checkSession();

        if (dbObject == null) {
            return;
        }

        logger.log(Level.INFO, "[SERVER] updatePersistantDBObject() : object={0}", new Object[] { dbObject, });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().update(dbObject);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveDetachedDBObject(Object dbObject) {
        checkSession();

        if (dbObject == null) {
            return;
        }

        logger.log(Level.INFO, "[SERVER] savePersistantDBObject() : object={0}", new Object[] { dbObject, });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().save(dbObject);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void refreshDetachedDBObject(Object dbObject) {
        checkSession();

        if (dbObject == null) {
            return;
        }

        logger.log(Level.INFO, "[SERVER] refreshPersistantDBObject() : object={0}", new Object[] { dbObject, });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().refresh(dbObject);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteDetachedDBObject(Object dbObject) {
        checkSession();

        if (dbObject == null) {
            return;
        }

        logger.log(Level.INFO, "[SERVER] deletePersistantDBObject() : object={0}", new Object[] { dbObject, });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().delete(dbObject);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // </editor-fold>

    public List<EdsEds> executeEdsCriteria(DetachedCriteria criteria) {
        logger.log(Level.INFO, String.format("[SERVER] executeEdsCriteria : criteria=%s", criteria));

        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                List<EdsEds> values = criteria.getExecutableCriteria(HibernateUtil.getSession()).list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return values;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> List<T> executeCriteria(DetachedCriteria criteria, Class<T> returnClass) {
        logger.log(Level.INFO, String.format("[SERVER] executeCriteria : criteria=%s class=", criteria, returnClass.getName()));

        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                List<T> values = criteria.getExecutableCriteria(HibernateUtil.getSession()).list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return values;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void lockEdsAccess(EdsUser callerUser, String edsId) throws E_AccessLocked {
        AccessToken fullEdsToken = accessTokens.get(new AccessToken.AccessTokenId(edsId, edsId));
        AccessToken edsToken = accessTokens.get(new AccessToken.AccessTokenId(edsId, null));
        Date currentDate = new Date();

        if (fullEdsToken != null && currentDate.before(fullEdsToken.getEndDate())) {
            throw new E_AccessLocked(fullEdsToken.getCallerUser());
        }
        if (edsToken != null && currentDate.before(edsToken.getEndDate())) {
            throw new E_AccessLocked(edsToken.getCallerUser());
        }

        fullEdsToken = new AccessToken(edsId, edsId, currentDate, callerUser);
        accessTokens.put(fullEdsToken.createId(), fullEdsToken);
    }

    public void freeEdsAccess(EdsUser callerUser, String edsId) {
        AccessToken fullEdsToken = accessTokens.get(new AccessToken.AccessTokenId(edsId, edsId));
        if (fullEdsToken != null && fullEdsToken.getCallerUser().equals(callerUser)) {
            accessTokens.remove(fullEdsToken.createId());
        }
    }

    public synchronized void lockFormAccess(EdsUser callerUser, String edsId, String formId) throws E_AccessLocked {
        AccessToken fullEdsToken = accessTokens.get(new AccessToken.AccessTokenId(edsId, formId));
        Date currentDate = new Date();

        if (fullEdsToken != null && currentDate.before(fullEdsToken.getEndDate())) {
            throw new E_AccessLocked(fullEdsToken.getCallerUser());
        }

        AccessToken formToken = accessTokens.get(new AccessToken.AccessTokenId(edsId, formId));

        if (formToken == null) {
            Date endDate = new Date();
            endDate.setTime(endDate.getTime() + 20000000);

            formToken = new AccessToken(edsId, formId, endDate, callerUser);
            AccessToken edsToken = new AccessToken(edsId, null, endDate, callerUser);

            accessTokens.put(formToken.createId(), formToken);
            accessTokens.put(edsToken.createId(), edsToken);
        } else {
            if (formToken.getEndDate().after(currentDate)) // Access still locked
            {
                if (callerUser.equals(formToken.getCallerUser())) // Same user => override
                {
                    Date endDate = new Date(currentDate.getTime() + 2400000);
                    formToken.setEndDate(endDate);
                } else {
                    throw new E_AccessLocked(formToken.getCallerUser());
                }
            } else // Not locked anymore => override
            {
                Date endDate = new Date(currentDate.getTime() + 2400000);

                formToken.setCallerUser(callerUser);
                formToken.setEndDate(endDate);
            }
        }
    }

    public void freeFormAccess(EdsUser callerUser, String edsId, String formId) {
        AccessToken token = accessTokens.get(new AccessToken.AccessTokenId(edsId, formId));
        if (token != null && token.getCallerUser().equals(callerUser)) {
            accessTokens.remove(token.createId());
            accessTokens.remove(new AccessToken.AccessTokenId(edsId, null));
        }
    }

    // PACKAGE
    static void queryInterval(Query q, int intervalFrom, int intervalSize) {
        q.setFirstResult(intervalFrom);
        q.setMaxResults(intervalSize);
    }

    // PROTECTED
    // PRIVATE
    private javax.mail.Session mailSession;
    private final static Logger logger = Logger.getLogger(EDSdao.class.getName());
    private final static SimpleDateFormat DF = new SimpleDateFormat("yyMMdd");
    private static EDSdao singleton = null;
    // private Session HibernateUtil.getSession();
    private HashMap<AccessToken.AccessTokenId, AccessToken> accessTokens;

    private void init() {
        // this.HibernateUtil.getSession() = null;
        try {
            this.mailSession = (javax.mail.Session) new InitialContext().lookup("java:comp/env/mail/edsRef");
        } catch (NamingException ex) {
            Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.accessTokens = new HashMap<AccessToken.AccessTokenId, AccessToken>();
    }

    private EDSdao() {
        init();
    }

    private void checkSession() {
        if (HibernateUtil.getSession() == null) {
            throw new RuntimeException("DB session not opened");
        }
    }

    private <T> T executeGet(String key, Class<T> returnClass) {
        if (key == null) {
            throw new NullPointerException();
        }

        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                T value = (T) HibernateUtil.getSession().get(returnClass, key);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();

                return value;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> void executeCreate(T object) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                HibernateUtil.getSession().save(object);

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> T executeSingletonQuery(Query query, Class<T> returnClass) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                T value = (T) query.uniqueResult();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return value;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> List<T> executeListQuery(Query query, Class<T> returnClass) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                List<T> values = query.list();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();
                return values;
            } catch (RuntimeException th) {
                HibernateUtil.commitTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private int executeCountQuery(Query query) {
        checkSession();

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                int count = ((Long) query.iterate().next()).intValue();

                HibernateUtil.commitTransaction();
                HibernateUtil.closeSession();

                return count;
            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private EdsEds versionMinorEdsInDB(EdsEds eds) {
        EdsEds edsDuplicata = new EdsEds(eds);
        edsDuplicata.setEdsState(EdsEds.DEFAULT_STATE);
        edsDuplicata.setEdsMinorVersion(edsDuplicata.getEdsMinorVersion() + 1);

        saveDetachedDBObject(edsDuplicata);

        return edsDuplicata;
    }

    private EdsEds versionMajorEdsInDB(EdsEds eds) {
        EdsEds edsDuplicata = new EdsEds(eds);
        edsDuplicata.setEdsState(EdsEds.DEFAULT_STATE);
        edsDuplicata.setEdsMajorVersion(edsDuplicata.getEdsMajorVersion() + 1);
        edsDuplicata.setEdsMinorVersion(EdsEds.FIRST_MINOR_VERSION);

        saveDetachedDBObject(edsDuplicata);

        return edsDuplicata;
    }

    /**
     * @param eds
     * @return la liste de tous les formulaires associé à la fiche eds
     */
    private List<I_FormData> getAllFormsData(String edsId) {
        List<I_FormData> formsData = new ArrayList<I_FormData>();

        checkSession();

        logger.log(Level.INFO, "[SERVER] getAllFormsData() : eds.id={0}", new Object[] { edsId });

        try {
            // UserTransaction tx = (UserTransaction) new InitialContext().lookup(
            // "java:comp/UserTransaction" );

            try {
                HibernateUtil.beginTransaction();

                for (Class<?> formClass : getAllFormDataClasses()) {
                    Query q = HibernateUtil.getSession()
                            .createQuery("select fd from " + formClass.getSimpleName() + " fd" + " where fd.edsEds.edsId=?");
                    q.setParameter(0, edsId);
                    formsData.addAll(q.list());
                }

                HibernateUtil.commitTransaction();

            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return formsData;
    }

    public List<Object> excuteQuery(String requete) {
        List<Object> formsData = new ArrayList<Object>();

        checkSession();

        logger.log(Level.INFO, "[SERVER] Execution requete", new Object[] { requete });

        try {
            try {
                HibernateUtil.beginTransaction();

                Query q = HibernateUtil.getSession().createQuery(requete);
                formsData.addAll(q.list());

                HibernateUtil.commitTransaction();

            } catch (RuntimeException th) {
                HibernateUtil.rollbackTransaction();

                throw th;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return formsData;
    }

    public List<EdsSAPReference> getAllSAPReferenceRevision() {
        logger.log(Level.INFO, String.format("[SERVER] getAllSAPReferenceRevision() "));

        checkSession();

        Query q = null;
        q = HibernateUtil.getSession().createQuery("from EdsSAPReference esr" + " order by esr.referenceRevision asc");

        return executeListQuery(q, EdsSAPReference.class);
    }
}
