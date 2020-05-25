package com.inetpsa.eds.application.content.eds.connectivity.bsx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * Panel displaying a BSX pins and name.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public class BSXPanel extends Panel {

    // PUBLIC

    /**
     * Panel constructor.
     * 
     * @param title Title.
     * @param bsx BSX to display.
     * @param chs The CHS imported.
     */
    public BSXPanel(String title, EdsApplicationController controller, EdsBsx bsx, Set<Chs> chs, boolean editableComment) {
        super(title);
        this.chs = chs;
        this.bsx = bsx;
        this.editableComment = editableComment;

        this.pinIn = new ArrayList<EdsBsxPin>();
        this.pinOut = new ArrayList<EdsBsxPin>();

        this.bundle = controller.getBundle();

        init();
        updateValues();
    }

    /**
     * Change the panel BSX, and update values.
     * 
     * @param bsx The new BSX to display.
     */
    public void setBsx(EdsBsx bsx) {
        this.bsx = bsx;
        updateValues();
    }

    /**
     * Change the panel CHS list, and update values.
     * 
     * @param chs The CHS imported.
     */
    public void setChs(Set<Chs> chs) {
        this.chs = chs;
        updateValues();
    }

    /**
     * Return the BSX comment.
     * 
     * @return The BSX comment.
     */
    public String getComment() {
        if (editableComment)
            return (String) bsxCommentEditable.getValue();
        else
            return bsx != null ? bsx.getComment() : "-";
    }

    /**
     * Update the BSX table.
     */
    @SuppressWarnings("unchecked")
    public void updateValues() {
        // Pin IN
        pinIn.clear();
        if (bsx != null) {
            pinIn.addAll(bsx.getPinIn());
            Collections.sort(pinIn);
        }

        tablePinIn.removeAllItems();

        int i = 0;

        if (bsx != null) {
            for (EdsBsxPin pin : pinIn) {
                tablePinIn.addItem(new Object[] { pin.getName() }, i++);
            }
        }

        // Pin OUT
        pinOut.clear();
        if (bsx != null) {
            pinOut.addAll(bsx.getPinOut());
            Collections.sort(pinOut);
        }

        tablePinOut.removeAllItems();

        i = 0;

        if (bsx != null) {
            for (EdsBsxPin pin : pinOut) {
                tablePinOut.addItem(new Object[] { pin.getName() }, i++);
            }
        }

        // BSX name
        bsxName.setCaption(bundle.getString("bsx-name") + (bsx != null ? bsx.getName() : "-"));

        // BSX path
        // bsxPath.setCaption(bundle.getString("bsx-path") + (bsx != null ? bsx.getPath() : "-"));

        // BSX comment
        if (editableComment) {
            String comment = "";
            if (bsx != null && bsx.getComment() != null)
                comment = bsx.getComment();
            bsxCommentEditable.setValue(comment);
        } else {
            bsxComment.setCaption(bundle.getString("bsx-comment") + " " + (bsx != null ? bsx.getComment() : "-"));
        }
    }

    // PRIVATE
    private Table tablePinIn;
    private ArrayList<EdsBsxPin> pinIn;
    private Table tablePinOut;
    private ArrayList<EdsBsxPin> pinOut;
    private Label bsxName;
    // private Label bsxPath;
    private Label bsxComment;
    private TextArea bsxCommentEditable;
    private ResourceBundle bundle;

    private EdsBsx bsx;
    private Set<Chs> chs;
    private boolean editableComment;

    /**
     * Initialize the panel.
     */
    private void init() {
        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout panelHl = new HorizontalLayout();

        // Pin IN table
        tablePinIn = new Table();
        tablePinIn.addContainerProperty(bundle.getString("bsx-pin-in"), String.class, null);

        tablePinIn.setCellStyleGenerator(new Table.CellStyleGenerator() {

            public String getStyle(Object itemId, Object propertyId) {
                int i = ((Integer) itemId).intValue();

                if (i >= pinIn.size())
                    return "normal";

                EdsBsxPin bsxPin = pinIn.get(i);

                for (Chs c : chs) {
                    for (ChsPin chsPin : c.getCavities()) {
                        if (bsxPin.isSamePinAs(chsPin.getCavity()))
                            return "green";
                    }
                }

                return "normal";
            }

        });

        panelHl.addComponent(tablePinIn);

        // Pin OUT table
        tablePinOut = new Table();
        tablePinOut.addContainerProperty(bundle.getString("bsx-pin-out"), String.class, null);

        tablePinOut.setCellStyleGenerator(new Table.CellStyleGenerator() {

            public String getStyle(Object itemId, Object propertyId) {
                int i = ((Integer) itemId).intValue();

                if (i >= pinOut.size())
                    return "normal";

                EdsBsxPin bsxPin = pinOut.get(i);

                for (Chs c : chs) {
                    for (ChsPin chsPin : c.getCavities()) {
                        if (bsxPin.isSamePinAs(chsPin.getCavity()))
                            return "green";
                    }
                }

                return "normal";
            }

        });

        panelHl.addComponent(tablePinOut);

        // BSX name
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setStyleName("bsx-info-vlayout");
        bsxName = new Label();
        infoLayout.addComponent(bsxName);

        // BSX path
        // bsxPath = new Label();
        // infoLayout.addComponent(bsxPath);

        // BSX comment
        if (editableComment) {
            bsxCommentEditable = new TextArea(bundle.getString("bsx-comment"));
            infoLayout.addComponent(bsxCommentEditable);
        } else {
            bsxComment = new Label();
            infoLayout.addComponent(bsxComment);
        }

        panelHl.addComponent(infoLayout);
        mainLayout.addComponent(panelHl);
        addComponent(mainLayout);
    }

}
