package com.inetpsa.pv2.rest.picto;

import java.io.File;
import java.util.Date;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.image.ImageType;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.imagetype.ImageTypeAssembler;
import com.inetpsa.pv2.rest.imagetype.ImageTypeRepresentation;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyAssemblerHelper;
import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;
import com.inetpsa.pv2.rest.user.UserAssembler;
import com.inetpsa.pv2.rest.user.UserRepresentation;

/**
 * The Class PictoAssembler.
 */
public class PictoAssembler extends BaseAssembler<Picto, PictoRepresentation> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(PictoAssembler.class);

    /** The image type assembler. */
    @Inject
    private ImageTypeAssembler imageTypeAssembler;

    /** The user assembler. */
    @Inject
    private UserAssembler userAssembler;

    /** The host url. */
    @Configuration("com.inetpsa.pv2.host.hostUrl")
    private String hostUrl;

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(PictoRepresentation targetDto, Picto sourceEntity) {
        if (targetDto != null && sourceEntity != null) {
            try {
                targetDto.setId(sourceEntity.getEntityId());
                targetDto.setVariantType(sourceEntity.getVariantType());
                targetDto.setPictoUrl(sourceEntity.getPictoUrl());
                targetDto.setIsFrontagePicto(sourceEntity.getIsFrontagePicto());
                targetDto.setIsVisible(sourceEntity.getIsVisible());
                targetDto.setCreateDate(sourceEntity.getCreateDate());
                targetDto.setModifyDate(sourceEntity.getModifyDate());
                targetDto.setLastUpdateDate(sourceEntity.getLastUpdateDate());

                User lastUpdUser = sourceEntity.getLastUpdatedUsr();
                if (lastUpdUser != null) {
                    UserRepresentation updateUsrRepresentation = new UserRepresentation();
                    userAssembler.doAssembleDtoFromAggregate(updateUsrRepresentation, lastUpdUser);
                    targetDto.setLastUpdatedUsr(updateUsrRepresentation);
                }

                User lastModifyUser = sourceEntity.getLastModifiedUsr();
                if (lastModifyUser != null) {
                    UserRepresentation modifyUsrRepresentation = new UserRepresentation();
                    userAssembler.doAssembleDtoFromAggregate(modifyUsrRepresentation, lastModifyUser);
                    targetDto.setLastModifiedUsr(modifyUsrRepresentation);
                }

                targetDto.setImageLocation(sourceEntity.getImageLocation());
                String image = sourceEntity.getImageLocation();
                String url;
                if (sourceEntity.getVersion() == null) {
                    url = hostUrl + PictoConstants.GET_IMAGE + tempFileDirectory + image + File.separator + image + "_"
                            + sourceEntity.getVariantType() + File.separator + image + "_" + sourceEntity.getVariantType() + PictoConstants.FILE_JPG;
                } else {
                    url = hostUrl + PictoConstants.GET_IMAGE + tempFileDirectory + image + File.separator + image + "_"
                            + sourceEntity.getVariantType() + "_" + sourceEntity.getVersion() + File.separator + image + "_"
                            + sourceEntity.getVariantType() + "_" + sourceEntity.getVersion() + PictoConstants.FILE_JPG;
                }
                targetDto.setImageUrl(url);

                targetDto.setVersion(sourceEntity.getVersion());
                PictoFamily pictoFamily = sourceEntity.getPictoFamilyID();
                PictoFamilyRepresentation pictoFamilyRep = new PictoFamilyRepresentation();
                PictoFamilyAssemblerHelper.doAssembleDtoFromAggregate(pictoFamilyRep, pictoFamily);
                targetDto.setFamilyID(pictoFamilyRep);

                ImageType imageType = sourceEntity.getImageTypes();
                if (imageType != null) {
                    ImageTypeRepresentation imageTypeRep = new ImageTypeRepresentation();
                    imageTypeAssembler.doAssembleDtoFromAggregate(imageTypeRep, imageType);
                    targetDto.setImageTypes(imageTypeRep);
                }
            } catch (Exception e) {
                logger.error("Error in setting Target DTO in method doAssembleDtoFromAggregate of PictoAssembler class ", e);
            }
        }
    }

    /**
     * Do merge aggregate with dto.
     *
     * @param targetEntity the p target entity
     * @param sourceDto the p source dto
     */
    @Override
    public void doMergeAggregateWithDto(Picto targetEntity, PictoRepresentation sourceDto) {
        if (targetEntity != null && sourceDto != null) {
            try {
                targetEntity.setEntityId(sourceDto.getId());
                targetEntity.setVariantType(sourceDto.getVariantType());
                targetEntity.setPictoUrl(sourceDto.getPictoUrl());
                targetEntity.setIsFrontagePicto(sourceDto.getIsFrontagePicto());
                targetEntity.setIsVisible(sourceDto.getIsVisible());
                if (sourceDto.getCreateDate() == null) {
                    targetEntity.setCreateDate(new Date());
                } else {
                    targetEntity.setCreateDate(sourceDto.getCreateDate());
                }
                targetEntity.setModifyDate(new Date());
                targetEntity.setImageLocation(sourceDto.getImageLocation());
                if (sourceDto.getVersion() != null && sourceDto.getVersion().isEmpty()) {
                    targetEntity.setVersion(null);
                } else {
                    targetEntity.setVersion(sourceDto.getVersion());
                }
                /* SN - GL - 318 - 19-Jul-16 - Start */
                UserRepresentation userRepresentation = sourceDto.getLastModifiedUsr();
                if (userRepresentation != null) {
                    User user = new User();
                    userAssembler.mergeAggregateWithDto(user, userRepresentation);
                    targetEntity.setLastModifiedUsr(user);
                }
                /* SN - GL - 318 - 19-Jul-16 - End */

            } catch (Exception e) {
                logger.error("Error in setting Target DTO in method doMergeAggregateWithDto of PictoAssembler class ", e);
            }

        }

    }

    /**
     * Assembler for create picto.
     *
     * @param targetEntity the p target entity
     * @param sourceDto the p source dto
     * @param refnum the refnum
     * @param name the name
     */
    public void doMergeAggregateWithDtoCreate(Picto targetEntity, CreatePictoRepresentation sourceDto, String refnum, String name, String refCharte) {
        if (targetEntity != null && sourceDto != null) {
            try {

                targetEntity.setVariantType(sourceDto.getVariantType());
                targetEntity.setIsFrontagePicto(true);
                targetEntity.setCreateDate(new Date());
                targetEntity.setModifyDate(new Date());
                if (sourceDto.getVersion() != null && sourceDto.getVersion().isEmpty()) {
                    targetEntity.setVersion(null);
                } else {
                    targetEntity.setVersion(sourceDto.getVersion());
                }

                if (PictoConstants.VISIBLE.equals(sourceDto.getIsVisible())) {
                    targetEntity.setIsVisible(true);
                }
                if (PictoConstants.INVISIBLE.equals(sourceDto.getIsVisible())) {
                    targetEntity.setIsVisible(false);
                }
                /* Fix for JIRA PRPO24006-150  Start */
                if (refCharte != null && !("").equals(refCharte)) {
                    targetEntity.setImageLocation(refnum + "_" + refCharte + name);
                } else
                targetEntity.setImageLocation(refnum + "_" + name);
                 
                /* SN - GL - 31 - 25-Jul-16 - Start */
                ImageTypeRepresentation imageRepresentation = new ImageTypeRepresentation();
                imageRepresentation.setImageAIWork(true);
                ImageType image = new ImageType();
                imageTypeAssembler.doMergeAggregateWithDto(image, imageRepresentation);
                image.setPictoId(targetEntity);
                targetEntity.setImageTypes(image);
                /* SN - GL - 316 - 25-Jul-16 - End */

            } catch (Exception e) {
                logger.error("Error in setting Target DTO in method doMergeAggregateWithDto of PictoAssembler class ", e);
            }

        }

    }

}
