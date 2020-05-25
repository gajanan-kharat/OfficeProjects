/*
 * Creation : 20 mai 2015
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.I_Tensions;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Abstract class for current consumption read views in consolidate stage
 * 
 * @author Idir MEZIANI
 */
public abstract class A_CurrentConsumptionTable extends VerticalLayout implements I_Tensions {

    public A_CurrentConsumptionTable(EdsApplicationController controller, String operatingModeName, boolean addTitle) {
        this.controller = controller;
        this.operatingModeName = operatingModeName;
        this.addTitle = addTitle;
        init();
    }

    // PRIVATE
    private EdsApplicationController controller;
    private String operatingModeName;
    private HorizontalLayout title;
    private boolean addTitle;

    private void init() {

        setSpacing(true);
        setWidth("100%");

        if (addTitle) {
            title = new HorizontalLayout();

            Label vLBLtitle = new Label(controller.getBundle().getString("operating-mode"));
            vLBLtitle.addStyleName("operating-mode");
            title.addComponent(vLBLtitle);
            if (operatingModeName != null) {
                Label vLBLOperatingModeName = new Label(operatingModeName);
                vLBLOperatingModeName.setStyleName("operating-mode-name");
                title.setSpacing(true);
                title.addComponent(vLBLOperatingModeName);
            }

            addComponent(title);
        }
    }

    public String getUmin(Float uMin, boolean bt) {
        if (uMin != null)
            return uMin.toString() + "V";

        if (bt)
            return U_MIN_BT;

        return U_MIN_TBT;
    }

    @Override
    public String getUmin(Float uMin) {
        return getUmin(uMin, false);
    }

    public String getUmoy12_5(Float uMoy12_5, boolean bt) {
        if (uMoy12_5 != null)
            return uMoy12_5.toString() + "V";

        if (bt)
            return U_MOY12_5_BT;

        return U_MOY12_5_TBT;
    }

    @Override
    public String getUmoy12_5(Float uMoy12_5) {
        return getUmoy12_5(uMoy12_5, false);
    }

    public String getUmoy13_5(Float uMoy13_5, boolean bt) {
        if (uMoy13_5 != null)
            return uMoy13_5.toString() + "V";

        if (bt)
            return U_MOY13_5_BT;

        return U_MOY13_5_TBT;
    }

    @Override
    public String getUmoy13_5(Float uMoy13_5) {
        return getUmoy13_5(uMoy13_5, false);
    }

    public String getUmax(Float uMax, boolean bt) {
        if (uMax != null)
            return uMax.toString() + "V";

        if (bt)
            return U_MAX_BT;

        return U_MAX_TBT;
    }

    @Override
    public String getUmax(Float uMax) {
        return getUmax(uMax, false);
    }
}
