/*
 * Creation : Jan 24, 2017
 */
package com.inetpsa.poc00.emsdeptcl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLFactory;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository;

@RunWith(SeedITRunner.class)
public class EmsDepTCLServiceIT {

    @Inject
    EmissionStdDepTCLRepository emsDepTclRepo;

    @Inject
    EmissionStdDepTCLFactory emsDepTclFactory;

    @Inject

    EmsDepTCLService emsDepService;

    @Inject

    EmissionStandardFactory emissionStandardFactory;

    @Inject
    EmissionStandardRepository emissionStandardRepo;

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void createEmsDepTCL() {
        EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
        EmissionStandard savedEmission = emissionStandardRepo.saveEmissionStandard(emissionStandard);
        EmissionStdDepTCL emsDepTcl = emsDepTclFactory.createEmissonStdDepTCL();
        EmissionStdDepTCL savedObj = emsDepService.createEmsDepTCL(savedEmission, emsDepTcl);
        assertNotNull(savedObj.getEntityId());
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteEmsDepTCL() {
        EmissionStdDepTCL emsDepTcl = emsDepTclFactory.createEmissonStdDepTCL();
        EmissionStdDepTCL savedObj = emsDepTclRepo.saveEmissonStdDepTCL(emsDepTcl);
        emsDepService.deleteEmsDepTCL(savedObj.getEntityId());
        EmissionStdDepTCL loadedObj = emsDepTclRepo.loadEmsDepTcl(savedObj.getEntityId());
        assertEquals(loadedObj, null);
    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void updateEmsDepTCL() {
        EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
        EmissionStandard savedEmssion = emissionStandardRepo.saveEmissionStandard(emissionStandard);
        EmissionStdDepTCL emsDepTcl = emsDepTclFactory.createEmissonStdDepTCL();
        emsDepTcl.setLabel("EmsDepTcl");
        emsDepTcl.setVersion("1.0");
        EmissionStdDepTCL savedEmsDepTcl = emsDepTclRepo.saveEmissonStdDepTCL(emsDepTcl);
        EmissionStdDepTCL updatedemsDepTcl = emsDepService.updateEmsDepTCL(savedEmsDepTcl, savedEmssion);
        assertTrue(new Double(updatedemsDepTcl.getVersion()) == new Double(emsDepTcl.getVersion()) + 1);
    }

}
