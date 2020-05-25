/*
 * Creation : Jan 23, 2017
 */
package com.inetpsa.poc00.typeapprovalarea;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import com.inetpsa.poc00.application.typeapprovalarea.TypeApprovalAreaService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaFactory;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository;

@RunWith(SeedITRunner.class)
public class TypeApprovalServiceIT {

    @Inject

    TypeApprovalAreaRepository typeApprovalRepo;

    @Inject

    TypeApprovalAreaFactory typeApprovalFactory;

    @Inject
    TypeApprovalAreaService typeApprovalService;

    @Test
    @WithUser(id = "poch1", password = "poch1")

    public void saveTypeApprovalArea() {

        TypeApprovalArea typeApprobalArea = typeApprovalFactory.createTypeApprovalArea();
        String response = typeApprovalService.saveTypeApprovalArea(typeApprobalArea);
        assertEquals(response, ConstantsApp.SUCCESS);
        typeApprobalArea.setLabel("TypeApproval");
        response = typeApprovalService.saveTypeApprovalArea(typeApprobalArea);
        assertEquals(response, ConstantsApp.SUCCESS);
        TypeApprovalArea newTypeApprobalArea = typeApprovalFactory.createTypeApprovalArea();
        newTypeApprobalArea.setLabel("TypeApproval");
        typeApprovalService.saveTypeApprovalArea(newTypeApprobalArea);
        assertEquals(typeApprobalArea.getLabel(), newTypeApprobalArea.getLabel());

    }

    @Test
    @WithUser(id = "poch1", password = "poch1")
    public void deleteTypeApprovalArea() {

        TypeApprovalArea typeApprobalArea = typeApprovalFactory.createTypeApprovalArea();
        typeApprobalArea.setLabel("TypeApproval");
        typeApprovalService.saveTypeApprovalArea(typeApprobalArea);
        List<TypeApprovalArea> typeApprovalList = typeApprovalRepo.getTypeApprovalAreaByLabel(typeApprobalArea.getLabel());
        typeApprovalService.deleteTypeApprovalArea(typeApprovalList.get(0).getEntityId());
        TypeApprovalArea loadedTypeApproval = typeApprovalRepo.loadTypeApproval(typeApprovalList.get(0).getEntityId());
        assertEquals(loadedTypeApproval, null);

    }

}
