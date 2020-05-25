/*
 * Creation : Aug 26, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.rest.archivebox.ArchiveBoxAssembler;
import com.inetpsa.poc00.rest.archivebox.ArchiveBoxRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleAssembler;
import com.inetpsa.poc00.rest.vehicle.VehicleRepresentation;

/**
 * The Class VehicleFileAssembler.
 */
public class VehicleFileAssembler extends BaseAssembler<VehicleFile, VehicleFileRepresentation> {

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    @Inject
    VehicleAssembler vehicleAssembler;

    @Inject
    ArchiveBoxAssembler archiveBoxAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(VehicleFileRepresentation targetDto, VehicleFile sourceEntity) {
        targetDto.setEntityId(sourceEntity.getEntityId());
        if (sourceEntity.getVehicle().getTechnicalCase() != null && sourceEntity.getVehicle().getTechnicalCase().getTvv() != null) {
            targetDto.setTvvLabel(sourceEntity.getVehicle().getTechnicalCase().getTvv().getLabel());
        }
        targetDto.setReferencesantorin(sourceEntity.getSantorinReference());
        if (sourceEntity.getVehicle().getVin() != null) {
            targetDto.setVin(sourceEntity.getVehicle().getVin());
        }

        if (sourceEntity.getVehicle().getRegistrationNumber() != null && !sourceEntity.getVehicle().getRegistrationNumber().isEmpty()) {
            targetDto.setGuidisplyLabel(sourceEntity.getVehicle().getRegistrationNumber());
            targetDto.setDisplayTitle("N° d’immatriculation");
            targetDto.setGuidisplyLabelForResultScr("Immatriculation");
        } else if (sourceEntity.getVehicle().getChasisNumber() != null && !sourceEntity.getVehicle().getChasisNumber().isEmpty()) {
            targetDto.setGuidisplyLabel(sourceEntity.getVehicle().getChasisNumber());
            targetDto.setDisplayTitle("N° de chassis");
            targetDto.setGuidisplyLabelForResultScr("Chassis");
        } else if (sourceEntity.getVehicle().getCounterMark() != null && !sourceEntity.getVehicle().getCounterMark().isEmpty()) {
            targetDto.setGuidisplyLabel(sourceEntity.getVehicle().getCounterMark());
            targetDto.setDisplayTitle("N° de contremarque");
            targetDto.setGuidisplyLabelForResultScr("Contremarque");
        }
        if (sourceEntity.getVehicleFileStatus() != null && sourceEntity.getVehicleFileStatus().getLabel() != null) {

            targetDto.setVehicleFileStatusLabel(sourceEntity.getVehicleFileStatus().getGuiLabel());

        }
        targetDto.setTypeOfTestLabel(sourceEntity.getTypeOfTest().getLabel());
        if (sourceEntity.getStatisticalDecision() != null) {
            targetDto.setVehFileStatisticalDecision(sourceEntity.getStatisticalDecision());
        }
        if (sourceEntity.getBasket() != null && sourceEntity.getBasket().getUser() != null) {

            String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();
            if (!userId.equalsIgnoreCase(sourceEntity.getBasket().getUser().getUserId())) {
                targetDto.setUserName(sourceEntity.getBasket().getUser().getUserId());
            } else {
                targetDto.setInBasket(true);
            }

        }
        String statisticalDecision = null;
        if (sourceEntity.getStatisticalDecision() != null) {
            statisticalDecision = setStatisticaldecision(sourceEntity);
        }
        targetDto.setVehFileStatisticalDecision(statisticalDecision);
        if (sourceEntity.getVehicle() != null) {
            VehicleRepresentation vehicleRep = new VehicleRepresentation();
            vehicleAssembler.doAssembleDtoFromAggregate(vehicleRep, sourceEntity.getVehicle());
            targetDto.setVehicleRepresentation(vehicleRep);
        }
        if (sourceEntity.getVehicleFileStatus() != null)
            targetDto.setVehicleFileStatusLabelEng(sourceEntity.getVehicleFileStatus().getLabel());
        if (sourceEntity.getArchiveBox() != null) {
            ArchiveBoxRepresentation archiveBoxRepr = new ArchiveBoxRepresentation(sourceEntity.getArchiveBox().getArchiveBoxNumber(),
                    sourceEntity.getArchiveBox().getModelYear(), sourceEntity.getArchiveBox().getProjectCodeFamily(),
                    sourceEntity.getArchiveBox().getFuel());
            targetDto.setArchiveBoxRepresentation(archiveBoxRepr);
        }

        targetDto.setUpdateDate(sourceEntity.getUpdateDate());
    }

    /**
     * Sets the statisticaldecision.
     *
     * @param sourceEntity the source entity
     * @return the string
     */
    private String setStatisticaldecision(VehicleFile sourceEntity) {
        if (sourceEntity.getStatisticalDecision().contains("R")) {
            return "Refusé";
        }
        if (sourceEntity.getStatisticalDecision().contains("I")) {
            return "Incertitude";
        }

        if (sourceEntity.getStatisticalDecision().contains("A")) {
            return "Accepté ";
        }
        if (sourceEntity.getStatisticalDecision().contains("MD")) {
            return "Manque de données";
        }
        return null;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    protected void doMergeAggregateWithDto(VehicleFile targetEntity, VehicleFileRepresentation sourceDto) {
        // DO NOTHING
    }

}
