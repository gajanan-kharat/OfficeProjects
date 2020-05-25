/*
 * Creation : Jan 4, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.application.typeapprovalarea;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;

/**
 * The TypeApprovalAreaService
 * 
 * @author mehaj
 *
 */
@Service
public interface TypeApprovalAreaService {
	public String saveTypeApprovalArea(TypeApprovalArea typeApprovalArea);

	String deleteTypeApprovalArea(long typeApprovalAreaId);
}
