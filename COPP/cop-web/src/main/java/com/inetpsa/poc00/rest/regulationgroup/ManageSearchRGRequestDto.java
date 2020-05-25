package com.inetpsa.poc00.rest.regulationgroup;

import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;


/**
 * The Class ManageSearchRGRequestDto.
 */
public class ManageSearchRGRequestDto {

	/** The tvv search representation. */
	private TvvSearchRepresentation tvvSearchRepresentation;
	
	/** The search lable. */
	private String searchLable;
	
	/** The sort alphabetically. */
	private boolean sortAlphabetically;
	
	/** The sort by date. */
	private boolean sortByDate;

	/**
	 * Gets the tvv search representation.
	 *
	 * @return the tvv search representation
	 */
	public TvvSearchRepresentation getTvvSearchRepresentation() {
		return tvvSearchRepresentation;
	}

	/**
	 * Sets the tvv search representation.
	 *
	 * @param tvvSearchRepresentation the new tvv search representation
	 */
	public void setTvvSearchRepresentation(TvvSearchRepresentation tvvSearchRepresentation) {
		this.tvvSearchRepresentation = tvvSearchRepresentation;
	}

	/**
	 * Gets the search lable.
	 *
	 * @return the search lable
	 */
	public String getSearchLable() {
		return searchLable;
	}

	/**
	 * Sets the search lable.
	 *
	 * @param searchLable the new search lable
	 */
	public void setSearchLable(String searchLable) {
		this.searchLable = searchLable;
	}

	/**
	 * Checks if is sort alphabetically.
	 *
	 * @return true, if is sort alphabetically
	 */
	public boolean isSortAlphabetically() {
		return sortAlphabetically;
	}

	/**
	 * Sets the sort alphabetically.
	 *
	 * @param sortAlphabetically the new sort alphabetically
	 */
	public void setSortAlphabetically(boolean sortAlphabetically) {
		this.sortAlphabetically = sortAlphabetically;
	}

	/**
	 * Checks if is sort by date.
	 *
	 * @return true, if is sort by date
	 */
	public boolean isSortByDate() {
		return sortByDate;
	}

	/**
	 * Sets the sort by date.
	 *
	 * @param sortByDate the new sort by date
	 */
	public void setSortByDate(boolean sortByDate) {
		this.sortByDate = sortByDate;
	}

}
