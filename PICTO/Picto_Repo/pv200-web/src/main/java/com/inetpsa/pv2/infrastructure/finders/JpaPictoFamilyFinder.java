/*
 * 
 */
package com.inetpsa.pv2.infrastructure.finders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.type.Type;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.color.ColorFinder;
import com.inetpsa.pv2.rest.color.ColorRepresentation;
import com.inetpsa.pv2.rest.picto.PictoFinder;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.pictoclient.PictoClientFinder;
import com.inetpsa.pv2.rest.pictoclient.PictoClientRepresentation;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyAssembler;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;
import com.inetpsa.pv2.rest.type.TypeAssembler;
import com.inetpsa.pv2.rest.type.TypeRepresentation;

/**
 * The Class JpaPictoFamilyFinder.
 */
public class JpaPictoFamilyFinder implements PictoFamilyFinder {

    /** The m entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger = LoggerFactory.getLogger(JpaPictoFamilyFinder.class);

    /** The m picto family assembler. */
    @Inject
    private PictoFamilyAssembler pictoFamilyAssembler;

    /** The m type assembler. */
    @Inject
    private TypeAssembler typeAssembler;

    /** The picto client finder. */
    @Inject
    private PictoClientFinder pictoClientFinder;

    /** The m picto finder. */
    @Inject
    PictoFinder pictoFinder;

    /** The m color finder. */
    @Inject
    private ColorFinder colorFinder;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#getFamilyInfoByRefnum(java.lang.String, org.seedstack.pv2.domain.user.User)
     */
    @Override
    public PictoFamilyRepresentation getFamilyInfoByRefnum(String refnum, User user) {
        logger.info(" Start: Query to get picto family object");
        String contentOfJpqlQuery = "select distinct objectPic from PictoFamily objectPic where objectPic.referenceNum = :ref";

        PictoFamilyRepresentation pictoFamilyRepresentation = new PictoFamilyRepresentation();
        try {
            TypedQuery<PictoFamily> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoFamily.class);
            typedQuery.setParameter("ref", refnum);

            PictoFamily picFamily = typedQuery.getSingleResult();

            pictoFamilyAssembler.doAssembleDtoFromAggregate(pictoFamilyRepresentation, picFamily);

            List<ColorRepresentation> colorList = colorFinder.getAllColor();

            List<ColorRepresentation> activeColor = pictoFamilyRepresentation.getWitnessActive();
            List<ColorRepresentation> alertColor = pictoFamilyRepresentation.getWitnessAlert();
            List<ColorRepresentation> failureColor = pictoFamilyRepresentation.getWitnessFailure();

            pictoFamilyRepresentation.setWitnessActive(compareColor(colorList, activeColor));
            pictoFamilyRepresentation.setWitnessAlert(compareColor(colorList, alertColor));
            pictoFamilyRepresentation.setWitnessFailure(compareColor(colorList, failureColor));
            List<PictoClientRepresentation> aiDownloadList = pictoClientFinder.getDownloadAIList();
            List<PictoRepresentation> listPicto = new ArrayList<PictoRepresentation>();

            List<PictoRepresentation> listPictoRepresentation = pictoFamilyRepresentation.getPictos();
            for (PictoRepresentation l_Picto : listPictoRepresentation) {
                pictoFinder.setColor(aiDownloadList, l_Picto, user.getId());
                StringBuilder str = getAdminList(l_Picto.getId());
                l_Picto.setAdminName(str.toString());
                listPicto.add(l_Picto);

            }
            pictoFamilyRepresentation.setPictos(listPicto);

        } catch (Exception e) {
            logger.error("Exception occure while fetching the picto family by reference number:", e);
            pictoFamilyRepresentation = null;
        }
        logger.info(" Finish: return the picto family object");
        return pictoFamilyRepresentation;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#downloadSinglePDF(java.lang.String)
     */
    @Override
    public PictoFamilyRepresentation downloadSinglePDF(String refnum) {
        logger.info(" Start getting the picto family object for download pdf");
        logger.info("Reference Number Passed in Single PDF Download" + refnum);
        String contentOfJpqlQuery = "select distinct objectPic from PictoFamily objectPic where objectPic.referenceNum = :ref";
        PictoFamilyRepresentation pictoFamilyRepresentation = new PictoFamilyRepresentation();
        try {
            TypedQuery<PictoFamily> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoFamily.class);
            typedQuery.setParameter("ref", refnum);
            PictoFamily picFamily = typedQuery.getSingleResult();
            pictoFamilyAssembler.doAssembleDtoFromAggregate(pictoFamilyRepresentation, picFamily);
            logger.info(" Finish: return the picto family object for single download pdf");
        } catch (Exception e) {
            logger.error("Exception occure while fetching the picto family by refence number for single download PDF:", e);
            pictoFamilyRepresentation = null;
        }

        return pictoFamilyRepresentation;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#downloadMultiPDF(java.lang.String)
     */
    @Override
    public List<PictoFamilyRepresentation> downloadMultiPDF(String refnum) {
        logger.info(" Start getting the picto family object for download MultiSelectpdf in method downloadMultiPDF of JPAPictoFamilyFinder Class");
        logger.info("Reference Number Passed in Multiple Download PDF" + refnum);
        String contentOfJpqlQuery = "select distinct objectPic from PictoFamily objectPic where objectPic.referenceNum IN (" + refnum + ")";
        List<PictoFamilyRepresentation> picFamilyList = new ArrayList<PictoFamilyRepresentation>();

        try {
            TypedQuery<PictoFamily> typedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoFamily.class);
            List<PictoFamily> picFamily = typedQuery.getResultList();

            if (picFamily != null && picFamily.size() >= 0) {
                for (PictoFamily l_Picto : picFamily) {
                    PictoFamilyRepresentation pictoFamilyRepresentation = new PictoFamilyRepresentation();
                    pictoFamilyAssembler.doAssembleDtoFromAggregate(pictoFamilyRepresentation, l_Picto);
                    picFamilyList.add(pictoFamilyRepresentation);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the picto family by refence number for Multiple download PDF:", e);
            picFamilyList = null;
        }

        return picFamilyList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#getAllTypes()
     */
    @Override
    public List<TypeRepresentation> getAllTypes() {
        logger.info("Start: Query to get TypesSuper Category");

        String contentOfJpqlQuery = "select distinct objectPic from Type objectPic";

        List<TypeRepresentation> listType = new ArrayList<TypeRepresentation>();
        try {
            TypedQuery<Type> typedQuery = entityManager.createQuery(contentOfJpqlQuery, Type.class);
            List<Type> resultTypeList = typedQuery.getResultList();
            if (resultTypeList != null && resultTypeList.size() >= 0) {
                for (Type l_Type : resultTypeList) {
                    TypeRepresentation typeRepresentation = new TypeRepresentation();
                    typeAssembler.doAssembleDtoFromAggregate(typeRepresentation, l_Type);
                    listType.add(typeRepresentation);

                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the super category:", e);
            listType = null;
        }
        logger.info(" Finish: return the all super category");
        return listType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#getMaxVersionForReference()
     */
    @Override
    public Long getMaxVersionForReference() {

        Query query = entityManager.createNativeQuery(PictoConstants.MAX_REFERENCE_NUMBER_QUERY);

        return ((BigInteger) query.getSingleResult()).longValue();

    }

    /**
     * Compare color.
     * 
     * @param allColors the all colors
     * @param selectedColors the selected colors
     * @return the list
     */
    public List<ColorRepresentation> compareColor(List<ColorRepresentation> allColors, List<ColorRepresentation> selectedColors) {
        List<ColorRepresentation> updatedColors = new ArrayList<ColorRepresentation>();

        for (ColorRepresentation temp : allColors) {
            ColorRepresentation newObj = new ColorRepresentation(temp);
            updatedColors.add(newObj);
        }

        for (ColorRepresentation colorAll : updatedColors) {
            for (ColorRepresentation activeColor : selectedColors) {
                if (colorAll.getColor().equals(activeColor.getColor())) {
                    colorAll.setFlag(Boolean.TRUE);
                    break;
                }

            }
        }

        return updatedColors;

    }

    /**
     * Gets the admin list.
     *
     * @param pictoid the pictoid
     * @return the admin list
     */
    public StringBuilder getAdminList(Long pictoid) {
        List<String> workAdminList = pictoClientFinder.getWorkingAdminList(pictoid);
        StringBuilder str = new StringBuilder();
        for (String adminList : workAdminList) {
            if (str.length() == 0) {
                str.append(adminList);
            } else {
                str.append(", ").append(adminList);
            }

        }
        return str;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#removeFavorite(java.lang.Long, java.lang.Long)
     */
    @Override
    public void removeFavorite(Long userId, Long pictoFamilyId) {
        Query query = entityManager.createNativeQuery("delete from PV2QTFAV where USR_ID = :user and PFM_ID = :picto");
        query.setParameter("user", userId);
        query.setParameter("picto", pictoFamilyId);
        query.executeUpdate();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#getFavPictoIDbyUser(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigInteger> getFavPictoIDbyUser(Long userId) {
        Query query = entityManager.createNativeQuery("select PFM_ID from PV2QTFAV where USR_ID = :user");

        query.setParameter("user", userId);
        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.pictofamily.PictoFamilyFinder#getCartPictoIDbyUser(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigInteger> getCartPictoIDbyUser(Long userId) {
        Query query = entityManager.createNativeQuery("select PIC_ID from PV2QTSHP where USR_ID =:user");
        query.setParameter("user", userId);
        return query.getResultList();

    }
}
