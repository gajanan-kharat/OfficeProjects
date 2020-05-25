package org.seedstack.pv2.infrastructure.data.type;

import java.util.List;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;

/**
 * The Class TypeDTO.
 */
public class TypeDTO {

	/** The type ID. */
	private Long typeID;
	
	/** The type label. */
	private String typeLabel;
	
	/** The parent type. */
	private TypeDTO parentType;
	
	/** The parent type list. */
	private List<TypeDTO> parentTypeList;
	
	/** The type list. */
	private List<PictoFamilyDTO> typeList;

	/**
	 * Gets the type ID.
	 *
	 * @return the typeID
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getTypeID() {
		return typeID;
	}

	/**
	 * Sets the type ID.
	 *
	 * @param typeID the typeID to set
	 */
	public void setTypeID(Long typeID) {
		this.typeID = typeID;
	}

	/**
	 * Gets the type label.
	 *
	 * @return the typeLable
	 */
	@MatchingFactoryParameter(index = 1)
	public String getTypeLabel() {
		return typeLabel;
	}

	/**
	 * Sets the type label.
	 *
	 * @param typeLabel the new type label
	 */
	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	/**
	 * Gets the parent type.
	 *
	 * @return the parentType
	 */
	@MatchingFactoryParameter(index = 2)
	public TypeDTO getParentType() {
		return parentType;
	}

	/**
	 * Sets the parent type.
	 *
	 * @param parentType the parentType to set
	 */
	public void setParentType(TypeDTO parentType) {
		this.parentType = parentType;
	}

	/**
	 * Gets the parent type list.
	 *
	 * @return the parentTypeList
	 */
	@MatchingFactoryParameter(index = 3)
	public List<TypeDTO> getParentTypeList() {
		return parentTypeList;
	}

	/**
	 * Sets the parent type list.
	 *
	 * @param parentTypeList the parentTypeList to set
	 */
	public void setParentTypeList(List<TypeDTO> parentTypeList) {
		this.parentTypeList = parentTypeList;
	}

	/**
	 * Gets the type list.
	 *
	 * @return the typeList
	 */
	@MatchingFactoryParameter(index = 4)
	public List<PictoFamilyDTO> getTypeList() {
		return typeList;
	}

	/**
	 * Sets the type list.
	 *
	 * @param typeList the typeList to set
	 */
	public void setTypeList(List<PictoFamilyDTO> typeList) {
		this.typeList = typeList;
	}

}
