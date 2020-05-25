package com.inetpsa.poc00.rest.technicalgroup;

import java.util.List;

import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;


/**
 * The Class ManageTGRequestDto.
 */
public class ManageTGRequestDto {

	/** The regulation group representation. */
	private RegulationGroupRepresentation regulationGroupRepresentation;

	/** The tg representation list. */
	private List<TechnicalGroupRepresentation> tgRepresentationList;

	/** The tvv object. */
	private TvvRepresentation tvvObject;

	/** The validate for emission standard. */
	private boolean validateForEmissionStandard;

	/**
	 * Gets the regulation group representation.
	 * 
	 * @return the regulationGroupRepresentation
	 */
	public RegulationGroupRepresentation getRegulationGroupRepresentation() {
		return regulationGroupRepresentation;
	}

	/**
	 * Sets the regulation group representation.
	 * 
	 * @param regulationGroupRepresentation the regulationGroupRepresentation to set
	 */
	public void setRegulationGroupRepresentation(RegulationGroupRepresentation regulationGroupRepresentation) {
		this.regulationGroupRepresentation = regulationGroupRepresentation;
	}

	/**
	 * Gets the tg representation list.
	 * 
	 * @return the tgRepresentationList
	 */
	public List<TechnicalGroupRepresentation> getTgRepresentationList() {
		return tgRepresentationList;
	}

	/**
	 * Sets the tg representation list.
	 * 
	 * @param tgRepresentationList the tgRepresentationList to set
	 */
	public void setTgRepresentationList(List<TechnicalGroupRepresentation> tgRepresentationList) {
		this.tgRepresentationList = tgRepresentationList;
	}

	/**
	 * Gets the tvv object.
	 * 
	 * @return the tvv object
	 */
	public TvvRepresentation getTvvObject() {
		return tvvObject;
	}

	/**
	 * Sets the tvv object.
	 * 
	 * @param tvvObject the new tvv object
	 */
	public void setTvvObject(TvvRepresentation tvvObject) {
		this.tvvObject = tvvObject;
	}

	/**
	 * Checks if is validate for emission standard.
	 * 
	 * @return true, if is validate for emission standard
	 */
	public boolean isValidateForEmissionStandard() {
		return validateForEmissionStandard;
	}

	/**
	 * Sets the validate for emission standard.
	 * 
	 * @param validateForEmissionStandard the new validate for emission standard
	 */
	public void setValidateForEmissionStandard(boolean validateForEmissionStandard) {
		this.validateForEmissionStandard = validateForEmissionStandard;
	}

}
