/**
 * Controller of the 'Connectivity' nodes, with the objective to concentrate the data under a single authority 
 * Creation : 19/08/2015
 * @author Joao Costa @ Alter Frame
 */
package com.inetpsa.eds.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.inetpsa.eds.dao.CHSdao;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsPinTypeComment;
import com.inetpsa.eds.tools.chs.observer.EdsChsChangeType;
import com.inetpsa.eds.tools.chs.observer.Observable;

public class ChsController extends Observable<EdsChsChangeType> {

    public static final String[] pinTypes = new String[] { "PWR", "GND", "IN", "OUT", "IxO" };

    private EdsEds eds;

    private Set<ChsPin> selectedChs;

    private List<Chs> filteredChs;

    private Chs lastConnected;

    private EdsApplicationController controller;

    public ChsController(EdsEds eds, EdsApplicationController controller) {
        this.eds = eds;
        this.controller = controller;
    }

    public EdsEds getEds() {
        return eds;
    }

    public Set<Chs> getConnectedChs() {
        return eds.getEdsChs();
    }

    public void addConnectedChs(Chs chs) {
        Set<Chs> current = eds.getEdsChs();
        boolean added = current.add(chs);
        if (added) {
            lastConnected = chs;
            eds.setEdsChs(current);

            this.setChanged();
            this.notifyObservers(EdsChsChangeType.CONNECTIVITY);
            if (current.size() == 1) {
                createComments();
                this.setChanged();
                this.notifyObservers(EdsChsChangeType.CONNECTIVITY_FILTER);
            }
            EDSdao.getInstance().mergeDetachedDBObject(eds);
            eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());
        }

    }

    private void createComments() {
        Set<EdsPinTypeComment> typeComment = new HashSet<EdsPinTypeComment>();
        Set<EdsPinConnect> pinComment = new HashSet<EdsPinConnect>();
        EdsPinTypeComment type;
        for (String pType : pinTypes) {
            type = new EdsPinTypeComment();
            type.setEds(eds);
            type.setType(pType);
            typeComment.add(type);
        }
        EdsPinConnect pin;
        for (ChsPin cav : lastConnected.getCavities()) {
            pin = new EdsPinConnect();
            pin.setEds(eds);
            pin.setCavity(cav.getCavity());
            pin.setPinType(cav.getPinType());
            pinComment.add(pin);
        }
        eds.setEdsTypeComment(typeComment);
        eds.setEdsPinConnect(pinComment);
    }

    public Chs getLastConnectedChs() {
        return lastConnected;
    }

    public void removeConnectedChs(Chs chs) {
        Set<Chs> current = eds.getEdsChs();
        boolean removed = current.remove(chs);
        if (removed) {
            eds.setEdsChs(current);

            if (current.size() == 0) {
                eds.getEdsTypeComment().clear();
                eds.getEdsPinConnect().clear();

                if (eds.getEdsBsxUnique() != null) {
                    for (EdsBsxPin pin : eds.getEdsBsxUnique().getPin()) {
                        EDSdao.getInstance().deleteDetachedDBObject(pin);
                    }
                    EDSdao.getInstance().deleteDetachedDBObject(eds.getEdsBsxUnique());
                    eds.setEdsBsxUnique(null);
                }

                this.setChanged();
                this.notifyObservers(EdsChsChangeType.CONNECTIVITY_FILTER);
            }
            EDSdao.getInstance().mergeDetachedDBObject(eds);
            eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());
        }

    }

    public List<Chs> getFilterChs() {
        if (filteredChs == null) {
            filteredChs = CHSdao.getInstance().getAllChs();
        }
        return filteredChs;
    }

    public void setFilterChs(DetachedCriteria filters) {
        filteredChs = CHSdao.getInstance().executeEdsCriteria(filters);
        this.setChanged();
        this.notifyObservers(EdsChsChangeType.FILTER);
    }

    public List<Chs> getAllChs() {
        return CHSdao.getInstance().getAllChs();
    }

    public Set<ChsPin> getSelectedPins() {
        if (selectedChs == null) {
            selectedChs = new HashSet<ChsPin>();
        }
        return selectedChs;
    }

    public void setSelectedChs(Set<ChsPin> selectedChs) {
        this.selectedChs = selectedChs;
        this.setChanged();
        this.notifyObservers(EdsChsChangeType.SELECTED);
    }

    public void init() {
        this.setChanged();
        this.notifyObservers(EdsChsChangeType.INIT);
    }

}
