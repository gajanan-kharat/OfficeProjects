/*
 * Creation : Mar 31, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

public class ManageTvvDepTDLRequestDto {
	private List<TvvDepTDLRepresentation> tvvDepTDLRepresentationList;

	public List<TvvDepTDLRepresentation> getTvvDepTDLRepresentationList() {
		return tvvDepTDLRepresentationList;
	}

	public void setTvvDepTDLRepresentationList(
			List<TvvDepTDLRepresentation> tvvDepTDLRepresentationList) {
		this.tvvDepTDLRepresentationList = tvvDepTDLRepresentationList;
	}
}
