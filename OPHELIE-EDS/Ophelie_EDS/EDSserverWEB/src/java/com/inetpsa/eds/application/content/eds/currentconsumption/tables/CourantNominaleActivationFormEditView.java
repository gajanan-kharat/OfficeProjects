package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.application.content.eds.composants.TwinTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view for Activation nominal current
 * 
 * @author Geometric Ltd.
 */
public abstract class CourantNominaleActivationFormEditView extends A_EdsEditForm implements Button.ClickListener, CellStyleGenerator {

    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;

    /**
     * Parameterized Constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecaa Object of EdsCourantNominaleActivation
     * @param edsSupply Object of EdsSupply
     * @param cnaedsRemove Boolean to set removable
     * @param cnaedsIEfficace Boolean to add/delete effective current
     * @param controller Controller of EDS application
     */
    public CourantNominaleActivationFormEditView(EdsQcf qcf, EdsCourantNominaleActivation ecna, boolean cnaedsRemove, boolean cnaedsIEfficace,
            EdsApplicationController controller, ConsolidateSupplyEdsTension tension, Boolean bt) {
        this.qcf = qcf;
        this.ecna = ecna;
        this.cnaedsIEfficace = cnaedsIEfficace;
        this.cnaedsRemove = cnaedsRemove;
        this.controller = controller;
        this.tension = tension;
        this.bt = bt;
        initComponent();
        init();

    }

    /**
     * Parameterized Constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecaa Object of EdsCourantNominaleActivation
     * @param edsSupply Object of EdsSupply
     * @param cnaedsRemove Boolean to set removable
     * @param cnaedsIEfficace Boolean to add/delete effective current
     * @param controller Controller of EDS application
     */
    public CourantNominaleActivationFormEditView(EdsQcf qcf, EdsCourantNominaleActivation ecaa, boolean cnaedsRemove, boolean cnaedsIEfficace,
            EdsApplicationController controller, Boolean bt) {
        this(qcf, ecaa, cnaedsRemove, cnaedsIEfficace, controller, new ConsolidateSupplyEdsTension(), bt);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
        // throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        resetQcf();
        ecna.setCnaedsName(vTXTTitle.getString());

        ecna.setCnaedsIminStabMnt(vTXTTminStabMnt.getCourant());
        ecna.setCnaedsTminStabMnt(vTXTTminStabMnt.getTime());
        ecna.setCnaedsImoyStabMnt(vTXTTmoyStabMnt.getCourant());
        ecna.setCnaedsTmoyStabMnt(vTXTTmoyStabMnt.getTime());
        ecna.setCnaedsImaxStabMnt(vTXTTmaxStab12v.getCourant());
        ecna.setCnaedsTmaxStabMnt(vTXTTmaxStab12v.getTime());
        ecna.setCnaedsStabMntComment(vTAStab_12V.getText());

        ecna.setCnaedsIminPireCasMnt(vTXTTminPirecasStab12v.getCourant());
        ecna.setCnaedsTminPireCasMnt(vTXTTminPirecasStab12v.getTime());
        ecna.setCnaedsImoyPireCasMnt(vTXTTmoyPirecasStab12v.getCourant());
        ecna.setCnaedsTmoyPireCasMnt(vTXTTmoyPirecasStab12v.getTime());
        ecna.setCnaedsImaxPireCasMnt(vTXTTmaxPirecasStab12v.getCourant());
        ecna.setCnaedsTmaxPireCasMnt(vTXTTmaxPirecasStab12v.getTime());
        ecna.setCnaedsPireCasMntComment(vTAPirecasStab_12V.getText());

        ecna.setCnaedsIminStab10Mt(vTXTTminStab10v.getCourant());
        ecna.setCnaedsTminStab10Mt(vTXTTminStab10v.getTime());
        ecna.setCnaedsImoyStab10Mt(vTXTTmoyStab10v.getCourant());
        ecna.setCnaedsTmoyStab10Mt(vTXTTmoyStab10v.getTime());
        ecna.setCnaedsImaxStab10Mt(vTXTTmaxStab10v.getCourant());
        ecna.setCnaedsTmaxStab10Mt(vTXTTmaxStab10v.getTime());
        ecna.setCnaedsStab10MtComment(vTAStab_10V.getText());

        ecna.setCnaedsIminStab13Mt(vTXTTminStab13v.getCourant());
        ecna.setCnaedsTminStab13Mt(vTXTTminStab13v.getTime());
        ecna.setCnaedsImoyStab13Mt(vTXTTmoyStab13v.getCourant());
        ecna.setCnaedsTmoyStab13Mt(vTXTTmoyStab13v.getTime());
        ecna.setCnaedsImaxStab13Mt(vTXTTmaxStab13v.getCourant());
        ecna.setCnaedsTmaxStab13Mt(vTXTTmaxStab13v.getTime());
        ecna.setCnaedsStab13MtComment(vTAStab_13V.getText());

        ecna.setCnaedsIminStab15Mt(vTXTTminStab15v.getCourant());
        ecna.setCnaedsTminStab15Mt(vTXTTminStab15v.getTime());
        ecna.setCnaedsImoyStab15Mt(vTXTTmoyStab15v.getCourant());
        ecna.setCnaedsTmoyStab15Mt(vTXTTmoyStab15v.getTime());
        ecna.setCnaedsImaxStab15Mt(vTXTTmaxStab15v.getCourant());
        ecna.setCnaedsTmaxStab15Mt(vTXTTmaxStab15v.getTime());
        ecna.setCnaedsStab15MtComment(vTAStab_15V.getText());

        ecna.setCnaedsIminPireCas10Mt(vTXTTminPirecasStab10v.getCourant());
        ecna.setCnaedsTminPireCas10Mt(vTXTTminPirecasStab10v.getTime());
        ecna.setCnaedsImoyPireCas10Mt(vTXTTmoyPirecasStab10v.getCourant());
        ecna.setCnaedsTmoyPireCas10Mt(vTXTTmoyPirecasStab10v.getTime());
        ecna.setCnaedsImaxPireCas10Mt(vTXTTmaxPirecasStab10v.getCourant());
        ecna.setCnaedsTmaxPireCas10Mt(vTXTTmaxPirecasStab10v.getTime());
        ecna.setCnaedsPireCas10MtComment(vTAPirecasStab_10V.getText());

        ecna.setCnaedsIminPireCas13Mt(vTXTTminPirecasStab13v.getCourant());
        ecna.setCnaedsTminPireCas13Mt(vTXTTminPirecasStab13v.getTime());
        ecna.setCnaedsImoyPireCas13Mt(vTXTTmoyPirecasStab13v.getCourant());
        ecna.setCnaedsTmoyPireCas13Mt(vTXTTmoyPirecasStab13v.getTime());
        ecna.setCnaedsImaxPireCas13Mt(vTXTTmaxPirecasStab13v.getCourant());
        ecna.setCnaedsTmaxPireCas13Mt(vTXTTmaxPirecasStab13v.getTime());
        ecna.setCnaedsPireCas13MtComment(vTAPirecasStab_13V.getText());

        ecna.setCnaedsIminPireCas15Mt(vTXTTminPirecasStab15v.getCourant());
        ecna.setCnaedsTminPireCas15Mt(vTXTTminPirecasStab15v.getTime());
        ecna.setCnaedsImoyPireCas15Mt(vTXTTmoyPirecasStab15v.getCourant());
        ecna.setCnaedsTmoyPireCas15Mt(vTXTTmoyPirecasStab15v.getTime());
        ecna.setCnaedsImaxPireCas15Mt(vTXTTmaxPirecasStab15v.getCourant());
        ecna.setCnaedsTmaxPireCas15Mt(vTXTTmaxPirecasStab15v.getTime());
        ecna.setCnaedsPireCas15MtComment(vTAPirecasStab_15V.getText());

        ecna.setCnaedsImoyDem(vTXTDem.getCourant());
        ecna.setCnaedsTmoyDem(vTXTDem.getTime());
        ecna.setCnaedsDemComment(vTADem.getText());

        ecna.getEdsCourantEfficace().setCeedsTminIeff12(vTXTTmineff12v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTminTeff12(vTXTTmineff12v.getTime());

        ecna.getEdsCourantEfficace().setCeedsTmoyIeff12(vTXTTmoyeff12v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmoyTeff12(vTXTTmoyeff12v.getTime());

        ecna.getEdsCourantEfficace().setCeedsTmaxIeff12(vTXTTmaxeff12v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmaxTeff12(vTXTTmaxeff12v.getTime());
        ecna.getEdsCourantEfficace().setCeedsCom12v(vTAeff_12V.getText());

        ecna.getEdsCourantEfficace().setCeedsTminIeff10(vTXTTmineff10v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmoyIeff10(vTXTTmoyeff10v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmaxIeff10(vTXTTmaxeff10v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsCom10v(vTAeff_10V.getText());

        ecna.getEdsCourantEfficace().setCeedsTminIeff13(vTXTTmineff13v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmoyIeff13(vTXTTmoyeff13v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmaxIeff13(vTXTTmaxeff13v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsCom13v(vTAeff_13V.getText());

        ecna.getEdsCourantEfficace().setCeedsTminIeff15(vTXTTmineff15v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmoyIeff15(vTXTTmoyeff15v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsTmaxIeff15(vTXTTmaxeff15v.getCourant());
        ecna.getEdsCourantEfficace().setCeedsCom15v(vTAeff_15V.getText());

        ecna.setCnaedsIEfficace(cnaedsIEfficace);
        ecna.setCnaedsRemove(cnaedsRemove);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTXTTitle.setValue(ecna.getCnaedsName());

        vTXTTminStabMnt.setCourant(ecna.getCnaedsIminStabMnt());
        vTXTTminStabMnt.setTime(ecna.getCnaedsTminStabMnt());
        vTXTTmoyStabMnt.setCourant(ecna.getCnaedsImoyStabMnt());
        vTXTTmoyStabMnt.setTime(ecna.getCnaedsTmoyStabMnt());
        vTXTTmaxStab12v.setCourant(ecna.getCnaedsImaxStabMnt());
        vTXTTmaxStab12v.setTime(ecna.getCnaedsTmaxStabMnt());
        vTAStab_12V.setValue(ecna.getCnaedsStabMntComment());

        vTXTTminPirecasStab12v.setCourant(ecna.getCnaedsIminPireCasMnt());
        vTXTTminPirecasStab12v.setTime(ecna.getCnaedsTminPireCasMnt());
        vTXTTmoyPirecasStab12v.setCourant(ecna.getCnaedsImoyPireCasMnt());
        vTXTTmoyPirecasStab12v.setTime(ecna.getCnaedsTmoyPireCasMnt());
        vTXTTmaxPirecasStab12v.setCourant(ecna.getCnaedsImaxPireCasMnt());
        vTXTTmaxPirecasStab12v.setTime(ecna.getCnaedsTmaxPireCasMnt());
        vTAPirecasStab_12V.setText(ecna.getCnaedsPireCasMntComment());

        vTXTTminStab10v.setCourant(ecna.getCnaedsIminStab10Mt());
        vTXTTminStab10v.setTime(ecna.getCnaedsTminStab10Mt());
        vTXTTmoyStab10v.setCourant(ecna.getCnaedsImoyStab10Mt());
        vTXTTmoyStab10v.setTime(ecna.getCnaedsTmoyStab10Mt());
        vTXTTmaxStab10v.setCourant(ecna.getCnaedsImaxStab10Mt());
        vTXTTmaxStab10v.setTime(ecna.getCnaedsTmaxStab10Mt());
        vTAStab_10V.setText(ecna.getCnaedsStab10MtComment());

        vTXTTminStab13v.setCourant(ecna.getCnaedsIminStab13Mt());
        vTXTTminStab13v.setTime(ecna.getCnaedsTminStab13Mt());
        vTXTTmoyStab13v.setCourant(ecna.getCnaedsImoyStab13Mt());
        vTXTTmoyStab13v.setTime(ecna.getCnaedsTmoyStab13Mt());
        vTXTTmaxStab13v.setCourant(ecna.getCnaedsImaxStab13Mt());
        vTXTTmaxStab13v.setTime(ecna.getCnaedsTmaxStab13Mt());
        vTAStab_13V.setText(ecna.getCnaedsStab13MtComment());

        vTXTTminStab15v.setCourant(ecna.getCnaedsIminStab15Mt());
        vTXTTminStab15v.setTime(ecna.getCnaedsTminStab15Mt());
        vTXTTmoyStab15v.setCourant(ecna.getCnaedsImoyStab15Mt());
        vTXTTmoyStab15v.setTime(ecna.getCnaedsTmoyStab15Mt());
        vTXTTmaxStab15v.setCourant(ecna.getCnaedsImaxStab15Mt());
        vTXTTmaxStab15v.setTime(ecna.getCnaedsTmaxStab15Mt());
        vTAStab_15V.setText(ecna.getCnaedsStab15MtComment());

        vTXTTminPirecasStab10v.setCourant(ecna.getCnaedsIminPireCas10Mt());
        vTXTTminPirecasStab10v.setTime(ecna.getCnaedsTminPireCas10Mt());
        vTXTTmoyPirecasStab10v.setCourant(ecna.getCnaedsImoyPireCas10Mt());
        vTXTTmoyPirecasStab10v.setTime(ecna.getCnaedsTmoyPireCas10Mt());
        vTXTTmaxPirecasStab10v.setCourant(ecna.getCnaedsImaxPireCas10Mt());
        vTXTTmaxPirecasStab10v.setTime(ecna.getCnaedsTmaxPireCas10Mt());
        vTAPirecasStab_10V.setText(ecna.getCnaedsPireCas10MtComment());

        vTXTTminPirecasStab13v.setCourant(ecna.getCnaedsIminPireCas13Mt());
        vTXTTminPirecasStab13v.setTime(ecna.getCnaedsTminPireCas13Mt());
        vTXTTmoyPirecasStab13v.setCourant(ecna.getCnaedsImoyPireCas13Mt());
        vTXTTmoyPirecasStab13v.setTime(ecna.getCnaedsTmoyPireCas13Mt());
        vTXTTmaxPirecasStab13v.setCourant(ecna.getCnaedsImaxPireCas13Mt());
        vTXTTmaxPirecasStab13v.setTime(ecna.getCnaedsTmaxPireCas13Mt());
        vTAPirecasStab_13V.setText(ecna.getCnaedsPireCas13MtComment());

        vTXTTminPirecasStab15v.setCourant(ecna.getCnaedsIminPireCas15Mt());
        vTXTTminPirecasStab15v.setTime(ecna.getCnaedsTminPireCas15Mt());
        vTXTTmoyPirecasStab15v.setCourant(ecna.getCnaedsImoyPireCas15Mt());
        vTXTTmoyPirecasStab15v.setTime(ecna.getCnaedsTmoyPireCas15Mt());
        vTXTTmaxPirecasStab15v.setCourant(ecna.getCnaedsImaxPireCas15Mt());
        vTXTTmaxPirecasStab15v.setTime(ecna.getCnaedsTmaxPireCas15Mt());
        vTAPirecasStab_15V.setText(ecna.getCnaedsPireCas15MtComment());

        vTXTDem.setCourant(ecna.getCnaedsImoyDem());
        vTXTDem.setTime(ecna.getCnaedsTmoyDem());
        vTADem.setText(ecna.getCnaedsDemComment());

        vTXTTmineff12v.setCourant(ecna.getEdsCourantEfficace().getCeedsTminIeff12());
        vTXTTmineff12v.setTime(ecna.getEdsCourantEfficace().getCeedsTminTeff12());

        vTXTTmoyeff12v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmoyIeff12());
        vTXTTmoyeff12v.setTime(ecna.getEdsCourantEfficace().getCeedsTmoyTeff12());

        vTXTTmaxeff12v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmaxIeff12());
        vTXTTmaxeff12v.setTime(ecna.getEdsCourantEfficace().getCeedsTmaxTeff12());
        vTAeff_12V.setText(ecna.getEdsCourantEfficace().getCeedsCom12v());

        vTXTTmineff10v.setCourant(ecna.getEdsCourantEfficace().getCeedsTminIeff10());
        vTXTTmoyeff10v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmoyIeff10());
        vTXTTmaxeff10v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmaxIeff10());
        vTAeff_10V.setText(ecna.getEdsCourantEfficace().getCeedsCom10v());

        vTXTTmineff13v.setCourant(ecna.getEdsCourantEfficace().getCeedsTminIeff13());
        vTXTTmoyeff13v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmoyIeff13());
        vTXTTmaxeff13v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmaxIeff13());
        vTAeff_13V.setText(ecna.getEdsCourantEfficace().getCeedsCom13v());

        vTXTTmineff15v.setCourant(ecna.getEdsCourantEfficace().getCeedsTminIeff15());
        vTXTTmoyeff15v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmoyIeff15());
        vTXTTmaxeff15v.setCourant(ecna.getEdsCourantEfficace().getCeedsTmaxIeff15());
        vTAeff_15V.setText(ecna.getEdsCourantEfficace().getCeedsCom15v());
        if (cnaedsIEfficace) {
            vBAddRemoveIeff.setCaption(controller.getBundle().getString("btn-delete-courant-efficace"));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) ecna);
    }

    /**
     * This method returns Collection of EdsCourantNominaleActivation
     * 
     * @return Collection of EdsCourantNominaleActivation
     */
    public Collection<EdsCourantNominaleActivation> getAllEdsCourantNominaleActivation() {
        return Collections.singleton(ecna);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsCourantNominalActivation
     */
    private EdsCourantNominaleActivation ecna;

    /**
     * Variable to hold list of EdsProject
     */
    private List<EdsProject> relatedProjects = new ArrayList<EdsProject>();
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of Boolean to set removable
     */
    private boolean cnaedsRemove;
    /**
     * Variable to hold value of Boolean to add/delete effective current button
     */
    private boolean cnaedsIEfficace;
    /**
     * Variable to hold value of MyTextField for title
     */
    private MyTextField vTXTTitle;
    /**
     * Variable to hold value of Remove Button
     */
    private Button vBRemove;
    /**
     * Variable to hold value of Button to add/delete effective current
     */
    private Button vBAddRemoveIeff;
    /**
     * Variable to hold value of MyTable
     */
    private MyTable vTEfficace;
    /**
     * Variable to hold value of MyTextArea for comment of nom stab 12 V
     */
    private MyTextArea vTAStab_12V;
    /**
     * Variable to hold value of TwinTextField for Tmin of nom stab 12 V
     */
    private TwinTextField vTXTTminStabMnt;
    /**
     * Variable to hold value of TwinTextField for Tmed of nom stab 12 V
     */
    private TwinTextField vTXTTmoyStabMnt;
    /**
     * Variable to hold value of TwinTextField for Tmax of nob stab 12 V
     */
    private TwinTextField vTXTTmaxStab12v;
    /**
     * Variable to hold value of MyTextArea for comment of Worstcase stab 12 V
     */
    private MyTextArea vTAPirecasStab_12V;
    /**
     * Variable to hold value of TwinTextField for Tmin of Worstcase stab 12 V
     */
    private TwinTextField vTXTTminPirecasStab12v;
    /**
     * Variable to hold value of TwinTextField for Tmed of Worstcase stab 12 V
     */
    private TwinTextField vTXTTmoyPirecasStab12v;
    /**
     * Variable to hold value of TwinTextField for Tmax of Worstcase stab 12 V
     */
    private TwinTextField vTXTTmaxPirecasStab12v;
    /**
     * Variable to hold value of MyTextArea for comment of nom stab 10 V
     */
    private MyTextArea vTAStab_10V;
    /**
     * Variable to hold value of TwinTextField for Tmin of nom stab 10 V
     */
    private TwinTextField vTXTTminStab10v;
    /**
     * Variable to hold value of TwinTextField for Tmed of nom stab 10 V
     */
    private TwinTextField vTXTTmoyStab10v;
    /**
     * Variable to hold value of TwinTextField for Tmax of nom stab 10 V
     */
    private TwinTextField vTXTTmaxStab10v;
    /**
     * Variable to hold value of MyTextArea for comment of nom stab 13 V
     */
    private MyTextArea vTAStab_13V;
    /**
     * Variable to hold value of TwinTextField for Tmin of nom stab 13 V
     */
    private TwinTextField vTXTTminStab13v;
    /**
     * Variable to hold value of TwinTextField for Tmed of nom stab 13 V
     */
    private TwinTextField vTXTTmoyStab13v;
    /**
     * Variable to hold value of TwinTextField for Tmax of nom stab 13 V
     */
    private TwinTextField vTXTTmaxStab13v;
    /**
     * Variable to hold value of MyTextArea for comment of nom stab 15 V
     */
    private MyTextArea vTAStab_15V;
    /**
     * Variable to hold value of TwinTextField for Tmin of nom stab 15 V
     */
    private TwinTextField vTXTTminStab15v;
    /**
     * Variable to hold value of TwinTextField for Tmed of nom stab 15 V
     */
    private TwinTextField vTXTTmoyStab15v;
    /**
     * Variable to hold value of TwinTextField for Tmax of nom stab 15 V
     */
    private TwinTextField vTXTTmaxStab15v;
    /**
     * Variable to hold value of MyTextArea for comment of Worstcase stab 10 V
     */
    private MyTextArea vTAPirecasStab_10V;
    /**
     * Variable to hold value of TwinTextField for Tmin of Worstcase stab 10 V
     */
    private TwinTextField vTXTTminPirecasStab10v;
    /**
     * Variable to hold value of TwinTextField for Tmed of Worstcase stab 10 V
     */
    private TwinTextField vTXTTmoyPirecasStab10v;
    /**
     * Variable to hold value of TwinTextField for Tmax of Worstcase stab 10 V
     */
    private TwinTextField vTXTTmaxPirecasStab10v;
    /**
     * Variable to hold value of MyTextArea for comment of Worstcase stab 13 V
     */
    private MyTextArea vTAPirecasStab_13V;
    /**
     * Variable to hold value of TwinTextField for Tmin of Worstcase stab 13 V
     */
    private TwinTextField vTXTTminPirecasStab13v;
    /**
     * Variable to hold value of TwinTextField for Tmed of Worstcase stab 13 V
     */
    private TwinTextField vTXTTmoyPirecasStab13v;
    /**
     * Variable to hold value of TwinTextField for Tmax of Worstcase stab 13 V
     */
    private TwinTextField vTXTTmaxPirecasStab13v;
    /**
     * Variable to hold value of MyTextArea for comment of Worstcase stab 15 V
     */
    private MyTextArea vTAPirecasStab_15V;
    /**
     * Variable to hold value of TwinTextField for Tmin of Worstcase stab 15 V
     */
    private TwinTextField vTXTTminPirecasStab15v;
    /**
     * Variable to hold value of TwinTextField for Tmed of Worstcase stab 15 V
     */
    private TwinTextField vTXTTmoyPirecasStab15v;
    /**
     * Variable to hold value of TwinTextField for Tmax of Worstcase stab 15 V
     */
    private TwinTextField vTXTTmaxPirecasStab15v;
    /**
     * Variable to hold value of MyTextArea for Comment of UStrart
     */
    private MyTextArea vTADem;
    /**
     * Variable to hold value of TwinTextField for UStart
     */
    private TwinTextField vTXTDem;
    /**
     * Variable to hold value of MyTextArea for comment of eff 12 V
     */
    private MyTextArea vTAeff_12V;
    /**
     * Variable to hold value of TwinTextField for Tmin of eff 12 V
     */
    private TwinTextField vTXTTmineff12v;
    /**
     * Variable to hold value of TwinTextField for Tmed of eff 12 V
     */
    private TwinTextField vTXTTmoyeff12v;
    /**
     * Variable to hold value of TwinTextField for Tmax of eff 12 V
     */
    private TwinTextField vTXTTmaxeff12v;
    /**
     * Variable to hold value of MyTextArea for comment of eff 10 V
     */
    private MyTextArea vTAeff_10V;
    /**
     * Variable to hold value of TwinTextField for Tmin of eff 10 V
     */
    private TwinTextField vTXTTmineff10v;
    /**
     * Variable to hold value of TwinTextField for Tmed of eff 10 V
     */
    private TwinTextField vTXTTmoyeff10v;
    /**
     * Variable to hold value of TwinTextField for Tmax of eff 10 V
     */
    private TwinTextField vTXTTmaxeff10v;
    /**
     * Variable to hold value of MyTextArea for comment of eff 13 V
     */
    private MyTextArea vTAeff_13V;
    /**
     * Variable to hold value of TwinTextField for Tmin of eff 13 V
     */
    private TwinTextField vTXTTmineff13v;
    /**
     * Variable to hold value of TwinTextField for Tmed of eff 13 V
     */
    private TwinTextField vTXTTmoyeff13v;
    /**
     * Variable to hold value of TwinTextField for Tmax of eff 13 V
     */
    private TwinTextField vTXTTmaxeff13v;
    /**
     * Variable to hold value of MyTextArea for comment of eff 15 V
     */
    private MyTextArea vTAeff_15V;
    /**
     * Variable to hold value of TwinTextField for Tmin of eff 15 V
     */
    private TwinTextField vTXTTmineff15v;
    /**
     * Variable to hold value of TwinTextField for Tmed of eff 15 V
     */
    private TwinTextField vTXTTmoyeff15v;
    /**
     * Variable to hold value of TwinTextField for Tmax of eff 15 V
     */
    private TwinTextField vTXTTmaxeff15v;

    /**
     * Initialize Edit form view for Activation nominal current
     */
    private void init() {

        vTXTTitle = new MyTextField();
        vTXTTitle.setWidth("300");
        vTXTTitle.setNullRepresentation("");
        vTXTTitle.setInputPrompt(controller.getBundle().getString("table-title"));

        HorizontalLayout vHlLayout1 = new HorizontalLayout();
        vHlLayout1.setWidth("100%");
        vHlLayout1.addComponent(vTXTTitle);
        vBAddRemoveIeff = new Button(controller.getBundle().getString("btn-ajout-courant-efficace"));
        vBAddRemoveIeff.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                ShowRemoveIeff();
            }
        });
        vHlLayout1.addComponent(vBAddRemoveIeff);
        vHlLayout1.setComponentAlignment(vBAddRemoveIeff, Alignment.BOTTOM_RIGHT);

        GridLayout layout = new GridLayout(2, 4);
        layout.setWidth("100%");
        layout.setSpacing(true);
        layout.addComponent(vHlLayout1, 0, 0);
        layout.addComponent(getCourantNominaleActivation(), 0, 1);

        if (cnaedsRemove) {

            vBRemove = new Button();
            vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
            vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
            vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
            vBRemove.addListener(this);
            layout.addComponent(vBRemove, 1, 1);
            layout.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
            layout.setSpacing(true);
        }
        layout.setColumnExpandRatio(0, 1f);
        layout.setColumnExpandRatio(1, 0f);

        layout.addComponent(getEfficacTab(), 0, 2);

        Label info = new Label(controller.getBundle().getString("courant-nominal-message-1") + "<br>"
                + controller.getBundle().getString("courant-nominal-message-2"), Label.CONTENT_XHTML);

        layout.addComponent(info, 0, 3);
        addComponent(layout);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

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

    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    /**
     * This method show Add an effective current or Delete an effective current button
     */
    private void ShowRemoveIeff() {
        if (cnaedsIEfficace) {
            cnaedsIEfficace = false;
            vBAddRemoveIeff.setCaption(controller.getBundle().getString("btn-ajout-courant-efficace"));

        } else {
            cnaedsIEfficace = true;
            vBAddRemoveIeff.setCaption(controller.getBundle().getString("btn-delete-courant-efficace"));//
        }
        vTEfficace.setVisible(cnaedsIEfficace);
        ecna.setCnaedsIEfficace(cnaedsIEfficace);
    }

    /**
     * Initialize component
     */
    private void initComponent() {
        vTAeff_12V = new MyTextArea();

        vTXTTmineff12v = new TwinTextField(true, controller);
        vTXTTmoyeff12v = new TwinTextField(true, controller);
        vTXTTmaxeff12v = new TwinTextField(true, controller);

        vTAeff_10V = new MyTextArea();
        vTXTTmineff10v = new TwinTextField(false, controller);
        vTXTTmoyeff10v = new TwinTextField(false, controller);
        vTXTTmaxeff10v = new TwinTextField(false, controller);

        vTAeff_13V = new MyTextArea();
        vTXTTmineff13v = new TwinTextField(false, controller);
        vTXTTmoyeff13v = new TwinTextField(false, controller);
        vTXTTmaxeff13v = new TwinTextField(false, controller);

        vTAeff_15V = new MyTextArea();
        vTXTTmineff15v = new TwinTextField(false, controller);
        vTXTTmoyeff15v = new TwinTextField(false, controller);
        vTXTTmaxeff15v = new TwinTextField(false, controller);

        vTAStab_12V = new MyTextArea();
        vTXTTminStabMnt = new TwinTextField(true, controller);
        vTXTTmoyStabMnt = new TwinTextField(true, controller);
        vTXTTmaxStab12v = new TwinTextField(true, controller);

        vTAPirecasStab_12V = new MyTextArea();
        vTXTTminPirecasStab12v = new TwinTextField(true, controller);
        vTXTTmoyPirecasStab12v = new TwinTextField(true, controller);
        vTXTTmaxPirecasStab12v = new TwinTextField(true, controller);

        vTAStab_10V = new MyTextArea();
        vTXTTminStab10v = new TwinTextField(true, controller);
        vTXTTmoyStab10v = new TwinTextField(true, controller);
        vTXTTmaxStab10v = new TwinTextField(true, controller);

        vTAStab_13V = new MyTextArea();
        vTXTTminStab13v = new TwinTextField(true, controller);
        vTXTTmoyStab13v = new TwinTextField(true, controller);
        vTXTTmaxStab13v = new TwinTextField(true, controller);

        vTAStab_15V = new MyTextArea();
        vTXTTminStab15v = new TwinTextField(true, controller);
        vTXTTmoyStab15v = new TwinTextField(true, controller);
        vTXTTmaxStab15v = new TwinTextField(true, controller);

        vTAPirecasStab_10V = new MyTextArea();
        vTXTTminPirecasStab10v = new TwinTextField(true, controller);
        vTXTTmoyPirecasStab10v = new TwinTextField(true, controller);
        vTXTTmaxPirecasStab10v = new TwinTextField(true, controller);

        vTAPirecasStab_13V = new MyTextArea();
        vTXTTminPirecasStab13v = new TwinTextField(true, controller);
        vTXTTmoyPirecasStab13v = new TwinTextField(true, controller);
        vTXTTmaxPirecasStab13v = new TwinTextField(true, controller);

        vTAPirecasStab_15V = new MyTextArea();
        vTXTTminPirecasStab15v = new TwinTextField(true, controller);
        vTXTTmoyPirecasStab15v = new TwinTextField(true, controller);
        vTXTTmaxPirecasStab15v = new TwinTextField(true, controller);

        vTADem = new MyTextArea();
        vTXTDem = new TwinTextField(true, controller);

    }

    /**
     * This method return Table for Activation nominal current
     * 
     * @return Table for Activation nominal current
     */
    private Table getCourantNominaleActivation() {
        MyTable table = new MyTable();
        table.setWidth("100%");
        table.setCellStyleGenerator(this);
        int i = 4;
        table.addContainerProperty("0", Label.class, null, controller.getBundle().getString("table-header-etat-moteur-label"), null, null);
        table.addContainerProperty("1", Label.class, null, controller.getBundle().getString("table-header-phase-vehicule-label"), null, null);
        table.addContainerProperty("2", Label.class, null, controller.getBundle().getString("table-header-label-tension"), null, null);
        table.addContainerProperty("3", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmin"), null, null);
        table.addContainerProperty("4", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"), null, null);
        table.addContainerProperty("5", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmax"), null, null);
        table.addContainerProperty("6", TextArea.class, null, controller.getBundle().getString("eds-comnent"), null, null);

        Label l1 = new Label(controller.getBundle().getString("current-conso-tab-data-prem-nom") + "<br>"
                + controller.getBundle().getString("current-conso-tab-data-rob-TnomStab"), Label.CONTENT_XHTML);
        table.addItem(
                new Object[] { controller.getBundle().getString("table-header-moteur-non-tournant-label"), l1,
                        super.getUmoy12_5(tension.getCsEdsUmoy(), bt), vTXTTminStabMnt, vTXTTmoyStabMnt, vTXTTmaxStab12v, vTAStab_12V }, new Integer(
                        0));
        // ------------------------------------------
        Label l2 = new Label(controller.getBundle().getString("current-conso-tab-data-prem-pire") + "**<br>"
                + controller.getBundle().getString("current-conso-tab-data-tpirecas"), Label.CONTENT_XHTML);
        table.addItem(new Object[] { "", l2, super.getUmoy12_5(tension.getCsEdsUmoy(), bt), vTXTTminPirecasStab12v, vTXTTmoyPirecasStab12v,
                vTXTTmaxPirecasStab12v, vTAPirecasStab_12V }, new Integer(1));
        // ------------------------------------------
        if (qcf.getQcfC3() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmin(tension.getCsEdsUmin(), bt), vTXTTminStab10v, vTXTTmoyStab10v, vTXTTmaxStab10v,
                    vTAStab_10V }, new Integer(2));
        }
        Label l3 = new Label(controller.getBundle().getString("current-conso-tab-data-prem-nom") + "<br>"
                + controller.getBundle().getString("current-conso-tab-data-rob-TnomStab"), Label.CONTENT_XHTML);
        table.addItem(
                new Object[] { controller.getBundle().getString("table-header-moteur-tournant-label"), l3,
                        super.getUmoy13_5(tension.getCsEdsUmoy(), bt), vTXTTminStab13v, vTXTTmoyStab13v, vTXTTmaxStab13v, vTAStab_13V }, new Integer(
                        3));

        if (qcf.getQcfC2() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmax(tension.getCsEdsUmax(), bt), vTXTTminStab15v, vTXTTmoyStab15v, vTXTTmaxStab15v,
                    vTAStab_15V }, new Integer(4));
        }
        // ------------------------------------------

        if (qcf.getQcfC3() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmin(tension.getCsEdsUmin(), bt), vTXTTminPirecasStab10v, vTXTTmoyPirecasStab10v,
                    vTXTTmaxPirecasStab10v, vTAPirecasStab_10V }, new Integer(5));
        }

        Label l4 = new Label(controller.getBundle().getString("current-conso-tab-data-prem-pire") + "**<br>"
                + controller.getBundle().getString("current-conso-tab-data-tpirecas"), Label.CONTENT_XHTML);
        table.addItem(new Object[] { "", l4, super.getUmoy13_5(tension.getCsEdsUmoy(), bt), vTXTTminPirecasStab13v, vTXTTmoyPirecasStab13v,
                vTXTTmaxPirecasStab13v, vTAPirecasStab_13V }, new Integer(6));

        if (qcf.getQcfC2() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmax(tension.getCsEdsUmax(), bt), vTXTTminPirecasStab15v, vTXTTmoyPirecasStab15v,
                    vTXTTmaxPirecasStab15v, vTAPirecasStab_15V }, new Integer(7));
        }

        // ------------------------------------------
        Label l5 = new Label(controller.getBundle().getString("current-conso-tab-data-InomDem") + "<br>"
                + controller.getBundle().getString("current-conso-tab-data-TnomDem"), Label.CONTENT_XHTML);
        if (qcf.getQcfC4() == 1) {
            i++;
            table.addItem(new Object[] { controller.getBundle().getString("table-header-conso-demarrage-label"), l5,
                    controller.getBundle().getString("current-conso-tab-data-rob-Udem"), null, vTXTDem, null, vTADem }, new Integer(8));
        }

        if (qcf.getQcf1() == 0) {
            table.setVisibleColumns(new Object[] { "0", "1", "2", "4", "6" });
        }

        table.setPageLength(i);
        return table;
    }

    /**
     * This method show component for Effective current
     * 
     * @return component for Effective current
     */
    private Component getEfficacTab() {
        vTEfficace = new MyTable();

        vTEfficace.setPageLength(4);
        vTEfficace.setWidth("100%");
        vTEfficace.setSelectable(true);

        vTEfficace.addContainerProperty("0", Label.class, null, controller.getBundle().getString("table-header-etat-moteur-label"), null, null);
        vTEfficace.addContainerProperty("1", Label.class, null, controller.getBundle().getString("table-header-phase-vehicule-label"), null, null);
        vTEfficace.addContainerProperty("2", Label.class, null, controller.getBundle().getString("table-header-label-tension"), null, null);
        vTEfficace.addContainerProperty("3", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmin"), null,
                null);
        vTEfficace.addContainerProperty("4", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"), null,
                null);
        vTEfficace.addContainerProperty("5", TwinTextField.class, null, controller.getBundle().getString("current-conso-tab-data-at-Tmax"), null,
                null);
        vTEfficace.addContainerProperty("6", TextArea.class, null, controller.getBundle().getString("eds-comnent"), null, null);

        vTEfficace.addItem(
                new Object[] {
                        controller.getBundle().getString("table-header-moteur-non-tournant-label"),
                        new Label(controller.getBundle().getString("current-conso-tab-data-Ieff") + "<br>"
                                + controller.getBundle().getString("current-conso-tab-data-Teff"), Label.CONTENT_XHTML), "12,5V", vTXTTmineff12v,
                        vTXTTmoyeff12v, vTXTTmaxeff12v, vTAeff_12V }, new Integer(0));

        vTEfficace.addItem(new Object[] { "", "", super.getUmin(tension.getCsEdsUmin(), bt), vTXTTmineff10v, vTXTTmoyeff10v, vTXTTmaxeff10v,
                vTAeff_10V }, new Integer(1));

        vTEfficace.addItem(
                new Object[] { controller.getBundle().getString("table-header-moteur-tournant-label"),
                        controller.getBundle().getString("current-conso-tab-data-Ieff"), "13,5V", vTXTTmineff13v, vTXTTmoyeff13v, vTXTTmaxeff13v,
                        vTAeff_13V }, new Integer(2));

        vTEfficace.addItem(new Object[] { "", "", "15,5V", vTXTTmineff15v, vTXTTmoyeff15v, vTXTTmaxeff15v, vTAeff_15V }, new Integer(3));

        if (qcf.getQcf1() == 0) {
            vTEfficace.setVisibleColumns(new Object[] { "0", "1", "2", "4", "6" });
        }

        vTEfficace.setVisible(cnaedsIEfficace);
        vTEfficace.setCellStyleGenerator(new CellStyleGenerator() {
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "green";
                }
                int row = ((Integer) itemId).intValue();
                int col = Integer.parseInt((String) propertyId);
                if (row == 0) {
                    return "c1";
                } else {
                    return "white";
                }
            }
        });

        return vTEfficace;
    }

    /**
     * This method is called by Table when a cell (and row) is painted.
     * 
     * @param itemId The itemId of the painted cell
     * @param propertyId The propertyId of the cell, null when getting row style
     * @return The style name to add to this cell or row
     */
    public String getStyle(Object itemId, Object propertyId) {
        if (propertyId == null) {
            return "green";
        }
        int row = ((Integer) itemId).intValue();
        int col = Integer.parseInt((String) propertyId);

        if ((row == 1 || row == 0) && col < 1) {
            return "c1";
        } else if (row > 1 && row < 8 && col < 1) {
            return "c2";
        } else if (row == 8 && col < 2) {
            return "c5";
        } else if (row == 0 && col == 1) {
            return "c3";
        } else if (row == 1 && col == 1) {
            return "c4";
        } else if (row > 1 && row < 5 && col == 1) {
            return "c3";
        } else if (row > 4 && row < 8 && col == 1) {
            return "c4";
        } else if (row % 2 == 0 && col > 1) {
            return "c5";
        } else if (row % 2 != 0 && col > 1) {
            return "c6";
        } else {
            return "white";
        }

    }

    /**
     * This method set table editable based on specified boolean value
     * 
     * @param enabled Boolean to specify editable table
     */
    public void setEditable(boolean enabled) {
        vTEfficace.setEditable(enabled);

        vTXTTminStabMnt.setEnabled(enabled);
        vTXTTminStabMnt.setEnabled(enabled);
        vTXTTmoyStabMnt.setEnabled(enabled);
        vTXTTmoyStabMnt.setEnabled(enabled);
        vTXTTmaxStab12v.setEnabled(enabled);
        vTXTTmaxStab12v.setEnabled(enabled);
        vTAStab_12V.setEnabled(enabled);

        vTXTTminPirecasStab12v.setEnabled(enabled);
        vTXTTminPirecasStab12v.setEnabled(enabled);
        vTXTTmoyPirecasStab12v.setEnabled(enabled);
        vTXTTmoyPirecasStab12v.setEnabled(enabled);
        vTXTTmaxPirecasStab12v.setEnabled(enabled);
        vTXTTmaxPirecasStab12v.setEnabled(enabled);
        vTAPirecasStab_12V.setEnabled(enabled);

        vTXTTminStab10v.setEnabled(enabled);
        vTXTTminStab10v.setEnabled(enabled);
        vTXTTmoyStab10v.setEnabled(enabled);
        vTXTTmoyStab10v.setEnabled(enabled);
        vTXTTmaxStab10v.setEnabled(enabled);
        vTXTTmaxStab10v.setEnabled(enabled);
        vTAStab_10V.setEnabled(enabled);

        vTXTTminStab13v.setEnabled(enabled);
        vTXTTminStab13v.setEnabled(enabled);
        vTXTTmoyStab13v.setEnabled(enabled);
        vTXTTmoyStab13v.setEnabled(enabled);
        vTXTTmaxStab13v.setEnabled(enabled);
        vTXTTmaxStab13v.setEnabled(enabled);
        vTAStab_13V.setEnabled(enabled);

        vTXTTminStab15v.setEnabled(enabled);
        vTXTTminStab15v.setEnabled(enabled);
        vTXTTmoyStab15v.setEnabled(enabled);
        vTXTTmoyStab15v.setEnabled(enabled);
        vTXTTmaxStab15v.setEnabled(enabled);
        vTXTTmaxStab15v.setEnabled(enabled);
        vTAStab_15V.setEnabled(enabled);

        vTXTTminPirecasStab10v.setEnabled(enabled);
        vTXTTminPirecasStab10v.setEnabled(enabled);
        vTXTTmoyPirecasStab10v.setEnabled(enabled);
        vTXTTmoyPirecasStab10v.setEnabled(enabled);
        vTXTTmaxPirecasStab10v.setEnabled(enabled);
        vTXTTmaxPirecasStab10v.setEnabled(enabled);
        vTAPirecasStab_10V.setEnabled(enabled);

        vTXTTminPirecasStab13v.setEnabled(enabled);
        vTXTTminPirecasStab13v.setEnabled(enabled);
        vTXTTmoyPirecasStab13v.setEnabled(enabled);
        vTXTTmoyPirecasStab13v.setEnabled(enabled);
        vTXTTmaxPirecasStab13v.setEnabled(enabled);
        vTXTTmaxPirecasStab13v.setEnabled(enabled);
        vTAPirecasStab_13V.setEnabled(enabled);

        vTXTTminPirecasStab15v.setEnabled(enabled);
        vTXTTminPirecasStab15v.setEnabled(enabled);
        vTXTTmoyPirecasStab15v.setEnabled(enabled);
        vTXTTmoyPirecasStab15v.setEnabled(enabled);
        vTXTTmaxPirecasStab15v.setEnabled(enabled);
        vTXTTmaxPirecasStab15v.setEnabled(enabled);
        vTAPirecasStab_15V.setEnabled(enabled);

        vTXTDem.setEnabled(enabled);
        vTXTDem.setEnabled(enabled);
        vTADem.setEnabled(enabled);
    }

    /**
     * This method reset EdsQcf for all questions
     */
    private void resetQcf() {

        if (qcf.getQcf1() == 0) {
            resetQcf1();
        }
        if (qcf.getQcfC2() == 0) {
            resetQcfC2();
        }
        if (qcf.getQcfC3() == 0) {
            resetQcfC3();
        }
        if (qcf.getQcfC4() == 0) {
            resetQcfC4();
        }
    }

    /**
     * This method reset EdsQcf for 3rd Question
     */
    private void resetQcfC3() {
        vTXTTminStab10v.reset();
        vTXTTmoyStab10v.reset();
        vTXTTmaxStab10v.reset();
        vTAStab_10V.setText("");

        vTXTTminPirecasStab10v.reset();
        vTXTTmoyPirecasStab10v.reset();
        vTXTTmaxPirecasStab10v.reset();
        vTAPirecasStab_10V.setText("");
    }

    /**
     * This method reset EdsQcf for 2nd Question
     */
    private void resetQcfC2() {

        vTXTTminStab15v.reset();
        vTXTTmoyStab15v.reset();
        vTXTTmaxStab15v.reset();
        vTAStab_15V.setText("");

        vTXTTminPirecasStab15v.reset();
        vTXTTmoyPirecasStab15v.reset();
        vTXTTmaxPirecasStab15v.reset();
        vTAPirecasStab_15V.setText("");
    }

    /**
     * This method reset EdsQcf for 4th Question
     */
    private void resetQcfC4() {
        vTADem.setText("");
        vTXTDem.reset();
    }

    /**
     * This method reset EdsQcf for 1st Question
     */
    private void resetQcf1() {
        vTXTTminStabMnt.reset();
        vTXTTminPirecasStab12v.reset();
        vTXTTminStab10v.reset();
        vTXTTminStab13v.reset();
        vTXTTminStab15v.reset();
        vTXTTminPirecasStab10v.reset();
        vTXTTminPirecasStab13v.reset();
        vTXTTminPirecasStab15v.reset();

        vTXTTmaxStab12v.reset();
        vTXTTmaxPirecasStab12v.reset();
        vTXTTmaxStab10v.reset();
        vTXTTmaxStab13v.reset();
        vTXTTmaxStab15v.reset();
        vTXTTmaxPirecasStab10v.reset();
        vTXTTmaxPirecasStab13v.reset();
        vTXTTmaxPirecasStab15v.reset();

    }

}
