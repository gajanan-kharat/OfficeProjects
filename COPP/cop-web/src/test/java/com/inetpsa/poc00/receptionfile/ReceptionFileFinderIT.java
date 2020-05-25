/*
 * Creation : Nov 4, 2016
 */
package com.inetpsa.poc00.receptionfile;

import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.rest.receptionfile.ReceptionFileFinder;
import com.inetpsa.poc00.rest.receptionfile.ReceptionFileRepresentation;

/**
 * The Class ReceptionFileFinderIT.
 */
@RunWith(SeedITRunner.class)
public class ReceptionFileFinderIT {

    /** The reception file finder. */
    @Inject
    private ReceptionFileFinder receptionFileFinder;

    /** The logger. */
    @Logging
    private final Logger logger = Logger.getLogger(ReceptionFileFinderIT.class);

    /**
     * Gets the all reception files.
     *
     * @return the all reception files
     */
    @Test
    public void getAllReceptionFiles() {

        List<ReceptionFileRepresentation> receptionFileObj = receptionFileFinder.getAllReceptionFiles("poce1");

        logger.info("**********getAllReceptionFiles tested successfully*********");
    }

}
