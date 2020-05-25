package com.inetpsa.eds.application.popup;

import java.util.List;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.mail.EdsMailAdviceBuilder;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used to create a EDS assignment window.
 * <ul>
 * It performs the following operations:
 * <li>Create Affectation/Assignment pop-up window.</li>
 * <li>Add labels to this window.</li>
 * <li>Add combo-box for all the users and test area for comments.</li>
 * <li>Add buttons and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class EdsAffectationWindow extends A_EdsWindow {
    /**
     * Variable to store the EDS details which need to be assigned.
     */
    private EdsEds eds;
    /**
     * Variable to store the EDS application controller.
     */
    private EdsApplicationController controleur;
    /**
     * List of ESD Users to be displayed.
     */
    private List<EdsUser> edsUsers;
    /**
     * Variable to store the label of Sheet administrator.
     */
    private Label vLAdmin;
    /**
     * Variable to store the label of Device development leader.
     */
    private Label vLCD;
    /**
     * Variable to store the label of Supplier.
     */
    private Label vLFNR;
    /**
     * Variable to store the label of Sheet administrator name.
     */
    private Label vLAdminName;
    /**
     * Variable to store the label of Device development leader name.
     */
    private Label vLCDName;
    /**
     * Variable to store the label of Supplier name.
     */
    private Label vLFNRName;
    /**
     * Variable to store the label of 'Assign the EDS to'.
     */
    private Label vLAffectation;
    /**
     * Combo-box to store the the list of all the users of EDS.
     */
    private ComboBox vCBAffectation;
    /**
     * Variable to store the label of 'Complementary information'.
     */
    private Label vLComment;
    /**
     * Text area to add the comment.
     */
    private MyTextArea vTAComent;
    /**
     * Button for validation.
     */
    private Button vBOK;
    /**
     * Cancel button.
     */
    private Button vBCancel;

    /**
     * Default constructor.
     */
    public EdsAffectationWindow() {
        super("Affectation de l'EDS");
        init();
    }

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details which need to be assigned.
     * @param controleur EDS Application controller object.
     */
    public EdsAffectationWindow(EdsEds eds, EdsApplicationController controleur) {
        super(controleur.getBundle().getString("button-affectation-tt") + " " + eds.getEdsName() + " (" + eds.getEdsRef() + ")");
        this.eds = eds;
        this.controleur = controleur;
        init();
    }

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Affectation/Assignment pop-up window.</li>
     * <li>Add labels to this window.</li>
     * <li>Add combo-box for all the users and test area for comments.</li>
     * <li>Add buttons and button listeners for validation.</li>
     * </ul>
     */
    private void init() {

        this.edsUsers = EDSdao.getInstance().getAllUsers();

        VerticalLayout vLayout = (VerticalLayout) getContent();
        vLayout.setMargin(true);
        GridLayout mainLayout = new GridLayout(2, 8);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");

        HorizontalLayout hl = new HorizontalLayout();

        Label l = new Label(controleur.getBundle().getString("eds-pop-affectation-intervenants"));
        l.addStyleName("gras");
        l.addStyleName("souligne");
        hl.addComponent(l);

        mainLayout.addComponent(hl, 0, 0, 1, 0);
        vLAdmin = new Label("- " + controleur.getBundle().getString("mail-content-eds-admin-fiche") + " :");
        vLAdmin.addStyleName("gras");
        vLAdmin.addStyleName("indent");
        mainLayout.addComponent(vLAdmin, 0, 1);

        vLAdminName = new Label(eds.getEdsUserByEdsAdminId() == null ? "" : eds.getEdsUserByEdsAdminId().toFullIdentity());
        mainLayout.addComponent(vLAdminName, 1, 1);

        vLCD = new Label("- " + controleur.getBundle().getString("mail-content-eds-charge-dev") + " :");
        vLCD.addStyleName("gras");
        vLCD.addStyleName("indent");
        mainLayout.addComponent(vLCD, 0, 2);

        vLCDName = new Label(eds.getEdsUserByEdsOfficerId() == null ? "" : eds.getEdsUserByEdsOfficerId().toFullIdentity());
        mainLayout.addComponent(vLCDName, 1, 2);
        vLFNR = new Label("- " + controleur.getBundle().getString("mail-content-eds-fnr") + " : ");
        vLFNR.addStyleName("gras");
        vLFNR.addStyleName("indent");
        mainLayout.addComponent(vLFNR, 0, 3);
        vLFNRName = new Label(eds.getEdsSupplier() == null ? "" : eds.getEdsSupplier().getSName());
        mainLayout.addComponent(vLFNRName, 1, 3);

        vLAffectation = new Label(controleur.getBundle().getString("eds-pop-affectation-at") + " : ");
        mainLayout.addComponent(vLAffectation, 0, 4);
        vCBAffectation = new ComboBox();
        vCBAffectation.setRequired(true);
        vCBAffectation.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        for (EdsUser user : edsUsers) {
            vCBAffectation.addItem(user);
            vCBAffectation.setItemCaption(user, user.toFullIdentity());
        }
        if (eds.getEdsUserByEdsAffectationUserId() != null) {
            vCBAffectation.setValue(eds.getEdsUserByEdsAffectationUserId());
        }
        mainLayout.addComponent(vCBAffectation, 1, 4);

        vLComment = new Label(controleur.getBundle().getString("eds-pop-affectation-infos-plus"));
        vLComment.addStyleName("gras");
        vLComment.addStyleName("souligne");
        mainLayout.addComponent(vLComment, 0, 5, 1, 5);

        vTAComent = new MyTextArea();
        vTAComent.setColumns(45);
        vTAComent.setRows(4);
        vTAComent.setSizeFull();
        vTAComent.setImmediate(true);
        vTAComent.setNullRepresentation("");
        mainLayout.addComponent(vTAComent, 0, 6, 1, 6);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        vBOK = new Button(controleur.getBundle().getString("Admin-proj-validate-button"));
        vBOK.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (vCBAffectation.getValue() != null) {
                    controleur.affectUser(eds, (EdsUser) vCBAffectation.getValue());
                    controleur.sendAdviceMail((EdsUser) vCBAffectation.getValue(), vTAComent.getText(), eds,
                            EdsMailAdviceBuilder.EDS_AFF_NOTIFICATION, false);
                    if (eds.getEdsUserByEdsAdminId() != (EdsUser) vCBAffectation.getValue()) {
                        controleur.sendAdviceMail(eds.getEdsUserByEdsAdminId(), vTAComent.getText(), eds, EdsMailAdviceBuilder.EDS_AFF_NOTIFICATION,
                                false);
                    }
                    showNotification(controleur.getBundle().getString("eds-pop-affectation-ok"));
                    close();
                } else {
                    showError(controleur.getBundle().getString("eds-pop-affectation-error-message"));
                }
            }
        });
        buttons.addComponent(vBOK);

        vBCancel = new Button(controleur.getBundle().getString("button-cancel"));
        vBCancel.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        buttons.addComponent(vBCancel);

        mainLayout.addComponent(buttons, 1, 7);
        mainLayout.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);

        vLayout.addComponent(mainLayout);
        vLayout.setSizeUndefined();

        setResizable(false);
    }
}
