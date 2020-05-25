/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.corvertdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * The Class CorvetData.
 */
@Entity
@Table(name = "COPQTCVD")
public class CorvetData extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The tvv. */
	@Column(name = "TVV")
	private String tvv;

	/** The nre. */
	@Column(name = "NRE")
	private String nre;

	/** The wltp masse. */
	@Column(name = "WLTP_MASSE")
	private String wltpMasse;

	/** The wltp scx. */
	@Column(name = "WLTP_SCX")
	private String wltpScx;

	/** The wltp crr. */
	@Column(name = "WLTP_CRR")
	private String wltpCrr;

	/** The wltpf0. */
	@Column(name = "WLTP_F0")
	private String wltpf0;

	/** The wltpf1. */
	@Column(name = "WLTP_F1")
	private String wltpf1;

	/** The wltpf2. */
	@Column(name = "WLTP_F2")
	private String wltpf2;

	/** The nedc masse. */
	@Column(name = "NEDC_MASSE")
	private String nedcMasse;

	/** The nedc scx. */
	@Column(name = "NEDC_SCX")
	private String nedcScx;

	/** The nedc crr. */
	@Column(name = "NEDC_CRR")
	private String nedcCrr;

	/** The nedc f0. */
	@Column(name = "NEDC_F0")
	private String nedcF0;

	/** The nedc f1. */
	@Column(name = "NEDC_F1")
	private String nedcF1;

	/** The nedc f2. */
	@Column(name = "NEDC_F2")
	private String nedcF2;

	/** The lcdv version. */
	@Column(name = "LCDV_VERSION")
	private String lcdvVersion;

	/** The wltp co2 vin moyen. */
	@Column(name = "WLTP_CO2_VIN_MOYEN")
	private Double wltpCo2VinMoyen;

	/**
	 * Instantiates a new corvet data.
	 */
	public CorvetData() {
		super();
	}

	/**
	 * Getter entityId.
	 *
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter tvv.
	 *
	 * @return the tvv
	 */
	public String getTvv() {
		return tvv;
	}

	/**
	 * Setter tvv.
	 *
	 * @param tvv the tvv to set
	 */
	public void setTvv(String tvv) {
		this.tvv = tvv;
	}

	/**
	 * Getter nre.
	 *
	 * @return the nre
	 */
	public String getNre() {
		return nre;
	}

	/**
	 * Setter nre.
	 *
	 * @param nre the nre to set
	 */
	public void setNre(String nre) {
		this.nre = nre;
	}

	/**
	 * Getter wltpMasse.
	 *
	 * @return the wltpMasse
	 */
	public String getWltpMasse() {
		return wltpMasse;
	}

	/**
	 * Setter wltpMasse.
	 *
	 * @param wltpMasse the wltpMasse to set
	 */
	public void setWltpMasse(String wltpMasse) {
		this.wltpMasse = wltpMasse;
	}

	/**
	 * Getter wltpScx.
	 *
	 * @return the wltpScx
	 */
	public String getWltpScx() {
		return wltpScx;
	}

	/**
	 * Setter wltpScx.
	 *
	 * @param wltpScx the wltpScx to set
	 */
	public void setWltpScx(String wltpScx) {
		this.wltpScx = wltpScx;
	}

	/**
	 * Getter wltpCrr.
	 *
	 * @return the wltpCrr
	 */
	public String getWltpCrr() {
		return wltpCrr;
	}

	/**
	 * Setter wltpCrr.
	 *
	 * @param wltpCrr the wltpCrr to set
	 */
	public void setWltpCrr(String wltpCrr) {
		this.wltpCrr = wltpCrr;
	}

	/**
	 * Getter wltpf0.
	 *
	 * @return the wltpf0
	 */
	public String getWltpf0() {
		return wltpf0;
	}

	/**
	 * Setter wltpf0.
	 *
	 * @param wltpf0 the wltpf0 to set
	 */
	public void setWltpf0(String wltpf0) {
		this.wltpf0 = wltpf0;
	}

	/**
	 * Getter wltpf1.
	 *
	 * @return the wltpf1
	 */
	public String getWltpf1() {
		return wltpf1;
	}

	/**
	 * Setter wltpf1.
	 *
	 * @param wltpf1 the wltpf1 to set
	 */
	public void setWltpf1(String wltpf1) {
		this.wltpf1 = wltpf1;
	}

	/**
	 * Getter wltpf2.
	 *
	 * @return the wltpf2
	 */
	public String getWltpf2() {
		return wltpf2;
	}

	/**
	 * Setter wltpf2.
	 *
	 * @param wltpf2 the wltpf2 to set
	 */
	public void setWltpf2(String wltpf2) {
		this.wltpf2 = wltpf2;
	}

	/**
	 * Getter nedcMasse.
	 *
	 * @return the nedcMasse
	 */
	public String getNedcMasse() {
		return nedcMasse;
	}

	/**
	 * Setter nedcMasse.
	 *
	 * @param nedcMasse the nedcMasse to set
	 */
	public void setNedcMasse(String nedcMasse) {
		this.nedcMasse = nedcMasse;
	}

	/**
	 * Getter nedcScx.
	 *
	 * @return the nedcScx
	 */
	public String getNedcScx() {
		return nedcScx;
	}

	/**
	 * Setter nedcScx.
	 *
	 * @param nedcScx the nedcScx to set
	 */
	public void setNedcScx(String nedcScx) {
		this.nedcScx = nedcScx;
	}

	/**
	 * Getter nedcCrr.
	 *
	 * @return the nedcCrr
	 */
	public String getNedcCrr() {
		return nedcCrr;
	}

	/**
	 * Setter nedcCrr.
	 *
	 * @param nedcCrr the nedcCrr to set
	 */
	public void setNedcCrr(String nedcCrr) {
		this.nedcCrr = nedcCrr;
	}

	/**
	 * Getter nedcF0.
	 *
	 * @return the nedcF0
	 */
	public String getNedcF0() {
		return nedcF0;
	}

	/**
	 * Setter nedcF0.
	 *
	 * @param nedcF0 the nedcF0 to set
	 */
	public void setNedcF0(String nedcF0) {
		this.nedcF0 = nedcF0;
	}

	/**
	 * Getter nedcF1.
	 *
	 * @return the nedcF1
	 */
	public String getNedcF1() {
		return nedcF1;
	}

	/**
	 * Setter nedcF1.
	 *
	 * @param nedcF1 the nedcF1 to set
	 */
	public void setNedcF1(String nedcF1) {
		this.nedcF1 = nedcF1;
	}

	/**
	 * Getter nedcF2.
	 *
	 * @return the nedcF2
	 */
	public String getNedcF2() {
		return nedcF2;
	}

	/**
	 * Setter nedcF2.
	 *
	 * @param nedcF2 the nedcF2 to set
	 */
	public void setNedcF2(String nedcF2) {
		this.nedcF2 = nedcF2;
	}

	/**
	 * Getter lcdvVersion.
	 *
	 * @return the lcdvVersion
	 */
	public String getLcdvVersion() {
		return lcdvVersion;
	}

	/**
	 * Setter lcdvVersion.
	 *
	 * @param lcdvVersion the lcdvVersion to set
	 */
	public void setLcdvVersion(String lcdvVersion) {
		this.lcdvVersion = lcdvVersion;
	}

	/**
	 * Getter wltpCo2VinMoyen.
	 *
	 * @return the wltpCo2VinMoyen
	 */
	public Double getWltpCo2VinMoyen() {
		return wltpCo2VinMoyen;
	}

	/**
	 * Setter wltpCo2VinMoyen.
	 *
	 * @param wltpCo2VinMoyen the wltpCo2VinMoyen to set
	 */
	public void setWltpCo2VinMoyen(Double wltpCo2VinMoyen) {
		this.wltpCo2VinMoyen = wltpCo2VinMoyen;
	}

}
