/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

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
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
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
 * The Class PollutantGasLimitListAssembler.
 */
public class PollutantGasLimitListAssembler extends BaseAssembler<PollutantGasLimitList, PollutantGasLimitListRepresentation> {
	
	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;
	
	/** The pollutant gas limit assembler. */
	@Inject
	private PollutantGasLimitAssembler pollutantGasLimitAssembler;
	
	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;
	
	/** The pollutant gas limit factory. */
	@Inject
	private PollutantGasLimitFactory pollutantGasLimitFactory;

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
	
	/** The clasz factory. */
	@Inject
	private ClaszFactory claszFactory;
	
	/** The clasz assembler. */
	@Inject
	private ClaszAssembler claszAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PollutantGasLimitListRepresentation pollutantGasLimitListRepresentation, PollutantGasLimitList pollutantGasLimitList) {
		pollutantGasLimitListRepresentation.setEntityId(pollutantGasLimitList.getEntityId());
		pollutantGasLimitListRepresentation.setDescription(pollutantGasLimitList.getDescription());
		pollutantGasLimitListRepresentation.setLabel(pollutantGasLimitList.getLabel());
		pollutantGasLimitListRepresentation.setVersion(pollutantGasLimitList.getVersion());

		EmissionStandard entity = pollutantGasLimitList.getEmissionStandard();
		EmissionStandardRepresentation newObj = new EmissionStandardRepresentation();
		emissionStandardAssembler.doAssembleDtoFromAggregate(newObj, entity);

		pollutantGasLimitListRepresentation.setEmissionStandard(newObj);
		if (pollutantGasLimitList.getPollutantGasLimit() != null && !pollutantGasLimitList.getPollutantGasLimit().isEmpty()) {
			List<PollutantGasLimit> dataItems = pollutantGasLimitList.getPollutantGasLimit();
			List<PollutantGasLimitRepresentation> dataList = new ArrayList<>();
			for (PollutantGasLimit dataItem : dataItems) {
				PollutantGasLimitRepresentation data = new PollutantGasLimitRepresentation();
				pollutantGasLimitAssembler.doAssembleDtoFromAggregate(data, dataItem);
				data.setPollutantGasLimitList(pollutantGasLimitListRepresentation);
				dataList.add(data);

			}

			pollutantGasLimitListRepresentation.setPollutantGasLimit(dataList);

		}
		if (pollutantGasLimitList.getCategories() != null && !pollutantGasLimitList.getCategories().isEmpty()) {
			Set<Category> dataItems = pollutantGasLimitList.getCategories();
			List<CategoryRepresentation> dataList = new ArrayList<>();
			for (Category dataItem : dataItems) {
				CategoryRepresentation data = new CategoryRepresentation();
				categoryAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			pollutantGasLimitListRepresentation.setCategories(dataList);
		}

		if (pollutantGasLimitList.getFuelInjectionTypes() != null && !pollutantGasLimitList.getFuelInjectionTypes().isEmpty()) {
			Set<FuelInjectionType> dataItems = pollutantGasLimitList.getFuelInjectionTypes();
			List<FuelInjectionTypeRepresentation> dataList = new ArrayList<>();
			for (FuelInjectionType dataItem : dataItems) {
				FuelInjectionTypeRepresentation data = new FuelInjectionTypeRepresentation();
				fuelInjectionTypeAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			pollutantGasLimitListRepresentation.setFuelInjectionTypes(dataList);
		}

		if (pollutantGasLimitList.getFuels() != null && !pollutantGasLimitList.getFuels().isEmpty()) {
			Set<Fuel> dataItems = pollutantGasLimitList.getFuels();
			List<FuelRepresentation> dataList = new ArrayList<>();
			for (Fuel dataItem : dataItems) {
				FuelRepresentation data = new FuelRepresentation();
				fuelAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			pollutantGasLimitListRepresentation.setFuels(dataList);
		}

		if (pollutantGasLimitList.getVehicleTechnologySet() != null && !pollutantGasLimitList.getVehicleTechnologySet().isEmpty()) {
			Set<VehicleTechnology> dataItems = pollutantGasLimitList.getVehicleTechnologySet();
			List<VehicleTechnologyRepresentation> dataList = new ArrayList<>();
			for (VehicleTechnology dataItem : dataItems) {
				VehicleTechnologyRepresentation data = new VehicleTechnologyRepresentation();
				vehicleTechnologyAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			pollutantGasLimitListRepresentation.setVehicleTechnologySet(dataList);

		}
		if (pollutantGasLimitList.getClasses() != null && !pollutantGasLimitList.getClasses().isEmpty()) {
			Set<Clasz> dataItems = pollutantGasLimitList.getClasses();
			List<ClaszRepresentation> dataList = new ArrayList<>();
			for (Clasz dataItem : dataItems) {
				ClaszRepresentation data = new ClaszRepresentation();
				claszAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			pollutantGasLimitListRepresentation.setClasses(dataList);
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasLimitList pollutantGasList, PollutantGasLimitListRepresentation pollutantGasListRepresentation) {
		pollutantGasList.setEntityId(pollutantGasListRepresentation.getEntityId());
		pollutantGasList.setDescription(pollutantGasListRepresentation.getDescription());
		pollutantGasList.setLabel(pollutantGasListRepresentation.getLabel());
		pollutantGasList.setVersion(pollutantGasListRepresentation.getVersion());

		EmissionStandardRepresentation entity = pollutantGasListRepresentation.getEmissionStandard();
		EmissionStandard newObj = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(newObj, entity);

		pollutantGasList.setEmissionStandard(newObj);
		if (pollutantGasListRepresentation.getPollutantGasLimit() != null && !pollutantGasListRepresentation.getPollutantGasLimit().isEmpty()) {
			List<PollutantGasLimitRepresentation> dataItems = pollutantGasListRepresentation.getPollutantGasLimit();
			List<PollutantGasLimit> dataList = new ArrayList<>();
			for (PollutantGasLimitRepresentation dataItem : dataItems) {
				PollutantGasLimit data = pollutantGasLimitFactory.createPollutantGasLimit();
				data.setIsDeleted(dataItem.getIsDeleted());
				pollutantGasLimitAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setPollutantGasLimitList(pollutantGasList);
				dataList.add(data);

			}
			pollutantGasList.setPollutantGasLimit(dataList);

		}
		if (pollutantGasListRepresentation.getCategories() != null && !pollutantGasListRepresentation.getCategories().isEmpty()) {
			List<CategoryRepresentation> dataItems = pollutantGasListRepresentation.getCategories();
			Set<Category> dataList = new HashSet<>();
			for (CategoryRepresentation dataItem : dataItems) {
				Category data = categoryFactory.createCategory();
				categoryAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			pollutantGasList.setCategories(dataList);
		}

		if (pollutantGasListRepresentation.getFuelInjectionTypes() != null && !pollutantGasListRepresentation.getFuelInjectionTypes().isEmpty()) {
			List<FuelInjectionTypeRepresentation> dataItems = pollutantGasListRepresentation.getFuelInjectionTypes();
			Set<FuelInjectionType> dataList = new HashSet<>();
			for (FuelInjectionTypeRepresentation dataItem : dataItems) {
				FuelInjectionType data = fuelInjectionTypeFactory.createFuelInjctnType();
				fuelInjectionTypeAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			pollutantGasList.setFuelInjectionTypes(dataList);
		}

		if (pollutantGasListRepresentation.getFuels() != null && !pollutantGasListRepresentation.getFuels().isEmpty()) {
			List<FuelRepresentation> dataItems = pollutantGasListRepresentation.getFuels();
			Set<Fuel> dataList = new HashSet<>();
			for (FuelRepresentation dataItem : dataItems) {
				Fuel data = fuelFactory.createFuel();
				fuelAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			pollutantGasList.setFuels(dataList);
		}

		if (pollutantGasListRepresentation.getVehicleTechnologySet() != null && !pollutantGasListRepresentation.getVehicleTechnologySet().isEmpty()) {
			List<VehicleTechnologyRepresentation> dataItems = pollutantGasListRepresentation.getVehicleTechnologySet();
			Set<VehicleTechnology> dataList = new HashSet<>();
			for (VehicleTechnologyRepresentation dataItem : dataItems) {
				VehicleTechnology data = vehicleTechnologyFactory.createVehTechnology();
				vehicleTechnologyAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			pollutantGasList.setVehicleTechnologySet(dataList);
		}

		if (pollutantGasListRepresentation.getClasses() != null && !pollutantGasListRepresentation.getClasses().isEmpty()) {
			List<ClaszRepresentation> dataItems = pollutantGasListRepresentation.getClasses();
			Set<Clasz> dataList = new HashSet<>();
			for (ClaszRepresentation dataItem : dataItems) {
				Clasz data = claszFactory.createClasz();
				claszAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			pollutantGasList.setClasses(dataList);
		}

	}

}
