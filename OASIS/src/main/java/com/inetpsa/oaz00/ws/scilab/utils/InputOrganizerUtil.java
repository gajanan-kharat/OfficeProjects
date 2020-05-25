/*
 * @(#)InputOrganizerUtil.java October 01, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * Util class having getters for getting some input objects of "SCILAB".
 * 
 * @author amit
 * 
 */
public class InputOrganizerUtil {

	/**
	 * Static getter for a list of original "MotherRequirement" object(s).
	 * 
	 * @param requirementList
	 * @return List of requirementType
	 */
	public static List<RequirementType> getPMotherRequirementsList(List<RequirementType> requirementList) {
		List<RequirementType> tempPMotherRequirementList = new ArrayList<RequirementType>();
		if (null != requirementList) {
			for (RequirementType aRequirement : requirementList) {
				// The parentId of a requirement should be equal to it's own id in order for it to be a motherRequirement.
				if (aRequirement.getParentId().equalsIgnoreCase(aRequirement.getId())) {
					tempPMotherRequirementList.add(aRequirement);
				}
			}
		}
		return tempPMotherRequirementList;
	}

	/**
	 * Static getter for getting the "transferModelType" object belonging to the supplied aSciMotherRequirementType object.
	 * 
	 * @param aMotherRequirement
	 * @param transferModels
	 * @return TransferModelType
	 */
	public static TransferModelType getTransferModel(RequirementType aMotherRequirement, List<TransferModelType> transferModels) {
		if (null != transferModels) {
			for (TransferModelType aTransferModel : transferModels) {
				// The parentId of a transferModel should be equal to the id of a motherRequirement in order for the former to be belonging to the
				// latter.
				if (aTransferModel.getParentId().equalsIgnoreCase(aMotherRequirement.getId())) {
					return aTransferModel;
				}
			}
		}
		return null;
	}

	/**
	 * Static getter for getting the list of "contributor" objects belonging to the supplied TransferModelType object.
	 * 
	 * @param requirementList
	 * @param aTransferModel
	 * @return List of RequirementType
	 */
	public static List<RequirementType> getPContributorsList(List<RequirementType> requirementList, TransferModelType aTransferModel) {

		List<RequirementType> tempPContributorList = new ArrayList<RequirementType>();

		for (RequirementType aRequirement : requirementList) {
			// The parentId of a contributor should be equal to the id of a transferModel in order for the former to be belonging to the latter.
			if (aRequirement.getParentId().equalsIgnoreCase(aTransferModel.getId())) {
				tempPContributorList.add(aRequirement);
			}
		}
		return tempPContributorList;
	}

}
