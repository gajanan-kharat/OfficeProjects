package com.inetpsa.eds.application.content.eds.connectivity.bsx;

import com.inetpsa.bsxdesigner.BSXdesignerController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * BSX Link form read view.
 */
public class BSXLinkFormReadView extends A_EdsReadForm {

    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public BSXLinkFormReadView(EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.controller = controller;
        this.eds = eds;
        this.bsx = eds.getEdsBsxUnique();
        init();
    }

    @Override
    public String getFormName() {
        return "connectivity-bsxlink-title";
    }

    @Override
    public String getFormTitle() {
        return "connectivity-bsxlink-title";
    }

    @Override
    public String getID() {
        return BSXLinkFormBuilder.ID;
    }

    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    @Override
    public EdsEds getEds() {
        return eds;
    }

    @Override
    public void refreshViewData() {
        bsx = eds.getEdsBsxUnique();
        bsxPanel.setBsx(bsx);

        // Only show if at least one CHS is present
        removeAllComponents();
        if (eds.getEdsChs().size() > 0)
            addComponent(mainLayout);
    }

    @Override
    public void refreshItems() {
        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /** Variable to store EDS application controller object. */
    private EdsApplicationController controller;

    /** EDS details. */
    private EdsEds eds;

    /** BSX designer controller */
    protected BSXdesignerController reader;

    /** BSX selected in the EDS */
    private EdsBsx bsx;

    /** BSX panel */
    private BSXPanel bsxPanel;

    /** Main layout */
    private VerticalLayout mainLayout;

    /**
     * Initialization method.
     */
    private void init() {

        // Main panel
        mainLayout = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        hl.setMargin(false);
        hl.setSpacing(false);
        hl.setWidth("100%");

        bsxPanel = new BSXPanel(controller.getBundle().getString("bsx-saved"), controller, bsx, eds.getEdsChs(), false);
        hl.addComponent(bsxPanel);

        mainLayout.addComponent(hl);
        addComponent(mainLayout);
    }

}
