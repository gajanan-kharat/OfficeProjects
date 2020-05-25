/*
 * Creation : Jun 21, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.List;

import com.inetpsa.poc00.rest.bodywork.BodyWorkRepresentation;
import com.inetpsa.poc00.rest.carbrand.CarBrandRepresentation;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.engine.EngineRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.gearbox.GearBoxRepresentation;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class TvvDataRepresentation.
 */
public class TvvDataRepresentation {

    /** The tvv label. */
    String tvvLabel;

    /** The pc family list. */
    private List<ProjectCodeFamilyRepresentation> pcFamilyList;

    /** The engine list. */
    private List<EngineRepresentation> engineList;

    /** The gear box list. */
    private List<GearBoxRepresentation> gearBoxList;

    /** The car brand list. */
    private List<CarBrandRepresentation> carBrandList;

    /** The emission standard list. */
    private List<EmissionStandardRepresentation> emissionStandardList;

    /** The fuel list. */
    private List<FuelRepresentation> fuelList;

    /** The body worklist. */
    private List<BodyWorkRepresentation> bodyWorklist;

    /** The category list. */
    private List<CategoryRepresentation> categoryList;

    /** The vt list. */
    private List<VehicleTechnologyRepresentation> vtList;

    /** The factory list. */
    private List<CarFactoryRepresentation> factoryList;

    private List<FuelInjectionTypeRepresentation> fuelInjectionList;

    /**
     * Gets the category list.
     * 
     * @return the category list
     */
    public List<CategoryRepresentation> getCategoryList() {
        return categoryList;
    }

    /**
     * Sets the category list.
     * 
     * @param categoryList the new category list
     */
    public void setCategoryList(List<CategoryRepresentation> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Gets the vt list.
     * 
     * @return the vt list
     */
    public List<VehicleTechnologyRepresentation> getVtList() {
        return vtList;
    }

    /**
     * Sets the vt list.
     * 
     * @param vtList the new vt list
     */
    public void setVtList(List<VehicleTechnologyRepresentation> vtList) {
        this.vtList = vtList;
    }

    /**
     * Gets the factory list.
     * 
     * @return the factory list
     */
    public List<CarFactoryRepresentation> getFactoryList() {
        return factoryList;
    }

    /**
     * Sets the factory list.
     * 
     * @param factoryList the new factory list
     */
    public void setFactoryList(List<CarFactoryRepresentation> factoryList) {
        this.factoryList = factoryList;
    }

    /**
     * Gets the tvv label.
     * 
     * @return the tvv label
     */
    public String getTvvLabel() {
        return tvvLabel;
    }

    /**
     * Sets the tvv label.
     * 
     * @param tvvLabel the new tvv label
     */
    public void setTvvLabel(String tvvLabel) {
        this.tvvLabel = tvvLabel;
    }

    /**
     * Gets the pc family list.
     * 
     * @return the pc family list
     */
    public List<ProjectCodeFamilyRepresentation> getPcFamilyList() {
        return pcFamilyList;
    }

    /**
     * Sets the pc family list.
     * 
     * @param pcFamilyList the new pc family list
     */
    public void setPcFamilyList(List<ProjectCodeFamilyRepresentation> pcFamilyList) {
        this.pcFamilyList = pcFamilyList;
    }

    /**
     * Gets the engine list.
     * 
     * @return the engine list
     */
    public List<EngineRepresentation> getEngineList() {
        return engineList;
    }

    /**
     * Sets the engine list.
     * 
     * @param engineList the new engine list
     */
    public void setEngineList(List<EngineRepresentation> engineList) {
        this.engineList = engineList;
    }

    /**
     * Gets the gear box list.
     * 
     * @return the gear box list
     */
    public List<GearBoxRepresentation> getGearBoxList() {
        return gearBoxList;
    }

    /**
     * Sets the gear box list.
     * 
     * @param gearBoxList the new gear box list
     */
    public void setGearBoxList(List<GearBoxRepresentation> gearBoxList) {
        this.gearBoxList = gearBoxList;
    }

    /**
     * Gets the car brand list.
     * 
     * @return the car brand list
     */
    public List<CarBrandRepresentation> getCarBrandList() {
        return carBrandList;
    }

    /**
     * Sets the car brand list.
     * 
     * @param carBrandList the new car brand list
     */
    public void setCarBrandList(List<CarBrandRepresentation> carBrandList) {
        this.carBrandList = carBrandList;
    }

    /**
     * Gets the emission standard list.
     * 
     * @return the emission standard list
     */
    public List<EmissionStandardRepresentation> getEmissionStandardList() {
        return emissionStandardList;
    }

    /**
     * Sets the emission standard list.
     * 
     * @param emissionStandardList the new emission standard list
     */
    public void setEmissionStandardList(List<EmissionStandardRepresentation> emissionStandardList) {
        this.emissionStandardList = emissionStandardList;
    }

    /**
     * Gets the fuel list.
     * 
     * @return the fuel list
     */
    public List<FuelRepresentation> getFuelList() {
        return fuelList;
    }

    /**
     * Sets the fuel list.
     * 
     * @param fuelList the new fuel list
     */
    public void setFuelList(List<FuelRepresentation> fuelList) {
        this.fuelList = fuelList;
    }

    /**
     * Gets the body worklist.
     * 
     * @return the body worklist
     */
    public List<BodyWorkRepresentation> getBodyWorklist() {
        return bodyWorklist;
    }

    /**
     * Sets the body worklist.
     * 
     * @param bodyWorklist the new body worklist
     */
    public void setBodyWorklist(List<BodyWorkRepresentation> bodyWorklist) {
        this.bodyWorklist = bodyWorklist;
    }

    public List<FuelInjectionTypeRepresentation> getFuelInjectionList() {
        return fuelInjectionList;
    }

    public void setFuelInjectionList(List<FuelInjectionTypeRepresentation> fuelInjectionList) {
        this.fuelInjectionList = fuelInjectionList;
    }

}
