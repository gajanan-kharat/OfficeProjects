package com.inetpsa.poc00.domain.genometvvrule;

import java.util.List;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryDto;

/**
 * The Class GenomeTVVRuleDto.
 */
public class GenomeTVVRuleDto {

	/** The entity id. */
	private Long entityId;

	/** The genome lcdv code value set dto. */
	private List<GenomeLCDVCodeValueDto> genomeLcdvCodeValueSetDto;

	/** The genome lcdv code set dto. */
	private List<GenomeLCDVCodeDto> genomeLcdvCodeSetDto;

	/** The genome lcdv dictionary set dto. */
	private List<GenomeLCDVDictionaryDto> genomeLcdvDictionarySetDto;

	/** The rule id. */
	private String ruleId;

	/** The kmat. */
	private String kmat;

	/** The lcdv code name. */
	private String lcdvCodeName;

	/** The lcdv code value. */
	private String lcdvCodeValue;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeTVVRuleDto() {
		super();
	}

	/**
	 * Instantiates a new genome tvv rule dto.
	 * 
	 * @param obj the obj
	 */
	public GenomeTVVRuleDto(GenomeTVVRule obj) {
		this.ruleId = obj.getRuleId();
		this.kmat = obj.getKmat();
		this.lcdvCodeName = obj.getLcdvCodeName();
		this.lcdvCodeValue = obj.getLcdvCodeValue();
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
	 * Gets the rule id.
	 * 
	 * @return the rule id
	 */
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * Sets the rule id.
	 * 
	 * @param ruleId the new rule id
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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
	 * Gets the lcdv code name.
	 * 
	 * @return the lcdv code name
	 */
	public String getLcdvCodeName() {
		return lcdvCodeName;
	}

	/**
	 * Sets the lcdv code name.
	 * 
	 * @param lcdvCodeName the new lcdv code name
	 */
	public void setLcdvCodeName(String lcdvCodeName) {
		this.lcdvCodeName = lcdvCodeName;
	}

	/**
	 * Gets the lcdv code value.
	 * 
	 * @return the lcdv code value
	 */
	public String getLcdvCodeValue() {
		return lcdvCodeValue;
	}

	/**
	 * Sets the lcdv code value.
	 * 
	 * @param lcdvCodeValue the new lcdv code value
	 */
	public void setLcdvCodeValue(String lcdvCodeValue) {
		this.lcdvCodeValue = lcdvCodeValue;
	}

	/**
	 * Gets the genome lcdv code value set dto.
	 * 
	 * @return the genome lcdv code value set dto
	 */
	public List<GenomeLCDVCodeValueDto> getGenomeLcdvCodeValueSetDto() {
		return genomeLcdvCodeValueSetDto;
	}

	/**
	 * Sets the genome lcdv code value set dto.
	 * 
	 * @param genomeLcdvCodeValueSetDto the new genome lcdv code value set dto
	 */
	public void setGenomeLcdvCodeValueSetDto(List<GenomeLCDVCodeValueDto> genomeLcdvCodeValueSetDto) {
		this.genomeLcdvCodeValueSetDto = genomeLcdvCodeValueSetDto;
	}

	/**
	 * Gets the genome lcdv code set dto.
	 * 
	 * @return the genome lcdv code set dto
	 */
	public List<GenomeLCDVCodeDto> getGenomeLcdvCodeSetDto() {
		return genomeLcdvCodeSetDto;
	}

	/**
	 * Sets the genome lcdv code set dto.
	 * 
	 * @param genomeLcdvCodeSetDto the new genome lcdv code set dto
	 */
	public void setGenomeLcdvCodeSetDto(List<GenomeLCDVCodeDto> genomeLcdvCodeSetDto) {
		this.genomeLcdvCodeSetDto = genomeLcdvCodeSetDto;
	}

	/**
	 * Gets the genome lcdv dictionary set dto.
	 * 
	 * @return the genome lcdv dictionary set dto
	 */
	public List<GenomeLCDVDictionaryDto> getGenomeLcdvDictionarySetDto() {
		return genomeLcdvDictionarySetDto;
	}

	/**
	 * Sets the genome lcdv dictionary set dto.
	 * 
	 * @param genomeLcdvDictionarySetDto the new genome lcdv dictionary set dto
	 */
	public void setGenomeLcdvDictionarySetDto(List<GenomeLCDVDictionaryDto> genomeLcdvDictionarySetDto) {
		this.genomeLcdvDictionarySetDto = genomeLcdvDictionarySetDto;
	}

}
