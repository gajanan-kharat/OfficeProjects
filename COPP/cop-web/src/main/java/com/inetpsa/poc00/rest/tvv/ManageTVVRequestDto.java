package com.inetpsa.poc00.rest.tvv;

import java.util.List;

import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;

/**
 * The Class ManageTVVRequestDto.
 */
public class ManageTVVRequestDto {

	/** The technical group representation. */
	private TechnicalGroupRepresentation technicalGroupRepresentation;

	/** The tvv representation list. */
	private List<TvvRepresentation> tvvRepresentationList;

	/**
	 * Gets the technical group representation.
	 * 
	 * @return the technicalGroupRepresentation
	 */
	public TechnicalGroupRepresentation getTechnicalGroupRepresentation() {
		return technicalGroupRepresentation;
	}

	/**
	 * Sets the technical group representation.
	 * 
	 * @param technicalGroupRepresentation the technicalGroupRepresentation to set
	 */
	public void setTechnicalGroupRepresentation(TechnicalGroupRepresentation technicalGroupRepresentation) {
		this.technicalGroupRepresentation = technicalGroupRepresentation;
	}

	/**
	 * Gets the tvv representation list.
	 * 
	 * @return the tvvRepresentationList
	 */
	public List<TvvRepresentation> getTvvRepresentationList() {
		return tvvRepresentationList;
	}

	/**
	 * Sets the tvv representation list.
	 * 
	 * @param tvvRepresentationList the tvvRepresentationList to set
	 */
	public void setTvvRepresentationList(List<TvvRepresentation> tvvRepresentationList) {
		this.tvvRepresentationList = tvvRepresentationList;
	}

}
