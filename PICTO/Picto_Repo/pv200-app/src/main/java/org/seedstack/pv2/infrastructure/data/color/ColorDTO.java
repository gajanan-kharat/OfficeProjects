package org.seedstack.pv2.infrastructure.data.color;

import java.util.List;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;

/**
 * Class: Color DTO
 *
 */
public class ColorDTO {

	private Long id;
	private String color;
	private List<PictoFamilyDTO> witnessActiveList;
	private List<PictoFamilyDTO> witnessAlertList;
	private List<PictoFamilyDTO> witnessFailureList;
	
	

	public ColorDTO() {
	}

	public ColorDTO(String color) {
		this.color = color;
	}

	/**
	 * @return the id
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the color
	 */
	@MatchingFactoryParameter(index = 1)
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the witnessActiveList
	 */
	@MatchingFactoryParameter(index = 2)
	public List<PictoFamilyDTO> getWitnessActiveList() {
		return witnessActiveList;
	}

	/**
	 * @param witnessActiveList
	 *            the witnessActiveList to set
	 */
	public void setWitnessActiveList(List<PictoFamilyDTO> witnessActiveList) {
		this.witnessActiveList = witnessActiveList;
	}

	/**
	 * @return the witnessAlertList
	 */
	@MatchingFactoryParameter(index = 3)
	public List<PictoFamilyDTO> getWitnessAlertList() {
		return witnessAlertList;
	}

	/**
	 * @param witnessAlertList
	 *            the witnessAlertList to set
	 */
	public void setWitnessAlertList(List<PictoFamilyDTO> witnessAlertList) {
		this.witnessAlertList = witnessAlertList;
	}

	/**
	 * @return the witnessFailureList
	 */
	@MatchingFactoryParameter(index = 4)
	public List<PictoFamilyDTO> getWitnessFailureList() {
		return witnessFailureList;
	}

	/**
	 * @param witnessFailureList
	 *            the witnessFailureList to set
	 */
	public void setWitnessFailureList(List<PictoFamilyDTO> witnessFailureList) {
		this.witnessFailureList = witnessFailureList;
	}

}
