/*
 * Creation : Jan 16, 2017
 */
package com.inetpsa.poc00.pollutantgaslabel;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.pollgaslabel.PollGasLabelService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;

@RunWith(SeedITRunner.class)
public class PollGasLabelServiceIT {
	@Inject
	PollGasLabelService pollGasLabelService;
	@Inject
	PollutantGasLabelRepository pollutantGasLabelRepository;
	@Inject
	PollutantGasLabelFactory pollutantGasLabelFactory;

	@Test
	@WithUser(id = "poch1", password = "poch1")
	public final void testSavePollGasLabel() {
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());

		String result = pollGasLabelService.savePollGasLabel(pgLabel);
		assertEquals(ConstantsApp.SUCCESS, result);
	}

	@Test
	@WithUser(id = "poch1", password = "poch1")
	public final void testDeletePollGasLabel() {
		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pgLabel.setLabel("PollGasLabel" + Calendar.getInstance().getTime());
		PollutantGasLabel pgLabelSaved = pollutantGasLabelRepository.savePollutantGasLabel(pgLabel);
		String result = pollGasLabelService.deletePollGasLabel(pgLabelSaved.getEntityId());
		assertEquals(ConstantsApp.SUCCESS, result);
	}

}
