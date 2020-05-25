/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.typeoftest;

import java.util.List;

/**
 * The ManageTypeOfTestRequestDTO Class
 * 
 * @author mehaj
 */
public class ManageTypeOfTestRequestDTO {

    /** The status representation list. */
    private List<TypeOfTestRepresentation> typeOfTestRepresentationList;

    /**
     * Getter typeOfTestRepresentationList
     * 
     * @return the typeOfTestRepresentationList
     */
    public List<TypeOfTestRepresentation> getTypeOfTestRepresentationList() {
        return typeOfTestRepresentationList;
    }

    /**
     * Setter typeOfTestRepresentationList
     * 
     * @param typeOfTestRepresentationList the typeOfTestRepresentationList to set
     */
    public void setTypeOfTestRepresentationList(List<TypeOfTestRepresentation> typeOfTestRepresentationList) {
        this.typeOfTestRepresentationList = typeOfTestRepresentationList;
    }

}
