/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.inetpsa.eds.dao.model;

/**
 * @author Alter SOLUTIONS - Rabah OULD TAHAR - e360527 <rabah.ouldtahar@ext.mpsa.com>
 */
public class EdsRoleType {
    // PUBLIC
    public EdsRoleType() {

    }

    public EdsRoleType(String roId, String name, int type) {
        this.roId = roId;
        this.name = name;
        this.type = type;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    // PROTECTED

    // PRIVATE
    private String roId;
    private String name;
    private int type;

}
