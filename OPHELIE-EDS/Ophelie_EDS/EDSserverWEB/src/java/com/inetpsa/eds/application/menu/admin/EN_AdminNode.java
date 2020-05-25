package com.inetpsa.eds.application.menu.admin;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.actionbar.discardchange.DiscardChangeButton;
import com.inetpsa.eds.application.actionbar.discardchange.I_Discardable;
import com.inetpsa.eds.application.actionbar.savechange.I_Savable;
import com.inetpsa.eds.application.actionbar.savechange.SaveChangeButton;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.dao.EDSdao;

/**
 * This class is used to save and discard changes made to an Edit form.
 * 
 * @author Geometric Ltd.
 */
public class EN_AdminNode extends A_EdsNavigationNode implements I_Discardable, I_Savable {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param name Window title name.
     * @param editForm Edit form.
     */
    public EN_AdminNode(EdsApplicationController controller, String name, A_EdsEditForm editForm) {
        super(controller);
        this.name = name;
        this.editForm = editForm;
        this.controller = controller;
        init();
    }

    /**
     * Initialization method.
     */
    private void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter()
     */
    @Override
    public void onEnter() {
        getController().getActionBar().clear();
        getController().setContent(editForm);
        editForm.prepare();
        editForm.discardChanges();
        getController().getActionBar().clear();
        getController().getActionBar().addLeftButton(getSaveButton());
        getController().getActionBar().addLeftButton(getDiscardButton());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        getController().getActionBar().clear();
        getController().getContent().removeAllComponents();
    }

    /**
     * This method is used to discard the changes made on the form
     * 
     * @see com.inetpsa.eds.application.actionbar.discardchange.I_Discardable#discard()
     */
    public void discard() {
        editForm.discardChanges();
    }

    /**
     * This method is used to save the changes made on the form.
     * 
     * @see com.inetpsa.eds.application.actionbar.savechange.I_Savable#save()
     */
    public void save() {
        if (editForm.isValid()) {
            if (editForm.commitChanges()) {

                for (Object object : editForm.getAllItemsToSave()) {
                    EDSdao.getInstance().mergeDetachedDBObject(object);
                }

                for (Object object : editForm.getAllItemsToDelete()) {
                    EDSdao.getInstance().deleteDetachedDBObject(object);
                }

                getController().getApplication().getMainWindow().showNotification(controller.getBundle().getString("Admin-save-mess"));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#areChildrenAllowed()
     */
    @Override
    public boolean areChildrenAllowed() {
        return false;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the edit form.
     */
    private A_EdsEditForm editForm;
    /**
     * Button to save changes.
     */
    private SaveChangeButton vBTsave;
    /**
     * Button to discard changes.
     */
    private DiscardChangeButton vBTdiscard;
    /**
     * Variable to store EDS application controller.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store window title.
     */
    private String name;

    /**
     * The method retrieves the save button object.
     * 
     * @return Save button.
     */
    private SaveChangeButton getSaveButton() {
        if (vBTsave == null) {
            vBTsave = new SaveChangeButton(this, controller);
        }
        return vBTsave;
    }

    /**
     * The method retrieves the discard button object.
     * 
     * @return Discard button.
     */
    private DiscardChangeButton getDiscardButton() {
        if (vBTdiscard == null) {
            vBTdiscard = new DiscardChangeButton(this, controller);
        }
        return vBTdiscard;
    }
}
