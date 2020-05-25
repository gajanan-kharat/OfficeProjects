package com.inetpsa.eds.tools.filter.stage;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.ComboBox;
import java.util.List;

/**
 * Filter editor plug depending on the stage of validation.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsStage extends A_FilterEditor {
    // PUBLIC
    /**
     * Paremeterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public FE_EdsStage(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterEditor#createFilter()
     */
    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        return new EdsStageFilter((Integer) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize Eds stage filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<Integer> stageList = EdsApplicationController.getAllStages();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("menu-project-tab-step"), stageList) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                Integer stage = (Integer) itemId;
                return EdsApplicationController.getStageText(stage);
            }
        };
        this.vCMBequalsValue.select(stageList.get(0));
        this.vCMBequalsValue.setNullSelectionAllowed(false);
        this.vCMBequalsValue.setTextInputAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
