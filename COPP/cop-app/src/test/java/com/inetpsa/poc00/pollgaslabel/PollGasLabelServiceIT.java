
package com.inetpsa.poc00.pollgaslabel;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
    PollutantGasLabelRepository pollGasLabelRepo;

    @Inject
    PollutantGasLabelFactory pollGasLabelFactory;

    @Inject
    PollGasLabelService pollGasLabelService;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void savePollGasLabel() {
        PollutantGasLabel pollutantGasLabel = pollGasLabelFactory.createPollutantGasLabel();
        pollutantGasLabel.setLabel("PollutantLabel");
        String response = pollGasLabelService.savePollGasLabel(pollutantGasLabel);
        assertEquals(response, ConstantsApp.SUCCESS);
        pollutantGasLabel.setLabel("UpdatedPollutantLabel");
        response = pollGasLabelService.savePollGasLabel(pollutantGasLabel);
        assertEquals(response, ConstantsApp.SUCCESS);
        PollutantGasLabel pollutantGasLabelNew = pollGasLabelFactory.createPollutantGasLabel();
        pollutantGasLabelNew.setLabel("UpdatedPollutantLabel");
        response = pollGasLabelService.savePollGasLabel(pollutantGasLabelNew);
        assertEquals(response, pollutantGasLabelNew.getLabel());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deletePollGasLabel() {
        PollutantGasLabel pollutantGasLabel = pollGasLabelFactory.createPollutantGasLabel();
        pollutantGasLabel.setLabel("PollutantLabel");
        pollGasLabelService.savePollGasLabel(pollutantGasLabel);
        List<PollutantGasLabel> pollutantGasLabelList = pollGasLabelRepo.getPollutantByLabel(pollutantGasLabel.getLabel());
        String response = pollGasLabelService.deletePollGasLabel(pollutantGasLabelList.get(0).getEntityId());
        assertEquals(response, ConstantsApp.SUCCESS);
        PollutantGasLabel loadedObj = pollGasLabelRepo.loadPollGasLabel(pollutantGasLabelList.get(0).getEntityId());
        assertEquals(loadedObj, null);
    }
}
