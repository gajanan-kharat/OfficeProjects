package com.inetpsa.eds.tools.filter.chs.connectivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Filter plug according to a name.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class ChsConnectivityFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsName Name of EDS
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public ChsConnectivityFilter(Chs chs, int filterType, EdsApplicationController controller) {
        super(controller);
        this.filterType = filterType;
        this.chs = chs;
        init();
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @param criteriaQuery The application in which to add the filter.
     * @return The modified query.
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {

        List<ChsPin> describeCav = new ArrayList<ChsPin>();
        describeCav.addAll(chs.getCavities());
        Collections.sort(describeCav);
        String hash;
        StringBuilder builder = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        MessageDigest cript;
        try {
            cript = MessageDigest.getInstance("SHA-1");
            for (ChsPin pin : describeCav) {
                builder.append(pin.getCavity());
                builder.append('_');
                builder.append(pin.getPinType());
                builder.append('#');
            }
            cript.reset();
            cript.update(builder.toString().getBytes());
            byte[] byteData = cript.digest();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
            criteriaQuery.add(name.eq(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return criteriaQuery;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        switch (filterType) {
        case EdsFilterManager.FILTER_EQUALS:
            return MessageFormat.format(controller.getBundle().getString("chs-filter-embnum-equal"), chs.getPartNumber());
        default:
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + getClass().getName().hashCode();
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return describe();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of property name
     */
    private static final Property name = Property.forName("describeCav");
    /**
     * Variable to hold value of type of filter
     */
    private int filterType;
    /**
     * Variable to hold value of name of EDS
     */
    private Chs chs;

    /**
     * Initialize filter for name
     */
    private void init() {
    }
}
