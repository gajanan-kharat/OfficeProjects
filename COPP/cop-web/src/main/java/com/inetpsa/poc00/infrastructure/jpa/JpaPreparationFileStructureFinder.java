/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureFinder;
import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureRepresentation;

public class JpaPreparationFileStructureFinder implements PreparationFileStructureFinder {

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public PreparationFileStructureRepresentation getPFSList() {

        TypedQuery<PreparationFileStructureRepresentation> query = entityManager.createQuery(
                "SELECT new " + PreparationFileStructureRepresentation.class.getName()
                        + "(pfs.entityId, pfs.version, pfs.modificationDate) FROM PreparationFileStructure pfs ORDER BY pfs.entityId desc",
                PreparationFileStructureRepresentation.class);
        List<PreparationFileStructureRepresentation> preparationList = query.getResultList();
        if (!preparationList.isEmpty())
            return preparationList.get(0);

        return null;
    }

    @Override
    public PreparationFileStructure getLatestPrepFileStructure() {

        TypedQuery<PreparationFileStructure> query = entityManager
                .createQuery("SELECT pfs FROM PreparationFileStructure pfs ORDER BY pfs.entityId desc", PreparationFileStructure.class);

        List<PreparationFileStructure> pfs = query.getResultList();

        if (pfs != null && !pfs.isEmpty()) {
            return query.getResultList().get(0);
        }

        return null;

    }

}
