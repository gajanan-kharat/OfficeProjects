package com.inetpsa.eds.application.content.eds.activationprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsCurrentStatement;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

/**
 * This class represents 'Current statement' in read view in activation profile.
 * 
 * @author Geometric Ltd.
 */
public class CurrentStatementReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param supply EDS current statement details.
     * @param controller EDS application controller object.
     */
    public CurrentStatementReadView(EdsCurrentStatement supply, EdsApplicationController controller) {
        super(controller);
        this.statement = supply;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        removeAllComponents();
        switch (statement.getCsStatementType()) {
        case EdsCurrentStatement.CHART_STATEMENT:
            addComponent(getStatementView());
            break;
        case EdsCurrentStatement.DOC_INFO_STATEMENT:
            addComponent(getDocInfoView());
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store current statement details.
     */
    private EdsCurrentStatement statement;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        refreshViewData();
    }

    /**
     * This method is used to retrieve the doc-info view.
     * 
     * @return Component for doc-info view.
     */
    private VerticalLayout getDocInfoView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        URL url = null;
        try {
            url = new URL(statement.getCsDocInfoUrl());

        } catch (MalformedURLException ex) {
            getWindow().showNotification(controller.getBundle().getString("eds-url-error"));
        }
        layout.addComponent(new LienDocInfo(url, controller));
        return layout;
    }

    /**
     * This method is used to build the current statement view. It created the table with EDS time and current details. It also creates chart using
     * these time and current details.
     * 
     * @return Component for Current statement view.
     */
    private VerticalLayout getStatementView() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setCaption(statement.getEdsUserId() == null ? controller.getBundle().getString("Activ-profil-no-profil") : MessageFormat
                .format(controller.getBundle().getString("Activ-profil-edited-by"), statement.getCsName(), statement.getEdsSupplyName(), statement
                        .getEdsUserId().toFullIdentity()));
        contentLayout.setMargin(true);
        contentLayout.setSpacing(true);
        contentLayout.setWidth("100%");

        HorizontalLayout subLayout = new HorizontalLayout();
        subLayout.setMargin(true);
        subLayout.setSpacing(true);

        Table vTBcustomProfileData = new Table(controller.getBundle().getString("Activ-profil-courant-complexe"));

        vTBcustomProfileData.addContainerProperty(controller.getBundle().getString("eds-time-label"), Float.class, null);
        vTBcustomProfileData.addContainerProperty(controller.getBundle().getString("eds-courant-label"), Float.class, null);

        String xyValue = statement.getCsXyData();
        XYSeries serie = new XYSeries(controller.getBundle().getString("Activ-profil-courant-title"), false);
        if (xyValue != null) {
            int i = 1;
            for (String xyPoint : xyValue.split(";")) {
                String[] xyData = xyPoint.split(":");
                vTBcustomProfileData.addItem(new Object[] { Float.parseFloat(xyData[0]), Float.parseFloat(xyData[1]) }, new Integer(i));
                serie.add(Double.parseDouble(xyData[1]), Double.parseDouble(xyData[0]));
                i++;
            }
        }

        Label vLBLcomments = new Label(statement.getCsComment());
        vLBLcomments.setWidth("100%");
        vLBLcomments.addStyleName("comment");

        subLayout.addComponent(vTBcustomProfileData);
        subLayout.addComponent(new JFreeChartWrapper(JFreeChartBuilder.buildCustomCurrentProfile(serie, controller)));
        contentLayout.addComponent(subLayout);
        if (!"".equals(statement.getCsComment())) {
            contentLayout.addComponent(new Label(controller.getBundle().getString("eds-comment-label") + ":"));
            contentLayout.addComponent(vLBLcomments);
        }

        return contentLayout;
    }
}
