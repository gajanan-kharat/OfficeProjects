package com.inetpsa.pv2.rest.specificdrawing;

import java.io.File;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;
import org.seedstack.seed.Configuration;

/**
 * The Class SpecificDrawingAssembler.
 */
public class SpecificDrawingAssembler extends BaseAssembler<SpecificDrawing, SpecificDrawingRepresentation> {

    /** The host url. */
    @Configuration("com.inetpsa.pv2.host.hostUrl")
    private String hostUrl;

    /** The m temp file directory. */
    @Configuration("com.inetpsa.pv2.web.tempFileDirectory")
    private String tempFileDirectory;

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(SpecificDrawingRepresentation targetDto, SpecificDrawing sourceEntity) {
        targetDto.setId(sourceEntity.getEntityId());
        targetDto.setName(sourceEntity.getName());
        targetDto.setCommentsEN(sourceEntity.getCommentsEN());
        targetDto.setCommentsFR(sourceEntity.getCommentsFR());
        targetDto.setSpecificDrawFile(sourceEntity.getSpecificDrawFile());
        /** PRP024006-134: GL modification STARTS Date:08/12/2016 */
        String url = hostUrl + PictoConstants.GET_IMAGE + tempFileDirectory + sourceEntity.getFamilyId().getPictos().get(0).getImageLocation()
                + File.separator + PictoConstants.SPECIFICDRAW + File.separator + sourceEntity.getSpecificDrawFile();
        /** PRP024006-134: GL modification ENDS Date:08/12/2016 */
        targetDto.setSpecficDrawUrl(url);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(SpecificDrawing targetEntity, SpecificDrawingRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getId());
        targetEntity.setName(sourceDto.getName());
        targetEntity.setCommentsEN(sourceDto.getCommentsEN());
        targetEntity.setCommentsFR(sourceDto.getCommentsFR());
        targetEntity.setSpecificDrawFile(sourceDto.getSpecificDrawFile());
    }
}
