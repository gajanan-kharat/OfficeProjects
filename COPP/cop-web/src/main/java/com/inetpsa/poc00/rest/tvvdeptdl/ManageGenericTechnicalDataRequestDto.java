/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

public class ManageGenericTechnicalDataRequestDto {
	private List<GenericTechnicalDataRepresentation> genericDataList;

	public List<GenericTechnicalDataRepresentation> getGenericDataList() {
		return genericDataList;
	}

	public void setGenericDataList(List<GenericTechnicalDataRepresentation> genericDataList) {
		this.genericDataList = genericDataList;
	}

}
