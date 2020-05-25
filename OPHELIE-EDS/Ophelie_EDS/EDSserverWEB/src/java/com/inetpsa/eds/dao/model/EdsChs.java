package com.inetpsa.eds.dao.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.inetpsa.eds.dao.I_FormData;

public class EdsChs implements Serializable, I_FormData {

    private String edsId;
    private String componentId;

    public EdsChs() {

    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getEdsId() {
        return edsId;
    }

    public void setEdsId(String edsId) {
        this.edsId = edsId;
    }

    @Override
    public I_FormData getCopy(EdsEds eds, HashMap<String, Object> copiesMap) {
        EdsChs copy = new EdsChs();
        copy.setComponentId(componentId);
        copy.setEdsId(eds.getEdsId());
        return copy;
    }

    @Override
    public List<Object> getAllItemsToSave() {
        return Collections.singletonList((Object) this);
    }

}
