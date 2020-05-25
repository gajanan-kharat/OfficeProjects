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
public enum SpecITReportLabelsEnum {

    /** The language. */
    LANGUAGE("Langue", "Language"),
    /** The date. */
    DATE("Date", "Date"),
    /** The title. */
    TITLE("Résultats de l'activité Spécification d'IT", "Results of TI Specification"),
    /** The tree. */
    TREE("Structure d'exigences", "Requirements tree"),
    /** The results. */
    RESULTS("Résultats numériques", "Numerical results"),
    /** The plot. */
    PLOT("Distribution de", "Distribution of"),
    /** The prestation. */
    PRESTATION("Prestation", "Customer requirement"),
    /** The designation. */
    DESIGNATION("Désignation", "Designation"),
    /** The objectives. */
    OBJECTIVES("Objectifs", "Objectives"),
    /** The optimized values. */
    OPTIMIZED_VALUES("Valeurs optimisées", "Optimized results"),
    /** The low value. */
    LOW_VALUE("Limite inférieure", "Lower limit"),
    /** The up value. */
    UP_VALUE("Limite supérieure", "Upper limit"),
    /** The Cap. */
    CAP("Cap", "Cp"),
    /** The Cpk. */
    CPK("Cpk", "Cpk"),
    /** The off centering. */
    OFF_CENTERING("Décentrage réel", "Real off-centering"),
    /** The ppm. */
    PPM("TNC (PPM)", "Fraction defective (PPM)"),
    /** The formula. */
    FORMULA("Formule", "Formula"),
    /** The residual standard deviation. */
    RESIDUAL_STD_DEV("Ecart-type résiduel", "Standard deviation"),
    /** The contributor. */
    CONTRIBUTOR("Contributeurs", "Contributors"),
    /** The distribution (KEEP THE SPACE AT THE END !!!). */
    DISTRIBUTION("Loi de distribution ", "Distribution "),
    /** The Parameter 1. */
    PARAM_1("Paramètre 1", "Parameter 1"),
    /** The Parameter 2. */
    PARAM_2("Paramètre 2", "Parameter 2"),
    /** The binomial. */
    BINOMIAL("Binomiale", "Binomial"),
    /** The exponential. */
    EXPO("Exponentielle", "Exponential"),
    /** The log-normal. */
    LOG("Log-normale", "Log-normal"),
    /** The normal (standard). */
    NORMAL_STD("Normale", "Normal"),
    /** The normal by range. */
    NORMAL_PLAGE("Normale par plage", "Normal by range"),
    /** The Poisson. */
    POISSON("Poisson", "Poisson"),
    /** The uniform. */
    UNIFORM("Uniforme", "Uniform"),
    /** The Weibull. */
    WEIBULL("Weibull", "Weibull"),
    /** The Rayleigh. */
    RAYLEIGH("Rayleigh", "Rayleigh");

    /** The french. */
    private final String french;

    /** The english. */
    private final String english;

    /**
     * Instantiates a new arith stat report labels enum.
     * 
     * @param fr the fr
     * @param en the en
     */
    SpecITReportLabelsEnum(String fr, String en) {
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
