/*
 * Creation : Mar 7, 2016
 */
package com.inetpsa.pv2.service;

import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.beans.PictoInformation;
import com.inetpsa.pv2.props.PropertiesCache;
import com.inetpsa.pv2.props.PropertiesLang;
import com.inetpsa.pv2.rest.RestService;

/**
 * To set data in PictoInformation object
 * 
 * @author mehaj
 */
public class PictoInfoSet {
    /**
     * Logger log4j to write messages
     */
    private static Logger logger = LoggerFactory.getLogger(PictoInfoSet.class);

    /**
     * loading properties file into a variable
     */
    private PropertiesCache propertiesCache = PropertiesCache.getInstance();
    RestService restService = new RestService();

    /**
     * Setting dummy data into PictoDetails object and adding it to a set
     * 
     * @return returning set<PictoDetails> having dummy data for all pictos present in local directory
     */
    public TreeSet<PictoInformation> getPictoClientInformation(long adminId) {
        TreeSet<PictoInformation> pictoSet = new TreeSet<PictoInformation>(new PictoInfoComparator());
        // Long pictoId = null;
        List<PictoInformation> allPictoIds = null;
        try {
            allPictoIds = restService.getAllPictosForUsrId(propertiesCache.getProperty("picto.domain.name")
                    + PictoConstants.GET_ALLPICTOS_BY_USRID_URL, adminId);

            if (allPictoIds != null && allPictoIds.size() > 0) {
                for (int i = 0; i < allPictoIds.size(); i++) {
                    logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_PICTOCLIENT_BY_PICTOID_URL);
                    PictoInformation pictoInformation = allPictoIds.get(i);
                    logger.debug(propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_WORKING_ADMIN_LIST_URL);
                    List<String> workingAdminList = restService.getWorkAdminList(
                            (propertiesCache.getProperty("picto.domain.name") + PictoConstants.GET_WORKING_ADMIN_LIST_URL),
                            pictoInformation.getPictoId());
                    pictoInformation.setWorkingAdminList(workingAdminList);

                    pictoSet.add(pictoInformation);

                }
            } else {
                logger.info(PropertiesLang.getInstance().getProperty("picto.empty.download"));
            }
        } catch (Exception e) {
            logger.error(" Exception occure while get all pictos ", e);
        }
        return pictoSet;
    }
}
