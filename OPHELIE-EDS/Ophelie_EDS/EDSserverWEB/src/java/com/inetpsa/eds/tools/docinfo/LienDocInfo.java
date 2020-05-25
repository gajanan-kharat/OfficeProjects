package com.inetpsa.eds.tools.docinfo;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.themes.BaseTheme;
import java.net.URL;

/**
 * This class provide component for performing various actions on attached documents
 * 
 * @author Geometric Ltd.
 */
public class LienDocInfo extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param urlDocInfo URL of document
     * @param controller Controller of EDS application
     */
    public LienDocInfo(URL urlDocInfo, EdsApplicationController controller) {
        this(urlDocInfo, null, controller);
    }

    /**
     * Parameterized constructor
     * 
     * @param urlDocInfo URL of Document
     * @param removeClickListener Remove click listener
     * @param controller Controller of EDS application
     */
    public LienDocInfo(URL urlDocInfo, Button.ClickListener removeClickListener, EdsApplicationController controller) {
        this.urlDocInfo = urlDocInfo;
        this.removeClickListener = removeClickListener;
        this.controller = controller;
        init();
    }

    /**
     * This method set button to Delete link
     * 
     * @param removeClickListener Remove button click listener
     */
    public void setRemovable(Button.ClickListener removeClickListener) {
        this.removeClickListener = removeClickListener;

        if (removeClickListener != null) {
            vBTremove = new Button(null, removeClickListener);

            vBTremove.setIcon(ICON_REMOVE);
            vBTremove.setStyleName(BaseTheme.BUTTON_LINK);
            vBTremove.setDescription(controller.getBundle().getString("Admin-lib-file-suppr-lync"));

            addComponent(vBTremove, 2, 0);
            setComponentAlignment(vBTremove, Alignment.BOTTOM_LEFT);
        }
    }

    /**
     * This method Expands the document link
     */
    public void expandLink() {
        vBTexpandCollapse.setIcon(ICON_COLLAPSED);
        vEMBdocInfo.setVisible(true);
    }

    /**
     * This method collapse the Document link
     */
    public void collapseLink() {
        vBTexpandCollapse.setIcon(ICON_EXPANDED);
        vEMBdocInfo.setVisible(false);
    }

    /**
     * This method switch expand state of document
     */
    public void switchExpandState() {
        if (vEMBdocInfo.isVisible()) {
            collapseLink();
        } else {
            expandLink();
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ICON_EXPANDED
     */
    private static final Resource ICON_EXPANDED = ResourceManager.getInstance().getThemeIconResource("icons/expand.png");
    /**
     * Constant to hold value of ICON_COLLAPSED
     */
    private static final Resource ICON_COLLAPSED = ResourceManager.getInstance().getThemeIconResource("icons/shrink.png");
    /**
     * Constant to hold value of ICON_REMOVE
     */
    private static final Resource ICON_REMOVE = ResourceManager.getInstance().getThemeIconResource("icons/erase.png ");
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of URL
     */
    private URL urlDocInfo;
    /**
     * Variable to hold value of Remove button click listener
     */
    private Button.ClickListener removeClickListener;
    /**
     * Variable to hold value of Button for expand or collapse
     */
    private Button vBTexpandCollapse;
    /**
     * Variable to hold value of Link to Document
     */
    private Link vLBLdocInfo;
    /**
     * Variable to hold value of remove Button
     */
    private Button vBTremove;
    /**
     * Variable to hold value of Embedded object
     */
    private Embedded vEMBdocInfo;

    /**
     * Initialize LienDocInfos
     */
    private void init() {
        // Layout
        setColumns(3);
        setRows(2);
        setSizeFull();
        setSpacing(true);

        setColumnExpandRatio(0, 0f);
        setColumnExpandRatio(1, 0f);
        setColumnExpandRatio(2, 1f);

        // Expand/Collapse button
        vBTexpandCollapse = new Button(null, new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                switchExpandState();
            }
        });
        vBTexpandCollapse.setIcon(ICON_EXPANDED);
        vBTexpandCollapse.setStyleName(BaseTheme.BUTTON_LINK);
        vBTexpandCollapse.setDescription(controller.getBundle().getString("docinfo-open"));

        addComponent(vBTexpandCollapse, 0, 0);
        setComponentAlignment(vBTexpandCollapse, Alignment.BOTTOM_LEFT);

        // Link
        vLBLdocInfo = new Link(urlDocInfo.toString(), new ExternalResource(urlDocInfo));
        vLBLdocInfo.setTargetName("_blank");

        addComponent(vLBLdocInfo, 1, 0);
        setComponentAlignment(vLBLdocInfo, Alignment.BOTTOM_LEFT);

        // Remove button if removeClickListener defined
        if (removeClickListener != null) {
            vBTremove = new Button(null, removeClickListener);

            vBTremove.setIcon(ICON_REMOVE);
            vBTremove.setStyleName(BaseTheme.BUTTON_LINK);
            vBTremove.setDescription(controller.getBundle().getString("Admin-lib-file-suppr-lync"));

            addComponent(vBTremove, 2, 0);
            setComponentAlignment(vBTremove, Alignment.BOTTOM_LEFT);
        }

        // Link embedding
        vEMBdocInfo = new Embedded(null, new ExternalResource(urlDocInfo));

        vEMBdocInfo.setType(Embedded.TYPE_BROWSER);
        vEMBdocInfo.setWidth("100%");
        vEMBdocInfo.setHeight("400px");
        vEMBdocInfo.setVisible(false);

        addComponent(vEMBdocInfo, 0, 1, 2, 1);
    }
}
