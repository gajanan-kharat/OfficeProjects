/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciTransferModelType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;
import com.rits.cloning.Cloner;

/**
 * The Class SciObjectInputAdapter.
 * 
 * @author U224106
 */
public class SciObjectInputAdapter {

    /**
     * Creates the Scilab requirement type.
     * 
     * @param requirement the requirement
     * @return the Scilab requirement type
     */
    public static SciRequirementType createScilabRequirementType(RequirementType requirement) {
        try {
            return new SciRequirementType(requirement);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the Scilab requirement list.
     * 
     * @param reqList the requirement list
     * @return the list
     */
    public static List<SciRequirementType> createScilabRequirementList(List<RequirementType> reqList) {
        Set<SciRequirementType> scilabReqSet = new HashSet<SciRequirementType>();
        Iterator<RequirementType> it = reqList.iterator();
        while (it.hasNext())
            scilabReqSet.add(createScilabRequirementType(it.next()));

        return new LinkedList<SciRequirementType>(scilabReqSet);
    }

    /**
     * Creates the Scilab contributor list from mother requirements.
     * 
     * @param motherList the Mother requirement list
     * @return the list
     */
    public static List<SciRequirementType> createScilabRequirementListFromMotherRequirements(List<SciMotherRequirementType> motherList) {
        Set<SciRequirementType> allContributors = new HashSet<SciRequirementType>();
        for (Iterator<SciMotherRequirementType> iterator = motherList.iterator(); iterator.hasNext();) {
            SciMotherRequirementType motherRequirement = iterator.next();
            allContributors.addAll(motherRequirement.getContributorList());
        }

        return new LinkedList<SciRequirementType>(allContributors);
    }

    /**
     * Creates the Scilab Mother requirement type.
     * 
     * @param pRequirement the original Mother requirement
     * @param pTransferModel the transfer model
     * @param pContributorList the contributor list
     * @return the Scilab Mother requirement type
     */
    public static SciMotherRequirementType createScilabMotherRequirementType(RequirementType pRequirement, TransferModelType pTransferModel,
            List<RequirementType> pContributorList) {
        try {
            return new SciMotherRequirementType(pRequirement, new SciTransferModelType(pTransferModel), createScilabRequirementList(pContributorList));
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generates the Scilab object by cloning original object.
     * 
     * @param plmObject the original PLM object
     * @param scilabObject the Scilab object to complete
     * @param classBean the class bean of the original object
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static void generateScilabObject(Object plmObject, Object scilabObject, Class<?> classBean) throws SecurityException,
            IllegalArgumentException, IllegalAccessException {

        Field[] fields = classBean.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (!fields[i].isSynthetic()) {
                Object fieldType = fields[i].getType();
                if (fieldType instanceof Collection) {
                    Cloner cloner = new Cloner();
                    fields[i].set(scilabObject, cloner.deepClone(fields[i].get(plmObject)));
                } else
                    fields[i].set(scilabObject, fields[i].get(plmObject));
            }
        }

    }
}
