/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.rest.emissionstandard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.rest.category.CategoryAssembler;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelAssembler;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeAssembler;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyAssembler;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class EmissionStandardAssembler.
 */
public class EmissionStandardAssembler extends BaseAssembler<EmissionStandard, EmissionStandardRepresentation> {

	/** The category assembler. */
	@Inject
	private CategoryAssembler categoryAssembler;

	/** The category factory. */
	@Inject
	private CategoryFactory categoryFactory;

	/** The fuel assembler. */
	@Inject
	private FuelAssembler fuelAssembler;

	/** The fuel factory. */
	@Inject
	private FuelFactory fuelFactory;

	/** The fuel injection type assembler. */
	@Inject
	private FuelInjectionTypeAssembler fuelInjectionTypeAssembler;

	/** The fuel injection type factory. */
	@Inject
	private FuelInjctnTypeFactory fuelInjectionTypeFactory;

	/** The vehicle technology assembler. */
	@Inject
	private VehicleTechnologyAssembler vehicleTechnologyAssembler;

	/** The vehicle technology factory. */
	@Inject
	private VehicleTechFactory vehicleTechnologyFactory;

	/** The status assembler. */
	@Inject
	private StatusRepresentationAssembler statusAssembler;

	/** The status factory. */
	@Inject
	private StatusFactory statusFactory;

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(EmissionStandardRepresentation targetDto, EmissionStandard sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setEsLabel(sourceEntity.getEsLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		targetDto.setDisplayLabel(sourceEntity.getEsLabel() + " " + sourceEntity.getStatus().getGuiLabel() + " V" + sourceEntity.getVersion());
		targetDto.setDisplayLabelTVV(sourceEntity.getEsLabel() + " V" + sourceEntity.getVersion());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleId(sourceEntity.getGenomeTvvRule().getEntityId());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());

		if (sourceEntity.getCategories() != null && !sourceEntity.getCategories().isEmpty()) {
			Set<Category> dataItems = sourceEntity.getCategories();
			List<CategoryRepresentation> dataList = new ArrayList<CategoryRepresentation>();
			for (Category dataItem : dataItems) {
				CategoryRepresentation data = new CategoryRepresentation();
				categoryAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setCategories(dataList);
		}

		if (sourceEntity.getFuelInjectionTypes() != null && !sourceEntity.getFuelInjectionTypes().isEmpty()) {
			Set<FuelInjectionType> dataItems = sourceEntity.getFuelInjectionTypes();
			List<FuelInjectionTypeRepresentation> dataList = new ArrayList<FuelInjectionTypeRepresentation>();
			for (FuelInjectionType dataItem : dataItems) {
				FuelInjectionTypeRepresentation data = new FuelInjectionTypeRepresentation();
				fuelInjectionTypeAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFuelInjectionTypes(dataList);
		}

		if (sourceEntity.getFuels() != null && !sourceEntity.getFuels().isEmpty()) {
			Set<Fuel> dataItems = sourceEntity.getFuels();
			List<FuelRepresentation> dataList = new ArrayList<FuelRepresentation>();
			for (Fuel dataItem : dataItems) {
				FuelRepresentation data = new FuelRepresentation();
				fuelAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFuels(dataList);
		}

		if (sourceEntity.getVehicleTechnologySet() != null && !sourceEntity.getVehicleTechnologySet().isEmpty()) {
			Set<VehicleTechnology> dataItems = sourceEntity.getVehicleTechnologySet();
			List<VehicleTechnologyRepresentation> dataList = new ArrayList<VehicleTechnologyRepresentation>();
			for (VehicleTechnology dataItem : dataItems) {
				VehicleTechnologyRepresentation data = new VehicleTechnologyRepresentation();
				vehicleTechnologyAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setVehicleTechnologySet(dataList);
		}

		if (sourceEntity.getStatus() != null) {

			StatusRepresentation data = new StatusRepresentation();
			statusAssembler.doAssembleDtoFromAggregate(data, sourceEntity.getStatus());

			targetDto.setStatus(data);

		}

		/*if (sourceEntity.getEmissionStdDepTDLists() != null && sourceEntity.getEmissionStdDepTDLists().isEmpty()) {
			Set<EmissionStdDepTDL> dataItems = sourceEntity.getEmissionStdDepTDLists();
			List<EmsDepTDLRepresentation> dataList = new ArrayList<EmsDepTDLRepresentation>();
			for (EmissionStdDepTDL dataItem : dataItems) {
				EmsDepTDLRepresentation data = new EmsDepTDLRepresentation();
				emissionStdDepTDLAssembler.doAssembleDtoFromAggregate(data, dataItem);
		
				dataList.add(data);
		
			}
			targetDto.setEmsDepTDLists(dataList);
		}*/

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(EmissionStandard targetEntity, EmissionStandardRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setEsLabel(sourceDto.getEsLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		if (sourceDto.getRuleId() != null)
			targetEntity.setGenomeTvvRule(sourceDto.getTvvRuleId());
		if (sourceDto.getReglementationTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getReglementationTvvRuleId()));
		if (sourceDto.getCategories() != null && !sourceDto.getCategories().isEmpty()) {
			List<CategoryRepresentation> dataItems = sourceDto.getCategories();
			Set<Category> dataList = new HashSet<Category>();
			// long i = 0;
			for (CategoryRepresentation dataItem : dataItems) {
				Category data = categoryFactory.createCategory();
				categoryAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);
				// i++;

			}
			targetEntity.setCategories(dataList);
		}

		if (sourceDto.getFuelInjectionTypes() != null && !sourceDto.getFuelInjectionTypes().isEmpty()) {
			List<FuelInjectionTypeRepresentation> dataItems = sourceDto.getFuelInjectionTypes();
			Set<FuelInjectionType> dataList = new HashSet<FuelInjectionType>();
			for (FuelInjectionTypeRepresentation dataItem : dataItems) {
				FuelInjectionType data = fuelInjectionTypeFactory.createFuelInjctnType();
				fuelInjectionTypeAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFuelInjectionTypes(dataList);
		}

		if (sourceDto.getFuels() != null && !sourceDto.getFuels().isEmpty()) {
			List<FuelRepresentation> dataItems = sourceDto.getFuels();
			Set<Fuel> dataList = new HashSet<Fuel>();
			for (FuelRepresentation dataItem : dataItems) {
				Fuel data = fuelFactory.createFuel();
				fuelAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFuels(dataList);
		}

		if (sourceDto.getVehicleTechnologySet() != null && !sourceDto.getVehicleTechnologySet().isEmpty()) {
			List<VehicleTechnologyRepresentation> dataItems = sourceDto.getVehicleTechnologySet();
			Set<VehicleTechnology> dataList = new HashSet<VehicleTechnology>();
			for (VehicleTechnologyRepresentation dataItem : dataItems) {
				VehicleTechnology data = vehicleTechnologyFactory.createVehTechnology();
				vehicleTechnologyAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setVehicleTechnologySet(dataList);
		}

		if (sourceDto.getStatus() != null) {

			Status data = statusFactory.createStatus();
			statusAssembler.doMergeAggregateWithDto(data, sourceDto.getStatus());

			targetEntity.setStatus(data);

		}

	}

}
