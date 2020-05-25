package com.inetpsa.eds.application.content.eds;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.activationprofile.ProfilActivationFormBuilder;
import com.inetpsa.eds.application.content.eds.attachments.AttachmentsFormBuilder;
import com.inetpsa.eds.application.content.eds.behavior.consolidate.ComportementConsolideFormBuilder;
import com.inetpsa.eds.application.content.eds.behavior.robust.ComportementRobusteFormBuilder;
import com.inetpsa.eds.application.content.eds.connectivity.association.ConnectivityFormBuilder;
import com.inetpsa.eds.application.content.eds.connectivity.bsx.BSXLinkFormBuilder;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.CavityDefFormBuilder;
import com.inetpsa.eds.application.content.eds.cse.CSEFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.ConsolidateFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.DriverDriftsFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.preliminary.PrimaryFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.psameasure.PsaMeasureFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.robust.RobustFormBuilder;
import com.inetpsa.eds.application.content.eds.genericdata.GenericDataFormBuilder;
import com.inetpsa.eds.application.content.eds.missionprofile.ProfilMissionFormBuilder;
import com.inetpsa.eds.application.content.eds.standbyreactivationfailure.DefaillanceVeilleReveilFormBuilder;
import com.inetpsa.eds.application.content.eds.supplyvoltage.consolidate.TensionAlimentationConsolideFormBuilder;
import com.inetpsa.eds.application.content.eds.supplyvoltage.preliminary.TensionAlimentationPreliminaireFormBuilder;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormBuilder;
import com.inetpsa.eds.application.content.eds.versionhistory.VersionHistoryFormBuilder;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;

/**
 * This Class acts as a tool to collect data on manufacturers form.
 * 
 * @author Geometric Ltd.
 * @see I_FormBuilder
 */
public class EdsFormFactory {
    // PUBLIC
    /**
     * Constant to hold value of NO_PATH
     */
    public static final CaptionPath NO_PATH = new CaptionPath();
    /**
     * Constant to hold value of PRELIMINARY_CAPTION_PATH
     */
    public static final CaptionPath PRELIMINARY_CAPTION_PATH = new CaptionPath("menu-app-step-prelim");
    /**
     * Constant to hold value of ROBUST_CAPTION_PATH
     */
    public static final CaptionPath ROBUST_CAPTION_PATH = new CaptionPath("menu-app-step-rob");
    /**
     * Constant to hold value of CONSOLIDATED_CAPTION_PATH
     */
    public static final CaptionPath CONSOLIDATED_CAPTION_PATH = new CaptionPath("Validation-step-cons");
    /**
     * Constant to hold value of CLOSED_CAPTION_PATH
     */
    public static final CaptionPath CLOSED_CAPTION_PATH = new CaptionPath("menu-app-step-clot");
    /**
     * Constant to hold value of CLOSED_CAPTION_PATH
     */
    public static final CaptionPath CONNECTIVITY_CAPTION_PATH = new CaptionPath("menu-app-conn");

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsFormFactory(EdsApplicationController controller) {
        init();
    }

    /**
     * Method listing existing IDs form.
     * 
     * @return List of identifiers form
     */
    public static Set<String> getFormUniqueIds() {
        return buildersMap.keySet();
    }

    /**
     * This method returns form builder for given Id
     * 
     * @param formId Id for which build the form
     * @return form builder for given Id
     */
    public static I_FormBuilder getBuilder(String formId) {
        return buildersMap.get(formId);
    }

    /**
     * This method returns form node Using id and Eds
     * 
     * @param formId Form id
     * @param controller Controller of EDS application
     * @param eds Eds details
     * @return form node
     */
    public static EN_FormNode buildNode(String formId, EdsApplicationController controller, EdsEds eds) {
        return buildersMap.get(formId).buildNode(controller, eds);
    }

    /**
     * This Method returns resource for given path
     * 
     * @param path Path of resource
     * @return resource
     */
    public static Resource getPathIcon(CaptionPath path) {
        return ICONS_PATH_MAP.get(path);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold map of String and I_FormBuilder
     */
    private static final Map<String, I_FormBuilder> buildersMap;

    static {
        LinkedHashMap<String, I_FormBuilder> map = new LinkedHashMap<String, I_FormBuilder>();

        map.put(GenericDataFormBuilder.ID, new GenericDataFormBuilder());
        map.put(HighValidationFormBuilder.ID, new HighValidationFormBuilder());
        map.put(DriverDriftsFormBuilder.ID, new DriverDriftsFormBuilder());
        // Preliminary
        map.put(PrimaryFormBuilder.ID, new PrimaryFormBuilder());
        map.put(TensionAlimentationPreliminaireFormBuilder.ID, new TensionAlimentationPreliminaireFormBuilder());
        // Robust
        map.put(RobustFormBuilder.ID, new RobustFormBuilder());
        map.put(ComportementRobusteFormBuilder.ID, new ComportementRobusteFormBuilder());
        map.put(ProfilMissionFormBuilder.ID, new ProfilMissionFormBuilder());
        // Consolidate
        map.put(ConsolidateFormBuilder.ID, new ConsolidateFormBuilder());
        map.put(TensionAlimentationConsolideFormBuilder.ID, new TensionAlimentationConsolideFormBuilder());
        map.put(ComportementConsolideFormBuilder.ID, new ComportementConsolideFormBuilder());
        map.put(ProfilActivationFormBuilder.ID, new ProfilActivationFormBuilder());
        map.put(CSEFormBuilder.ID, new CSEFormBuilder());
        // Closed
        map.put(PsaMeasureFormBuilder.ID, new PsaMeasureFormBuilder());
        map.put(DefaillanceVeilleReveilFormBuilder.ID, new DefaillanceVeilleReveilFormBuilder());
        // Connectivity
        map.put(ConnectivityFormBuilder.ID, new ConnectivityFormBuilder());
        map.put(BSXLinkFormBuilder.ID, new BSXLinkFormBuilder());
        map.put(CavityDefFormBuilder.ID, new CavityDefFormBuilder());
        // other
        map.put(VersionHistoryFormBuilder.ID, new VersionHistoryFormBuilder()); //
        map.put(AttachmentsFormBuilder.ID, new AttachmentsFormBuilder()); //

        buildersMap = Collections.unmodifiableMap(map);
    }

    /**
     * Constant to hold map of CaptionPath and Resource
     */
    private static final Map<CaptionPath, Resource> ICONS_PATH_MAP;

    static {
        HashMap<CaptionPath, Resource> iconsMap = new HashMap<CaptionPath, Resource>();

        iconsMap.put(PRELIMINARY_CAPTION_PATH, ResourceManager.getInstance().getThemeIconResource("icons/preliminary.ico"));
        iconsMap.put(ROBUST_CAPTION_PATH, ResourceManager.getInstance().getThemeIconResource("icons/robust.ico"));
        iconsMap.put(CONSOLIDATED_CAPTION_PATH, ResourceManager.getInstance().getThemeIconResource("icons/consolidated.ico"));
        iconsMap.put(CLOSED_CAPTION_PATH, ResourceManager.getInstance().getThemeIconResource("icons/closed.ico"));
        iconsMap.put(CONNECTIVITY_CAPTION_PATH, ResourceManager.getInstance().getThemeIconResource("icons/connectivity.ico"));
        ICONS_PATH_MAP = Collections.unmodifiableMap(iconsMap);
    }

    /**
     * Initialize Form factory
     */
    private void init() {
    }
}
