package com.inetpsa.poc00.domain.genomelcdvdictionary;

import java.util.List;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleDto;

	/**
 * The Class GenomeLCDVDictionaryDto.
 */
public class GenomeLCDVDictionaryDto {

	/** The entity id. */
	protected Long entityId;

	/** The genome lcdv code list dto. */
	private List<GenomeLCDVCodeDto> genomeLcdvCodeListDto;

	/** The genome tvv rule dy dto. */
	private List<GenomeTVVRuleDto> genomeTVVRuleDyDto;

	/** The fr label. */
	private String frLabel;

	/** The dictionary value. */
	private String dictionaryValue;

	/** The kmat. */
	private String kmat;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeLCDVDictionaryDto() {
		super();

	}

	/**
	 * Constructor GenomeLCDVDictionaryDto .
	 * 
	 * @param lcdvDicFrLabel - FrLable to set
	 * @param dictionaryValue - Dictionary Value to set
	 * @param kmat - kmat value to set
	 */
	public GenomeLCDVDictionaryDto(String lcdvDicFrLabel, String dictionaryValue, String kmat) {

		this.frLabel = lcdvDicFrLabel;
		this.dictionaryValue = dictionaryValue;
		this.kmat = kmat;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
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
	 * Gets the fr label.
	 * 
	 * @return the fr label
	 */
	public String getFrLabel() {
		return frLabel;
	}

	/**
	 * Sets the fr label.
	 * 
	 * @param frLabel the new fr label
	 */
	public void setFrLabel(String frLabel) {
		this.frLabel = frLabel;
	}

	/**
	 * Gets the dictionary value.
	 * 
	 * @return the dictionary value
	 */
	public String getDictionaryValue() {
		return dictionaryValue;
	}

	/**
	 * Sets the dictionary value.
	 * 
	 * @param dictionaryValue the new dictionary value
	 */
	public void setDictionaryValue(String dictionaryValue) {
		this.dictionaryValue = dictionaryValue;
	}

	/**
	 * Gets the kmat.
	 * 
	 * @return the kmat
	 */
	public String getKmat() {
		return kmat;
	}

	/**
	 * Sets the kmat.
	 * 
	 * @param kmat the new kmat
	 */
	public void setKmat(String kmat) {
		this.kmat = kmat;
	}

	/**
	 * Gets the genome lcdv code list dto.
	 * 
	 * @return the genome lcdv code list dto
	 */
	public List<GenomeLCDVCodeDto> getGenomeLcdvCodeListDto() {
		return genomeLcdvCodeListDto;
	}

	/**
	 * Sets the genome lcdv code list dto.
	 * 
	 * @param genomeLcdvCodeListDto the new genome lcdv code list dto
	 */
	public void setGenomeLcdvCodeListDto(List<GenomeLCDVCodeDto> genomeLcdvCodeListDto) {
		this.genomeLcdvCodeListDto = genomeLcdvCodeListDto;
	}

	/**
	 * Gets the genome tvv rule dy dto.
	 * 
	 * @return the genome tvv rule dy dto
	 */
	public List<GenomeTVVRuleDto> getGenomeTVVRuleDyDto() {
		return genomeTVVRuleDyDto;
	}

	/**
	 * Sets the genome tvv rule dy dto.
	 * 
	 * @param genomeTVVRuleDyDto the new genome tvv rule dy dto
	 */
	public void setGenomeTVVRuleDyDto(List<GenomeTVVRuleDto> genomeTVVRuleDyDto) {
		this.genomeTVVRuleDyDto = genomeTVVRuleDyDto;
	}

}
