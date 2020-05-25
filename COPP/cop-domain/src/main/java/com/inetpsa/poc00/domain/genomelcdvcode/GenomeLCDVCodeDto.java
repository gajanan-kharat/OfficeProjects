package com.inetpsa.poc00.domain.genomelcdvcode;

import java.util.List;

import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryDto;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleDto;


/**
 * The Class GenomeLCDVCodeDto.
 */
/*
 * DTO Class for GenomeLCDVCode Entity
 * 
 * */
public class GenomeLCDVCodeDto {

	/** The entity id. */
	private Long entityId;

	/** The genome lcdv code value list dto. */
	private List<GenomeLCDVCodeValueDto> genomeLcdvCodeValueListDto;

	/** The genome lcdv dictionary dto. */
	private GenomeLCDVDictionaryDto genomeLCDVDictionaryDto;

	/** The genome tvv rule code dto. */
	private List<GenomeTVVRuleDto> genomeTVVRuleCodeDto;

	/** The fr label. */
	private String frLabel;

	/** The z2 label. */
	private String z2Label;

	/** The code name. */
	private String codeName;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeLCDVCodeDto() {
		super();
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
	 * Gets the z2 label.
	 * 
	 * @return the z2 label
	 */
	public String getZ2Label() {
		return z2Label;
	}

	/**
	 * Sets the z2 label.
	 * 
	 * @param z2Label the new z2 label
	 */
	public void setZ2Label(String z2Label) {
		this.z2Label = z2Label;
	}

	/**
	 * Gets the code name.
	 * 
	 * @return the code name
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * Sets the code name.
	 * 
	 * @param codeName the new code name
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * Gets the genome lcdv code value list dto.
	 * 
	 * @return the genome lcdv code value list dto
	 */
	public List<GenomeLCDVCodeValueDto> getGenomeLcdvCodeValueListDto() {
		return genomeLcdvCodeValueListDto;
	}

	/**
	 * Sets the genome lcdv code value list dto.
	 * 
	 * @param genomeLcdvCodeValueListDto the new genome lcdv code value list dto
	 */
	public void setGenomeLcdvCodeValueListDto(List<GenomeLCDVCodeValueDto> genomeLcdvCodeValueListDto) {
		this.genomeLcdvCodeValueListDto = genomeLcdvCodeValueListDto;
	}

	/**
	 * Gets the genome lcdv dictionary dto.
	 * 
	 * @return the genome lcdv dictionary dto
	 */
	public GenomeLCDVDictionaryDto getGenomeLCDVDictionaryDto() {
		return genomeLCDVDictionaryDto;
	}

	/**
	 * Sets the genome lcdv dictionary dto.
	 * 
	 * @param genomeLCDVDictionaryDto the new genome lcdv dictionary dto
	 */
	public void setGenomeLCDVDictionaryDto(GenomeLCDVDictionaryDto genomeLCDVDictionaryDto) {
		this.genomeLCDVDictionaryDto = genomeLCDVDictionaryDto;
	}

	/**
	 * Gets the genome tvv rule code dto.
	 * 
	 * @return the genomeTVVRuleCodeDto
	 */
	public List<GenomeTVVRuleDto> getGenomeTVVRuleCodeDto() {
		return genomeTVVRuleCodeDto;
	}

	/**
	 * Sets the genome tvv rule code dto.
	 * 
	 * @param genomeTVVRuleCodeDto the genomeTVVRuleCodeDto to set
	 */
	public void setGenomeTVVRuleCodeDto(List<GenomeTVVRuleDto> genomeTVVRuleCodeDto) {
		this.genomeTVVRuleCodeDto = genomeTVVRuleCodeDto;
	}

}
