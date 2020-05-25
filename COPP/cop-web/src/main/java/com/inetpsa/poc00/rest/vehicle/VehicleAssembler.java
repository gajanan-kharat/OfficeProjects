/*
 * Creation : Aug 22, 2016
 */
package com.inetpsa.poc00.rest.vehicle;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.rest.carfactory.CarFactoryAssembler;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.country.CountryAssembler;
import com.inetpsa.poc00.rest.country.CountryRepresentation;

/**
 * The Class VehicleAssembler.
 */
public class VehicleAssembler extends BaseAssembler<Vehicle, VehicleRepresentation> {

    /** The car factory repo. */
    @Inject
    private CarFactoryRepository carFactoryRepo;

    /** The tech case repo. */
    @Inject
    private TechCaseRepository techCaseRepo;

    /** The country assembler. */
    @Inject
    CountryAssembler countryAssembler;

    /** The country factory. */
    @Inject
    CountryFactory countryFactory;

    /** The car factory assembler. */
    @Inject
    CarFactoryAssembler carFactoryAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(VehicleRepresentation targetDto, Vehicle sourceEntity) {
        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setChasisNumber(sourceEntity.getChasisNumber());
        targetDto.setCounterMark(sourceEntity.getCounterMark());
        targetDto.setRegistrationNumber(sourceEntity.getRegistrationNumber());
        targetDto.setVin(sourceEntity.getVin());
        targetDto.setDescription(sourceEntity.getDescription());
        targetDto.setModelYear(sourceEntity.getModelYear());
        targetDto.setmCO2I(sourceEntity.getmCO2I());
        targetDto.setFlowType(sourceEntity.getFlowType());
        targetDto.setPriorityIndicator(sourceEntity.getPriorityIndicator());
        targetDto.setTechnicalCaseId(sourceEntity.getTechnicalCase().getEntityId());
        if (sourceEntity.getTechnicalCase().getTvv().getFuel() != null)
            targetDto.setFuelLabel(sourceEntity.getTechnicalCase().getTvv().getFuel().getLabel());
        if (sourceEntity.getTechnicalCase().getTvv().getProjectCodeFamily() != null)
            targetDto.setProjectFamilyLabel(sourceEntity.getTechnicalCase().getTvv().getProjectCodeFamily().getProjectCodeLabel() + "-"
                    + sourceEntity.getTechnicalCase().getTvv().getProjectCodeFamily().getVehicleFamilyLabel());
        if (sourceEntity.getDestinationCountry() != null) {
            CountryRepresentation countryRep = new CountryRepresentation();
            countryAssembler.doAssembleDtoFromAggregate(countryRep, sourceEntity.getDestinationCountry());
            targetDto.setDestinationCountry(countryRep);
        }
        if (sourceEntity.getCarfactory() != null) {
            CarFactoryRepresentation carFactoryRep = new CarFactoryRepresentation();
            carFactoryAssembler.doAssembleDtoFromAggregate(carFactoryRep, sourceEntity.getCarfactory());
            targetDto.setCarFactoryrepresentation(carFactoryRep);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(Vehicle targetEntity, VehicleRepresentation sourceDto) {

        targetEntity.setEntityId(sourceDto.getEntityId());
        targetEntity.setCounterMark(sourceDto.getCounterMark());
        targetEntity.setChasisNumber(sourceDto.getChasisNumber());
        targetEntity.setRegistrationNumber(sourceDto.getRegistrationNumber());
        targetEntity.setModelYear(sourceDto.getModelYear());
        targetEntity.setmCO2I(sourceDto.getmCO2I());
        targetEntity.setVin(sourceDto.getVin());
        targetEntity.setDescription(sourceDto.getDescription());
        targetEntity.setFlowType(sourceDto.getFlowType());
        targetEntity.setPriorityIndicator(sourceDto.getPriorityIndicator());
        if (sourceDto.getCarFactoryId() != null) {
            targetEntity.setCarfactory(carFactoryRepo.load(sourceDto.getCarFactoryId()));
        }
        if (sourceDto.getDestinationCountry() != null) {
            Country country = countryFactory.createCountry();
            countryAssembler.doMergeAggregateWithDto(country, sourceDto.getDestinationCountry());
            targetEntity.setDestinationCountry(country);
        }
        targetEntity.setTechnicalCase(techCaseRepo.load(sourceDto.getTechnicalCaseId()));

    }

}
