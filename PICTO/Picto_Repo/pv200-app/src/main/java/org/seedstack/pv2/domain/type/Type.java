package org.seedstack.pv2.domain.type;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

@Entity
@Table(name = "PV2QTTYP")
public class Type extends BaseAggregateRoot<Long> {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long typeID;

	/**
	 * Type lable information
	 */
	@Column(name = "TYPE_LABEL")
	private String typeLabel;

	/**
	 * Parent type
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_TYPE")
	private Type parentType;

	// bi-directional many-to-one association to Pv2qttyp
	@OneToMany(mappedBy = "parentType", targetEntity = Type.class)
	private List<Type> parentTypeList;

	@OneToMany(mappedBy = "typeID", targetEntity = PictoFamily.class)
	private List<PictoFamily> typeList;

	public Type() {

	}

	Type(String typeLabel, Type parentType) {
		this.typeLabel = typeLabel;
		this.parentType = parentType;

	}

	Type(Long id, String typeLabel, Type parentType) {
		this.typeID = id;
		this.typeLabel = typeLabel;
		this.parentType = parentType;

	}

	/**
	 * @return the typeID
	 */
	public Long getEntityId() {
		return typeID;
	}

	/**
	 * @param typeID
	 *            the typeID to set
	 */
	public void setEntityId(Long typeID) {
		this.typeID = typeID;
	}

	/**
	 * @return the typeLable
	 */
	public String getTypeLable() {
		return typeLabel;
	}

	/**
	 * @param typeLable
	 *            the typeLable to set
	 */
	public void setTypeLable(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	/**
	 * @return the parentType
	 */
	public Type getParentType() {
		return parentType;
	}

	/**
	 * @param parentType
	 *            the parentType to set
	 */
	public void setParentType(Type parentType) {
		this.parentType = parentType;
	}

}
