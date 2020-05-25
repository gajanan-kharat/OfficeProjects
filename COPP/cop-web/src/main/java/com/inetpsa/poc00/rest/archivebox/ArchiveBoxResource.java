/*
 * Creation : Feb 9, 2017
 */
package com.inetpsa.poc00.rest.archivebox;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.archivebox.ArchiveBoxService;
import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;

import net.minidev.json.JSONObject;

/**
 * The Class ArchiveBoxResource.
 */
@Path("/archiveboxs")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ArchiveBoxResource {

    /** The archive box service. */
    @Inject
    private ArchiveBoxService archiveBoxService;

    /** The archive box finder. */
    @Inject
    private ArchiveBoxFinder archiveBoxFinder;

    /** The archive box assembler. */
    @Inject
    private ArchiveBoxAssembler archiveBoxAssembler;

    /** The type of test repo. */
    @Inject
    private TypeOfTestRepository typeOfTestRepo;

    /** The pcf repo. */
    @Inject
    private ProjectCodeFamilyRepository pcfRepo;

    /** The fuel repo. */
    @Inject
    private FuelRepository fuelRepo;

    /**
     * Removes the vehicle from basket.
     *
     * @param typeOfTestId the type of test id
     * @param modelYear the model year
     * @param projectFamilyLabel the project family label
     * @param fuelLabel the fuel label
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/archivebox/{typeOfTestId}/{modelYear}/{projectFamilyLabel}/{fuelLabel}")
    public Response createArchiveBox(@PathParam("typeOfTestId") Long typeOfTestId, @PathParam("modelYear") String modelYear,
            @PathParam("projectFamilyLabel") String projectFamilyLabel, @PathParam("fuelLabel") String fuelLabel) {

        TypeOfTest typeOfTest = typeOfTestRepo.load(typeOfTestId);

        ProjectCodeFamily pcfObj = pcfRepo.getProjecCodeFamilybyLabel(projectFamilyLabel);

        Fuel fuelObj = fuelRepo.getFuelByLabel(fuelLabel).get(0);

        JSONObject jsontoRespnse = new JSONObject();

        ArchiveBox archiveBox = archiveBoxFinder.searchArchiveBox(typeOfTestId, modelYear, pcfObj.getEntityId(), fuelObj.getEntityId());
        if (archiveBox != null) {
            jsontoRespnse.put("archiveBoxNumber", archiveBox.getArchiveBoxNumber());
            jsontoRespnse.put("alreadyExist", true);
        } else {

            ArchiveBox archiveBoxCreated = archiveBoxService.createArchiveBox(typeOfTest, modelYear, pcfObj, fuelObj);
            jsontoRespnse.put("archiveBoxNumber", archiveBoxCreated.getArchiveBoxNumber());
            jsontoRespnse.put("alreadyExist", false);
        }

        return Response.ok(jsontoRespnse).build();

    }

    /**
     * Gets the archive box.
     *
     * @param archiveBoxNumber the archive box number
     * @return the archive box
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/archivebox/{archiveBoxNumber}")
    public Response getArchiveBox(@PathParam("archiveBoxNumber") Long archiveBoxNumber) {

        ArchiveBox archiveBox = archiveBoxFinder.getArchiveBoxByNumber(archiveBoxNumber);

        ArchiveBoxRepresentation archiveBoxRep = new ArchiveBoxRepresentation();

        if (archiveBox == null)
            return Response.status(Status.PRECONDITION_FAILED).build();

        archiveBoxAssembler.doAssembleDtoFromAggregate(archiveBoxRep, archiveBox);

        return Response.ok(archiveBoxRep).build();

    }

    /**
     * Gets the archive boxby fields.
     *
     * @param typeOfTestId the type of test id
     * @param modelYear the model year
     * @param projectFamilyLabel the project family label
     * @param fuelLabel the fuel label
     * @return the archive boxby fields
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/archiveboxbyfields/{typeOfTestId}/{modelYear}/{projectFamilyLabel}/{fuelLabel}")
    public Response getArchiveBoxbyFields(@PathParam("typeOfTestId") Long typeOfTestId, @PathParam("modelYear") String modelYear,
            @PathParam("projectFamilyLabel") String projectFamilyLabel, @PathParam("fuelLabel") String fuelLabel) {

        ProjectCodeFamily pcfObj = pcfRepo.getProjecCodeFamilybyLabel(projectFamilyLabel);

        Fuel fuelObj = fuelRepo.getFuelByLabel(fuelLabel).get(0);

        ArchiveBox archiveBox = archiveBoxFinder.searchArchiveBox(typeOfTestId, modelYear, pcfObj.getEntityId(), fuelObj.getEntityId());
        ArchiveBoxRepresentation archiveBoxRep = new ArchiveBoxRepresentation();
        if (archiveBox == null)
            return Response.status(Status.PRECONDITION_FAILED).build();

        archiveBoxAssembler.doAssembleDtoFromAggregate(archiveBoxRep, archiveBox);

        return Response.ok(archiveBoxRep).build();

    }

    /**
     * Close archive box.
     *
     * @param archiveBoxId the archive box id
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("archive/{archiveBoxId}")
    public Response closeArchiveBox(@PathParam("archiveBoxId") Long archiveBoxId) {

        archiveBoxService.closeArchiveBox(archiveBoxId);

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/archiveboxlist")
    public Response getArchiveBoxList() {

        List<ArchiveBox> archiveBoxList = archiveBoxFinder.getAllArchiveBox();

        List<ArchiveBoxRepresentation> archiveBoxReprList = new ArrayList<>();

        for (ArchiveBox archiveBox : archiveBoxList) {

            ArchiveBoxRepresentation archiveBoxRepr = new ArchiveBoxRepresentation();
            archiveBoxAssembler.doAssembleDtoFromAggregate(archiveBoxRepr, archiveBox);

            archiveBoxReprList.add(archiveBoxRepr);
        }

        return Response.ok(archiveBoxReprList).build();
    }

}
