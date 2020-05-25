package com.inetpsa.eds.ws.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGround;
import com.inetpsa.eds.dao.model.EdsGroundConsolidate;
import com.inetpsa.eds.dao.model.EdsGroundRobustCurent;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsMissionProfil;
import com.inetpsa.eds.dao.model.EdsMissionProfilFormData;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.ws.model.Current;
import com.inetpsa.eds.ws.model.Eds;
import com.inetpsa.eds.ws.model.EdsConsolidatedType;
import com.inetpsa.eds.ws.model.EdsMotorState;
import com.inetpsa.eds.ws.model.EdsSupplyValues;
import com.inetpsa.eds.ws.model.EdsTemp;
import com.inetpsa.eds.ws.model.EdsTension;
import com.inetpsa.eds.ws.model.EdsValue;
import com.inetpsa.eds.ws.model.EdsValueConsolidated;
import com.inetpsa.eds.ws.model.EdsValueConsolidatedKey;
import com.inetpsa.eds.ws.model.EdsValueKey;
import com.inetpsa.eds.ws.model.EdsValueMetadata;
import com.inetpsa.eds.ws.model.EdsValueRobust;
import com.inetpsa.eds.ws.model.EdsValueRobustKey;
import com.inetpsa.eds.ws.model.EdsValueType;
import com.inetpsa.eds.ws.model.EdsVersion;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;
import com.inetpsa.eds.ws.model.EdsesResponse;
import com.inetpsa.eds.ws.model.Stage;
import com.inetpsa.eds.ws.model.Supply;
import com.inetpsa.eds.ws.model.User;
import com.inetpsa.eds.ws.model.Version;
import com.inetpsa.eds.ws.model.Wording;

/**
 * Ws resource for getting Edses list of one project RESSOURCE FOR OPHELIE GS
 * 
 * @author ALTER-FRAME - Clément Hémidy <chemidy@alter-frame.com>
 */
@Path("/edses")
public class GsEdsesRessource {

    /**
     * Get response
     * 
     * @param tokenId Token id for authentication
     * @param name Name of project
     * @param request Request object
     * @return EdsesResponse object
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public EdsesResponse getProjectData(@QueryParam("token-id") String tokenId, @QueryParam("project-name") String name,
            @Context HttpServletRequest request) {
        EdsesResponse response = null;
        if (tokenId != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Token control
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().after(new Date())
                    && token.getWstSource().equals(EdsWsSessionToken.SOURCE_GS)) {

                Date startDate = new Date();
                response = getEdsesResponseFrom(token, name);
                ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                        "EDS_WS_" + token.getWstSource());
            }

        }

        return response;
    }

    /**
     * Gets the eds from.
     * 
     * @param eds the eds
     * @return the eds from
     */
    public Eds getEdsFrom(EdsEds eds) {
        Eds e = new Eds();

        // Name
        e.setName(eds.getEdsName());

        // Description
        e.setDescription(eds.getEdsDescription());

        // Reference
        e.setRef(eds.getEdsRef());

        // List of supplies
        e.setSupplies(new ArrayList<Supply>(getAllSupplies(eds).values()));

        // ConnectEvo4 - All EDS by versions

        // List of versions
        List<Object[]> test = EDSdao.getInstance().getAllEdsVersionsValuesByRef(eds.getEdsRef());

        for (Object[] versionRaw : test) {
            Integer major = (Integer) versionRaw[0];
            Integer minor = (Integer) versionRaw[1];
            Integer state = (Integer) versionRaw[2];
            EdsValueType stage = getEdsValueType((Integer) versionRaw[3]);

            EdsVersion edsVersion = new EdsVersion();
            edsVersion.setMajorVersion(major);
            edsVersion.setMinorVersion(minor);
            edsVersion.setState(state);
            edsVersion.setStage(stage);

            e.addVersion(edsVersion);
        }

        // Project ref
        e.setProjectRef(eds.getEdsProject().getPName());

        // End of connectEvo4

        // ConnectEvo3 - New field in the web service

        // Description (former name)
        e.setDescription(eds.getEdsDescription());

        // Officer
        // VCOAMS-112 GL code Modification START. Date:24-February-2017
        if (eds.getEdsUserByEdsOfficerId() != null && getUserFromEdsUser(eds.getEdsUserByEdsOfficerId()) != null) {
            e.setOfficer(getUserFromEdsUser(eds.getEdsUserByEdsOfficerId()));
        } else {
            User user = new User();
            user.setFirstName(StringUtils.EMPTY);
            user.setLastName(StringUtils.EMPTY);
            user.setLogin(StringUtils.EMPTY);
            user.setRole(StringUtils.EMPTY);
            e.setOfficer(user);
        }
        // VCOAMS-112 GL code Modification END. Date:24-February-2017
        // Admin
        e.setAdmin(getUserFromEdsUser(eds.getEdsUserByEdsAdminId()));

        // Supplier
        e.setSupplier(eds.getEdsSupplier() != null ? eds.getEdsSupplier().getSName() : null);

        // Version (raw)
        e.setMinorVersion(new Integer(eds.getEdsMinorVersion()));
        e.setMajorVersion(new Integer(eds.getEdsMajorVersion()));

        // Component type
        e.setComponentType(eds.getEdsComponentType().getCtName().getValueByLocale(Locale.FRENCH));

        // Version
        e.setVersion(eds.getEdsMajorVersion() + "." + eds.getEdsMinorVersion());

        // State
        e.setState(eds.getEdsState());
        // End of ConnectEvo3

        // ConnectEvo6 - EDS values needs to be exported in an easy-to-use way, but for code legacy purpose the old supply list
        // is still maintained.
        e.setSupplyValues(getSuppliesValues(eds, e));

        // Number of activation
        setEdsActivationNumbers(eds, e);

        // Wave frequency
        setEdsWaveFrequency(eds, e);

        // End of ConnectEvo6

        // Add stage info to the EDS
        e.setStage(getEdsValueType(retrieveEdsStage(eds)));
        return e;
    }

    private void setEdsWaveFrequency(EdsEds eds, Eds e) {
        EdsComportementConsolideFormData edsComportementConsolideFormData = EdsApplicationController.getFormDataModel(eds,
                EdsComportementConsolideFormData.class);

        if (edsComportementConsolideFormData != null) {
            // Plage Frequence
            EdsValueKey key = new EdsValueKey("plage-frequence", "", EdsValueType.IGNORED);
            EdsValue value = new EdsValue(key, 0F, edsComportementConsolideFormData.getCocofdPlageFrequence());
            EdsValueMetadata metadata = new EdsValueMetadata(true, eds.getEdsMajorVersion(), eds.getEdsMinorVersion());
            value.setMetadata(metadata);

            e.setPlageFrequence(value);

            // Type pilotage
            key = new EdsValueKey("type-pilotage", "", EdsValueType.IGNORED);
            value = new EdsValue(key, 0F, edsComportementConsolideFormData.getCocofdTypePilotage());
            metadata = new EdsValueMetadata(true, eds.getEdsMajorVersion(), eds.getEdsMinorVersion());
            value.setMetadata(metadata);

            e.setTypePilotage(value);
        }
    }

    /**
     * This method is used to retrieve Stage name.
     * 
     * @return Stage name.
     */
    private String getEdsStageText(EdsEds eds) {
        int stage = retrieveEdsStage(eds);
        return EdsApplicationController.getStageText(stage);
    }

    /**
     * @param eds
     * @return
     */
    private int retrieveEdsStage(EdsEds eds) {
        int stage = EdsApplicationController.PRELIM_STAGE;

        if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {

            stage = EdsApplicationController.getFormDataModel(eds, EdsLowValidationFormData.class).getValidationStage();
        } else if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
            stage = EdsApplicationController.getFormDataModel(eds, EdsHighValidationFormData.class).getValidationStage();
        }

        return stage;
    }

    /**
     * @param stage
     * @return
     */
    private EdsValueType getEdsValueType(int stage) {
        switch (stage) {
        case 0:
            return EdsValueType.PRELIMINARY;
        case 1:
            return EdsValueType.ROBUST;
        case 2:
            return EdsValueType.CONSOLIDATED;
        case 3:
            return EdsValueType.CLOSED;

        default:
            return EdsValueType.IGNORED;
        }
    }

    /**
     * Sets the eds activation numbers.
     * 
     * @param eds the eds
     * @param e the e
     */
    private void setEdsActivationNumbers(EdsEds eds, Eds e) {
        EdsMissionProfilFormData edsMissionProfilFormData = EdsApplicationController.getFormDataModel(eds, EdsMissionProfilFormData.class);

        if (edsMissionProfilFormData != null) {
            for (EdsMissionProfil edsProfil : edsMissionProfilFormData.getEdsMissionProfils()) {
                EdsValueKey key = new EdsValueKey("activation-number", edsProfil.getMpNomProfil(), EdsValueType.IGNORED);
                EdsValue value = new EdsValue(key, edsProfil.getMpNombreActivation(), edsProfil.getMpCommentaire());
                EdsValueMetadata metadata = new EdsValueMetadata(true, eds.getEdsMajorVersion(), eds.getEdsMinorVersion());
                value.setMetadata(metadata);

                e.getActivations().add(value);
            }
        }
    }

    /**
     * Sets the eds robust supply.
     * 
     * @param supplyValues the supply values
     * @param robustSupply the robust supply
     */
    private void setEdsRobustSupply(Eds e, EdsSupplyValues supplyValues, EdsRobustSupply robustSupply) {

        // Inomstab
        EdsValueRobustKey key = new EdsValueRobustKey("i-nomstab", robustSupply.getRsedsUseCaseName());
        EdsValueRobust val = new EdsValueRobust(key, robustSupply.getRsedsINomStab(), robustSupply.getRsedsINomStabComment());
        supplyValues.addRobustValue(val, e);

        // Tnomstab
        key = new EdsValueRobustKey("t-nomstab", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsTNomStab(), robustSupply.getRsedsTnomStabComment());
        supplyValues.addRobustValue(val);

        // IpireCas
        key = new EdsValueRobustKey("i-pirecas", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsIPirecasStab(), robustSupply.getRsedsIPirecasComment());
        supplyValues.addRobustValue(val);

        // Tpirecas
        key = new EdsValueRobustKey("t-pirecas", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsTPireCas(), robustSupply.getRsedsTpireCasComment());
        supplyValues.addRobustValue(val);

        // Idysf
        key = new EdsValueRobustKey("i-dysf", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsIdysf(), robustSupply.getRsedsIdysfComment());
        supplyValues.addRobustValue(val);

        // Tdysf
        key = new EdsValueRobustKey("t-dysf", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsTdysf(), robustSupply.getRsedsTdysfComment());
        supplyValues.addRobustValue(val);

        // Imst
        key = new EdsValueRobustKey("i-mst", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsImst(), robustSupply.getRsedsImstComment());
        supplyValues.addRobustValue(val);

        // Tmst
        key = new EdsValueRobustKey("t-mst", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsTmst(), robustSupply.getRsedsTmstComment());
        supplyValues.addRobustValue(val);

        // Imax
        key = new EdsValueRobustKey("i-maxpulse", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsIMax(), robustSupply.getRsedsIMaxComment());
        supplyValues.addRobustValue(val);

        // Tmax
        key = new EdsValueRobustKey("t-maxpulse", robustSupply.getRsedsUseCaseName());
        val = new EdsValueRobust(key, robustSupply.getRsedsTIMax(), robustSupply.getRsedsTIMaxComment());
        supplyValues.addRobustValue(val);

        // Nb activation
        // TODO

        // Forme d'onde
        // TODO

        // Each use cases
        for (EdsRobustSupplyUseCase robustSupplyUseCase : robustSupply.getUseCases()) {
            // Inomstab
            key = new EdsValueRobustKey("i-nomstab", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucINomStab(), robustSupplyUseCase.getRsucINomStabComment());
            supplyValues.addRobustValue(val);

            // Tnomstab
            key = new EdsValueRobustKey("t-nomstab", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucTNomStab(), robustSupplyUseCase.getRsucTnomStabComment());
            supplyValues.addRobustValue(val);

            // IpireCas
            key = new EdsValueRobustKey("i-pirecas", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucIPirecasStab(), robustSupplyUseCase.getRsucIPirecasComment());
            supplyValues.addRobustValue(val);

            // Tpirecas
            key = new EdsValueRobustKey("t-pirecas", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucTPireCas(), robustSupplyUseCase.getRsucTpireCasComment());
            supplyValues.addRobustValue(val);

            // Idysf
            key = new EdsValueRobustKey("i-dysf", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucIdysf(), robustSupplyUseCase.getRsucIdysfComment());
            supplyValues.addRobustValue(val);

            // Tdysf
            key = new EdsValueRobustKey("t-dysf", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucTdysf(), robustSupplyUseCase.getRsucTdysfComment());
            supplyValues.addRobustValue(val);

            // Imst
            key = new EdsValueRobustKey("i-mst", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucImst(), robustSupplyUseCase.getRsucImstComment());
            supplyValues.addRobustValue(val);

            // Tmst
            key = new EdsValueRobustKey("t-mst", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucTmst(), robustSupplyUseCase.getRsucTmstComment());
            supplyValues.addRobustValue(val);

            // Imax
            key = new EdsValueRobustKey("i-maxpulse", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucIMax(), robustSupplyUseCase.getRsucIMaxComment());
            supplyValues.addRobustValue(val);

            // Tmax
            key = new EdsValueRobustKey("t-maxpulse", robustSupplyUseCase.getRsucName());
            val = new EdsValueRobust(key, robustSupplyUseCase.getRsucTIMax(), robustSupplyUseCase.getRsucTIMaxComment());
            supplyValues.addRobustValue(val);

            // Nb activation
            // TODO

            // Forme d'onde
            // TODO
        }
    }

    /**
     * Sets the eds robust supply.
     * 
     * @param supplyValues the supply values
     * @param robustSupply the robust supply
     */
    private void setEdsRobustGroundSupply(Eds e, EdsSupplyValues supplyValues, EdsGroundRobustCurent groundRobustSupply) {

        // Inomstab
        EdsValueRobustKey key = new EdsValueRobustKey("i-nomstab", groundRobustSupply.getGsedsTitreMasse());
        EdsValueRobust val = new EdsValueRobust(key, groundRobustSupply.getGsedsINomStab(), groundRobustSupply.getGsedsINomStabComment());
        supplyValues.addRobustValue(val, e);

        // Tnomstab
        key = new EdsValueRobustKey("t-nomstab", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsTnomStab(), groundRobustSupply.getGsedsTnomStabComment());
        supplyValues.addRobustValue(val);

        // IpireCas
        key = new EdsValueRobustKey("i-pirecas", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsIPirecasStab(), groundRobustSupply.getGsedsIPirecasComment());
        supplyValues.addRobustValue(val);

        // Tpirecas
        key = new EdsValueRobustKey("t-pirecas", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsTpireCas(), groundRobustSupply.getGsedsTpireCasComment());
        supplyValues.addRobustValue(val);

        // Idysf
        key = new EdsValueRobustKey("i-dysf", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsIdysf(), groundRobustSupply.getGsedsIdysfComment());
        supplyValues.addRobustValue(val);

        // Tdysf
        key = new EdsValueRobustKey("t-dysf", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsTdysf(), groundRobustSupply.getGsedsTdysfComment());
        supplyValues.addRobustValue(val);

        // Imst
        key = new EdsValueRobustKey("i-mst", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsIMst(), groundRobustSupply.getGsedsImstComment());
        supplyValues.addRobustValue(val);

        // Tmst
        key = new EdsValueRobustKey("t-mst", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsTmst(), groundRobustSupply.getGsedsTmstComment());
        supplyValues.addRobustValue(val);

        // Imax
        key = new EdsValueRobustKey("i-maxpulse", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsIPic(), groundRobustSupply.getGsedsIPicComment());
        supplyValues.addRobustValue(val);

        // Tmax
        key = new EdsValueRobustKey("t-maxpulse", groundRobustSupply.getGsedsTitreMasse());
        val = new EdsValueRobust(key, groundRobustSupply.getGsedsTIpic(), groundRobustSupply.getGsedsIPicComment());
        supplyValues.addRobustValue(val);
    }

    /**
     * Sets the eds courant appelle activation.
     * 
     * @param e
     * @param supplyValues the supply values
     * @param courantAppelleActivation the courant appelle activation
     */
    private void setEdsCourantAppelleActivation(Eds e, EdsSupplyValues supplyValues, EdsCourantAppelleActivation courantAppelleActivation,
            EdsConsolidatedType type) {

        // Imaxpulse - Tmin
        EdsValueConsolidatedKey key = new EdsValueConsolidatedKey();
        key.ID = "i-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        EdsValueConsolidated val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTminImaxPulse(),
                courantAppelleActivation.getCaaedsTmoyImaxPulseComment());
        supplyValues.addConsolidatedValue(val, e);

        // Imaxpulse - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTmoyImaxPulse(),
                courantAppelleActivation.getCaaedsTmoyImaxPulseComment());
        supplyValues.addConsolidatedValue(val, e);

        // Imaxpulse - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTmaxImaxPulse(),
                courantAppelleActivation.getCaaedsTmoyImaxPulseComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tmaxpulse - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTminDtPulse(), courantAppelleActivation.getCaaedsTmoyDtPulseComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tmaxpulse - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTmoyDtPulse(), courantAppelleActivation.getCaaedsTmoyDtPulseComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tmaxpulse - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-maxpulse";
        key.useCase = courantAppelleActivation.getCaaedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantAppelleActivation.getCaaedsTmaxDtPulse(), courantAppelleActivation.getCaaedsTmoyDtPulseComment());
        supplyValues.addConsolidatedValue(val, e);
    }

    /**
     * Sets the eds courant bloque courant dysfonctionnement.
     * 
     * @param e
     * @param supplyValues the supply values
     * @param courantBloqueCourantDysf the courant bloque courant dysf
     */
    private void setEdsCourantBloqueCourantDysfonctionnement(Eds e, EdsSupplyValues supplyValues,
            EdsCourantBloqueCourantDysfonctionnement courantBloqueCourantDysf, EdsConsolidatedType type) {

        // Idysf - Tmin
        EdsValueConsolidatedKey key = new EdsValueConsolidatedKey();
        key.ID = "i-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        EdsValueConsolidated val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTminIdys(),
                courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);

        // Idysf - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTmoyIdys(), courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);

        // Idysf - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTmaxIdys(), courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tdysf - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTminTdys(), courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tdys - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTmoyTdys(), courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tdys - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-dysf";
        key.useCase = courantBloqueCourantDysf.getCbcdedsTitre();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantBloqueCourantDysf.getCbcdedsTmaxTdys(), courantBloqueCourantDysf.getCbcdedsComment());
        supplyValues.addConsolidatedValue(val, e);
    }

    /**
     * Sets the eds courant mise sous tension.
     * 
     * @param e
     * @param supplyValues the supply values
     * @param courantMiseSousTensions the courant mise sous tensions
     */
    private void setEdsCourantMiseSousTension(Eds e, EdsSupplyValues supplyValues, EdsCourantMiseSousTension courantMiseSousTensions,
            EdsConsolidatedType type) {

        // Imst - Tmoy
        EdsValueConsolidatedKey key = new EdsValueConsolidatedKey();
        key.ID = "i-mst";
        key.useCase = courantMiseSousTensions.getCmstedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        EdsValueConsolidated val = new EdsValueConsolidated(key, courantMiseSousTensions.getCmstedsTpirecasImst(),
                courantMiseSousTensions.getCmstedsTpirecasImstComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tmst - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-mst";
        key.useCase = courantMiseSousTensions.getCmstedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.IGNORED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantMiseSousTensions.getCmstedsTpirecasDt(), courantMiseSousTensions.getCmstedsTpirecasDtComment());
        supplyValues.addConsolidatedValue(val, e);
    }

    /**
     * Sets the eds courant nominale activation.
     * 
     * @param e
     * @param supplyValues the supply values
     * @param courantNominaleActivations the courant nominale activations
     */
    private void setEdsCourantNominaleActivation(Eds e, EdsSupplyValues supplyValues, EdsCourantNominaleActivation courantNominaleActivations,
            EdsConsolidatedType type) {

        // Inomstab - Motor not running - Tmin
        EdsValueConsolidatedKey key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        EdsValueConsolidated val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminStabMnt(),
                courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor not running - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminStabMnt(), courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor not running- Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyStabMnt(), courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor not running- Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyStabMnt(), courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor not running- Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxStabMnt(), courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor not running- Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxStabMnt(), courantNominaleActivations.getCnaedsStabMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 10.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 10.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 10.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 10.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 10.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 10.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxStab10Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 13.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 13.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 13.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 13.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 13.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 13.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxStab13Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 15.2V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 15.2V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 15.2V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 15.2V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Inomstab - Motor running - 15.2V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tnomstab - Motor running - 15.2V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-nomstab";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxStab15Mt(),
                courantNominaleActivations.getCnaedsStab10MtComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor not running - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor not running - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor not running- Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor not running- Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor not running- Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor not running- Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.STOPPED;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxPireCasMnt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 10.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 10.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 10.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 10.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 10.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 10.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMIN;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxPireCas10Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 13.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 13.5V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val);

        // Ipirecas - Motor running - 13.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 13.5V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 13.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 13.5V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMOY;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxPireCas13Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 15.2V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsIminPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 15.2V - Tmin
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.LOW;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTminPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 15.2V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImoyPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 15.2V - Tmoy
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.MEDIUM;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmoyPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Ipirecas - Motor running - 15.2V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "i-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsImaxPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);

        // Tpirecas - Motor running - 15.2V - Tmax
        key = new EdsValueConsolidatedKey();
        key.ID = "t-pirecas";
        key.useCase = courantNominaleActivations.getCnaedsName();
        key.consolidatedType = type;
        key.motorState = EdsMotorState.RUNNING;
        key.tension = EdsTension.UMAX;
        key.temp = EdsTemp.HIGH;
        val = new EdsValueConsolidated(key, courantNominaleActivations.getCnaedsTmaxPireCas15Mt(),
                courantNominaleActivations.getCnaedsPireCasMntComment());
        supplyValues.addConsolidatedValue(val, e);
    }

    /**
     * Compute the supplies values for a given EDS. This method will iterate over every supply of every state to export, and compute a map of values
     * by supplies, containing all the values to export for each supply.
     * 
     * @param eds The EDS to use.
     * @param e The EDS to export, used to generate metadata for the values.
     * @return The list of supply valued, ordered by supply names.
     */
    private Map<String, EdsSupplyValues> getSuppliesValues(EdsEds eds, Eds e) {
        Map<String, EdsSupplyValues> values = new LinkedHashMap<String, EdsSupplyValues>();

        // For now - Only get robust and consolidated state

        // Robust values
        EdsRobustCurentFormData edsRobustData = EdsApplicationController.getFormDataModel(eds, EdsRobustCurentFormData.class);

        for (EdsSupply supply : edsRobustData.getEdsSupplies()) {
            // Get robust supply
            EdsRobustSupply robustSupply = supply.getEdsRobustSupply();

            if (robustSupply == null)
                continue; // Ignore null supply

            // Get the supply value by its name
            EdsSupplyValues supplyValues = values.get(supply.getSedsSupplyName());

            if (supplyValues == null) {
                supplyValues = new EdsSupplyValues();
                supplyValues.setName(supply.getSedsSupplyName());
                values.put(supply.getSedsSupplyName(), supplyValues);
            }

            setEdsRobustSupply(e, supplyValues, robustSupply);
        }

        for (EdsGround edsGround : edsRobustData.getEdsGrounds()) {
            // Get robust supply ground
            EdsGroundRobustCurent groundRobustCurent = edsGround.getEdsGroundRobustCurent();

            if (groundRobustCurent == null)
                continue; // Ignore null supply

            // Get ground supply
            EdsSupplyValues supplyValues = values.get("Masse");

            if (supplyValues == null) {
                supplyValues = new EdsSupplyValues();
                supplyValues.setName("Masse");
                values.put("Masse", supplyValues);
            }

            setEdsRobustGroundSupply(e, supplyValues, groundRobustCurent);
        }

        // Consolidated value
        EdsConsolidateCurentFormData edsConsolidateData = EdsApplicationController.getFormDataModel(eds, EdsConsolidateCurentFormData.class);

        // Supplies
        for (EdsSupply supply : edsConsolidateData.getEdsSupplies()) {
            // Get the consolidated supply
            EdsConsolidateSupply consolidatedSupply = supply.getEdsConsolidateSupply();

            if (consolidatedSupply == null)
                continue; // Ignore null supply

            // Get the supply value by its name
            EdsSupplyValues supplyValues = values.get(supply.getSedsSupplyName());

            if (supplyValues == null) {
                supplyValues = new EdsSupplyValues();
                supplyValues.setName(supply.getSedsSupplyName());
                values.put(supply.getSedsSupplyName(), supplyValues);
            }

            // Theorical values
            EdsConsolidateSupplyTheoritic consolidatedSupplyTheoric = consolidatedSupply.getEdsConsolidateSupplyTheoritic();

            if (consolidatedSupplyTheoric != null) {

                // "Courant d'appel à l'activation"
                for (EdsCourantAppelleActivation courantAppelleActivation : consolidatedSupplyTheoric.getEdsCourantAppelleActivations()) {
                    setEdsCourantAppelleActivation(e, supplyValues, courantAppelleActivation, EdsConsolidatedType.THEORETICAL);
                }

                // "Courant couple bloqué / Courant dysfonctionnel"
                for (EdsCourantBloqueCourantDysfonctionnement courantBloqueCourantDysf : consolidatedSupplyTheoric
                        .getEdsCourantBloqueCourantDysfonctionnements()) {
                    setEdsCourantBloqueCourantDysfonctionnement(e, supplyValues, courantBloqueCourantDysf, EdsConsolidatedType.THEORETICAL);
                }

                // "Courant de mise sous tension"
                for (EdsCourantMiseSousTension courantMiseSousTensions : consolidatedSupplyTheoric.getEdsCourantMiseSousTensions()) {
                    setEdsCourantMiseSousTension(e, supplyValues, courantMiseSousTensions, EdsConsolidatedType.THEORETICAL);
                }

                // "Courant nominal à l'activation"
                for (EdsCourantNominaleActivation courantNominaleActivations : consolidatedSupplyTheoric.getEdsCourantNominaleActivations()) {
                    setEdsCourantNominaleActivation(e, supplyValues, courantNominaleActivations, EdsConsolidatedType.THEORETICAL);
                }
            }

            // Measured values
            EdsConsolidateSupplyMesure consolidatedSupplyMesure = consolidatedSupply.getEdsConsolidateSupplyMesure();

            if (consolidatedSupplyMesure != null) {
                // "Courant d'appel à l'activation"
                for (EdsCourantAppelleActivation courantAppelleActivation : consolidatedSupplyMesure.getEdsCourantAppelleActivationMesures()) {
                    setEdsCourantAppelleActivation(e, supplyValues, courantAppelleActivation, EdsConsolidatedType.MEASURED);
                }

                // "Courant couple bloqué / Courant dysfonctionnel"
                for (EdsCourantBloqueCourantDysfonctionnement courantBloqueCourantDysf : consolidatedSupplyMesure
                        .getEdsCourantBloqueCourantDysfonctionnementMesures()) {
                    setEdsCourantBloqueCourantDysfonctionnement(e, supplyValues, courantBloqueCourantDysf, EdsConsolidatedType.MEASURED);
                }

                // "Courant de mise sous tension"
                for (EdsCourantMiseSousTension courantMiseSousTensions : consolidatedSupplyMesure.getEdsCourantMiseSousTensionMesures()) {
                    setEdsCourantMiseSousTension(e, supplyValues, courantMiseSousTensions, EdsConsolidatedType.MEASURED);
                }

                // "Courant nominal à l'activation"
                for (EdsCourantNominaleActivation courantNominaleActivations : consolidatedSupplyMesure.getEdsCourantNominaleActivationMesures()) {
                    setEdsCourantNominaleActivation(e, supplyValues, courantNominaleActivations, EdsConsolidatedType.MEASURED);
                }
            }

            // Wave form
            // TODO

            // Frequency
            // TODO

        }

        // Ground
        if (edsConsolidateData.getEdsGroundConsolidateCurent() != null) {
            for (EdsGroundConsolidate consolidatedGroundSupply : edsConsolidateData.getEdsGroundConsolidateCurent().getEdsGroundConsolidates()) {

                // Get the supply value by its name
                EdsSupplyValues supplyValues = values.get("Masse");

                if (supplyValues == null) {
                    supplyValues = new EdsSupplyValues();
                    supplyValues.setName("Masse");
                    values.put("Masse", supplyValues);
                }

                // "Courant d'appel à l'activation"
                for (EdsCourantAppelleActivation courantAppelleActivation : consolidatedGroundSupply.getEdsCourantAppelleActivations()) {
                    EdsSupplyValues tmpSupplyValues = new EdsSupplyValues();
                    setEdsCourantAppelleActivation(e, tmpSupplyValues, courantAppelleActivation, EdsConsolidatedType.THEORETICAL);

                    // Update use-cases to match with the ground name
                    for (EdsValue value : tmpSupplyValues.getConsolidatedValues()) {
                        value.getKey().setUseCase(consolidatedGroundSupply.getGedsTitle() + "/" + value.getKey().getUseCase());
                        supplyValues.addConsolidatedValue(value);
                    }
                }

                // "Courant couple bloqué / Courant dysfonctionnel"
                for (EdsCourantBloqueCourantDysfonctionnement courantBloqueCourantDysf : consolidatedGroundSupply
                        .getEdsCourantBloqueCourantDysfonctionnements()) {
                    EdsSupplyValues tmpSupplyValues = new EdsSupplyValues();
                    setEdsCourantBloqueCourantDysfonctionnement(e, tmpSupplyValues, courantBloqueCourantDysf, EdsConsolidatedType.THEORETICAL);

                    // Update use-cases to match with the ground name
                    for (EdsValue value : tmpSupplyValues.getConsolidatedValues()) {
                        value.getKey().setUseCase(consolidatedGroundSupply.getGedsTitle() + "/" + value.getKey().getUseCase());
                        supplyValues.addConsolidatedValue(value);
                    }
                }

                // "Courant de mise sous tension"
                for (EdsCourantMiseSousTension courantMiseSousTensions : consolidatedGroundSupply.getEdsCourantMiseSousTensions()) {
                    EdsSupplyValues tmpSupplyValues = new EdsSupplyValues();
                    setEdsCourantMiseSousTension(e, tmpSupplyValues, courantMiseSousTensions, EdsConsolidatedType.THEORETICAL);

                    // Update use-cases to match with the ground name
                    for (EdsValue value : tmpSupplyValues.getConsolidatedValues()) {
                        value.getKey().setUseCase(consolidatedGroundSupply.getGedsTitle() + "/" + value.getKey().getUseCase());
                        supplyValues.addConsolidatedValue(value);
                    }
                }

                // "Courant nominal à l'activation"
                for (EdsCourantNominaleActivation courantNominaleActivations : consolidatedGroundSupply.getEdsCourantNominaleActivations()) {
                    EdsSupplyValues tmpSupplyValues = new EdsSupplyValues();
                    setEdsCourantNominaleActivation(e, tmpSupplyValues, courantNominaleActivations, EdsConsolidatedType.THEORETICAL);

                    // Update use-cases to match with the ground name
                    for (EdsValue value : tmpSupplyValues.getConsolidatedValues()) {
                        value.getKey().setUseCase(consolidatedGroundSupply.getGedsTitle() + "/" + value.getKey().getUseCase());
                        supplyValues.addConsolidatedValue(value);
                    }
                }
            }
        }

        return values;
    }

    /**
     * Construct response.
     * 
     * @param token Token id.
     * @param name Name of project.
     * @return EdsesResponse.
     */
    private EdsesResponse getEdsesResponseFrom(EdsWsSessionToken token, String name) {
        EdsesResponse response = new EdsesResponse();

        EdsProject project = EDSdao.getInstance().getProjectbyName(name);

        List<String> usedEds = new ArrayList<String>();

        if (project != null) {
            for (EdsEds eds : EDSdao.getInstance().getAllEDSByProject(project)) {

                if (!usedEds.contains(eds.getEdsId())) {
                    response.getEdses().add(getEdsFrom(eds));
                    usedEds.add(eds.getEdsId());
                }
            }
        }

        return response;
    }

    /**
     * Convert an EdsUser to an User to be exported through the web service. If the provided user is null, an empty user will still be returned.
     * 
     * @param user The user to be exported.
     * @return The exported user.
     */
    private User getUserFromEdsUser(EdsUser user) {
        User u = new User();
        if (user != null) {
            u.setFirstName(user.getUFirstname());
            u.setLastName(user.getULastname());
            u.setLogin(user.getUPsaId()); // TODO
            u.setRole(user.getEdsRole().getRoName());
        }

        return u;
    }

    /**
     * Get hashmap of supplies
     * 
     * @param eds EdsEds object (DAO)
     * @return HashMap of String of Supply
     */
    private HashMap<String, Supply> getAllSupplies(EdsEds eds) {
        HashMap<String, Supply> supplies = new HashMap<String, Supply>();

        List<EdsEds> edsVersionsList = EDSdao.getInstance().getAllEdsVersionsByRef(eds.getEdsRef());

        EdsEds lastEds = edsVersionsList.get(edsVersionsList.size() - 1);

        for (EdsEds eds2 : edsVersionsList) {

            if ((lastEds.getEdsMajorVersion() == 1 && lastEds.getEdsMinorVersion() == 0)
                    || (lastEds.getEdsMajorVersion() != eds2.getEdsMajorVersion() || lastEds.getEdsMinorVersion() != eds2.getEdsMinorVersion())) {
                for (EdsSupply supply : EDSdao.getInstance().getAllEdsSuppliesByEdsId(eds2.getEdsId())) {
                    Supply sup = null;
                    if (!supplies.containsKey(supply.getSedsSupplyName())) {
                        sup = new Supply();
                        sup.setName(supply.getSedsSupplyName());
                        sup.setId(supply.getSedsId());
                        supplies.put(supply.getSedsSupplyName(), sup);
                    } else {
                        sup = supplies.get(supply.getSedsSupplyName());
                    }

                    Version version = new Version();
                    version.setMajor(eds2.getEdsMajorVersion());
                    version.setMinor(eds2.getEdsMinorVersion());

                    if (eds2.getEdsOrganFamily() != null) {
                        version.setOrganType(eds2.getEdsOrganFamily().getValueByLocale(Locale.FRENCH));
                    }

                    if (eds2.getEdsAltSubFct() != null) {
                        Wording AltSubFct = new Wording();
                        AltSubFct.setWId(eds2.getEdsAltSubFct().getWId());
                        AltSubFct.setWValue(eds2.getEdsAltSubFct().getValueByLocale(Locale.FRENCH));
                        version.setSubAltFct(AltSubFct);
                    }

                    if (eds2.getEdsBatSubFct() != null) {
                        Wording BatSubFct = new Wording();
                        BatSubFct.setWId(eds2.getEdsBatSubFct().getWId());
                        BatSubFct.setWValue(eds2.getEdsBatSubFct().getValueByLocale(Locale.FRENCH));
                        version.setSubBatFct(BatSubFct);
                    }

                    if (eds2.getEdsAltSubSys() != null) {
                        Wording AltSubSys = new Wording();
                        AltSubSys.setWId(eds2.getEdsAltSubSys().getWId());
                        AltSubSys.setWValue(eds2.getEdsAltSubSys().getValueByLocale(Locale.FRENCH));
                        version.setSubAltSys(AltSubSys);
                    }

                    if (eds2.getEdsBatSubSys() != null) {
                        Wording BatSubSys = new Wording();
                        BatSubSys.setWId(eds2.getEdsBatSubSys().getWId());
                        BatSubSys.setWValue(eds2.getEdsBatSubSys().getValueByLocale(Locale.FRENCH));
                        version.setSubBatSys(BatSubSys);
                    }

                    version.setNumberOfOccurrences(eds2.getEdsPLaunchCount());
                    version.setPrimarySupply(getSupplyValues(supply.getEdsPrimarySupply()));
                    version.setRobustSupply(getSupplyValues(supply.getEdsRobustSupply()));
                    version.setConsolidateSupply(getSupplyValues(supply.getEdsConsolidateSupply()));
                    version.setPsaMesureSupply(getSupplyValues(supply.getEdsPsaMesureSupply()));
                    sup.getVersions().add(version);
                }
            }
        }

        return supplies;
    }

    /**
     * Get supply values for EDS PRIMARY SUPPLY
     * 
     * @param primary EdsPrimarySupply object
     * @return Stage object
     */
    private Stage getSupplyValues(EdsPrimarySupply primary) {
        Stage primaryStage = new Stage();

        if (primary != null) {
            // InomStab
            if (primary.getPsedsINomStab() != null) {
                primaryStage.getiNomStabCurrents().add(
                        new Current("primary.data.i-nom-stab", primary.getPsedsINomStab().doubleValue(), true, primary.getPsedsINomStabComment()));
            }

            // FactoryParc -
            // Iveille
            if (primary.getPsedsIVeille() != null) {
                primaryStage.getiStdbyCurrents()
                        .add(new Current("primary.data.i-standby", primary.getPsedsIVeille().doubleValue(), true, primary.getPsedsIVeilleComment()));
            }

            // IreveilInactif
            if (primary.getPsedsIReveilleInactif() != null) {
                primaryStage.getiWakeUpCurrents().add(new Current("primary.data.i-wakeup-inactive", primary.getPsedsIReveilleInactif().doubleValue(),
                        true, primary.getPsedsReveilleComment()));
            }

            // Activation 100%
            if (primary.getPsedsIReveilleInactif() != null) {
                primaryStage.getFullActivationCurrents().add(new Current("primary.data.i-wakeup-inactive",
                        primary.getPsedsIReveilleInactif().doubleValue(), false, primary.getPsedsReveilleComment()));
            }
            if (primary.getPsedsINomStab() != null) {
                primaryStage.getFullActivationCurrents().add(
                        new Current("primary.data.i-nom-stab", primary.getPsedsINomStab().doubleValue(), true, primary.getPsedsINomStabComment()));
            }

            // Activation Specific
            if (primary.getPsedsINomStab() != null) {
                primaryStage.getSpecificActivationCurrents().add(
                        new Current("primary.data.i-nom-stab", primary.getPsedsINomStab().doubleValue(), true, primary.getPsedsINomStabComment()));
            }
            if (primary.getPsedsIReveilleInactif() != null) {
                primaryStage.getSpecificActivationCurrents().add(new Current("primary.data.i-wakeup-inactive",
                        primary.getPsedsIReveilleInactif().doubleValue(), false, primary.getPsedsReveilleComment()));
            }
        }

        return primaryStage;
    }

    /**
     * Get supply values for EDS ROBUST SUPPLY
     * 
     * @param robust EdsRobustSupply object
     * @return Stage object
     */
    private Stage getSupplyValues(EdsRobustSupply robust) {
        Stage robustStage = new Stage();

        if (robust != null) {
            // InomStab
            if (robust.getRsedsINomStab() != null) {
                robustStage.getiNomStabCurrents()
                        .add(new Current("robust.data.i-nom-stab", robust.getRsedsINomStab().doubleValue(), true, robust.getRsedsINomStabComment()));
            }

            // Iveille
            if (robust.getRsedsIVeille() != null) {
                robustStage.getiStdbyCurrents()
                        .add(new Current("robust.data.i-standby", robust.getRsedsIVeille().doubleValue(), true, robust.getRsedsIVeilleComment()));
            }

            // IreveilInactif
            if (robust.getRsedsIReveilleInactif() != null) {
                robustStage.getiWakeUpCurrents().add(new Current("robust.data.i-wakeup-inactive", robust.getRsedsIReveilleInactif().doubleValue(),
                        true, robust.getRsedsReveilleComment()));
            }

            // FactoryParc
            if (robust.getRsedsIConsoParc() != null) {
                robustStage.getFactoryParcCurrents().add(new Current("robust.data.rsedsIConsoParc", robust.getRsedsIConsoParc().doubleValue(), true,
                        robust.getRsedsIConsoParcComment()));
            }

            // Activation 100%
            if (robust.getRsedsIReveilleInactif() != null) {
                robustStage.getFullActivationCurrents().add(new Current("robust.data.i-wakeup-inactive",
                        robust.getRsedsIReveilleInactif().doubleValue(), false, robust.getRsedsReveilleComment()));
            }
            if (robust.getRsedsINomStab() != null) {
                robustStage.getFullActivationCurrents()
                        .add(new Current("robust.data.i-nom-stab", robust.getRsedsINomStab().doubleValue(), false, robust.getRsedsINomStabComment()));
            }

            // Activation Specific
            if (robust.getRsedsINomStab() != null) {
                robustStage.getSpecificActivationCurrents()
                        .add(new Current("robust.data.i-nom-stab", robust.getRsedsINomStab().doubleValue(), false, robust.getRsedsINomStabComment()));
            }
            if (robust.getRsedsIReveilleInactif() != null) {
                robustStage.getSpecificActivationCurrents().add(new Current("robust.data.i-wakeup-inactive",
                        robust.getRsedsIReveilleInactif().doubleValue(), false, robust.getRsedsReveilleComment()));
            }
        }

        return robustStage;
    }

    /**
     * Get supply values for EDS CONSOLIDATE SUPPLY
     * 
     * @param cons EdsConsolidateSupply object
     * @return Stage object
     */
    // CHECKSTYLE:OFF every line is different (no need factorization)
    private Stage getSupplyValues(EdsConsolidateSupply cons) {
        Stage consStage = new Stage();

        // MEASURES
        if (cons != null) {
            // INOMSTAB
            if (cons.getEdsConsolidateSupplyMesure() != null
                    && cons.getEdsConsolidateSupplyMesure().getEdsCourantNominaleActivationMesures() != null) {
                for (EdsCourantNominaleActivation edsCourantNominaleActivation : cons.getEdsConsolidateSupplyMesure()
                        .getEdsCourantNominaleActivationMesures()) {
                    if (edsCourantNominaleActivation.getCnaedsIminStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsIminStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsIminStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsIminStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImoyStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImoyStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImoyStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImaxStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImaxStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImaxStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }

                    // FULL
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }

                    // SPEC
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                }
            }

            // FACTORYPARK
            if (cons.getEdsConsolidateSupplyMesure() != null && cons.getEdsConsolidateSupplyMesure().getEdsModeParcMesures() != null) {
                for (EdsModeParc edsModeParc : cons.getEdsConsolidateSupplyMesure().getEdsModeParcMesures()) {
                    if (edsModeParc.getMpedsTmoyModeParc() != null) {
                        consStage.getFactoryParcCurrents().add(new Current("consolidate.measure.data.ModeParcs.Tmoy",
                                edsModeParc.getMpedsTmoyModeParc().doubleValue(), false, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                    if (edsModeParc.getMpedsTmaxModeParc() != null) {
                        consStage.getFactoryParcCurrents().add(new Current("consolidate.measure.data.ModeParcs.Tmax",
                                edsModeParc.getMpedsTmaxModeParc().doubleValue(), false, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                }
            }

            // ISTDBY && WAKEUP
            if (cons.getEdsConsolidateSupplyMesure() != null
                    && cons.getEdsConsolidateSupplyMesure().getEdsReseauVeilleReveilleOrganeInactifMesures() != null) {
                for (EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif : cons.getEdsConsolidateSupplyMesure()
                        .getEdsReseauVeilleReveilleOrganeInactifMesures()) {
                    // ISTDBY
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TminIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TminIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TminIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TminIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }

                    // WAKEUP
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.measure.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }

                    // FULL
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }

                    // SPEC
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.measure.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                }
            }
        }

        // theoretical
        if (cons != null) {
            // INOMSTAB
            if (cons.getEdsConsolidateSupplyTheoritic() != null
                    && cons.getEdsConsolidateSupplyTheoritic().getEdsCourantNominaleActivations() != null) {
                for (EdsCourantNominaleActivation edsCourantNominaleActivation : cons.getEdsConsolidateSupplyTheoritic()
                        .getEdsCourantNominaleActivations()) {
                    if (edsCourantNominaleActivation.getCnaedsIminStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsIminStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsIminStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsIminStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImoyStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImoyStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab13Mt().doubleValue(), true,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImoyStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab10Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImaxStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab13Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImaxStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab15Mt() != null) {
                        consStage.getiNomStabCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImaxStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }

                    // FULL
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }

                    // SPEC
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                }
            }

            // FACTORYPARK
            if (cons.getEdsConsolidateSupplyTheoritic() != null && cons.getEdsConsolidateSupplyTheoritic().getEdsModeParcs() != null) {
                for (EdsModeParc edsModeParc : cons.getEdsConsolidateSupplyTheoritic().getEdsModeParcs()) {
                    if (edsModeParc.getMpedsTmoyModeParc() != null) {
                        consStage.getFactoryParcCurrents().add(new Current("consolidate.theoretical.data.ModeParcs.Tmoy",
                                edsModeParc.getMpedsTmoyModeParc().doubleValue(), true, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                    if (edsModeParc.getMpedsTmaxModeParc() != null) {
                        consStage.getFactoryParcCurrents().add(new Current("consolidate.theoretical.data.ModeParcs.Tmax",
                                edsModeParc.getMpedsTmaxModeParc().doubleValue(), false, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                }
            }

            // ISTDBY && WAKEUP
            if (cons.getEdsConsolidateSupplyTheoritic() != null
                    && cons.getEdsConsolidateSupplyTheoritic().getEdsReseauVeilleReveilleOrganeInactifs() != null) {
                for (EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif : cons.getEdsConsolidateSupplyTheoritic()
                        .getEdsReseauVeilleReveilleOrganeInactifs()) {
                    // ISTDBY
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant().doubleValue(), true,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIveilleMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille8hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIveille8hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille8hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIveille21hMoteurNonTouran",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                    }

                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant() != null) {
                        consStage.getiStdbyCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIveille30hMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                    }

                    // WAKEUP
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), true,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getiWakeUpCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }

                    // FULL
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getFullActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }

                    // SPEC
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TminIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TmoyIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                    if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                        consStage.getSpecificActivationCurrents()
                                .add(new Current("consolidate.theoretical.data.TmaxIreveilleInactifMoteurNonTourant",
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(), false,
                                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                    }
                }
            }
        }

        return consStage;
    }

    // CHECKSTYLE:ON

    /**
     * Get supply values for EDS PSA MESURE SUPPLY
     * 
     * @param psa EdsPsaMesureSupply object
     * @return Stage object
     */
    // CHECKSTYLE:OFF every line is different (no need factorization)
    private Stage getSupplyValues(EdsPsaMesureSupply psa) {
        Stage psaStage = new Stage();

        if (psa != null) {
            // INOMSTAB
            if (psa.getEdsPmCourantNominaleActivations() != null) {
                for (EdsCourantNominaleActivation edsCourantNominaleActivation : psa.getEdsPmCourantNominaleActivations()) {
                    if (edsCourantNominaleActivation.getCnaedsIminStab10Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsIminStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab13Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsIminStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsIminStab15Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsIminStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsIminStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab10Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImoyStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab13Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImoyStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab13Mt().doubleValue(), true,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStab15Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImoyStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImoyStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab10Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImaxStab10Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab10Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab10MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab13Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImaxStab13Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab13Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab13MtComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStab15Mt() != null) {
                        psaStage.getiNomStabCurrents()
                                .add(new Current("psameasures.data.cnaedsImaxStab15Mt",
                                        edsCourantNominaleActivation.getCnaedsImaxStab15Mt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStab15MtComment()));
                    }

                    // FULL
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        psaStage.getFullActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        psaStage.getFullActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), true,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        psaStage.getFullActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }

                    // SPEC
                    if (edsCourantNominaleActivation.getCnaedsIminStabMnt() != null) {
                        psaStage.getSpecificActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsIminStabMnt",
                                        edsCourantNominaleActivation.getCnaedsIminStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImoyStabMnt() != null) {
                        psaStage.getSpecificActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsImoyStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImoyStabMnt().doubleValue(), true,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                    if (edsCourantNominaleActivation.getCnaedsImaxStabMnt() != null) {
                        psaStage.getSpecificActivationCurrents()
                                .add(new Current("psameasures.data.cnaedsImaxStabMnt",
                                        edsCourantNominaleActivation.getCnaedsImaxStabMnt().doubleValue(), false,
                                        edsCourantNominaleActivation.getCnaedsStabMntComment()));
                    }
                }
            }

            // FACTORYPARK
            if (psa.getEdsPmModeParcs() != null) {
                for (EdsModeParc edsModeParc : psa.getEdsPmModeParcs()) {
                    if (edsModeParc.getMpedsTmoyModeParc() != null) {
                        psaStage.getFactoryParcCurrents().add(new Current("psameasures.data.ModeParcs.Tmoy",
                                edsModeParc.getMpedsTmoyModeParc().doubleValue(), true, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                    if (edsModeParc.getMpedsTmaxModeParc() != null) {
                        psaStage.getFactoryParcCurrents().add(new Current("psameasures.data.ModeParcs.Tmax",
                                edsModeParc.getMpedsTmaxModeParc().doubleValue(), false, edsModeParc.getMpedsTmoyModeParcComment()));
                    }
                }
            }

            // ISTDBY && WAKEUP
            if (psa.getEdsPmReseauVeilleReveilleOrganeInactifs() != null) {
                for (EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif : psa.getEdsPmReseauVeilleReveilleOrganeInactifs()) {
                    if (edsReseauVeilleReveilleOrganeInactif != null) {
                        // ISTDBY
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TminIveilleMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmoyIveilleMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant().doubleValue(), true,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmaxIveilleMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment()));
                        }

                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TminIveille8hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmoyIveille8hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmaxIveille8hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment()));
                        }

                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TminIveille21hMoteurNonTouran",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmoyIveille21hMoteurNonTouran",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmaxIveille21hMoteurNonTouran",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment()));
                        }

                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TminIveille30hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmoyIveille30hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant() != null) {
                            psaStage.getiStdbyCurrents()
                                    .add(new Current("psameasures.data.TmaxIveille30hMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant().doubleValue(), false,
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment()));
                        }

                        // WAKEUP
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getiWakeUpCurrents()
                                    .add(new Current("psameasures.data.TminIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getiWakeUpCurrents()
                                    .add(new Current("psameasures.data.TmoyIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(),
                                            true, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getiWakeUpCurrents()
                                    .add(new Current("psameasures.data.TmaxIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }

                        // FULL
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getFullActivationCurrents()
                                    .add(new Current("psameasures.data.TminIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getFullActivationCurrents()
                                    .add(new Current("psameasures.data.TmoyIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getFullActivationCurrents()
                                    .add(new Current("psameasures.data.TmaxIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }

                        // SPEC
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getSpecificActivationCurrents()
                                    .add(new Current("psameasures.data.TminIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getSpecificActivationCurrents()
                                    .add(new Current("psameasures.data.TmoyIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                        if (edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant() != null) {
                            psaStage.getSpecificActivationCurrents()
                                    .add(new Current("psameasures.data.TmaxIreveilleInactifMoteurNonTourant",
                                            edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant().doubleValue(),
                                            false, edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment()));
                        }
                    }
                }
            }
        }

        return psaStage;
    }
}
