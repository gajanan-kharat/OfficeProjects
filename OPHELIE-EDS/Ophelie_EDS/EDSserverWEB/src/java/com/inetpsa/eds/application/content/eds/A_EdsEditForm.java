package com.inetpsa.eds.application.content.eds;

import java.util.Collection;

import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.I_Tensions;
import com.vaadin.ui.VerticalLayout;

/**
 * This is abstract class for Editing form
 * 
 * @author Geometric Ltd.
 */
public abstract class A_EdsEditForm extends VerticalLayout implements I_Tensions {
    // PUBLIC
    /**
     * Check if data form is valid
     * 
     * @return true if the data forms are valid.
     */
    public abstract boolean isValid();

    /**
     * Check if validation for the data form is valid
     * 
     * @return 0 if data forms are valid
     */
    public int isValidEDS() {
        return -2;
    }

    /**
     * Saves changes to the form in the associated data model. This method should also update the last modified date of listing.
     * 
     * @return true if the operation succeeds.
     */
    public abstract boolean commitChanges();

    /**
     * Resets the form back to the original data.
     */
    public abstract void discardChanges();

    /**
     * Returns all items to be saved in Database
     * 
     * @return objects to be stored in the database.
     */
    public abstract Collection getAllItemsToSave();

    /**
     * Returns all items to be deleted from Database
     * 
     * @return orphans to be deleted from the database objects.
     */
    public abstract Collection getAllItemsToDelete();

    /**
     * model form data to update.
     */
    public abstract void refreshItems();

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

    // PROTECTED
    // PRIVATE

    public void prepare() {
        // Nothing
    }

}
