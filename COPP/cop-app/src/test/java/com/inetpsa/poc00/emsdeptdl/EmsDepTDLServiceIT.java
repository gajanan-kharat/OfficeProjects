/*
 * Creation : Jan 25, 2017
 */
package com.inetpsa.poc00.emsdeptdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLFactory;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository;

@RunWith(SeedITRunner.class)
public class EmsDepTDLServiceIT {

    @Inject
    EmissionStdDepTDLRepository emsTdlRepo;

    @Inject
    EmissionStdDepTDLFactory emsDepTdlFactory;

    @Inject
    EmsDepTDLService emsDepTdlService;

    @Inject
    EmissionStandardRepository emissionRepo;

    @Inject
    EmissionStandardFactory emissionFactory;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void createEmsDepTDL() {

        EmissionStandard emissionStandard = emissionFactory.createEmissonStandard();
        EmissionStandard savedEmission = emissionRepo.saveEmissionStandard(emissionStandard);
        EmissionStdDepTDL emsDepTdl = emsDepTdlFactory.createEmissonStdDepTDL();
        EmissionStdDepTDL savedEmsDepTdl = emsDepTdlService.createEmsDepTDL(emissionStandard, emsDepTdl);
        assertNotNull(savedEmsDepTdl.getEntityId());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteEmsDepTDL() {
        EmissionStdDepTDL emsDepTdl = emsDepTdlFactory.createEmissonStdDepTDL();
        EmissionStdDepTDL savedEmsDepTdl = emsTdlRepo.saveEmissonStdDepTDL(emsDepTdl);
        emsDepTdlService.deleteEmsDepTDL(savedEmsDepTdl.getEntityId());
        EmissionStdDepTDL loadedObj = emsTdlRepo.loadEmsDepTdl(savedEmsDepTdl.getEntityId());
        assertEquals(loadedObj, null);

    }

    @Test
    public void copySingleTDL() {
        EmissionStandard emissionStandard = emissionFactory.createEmissonStandard();
        EmissionStandard savedEmission = emissionRepo.saveEmissionStandard(emissionStandard);
        EmissionStdDepTDL emsDepTdl = emsDepTdlFactory.createEmissonStdDepTDL();
        EmissionStdDepTDL savedEmsDepTdl = emsTdlRepo.saveEmissonStdDepTDL(emsDepTdl);
        EmissionStdDepTDL copiedEmsDepTdl = emsDepTdlService.copySingleTDL(savedEmsDepTdl, savedEmission);
        assertEquals(savedEmission.getEntityId(), copiedEmsDepTdl.getEmissionStandard().getEntityId());
    }

    @Test
    public void updateEmsDepTDL() {
        EmissionStdDepTDL emsDepTdl = emsDepTdlFactory.createEmissonStdDepTDL();
        emsDepTdl.setLabel("EmsDepTdl");
        emsDepTdl.setVersion("1.0");
        EmissionStdDepTDL savedEmsDepTdl = emsTdlRepo.saveEmissonStdDepTDL(emsDepTdl);
        EmissionStandard emissionStandard = emissionFactory.createEmissonStandard();
        EmissionStandard savedEmission = emissionRepo.saveEmissionStandard(emissionStandard);
        EmissionStdDepTDL updatedEmsDepTdl = emsDepTdlService.updateEmsDepTDL(savedEmsDepTdl, savedEmission);
        assertTrue(new Double(updatedEmsDepTdl.getVersion()) == new Double(savedEmsDepTdl.getVersion()) + 1);

    }

}
