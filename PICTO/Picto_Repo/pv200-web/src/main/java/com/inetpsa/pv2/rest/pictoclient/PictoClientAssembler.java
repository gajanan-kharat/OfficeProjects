package com.inetpsa.pv2.rest.pictoclient;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.pv2.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.picto.PictoAssembler;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.user.UserAssembler;
import com.inetpsa.pv2.rest.user.UserRepresentation;

/**
 * The Class PictoClientAssembler.
 */
public class PictoClientAssembler extends BaseAssembler<PictoClient, PictoClientRepresentation> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(PictoClientAssembler.class);

    /** The picto assembler. */
    @Inject
    private PictoAssembler pictoAssembler;

    /** The user assembler. */
    @Inject
    private UserAssembler userAssembler;

    /**
     * Do assemble dto from aggregate.
     *
     * @param targetDto the p target dto
     * @param sourceEntity the p source entity
     */
    @Override
    public void doAssembleDtoFromAggregate(PictoClientRepresentation targetDto, PictoClient sourceEntity) {
        if (targetDto != null && sourceEntity != null) {
            try {
                targetDto.setId(sourceEntity.getEntityId());
                targetDto.setDownloadDate(sourceEntity.getDownloadDate());
                targetDto.setDownloadFlag(sourceEntity.getDownloadFlag());
                targetDto.setIsOpenLocalImg(sourceEntity.getIsOpenLocalImg());
                Picto picto = sourceEntity.getPicto();
                PictoRepresentation pictoRep = new PictoRepresentation();
                pictoAssembler.doAssembleDtoFromAggregate(pictoRep, picto);
                targetDto.setPictoId(pictoRep);

                User user = sourceEntity.getUserId();
                UserRepresentation userRep = new UserRepresentation();
                userAssembler.doAssembleDtoFromAggregate(userRep, user);
                targetDto.setUserId(userRep);
            } catch (Exception e) {
                logger.error("Error in setting Target DTO in method doAssembleDtoFromAggregate of PictoClientAssembler class ", e);
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
    protected void doMergeAggregateWithDto(PictoClient targetEntity, PictoClientRepresentation sourceDto) {

    }

}
