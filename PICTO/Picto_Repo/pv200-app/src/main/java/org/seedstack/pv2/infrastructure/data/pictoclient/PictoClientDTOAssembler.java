/*
 * Creation : Apr 6, 2016
 */
package org.seedstack.pv2.infrastructure.data.pictoclient;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.pv2.infrastructure.data.user.UserDTO;
import org.seedstack.pv2.infrastructure.data.user.UserDTOAssembler;

public class PictoClientDTOAssembler extends BaseAssembler<PictoClient, PictoClientDTO> {
    @Inject
    private UserDTOAssembler m_userDTOAssembler;

    @Override
    protected void doAssembleDtoFromAggregate(PictoClientDTO targetDto, PictoClient sourceAggregate) {
        targetDto.setId(sourceAggregate.getEntityId());
        targetDto.setDownloadDate(sourceAggregate.getDownloadDate());
        targetDto.setDownloadFlag(sourceAggregate.getDownloadFlag());
        targetDto.setPicId(sourceAggregate.getPicto());
        targetDto.setUsrId(sourceAggregate.getUserId());
        UserDTO userDTO = new UserDTO();

        m_userDTOAssembler.assembleDtoFromAggregate(userDTO, sourceAggregate.getUserId());
        // targetDto.setFamilyID(sourceAggregate.getPictoFamilyID());
    }

    @Override
    protected void doMergeAggregateWithDto(PictoClient targetAggregate, PictoClientDTO sourceDto) {
        targetAggregate.setEntityId(sourceDto.getId());
        targetAggregate.setDownloadDate(sourceDto.getDownloadDate());
        targetAggregate.setDownloadFlag(sourceDto.getDownloadFlag());
        targetAggregate.setPicto(sourceDto.getPicId());
        targetAggregate.setUserId(sourceDto.getUsrId());

    }

}
