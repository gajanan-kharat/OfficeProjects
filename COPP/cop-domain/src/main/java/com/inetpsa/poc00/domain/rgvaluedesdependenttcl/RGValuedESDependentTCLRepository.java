package com.inetpsa.poc00.domain.rgvaluedesdependenttcl;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of RegGrpValEmStdDepTCL.
 */

public interface RGValuedESDependentTCLRepository extends GenericRepository<RGValuedESDependentTCL, Long> {

    /**
     * Saves the RegGrpValEmStdDepTCL.
     *
     * @param object the RegGrpValEmStdDepTCL to save
     * @return the RegGrpValEmStdDepTCL
     */
    RGValuedESDependentTCL saveRegGrpValEmStdDepTCL(RGValuedESDependentTCL object);

    /**
     * Delete reg grp val em std dep tcl.
     *
     * @param rgvaluedDepTclId the rgvalued dep tcl id
     */
    void deleteRegGrpValEmStdDepTCL(long rgvaluedDepTclId);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */

    RGValuedESDependentTCL loadRGValuedESDependentTCL(long rgValuedId);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return RegGrpValEmStdDepTCL count
     */
    long count();

}
