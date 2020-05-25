/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemFinder;
import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemRepresentation;

public class JpaPreparationItemFinder implements PreparationItemFinder {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<PreparationItemRepresentation> getPreparationItembygpcId(Long gpcId) {
        TypedQuery<PreparationItemRepresentation> query = entityManager.createQuery("SELECT new " + PreparationItemRepresentation.class.getName()
                + "(gpi.entityId, gpi.label, gpi.helpText, gpi.unit,gpi.dataType,gpi.order,gpi.mandatory,gpi.authorizedComment) FROM GenericPreparationItem gpi where gpi.genericPreparationChecklist.entityId ="
                + gpcId, PreparationItemRepresentation.class);
        return query.getResultList();
    }

}
