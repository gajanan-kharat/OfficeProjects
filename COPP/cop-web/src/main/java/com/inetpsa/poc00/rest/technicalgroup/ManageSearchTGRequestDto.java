package com.inetpsa.poc00.rest.technicalgroup;

import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;


/**
 * The Class ManageSearchTGRequestDto.
 */
public class ManageSearchTGRequestDto {

	/** The tvv search representation. */
	private TvvSearchRepresentation tvvSearchRepresentation;

	/** The search lable. */
	private String searchLable;

	/** The search for rg. */
	private boolean searchForRG;
	private boolean sortAlphabetically;
	private boolean sortByDate;

	/**
	 * Gets the tvv search representation.
	 * 
	 * @return the tvvSearchRepresentation
	 */
	public TvvSearchRepresentation getTvvSearchRepresentation() {
		return tvvSearchRepresentation;
	}

	/**
	 * Sets the tvv search representation.
	 * 
	 * @param tvvSearchRepresentation the tvvSearchRepresentation to set
	 */
	public void setTvvSearchRepresentation(TvvSearchRepresentation tvvSearchRepresentation) {
		this.tvvSearchRepresentation = tvvSearchRepresentation;
	}

	/**
	 * Gets the search lable.
	 * 
	 * @return the searchLable
	 */
	public String getSearchLable() {
		return searchLable;
	}

	/**
	 * Sets the search lable.
	 * 
	 * @param searchLable the searchLable to set
	 */
	public void setSearchLable(String searchLable) {
		this.searchLable = searchLable;
	}

	/**
	 * Checks if is search for rg.
	 * 
	 * @return true, if is search for rg
	 */
	public boolean isSearchForRG() {
		return searchForRG;
	}

	/**
	 * Sets the search for rg.
	 * 
	 * @param searchForRG the new search for rg
	 */
	public void setSearchForRG(boolean searchForRG) {
		this.searchForRG = searchForRG;
	}

	public boolean isSortAlphabetically() {
		return sortAlphabetically;
	}

	public void setSortAlphabetically(boolean sortAlphabetically) {
		this.sortAlphabetically = sortAlphabetically;
	}

	public boolean isSortByDate() {
		return sortByDate;
	}

	public void setSortByDate(boolean sortByDate) {
		this.sortByDate = sortByDate;
	}

}
