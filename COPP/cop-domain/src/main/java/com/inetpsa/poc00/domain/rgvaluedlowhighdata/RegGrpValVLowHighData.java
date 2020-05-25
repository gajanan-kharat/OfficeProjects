/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.rgvaluedlowhighdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * The customer aggregate root.
 */

@Entity
@Table(name = "COPQTRVD")
public class RegGrpValVLowHighData extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	protected Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The crrv high. */
	@Column(name = "CRR_V_HIGH")
	private double crrvHigh;

	/** The crrv ind. */
	@Column(name = "CRR_V_IND")
	private double crrvInd;

	/** The crrv low. */
	@Column(name = "CRR_V_LOW")
	private double crrvLow;

	/** The f 0 v high. */
	@Column(name = "F0_V_HIGH")
	private double f0vHigh;

	/** The f 0 v ind. */
	@Column(name = "F0_V_IND")
	private double f0vInd;

	/** The f 0 v low. */
	@Column(name = "F0_V_LOW")
	private double f0vLow;

	/** The f 1 v high. */
	@Column(name = "F1_V_HIGH")
	private double f1vHigh;

	/** The f 1 v ind. */
	@Column(name = "F1_V_IND")
	private double f1vInd;

	/** The f 1 v low. */
	@Column(name = "F1_V_LOW")
	private double f1vLow;

	/** The f 2 v high. */
	@Column(name = "F2_V_HIGH")
	private double f2vHigh;

	/** The f 2 v ind. */
	@Column(name = "F2_V_IND")
	private double f2vInd;

	/** The f 2 v low. */
	@Column(name = "F2_V_LOW")
	private double f2vLow;

	/** The scx V high. */
	@Column(name = "SCX_V_HIGH")
	private double scxVHigh;

	/** The scx V ind. */
	@Column(name = "SCX_V_IND")
	private double scxVInd;

	/** The scx V low. */
	@Column(name = "SCX_V_LOW")
	private double scxVLow;

	/** The masse vhigh. */
	@Column(name = "MASSE_V_HIGH")
	private int masseVhigh;

	/** The mass V ind. */
	@Column(name = "MASSE_V_IND")
	private int massVInd;

	/** The mass V low. */
	@Column(name = "MASSE_V_LOW")
	private int massVLow;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public RegGrpValVLowHighData() {
		// Default Constructor
	}

	/* (non-Javadoc)
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
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the crrv high.
	 *
	 * @return the crrv high
	 */
	public double getCrrvHigh() {
		return crrvHigh;
	}

	/**
	 * Sets the crrv high.
	 *
	 * @param crrvHigh the new crrv high
	 */
	public void setCrrvHigh(double crrvHigh) {
		this.crrvHigh = crrvHigh;
	}

	/**
	 * Gets the crrv ind.
	 *
	 * @return the crrv ind
	 */
	public double getCrrvInd() {
		return crrvInd;
	}

	/**
	 * Sets the crrv ind.
	 *
	 * @param crrvInd the new crrv ind
	 */
	public void setCrrvInd(double crrvInd) {
		this.crrvInd = crrvInd;
	}

	/**
	 * Gets the crrv low.
	 *
	 * @return the crrv low
	 */
	public double getCrrvLow() {
		return crrvLow;
	}

	/**
	 * Sets the crrv low.
	 *
	 * @param crrvLow the new crrv low
	 */
	public void setCrrvLow(double crrvLow) {
		this.crrvLow = crrvLow;
	}

	/**
	 * Gets the f 0 v high.
	 *
	 * @return the f 0 v high
	 */
	public double getF0vHigh() {
		return f0vHigh;
	}

	/**
	 * Sets the f 0 v high.
	 *
	 * @param f0vHigh the new f 0 v high
	 */
	public void setF0vHigh(double f0vHigh) {
		this.f0vHigh = f0vHigh;
	}

	/**
	 * Gets the f 0 v ind.
	 *
	 * @return the f 0 v ind
	 */
	public double getF0vInd() {
		return f0vInd;
	}

	/**
	 * Sets the f 0 v ind.
	 *
	 * @param f0vInd the new f 0 v ind
	 */
	public void setF0vInd(double f0vInd) {
		this.f0vInd = f0vInd;
	}

	/**
	 * Gets the f 0 v low.
	 *
	 * @return the f 0 v low
	 */
	public double getF0vLow() {
		return f0vLow;
	}

	/**
	 * Sets the f 0 v low.
	 *
	 * @param f0vLow the new f 0 v low
	 */
	public void setF0vLow(double f0vLow) {
		this.f0vLow = f0vLow;
	}

	/**
	 * Gets the f 1 v high.
	 *
	 * @return the f 1 v high
	 */
	public double getF1vHigh() {
		return f1vHigh;
	}

	/**
	 * Sets the f 1 v high.
	 *
	 * @param f1vHigh the new f 1 v high
	 */
	public void setF1vHigh(double f1vHigh) {
		this.f1vHigh = f1vHigh;
	}

	/**
	 * Gets the f 1 v ind.
	 *
	 * @return the f 1 v ind
	 */
	public double getF1vInd() {
		return f1vInd;
	}

	/**
	 * Sets the f 1 v ind.
	 *
	 * @param f1vInd the new f 1 v ind
	 */
	public void setF1vInd(double f1vInd) {
		this.f1vInd = f1vInd;
	}

	/**
	 * Gets the f 1 v low.
	 *
	 * @return the f 1 v low
	 */
	public double getF1vLow() {
		return f1vLow;
	}

	/**
	 * Sets the f 1 v low.
	 *
	 * @param f1vLow the new f 1 v low
	 */
	public void setF1vLow(double f1vLow) {
		this.f1vLow = f1vLow;
	}

	/**
	 * Gets the f 2 v high.
	 *
	 * @return the f 2 v high
	 */
	public double getF2vHigh() {
		return f2vHigh;
	}

	/**
	 * Sets the f 2 v high.
	 *
	 * @param f2vHigh the new f 2 v high
	 */
	public void setF2vHigh(double f2vHigh) {
		this.f2vHigh = f2vHigh;
	}

	/**
	 * Gets the f 2 v ind.
	 *
	 * @return the f 2 v ind
	 */
	public double getF2vInd() {
		return f2vInd;
	}

	/**
	 * Sets the f 2 v ind.
	 *
	 * @param f2vInd the new f 2 v ind
	 */
	public void setF2vInd(double f2vInd) {
		this.f2vInd = f2vInd;
	}

	/**
	 * Gets the f 2 v low.
	 *
	 * @return the f 2 v low
	 */
	public double getF2vLow() {
		return f2vLow;
	}

	/**
	 * Sets the f 2 v low.
	 *
	 * @param f2vLow the new f 2 v low
	 */
	public void setF2vLow(double f2vLow) {
		this.f2vLow = f2vLow;
	}

	/**
	 * Gets the scx V high.
	 *
	 * @return the scx V high
	 */
	public double getScxVHigh() {
		return scxVHigh;
	}

	/**
	 * Sets the scx V high.
	 *
	 * @param scxVHigh the new scx V high
	 */
	public void setScxVHigh(double scxVHigh) {
		this.scxVHigh = scxVHigh;
	}

	/**
	 * Gets the scx V ind.
	 *
	 * @return the scx V ind
	 */
	public double getScxVInd() {
		return scxVInd;
	}

	/**
	 * Sets the scx V ind.
	 *
	 * @param scxVInd the new scx V ind
	 */
	public void setScxVInd(double scxVInd) {
		this.scxVInd = scxVInd;
	}

	/**
	 * Gets the scx V low.
	 *
	 * @return the scx V low
	 */
	public double getScxVLow() {
		return scxVLow;
	}

	/**
	 * Sets the scx V low.
	 *
	 * @param scxVLow the new scx V low
	 */
	public void setScxVLow(double scxVLow) {
		this.scxVLow = scxVLow;
	}

	/**
	 * Gets the masse vhigh.
	 *
	 * @return the masse vhigh
	 */
	public int getMasseVhigh() {
		return masseVhigh;
	}

	/**
	 * Sets the masse vhigh.
	 *
	 * @param masseVhigh the new masse vhigh
	 */
	public void setMasseVhigh(int masseVhigh) {
		this.masseVhigh = masseVhigh;
	}

	/**
	 * Gets the mass V ind.
	 *
	 * @return the mass V ind
	 */
	public int getMassVInd() {
		return massVInd;
	}

	/**
	 * Sets the mass V ind.
	 *
	 * @param massVInd the new mass V ind
	 */
	public void setMassVInd(int massVInd) {
		this.massVInd = massVInd;
	}

	/**
	 * Gets the mass V low.
	 *
	 * @return the mass V low
	 */
	public int getMassVLow() {
		return massVLow;
	}

	/**
	 * Sets the mass V low.
	 *
	 * @param massVLow the new mass V low
	 */
	public void setMassVLow(int massVLow) {
		this.massVLow = massVLow;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return label;
	}
}
