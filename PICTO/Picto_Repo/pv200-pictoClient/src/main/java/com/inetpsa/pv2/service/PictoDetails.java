/*
 * Creation : Mar 10, 2016
 */
package com.inetpsa.pv2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.rest.RestService;

/**
 * To return Set of all picto images downloaded by administrator
 * 
 * @author mehaj
 */
public class PictoDetails {
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(PictoDetails.class);
    /**
     * RestService class object to access rest call methods
     */
    RestService restService = new RestService();
    /**
     * PictoInfoSet Object
     */
    private static PictoInfoSet pictoInfoSet = new PictoInfoSet();
    /**
     * Singleton object
     */
    private static final PictoDetails pictoDetails = new PictoDetails();

    /*
     * List of picto Ids
     */
    List<Long> pictoNameList = new ArrayList<Long>();
    /*
     * Loading Properties file
     */
    PropertiesCache propertiesCache = PropertiesCache.getInstance();

    /**
     * A private Constructor prevents any other class from instantiating.
     */
    private PictoDetails() {
    }

    /**
     * Static 'instance' method
     * 
     * @return : singleton instance
     */
    public static PictoDetails getInstance() {
        return pictoDetails;
    }

    /**
     * getting all picto information from PictoInfoSet class
     */
    public TreeSet<PictoInformation> getAllPictos() {
        long adminId = AdminDetails.getInstance().getLoggedInAdmin();
        TreeSet<PictoInformation> pictoInformationSet = new TreeSet<PictoInformation>();
        pictoInformationSet = pictoInfoSet.getPictoClientInformation(adminId);
        return pictoInformationSet;
    }

    public List<Long> listOfAllLocalDirPictos() {
        List<Long> allDownloadedPictoIds = new ArrayList<Long>();
        try {
            logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_ALLPICTOS_BY_USRID_URL);
            List<PictoInformation> allDownloadedPictos = restService.getAllPictosForUsrId(propertiesCache.getProperty("picto.domain.name")
                    + PictoConstants.GET_ALLPICTOS_BY_USRID_URL, AdminDetails.getInstance().getLoggedInAdmin());
            for (PictoInformation pictoInformation : allDownloadedPictos) {
                allDownloadedPictoIds.add(pictoInformation.getPictoId());
            }
        } catch (Exception e) {

            logger.error(" Exception occure while get the local directory picto ", e);
        }
        return allDownloadedPictoIds;
    }

    public List<Long> setselectedPictos(Long filename) {

        pictoNameList.add(filename);
        return pictoNameList;

    }

    public List<Long> getselectedPictos() {

        return pictoNameList;

    }

}
