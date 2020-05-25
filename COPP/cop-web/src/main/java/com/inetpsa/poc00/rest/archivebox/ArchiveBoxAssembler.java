/*
 * Creation : Feb 13, 2017
 */
package com.inetpsa.poc00.rest.archivebox;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.rest.fuel.FuelAssembler;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyAssembler;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestReprAssembler;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileAssembler;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class ArchiveBoxAssembler.
 */
public class ArchiveBoxAssembler extends BaseAssembler<ArchiveBox, ArchiveBoxRepresentation> {

    /** The project code family assembler. */
    @Inject
    private ProjectCodeFamilyAssembler projectCodeFamilyAssembler;

    /** The fuel assembler. */
    @Inject
    private FuelAssembler fuelAssembler;

    /** The type of test assembler. */
    @Inject
    private TypeOfTestReprAssembler typeOfTestAssembler;

    /** The vehicle file assembler. */
    @Inject
    private VehicleFileAssembler vehicleFileAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(ArchiveBoxRepresentation targetDto, ArchiveBox sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setArchiveBoxNumber(sourceEntity.getArchiveBoxNumber());
        targetDto.setModelYear(sourceEntity.getModelYear());
        targetDto.setOpeningDate(sourceEntity.getOpeningDate());
        targetDto.setIsOpen(sourceEntity.getIsOpen());

        ProjectCodeFamilyRepresentation pcfRepresentation = new ProjectCodeFamilyRepresentation();
        projectCodeFamilyAssembler.doAssembleDtoFromAggregate(pcfRepresentation, sourceEntity.getProjectCodeFamily());
        targetDto.setProjectCodeFamilyReprsentation(pcfRepresentation);

        FuelRepresentation fuelRepresentation = new FuelRepresentation();
        fuelAssembler.doAssembleDtoFromAggregate(fuelRepresentation, sourceEntity.getFuel());
        targetDto.setFuelRepresentation(fuelRepresentation);

        TypeOfTestRepresentation totRepresentation = new TypeOfTestRepresentation();
        typeOfTestAssembler.doAssembleDtoFromAggregate(totRepresentation, sourceEntity.getTypeOfTest());
        targetDto.setTypeOfTestRepresentation(totRepresentation);

        if (sourceEntity.getVehicleFiles() != null) {
            List<VehicleFileRepresentation> vehicleFileRepList = new ArrayList<>();

            for (VehicleFile vehicleFile : sourceEntity.getVehicleFiles()) {
                VehicleFileRepresentation vehicleFileRepresentation = new VehicleFileRepresentation();
                vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRepresentation, vehicleFile);
                vehicleFileRepList.add(vehicleFileRepresentation);

            }
            targetDto.setVehicleFilesRepresentation(vehicleFileRepList);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(ArchiveBox arg0, ArchiveBoxRepresentation arg1) {

    }

}
