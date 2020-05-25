package com.inetpsa.pv2.infrastructure.finders;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.notificationContrib.NotificationContrib;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.transaction.Propagation;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.category.CategoryAssembler;
import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.picto.PictoAssembler;
import com.inetpsa.pv2.rest.picto.PictoFilterRepresentation;
import com.inetpsa.pv2.rest.picto.PictoFinder;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.pictoclient.PictoClientFinder;
import com.inetpsa.pv2.rest.pictoclient.PictoClientRepresentation;

/**
 * The Class JpaPictoFinder.
 */
public class JpaPictoFinder implements PictoFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(JpaPictoFinder.class);

    /** The picto assembler. */
    @Inject
    private PictoAssembler pictoAssembler;

    /** The category assembler. */
    @Inject
    private CategoryAssembler categoryAssembler;

    /** The picto client finder. */
    @Inject
    private PictoClientFinder pictoClientFinder;

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    /** The result cart. */
    String resultCart;

    /** The admin 1 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole1")
    private String admin1Role;

    /** The admin 2 role. */
    @Configuration("com.inetpsa.pv2.role.adminRole2")
    private String admin2Role;

    /** The host url. */
    @Configuration("com.inetpsa.pv2.host.hostUrl")
    private String hostUrl;

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /** The Constant PATTERN_REGEX_FOR_AND_KEYWORD. */
    private static final Pattern PATTERN_REGEX_FOR_AND_KEYWORD = Pattern.compile("\\+");

    /** The Constant STAR_CHAR. */
    private static final String STAR_CHAR = "*";

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getAllPictos(org.seedstack.pv2.domain.user.User)
     */
    @Override
    public List<PictoRepresentation> getAllPictos(User user) {
        logger.info("Start Query to get all picto object");
        String contentOfJpqlQuery;
        boolean isAdmin = false;
        if (securitySupport.hasRole(admin1Role) || securitySupport.hasRole(admin2Role)) {
            isAdmin = true;
        }

        if (isAdmin) {
            contentOfJpqlQuery = "select distinct objectPic from Picto objectPic where objectPic.isFrontagePicto = true ORDER BY objectPic.pictoFamilyID.referenceNum ASC";
        } else {
            contentOfJpqlQuery = "select distinct objectPic from Picto objectPic"
                    + " where objectPic.isFrontagePicto = true and objectPic.isVisible=true ORDER BY objectPic.pictoFamilyID.refCharte ASC NULLS LAST";
        }

        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();
        try {
            TypedQuery<Picto> typedQuery = entityManager.createQuery(contentOfJpqlQuery, Picto.class);
            List<Picto> listPictoId = typedQuery.getResultList();

            List<PictoClientRepresentation> aiDownloadList = pictoClientFinder.getDownloadAIList();

            if (listPictoId != null && listPictoId.size() >= 0) {
                for (Picto l_Picto : listPictoId) {
                    PictoRepresentation pictoRepresentation = new PictoRepresentation();
                    pictoAssembler.doAssembleDtoFromAggregate(pictoRepresentation, l_Picto);
                    setColor(aiDownloadList, pictoRepresentation, user.getId());
                    listPicto.add(pictoRepresentation);

                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the all pictos :", e);
            listPicto = null;
        }
        logger.info(" Finish: return the all picto object");
        return listPicto;
    }

    /**
     * Set the color based on the AI file download by user.
     *
     * @param aiDownloadList the ai download list
     * @param pictoRepresentation the picto representation
     * @param userId the user id
     */
    @Override
    public void setColor(List<PictoClientRepresentation> aiDownloadList, PictoRepresentation pictoRepresentation, Long userId) {

        if (!aiDownloadList.isEmpty()) {
            for (PictoClientRepresentation obj : aiDownloadList) {
                if ((obj.getUserId().getId() == userId) && (obj.getPictoId().getId().equals(pictoRepresentation.getId()))) {
                    pictoRepresentation.setColorFlag(PictoConstants.RED);
                    break;
                }
                if (obj.getUserId().getId() != userId && obj.getPictoId().getId().equals(pictoRepresentation.getId())) {
                    pictoRepresentation.setColorFlag(PictoConstants.YELLOW);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getPictosInCart(org.seedstack.pv2.domain.user.User)
     */
    @Override
    public List<PictoRepresentation> getPictosInCart(User user) {
        logger.info("Start: Query to get all pictos for user : " + user.getId());

        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();
        try {

            List<Picto> listPictoId = user.getUsersListShopCarts();
            List<PictoClientRepresentation> aiDownloadList = pictoClientFinder.getDownloadAIList();

            if (listPictoId != null && listPictoId.size() >= 0) {
                for (Picto l_Picto : listPictoId) {
                    PictoRepresentation pictoRepresentation = new PictoRepresentation();
                    pictoAssembler.doAssembleDtoFromAggregate(pictoRepresentation, l_Picto);
                    setColor(aiDownloadList, pictoRepresentation, user.getId());
                    listPicto.add(pictoRepresentation);

                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching Shopping cart pictos:", e);
            listPicto = null;
        }
        logger.info(" Finish: return the all picto object in shop cart");
        return listPicto;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getAllNotification()
     */
    @Override
    public List<PictoRepresentation> getAllNotification() {
        logger.info("Start: Query to get all picto object");
        Long familyID = null;
        String contentOfJpqlQuery = "select distinct objectNotif from NotificationContrib objectNotif";

        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();
        try {
            TypedQuery<NotificationContrib> typedQuery = entityManager.createQuery(contentOfJpqlQuery, NotificationContrib.class);
            List<NotificationContrib> notificationList = typedQuery.getResultList();
            for (NotificationContrib notification : notificationList) {
                familyID = notification.getFamilyID().getEntityId();
                if (familyID != null) {
                    List<PictoRepresentation> pictoRepresentationList = getFrontagePictoByFamId(familyID);
                    for (PictoRepresentation pictoRepresentation : pictoRepresentationList) {
                        if (pictoRepresentation.getVersion() != null) {
                            if (pictoRepresentation.getFamilyID().getRefCharte() != null) {

                                pictoRepresentation
                                        .setPictoName(pictoRepresentation.getFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                                + pictoRepresentation.getFamilyID().getReferenceNum() + pictoRepresentation.getFamilyID().getName()
                                                + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVariantType()
                                                + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVersion());
                            } else {
                                pictoRepresentation.setPictoName(pictoRepresentation.getFamilyID().getReferenceNum()
                                        + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getFamilyID().getName()
                                        + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVariantType()
                                        + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVersion());
                            }
                        } else {
                            if (pictoRepresentation.getFamilyID().getRefCharte() != null) {

                                pictoRepresentation
                                        .setPictoName(pictoRepresentation.getFamilyID().getReferenceNum() + PictoConstants.PICTO_NAME_SEPARATOR
                                                + pictoRepresentation.getFamilyID().getRefCharte() + pictoRepresentation.getFamilyID().getName()
                                                + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVariantType());

                            } else {
                                pictoRepresentation.setPictoName(pictoRepresentation.getFamilyID().getReferenceNum()
                                        + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getFamilyID().getName()
                                        + PictoConstants.PICTO_NAME_SEPARATOR + pictoRepresentation.getVariantType());
                            }
                        }
                        listPicto.add(pictoRepresentation);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the all notifications :", e);
            listPicto = null;
        }
        logger.info(" Finish: Rturn all notification objects . ");
        return listPicto;
    }

    /**
     * Gets the frontage picto by fam id.
     * 
     * @param familyId the family id
     * @return the frontage picto by fam id
     */
    public List<PictoRepresentation> getFrontagePictoByFamId(Long familyId) {

        logger.info("Start: Query to get all picto object");

        String contentOfJpqlQuery = "select distinct objectPic from Picto objectPic"
                + " where objectPic.isFrontagePicto = true and objectPic.pictoFamilyID.familyId= ?1";

        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();
        try {
            TypedQuery<Picto> typedQuery = entityManager.createQuery(contentOfJpqlQuery, Picto.class);
            typedQuery.setParameter(1, familyId);
            List<Picto> listPictoId = typedQuery.getResultList();
            if (listPictoId != null && listPictoId.size() >= 0) {
                for (Picto l_Picto : listPictoId) {
                    PictoRepresentation pictoRepresentation = new PictoRepresentation();
                    pictoAssembler.doAssembleDtoFromAggregate(pictoRepresentation, l_Picto);
                    listPicto.add(pictoRepresentation);

                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the all pictos:", e);
            listPicto = null;
        }
        logger.info(" Finish: return the all picto object");
        return listPicto;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getAllCategories()
     */
    @Override
    public List<CategoryRepresentation> getAllCategories() {
        logger.info("Start: Query to get all category");
        String contentOfJpqlQuery = "select distinct category from Category category";
        List<CategoryRepresentation> categoryRepresentationList = new ArrayList<CategoryRepresentation>();
        try {
            TypedQuery<Category> typedQuery = entityManager.createQuery(contentOfJpqlQuery, Category.class);
            List<Category> categoryList = typedQuery.getResultList();
            if (categoryList != null && !categoryList.isEmpty()) {
                for (Category category : categoryList) {
                    CategoryRepresentation categoryRepresentation = new CategoryRepresentation();
                    categoryAssembler.doAssembleDtoFromAggregate(categoryRepresentation, category);
                    categoryRepresentationList.add(categoryRepresentation);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the all categories:", e);
            categoryRepresentationList = null;
        }
        logger.info(" Finish: return the all categories object");
        return categoryRepresentationList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getFilteredPictos(com.inetpsa.pv2.rest.picto.PictoFilterRepresentation)
     */
    /* SN - PSA - PRP024006-132 - 08-Dec-16 - Start */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PictoRepresentation> getFilterData(PictoFilterRepresentation pictoFilterRepresentation) {
        List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();

        StringBuilder nativeQueryBuilder = new StringBuilder(
                "SELECT PIC.ID, PIC.VARIANT, PIC.IS_FRONTAGE_PICTO, PIC.IS_VISIBLE, PIC.IMAGE_LOCATION, PIC.VERSION,"
                        + " PFM.ID FAMILYID, PFM.REF_NUM, PFM.NAME, PFM.INFO_TYPE, PFM.VALIDATION_LEVEL, PFM.REF_CHARTE "
                        + " FROM PV2QTPFM PFM, PV2QTPIC PIC ");
        if (pictoFilterRepresentation.getCategory() != null) {
            nativeQueryBuilder.append(" ,PV2QTCAT CAT ");
        }
        nativeQueryBuilder.append(" WHERE PIC.PFM_ID = PFM.ID AND PIC.IS_FRONTAGE_PICTO=:frontage");

        // Category type where clause
        addCategoryTypeClause(pictoFilterRepresentation, nativeQueryBuilder);

        // Image type where clause
        addImageTypeClause(pictoFilterRepresentation, nativeQueryBuilder);

        // VisiblityClause
        addVisiblityClause(pictoFilterRepresentation, nativeQueryBuilder);

        // Favorites where clause
        List<Long> favoriteList = addFavoritesClause(pictoFilterRepresentation, nativeQueryBuilder);

        // ModifyDate where clause
        addModifyDateClause(pictoFilterRepresentation, nativeQueryBuilder);

        // InformationType where clause
        addInformationTypeClause(pictoFilterRepresentation, nativeQueryBuilder);

        // Picto Client where clause
        List<Long> pictoClientIds = addPictoClientClause(pictoFilterRepresentation, nativeQueryBuilder);

        // ValidationLevel where clause
        addValidationLevelClause(pictoFilterRepresentation, nativeQueryBuilder);

        // Free Text Search where clause
        addFreeTextSearchClause(nativeQueryBuilder, pictoFilterRepresentation.getSearchText());

        // OrderBy where clause
        addOrderByClause(pictoFilterRepresentation, nativeQueryBuilder);

        // Create native query
        Query nativeSearchQuery = entityManager.createNativeQuery(nativeQueryBuilder.toString());

        // populate search parameters
        setSearchParameters(pictoFilterRepresentation, favoriteList, pictoClientIds, nativeSearchQuery);

        List<PictoClientRepresentation> aiDownloadList = pictoClientFinder.getDownloadAIList();

        // get results
        List<Object> resultSet;
        resultSet = nativeSearchQuery.getResultList();
        for (Iterator iterator = resultSet.iterator(); iterator.hasNext();) {

            Object[] resultSetRow = (Object[]) iterator.next();
            PictoRepresentation picto = new PictoRepresentation(((BigInteger) resultSetRow[0]).longValue(), (String) resultSetRow[1],
                    (Boolean) resultSetRow[2], (Boolean) resultSetRow[3], (String) resultSetRow[4], (String) resultSetRow[5],
                    ((BigInteger) resultSetRow[6]).longValue(), (String) resultSetRow[7], (String) resultSetRow[8], (String) resultSetRow[9],
                    (String) resultSetRow[10], (String) resultSetRow[11]);

            // set image URL
            String url = createUrl(picto);
            picto.setImageUrl(url);
            // set color
            setColor(aiDownloadList, picto, pictoFilterRepresentation.getUser().getId());
            // add to collection
            listPicto.add(picto);
        }

        return listPicto;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.picto.PictoFinder#getAllPictoId(java.lang.Long)
     */
    @Override
    public List<Long> getAllPictoId(Long picFamilyId) {
        List<Long> pictoId = null;
        if (picFamilyId != null) {
            try {
                String contentOfJpqlQuery = "select objectPic.pictoId from Picto objectPic" + " where objectPic.pictoFamilyID.familyId = :familyID";
                Query typedQuery = entityManager.createQuery(contentOfJpqlQuery);
                typedQuery.setParameter("familyID", picFamilyId);
                pictoId = typedQuery.getResultList();
            } catch (Exception e) {
                logger.error("Error in getting picto id from Picto ", e);
            }
        }
        return pictoId;
    }

    /**
     * remove the shopping cart information.
     *
     * @param userId the user id
     * @param pictoId the picto id
     */
    @Override
    public void removeShopCart(Long userId, Long pictoId) {
        Query query = entityManager.createNativeQuery("delete from PV2QTSHP where USR_ID = :user and PIC_ID = :picto");
        query.setParameter("user", userId);
        query.setParameter("picto", pictoId);
        query.executeUpdate();

    }

    /**
     * Transform search text.
     *
     * @param searchString the search string
     * @return the formatted searh string
     */
    private static String transformSearchText(String searchString) {
        // get a matcher object
        Matcher regexMatcher = PATTERN_REGEX_FOR_AND_KEYWORD.matcher(searchString);
        StringBuilder formattedSearchString = new StringBuilder();
        // Define separator between the keywords for And and Or cases
        String andOrSeparator;
        if (regexMatcher.find()) {
            // Handling AND case
            formattedSearchString.append(regexMatcher.replaceAll(" ").trim());
            andOrSeparator = " +";
        } else {
            // Handling OR case
            formattedSearchString.append(searchString);
            andOrSeparator = " ";

        }

        // Split the keywords using space separator
        String[] keywordTokens = formattedSearchString.toString().trim().split(" ");
        // re-initialize formatted search text string
        formattedSearchString = new StringBuilder();
        for (int index = 0; index < keywordTokens.length; index++) {
            String keyWord = keywordTokens[index];
            // add keyword if not empty and apend the and/Or separator and star char to search as BEGINS_WITH
            if (!keyWord.trim().isEmpty()) {
                formattedSearchString.append(andOrSeparator).append(keyWord.trim()).append(STAR_CHAR);
            }

        }
        // return formatted string after trimming the empty spaces.
        return formattedSearchString.toString().trim();
    }

    /**
     * Gets the modified search data.
     *
     * @param nativeQueryBuilder the native query builder
     * @param searchText the search
     * @return the modified search data 1) This method is being extracted from "getSearchData()" of the same class 2) This is yet to test completely.
     *         3) If this method works absolutely fine, Please remove the getSearchData() method 4) Modified By : ankurp, mehaj
     */
    public void addFreeTextSearchClause(StringBuilder nativeQueryBuilder, String searchText) {

        if (searchText != null && !searchText.isEmpty()) {
            String transformedSearchText = transformSearchText(searchText);
            logger.debug("transformedSearchText:--> " + transformedSearchText);
            nativeQueryBuilder.append(" AND ( ");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.REF_NUM");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.NAME");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.FUNCTION_FR");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.FUNCTION_EN");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.REF_CHARTE");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.KEYWORD_EN");
            addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.KEYWORD_FR");

            if (securitySupport.hasRole(admin1Role) || securitySupport.hasRole(admin2Role)) {
                addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.INFO_LABEL_EN");
                addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.INFO_LABEL_FR");
                addCloumnAndSearchText(nativeQueryBuilder, transformedSearchText, "PFM.ADMIN_INFO");
            }

            nativeQueryBuilder.replace(nativeQueryBuilder.length() - 2, nativeQueryBuilder.length(), ") ");
        }

    }

    /**
     * Adds the cloumn and search text.
     *
     * @param nativeQueryBuilder the native query builder
     * @param search the search
     * @param columnName the column name
     */
    private void addCloumnAndSearchText(StringBuilder nativeQueryBuilder, String search, String columnName) {
        nativeQueryBuilder.append(" MATCH(" + columnName + ") AGAINST('" + search + "' IN BOOLEAN MODE ) OR");
    }

    /**
     * Adds the category type clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addCategoryTypeClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.getCategory() != null) {
            nativeQueryBuilder.append(" AND PFM.CATEGORY_ID= CAT.ID ");
            nativeQueryBuilder.append(" AND CAT.CATEGORY = :categoryId ");
        }
    }

    /**
     * Adds the image type clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addImageTypeClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.getImageType() != null && !pictoFilterRepresentation.getImageType().isEmpty()) {
            if (pictoFilterRepresentation.isNullTypeSelected()) {
                nativeQueryBuilder.append(" AND (PFM.TYPE_ID IN (:p_typeIdList) OR PFM.TYPE_ID IS NULL)");
            } else {
                nativeQueryBuilder.append(" AND PFM.TYPE_ID IN (:p_typeIdList) ");
            }
        }
    }

    /**
     * Adds the order by clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addOrderByClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.getSortByParameter() != null) {
            String sortParamenter = pictoFilterRepresentation.getSortByParameter();
            if (PictoConstants.REFERENCE_NUM.equalsIgnoreCase(sortParamenter) || PictoConstants.REFERENCE_NUMEN.equalsIgnoreCase(sortParamenter)) {
                nativeQueryBuilder.append(" ORDER BY PFM.REF_NUM ASC ");
            }
            if (PictoConstants.CHARTE_NUM.equalsIgnoreCase(sortParamenter) || PictoConstants.CHARTE_NUMEN.equalsIgnoreCase(sortParamenter)) {
                nativeQueryBuilder.append(" ORDER BY ISNULL(PFM.REF_CHARTE), PFM.REF_CHARTE ASC , PFM.REF_NUM ASC ");
            }
            if (PictoConstants.UPDATED_DATE.equalsIgnoreCase(sortParamenter)) {
                nativeQueryBuilder.append(" ORDER BY PIC.MODIFY_DATE DESC ");
            }
        }
    }

    /**
     * Adds the validation level clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addValidationLevelClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.isInProgress() && pictoFilterRepresentation.isValidated()) {
            // No need of any statement, if both condition are true.
        } else if (pictoFilterRepresentation.isInProgress()) {
            nativeQueryBuilder.append(" AND PFM.VALIDATION_LEVEL =" + PictoConstants.VALIDATION_LEVEL_INPROGRESS);
        } else if (pictoFilterRepresentation.isValidated()) {
            nativeQueryBuilder.append(" AND PFM.VALIDATION_LEVEL =" + PictoConstants.VALIDATION_LEVEL_VALIDATE);
        }
    }

    /**
     * Adds the picto client clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     * @return the list
     */
    private List<Long> addPictoClientClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        Query typedQueryClient = entityManager.createQuery("select objectClient.pictoId.pictoId from PictoClient objectClient");
        List<Long> pictoClientIds = typedQueryClient.getResultList();
        if (pictoClientIds.isEmpty()) {
            pictoClientIds.add(0L);
        }
        if (pictoFilterRepresentation.isExceptInModification() && pictoFilterRepresentation.isInModificationPictos()) {
            // No need of any statement, if both condition are true.
        } else if (pictoFilterRepresentation.isExceptInModification()) {
            nativeQueryBuilder.append(" AND PIC.ID NOT IN (:pictoClient) ");
        } else if (pictoFilterRepresentation.isInModificationPictos()) {
            nativeQueryBuilder.append(" AND PIC.ID IN (:pictoClient) ");
        }
        return pictoClientIds;
    }

    /**
     * Adds the information type clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addInformationTypeClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.isInfoInProgress() && pictoFilterRepresentation.isPictoInfo()) {
            // No need of any statement, if both condition are true.
        } else if (pictoFilterRepresentation.isInfoInProgress()) {
            nativeQueryBuilder.append(" AND PFM.INFO_TYPE =" + PictoConstants.INFO_IN_PROGRESS);
        } else if (pictoFilterRepresentation.isPictoInfo()) {
            nativeQueryBuilder.append(" AND PFM.INFO_TYPE =" + PictoConstants.INFO_TO_BE_READ);
        }
    }

    /**
     * Adds the modify date clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addModifyDateClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.getDateFilter() != null && pictoFilterRepresentation.getDateSpan() != null) {
            if (pictoFilterRepresentation.getDateSpan().equalsIgnoreCase(PictoConstants.AFTER)) {
                nativeQueryBuilder.append(" AND PIC.MODIFY_DATE >= :modifyDate ");
            }
            if (pictoFilterRepresentation.getDateSpan().equalsIgnoreCase(PictoConstants.BEFORE)) {
                nativeQueryBuilder.append(" AND PIC.MODIFY_DATE <= :modifyDate ");
            }
        }
    }

    /**
     * Adds the favorites clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     * @return the list
     */
    private List<Long> addFavoritesClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        List<Long> favoriteList = new ArrayList<Long>();
        List<PictoFamily> pictosInFav = pictoFilterRepresentation.getUser().getUsersListFavorites();
        for (PictoFamily l_Picto : pictosInFav) {
            favoriteList.add(l_Picto.getEntityId());
        }
        if (favoriteList.isEmpty()) {
            favoriteList.add(0L);
        }
        if (pictoFilterRepresentation.isFavPictos() && pictoFilterRepresentation.isExceptFavPictos()) {
            // No need of any statement, if both condition are true.
        } else if (pictoFilterRepresentation.isFavPictos()) {
            nativeQueryBuilder.append(" AND PFM.ID IN (:famIdListInFav) ");
        } else if (pictoFilterRepresentation.isExceptFavPictos()) {
            nativeQueryBuilder.append(" AND PFM.ID NOT IN (:famIdListInFav) ");
        }
        return favoriteList;
    }

    /**
     * Adds the visiblity clause.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param nativeQueryBuilder the native query builder
     */
    private void addVisiblityClause(PictoFilterRepresentation pictoFilterRepresentation, StringBuilder nativeQueryBuilder) {
        if (pictoFilterRepresentation.isVisiblePicto() && pictoFilterRepresentation.isInvisiblePictos()) {
            nativeQueryBuilder.append(" AND PIC.IS_VISIBLE IN (0,1) ");
        } else if (pictoFilterRepresentation.isVisiblePicto()) {
            nativeQueryBuilder.append(" AND PIC.IS_VISIBLE = 1 ");
        } else if (pictoFilterRepresentation.isInvisiblePictos()) {
            nativeQueryBuilder.append(" AND PIC.IS_VISIBLE = 0 ");
        }
        boolean isAdmin = false;
        if (securitySupport.hasRole(admin1Role) || securitySupport.hasRole(admin2Role)) {
            isAdmin = true;
        }
        if (!isAdmin) {
            nativeQueryBuilder.append(" AND PIC.IS_VISIBLE = 1 ");
        }
    }

    /**
     * Sets the search parameters.
     *
     * @param pictoFilterRepresentation the picto filter representation
     * @param favoriteList the favorite list
     * @param pictoClientIds the picto client ids
     * @param nativeSearchQuery the native search query
     */
    private void setSearchParameters(PictoFilterRepresentation pictoFilterRepresentation, List<Long> favoriteList, List<Long> pictoClientIds,
            Query nativeSearchQuery) {
        nativeSearchQuery.setParameter("frontage", true);
        if (pictoFilterRepresentation.isFavPictos() && pictoFilterRepresentation.isExceptFavPictos()) {
            // No need of set statement, if both condition are true.
        } else if ((pictoFilterRepresentation.isFavPictos() || pictoFilterRepresentation.isExceptFavPictos()) && (!favoriteList.isEmpty())) {
            nativeSearchQuery.setParameter("famIdListInFav", favoriteList);
        }

        if (pictoFilterRepresentation.isExceptInModification() && pictoFilterRepresentation.isInModificationPictos()) {
            // No need of set statement, if both condition are true.
        } else if ((pictoFilterRepresentation.isExceptInModification() || pictoFilterRepresentation.isInModificationPictos())
                && !pictoClientIds.isEmpty()) {
            nativeSearchQuery.setParameter("pictoClient", pictoClientIds);
        }

        if (pictoFilterRepresentation.getDateFilter() != null && (pictoFilterRepresentation.getDateSpan() != null)) {
            String dateSelected = pictoFilterRepresentation.getDateFilter().substring(0, 10);
            java.sql.Date modifyDate = java.sql.Date.valueOf(dateSelected);

            nativeSearchQuery.setParameter("modifyDate", modifyDate);
        }
        if (pictoFilterRepresentation.getCategory() != null) {
            nativeSearchQuery.setParameter("categoryId", pictoFilterRepresentation.getCategory());
        }

        if (pictoFilterRepresentation.getImageType() != null && !pictoFilterRepresentation.getImageType().isEmpty()) {
            nativeSearchQuery.setParameter("p_typeIdList", pictoFilterRepresentation.getImageType());
        }
    }

    /**
     * Creates the url.
     *
     * @param picto the picto
     * @return the string
     */
    private String createUrl(PictoRepresentation picto) {
        String image = picto.getImageLocation();
        String url;
        if (picto.getVersion() == null) {
            url = hostUrl + PictoConstants.GET_IMAGE + tempFileDirectory + image + File.separator + image + PictoConstants.PICTO_NAME_SEPARATOR
                    + picto.getVariantType() + File.separator + image + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType()
                    + PictoConstants.FILE_JPG;
        } else {
            url = hostUrl + PictoConstants.GET_IMAGE + tempFileDirectory + image + File.separator + image + PictoConstants.PICTO_NAME_SEPARATOR
                    + picto.getVariantType() + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVersion() + File.separator + image
                    + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVariantType() + PictoConstants.PICTO_NAME_SEPARATOR + picto.getVersion()
                    + PictoConstants.FILE_JPG;
        }
        return url;
    }
    /* SN - PSA - PRP024006-132 - 08-Dec-16 - End */

}
