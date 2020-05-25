/*
 * Creation : Nov 11, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.status;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class StatusServiceIT {
    @Inject
    StatusFactory statusFatory;
    @Inject
    StatusRepository statusRepository;

    @Test
    public void saveStatus() {
        Status status = statusFatory.createStatus();
        status.setLabel("SaveStatusJunit" + Calendar.getInstance().getTime());
        status.setGuiLabel("SaveStatusJunit" + Calendar.getInstance().getTime());
        Status statusSaved = statusRepository.saveStatus(status);
        Assert.assertNotNull(statusSaved);
    }

    @Test
    public void deleteStatus() {
        Status status = statusFatory.createStatus();
        status.setLabel("DeleteStatusJunit" + Calendar.getInstance().getTime());
        status.setGuiLabel("DeleteStatusJunit" + Calendar.getInstance().getTime());
        Status statusSaved = statusRepository.saveStatus(status);
        long statusdeletedId = statusRepository.deleteStatus(statusSaved.getEntityId());
        Assert.assertNotNull(statusdeletedId);
    }
}
