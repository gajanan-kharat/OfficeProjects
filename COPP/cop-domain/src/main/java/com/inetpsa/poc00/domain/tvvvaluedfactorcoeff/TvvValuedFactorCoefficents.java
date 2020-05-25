/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfactorcoeff;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;

/**
 * The Class TvvValuedFactorCoefficents.
 */
@Entity
@Table(name = "COPQTVFC")
public class TvvValuedFactorCoefficents extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long entityId;

    /** The default value. */
    @Column(name = "DEFAULTVALUE")
    private double defaultValue;

    /** The fc list. */
    @ManyToOne
    @JoinColumn(name = "FCLIST_ID")
    private TvvValuedEsDepFCL fcList;

    /** The fc label. */
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "FCLABEL_ID")
    private FactorCoefficentsLabel fcLabel;

    /** The pg label. */
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "PGASLABEL_ID")
    private PollutantGasLabel pgLabel;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public TvvValuedFactorCoefficents() {
    	super();
    }

    /**
     * Instantiates a new factor coefficents.
     * 
     * @param defaultValue the default value
     * @param fcLabel the fc label
     * @param pollutantGasLabel the pollutant gas label
     */
    public TvvValuedFactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel) {
        this.defaultValue = defaultValue;
        this.fcLabel = new FactorCoefficentsLabel(fcLabel.getLabel());
        this.pgLabel = pollutantGasLabel;
    }

    /**
     * Instantiates a new factor coefficents.
     * 
     * @param defaultValue the default value
     * @param pgLabel the pg label
     */
    public TvvValuedFactorCoefficents(double defaultValue, PollutantGasLabel pgLabel) {
        this.defaultValue = defaultValue;

        this.pgLabel = pgLabel;
    }

    /**
     * Instantiates a new tvv valued factor coefficents.
	 * 
     * @param tvvValuedFactorCoefficents the tvv valued factor coefficents
     */
    public TvvValuedFactorCoefficents(TvvValuedFactorCoefficents tvvValuedFactorCoefficents) {
        this.entityId = 0;
        this.defaultValue = tvvValuedFactorCoefficents.defaultValue;
		if (tvvValuedFactorCoefficents.getFclabel() != null)
        this.fcLabel = new FactorCoefficentsLabel(tvvValuedFactorCoefficents.fcLabel);
		if (tvvValuedFactorCoefficents.getPgLabel() != null)
        this.pgLabel = new PollutantGasLabel(tvvValuedFactorCoefficents.pgLabel);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the default value.
     * 
     * @return the default value
     */
    public double getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value.
     * 
     * @param defaultValue the new default value
     */
    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the fclabel.
     * 
     * @return the fclabel
     */
    public FactorCoefficentsLabel getFclabel() {
        return fcLabel;
    }

    /**
     * Sets the fclabel.
     * 
     * @param fclabel the new fclabel
     */
    public void setFclabel(FactorCoefficentsLabel fclabel) {
        this.fcLabel = fclabel;
    }

    /**
     * Gets the pg label.
     * 
     * @return the pg label
     */
    public PollutantGasLabel getPgLabel() {
        return pgLabel;
    }

    /**
     * Sets the pg label.
     * 
     * @param pgLabel the new pg label
     */
    public void setPgLabel(PollutantGasLabel pgLabel) {
        this.pgLabel = pgLabel;
    }

    /**
     * Gets the fc list.
	 * 
     * @return the fc list
     */
    public TvvValuedEsDepFCL getFcList() {
        return fcList;
    }

    /**
     * Sets the fc list.
	 * 
     * @param fcList the new fc list
     */
    public void setFcList(TvvValuedEsDepFCL fcList) {
        this.fcList = fcList;
    }

    /**
     * to String implementation.
	 * 
     * @return the string
     */
    @Override
    public String toString() {
        if (fcLabel != null) {
            return fcLabel.getLabel();
        } else {
            return DomainConstants.EMPTY;
        }

    }
}
