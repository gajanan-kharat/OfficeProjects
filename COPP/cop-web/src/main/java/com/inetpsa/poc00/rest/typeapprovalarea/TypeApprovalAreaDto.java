package com.inetpsa.poc00.rest.typeapprovalarea;

import java.util.List;

/**
 * The Class TypeApprovalAreaDto.
 */
public class TypeApprovalAreaDto {

	/** The type approval area representation list. */
	List<TypeApprovalAreaRepresentation> typeApprovalAreaRepresentationList;

	/**
	 * Gets the type approval area representation list.
	 * 
	 * @return the type approval area representation list
	 */
	public List<TypeApprovalAreaRepresentation> getTypeApprovalAreaRepresentationList() {
		return typeApprovalAreaRepresentationList;
	}

	/**
	 * Sets the type approval area representation list.
	 * 
	 * @param typeApprovalAreaRepresentationList the new type approval area representation list
	 */
	public void setTypeApprovalAreaRepresentationList(List<TypeApprovalAreaRepresentation> typeApprovalAreaRepresentationList) {
		this.typeApprovalAreaRepresentationList = typeApprovalAreaRepresentationList;
	}

}
