package com.inetpsa.eds.application.content.eds.activationprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsCurrentStatement;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionActivationFormData;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.vaadin.dialogs.ConfirmDialog;

/**
 * This class represents 'Activation Profile' edit view.
 * 
 * @author Geometric Ltd.
 */
public class ProfilActivationFormEditView extends A_EdsEditForm implements TabSheet.CloseHandler {
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Activation form data.
     * @param consolidateData Consolidated current data.
     * @param controller EDS application controller object.
     */
    public ProfilActivationFormEditView(EdsEds eds, EdsMissionActivationFormData data, EdsConsolidateCurentFormData consolidateData,
            EdsApplicationController controller) {
        this.eds = eds;
        this.data = data;
        this.consolidateData = consolidateData;
        this.controller = controller;
        init();
    }

    /**
     * This method is used to delete the activation profile once the tab is closed.
     * 
     * @param tabsheet Tab-sheet from which tab needs to be removed.
     * @param tabContent Tab component to be removed.
     * @see com.vaadin.ui.TabSheet.CloseHandler#onTabClose(com.vaadin.ui.TabSheet, com.vaadin.ui.Component)
     */
    public void onTabClose(TabSheet tabsheet, Component tabContent) {
        final CurrentStatementEditView removedView = (CurrentStatementEditView) tabContent;
        ConfirmDialog.show(controller.getApplication().getMainWindow(), controller.getBundle().getString("pop-confirm-delete"), controller
                .getBundle().getString("profil-activation-delete-question"), controller.getBundle().getString("eds-delete-btn"), controller
                .getBundle().getString("button-cancel"), new ConfirmDialog.Listener() {
            public void onClose(ConfirmDialog cd) {
                if (cd.isConfirmed()) {
                    EdsCurrentStatement statement = removedView.getStatement();
                    statementViews.remove(removedView);
                    usedNames.remove(statement.getCsName());
                    vTSt.removeComponent(removedView);
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            data.setMafdCommentaire((String) vTACommentaire.getValue());

            Set<EdsCurrentStatement> newStatements = new HashSet<EdsCurrentStatement>();
            for (CurrentStatementEditView view : statementViews) {
                view.commitChanges();
                newStatements.add(view.getStatement());
            }
            data.getEdsCurrentStatements().clear();
            data.getEdsCurrentStatements().addAll(newStatements);
            eds.setEdsModifDate(new Date());
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        statementViews.clear();
        usedNames.clear();
        vTSt.removeAllComponents();
        vTSt.addTab(mainLayout, controller.getBundle().getString("Activ-profil-title"));
        vTACommentaire.setValue(data.getMafdCommentaire());
        vTFstatementNameValue.setValue(null);
        vCBstatementAlimValue.removeAllItems();
        for (EdsSupply supply : consolidateData.getEdsSupplies()) {
            vCBstatementAlimValue.addItem(supply);
            vCBstatementAlimValue.setItemCaption(supply, supply.getSedsSupplyName());
        }

        for (EdsCurrentStatement statement : data.getEdsCurrentStatements()) {
            CurrentStatementEditView view = new CurrentStatementEditView(statement, controller);
            view.discardChanges();
            TabSheet.Tab tab = vTSt.addTab(view, statement.getCsName());
            tab.setClosable(true);
            usedNames.add(statement.getCsName());
            statementViews.add(view);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.singleton(data);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        data = controller.getFormDataModel(eds, EdsMissionActivationFormData.class);
        consolidateData = controller.getFormDataModel(eds, EdsConsolidateCurentFormData.class);
    }

    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store Mission activation form data.
     */
    private EdsMissionActivationFormData data;
    /**
     * Variable to store Consolidated stage current form data.
     */
    private EdsConsolidateCurentFormData consolidateData;
    /**
     * Variable to store EDS application controller data.
     */
    private EdsApplicationController controller;
    /**
     * Tab-sheet for activation profile.
     */
    private TabSheet vTSt;
    /**
     * Layout for main panel.
     */
    private VerticalLayout mainLayout;
    /**
     * Layout for adding statement.
     */
    private VerticalLayout addStatementLayout;
    /**
     * Text field for 'Chart name' value.
     */
    private TextField vTFstatementNameValue;
    /**
     * Text field for 'Power supply reference' value.
     */
    private ComboBox vCBstatementAlimValue;
    /**
     * Text field for 'Chart type' value.
     */
    private ComboBox vCBstatementTypeValue;
    /**
     * Text field for 'Doc Info URL' value.
     */
    private TextField vTFstatementDocInfoValue;
    /**
     * List of all used chart names.
     */
    private Set<String> usedNames;
    /**
     * List of current statement Edit view.
     */
    private List<CurrentStatementEditView> statementViews;
    /**
     * Text area for 'Realization conditions of the activation profile' value.
     */
    private TextArea vTACommentaire;

    /**
     * Initialization method.
     */
    private void init() {
        this.usedNames = new HashSet<String>();
        this.statementViews = new ArrayList<CurrentStatementEditView>();

        vTSt = new TabSheet();
        vTSt.setCloseHandler(this);

        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");

        mainLayout.addComponent(getAddCurrentStatementLayout());

        Label vLABsoustitre = new Label(controller.getBundle().getString("profil-activation-condition-activ") + " :");
        vLABsoustitre.addStyleName("h2");
        mainLayout.addComponent(vLABsoustitre);

        vTACommentaire = new TextArea();
        vTACommentaire.setWidth("760px");
        vTACommentaire.setMaxLength(2048);
        vTACommentaire.setNullSettingAllowed(true);
        vTACommentaire.setNullRepresentation("");
        mainLayout.addComponent(vTACommentaire);

        vTSt.addTab(mainLayout, controller.getBundle().getString("Activ-profil-gen"));

        addComponent(vTSt);
    }

    /**
     * This method is used to create the tab for creating new activation profiles. This method adds labels and fields for user input.
     * 
     * @return Layout for activation profile chart.
     */
    private VerticalLayout getAddCurrentStatementLayout() {
        if (addStatementLayout == null) {
            addStatementLayout = new VerticalLayout();
            addStatementLayout.setSpacing(true);

            Label vLBLstatements = new Label(controller.getBundle().getString("Activ-profil-add-profil") + " :");
            addStatementLayout.addStyleName("h2");
            addStatementLayout.addComponent(vLBLstatements);

            // Info on the name
            HorizontalLayout nameLayout = new HorizontalLayout();
            nameLayout.setSpacing(true);
            Label vLBLname = new Label(controller.getBundle().getString("Activ-profil-Name-profil") + " :");
            vLBLname.setWidth("200px");
            nameLayout.addComponent(vLBLname);
            vTFstatementNameValue = new TextField();
            vTFstatementNameValue.setRequired(true);
            vTFstatementNameValue.setImmediate(true);
            vTFstatementNameValue.setNullRepresentation("");
            vTFstatementNameValue.setWidth("350px");
            nameLayout.addComponent(vTFstatementNameValue);

            addStatementLayout.addComponent(nameLayout);

            // Information on the reference supply
            HorizontalLayout alimLayout = new HorizontalLayout();
            alimLayout.setSpacing(true);
            Label vLBLalim = new Label(controller.getBundle().getString("Activ-profil-alim") + " :");
            vLBLalim.setWidth("200px");
            alimLayout.addComponent(vLBLalim);

            vCBstatementAlimValue = new ComboBox();
            vCBstatementAlimValue.setRequired(true);
            vCBstatementAlimValue.setImmediate(true);
            vCBstatementAlimValue.setWidth("350px");
            alimLayout.addComponent(vCBstatementAlimValue);

            addStatementLayout.addComponent(alimLayout);

            // Information on the type of statement
            HorizontalLayout typeLayout = new HorizontalLayout();
            typeLayout.setSpacing(true);
            Label vLBLtype = new Label(controller.getBundle().getString("Activ-profil-type") + " :");
            vLBLtype.setWidth("200px");
            typeLayout.addComponent(vLBLtype);

            vCBstatementTypeValue = new ComboBox();
            vCBstatementTypeValue.setRequired(true);
            vCBstatementTypeValue.setImmediate(true);
            vCBstatementTypeValue.setWidth("350px");
            vCBstatementTypeValue.setNullSelectionAllowed(false);

            vCBstatementTypeValue.addItem(EdsCurrentStatement.CHART_STATEMENT);
            vCBstatementTypeValue.setItemCaption(EdsCurrentStatement.CHART_STATEMENT, controller.getBundle().getString("Activ-profil-tab"));
            vCBstatementTypeValue.addItem(EdsCurrentStatement.DOC_INFO_STATEMENT);
            vCBstatementTypeValue.setItemCaption(EdsCurrentStatement.DOC_INFO_STATEMENT, controller.getBundle().getString("Activ-profil-doc-lync"));
            vCBstatementTypeValue.setValue(EdsCurrentStatement.DOC_INFO_STATEMENT);
            typeLayout.addComponent(vCBstatementTypeValue);

            addStatementLayout.addComponent(typeLayout);

            // Info on the link DOCINFO
            HorizontalLayout docinfoLayout = new HorizontalLayout();
            docinfoLayout.setSpacing(true);
            final Label vLBLdocinfo = new Label(controller.getBundle().getString("Activ-profil-url") + " :");
            vLBLdocinfo.setWidth("200px");
            docinfoLayout.addComponent(vLBLdocinfo);
            vTFstatementDocInfoValue = new TextField();
            vTFstatementDocInfoValue.setImmediate(true);
            vTFstatementDocInfoValue.setNullRepresentation("");
            vTFstatementDocInfoValue.setWidth("350px");
            vTFstatementDocInfoValue.setValue(null);
            docinfoLayout.addComponent(vTFstatementDocInfoValue);

            addStatementLayout.addComponent(docinfoLayout);

            // Listener to enable / disable the intelligence doc link info
            vCBstatementTypeValue.addListener(new ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                    switch ((Integer) vCBstatementTypeValue.getValue()) {
                    case EdsCurrentStatement.CHART_STATEMENT:
                        vTFstatementDocInfoValue.setValue(null);
                        vTFstatementDocInfoValue.setEnabled(false);
                        vLBLdocinfo.setEnabled(false);
                        break;
                    case EdsCurrentStatement.DOC_INFO_STATEMENT:
                        vTFstatementDocInfoValue.setEnabled(true);
                        vLBLdocinfo.setEnabled(true);
                        break;
                    }
                }
            });

            Button vBTaddStatement = new Button(controller.getBundle().getString("Activ-profil-add-relev"), new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    String newAlimName = (String) vTFstatementNameValue.getValue();
                    if (newAlimName == null) {
                        controller
                                .getApplication()
                                .getMainWindow()
                                .showNotification(controller.getBundle().getString("pop-error-title"),
                                        controller.getBundle().getString("Activ-profil-error-no-profil"), Notification.TYPE_ERROR_MESSAGE);
                        return;
                    }
                    if (usedNames.contains(newAlimName)) {
                        controller
                                .getApplication()
                                .getMainWindow()
                                .showNotification(controller.getBundle().getString("pop-error-title"),
                                        MessageFormat.format(controller.getBundle().getString("profil-eds-used"), newAlimName),
                                        Notification.TYPE_ERROR_MESSAGE);
                        return;
                    }
                    EdsSupply supply = (EdsSupply) vCBstatementAlimValue.getValue();
                    if (supply == null) {
                        controller
                                .getApplication()
                                .getMainWindow()
                                .showNotification(controller.getBundle().getString("pop-error-title"),
                                        controller.getBundle().getString("Activ-profill-error-no-alim"), Notification.TYPE_ERROR_MESSAGE);
                        return;
                    }

                    String urlValue = null;

                    if ((Integer) vCBstatementTypeValue.getValue() == EdsCurrentStatement.DOC_INFO_STATEMENT) {
                        try {
                            urlValue = getDocInfoValue();
                        } catch (MalformedURLException e) {
                            controller
                                    .getApplication()
                                    .getMainWindow()
                                    .showNotification(controller.getBundle().getString("pop-error-title"),
                                            controller.getBundle().getString("Activ-profill-error-url"), Notification.TYPE_ERROR_MESSAGE);
                            return;
                        }
                    }

                    EdsCurrentStatement statement = new EdsCurrentStatement(UUID.randomUUID().toString(), supply, data, newAlimName);
                    statement.setCsStatementType((Integer) vCBstatementTypeValue.getValue());
                    statement.setCsDocInfoUrl(urlValue);
                    CurrentStatementEditView view = new CurrentStatementEditView(statement, controller);
                    view.discardChanges();
                    TabSheet.Tab tab = vTSt.addTab(view, newAlimName);
                    tab.setClosable(true);
                    usedNames.add(newAlimName);
                    statementViews.add(view);
                }
            });

            addStatementLayout.addComponent(vBTaddStatement);
        }
        return addStatementLayout;
    }

    /**
     * This method is used to validate the input doc info URL.
     * 
     * @return URL if valid.
     * @throws MalformedURLException If URL is not valid.
     */
    private String getDocInfoValue() throws MalformedURLException {
        String val = (String) vTFstatementDocInfoValue.getValue();

        if (val != null && val.matches("\\d+\\S*")) // url starts with a number
        {
            val = "http://docinfogroupe.inetpsa.com/ead/doc/ref." + val + "/v.dp/fiche";
        }
        URL url = new URL(val);
        return url.toString();
    }
}
