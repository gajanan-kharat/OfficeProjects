package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileFinder;

/**
 * The Class JpaPreparationFileFinder.
 */
public class JpaPreparationFileFinder implements PreparationFileFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.preparationfile.PreparationFileFinder#findPreparationFileById(java.lang.Long)
     */
    @Override
    public PreparationFile findPreparationFileById(Long entityId) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.preparationfile.PreparationFileFinder#findPrepFileByVehicleFileId(java.lang.Long)
     */
    @Override
    public PreparationFile findPrepFileByVehicleFileId(Long vehicleFileId) {

    	TypedQuery<PreparationFile> query = entityManager.createQuery("SELECT pf FROM PreparationFile pf WHERE pf.vehicleFile.entityId = ?1", PreparationFile.class);

    	query.setParameter(1, vehicleFileId);
    	
		List<PreparationFile> preparationFileList = query.getResultList();

		if (preparationFileList != null && !preparationFileList.isEmpty()) {
			return preparationFileList.get(0);
		}

		return null;

	}

}
