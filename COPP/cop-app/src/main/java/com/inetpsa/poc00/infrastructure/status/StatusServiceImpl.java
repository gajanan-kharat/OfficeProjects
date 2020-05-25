/*
 * Creation : Nov 11, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.status;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.status.StatusService;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;

/**
 * StatusServiceImpl Class
 * 
 * @author mehaj
 */
public class StatusServiceImpl implements StatusService {
    @Inject
    StatusRepository statusRepository;

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status saveStatus(Status status) {
        return statusRepository.saveStatus(status);

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Long deleteStatus(Long statusId) {
        return statusRepository.deleteStatus(statusId);
    }

}
