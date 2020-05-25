/*
 * Creation : 26 mai 2015
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

/**
 * This Interface is implemented by the views of table data where tensions are used.
 * 
 * @author Idir MEZIANI
 */
public interface I_Tensions {

    // Default values of the tensions.
    String U_MIN_BT = "Umin";
    String U_MOY12_5_BT = "Umoy";
    String U_MOY13_5_BT = "Umoy";
    String U_MAX_BT = "Umax";
    String U_MIN_TBT = "10.5V";
    String U_MOY12_5_TBT = "12.5V";
    String U_MOY13_5_TBT = "13.5V";
    String U_MAX_TBT = "15.2V";

    /**
     * @param uMin
     * @return The string value of Umin
     */
    public String getUmin(Float uMin);

    /**
     * @param uMoy12_5
     * @return The String value of Umoy
     */
    public String getUmoy12_5(Float uMoy12_5);

    /**
     * @param uMoy13_5
     * @return String value of Umoy
     */
    public String getUmoy13_5(Float uMoy13_5);

    /**
     * @param uMax
     * @return String value of Umax
     */
    public String getUmax(Float uMax);

}
