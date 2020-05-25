package com.inetpsa.eds.application.content.admin.wording;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditWordingWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.doublelist.DoubleList;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide EdsWording management form
 * 
 * @author Geometric Ltd.
 */
public class EdsWordingManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsWordingManagementForm(EdsApplicationController controller) {
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
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        wordingsToSave.clear();

        for (DoubleList list : doubleLists.values()) {
            for (Object inactiveWording : list.getAllOptions()) {
                ((EdsWording) inactiveWording).setWIndex(EdsWording.INACTIVE);
                wordingsToSave.add((EdsWording) inactiveWording);
            }
            for (Object activeWording : list.getAllSelections()) {
                ((EdsWording) activeWording).setWIndex(list.indexOfItem(activeWording) + 1);
                wordingsToSave.add((EdsWording) activeWording);
            }
        }

        Container.Indexed container = (Container.Indexed) vLSTdefaultLinks.getContainerDataSource();
        for (EdsWording wording : (Collection<EdsWording>) container.getItemIds()) {
            wording.setWIndex(container.indexOfId(wording) + 1);
            wordingsToSave.add(wording);
        }

        wordingsToSave.addAll(wordingsDeleted);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        wordingsDeleted.clear();

        for (String wordingType : doubleLists.keySet()) {
            DoubleList list = doubleLists.get(wordingType);
            list.removeAllItems();
            for (EdsWording inactiveWording : EDSdao.getInstance().getAllInactiveWordingsByType(wordingType)) {
                list.addOption(inactiveWording);
                list.setItemCaption(inactiveWording, inactiveWording.getCaption());
            }
            for (EdsWording activeWording : EDSdao.getInstance().getAllActiveWordingsByType(wordingType)) {
                list.addSelection(activeWording);
                list.setItemCaption(activeWording, activeWording.getCaption());
            }
        }

        vLSTdefaultLinks.removeAllItems();
        for (EdsWording wording : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.DEFAULT_LINKS)) {
            vLSTdefaultLinks.addItem(wording);
            vLSTdefaultLinks.setItemCaption(wording, wording.getWValue());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return new ArrayList<Object>(wordingsToSave);
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
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of map of String and DoubleList
     */
    private HashMap<String, DoubleList> doubleLists;
    /**
     * Variable to hold value of DoubleList for power supplies
     */
    private DoubleList vDLSTalims;
    /**
     * Variable to hold value of DoubleList for Organs families
     */
    private DoubleList vDLSTorgansFamilies;
    /**
     * Variable to hold value of DoubleList for Data origins
     */
    private DoubleList vDLSTdataOrigins;
    /**
     * Variable to hold value of DoubleList for comments
     */
    private DoubleList vDLSTcomments;
    /**
     * Variable to hold value of DoubleList for sub alternateur funnction
     */
    private DoubleList vDLSTsFAlt;
    /**
     * Variable to hold value of DoubleList for sub alternateur system
     */
    private DoubleList vDLSTsSAlt;
    /**
     * Variable to hold value of DoubleList for sub batterie funnction
     */
    private DoubleList vDLSTsFBat;
    /**
     * Variable to hold value of DoubleList for sub baterie system
     */
    private DoubleList vDLSTsSBat;
    /**
     * Variable to hold value of ListSelect of default links
     */
    private ListSelect vLSTdefaultLinks;
    /**
     * Variable to hold value of List of EdsWording to save
     */
    private ArrayList<EdsWording> wordingsToSave;
    /**
     * Variable to hold value of List of EdsWording to delete
     */
    private ArrayList<EdsWording> wordingsDeleted;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsWording management form
     */
    private void init() {
        this.wordingsToSave = new ArrayList<EdsWording>();
        this.wordingsDeleted = new ArrayList<EdsWording>();
        this.doubleLists = new HashMap<String, DoubleList>();

        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        // label supplies
        this.vDLSTalims = new DoubleList();
        vDLSTalims.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-volt-inact"));
        vDLSTalims.setRightColumnCaption(controller.getBundle().getString("Admin-lib-volt-act"));
        String newAlimValue = Locale.FRENCH.getISO3Language() + ":Nouvelle alimentation;";
        newAlimValue += Locale.ENGLISH.getISO3Language() + ":New alimentation;";
        vTSmain.addTab(getWordingLayout(vDLSTalims, controller.getBundle().getString("Admin-lib-new-volt"), newAlimValue, EdsWording.ALIM),
                controller.getBundle().getString("eds-alimentation"));

        // Label family members
        this.vDLSTorgansFamilies = new DoubleList();
        vDLSTorgansFamilies.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-family-inact"));
        vDLSTorgansFamilies.setRightColumnCaption(controller.getBundle().getString("Admin-lib-family-act"));
        String newOrganFamilyValue = Locale.FRENCH.getISO3Language() + ":Nouvelle famille d'organe;";
        newOrganFamilyValue += Locale.ENGLISH.getISO3Language() + ":New organ family;";
        vTSmain.addTab(
                getWordingLayout(vDLSTorgansFamilies, controller.getBundle().getString("Admin-lib-new-rogane-family"), newOrganFamilyValue,
                        EdsWording.ORGAN_FAMILY), controller.getBundle().getString("Admin-lib-ongle-organe-family"));

        // Label data origin
        this.vDLSTdataOrigins = new DoubleList();
        vDLSTdataOrigins.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-origin-inact"));
        vDLSTdataOrigins.setRightColumnCaption(controller.getBundle().getString("Admin-lib-origin-act"));
        String newDataOriginValue = Locale.FRENCH.getISO3Language() + ":Nouvelle origine;";
        newDataOriginValue += Locale.ENGLISH.getISO3Language() + ":New origin;";
        vTSmain.addTab(
                getWordingLayout(vDLSTdataOrigins, controller.getBundle().getString("Admin-lib-new-origin-data"), newDataOriginValue,
                        EdsWording.DATA_ORIGIN), controller.getBundle().getString("Admin-lib-ongle-origin-data"));

        // Labels preset comments
        this.vDLSTcomments = new DoubleList();
        vDLSTcomments.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-volt-inact"));
        vDLSTcomments.setRightColumnCaption(controller.getBundle().getString("Admin-lib-volt-act"));
        String newCommentValue = Locale.FRENCH.getISO3Language() + ":Nouveau commentaire prédéfini;";
        newCommentValue += Locale.ENGLISH.getISO3Language() + ":New predefined comment;";
        vTSmain.addTab(
                getWordingLayout(vDLSTcomments, controller.getBundle().getString("Admin-lib-new-comment"), newCommentValue, EdsWording.COMMENT),
                controller.getBundle().getString("Admin-lib-ongle-comment"));

        // Sous Fonction ALTERNATEUR
        this.vDLSTsFAlt = new DoubleList();
        vDLSTsFAlt.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-sf-alt-inact"));
        vDLSTsFAlt.setRightColumnCaption(controller.getBundle().getString("Admin-lib-sf-alt-act"));
        String newsfALTValue = Locale.FRENCH.getISO3Language() + ":Nouvelle sous fonction alternateur;";
        newsfALTValue += Locale.ENGLISH.getISO3Language() + ":New sub alternator function;";
        vTSmain.addTab(getWordingLayout(vDLSTsFAlt, "Edit", newsfALTValue, EdsWording.SF_ALTERNANTEUR),
                controller.getBundle().getString("Admin-lib-ongle-sf-alt"));

        // Sous Fonction BATTERIE
        this.vDLSTsFBat = new DoubleList();
        vDLSTsFBat.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-sf-bat-inact"));
        vDLSTsFBat.setRightColumnCaption(controller.getBundle().getString("Admin-lib-sf-bat-act"));
        String newsfBatValue = Locale.FRENCH.getISO3Language() + ":Nouvelle sous fonction batterie;";
        newsfBatValue += Locale.ENGLISH.getISO3Language() + ":New sub batterie function;";
        vTSmain.addTab(getWordingLayout(vDLSTsFBat, "Edit", newsfBatValue, EdsWording.SF_BATTERIE),
                controller.getBundle().getString("Admin-lib-ongle-sf-bat"));

        // Sous Système ALTERNATEUR
        this.vDLSTsSAlt = new DoubleList();
        vDLSTsSAlt.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-ss-alt-inact"));
        vDLSTsSAlt.setRightColumnCaption(controller.getBundle().getString("Admin-lib-ss-alt-act"));
        String newssALTValue = Locale.FRENCH.getISO3Language() + ":Nouveau sous système alternateur;";
        newssALTValue += Locale.ENGLISH.getISO3Language() + ":New sub alternator system;";
        vTSmain.addTab(getWordingLayout(vDLSTsSAlt, "Edit", newssALTValue, EdsWording.SS_ALTERNANTEUR),
                controller.getBundle().getString("Admin-lib-ongle-ss-alt"));

        // Sous Système BATTERIE
        this.vDLSTsSBat = new DoubleList();
        vDLSTsSBat.setLeftColumnCaption(controller.getBundle().getString("Admin-lib-ss-bat-inact"));
        vDLSTsSBat.setRightColumnCaption(controller.getBundle().getString("Admin-lib-ss-bat-act"));
        String newssBatValue = Locale.FRENCH.getISO3Language() + ":Nouveau sous système batterie;";
        newssBatValue += Locale.ENGLISH.getISO3Language() + ":New sub batterie system;";
        vTSmain.addTab(getWordingLayout(vDLSTsSBat, "Edit", newssBatValue, EdsWording.SS_BATTERIE),
                controller.getBundle().getString("Admin-lib-ongle-ss-bat"));
        // Files included by default
        vTSmain.addTab(getDefaultLinksLayout(), controller.getBundle().getString("Admin-lib-ongle-file"));

        this.doubleLists.put(EdsWording.ALIM, vDLSTalims);
        this.doubleLists.put(EdsWording.ORGAN_FAMILY, vDLSTorgansFamilies);
        this.doubleLists.put(EdsWording.DATA_ORIGIN, vDLSTdataOrigins);
        this.doubleLists.put(EdsWording.COMMENT, vDLSTcomments);
        this.doubleLists.put(EdsWording.SF_ALTERNANTEUR, vDLSTsFAlt);
        this.doubleLists.put(EdsWording.SF_BATTERIE, vDLSTsFBat);
        this.doubleLists.put(EdsWording.SS_ALTERNANTEUR, vDLSTsSAlt);
        this.doubleLists.put(EdsWording.SS_BATTERIE, vDLSTsSBat);

        addComponent(vTSmain);

    }

    /**
     * This method returns layout for EdsWording management form
     * 
     * @param doubleList DoubleList
     * @param editWindowTitle Edit window title
     * @param defaultValue default value
     * @param wordingType Wording type
     * @return Layout for EdsWording management form
     */
    private Layout getWordingLayout(final DoubleList doubleList, final String editWindowTitle, final String defaultValue, final String wordingType) {
        HorizontalLayout wordingLayout = new HorizontalLayout();
        wordingLayout.setSpacing(true);
        wordingLayout.setMargin(true);

        doubleList.setRows(20);
        doubleList.setMultiSelect(true);
        doubleList.setNullSelectionAllowed(true);
        doubleList.setImmediate(true);
        doubleList.setWidth("500px");
        wordingLayout.addComponent(doubleList);
        wordingLayout.setSizeFull();

        // Adding listener double click -> Rename window opens
        doubleList.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsWording>) doubleList.getOptionValue()).isEmpty()) {
                    EdsWording selectedWording = ((Set<EdsWording>) doubleList.getOptionValue()).iterator().next();
                    EditWordingWindow window = new EditWordingWindow(selectedWording, (AbstractSelect) event.getComponent(), editWindowTitle,
                            controller);
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        doubleList.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsWording>) doubleList.getSelectedValue()).isEmpty()) {
                    EdsWording selectedWording = ((Set<EdsWording>) doubleList.getSelectedValue()).iterator().next();
                    EditWordingWindow window = new EditWordingWindow(selectedWording, (AbstractSelect) event.getComponent(), editWindowTitle,
                            controller);
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        VerticalLayout alimControlLayout = new VerticalLayout();
        alimControlLayout.setSpacing(true);

        // Button to bring up the list
        Button upButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Set<EdsWording> valueToMove = new HashSet<EdsWording>((Set<EdsWording>) doubleList.getSelectedValue());
                if (valueToMove.size() == 1) {
                    doubleList.moveUp(valueToMove.iterator().next());
                }
            }
        });
        upButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/up.png "));
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setDescription(controller.getBundle().getString("admin-lib-tooltips-3"));
        alimControlLayout.addComponent(upButton);

        // Button to scroll down the list
        Button downButton = new Button("", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Set<EdsWording> valueToMove = new HashSet<EdsWording>((Set<EdsWording>) doubleList.getSelectedValue());
                if (valueToMove.size() == 1) {
                    doubleList.moveDown(valueToMove.iterator().next());
                }
            }
        });
        downButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/down.png "));
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setDescription(controller.getBundle().getString("admin-lib-tooltips-2"));
        alimControlLayout.addComponent(downButton);

        // Button to add a inactive wording
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                EdsWording newWording = new EdsWording(UUID.randomUUID().toString(), defaultValue, wordingType, EdsWording.INACTIVE);
                doubleList.addOption(newWording);
                doubleList.setItemCaption(newWording, newWording.getCaption());
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription(controller.getBundle().getString("admin-lib-tooltips-1"));
        doubleList.addButton(addButton);

        wordingLayout.addComponent(alimControlLayout);
        wordingLayout.setComponentAlignment(alimControlLayout, Alignment.MIDDLE_LEFT);
        wordingLayout.setExpandRatio(doubleList, 1);
        alimControlLayout.setSizeUndefined();

        return wordingLayout;
    }

    /**
     * This method returns layout for Default links attached
     * 
     * @return Layout for Default links attached
     */
    private Layout getDefaultLinksLayout() {
        HorizontalLayout defaultLinksLayout = new HorizontalLayout();
        defaultLinksLayout.setSpacing(true);
        defaultLinksLayout.setMargin(true);

        vLSTdefaultLinks = new ListSelect();
        vLSTdefaultLinks.setWidth("500px");
        vLSTdefaultLinks.setNullSelectionAllowed(false);
        vLSTdefaultLinks.setNewItemsAllowed(true);
        vLSTdefaultLinks.setNewItemHandler(new AbstractSelect.NewItemHandler() {
            public void addNewItem(String newItemCaption) {
                String link = null;
                if (Character.isDigit(newItemCaption.charAt(0))) {
                    link = "http://docinfogroupe.inetpsa.com/ead/doc/ref." + newItemCaption;
                } else {
                    link = newItemCaption;
                }

                EdsWording newLink = new EdsWording(UUID.randomUUID().toString(), link, EdsWording.DEFAULT_LINKS, vLSTdefaultLinks.size() + 1);
                vLSTdefaultLinks.addItem(newLink);
                vLSTdefaultLinks.setItemCaption(newLink, link);
            }
        });

        defaultLinksLayout.addComponent(vLSTdefaultLinks);

        VerticalLayout buttonsLayout = new VerticalLayout();
        buttonsLayout.setSpacing(true);

        // Button to bring up the list
        Button upButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                EdsWording valueToMove = (EdsWording) vLSTdefaultLinks.getValue();
                if (vLSTdefaultLinks.containsId(valueToMove)) {
                    Container.Indexed container = (Indexed) vLSTdefaultLinks.getContainerDataSource();
                    int itemIndex = container.indexOfId(valueToMove);

                    if (itemIndex > 0) {
                        container.removeItem(valueToMove);
                        container.addItemAt(itemIndex - 1, valueToMove);
                    }
                }
            }
        });
        upButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/up.png "));
        upButton.setStyleName(BaseTheme.BUTTON_LINK);
        upButton.setDescription(controller.getBundle().getString("Admin-lib-file-up-lync"));
        buttonsLayout.addComponent(upButton);

        // Button to scroll down the list
        Button downButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                EdsWording valueToMove = (EdsWording) vLSTdefaultLinks.getValue();
                if (vLSTdefaultLinks.containsId(valueToMove)) {
                    Container.Indexed container = (Indexed) vLSTdefaultLinks.getContainerDataSource();
                    int itemIndex = container.indexOfId(valueToMove);
                    if (itemIndex < container.size() - 1) {
                        container.removeItem(valueToMove);
                        container.addItemAt(itemIndex + 1, valueToMove);
                    }
                }
            }
        });
        downButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/down.png "));
        downButton.setStyleName(BaseTheme.BUTTON_LINK);
        downButton.setDescription(controller.getBundle().getString("Admin-lib-file-down-lync"));
        buttonsLayout.addComponent(downButton);

        // Button to delete
        Button deleteButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (vLSTdefaultLinks.getValue() != null) {
                    EdsWording valueToDelete = (EdsWording) vLSTdefaultLinks.getValue();
                    valueToDelete.setWIndex(EdsWording.HIDDEN);
                    vLSTdefaultLinks.removeItem(valueToDelete);
                    wordingsDeleted.add(valueToDelete);
                }

            }
        });
        deleteButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/delete2.png "));
        deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
        deleteButton.setDescription(controller.getBundle().getString("Admin-lib-file-down-lync"));
        buttonsLayout.addComponent(deleteButton);

        defaultLinksLayout.addComponent(buttonsLayout);
        defaultLinksLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_LEFT);
        defaultLinksLayout.setExpandRatio(vLSTdefaultLinks, 1);
        buttonsLayout.setSizeUndefined();

        return defaultLinksLayout;
    }
}
