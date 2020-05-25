/*
 * Creation : Dec 7, 2016
 */
package com.inetpsa.poc00.dto;

/**
 * The Class ValueDto.
 */
public class ValueDto {

	/** The value an. */
	Double valueAn;

	/** The value bn. */
	Double valueBn;

	/** The value in. */
	Double valueIn;

	/** The element count. */
	Integer elementCount = 0;

	private ValueDto() {

	}

	public ValueDto(Double valueAn, Double valueBn, Double valueIn, Integer elementCount) {
		this.valueAn = valueAn;
		this.valueBn = valueBn;
		this.valueIn = valueIn;
		this.elementCount = elementCount;

	}

	/**
	 * Gets the value an.
	 *
	 * @return the value an
	 */
	public Double getValueAn() {
		return valueAn;
	}

	/**
	 * Sets the value an.
	 *
	 * @param valueAn the new value an
	 */
	public void setValueAn(Double valueAn) {
		this.valueAn = valueAn;
	}

	/**
	 * Gets the value bn.
	 *
	 * @return the value bn
	 */
	public Double getValueBn() {
		return valueBn;
	}

	/**
	 * Sets the value bn.
	 *
	 * @param valueBn the new value bn
	 */
	public void setValueBn(Double valueBn) {
		this.valueBn = valueBn;
	}

	/**
	 * Gets the value in.
	 *
	 * @return the value in
	 */
	public Double getValueIn() {
		return valueIn;
	}

	/**
	 * Sets the value in.
	 *
	 * @param valueIn the new value in
	 */
	public void setValueIn(Double valueIn) {
		this.valueIn = valueIn;
	}

	/**
	 * Gets the element count.
	 *
	 * @return the element count
	 */
	public Integer getElementCount() {
		return elementCount;
	}

	/**
	 * Sets the element count.
	 *
	 * @param elementCount the new element count
	 */
	public void setElementCount(Integer elementCount) {
		this.elementCount = elementCount;
	}
}
