/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.utils;

/**
 * The Enum ArithStatReportLabelsEnum.
 * 
 * @author U224106
 */
public enum ArithStatReportLabelsEnum {

    /** The language. */
    LANGUAGE("Langue", "Language"),
    /** The date. */
    DATE("Date", "Date"),
    /** The title. */
    TITLE("Résultats de l'activité Arithmétique et Statistique", "Results of Worst case and Statistical simulation"),
    /** The project. */
    PROJECT("Projet", "Project"),
    /** The results. */
    RESULTS("Résultats numériques", "Numerical results"),
    /** The free play. */
    FREE_PLAY("Jeu", "Free play"),
    /** The vector. */
    VECTOR("Vecteur", "Vector"),
    /** The designation. */
    DESIGNATION("Désignation", "Designation"),
    /** The nominal. */
    NOMINAL("Nominal", "Nominal value"),
    /** The off centering. */
    OFF_CENTERING("Décentrage", "Off-centering"),
    /** The ppm. */
    PPM("TNC (PPM)", "Fraction defective (PPM)"),
    /** The arithmetic. */
    ARITH("Arithmétique", "Worst case"),
    /** The statistical. */
    STAT("Statistique", "Statistical"),
    /** The lower value. */
    LOW_VALUE("Limite inférieure", "Lower limit"),
    /** The upper value. */
    UP_VALUE("Limite supérieure", "Upper limit"),
    /** The lower tolerance interval. */
    LOW_TI("IT inférieur", "Lower TI"),
    /** The upper tolerance interval. */
    UP_TI("IT supérieur", "Lower TI"),
    /** The tolerance interval. */
    TI("Intervalle de tolérance", "Tolerance interval"),
    /** The formula. */
    FORMULA("Formule", "Formula"),
    /** The contributor. */
    CONTRIBUTOR("Contributeurs", "Contributors"),
    /** The picture. */
    PICTURE("Représentation", "Picture");

    /** The French. */
    private final String french;

    /** The English. */
    private final String english;

    /**
     * Instantiates a new arithmetic statistical report labels enumeration.
     * 
     * @param fr the French value
     * @param en the English value
     */
    ArithStatReportLabelsEnum(String fr, String en) {
        french = fr;
        english = en;
    }

    /**
     * French.
     * 
     * @return the string
     */
    public String french() {
        return french;
    }

    /**
     * English.
     * 
     * @return the string
     */
    public String english() {
        return english;
    }

}
