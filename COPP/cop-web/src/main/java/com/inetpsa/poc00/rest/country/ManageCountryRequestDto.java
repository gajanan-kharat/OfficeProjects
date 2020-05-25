package com.inetpsa.poc00.rest.country;

import java.util.List;


/**
 * The Class ManageCountryRequestDto.
 */
public class ManageCountryRequestDto {

	/** The country representation list. */
	private List<CountryRepresentation> countryRepresentationList;

	/**
	 * Gets the country representation list.
	 *
	 * @return the country representation list
	 */
	public List<CountryRepresentation> getCountryRepresentationList() {
		return countryRepresentationList;
	}

	/**
	 * Sets the country representation list.
	 *
	 * @param countryRepresentationList the new country representation list
	 */
	public void setCountryRepresentationList(
			List<CountryRepresentation> countryRepresentationList) {
		this.countryRepresentationList = countryRepresentationList;
	}
	
}
