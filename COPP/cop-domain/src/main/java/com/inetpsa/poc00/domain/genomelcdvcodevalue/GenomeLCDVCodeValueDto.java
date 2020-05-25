package com.inetpsa.poc00.domain.genomelcdvcodevalue;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleDto;


/**
 * The Class GenomeLCDVCodeValueDto.
 */
public class GenomeLCDVCodeValueDto {

	/** The entity id. */
	private Long entityId;

	/** The genome lcdv code dto. */
	private GenomeLCDVCodeDto genomeLcdvCodeDto;

	/** The genome tvv rule dto. */
	private GenomeTVVRuleDto genomeTvvRuleDto;

	/** The fr label. */
	private String frLabel;

	/** The z2 label. */
	private String z2Label;

	/** The lcdv code value. */
	private String lcdvCodeValue;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeLCDVCodeValueDto() {
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
	 * @param lcdvcodeValue the new lcdv code value
	 */
	public void setLcdvCodeValue(String lcdvcodeValue) {
		this.lcdvCodeValue = lcdvcodeValue;
	}

	/**
	 * Gets the genome lcdv code dto.
	 *
	 * @return the genome lcdv code dto
	 */
	public GenomeLCDVCodeDto getGenomeLcdvCodeDto() {
		return genomeLcdvCodeDto;
	}

	/**
	 * Sets the genome lcdv code dto.
	 *
	 * @param genomeLcdvCodeDto the new genome lcdv code dto
	 */
	public void setGenomeLcdvCodeDto(GenomeLCDVCodeDto genomeLcdvCodeDto) {
		this.genomeLcdvCodeDto = genomeLcdvCodeDto;
	}

	/**
	 * Gets the genome tvv rule dto.
	 *
	 * @return the genome tvv rule dto
	 */
	public GenomeTVVRuleDto getGenomeTvvRuleDto() {
		return genomeTvvRuleDto;
	}

	/**
	 * Sets the genome tvv rule dto.
	 *
	 * @param genomeTvvRuleDto the new genome tvv rule dto
	 */
	public void setGenomeTvvRuleDto(GenomeTVVRuleDto genomeTvvRuleDto) {
		this.genomeTvvRuleDto = genomeTvvRuleDto;
	}

}
