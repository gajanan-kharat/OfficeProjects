package com.inetpsa.eds.dao.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.inetpsa.bsxmanager.BSXBoxController;
import com.inetpsa.common.distrib.model.Board;
import com.inetpsa.common.distrib.model.box.Box;
import com.inetpsa.common.distrib.model.box.Pin;
import com.inetpsa.gestion.sync.model.BSXNodeFS;

@XmlType
public class EdsBsx implements Serializable {

    /**
     * String Variable to hold the value for Column BSX_PIN_IN of Table OPLQTBSX
     */
    private Set<EdsBsxPin> pinIn = new HashSet<EdsBsxPin>(0);

    /**
     * String Variable to hold the value for Column BSX_PIN_OUT of Table OPLQTBSX
     */
    private Set<EdsBsxPin> pinOut = new HashSet<EdsBsxPin>(0);

    /**
     * String Variable to hold the value for Column BSX_U_ID of Table OPLQTUSE. This is the real unique table ID.
     */
    private String uId;

    /**
     * String Variable to hold the value for Column BSX_ID of Table OPLQTBSX. This is the imported BSX ID.
     */
    private String id;

    /**
     * String Variable to hold the value for Column BSX_NAME of Table OPLQTBSX
     */
    private String name;

    /**
     * String Variable to hold the value for Column BSX_PATH of Table OPLQTBSX
     */
    private String path;

    /**
     * String Variable to hold the value for Column BSX_COMMENT of Table OPLQTBSX
     */
    private String comment;

    private EdsEds eds;

    /**
     * Default Constructor
     */
    public EdsBsx() {
    }

    /**
     * Parameterized Constructor
     */
    public EdsBsx(EdsBsx bsx) {
        this(bsx.uId, bsx.id, bsx.name, bsx.path, bsx.comment, bsx.pinIn, bsx.pinOut);
    }

    /**
     * Parameterized Constructor
     */
    public EdsBsx(String uId, String id, String name, String path, String comment, Set<EdsBsxPin> pinIn, Set<EdsBsxPin> pinOut) {
        this.uId = uId;
        this.id = id;
        this.name = name;
        this.path = path;
        this.comment = comment;
        this.pinIn = pinIn;
        this.pinOut = pinOut;
    }

    // GL code addition start for CUG-0225
    /**
     * @param bsxBoxController
     * @param bsxNode
     */
    public EdsBsx(BSXBoxController bsxBoxController, BSXNodeFS bsxNode) {
        String partNumber = "";

        this.uId = UUID.randomUUID().toString();
        this.id = bsxNode.getParent().getID();
        this.name = bsxNode.getParent().getName();
        // this.path = getPartnumber() + "/" + getLoadname();
        if (bsxNode.getParent() != null && bsxNode.getParent().getParent() != null) {
            partNumber = bsxNode.getParent().getParent().getName();
        }
        String loadName = bsxNode.getParent().getName();
        this.path = partNumber + "/" + loadName;
        this.comment = "";

        for (Board board : bsxBoxController.getEtudeDistrib().getAllBoards()) {
            board.getDrawingTable().postLoad();
            for (Box box : board.getAllBoxes()) {
                if (bsxNode.getID().equals(box.getID())) {
                    populatePinsForEDSBSx(box);
                }
            }
        }
    }

    /**
     * Populate the EDSbox with the cavities from the selected box
     * 
     * @param box
     */
    private void populatePinsForEDSBSx(Box box) {
        for (Pin pin : box.getAllPins()) {
            if (pin.isPinTypeIn()) {
                addPinIn(new EdsBsxPin(UUID.randomUUID().toString(), pin.getName()));
            } else if (pin.isPinTypeOut()) {
                addPinOut(new EdsBsxPin(UUID.randomUUID().toString(), pin.getName()));
            }
        }
    }

    // GL code addition ends for CUG-0225

    @XmlElement(name = "pinsIn")
    public Set<EdsBsxPin> getPinIn() {
        return pinIn;
    }

    public void setPinIn(Set<EdsBsxPin> pinIn) {
        this.pinIn = pinIn;
    }

    public void addPinIn(EdsBsxPin pin) {
        if (pin == null)
            return;

        pin.setType("IN");
        pin.setEdsBsx(this);
        pinIn.add(pin);
    }

    @XmlElement(name = "pinsOut")
    public Set<EdsBsxPin> getPinOut() {
        return pinOut;
    }

    public void setPinOut(Set<EdsBsxPin> pinOut) {
        this.pinOut = pinOut;
    }

    public void addPinOut(EdsBsxPin pin) {
        if (pin == null)
            return;

        pin.setType("OUT");
        pin.setEdsBsx(this);
        pinOut.add(pin);
    }

    public void setPin(Set<EdsBsxPin> pin) {
        for (EdsBsxPin p : pin) {
            addPin(p);
        }
    }

    public Set<EdsBsxPin> getPin() {
        HashSet<EdsBsxPin> pin = new HashSet<EdsBsxPin>();
        pin.addAll(getPinIn());
        pin.addAll(getPinOut());

        return pin;
    }

    public void addPin(EdsBsxPin pin) {
        if (pin == null)
            return;

        if ("OUT".equals(pin.getType()))
            addPinOut(pin);
        else
            addPinIn(pin);
    }

    @XmlTransient
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "bsx-uid")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @XmlTransient
    public EdsEds getEdsEds() {
        return eds;
    }

    public void setEdsEds(EdsEds eds) {
        this.eds = eds;
    }

    @XmlElement(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? "" : comment;
    }

    public EdsBsx duplicate(EdsEds eds) {
        EdsBsx clone = new EdsBsx();

        clone.setComment(comment);
        clone.setEdsEds(eds);
        clone.setName(name);
        clone.setPath(path);
        clone.setId(id);
        clone.setuId(UUID.randomUUID().toString());
        Set<EdsBsxPin> clonePin = new HashSet<EdsBsxPin>();
        EdsBsxPin item;
        for (EdsBsxPin pin : getPin()) {
            if (pin != null) {
                item = new EdsBsxPin();
                item.setEdsBsx(clone);
                item.setName(pin.getName());
                item.setType(pin.getType());
                item.setuId(UUID.randomUUID().toString());
                clonePin.add(item);
            }
        }
        clone.setPin(clonePin);

        return clone;
    }
}
