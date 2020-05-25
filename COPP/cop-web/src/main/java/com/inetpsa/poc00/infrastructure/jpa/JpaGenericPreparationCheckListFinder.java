/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListFinder;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListRepresentation;

public class JpaGenericPreparationCheckListFinder implements GenericPreparationCheckListFinder {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<GenericPreparationCheckListRepresentation> getGPCListbypfsId(Long pfsId) {
        TypedQuery<GenericPreparationCheckListRepresentation> query = entityManager
                .createQuery("SELECT new " + GenericPreparationCheckListRepresentation.class.getName()
                        + "(gpc.entityId,gpc.label,gpc.order,gpc.description,gpc.enabled) FROM GenericPreparationChecklist gpc where gpc.preparationFileStructure.entityId = "
                        + pfsId, GenericPreparationCheckListRepresentation.class);
        return query.getResultList();
    }

}
