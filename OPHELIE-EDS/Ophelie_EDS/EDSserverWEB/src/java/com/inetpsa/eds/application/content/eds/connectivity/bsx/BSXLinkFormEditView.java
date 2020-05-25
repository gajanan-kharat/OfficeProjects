package com.inetpsa.eds.application.content.eds.connectivity.bsx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.bsxdesigner.BSXdesignerController;
import com.inetpsa.bsxmanager.BSXBoxController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.gestion.GestionBrowserTree;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.gestion.sync.model.BSXNodeFS;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * BSX Link form edit view.
 */
public class BSXLinkFormEditView extends A_EdsEditForm {

    private static Logger logger = Logger.getLogger(BSXLinkFormEditView.class);

    /**
     * Form constructor.
     * 
     * @param eds The EDS.
     * @param controller The controller.
     */
    public BSXLinkFormEditView(EdsEds eds, EdsApplicationController controller) {
        super();
        this.eds = eds;
        this.controller = controller;
    }

    @Override
    public boolean isValid() {
        // Always true
        return true;
    }

    @Override
    public boolean commitChanges() {
        // Ignore cases where the box is the same and changed/reloaded
        if (eds.getEdsBsxUnique() != savedBsx) {

            // Delete the existing BSX (if any)
            if (eds.getEdsBsxUnique() != null) {
                for (EdsBsx bsx : eds.getEdsBsx()) {
                    toDelete.addAll(bsx.getPin());
                    toDelete.add(bsx);
                }
            }
        }

        // Save the saved BSX, and update the EDS
        if (savedBsx != null) {
            savedBsx.setComment(savedBsxPanel.getComment());
            toSave.add(savedBsx);
            toSave.addAll(savedBsx.getPin());
        }
        eds.setEdsBsxUnique(savedBsx);
        clearCrossedPins();
        return true;
    }

    @Override
    public void discardChanges() {
        // Set values back to before
        savedBsx = eds.getEdsBsxUnique();
        selectedBsx = null;

        // Update views
        savedBsxPanel.setBsx(savedBsx);
        selectedBsxPanel.setBsx(selectedBsx);
    }

    @Override
    public Collection<Object> getAllItemsToSave() {
        return new ArrayList<Object>(toSave);
    }

    @Override
    public Collection<Object> getAllItemsToDelete() {
        return new ArrayList<Object>(toDelete);
    }

    @Override
    public void refreshItems() {
        if (initialized) {
            eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());
            selectedBsxPanel.setChs(eds.getEdsChs());
            savedBsxPanel.setChs(eds.getEdsChs());
            discardChanges();
        }
    }

    @Override
    public void prepare() {
        if (!initialized)
            init();

        try {
            gestionBrowser.refresh();
        } catch (Exception e) {
            controller.showError(controller.getBundle().getString("bsx-error"));
        }
    }

    // PRIVATE
    private EdsApplicationController controller;
    private EdsBsx selectedBsx;
    private EdsBsx savedBsx;
    private EdsEds eds;

    private GestionBrowserTree gestionBrowser;
    private BSXPanel selectedBsxPanel;
    private BSXPanel savedBsxPanel;

    private BSXdesignerController reader;

    private LinkedHashSet<Object> toDelete = new LinkedHashSet<Object>();
    private LinkedHashSet<Object> toSave = new LinkedHashSet<Object>();

    private String unmatchedPins;

    private boolean initialized = false;

    /**
     * Initialization method.
     */
    private void init() {
        if (initialized)
            return;

        initialized = true;

        // Main panel
        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        hl.setMargin(false);
        hl.setSpacing(false);
        hl.setWidth("100%");

        // BSX selection panel
        addBSXSelectionPanel(hl);

        // Selected BSX panel
        selectedBsxPanel = new BSXPanel(controller.getBundle().getString("bsx-selected"), controller, null, eds.getEdsChs(), false);
        hl.addComponent(selectedBsxPanel);

        // Select button
        Button select = new Button(controller.getBundle().getString("bsx-select-action"));
        select.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (selectedBsx != null) {

                    if (!validCrossing()) {
                        showCrossWarning(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                if (savedBsx != null) {
                                    showWarning(new Callable<Void>() {
                                        @Override
                                        public Void call() throws Exception {
                                            selectBSX();
                                            return null;
                                        }
                                    });

                                } else {
                                    selectBSX();
                                }
                                return null;
                            }
                        });
                    } else if (savedBsx != null) {
                        showWarning(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                selectBSX();
                                return null;
                            }
                        });
                    } else {
                        selectBSX();
                    }
                }
            }
        });
        selectedBsxPanel.addComponent(select);

        // Saved BSX panel
        savedBsxPanel = new BSXPanel(controller.getBundle().getString("bsx-saved"), controller, null, eds.getEdsChs(), true);
        hl.addComponent(savedBsxPanel);

        // Deselection button
        Button deselect = new Button(controller.getBundle().getString("bsx-deselect-action"));
        deselect.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (savedBsx != null) {
                    showWarning(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            deselectBSX();
                            return null;
                        }
                    });
                }
            }
        });
        savedBsxPanel.addComponent(deselect);

        mainLayout.addComponent(hl);
        addComponent(mainLayout);
    }

    /**
     * Save the currently selected BSX, and make it the saved one.
     */
    private void selectBSX() {
        // Update ref and view
        savedBsx = selectedBsx;
        savedBsxPanel.setBsx(savedBsx);

    }

    private void clearCrossedPins() {
        Set<EdsBsxPin> bsxPins;
        if (savedBsx != null) {
            for (EdsPinConnect pin : eds.getEdsPinConnect()) {
                bsxPins = savedBsx.getPin();
                for (EdsBsxPin p : bsxPins) {
                    if (p.isSamePinAs(pin.getCavity())) {
                        pin.setCrossed(true);
                        if (pin.getSpProperty() != null) {
                            pin.getSpProperty().clear();
                        }
                        if (pin.getCpProfile() != null) {
                            pin.getCpProfile().clear();
                        }
                        if (pin.getCpProperty() != null) {
                            pin.getCpProperty().clear();
                        }
                        pin.setComplexProfile(null);
                        pin.setSimpleProfile(null);
                        pin.setSupply(null);

                        if (pin.getWiProperty() != null) {
                            pin.getWiProperty().clear();
                        }
                        pin.setWiredInterface(null);
                        pin.setIfImpedance(null);
                    }
                }
            }
            toSave.add(eds);
        }
    }

    /**
     * Deselect the currently saved BSX.
     */
    private void deselectBSX() {
        // Update ref and view
        savedBsx = null;
        savedBsxPanel.setBsx(null);
    }

    /**
     * Validates the possibility of crossing and gives aditional warnings. Fills the unmatched String for the warning
     */
    private boolean validCrossing() {
        StringBuilder builder = new StringBuilder();
        Set<EdsPinConnect> chsPins = eds.getEdsPinConnect();
        Set<EdsBsxPin> bsxPins = selectedBsx.getPin();
        boolean valid = true;
        boolean matched;
        for (EdsBsxPin bsx : bsxPins) {
            matched = false;
            for (EdsPinConnect chs : chsPins) {
                if (bsx.isSamePinAs(chs.getCavity())) {
                    matched = true;
                    generateMismatch(builder, bsx, chs);
                    if (!bsx.getType().equals(chs.getPinType())) {
                        valid = false;
                    }

                }
            }
            if (!matched) {
                generateMismatch(builder, bsx, null);
                valid = false;
            }

        }
        unmatchedPins = builder.toString();
        return valid;

    }

    private void generateMismatch(StringBuilder builder, EdsBsxPin bsx, EdsPinConnect chs) {
        builder.append(bsx.getName());
        builder.append("(");
        builder.append(bsx.getType());
        builder.append(")");
        if (chs != null) {
            builder.append(" - ");
            builder.append(chs.getCavity());
            builder.append("(");
            builder.append(chs.getPinType());
            builder.append(")");
        }
        builder.append("<br>");
    }

    /**
     * Show a warning, and call the given callback if the user accept the warning.
     * 
     * @param callback Called after the pop-up.
     */
    private void showWarning(final Callable<Void> callback) {
        ConfirmDialog dialog = ConfirmDialog.show(controller.getUserWindow().getWindow(), controller.getBundle().getString("bsx-dialog-title"),
                controller.getBundle().getString("bsx-dialog-message"), controller.getBundle().getString("bsx-dialog-confirm"), controller
                        .getBundle().getString("bsx-dialog-cancel"), new ConfirmDialog.Listener() {

                    @Override
                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {
                            try {
                                callback.call();
                            } catch (Exception e) {
                                // Ignore
                            }
                        }
                    }
                });
        dialog.setContentMode(ConfirmDialog.CONTENT_HTML);
    }

    /**
     * Show a warning message for possible mismatch crossing.
     * 
     * @param callback Called after the pop-up
     */
    private void showCrossWarning(final Callable<Void> callback) {
        StringBuilder build = new StringBuilder();
        build.append(controller.getBundle().getString("bsx-dialog-cross-warning"));
        build.append("<br>");
        build.append(unmatchedPins);

        ConfirmDialog dialog = ConfirmDialog.show(controller.getUserWindow().getWindow(), controller.getBundle().getString("bsx-dialog-cross-title"),
                build.toString(), controller.getBundle().getString("bsx-dialog-confirm"), controller.getBundle().getString("bsx-dialog-cancel"),
                new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {
                            try {
                                callback.call();
                            } catch (Exception e) {
                                // Ignore
                            }
                        }
                    }
                });
        dialog.setContentMode(ConfirmDialog.CONTENT_HTML);
        dialog.setResizable(true);
        dialog.setHeight("250px");
    }

    /**
     * Add the BSX selection panel. The panel will display BSX boxed that can be imported from Gestion server.
     * 
     * @param hl The layout where to append the panel.
     */
    private void addBSXSelectionPanel(HorizontalLayout hl) {
        Panel panel = new Panel(controller.getBundle().getString("bsx-explorer")); // BSX box choice

        // Custom type names
        Map<Integer, String> containers = new HashMap<Integer, String>();        
        containers.put(814, controller.getBundle().getString("bsx-box")); // {813=BSX Box}

        // Custom type icons
        Map<Integer, ThemeResource> icons = new HashMap<Integer, ThemeResource>();
        icons.put(814, new ThemeResource("icons/bsxDesignerIcon.png")); // To bring BSX boxes from Gestion whose typecontent is 813

        try {
            this.gestionBrowser = new GestionBrowserTree(controller, containers) {

            };
            // GL code addition for CUG-0225
            this.gestionBrowser.addListener(new Property.ValueChangeListener() {
                @SuppressWarnings("unchecked")
                @Override
                public void valueChange(ValueChangeEvent event) {
                    if (event.getProperty().getValue() instanceof BSXNodeFS) {
                        BSXNodeFS bsxNode = (BSXNodeFS) event.getProperty().getValue();
                        try {
                            BSXBoxController bsxBoxController = GestionConnector.loadBSXBoxDistribContainer(bsxNode.getDistribContainerID());
                            selectedBsx = new EdsBsx(bsxBoxController, bsxNode);
                            // DO we have to set name here

                        } catch (GestionConnectorException ex) {
                            logger.error("Exception occured while reading BSX box Cavities", ex);

                            selectedBsx = new EdsBsx(UUID.randomUUID().toString(), bsxNode.getParent().getID(), bsxNode.getParent().getName(), "",
                                    "", Collections.EMPTY_SET, Collections.EMPTY_SET);

                        } finally {
                            selectedBsx.setEdsEds(eds);
                            selectedBsxPanel.setBsx(selectedBsx);
                        }
                    }
                }

            });

            panel.addComponent(this.gestionBrowser);
        } catch (GestionConnectorException e) {
            controller.showError(controller.getBundle().getString("bsx-error"));
        } catch (Exception e) {
            controller.showError(controller.getBundle().getString("bsx-error"));
        }
        hl.addComponent(panel);

    }
}