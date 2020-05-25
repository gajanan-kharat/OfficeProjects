/*
 * Creation : Jun 3, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.List;

import com.inetpsa.poc00.common.Constants;

/**
 * The Class TvvSearchRepresentation.
 */
public class TvvSearchRepresentation {

	/** The tvv label. */
	private String tvvLabel;

	/** The pc family list. */
	private List<String> pcFamilyList;

	/** The body work list. */
	private List<String> bodyWorkList;

	/** The engine list. */
	private List<String> engineList;

	/** The gear box list. */
	private List<String> gearBoxList;

	/** The inertia l ist. */
	private List<Integer> inertiaLIst;

	/** The coast down list. */
	private List<String> coastDownList;

	/** The pg min limits. */
	private List<Double> pgMinLimits;

	/** The pg max limits. */
	private List<Double> pgMaxLimits;

	/** The ems list. */
	private List<String> emsList;

	/** The country list. */
	private List<String> countryList;

	/** The fuel list. */
	private List<String> fuelList;

	/** The type approval area list. */
	List<String> typeApprovalAreaList;
	private boolean sortAlphabetically;
	private boolean sortByDate;

	private boolean searchForTg;
	private String statuslabel=Constants.VALID;


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
	public List<String> getPcFamilyList() {
		return pcFamilyList;
	}

	/**
	 * Sets the pc family list.
	 * 
	 * @param pcFamilyList the new pc family list
	 */
	public void setPcFamilyList(List<String> pcFamilyList) {
		this.pcFamilyList = pcFamilyList;
	}

	/**
	 * Gets the body work list.
	 * 
	 * @return the body work list
	 */
	public List<String> getBodyWorkList() {
		return bodyWorkList;
	}

	/**
	 * Sets the body work list.
	 * 
	 * @param bodyWorkList the new body work list
	 */
	public void setBodyWorkList(List<String> bodyWorkList) {
		this.bodyWorkList = bodyWorkList;
	}

	/**
	 * Gets the engine list.
	 * 
	 * @return the engine list
	 */
	public List<String> getEngineList() {
		return engineList;
	}

	/**
	 * Sets the engine list.
	 * 
	 * @param engineList the new engine list
	 */
	public void setEngineList(List<String> engineList) {
		this.engineList = engineList;
	}

	/**
	 * Gets the gear box list.
	 * 
	 * @return the gear box list
	 */
	public List<String> getGearBoxList() {
		return gearBoxList;
	}

	/**
	 * Sets the gear box list.
	 * 
	 * @param gearBoxList the new gear box list
	 */
	public void setGearBoxList(List<String> gearBoxList) {
		this.gearBoxList = gearBoxList;
	}

	/**
	 * Gets the inertia l ist.
	 * 
	 * @return the inertia l ist
	 */
	public List<Integer> getInertiaLIst() {
		return inertiaLIst;
	}

	/**
	 * Sets the inertia l ist.
	 * 
	 * @param inertiaLIst the new inertia l ist
	 */
	public void setInertiaLIst(List<Integer> inertiaLIst) {
		this.inertiaLIst = inertiaLIst;
	}

	/**
	 * Gets the coast down list.
	 * 
	 * @return the coast down list
	 */
	public List<String> getCoastDownList() {
		return coastDownList;
	}

	/**
	 * Sets the coast down list.
	 * 
	 * @param coastDownList the new coast down list
	 */
	public void setCoastDownList(List<String> coastDownList) {
		this.coastDownList = coastDownList;
	}

	/**
	 * Gets the pg min limits.
	 * 
	 * @return the pg min limits
	 */
	public List<Double> getPgMinLimits() {
		return pgMinLimits;
	}

	/**
	 * Sets the pg min limits.
	 * 
	 * @param pgMinLimits the new pg min limits
	 */
	public void setPgMinLimits(List<Double> pgMinLimits) {
		this.pgMinLimits = pgMinLimits;
	}

	/**
	 * Gets the pg max limits.
	 * 
	 * @return the pg max limits
	 */
	public List<Double> getPgMaxLimits() {
		return pgMaxLimits;
	}

	/**
	 * Sets the pg max limits.
	 * 
	 * @param pgMaxLimits the new pg max limits
	 */
	public void setPgMaxLimits(List<Double> pgMaxLimits) {
		this.pgMaxLimits = pgMaxLimits;
	}

	/**
	 * Gets the ems list.
	 * 
	 * @return the ems list
	 */
	public List<String> getEmsList() {
		return emsList;
	}

	/**
	 * Sets the ems list.
	 * 
	 * @param emsList the new ems list
	 */
	public void setEmsList(List<String> emsList) {
		this.emsList = emsList;
	}

	/**
	 * Gets the country list.
	 * 
	 * @return the country list
	 */
	public List<String> getCountryList() {
		return countryList;
	}

	/**
	 * Sets the country list.
	 * 
	 * @param countryList the new country list
	 */
	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	/**
	 * Gets the fuel list.
	 * 
	 * @return the fuel list
	 */
	public List<String> getFuelList() {
		return fuelList;
	}

	/**
	 * Sets the fuel list.
	 * 
	 * @param fuelList the new fuel list
	 */
	public void setFuelList(List<String> fuelList) {
		this.fuelList = fuelList;
	}

	/**
	 * Gets the type approval area list.
	 * 
	 * @return the type approval area list
	 */
	public List<String> getTypeApprovalAreaList() {
		return typeApprovalAreaList;
	}

	/**
	 * Sets the type approval area list.
	 * 
	 * @param typeApprovalAreaList the new type approval area list
	 */
	public void setTypeApprovalAreaList(List<String> typeApprovalAreaList) {
		this.typeApprovalAreaList = typeApprovalAreaList;
	}

	public boolean getSortAlphabetically() {
		return sortAlphabetically;
	}

	public void setSortAlphabetically(boolean sortAlphabetically) {
		this.sortAlphabetically = sortAlphabetically;
	}

	public boolean getSortByDate() {
		return sortByDate;
	}

	public void setSortByDate(boolean sortByDate) {
		this.sortByDate = sortByDate;
	}


	/**
	 * @return the searchForTg
	 */
	public boolean isSearchForTg() {
		return searchForTg;
	}

	/**
	 * @param searchForTg the searchForTg to set
	 */
	public void setSearchForTg(boolean searchForTg) {
		this.searchForTg = searchForTg;
	}

	/**
	 * @return the statuslabel
	 */
	public String getStatuslabel() {
		return statuslabel;
	}

	/**
	 * @param statuslabel the statuslabel to set
	 */
	public void setStatuslabel(String statuslabel) {
		this.statuslabel = statuslabel;
	}



}
