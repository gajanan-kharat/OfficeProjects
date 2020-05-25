/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppFactory;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffFactory;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.rest.category.CategoryAssembler;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.clasz.ClaszAssembler;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelAssembler;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeAssembler;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyAssembler;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class FactorCoefficentListAssembler.
 */
public class FactorCoefficentListAssembler extends BaseAssembler<FactorCoefficentList, FactorCoefficentListRepresentation> {

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The factor coefficent assembler. */
	@Inject
	private FactorCoefficentsAssembler factorCoefficentAssembler;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The factor coefficent factory. */
	@Inject
	private FactorCoeffFactory factorCoefficentFactory;

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

	/** The factor coeff app type assembler. */
	@Inject
	private FactorCoeffAppTypeAssembler factorCoeffAppTypeAssembler;

	/** The factor coeff app type factory. */
	@Inject
	private FactorCoeffAppFactory factorCoeffAppTypeFactory;

	/** The clasz factory. */
	@Inject
	private ClaszFactory claszFactory;

	/** The clasz assembler. */
	@Inject
	private ClaszAssembler claszAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FactorCoefficentList targetEntity, FactorCoefficentListRepresentation sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());

		EmissionStandardRepresentation entity = sourceDto.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);

		targetEntity.setEmissionStandard(newObj);
		if (sourceDto.getFactorOrCoeffiecients() != null && !sourceDto.getFactorOrCoeffiecients().isEmpty()) {
			List<FactorCoefficentsRepresentation> dataItems = sourceDto.getFactorOrCoeffiecients();
			List<FactorCoefficents> dataList = new ArrayList<FactorCoefficents>();
			for (FactorCoefficentsRepresentation dataItem : dataItems) {
				FactorCoefficents data = factorCoefficentFactory.createFactorCoefficents();
				factorCoefficentAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setFcList(targetEntity);
				dataList.add(data);

			}
			targetEntity.setFactorOrCoeffiecients(dataList);

		}
		if (sourceDto.getCategories() != null && !sourceDto.getCategories().isEmpty()) {
			List<CategoryRepresentation> dataItems = sourceDto.getCategories();
			Set<Category> dataList = new HashSet<>();
			for (CategoryRepresentation dataItem : dataItems) {
				Category data = categoryFactory.createCategory();
				categoryAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setCategories(dataList);
		}

		if (sourceDto.getFuelInjectionTypes() != null && !sourceDto.getFuelInjectionTypes().isEmpty()) {
			List<FuelInjectionTypeRepresentation> dataItems = sourceDto.getFuelInjectionTypes();
			List<FuelInjectionType> dataList = new ArrayList<>();
			for (FuelInjectionTypeRepresentation dataItem : dataItems) {
				FuelInjectionType data = fuelInjectionTypeFactory.createFuelInjctnType();
				fuelInjectionTypeAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFuelInjectionTypes(dataList);
		}

		if (sourceDto.getFuels() != null && !sourceDto.getFuels().isEmpty()) {
			List<FuelRepresentation> dataItems = sourceDto.getFuels();
			List<Fuel> dataList = new ArrayList<>();
			for (FuelRepresentation dataItem : dataItems) {
				Fuel data = fuelFactory.createFuel();
				fuelAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFuels(dataList);
		}

		if (sourceDto.getVehicleTechnologySet() != null && !sourceDto.getVehicleTechnologySet().isEmpty()) {
			List<VehicleTechnologyRepresentation> dataItems = sourceDto.getVehicleTechnologySet();
			List<VehicleTechnology> dataList = new ArrayList<>();
			for (VehicleTechnologyRepresentation dataItem : dataItems) {
				VehicleTechnology data = vehicleTechnologyFactory.createVehTechnology();
				vehicleTechnologyAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setVehicleTechnologySet(dataList);
		}
		if (sourceDto.getFcApplicationTypes() != null && !sourceDto.getFcApplicationTypes().isEmpty()) {
			Set<FactorCoeffApplicationTypeRepresentation> dataItems = sourceDto.getFcApplicationTypes();
			Set<FactorCoeffApplicationType> dataList = new HashSet<>();
			for (FactorCoeffApplicationTypeRepresentation dataItem : dataItems) {
				FactorCoeffApplicationType data = factorCoeffAppTypeFactory.createFactorCoefficents();
				factorCoeffAppTypeAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setFcApplicationType(dataList);
		}

		if (sourceDto.getClasses() != null && !sourceDto.getClasses().isEmpty()) {
			List<ClaszRepresentation> dataItems = sourceDto.getClasses();
			Set<Clasz> dataList = new HashSet<>();
			for (ClaszRepresentation dataItem : dataItems) {
				Clasz data = claszFactory.createClasz();
				claszAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			targetEntity.setClasses(dataList);
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FactorCoefficentListRepresentation targetDto, FactorCoefficentList sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

		EmissionStandard entity = sourceEntity.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		targetDto.setEmissionStandard(newObj);
		if (sourceEntity.getFactorOrCoeffiecients() != null && !sourceEntity.getFactorOrCoeffiecients().isEmpty()) {
			List<FactorCoefficents> dataItems = sourceEntity.getFactorOrCoeffiecients();
			List<FactorCoefficentsRepresentation> dataList = new ArrayList<>();
			for (FactorCoefficents dataItem : dataItems) {
				FactorCoefficentsRepresentation data = new FactorCoefficentsRepresentation();
				factorCoefficentAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setFcList(targetDto);
				dataList.add(data);

			}

			targetDto.setFactorOrCoeffiecients(dataList);

		}
		if (sourceEntity.getCategories() != null && !sourceEntity.getCategories().isEmpty()) {
			Set<Category> dataItems = sourceEntity.getCategories();
			List<CategoryRepresentation> dataList = new ArrayList<>();
			for (Category dataItem : dataItems) {
				CategoryRepresentation data = new CategoryRepresentation();
				categoryAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setCategories(dataList);
		}

		if (sourceEntity.getFuelInjectionTypes() != null && !sourceEntity.getFuelInjectionTypes().isEmpty()) {
			List<FuelInjectionType> dataItems = sourceEntity.getFuelInjectionTypes();
			List<FuelInjectionTypeRepresentation> dataList = new ArrayList<>();
			for (FuelInjectionType dataItem : dataItems) {
				FuelInjectionTypeRepresentation data = new FuelInjectionTypeRepresentation();
				fuelInjectionTypeAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFuelInjectionTypes(dataList);
		}

		if (sourceEntity.getFuels() != null && !sourceEntity.getFuels().isEmpty()) {
			List<Fuel> dataItems = sourceEntity.getFuels();
			List<FuelRepresentation> dataList = new ArrayList<>();
			for (Fuel dataItem : dataItems) {
				FuelRepresentation data = new FuelRepresentation();
				fuelAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFuels(dataList);
		}

		if (sourceEntity.getVehicleTechnologySet() != null && !sourceEntity.getVehicleTechnologySet().isEmpty()) {
			List<VehicleTechnology> dataItems = sourceEntity.getVehicleTechnologySet();
			List<VehicleTechnologyRepresentation> dataList = new ArrayList<>();
			for (VehicleTechnology dataItem : dataItems) {
				VehicleTechnologyRepresentation data = new VehicleTechnologyRepresentation();
				vehicleTechnologyAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setVehicleTechnologySet(dataList);

		}
		if (sourceEntity.getFcApplicationTypes() != null && !sourceEntity.getFcApplicationTypes().isEmpty()) {
			Set<FactorCoeffApplicationType> dataItems = sourceEntity.getFcApplicationTypes();
			Set<FactorCoeffApplicationTypeRepresentation> dataList = new HashSet<>();
			for (FactorCoeffApplicationType dataItem : dataItems) {
				FactorCoeffApplicationTypeRepresentation data = new FactorCoeffApplicationTypeRepresentation();
				factorCoeffAppTypeAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setFcApplicationTypes(dataList);
		}

		if (sourceEntity.getClasses() != null && !sourceEntity.getClasses().isEmpty()) {
			Set<Clasz> dataItems = sourceEntity.getClasses();
			List<ClaszRepresentation> dataList = new ArrayList<>();
			for (Clasz dataItem : dataItems) {
				ClaszRepresentation data = new ClaszRepresentation();
				claszAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			targetDto.setClasses(dataList);
		}
	}
}
