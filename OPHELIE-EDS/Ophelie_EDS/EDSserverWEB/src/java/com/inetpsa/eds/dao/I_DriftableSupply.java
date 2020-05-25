/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

/**
 * @author VAUSHELL - Frederic PEAK <fred@vaushell.com>
 */
public interface I_DriftableSupply {
    // PUBLIC
    /**
     * This method is used for retrieving 'Iasleep' data for various milestones.
     * 
     * @return Sleep current for milestone.
     */
    public Float getISleepCurrent();

    /**
     * The method is used for retrieving 'Non functioning 12,5V awake current' data for various milestones.
     * 
     * @return 12,5V awake current for milestone.
     */
    public Float getIAwake12_5();

    /**
     * The method is used for retrieving 'Non functioning 13,5V awake current' data for various milestones.
     * 
     * @return 13,5V awake current for milestone.
     */
    public Float getIAwake13_5();

    /**
     * The method is used for retrieving 'Inom Stab 12,5V (GMP OFF)' data for various milestones.
     * 
     * @return 12,5V normal current(GMP OFF) for milestone.
     */
    public Float getINomStab12_5();

    /**
     * The method is used for retrieving 'Inom Stab 13,5V (GMP ON)' data for various milestones.
     * 
     * @return 13,5V normal current(GMP ON) for milestone.
     */
    public Float getINomStab13_5();

    /**
     * The method is used for retrieving 'Iworstcase Stab 12,5V (GMP OFF)' data for various milestones.
     * 
     * @return 12,5V worst-case current(GMP OFF) for milestone.
     */
    public Float getIWorstStab12_5();

    /**
     * The method is used for retrieving 'Iworstcase Stab 13,5V (GMP ON)' data for various milestones.
     * 
     * @return 13,5V worst-case current(GMP ON) for milestone.
     */
    public Float getIWorstStab13_5();
}
