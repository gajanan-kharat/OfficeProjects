package com.inetpsa.eds.application.content.eds.versionhistory;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This class represents the Version table on the User interface.
 * 
 * @author Geometric Ltd.
 */
public class EdsVersionTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsVersionTable(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method retrieves the EDS application controller object.
     * 
     * @return EDS application controller object.
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * Clears any data that already exist in the table and populates it with data from the parameter list.
     * 
     * @param edses Data to display in the table.
     */
    public void setEdsList(List<EdsEds> edses) {
        removeAllItems();

        Collections.sort(edses, new Comparator<EdsEds>() {
            public int compare(EdsEds o1, EdsEds o2) {
                if (o1.getEdsMajorVersion() == o2.getEdsMajorVersion()) {
                    return o1.getEdsMinorVersion() - o2.getEdsMinorVersion();
                } else {
                    return o1.getEdsMajorVersion() - o2.getEdsMajorVersion();
                }
            }
        });

        if (edses.size() == 1) {
            EdsEds currentEds = edses.get(0);
            String modifType = null;
            if (currentEds.getEdsState() == EdsEds.ABORTED_STATE) {
                modifType = controller.getBundle().getString("eds-version-fiche-abandonnee");
            } else {
                modifType = controller.getBundle().getString("hist-indic");
            }
            addItem(new Object[] { new Link(currentEds.getEdsRef(), new ExternalResource("#" + FragmentManager.formatURLFragmentForEDS(currentEds))),
                    currentEds.getVersionShort(), currentEds.getEdsCreaDate(), currentEds.getEdsUserByEdsCreaUserId(), modifType, null, null },
                    currentEds);
        } else {
            for (int i = 0; i < edses.size(); ++i) {
                EdsEds currentEds = edses.get(i);

                if (i == 0) {
                    String modifType = null;
                    if (currentEds.getEdsMajorVersion() != edses.get(i + 1).getEdsMajorVersion()) {
                        modifType = controller.getBundle().getString("hist-stade-change");
                    } else if (currentEds.getEdsMinorVersion() != edses.get(i + 1).getEdsMinorVersion()) {
                        modifType = controller.getBundle().getString("hist-figeage");
                    } else if (currentEds.getEdsState() == EdsEds.ABORTED_STATE) {
                        modifType = controller.getBundle().getString("eds-version-fiche-abandonnee");
                    } else {
                        modifType = controller.getBundle().getString("hist-inconnu");
                    }

                    addItem(new Object[] {
                            new Link(currentEds.getEdsRef(), new ExternalResource("#" + FragmentManager.formatURLFragmentForEDS(currentEds))),
                            currentEds.getVersionShort(), currentEds.getEdsCreaDate(), currentEds.getEdsUserByEdsCreaUserId(), modifType,
                            currentEds.getEdsModifDate(), currentEds.getEdsUserByEdsModifUserId() }, currentEds);
                } else if (i == edses.size() - 1) // La dernière version
                {
                    String modifType = null;
                    if (currentEds.getEdsState() == EdsEds.ABORTED_STATE) {
                        modifType = controller.getBundle().getString("eds-version-fiche-abandonnee");
                    } else {
                        modifType = controller.getBundle().getString("hist-indic");
                    }

                    addItem(new Object[] {
                            new Link(currentEds.getEdsRef(), new ExternalResource("#" + FragmentManager.formatURLFragmentForEDS(currentEds))),
                            currentEds.getVersionShort(), null, null, modifType, currentEds.getEdsModifDate(),
                            currentEds.getEdsUserByEdsModifUserId() }, currentEds);
                } else {
                    String modifType = null;
                    if (currentEds.getEdsMajorVersion() != edses.get(i + 1).getEdsMajorVersion()) {
                        modifType = controller.getBundle().getString("hist-stade-change");
                    } else if (currentEds.getEdsMinorVersion() != edses.get(i + 1).getEdsMinorVersion()) {
                        modifType = controller.getBundle().getString("hist-figeage");
                    } else {
                        modifType = controller.getBundle().getString("hist-inconnu");
                    }

                    addItem(new Object[] {
                            new Link(currentEds.getEdsRef(), new ExternalResource("#" + FragmentManager.formatURLFragmentForEDS(currentEds))),
                            currentEds.getVersionShort(), null, null, modifType, currentEds.getEdsModifDate(),
                            currentEds.getEdsUserByEdsModifUserId() }, currentEds);
                }
            }
        }
    }

    /**
     * Change the display string representation for an empty field.
     * 
     * @param nullRepresentation The string representing the empty fields.
     */
    public void setNullPropertyRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
    }

    // PROTECTED
    /**
     * Overloaded method to customize the display of the fields of a particular type.
     * 
     * @param rowId Id line in commonly treated part.
     * @param colId Identifier column in commonly treated part.
     * @param property Property of the element commonly Treaty.
     * @return Representation as a string of box fluently processed.
     */
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        if (property.getValue() == null) {
            return nullRepresentation;
        }
        if (property.getType().equals(EdsUser.class)) {
            return ((EdsUser) property.getValue()).toFullName();
        }
        if (property.getType().equals(Date.class)) {
            return df.format((Date) property.getValue());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    // PRIVATE
    // STATIC FINAL
    /**
     * Variable to store date format.
     */
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
    /**
     * Constant variable to store 'edsRef'.
     */
    private static final String PROPERTY_REF = "edsRef";
    /**
     * Constant variable to store 'edsMajorVersion'.
     */
    private static final String PROPERTY_VERSION = "edsMajorVersion";
    /**
     * Constant variable to store 'edsCreaDate'.
     */
    private static final String PROPERTY_CREATION_DATE = "edsCreaDate";
    /**
     * Constant variable to store 'edsUserByEdsCreaUserId'.
     */
    private static final String PROPERTY_CREATION_USER = "edsUserByEdsCreaUserId";
    /**
     * Constant variable to store 'edsModifDate'.
     */
    private static final String PROPERTY_MODIFICATION_DATE = "edsModifDate";
    /**
     * Constant variable to store 'edsUserByEdsModifUserId'.
     */
    private static final String PROPERTY_MODIFICATION_USER = "edsUserByEdsModifUserId";
    /**
     * Constant variable to store 'modif-type'.
     */
    private static final String PROPERTY_MODIF_TYPE = "modif-type";
    // STANDARD
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store null representation.
     */
    private String nullRepresentation;

    /**
     * Initialization method.
     */
    private void init() {
        // General settings.
        this.setSelectable(false);
        this.setPageLength(10);
        this.setNullPropertyRepresentation("-");
        this.setImmediate(true);

        // Model settings.
        addContainerProperty(PROPERTY_REF, Link.class, null);
        addContainerProperty(PROPERTY_VERSION, String.class, null);
        addContainerProperty(PROPERTY_CREATION_DATE, Date.class, null);
        addContainerProperty(PROPERTY_CREATION_USER, EdsUser.class, null);
        addContainerProperty(PROPERTY_MODIF_TYPE, String.class, null);
        addContainerProperty(PROPERTY_MODIFICATION_DATE, Date.class, null);
        addContainerProperty(PROPERTY_MODIFICATION_USER, EdsUser.class, null);

        // Renderers settings.
        // this.addGeneratedColumn( PROPERTY_REF ,
        // new Table.ColumnGenerator()
        // {
        // public Object generateCell( Table source ,
        // Object itemId ,
        // Object columnId )
        // {
        // Property prop = source.getItem( itemId ).getItemProperty( columnId );
        // if ( prop.getValue() != null )
        // {
        // return new Link( prop.getValue().toString() ,
        // new ExternalResource(
        // "#" + FragmentManager.formatURLFragmentForEDS( (EdsEds) itemId ) ) );
        // }
        // return nullRepresentation;
        // }
        // } );

        // this.setVisibleColumns( new Object[]
        // {
        // PROPERTY_REF , PROPERTY_VERSION , PROPERTY_CREATION_DATE , PROPERTY_CREATION_USER , PROPERTY_MODIF_TYPE ,
        // PROPERTY_MODIFICATION_DATE , PROPERTY_MODIFICATION_USER
        // } );
        this.setColumnHeaders(new String[] { controller.getBundle().getString("hist-ref"), controller.getBundle().getString("hist-vers"),
                controller.getBundle().getString("eds-creation-date"), controller.getBundle().getString("hist-creator"),
                controller.getBundle().getString("hist-modif-type"), controller.getBundle().getString("hist-date-vers"),
                controller.getBundle().getString("hist-init") });
    }
}
