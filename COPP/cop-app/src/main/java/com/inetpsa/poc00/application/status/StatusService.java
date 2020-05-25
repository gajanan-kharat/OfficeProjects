/*
 * Creation : Nov 11, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.status;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.status.Status;

/**
 * StatusService
 * 
 * @author mehaj
 */
@Service
public interface StatusService {
    public Status saveStatus(Status status);

    public Long deleteStatus(Long statusId);
}
