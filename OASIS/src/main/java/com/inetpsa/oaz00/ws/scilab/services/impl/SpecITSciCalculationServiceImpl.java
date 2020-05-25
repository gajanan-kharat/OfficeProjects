/*
 * Author: U224106
 * Creation: 21 oct. 2014
 */
package com.inetpsa.oaz00.ws.scilab.services.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javasci.SciDouble;
import javasci.SciDoubleArray;
import javasci.SciString;
import javasci.SciStringArray;
import javasci.Scilab;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;

// TODO: Auto-generated Javadoc
/**
 * Classe d'evaluations de calcul de Strom.
 * 
 * @author j624318
 */

public class SpecITSciCalculationServiceImpl extends SciConnectorAbstractService {

    /** The mother list. */
    private List<SciMotherRequirementType> motherList;

    /** The prestation name list. */
    private List<String> prestNameList;

    /** The model list. */
    private List<String> modelList;

    /** The prestation lower value list. */
    private List<Double> prestationInfValueList;

    /** The prestation upper value list. */
    private List<Double> prestationSupValueList;

    /** The prestation objective CAP list. */
    private List<Double> prestationCAPObjList;

    /** The prestation objective TNC list. */
    private List<Double> prestationTNCObjList;

    /** The prestation objective off-centering list. */
    private List<Double> prestationOffCentringObjList;

    /** The transfer model uncertainty list. */
    private List<Double> transferModelUncertaintyList;

    /** The contributor list. */
    private List<SciRequirementType> contributorList;

    /** The contributor name list. */
    private List<String> contributorNameList;

    /** The contributor it list. */
    private List<Double> contributorITList;

    /** The contributor lower value list. */
    private List<Double> contributorInfValueList;

    /** The contributor higher value list. */
    private List<Double> contributorSupValueList;

    /** The graph. */
    private String graphe;

    /** The nb of samples. */
    private double nbSamples;

    /** The Constant POINTS_NUMBER. */
    private static final double POINTS_NUMBER = 1000.0;

    /** The Constant SAMPLES_NUMBER. */
    private static final double SAMPLES_NUMBER = 1000.0;

    /** The Constant SAMPLES_NUMBER. */
    public static final double SAMPLES_NUMBER_EXCEPTIONAL = 2000.0;

    /** The Constant MAX_ITERATION_NUMBER. */
    private static final double MAX_ITERATION_NUMBER = 50.0;

    /**
     * Instantiates a new TI Specification Scilab calculation service implementation.
     * 
     * @param pMotherList the Mother requirement list
     * @param sGraphe the graph path
     */
    public SpecITSciCalculationServiceImpl(List<SciMotherRequirementType> pMotherList, List<SciRequirementType> pContributorList, String sGraphe) {

        this.motherList = pMotherList;
        this.graphe = sGraphe;
        this.contributorList = pContributorList;
        this.nbSamples = SAMPLES_NUMBER;
    }

    private void init() {
        prestNameList = new LinkedList<String>();
        modelList = new LinkedList<String>();
        prestationInfValueList = new LinkedList<Double>();
        prestationSupValueList = new LinkedList<Double>();
        prestationCAPObjList = new LinkedList<Double>();
        prestationTNCObjList = new LinkedList<Double>();
        prestationOffCentringObjList = new LinkedList<Double>();
        transferModelUncertaintyList = new LinkedList<Double>();
        contributorNameList = new LinkedList<String>();
        contributorITList = new LinkedList<Double>();
        contributorInfValueList = new LinkedList<Double>();
        contributorSupValueList = new LinkedList<Double>();

    }

    /**
     * Send to SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#sendToScilab()
     */
    public void sendToScilab() {
        init();

        Scilab.Exec("clear(''graphe'',''nPts'',''nTirs'',''nbIterationsMax'',''incertitudeList'',"
                + "''ITcentre'',''defParList'',''ITmini'',''INFpar'',''SUPpar'',''fonctionsObjPenalite'',''prestList'',"
                + "''modelList'',''ParList'',''echelle'',''poids'',''TNCObj'',''CAPObj'',''DecSigmaObj'',''INFprest'',''SUPprest'',''adage'',"
                + "''tranfoXY'',''Tkj'',''T0j'',''Tj'',''T0'',''descriptList'')");

        // Scilab objects initialization
        new SciString("graphe", this.graphe);
        new SciDouble("nPts", POINTS_NUMBER);
        new SciDouble("nTirs", nbSamples);
        new SciDouble("nbIterationsMax", MAX_ITERATION_NUMBER);
        scilabDoubleArray("echelle", getArrayOfOnes(motherList.size()));
        scilabDoubleArray("poids", getArrayOfOnes(motherList.size()));

        // Scilab BrainStorm variables
        new SciDoubleArray("tranfoXY", 0, 0);
        new SciDoubleArray("Tkj", 0, 0);
        new SciDoubleArray("T0j", 0, 0);
        new SciDoubleArray("Tj", 0, 0);
        new SciDoubleArray("T0", 0, 0);

        Scilab.Exec("ITcentre=%F");
        Scilab.Exec("adage=%F");

        // Generation of all lists
        generateLists();

        // Prerequisite lists initialization
        initializePrerequisiteLists();

        // Contributors lists initialization
        initializeContributorLists();

        // Prestations lists initialization
        initializePrestationLists();

        // Prestations' objectives initialization
        initializeObjectiveLists();

    }

    /**
     * Generate lists.
     */
    private void generateLists() {
        for (Iterator<SciMotherRequirementType> it = motherList.iterator(); it.hasNext();) {
            SciMotherRequirementType motherRequirement = it.next();
            completePrestationLists(motherRequirement);

        }
        String contributors = "";
        for (Iterator<SciRequirementType> iterator = contributorList.iterator(); iterator.hasNext();) {
            SciRequirementType contributor = iterator.next();
            completeContributorLists(contributor);

            Scilab.Exec("clear(''" + contributor.getCalculationName() + "'')");

            Scilab.Exec(contributor.getScilabMCDistribution());

            if (iterator.hasNext())
                contributors = contributors + contributor.getCalculationName() + ",";
            else
                contributors = contributors + contributor.getCalculationName();
        }
        Scilab.Exec("varTmpJava=''defParList=list(" + contributors + ")''");

        Scilab.Exec("execstr(varTmpJava);clear(''varTmpJava'')");
    }

    /**
     * Complete prestation lists.
     * 
     * @param motherRequirement the mother requirement
     */
    private void completePrestationLists(SciMotherRequirementType motherRequirement) {

        // Complete prestNameList with motherRequirement calculation name
        prestNameList.add(motherRequirement.getCalculationName());
        // Complete prestationInfValueList with motherRequirement Lower value
        prestationInfValueList.add(motherRequirement.getValueInf());
        // Complete prestationSupValueList with motherRequirement Upper value
        prestationSupValueList.add(motherRequirement.getValueSup());
        // Complete prestationTNCObjList with motherRequirement TNC
        Double d = motherRequirement.getTnc();
        if (d == null)
            d = new Double("NaN");
        else
            d = new Double(d.doubleValue() / 10000);
        prestationTNCObjList.add(d);
        // Complete prestationCAPObjList with motherRequirement CAP
        d = motherRequirement.getCap();
        if (d == null)
            d = new Double("NaN");
        prestationCAPObjList.add(d);
        // Complete prestationOffCentringObjList with motherRequirement
        // centering
        d = motherRequirement.getCenteringMax();
        if (d == null)
            d = new Double("NaN");
        prestationOffCentringObjList.add(d);

        if (motherRequirement.getTransferModel() != null) {
            // Complete modelList with calculation formula
            modelList.add(motherRequirement.getTransferModel().getCalculationFormula());
            // Complete transferModelUncertaintyList with TM uncertainty
            d = motherRequirement.getTransferModel().getUncertainty();
            if (d == null)
                d = new Double("NaN");
            transferModelUncertaintyList.add(d);
        }

    }

    /**
     * Complete contributor lists.
     * 
     * @param requirement the requirement
     */
    private void completeContributorLists(SciRequirementType requirement) {
        // Complete contributorNameList with contributor object
        // parameter
        contributorNameList.add(requirement.getCalculationName());
        // Complete contributorInfValueList with contributor object
        // parameter
        contributorInfValueList.add(requirement.getValueInf());
        // Complete contributorSupValuesList with contributor object
        // parameter
        contributorSupValueList.add(requirement.getValueSup());
        // Complete contributorITList with contributor object
        // parameter
        contributorITList.add(new Double(0.0));
        // contributorITList.add(new Double(requirement.getValueSup()
        // .doubleValue() - requirement.getValueInf().doubleValue()));
    }

    /**
     * Initialize contributor lists.
     */
    private void initializeContributorLists() {
        scilabDoubleArray("ITmini", contributorITList.toArray(new Double[contributorITList.size()]));

        scilabDoubleArray("INFpar", contributorInfValueList.toArray(new Double[contributorInfValueList.size()]));

        scilabDoubleArray("SUPpar", contributorSupValueList.toArray(new Double[contributorSupValueList.size()]));

        scilabStringArray("ParList", contributorNameList);
    }

    /**
     * Initialize prestation lists.
     */
    private void initializePrestationLists() {
        scilabStringArray("prestList", prestNameList);
        scilabStringArray("modelList", modelList);
        scilabDoubleArray("INFprest", prestationInfValueList.toArray(new Double[prestationInfValueList.size()]));
        scilabDoubleArray("SUPprest", prestationSupValueList.toArray(new Double[prestationSupValueList.size()]));
        Scilab.Exec("fonctionsObjPenalite=tlist([''fonctionsObjPenalite'',''NamePrest'',''ModelePrest'',"
                + "''toleranceINF'',''toleranceSUP'',''incertitude''],prestList,modelList,INFprest,SUPprest,incertitudeList)");
    }

    /**
     * Initialize objective lists.
     */
    private void initializeObjectiveLists() {
        scilabDoubleArray("TNCObj", prestationTNCObjList.toArray(new Double[prestationTNCObjList.size()]));
        scilabDoubleArray("CAPObj", prestationCAPObjList.toArray(new Double[prestationCAPObjList.size()]));
        scilabDoubleArray("DecSigmaObj", prestationOffCentringObjList.toArray(new Double[prestationOffCentringObjList.size()]));
    }

    /**
     * Initialize prerequisite lists.
     */
    private void initializePrerequisiteLists() {
        // Model uncertainty initialization
        scilabDoubleArray("incertitudeList", transferModelUncertaintyList.toArray(new Double[transferModelUncertaintyList.size()]));

        // Calculation description initialization
        scilabStringArray("descriptList", new String[] { "" });
    }

    /**
     * Compute in SCILAB. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#computeInScilab()
     */
    public void computeInScilab() {
        this.sendToScilab();
        Scilab.Exec("clear(''rapportOpt'',''rapportOptNames'',''rapportPar'',''rapportParNames'',''erreur'')");

        String SciCmd = "[rapportOpt, rapportOptNames, rapportPar, rapportParNames]=specificationIT(nPts,nTirs,nbIterationsMax,defParList,"
                + "ITmini,INFpar,SUPpar,fonctionsObjPenalite,poids,echelle,INFprest,SUPprest,TNCObj,CAPObj,DecSigmaObj,ITcentre,graphe,adage,tranfoXY,Tkj,T0j,Tj,T0,descriptList)";
        Scilab.Exec(SciCmd);
    }

    /**
     * Gets the array of ones.
     * 
     * @param size the size
     * @return the array of ones
     */
    private Double[] getArrayOfOnes(int size) {
        Double[] d = new Double[size];
        Arrays.fill(d, new Double(1.0));
        return d;
    }

    /**
     * Gets the TI Specification prestations results.
     * 
     * @return the TI Specification prestations results
     */
    public double[] getSpecITPrestationsResults() {
        SciDoubleArray SciSize = new SciDoubleArray("stormSizeRapport", 2, 1);
        Scilab.Exec("stormSizeRapport=size(rapportOpt)");
        double[] tSize = SciSize.getData();
        SciDoubleArray SciRapportOpt = new SciDoubleArray("stormRapportOpt", (int) tSize[0], (int) tSize[1]);
        Scilab.Exec("stormRapportOpt=rapportOpt");
        double[] ret = SciRapportOpt.getData();
        Scilab.Exec("clear(''stormSizeRapport'',''stormRapportOpt'')");
        return ret;
    }

    /**
     * Gets the TI Specification contributors results.
     * 
     * @return the TI Specification contributors results
     */
    public double[] getSpecITContributorsResults() {
        SciDoubleArray SciSize = new SciDoubleArray("stormSizeRapport", 2, 1);
        Scilab.Exec("stormSizeRapport=size(rapportPar)");
        double[] tSize = SciSize.getData();
        SciDoubleArray SciRapportPar = new SciDoubleArray("stormRapportPar", (int) tSize[0], (int) tSize[1]);
        Scilab.Exec("stormRapportPar=rapportPar");
        double[] ret = SciRapportPar.getData();
        Scilab.Exec("clear(''stormSizeRapport'',''stormRapportPar'')");
        return ret;
    }

    /**
     * Gets the TI Specification prestations names.
     * 
     * @return the TI Specification prestations names
     */
    public String[] getSpecITPrestationsNames() {
        SciDoubleArray SciSize = new SciDoubleArray("stormSizeRapport", 2, 1);
        Scilab.Exec("stormSizeRapport=size(rapportOptNames)");
        double[] tSize = SciSize.getData();
        SciStringArray SciResults = new SciStringArray("StormFunctionNames", (int) tSize[0], (int) tSize[1]);
        Scilab.Exec("StormFunctionNames=rapportOptNames");
        String[] ret = SciResults.getData();
        Scilab.Exec("clear(''stormSizeRapport'',''StormFunctionNames'')");
        return ret;
    }

    /**
     * Gets the TI Specification contributors names.
     * 
     * @return the TI Specification contributors names
     */
    public String[] getSpecITContributorsNames() {
        SciDoubleArray SciSize = new SciDoubleArray("stormSizeRapport", 2, 1);
        Scilab.Exec("stormSizeRapport=size(rapportParNames)");
        double[] tSize = SciSize.getData();
        SciStringArray SciResults = new SciStringArray("StormParameterNames", (int) tSize[0], (int) tSize[1]);
        Scilab.Exec("StormParameterNames=rapportParNames");
        String[] ret = SciResults.getData();
        Scilab.Exec("clear(''stormSizeRapport'',''StormParameterNames'')");
        return ret;
    }

    /**
     * Gets the TI Specification error level.
     * 
     * @return the TI Specification error level
     */
    public int getSpecITErrorLevel() {
        int ret = -1;
        SciDouble SciError = new SciDouble("erreurStorm");
        Scilab.Exec("erreurStorm=erreur");
        Double d = new Double(SciError.getData());
        if (!d.isNaN()) {
            ret = d.intValue();
        }
        Scilab.Exec("clear(''erreur'',''erreurStorm'')");
        return ret;
    }

    /**
     * Gets the Scilab data. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.scilab.services.SciCalculationService#getScilabData()
     */
    @Override
    public double[] getScilabData() {
        // TODO Auto-generated method stub
        return null;
    }

    public void startExceptionalMode() {
        nbSamples = SAMPLES_NUMBER_EXCEPTIONAL;
    }
}
