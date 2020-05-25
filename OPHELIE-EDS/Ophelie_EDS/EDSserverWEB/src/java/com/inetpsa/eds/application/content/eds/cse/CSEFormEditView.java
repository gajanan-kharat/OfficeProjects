package com.inetpsa.eds.application.content.eds.cse;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsCseFormData;
import com.inetpsa.eds.dao.model.EdsCseLine;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Item;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class provide component for editing view of CSE form
 * 
 * @author Geometric Ltd.
 */
public class CSEFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param formData object of EdsCseFormData
     * @param controller Controller of EDS application
     * @param eds Object of EdsEds
     */
    public CSEFormEditView(EdsCseFormData formData, EdsApplicationController controller, EdsEds eds) {
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isTableValid = editableTable.isValid();

        if (!isTableValid) {
            controller.showError(editableTable.getValidationErrorMessage());
        }

        return isTableValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        editableTable.commit();

        formData.addEdsCseLines(linesToAdd);
        formData.removeEdsCseLines(linesToRemove);

        for (EdsCseLine line : formData.getEdsCseLines()) {
            editableTable.updateCseLine(line);
        }

        Iterator<EdsCseLine> it = formData.getEdsCseLines().iterator();
        while (it.hasNext()) {
            EdsCseLine line = it.next();
            if (line.getCselDataname() == null) {
                it.remove();
            }
        }

        eds.setEdsModifDate(new Date());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        editableTable.discard();
        linesToAdd.clear();
        linesToRemove.clear();
        linesMap.clear();

        editableTable.setCseList(formData.getEdsCseLines());
        for (EdsCseLine line : formData.getEdsCseLines()) {
            linesMap.put(line.getCselId(), line);
        }

        EdsCseLine line = editableTable.createNewCse(formData);
        linesToAdd.add(line);
        linesMap.put(line.getCselId(), line);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<EdsCseFormData> getAllItemsToSave() {
        return Collections.singletonList(formData);
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

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ADD_ICON
     */
    private static final Resource ADD_ICON = ResourceManager.getInstance().getThemeIconResource("icons/activate.png");
    /**
     * Constant to hold value of REMOVE_ICON
     */
    private static final Resource REMOVE_ICON = ResourceManager.getInstance().getThemeIconResource("icons/delete2.png");
    /**
     * Variable to hold value of EdsCseFormData
     */
    private EdsCseFormData formData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of CseEditableTable
     */
    private CseEditableTable editableTable;
    /**
     * Variable to hold set of EdsCseLine to add
     */
    private HashSet<EdsCseLine> linesToAdd;
    /**
     * Variable to hold set of EdsCseLine to remove
     */
    private HashSet<EdsCseLine> linesToRemove;
    /**
     * Variable to hold map of String and EdsCseLine
     */
    private HashMap<String, EdsCseLine> linesMap;

    /**
     * Initialize CSE Form edit view
     */
    private void init() {
        this.linesToAdd = new HashSet<EdsCseLine>();
        this.linesToRemove = new HashSet<EdsCseLine>();
        this.linesMap = new HashMap<String, EdsCseLine>();

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSizeFull();
        tableLayout.setSpacing(true);

        this.editableTable = new CseEditableTable(controller);
        this.editableTable.setSizeFull();

        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setSpacing(true);
        // add CSE
        Button vBTaddCse = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                EdsCseLine line = editableTable.createNewCse(formData);
                linesToAdd.add(line);
                linesMap.put(line.getCselId(), line);
            }
        });
        vBTaddCse.setIcon(ADD_ICON);
        vBTaddCse.setDescription(controller.getBundle().getString("CSE-add-CSE"));
        vBTaddCse.setStyleName(BaseTheme.BUTTON_LINK);
        // Remove CSE
        Button vBTremoveCse = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Set<String> selectedIds = (Set<String>) editableTable.getValue();

                for (String itemId : selectedIds) {
                    Item item = editableTable.getItem(itemId);

                    if ((Integer) item.getItemProperty(CseEditableTable.PROPERTY_NUMBER).getValue() != 1
                            && (Integer) item.getItemProperty(CseEditableTable.PROPERTY_NUMBER).getValue() != 2) {
                        linesToAdd.remove(linesMap.get(itemId));
                        linesToRemove.add(linesMap.get(itemId));
                        editableTable.removeCse(linesMap.get(itemId));
                        linesMap.remove(itemId);
                    }
                }
            }
        });
        vBTremoveCse.setIcon(REMOVE_ICON);
        vBTremoveCse.setDescription(controller.getBundle().getString("CSE-suppr-CSE"));
        vBTremoveCse.setStyleName(BaseTheme.BUTTON_LINK);
        buttonsLayout.addComponent(vBTaddCse);
        buttonsLayout.addComponent(vBTremoveCse);
        buttonsLayout.setComponentAlignment(vBTaddCse, Alignment.BOTTOM_CENTER);
        buttonsLayout.setComponentAlignment(vBTremoveCse, Alignment.TOP_CENTER);

        tableLayout.addComponent(this.editableTable);
        tableLayout.addComponent(buttonsLayout);
        tableLayout.setExpandRatio(this.editableTable, 1f);
        tableLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        this.addComponent(tableLayout);
    }
}
