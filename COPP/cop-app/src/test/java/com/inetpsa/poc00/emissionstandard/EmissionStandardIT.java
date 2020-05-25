/*
 * Creation : Jan 11, 2017
 */
package com.inetpsa.poc00.emissionstandard;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.status.StatusRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class EmissionStandardIT.
 */
@RunWith(SeedITRunner.class)
public class EmissionStandardIT {

    /** The emission service. */
    @Inject
    EmissionStandardService emissionService;

    /** The emission factory. */
    @Inject
    EmissionStandardFactory emissionFactory;

    /** The emission repository. */
    @Inject
    EmissionStandardRepository emissionRepository;

    /** The status factory. */
    @Inject
    StatusFactory statusFactory;

    /** The status repository. */
    @Inject
    StatusRepository statusRepository;

    /**
     * Creates the emission standard.
     */
    @Test
    public void createEmissionStandard() {

        EmissionStandard emission = emissionFactory.createEmissonStandard();
        Status status = statusFactory.createStatus(ConstantsApp.DRAFT);
        statusRepository.saveStatus(status);
        EmissionStandard emsObj = emissionService.createEmissionStandard(emission);
        assertNotNull(emsObj.getEntityId());

    }

    /**
     * Change emission standard version.
     */
    @Test
    public void changeEmissionStandardVersion() {
        EmissionStandard emission = emissionFactory.createEmissonStandard();
        Status status = statusFactory.createStatus(ConstantsApp.VALID);
        statusRepository.saveStatus(status);
        EmissionStandard emissionobj = emissionRepository.saveEmissionStandard(emission);
        EmissionStandard versionedEmission = emissionService.changeEmissionStandardVersion(emissionobj.getEntityId());
        assertNotNull(versionedEmission.getEntityId());
    }

    /**
     * Save emission standard.
     */
    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void saveEmissionStandard() {
        EmissionStandard emission = emissionFactory.createEmissonStandard();
        emission.setEsLabel("EmissionLabel");
        EmissionStandard emissionobj = emissionRepository.saveEmissionStandard(emission);
        EmissionStandard emsObj = emissionService.saveEmissionStandard(emissionobj);
        assertNotNull(emsObj.getEntityId());
    }

    /**
     * Delete.
     */
    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void delete() {
        EmissionStandard emission = emissionFactory.createEmissonStandard();
        EmissionStandard emissionobj = emissionRepository.saveEmissionStandard(emission);
        emissionobj.setRegulationGrp(null);
        emissionobj.setTechnicalCases(null);
        emissionobj.setPgLists(null);
        emissionService.deleteEmissionStandard(emissionobj.getEntityId());
    }

    /**
     * Copy emission standard.
     */
    @Test
    public void copyEmissionStandard() {
        EmissionStandard emission = emissionFactory.createEmissonStandard();
        emission.setEsLabel("esLabel");
        emission.setDescription("Description");
        emission.setVersion("1.0");
        EmissionStandard emissionobj = emissionRepository.saveEmissionStandard(emission);
        emissionService.copyEmissionStandard(emissionobj);

    }

}