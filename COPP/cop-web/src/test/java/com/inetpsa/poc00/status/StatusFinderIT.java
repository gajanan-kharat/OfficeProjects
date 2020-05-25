/*
 * Creation : Nov 10, 2016
 */
package com.inetpsa.poc00.status;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.status.StatusRepresentation;

/**
 * The Class StatusFinderIT.
 */
@RunWith(SeedITRunner.class)
public class StatusFinderIT {

	/** The status fatory. */
	@Inject
	StatusFactory statusFatory;
	
	/** The status repository. */
	@Inject
	StatusRepository statusRepository;
	
	/** The status finder. */
	@Inject
	StatusFinder statusFinder;

	/**
	 * Gets the all status test.
	 *
	 * @return the all status test
	 */
	@Test
	public void getAllStatusTest() {
		Status status = statusFatory.createStatus();
		status.setLabel("StatusJUnit" + Calendar.getInstance().getTime());
		status.setGuiLabel("GUILabelStatusTest");
		Status statusSaved = statusRepository.saveStatus(status);
		List<StatusRepresentation> liststatus = statusFinder.getAllStatus();
		Assert.assertNotEquals(0, liststatus.size());
	}
	
	/**
	 * Find status by label.
	 */
	@Test
	public void findStatusByLabel() {
		Status status = statusFatory.createStatus();
		status.setLabel("StatusJUnitLabelTest" + Calendar.getInstance().getTime());
		status.setGuiLabel("GUILabelStatusEMSTest" + Calendar.getInstance().getTime());
		Status statusSaved = statusRepository.saveStatus(status);
		Status statusnew = statusFinder.findStatusByLabel(statusSaved.getLabel());
		Assert.assertNotNull(statusnew);
	}

	/**
	 * Gets the all status natures for tvv.
	 *
	 * @return the all status natures for tvv
	 */
	@Test
	public void getAllStatusNaturesForTvv() {
		Status status = statusFatory.createStatus();
		status.setLabel("StatusJUnitForTvv" + Calendar.getInstance().getTime());
		status.setGuiLabel("GUILabelStatusTest" + Calendar.getInstance().getTime());
		Status statusSaved = statusRepository.saveStatus(status);
		List<Status> liststatus = statusFinder.getAllStatusNaturesForTvv();
		Assert.assertNotEquals(0, liststatus.size());
	}
}
