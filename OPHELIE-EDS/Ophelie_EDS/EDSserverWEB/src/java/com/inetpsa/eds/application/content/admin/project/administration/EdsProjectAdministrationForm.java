package com.inetpsa.eds.application.content.admin.project.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditStringWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectNode;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.doublelist.DoubleList;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide project administrator form
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectAdministrationForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsProjectAdministrationForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        // Check that all projects have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        int i = 0;
        for (Object o : projects) {
            differentNameCount.add(((EdsProject) o).getPName());
            i++;
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {

            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-proj-ss-pop-error"), Notification.TYPE_ERROR_MESSAGE);
        }
        return isValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        milestones.clear();

        milestones.addAll(EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.MILESTONE));

        for (Object inactiveProject : vDLSTprojects.getAllOptions()) {
            ((EdsProject) inactiveProject).setPIndex(EdsProject.INACTIVE);
            ((EdsProject) inactiveProject).setEdsNodeId(null);
        }
        for (Object activeWording : vDLSTprojects.getAllSelections()) {
            activateProject((EdsProject) activeWording);
        }
        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        this.projects = new ArrayList<EdsProject>(EDSdao.getInstance().getAllProjects(true));
        selectTreeNode(root);
        constructTree();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection<Object> itemsToSave = new LinkedHashSet<Object>(modifiedNodes);
        itemsToSave.addAll(projects);

        return itemsToSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection<Object> getAllItemsToDelete() {
        Collection<Object> itemsToDelete = new HashSet<Object>(deletedNodes);

        return itemsToDelete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of doubler list
     */
    private DoubleList vDLSTprojects;
    /**
     * Tree
     */
    private Tree tree;
    /**
     * Tree panel, used to force refresh
     */
    private Panel treePanel;
    /**
     * Root node (not from the database)
     */
    private EdsProjectNode root;
    private EdsProjectNode selected = null;

    /**
     * Variable to hold list of modified nodes
     */
    private ArrayList<EdsProjectNode> modifiedNodes;

    /**
     * Variable to hold list of deleted nodes
     */
    private ArrayList<EdsProjectNode> deletedNodes;

    /**
     * Projects
     */
    private ArrayList<EdsProject> projects;

    /**
     * Variable to hold list of EdsWordings
     */
    private ArrayList<EdsWording> milestones;

    /**
     * Initialize EdsProjectAdministrationForm
     */
    private void init() {
        this.root = new EdsProjectNode(null, null, controller.getBundle().getString("Admin-proj-ss-tree-root"));
        this.modifiedNodes = new ArrayList<EdsProjectNode>();
        this.deletedNodes = new ArrayList<EdsProjectNode>();
        this.milestones = new ArrayList<EdsWording>();
        this.projects = new ArrayList<EdsProject>(EDSdao.getInstance().getAllProjects(true));
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getProjectLayout(), controller.getBundle().getString("Admin-proj-ss-title-adm"));

        addComponent(vTSmain);
    }

    /**
     * Remove recursively all the parent's children, and the parent, from the tree
     * 
     * @param parent The parent
     */
    private void removeTreeChildren(EdsProjectNode parent) {
        if (tree.hasChildren(parent)) {
            Collection<Object> nodes = new ArrayList<Object>(tree.getChildren(parent));
            for (Object node : nodes) {
                removeTreeChildren((EdsProjectNode) node);
            }
        }

        if (!parent.equals(root)) { // Remove everything but parent
            tree.removeItem(parent);

            for (EdsProject project : projects) {
                if (project.isActive() && parent.getEdspnId().equals(project.getEdsNodeId())) {
                    project.setEdsNodeId(null);
                    project.setPIndex(0);
                }
            }

            deletedNodes.add(parent);
        }
    }

    /**
     * Construct project node tree
     * 
     * @param parent The parent (null for root level)
     */
    private void constructTree(EdsProjectNode parent) {
        for (EdsProjectNode node : EDSdao.getInstance().getProjectNodeChild(parent)) {
            tree.addItem(node);
            if (parent != null)
                tree.setParent(node, parent);
            else
                tree.setParent(node, root);
            constructTree(node);
        }
    }

    /**
     * Init the tree and its nodes
     */
    private void constructTree() {
        if (tree == null)
            tree = new Tree();
        else
            tree.removeAllItems();

        // Construct tree
        tree.addItem(root);
        constructTree(null);
        tree.expandItemsRecursively(root);
    }

    /**
     * Edit a tree node and change its name, and reload the tree
     * 
     * @param node The node to edit
     * @param newName The new name
     */
    private void editTreeNode(EdsProjectNode node, String newName) {
        node.setEdspnName(newName);

        modifiedNodes.add(node);

        tree.select(node);
        tree.focus();
    }

    /**
     * Select a node in the tree
     * 
     * @param node The selected node, or null
     */
    private void selectTreeNode(EdsProjectNode node) {
        if (node != null && node.equals(root))
            node = null;

        this.selected = node;

        vDLSTprojects.removeAllItems();

        for (EdsProject project : projects) {
            if (!project.isActive()) {
                vDLSTprojects.addOption(project);
            } else if ((node == null && project.getEdsNodeId() == null)
                    || (node != null && project.getEdsNodeId() != null && project.getEdsNodeId().equals(node.getEdspnId()))) {
                vDLSTprojects.addSelection(project);
            }
            vDLSTprojects.setItemCaption(project, project.getPName());
        }

    }

    /**
     * Add a node to the tree
     * 
     * @param parent The node's parent
     */
    private void addTreeNode(EdsProjectNode parent) {
        // Create the new node
        EdsProjectNode newNode = new EdsProjectNode(UUID.randomUUID().toString(), parent.getEdspnId(), controller.getBundle().getString(
                "Admin-proj-ss-tree-new-name"));

        modifiedNodes.add(newNode);

        // Add the node
        tree.addItem(newNode);
        tree.setParent(newNode, parent);
    }

    /**
     * This method provide layout for Project administration
     * 
     * @return Layout for Project administrations
     */
    private Layout getProjectLayout() {
        HorizontalLayout projectLayout = new HorizontalLayout();
        projectLayout.setSpacing(true);
        projectLayout.setMargin(true);

        treePanel = new Panel();
        treePanel.setWidth("250px");
        treePanel.setHeight("100%");

        // Construct tree
        constructTree();
        tree.setSelectable(true);
        tree.setImmediate(true);
        tree.select(root);
        tree.setNullSelectionAllowed(false);

        // Rename event
        this.tree.addListener(new ItemClickListener() {
            private static final long serialVersionUID = 1L;
            private long lastClick = System.currentTimeMillis();

            @Override
            public void itemClick(ItemClickEvent event) {
                long estimatedTime = System.currentTimeMillis() - lastClick;

                if (estimatedTime < 100) {
                    final EdsProjectNode node = (EdsProjectNode) tree.getValue();
                    if (node != null && !node.equals(root)) {
                        EditStringWindow window = new EditStringWindow(node.getEdspnName(), controller.getBundle().getString("Admin-proj-ss-rename"),
                                controller.getBundle().getString("Admin-proj-ss-new-name"), controller, new EditStringWindow.ValidateListener() {

                                    @Override
                                    public void onValidate(String newValue) {
                                        // Change value name
                                        editTreeNode(node, newValue);
                                    }
                                });
                        window.setModal(true);
                        getApplication().getMainWindow().addWindow(window);
                        window.center();
                    }
                }
                lastClick = System.currentTimeMillis();
            }

        });

        // Select event
        this.tree.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 7917609101322586904L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                commitChanges(); // Commit changes (i.e. update indexes, activated and deactivated values)
                selectTreeNode((EdsProjectNode) tree.getValue());
            }
        });

        treePanel.addComponent(this.tree);
        treePanel.setCaption(controller.getBundle().getString("Admin-proj-ss-tree"));
        projectLayout.addComponent(treePanel);

        VerticalLayout treeButtonLayout = new VerticalLayout();
        treeButtonLayout.setSpacing(true);

        // Button to add a new element to the selected node
        Button addNodeButton = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = -4171613037014243105L;

            public void buttonClick(Button.ClickEvent event) {
                EdsProjectNode parent = (EdsProjectNode) tree.getValue();
                if (parent != null) {
                    addTreeNode(parent);
                }
            }
        });
        addNodeButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addNodeButton.setStyleName(BaseTheme.BUTTON_LINK);
        addNodeButton.setDescription(controller.getBundle().getString("Admin-proj-add-tt"));
        treeButtonLayout.addComponent(addNodeButton);

        // Button to delete a selected node
        Button removeNodeButton = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = -3129539746314403711L;

            public void buttonClick(Button.ClickEvent event) {
                EdsProjectNode node = (EdsProjectNode) tree.getValue();
                if (node != null && !node.equals(root)) {
                    // Remove the node (and the sub-nodes)
                    removeTreeChildren(node);
                    tree.select(root);
                }
            }
        });
        removeNodeButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
        removeNodeButton.setStyleName(BaseTheme.BUTTON_LINK);
        removeNodeButton.setDescription(controller.getBundle().getString("Admin-proj-add-tt"));
        treeButtonLayout.addComponent(removeNodeButton);

        projectLayout.addComponent(treeButtonLayout);
        projectLayout.setComponentAlignment(treeButtonLayout, Alignment.MIDDLE_CENTER);

        // Double list
        this.vDLSTprojects = new DoubleList();
        vDLSTprojects.setLeftColumnCaption(controller.getBundle().getString("Admin-proj-inact"));
        vDLSTprojects.setRightColumnCaption(controller.getBundle().getString("Admin-proj-act"));
        vDLSTprojects.setRows(20);
        vDLSTprojects.setMultiSelect(true);
        vDLSTprojects.setNullSelectionAllowed(true);
        vDLSTprojects.setImmediate(true);
        vDLSTprojects.setWidth("500px");
        projectLayout.addComponent(vDLSTprojects);

        // Adding listener double click for option list-> Rename window opens
        vDLSTprojects.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsProject>) vDLSTprojects.getOptionValue()).isEmpty()) {
                    final EdsProject selectedProject = ((Set<EdsProject>) vDLSTprojects.getOptionValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedProject.getPName(), controller.getBundle().getString(
                            "Admin-proj-ss-rename"), controller.getBundle().getString("Admin-proj-ss-new-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedProject.setPName(newValue);
                                    vDLSTprojects.setItemCaption(selectedProject, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Adding listener double click for selected list-> Rename window opens
        vDLSTprojects.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsProject>) vDLSTprojects.getSelectedValue()).isEmpty()) {

                    final EdsProject selectedProject = ((Set<EdsProject>) vDLSTprojects.getSelectedValue()).iterator().next();
                    EditStringWindow window = new EditStringWindow(selectedProject.getPName(), controller.getBundle().getString(
                            "Admin-proj-ss-rename"), controller.getBundle().getString("Admin-proj-ss-new-name"), controller,
                            new EditStringWindow.ValidateListener() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @see com.inetpsa.eds.application.popup. EditStringWindow .ValidateListener#onValidate (java.lang.String)
                                 */
                                @Override
                                public void onValidate(String newValue) {
                                    selectedProject.setPName(newValue);
                                    vDLSTprojects.setItemCaption(selectedProject, newValue);
                                }
                            });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        VerticalLayout projectControlLayout = new VerticalLayout();
        projectControlLayout.setSpacing(true);

        // Button to bring up the list
        Button upButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Set<EdsProject> valueToMove = new HashSet<EdsProject>((Set<EdsProject>) vDLSTprojects.getSelectedValue());
                if (valueToMove.size() == 1) {
                    vDLSTprojects.moveUp(valueToMove.iterator().next());
                }
            }
        });
        upButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/up.png "));
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setDescription(controller.getBundle().getString("Admin-proj-up-proj-tt"));
        projectControlLayout.addComponent(upButton);

        // Button to scroll down the list
        Button downButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Set<EdsProject> valueToMove = new HashSet<EdsProject>((Set<EdsProject>) vDLSTprojects.getSelectedValue());
                if (valueToMove.size() == 1) {
                    vDLSTprojects.moveDown(valueToMove.iterator().next());
                }
            }
        });
        downButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/down.png "));
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setDescription(controller.getBundle().getString("Admin-proj-down-proj-tt"));
        projectControlLayout.addComponent(downButton);

        // Button to add a new project
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsProject newProject = new EdsProject(UUID.randomUUID().toString(), controller.getBundle().getString("Admin-proj-ss-new"),
                        EdsProject.INACTIVE);
                newProject.setEdsNodeId(selected == null ? null : selected.getEdspnId());
                vDLSTprojects.addOption(newProject);
                vDLSTprojects.setItemCaption(newProject, newProject.getPName());
                projects.add(newProject);
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("Admin-proj-add-tt"));
        vDLSTprojects.addButton(addButton);

        // Button to duplicate a project
        Button duplicateButton = new Button("", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                controller.queryDuplicateProject();
            }
        });
        duplicateButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/duplicate.png"));
        duplicateButton.setStyleName(BaseTheme.BUTTON_LINK);
        duplicateButton.setDescription(controller.getBundle().getString("Admin-proj-dup-tt"));
        vDLSTprojects.addButton(duplicateButton);

        projectLayout.addComponent(projectControlLayout);
        projectLayout.setComponentAlignment(projectControlLayout, Alignment.MIDDLE_LEFT);
        projectLayout.setExpandRatio(vDLSTprojects, 1);
        projectControlLayout.setSizeUndefined();

        return projectLayout;
    }

    /**
     * This method activate the specified project
     * 
     * @param edsProject Project to be activated
     */
    private void activateProject(EdsProject edsProject) {
        if (selected != null) { // Update organization node
            edsProject.setEdsNodeId(selected.getEdspnId());
        }
        edsProject.setPIndex(vDLSTprojects.indexOfItem(edsProject) + 1);
        if (edsProject.getEdsWordingByPWIdFirstStage() == null && milestones.size() > 0) {
            edsProject.setEdsWordingByPWIdFirstStage(milestones.get(0));
        }
        if (edsProject.getEdsWordingByPWIdSecondStage() == null && milestones.size() > 1) {
            edsProject.setEdsWordingByPWIdSecondStage(milestones.get(1));
        }
        if (edsProject.getEdsWordingByPWIdThirdStage() == null && milestones.size() > 2) {
            edsProject.setEdsWordingByPWIdThirdStage(milestones.get(2));
        }
        if (edsProject.getEdsWordingByPWIdFourthStage() == null && milestones.size() > 3) {
            edsProject.setEdsWordingByPWIdFourthStage(milestones.get(3));
        }
    }
}
