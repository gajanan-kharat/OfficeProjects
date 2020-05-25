/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.finalreduction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.finalreduction.FinalReductionService;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioFactory;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository;

@RunWith(SeedITRunner.class)
public class FinalReductionServiceIT {

    @Inject
    FinalReductionRatioRepository finalReductionRepo;

    @Inject
    FinalReductionRatioFactory finalReductionFactory;

    @Inject
    FinalReductionService finalReductionService;

    @Test
    @WithUser(id = "poch1", password = "poch1")

    public void saveFinalReduction() {
        FinalReductionRatio finalReductionRatio = finalReductionFactory.createReductionRatio();
        FinalReductionRatio savedFinalRed = finalReductionService.saveFinalReduction(finalReductionRatio);
        assertNotNull(savedFinalRed.getEntityId());
        savedFinalRed.setLabel("FinalReduction");
        FinalReductionRatio updatedFinalRed = finalReductionService.saveFinalReduction(savedFinalRed);
        FinalReductionRatio loadedFinalRed = finalReductionRepo.loadFinalReduction(updatedFinalRed.getEntityId());
        assertEquals(updatedFinalRed.getLabel(), loadedFinalRed.getLabel());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteFinalReduction() {
        FinalReductionRatio finalReductionRatio = finalReductionFactory.createReductionRatio();
        FinalReductionRatio savedFinalRed = finalReductionService.saveFinalReduction(finalReductionRatio);
        finalReductionService.deleteFinalReduction(savedFinalRed.getEntityId());
        FinalReductionRatio loadedFinalRed = finalReductionRepo.loadFinalReduction(savedFinalRed.getEntityId());
        assertEquals(loadedFinalRed, null);
    }
}
