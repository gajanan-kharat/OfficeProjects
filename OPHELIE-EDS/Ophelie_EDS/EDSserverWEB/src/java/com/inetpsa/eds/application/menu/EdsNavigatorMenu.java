package com.inetpsa.eds.application.menu;

import java.util.HashMap;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.CaptionPath;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.DriverDriftsFormBuilder;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.application.menu.edsnode.EN_EdsNode;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.resource.ResourceManager;

/**
 * This class is used to create EDS navigator menu.
 * 
 * @author Geometric Ltd.
 */
public class EdsNavigatorMenu extends A_EdsNavigationMenu {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsNavigatorMenu(EdsApplicationController controller) {
        super(controller, controller.getBundle().getString("menu-app-dur-EDS"));
        init();
    }

    /**
     * This method is used to select an EDS in EDS navigator. On selection of the EDS the navigator expands all the child nodes.
     * 
     * @param edsId EDS to be selected.
     */
    public void selectEds(String edsId) {
        unselectNode();

        EN_ContainerNode edsNode = edsNodes.get(edsId);
        if (!isExpanded(edsNode)) {
            expandItem(edsNode);
        }
        select(getChildren(edsNode).iterator().next());
    }

    /**
     * This method is used to select drift driver in EDS navigator.
     * 
     * @param edsId EDS to be selected.
     */
    public void selectDriftDriver(String edsId) {
        unselectNode();

        A_EdsNavigationNode driftDriverNode = driftDriverNodes.get(edsId);
        if (driftDriverNode != null && !isExpanded(getParent(driftDriverNode))) {
            expandItem(getParent(driftDriverNode));
        }
        select(driftDriverNode);
    }

    /**
     * This method is used to add nodes to the EDS navigator menu.
     * 
     * @param eds EDS details to be added.
     * @return true if EDS is added, else false.
     */
    public boolean addEdsNode(EdsEds eds) {

        if (edsNodes.containsKey(eds.getEdsId())) {
            return false;
        }

        // Add the card to navigation
        EN_EdsNode edsNode = new EN_EdsNode(controller, eds);
        addNode(edsNode);
        navigationTree.setItemIcon(edsNode, ResourceManager.getInstance().getThemeIconResource("icons/eds.png"));

        HashMap<CaptionPath, EN_ContainerNode> pathNodes = new HashMap<CaptionPath, EN_ContainerNode>();
        Set<String> formSetIds = eds.isBttbt() ? eds.getEdsComponentType().getAllBttbtbForms() : eds.getEdsComponentType().getAllNonBttbtbForms();

        for (String formId : formSetIds) {
            I_FormBuilder builder = EdsFormFactory.getBuilder(formId);

            if (controller.userHasSufficientRights(builder.getReadingRightId())) {
                EN_ContainerNode parentNode = edsNode;

                for (int i = 0; i < builder.getCaptionPath().getStringPath().length; ++i) {
                    String[] currentPathCopy = new String[i + 1];
                    for (int j = 0; j < currentPathCopy.length; ++j) {
                        currentPathCopy[j] = builder.getCaptionPath().getStringPath()[j];
                    }
                    CaptionPath currentPath = new CaptionPath(currentPathCopy);

                    if (!pathNodes.containsKey(currentPath)) {
                        EN_ContainerNode newContainerNode = new EN_ContainerNode(controller, controller.getBundle().getString(
                                builder.getCaptionPath().getStringPath()[i]));
                        addNode(newContainerNode, parentNode);
                        navigationTree.setItemIcon(newContainerNode, EdsFormFactory.getPathIcon(builder.getCaptionPath()));
                        parentNode = newContainerNode;
                        pathNodes.put(currentPath, newContainerNode);
                    } else {
                        parentNode = pathNodes.get(currentPath);
                    }
                }

                A_EdsNavigationNode node = builder.buildNode(controller, eds);
                // Hack to save the node driver drift
                if (builder.getUniqueId().equals(DriverDriftsFormBuilder.ID)) {
                    driftDriverNodes.put(eds.getEdsId(), node);
                }
                addNode(node, parentNode);
                navigationTree.setItemIcon(node, builder.getIcon());
            }
        }

        edsNodes.put(eds.getEdsId(), edsNode);

        if (!isVisible()) {
            setVisible(true);
        }

        return true;
    }

    /**
     * This method is used to retrieve the EDS node based on the edsId.
     * 
     * @param edsId EDS id of the node to be selected.
     * @return EDS navigation node.
     */
    public A_EdsNavigationNode getEsdNode(String edsId) {
        return edsNodes.get(edsId);
    }

    /**
     * This method is used to retrieve the Drift driver based on the edsId.
     * 
     * @param edsId EDS id of the node to be selected.
     * @return EDS navigation node.
     */
    public A_EdsNavigationNode getDriftDriverNode(String edsId) {
        return driftDriverNodes.get(edsId);
    }

    /**
     * This method is used to remove EDS node from both EDS and Drift driver navigation menu.
     * 
     * @param edsId EDS id of the node to be removed.
     */
    public void removeEdsNode(String edsId) {
        if (edsNodes.containsKey(edsId)) {
            removeNodeRecursively(edsNodes.get(edsId));
            edsNodes.remove(edsId);
            driftDriverNodes.remove(edsId);

            if (edsNodes.isEmpty()) {
                setVisible(false);
            }
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Map of all the EDS nodes.
     */
    private HashMap<String, EN_EdsNode> edsNodes;
    /**
     * Map of all the drift driver nodes.
     */
    private HashMap<String, A_EdsNavigationNode> driftDriverNodes;

    /**
     * Initialization method.
     */
    private void init() {
        this.edsNodes = new HashMap<String, EN_EdsNode>();
        this.driftDriverNodes = new HashMap<String, A_EdsNavigationNode>();

        this.setVisible(false);
    }
}
