package org.seedstack.pv2.domain.color;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * Class : Color
 *
 */
@Entity
@Table(name = "PV2QTCOL")
public class Color extends BaseAggregateRoot<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long colorId;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "ORDER_ID")
	private Long order;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "witnessFailureList")
	private List<PictoFamily> witnessActiveList;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "witnessFailureList")
	private List<PictoFamily> witnessAlertList;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "witnessFailureList")
	private List<PictoFamily> witnessFailureList;

	public Color() {

	}

	Color(Long id, String color) {
		this.colorId = id;
		this.color = color;

	}

	/**
	 * @return the colorId
	 */
	public Long getEntityId() {
		return colorId;
	}

	/**
	 * @param colorId the colorId to set
	 */
	public void setEntityId(Long colorId) {
		this.colorId = colorId;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the witnessActiveList
	 */
	public List<PictoFamily> getWitnessActiveList() {
		return witnessActiveList;
	}

	/**
	 * @param witnessActiveList the witnessActiveList to set
	 */
	public void setWitnessActiveList(List<PictoFamily> witnessActiveList) {
		this.witnessActiveList = witnessActiveList;
	}

	/**
	 * @return the witnessAlertList
	 */
	public List<PictoFamily> getWitnessAlertList() {
		return witnessAlertList;
	}

	/**
	 * @param witnessAlertList the witnessAlertList to set
	 */
	public void setWitnessAlertList(List<PictoFamily> witnessAlertList) {
		this.witnessAlertList = witnessAlertList;
	}

	/**
	 * @return the witnessFailureList
	 */
	public List<PictoFamily> getWitnessFailureList() {
		return witnessFailureList;
	}

	/**
	 * @param witnessFailureList the witnessFailureList to set
	 */
	public void setWitnessFailureList(List<PictoFamily> witnessFailureList) {
		this.witnessFailureList = witnessFailureList;
	}

	/**
	 * @return the order
	 */
	public Long getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Long order) {
		this.order = order;
	}
}
