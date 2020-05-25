package com.inetpsa.eds.application.content.eds.supplyvoltage.preliminary;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPreliminarySupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;

/**
 * This class is used to build edit view for 'Supply Voltage' for Preliminary stage.
 * 
 * @author Geometric Ltd.
 */
public class TensionAlimentationPreliminaireFormEditView extends A_EdsEditForm {
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> OriginDonnee;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store preliminary stage form data.
     */
    private EdsPreliminarySupplyVoltageFormData formData;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Preliminary stage form data of Supply voltage to be displayed.
     * @param controller EDS application controller object.
     */
    public TensionAlimentationPreliminaireFormEditView(com.inetpsa.eds.dao.model.EdsEds eds, EdsPreliminarySupplyVoltageFormData data,
            EdsApplicationController controller) {
        this.eds = eds;
        this.formData = data;
        this.controller = controller;
        init();
    }

    /**
     * Numeric text field for 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private MyTextField vTXTUmaxNominale;
    /**
     * Numeric text field for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private MyTextField vTXTUmaxRehabilitation;
    /**
     * Numeric text field for 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private MyTextField vTXTUminInitialisation;
    /**
     * Numeric text field for 'Minimal voltage of nominal operation (Umin)'.
     */
    private MyTextField vTXTUmintNominale;
    /**
     * Combo-box for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private ComboBox vCBUmaxNominale;
    /**
     * Combo-box for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private ComboBox vCBUmaxRehabilitation;
    /**
     * Combo-box for 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private ComboBox vCBUminInitialisation;
    /**
     * Combo-box for 'Minimal voltage of nominal operation (Umin)'.
     */
    private ComboBox vCBUminNominale;
    /**
     * Table to display all the fields.
     */
    private TensionAlimentationPreliminaireTabEditView table;

    /**
     * Initialization method. This method is used to generate the Edit view for Supply voltage form.
     */
    private void init() {

        OriginDonnee = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.DATA_ORIGIN);

        vTXTUmaxNominale = new MyTextField();
        vTXTUmaxRehabilitation = new MyTextField();
        vTXTUminInitialisation = new MyTextField();
        vTXTUmintNominale = new MyTextField();
        vCBUmaxNominale = new ComboBox("", OriginDonnee);
        vCBUmaxRehabilitation = new ComboBox("", OriginDonnee);
        vCBUminInitialisation = new ComboBox("", OriginDonnee);
        vCBUminNominale = new ComboBox("", OriginDonnee);

        table = new TensionAlimentationPreliminaireTabEditView(controller);
        table.setPageLength(4);
        table.setWidth("800");

        table.addOrderToContainer(controller.getBundle().getString("alim-voltage-data-degr-max"), vTXTUmaxRehabilitation, vCBUmaxRehabilitation);
        table.addOrderToContainer(controller.getBundle().getString("alim-voltage-data-nom-max"), vTXTUmaxNominale, vCBUmaxNominale);
        table.addOrderToContainer(controller.getBundle().getString("alim-voltage-data-degr-nom"), vTXTUmintNominale, vCBUminNominale);
        table.addOrderToContainer(controller.getBundle().getString("alim-voltage-data-degr-min"), vTXTUminInitialisation, vCBUminInitialisation);

        addComponent(table);

        Resource rsrc = ResourceManager.getInstance().getThemeIconResource("icons/image_tension_alimentation.png");
        Embedded image = new Embedded(null, rsrc);
        image.setType(Embedded.TYPE_IMAGE);

        addComponent(image);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (!("".equals(vTXTUmaxNominale.getValue())) && vTXTUmaxNominale.getValue() != null) {
            try {
                float a = new Float(vTXTUmaxNominale.toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }

        if (!("".equals(vTXTUmaxRehabilitation.getValue())) && vTXTUmaxRehabilitation.getValue() != null) {
            try {
                float b = new Float(vTXTUmaxRehabilitation.toString());
                if (b < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTUminInitialisation.getValue())) && vTXTUminInitialisation.getValue() != null) {
            try {
                float c = new Float(vTXTUminInitialisation.toString());
                if (c < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTUmintNominale.getValue())) && vTXTUmintNominale.getValue() != null) {
            try {
                float d = new Float(vTXTUmintNominale.toString());
                if (d < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }

        }
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
            formData.setEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale((EdsWording) vCBUmaxNominale.getValue());
            formData.setEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation((EdsWording) vCBUmaxRehabilitation.getValue());
            formData.setEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation((EdsWording) vCBUminInitialisation.getValue());
            formData.setEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale((EdsWording) vCBUminNominale.getValue());
            formData.setPsvValeurTensionMaximaleFonctionnementNominale(vTXTUmaxNominale.getString());
            formData.setPsvValeurTensionMinimaleFonctionnementInitialisation((vTXTUminInitialisation.getString()));
            formData.setPsvValeurTensionMinimaleFonctionnementNominale(vTXTUmintNominale.getString());
            formData.setPsvValeurTensionMaximaleFonctionnementRehabilisation(vTXTUmaxRehabilitation.getString());
            eds.setEdsModifDate(new Date());
            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vCBUmaxNominale.setValue(formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale());
        vCBUmaxRehabilitation.setValue(formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation());
        vCBUminInitialisation.setValue(formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation());
        vCBUminNominale.setValue(formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale());

        vTXTUmaxNominale.setValue(formData.getPsvValeurTensionMaximaleFonctionnementNominale());
        vTXTUmaxRehabilitation.setValue(formData.getPsvValeurTensionMaximaleFonctionnementRehabilisation());
        vTXTUminInitialisation.setValue(formData.getPsvValeurTensionMinimaleFonctionnementInitialisation());
        vTXTUmintNominale.setValue(formData.getPsvValeurTensionMinimaleFonctionnementNominale());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) formData);
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
        formData = controller.getFormDataModel(eds, formData.getClass());
    }
}
