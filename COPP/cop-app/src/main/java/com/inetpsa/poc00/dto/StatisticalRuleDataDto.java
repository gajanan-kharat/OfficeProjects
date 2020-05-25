/*
 * Creation : Dec 7, 2016
 */
package com.inetpsa.poc00.dto;

import java.util.Map;

/**
 * The Class StatisticalRuleDataDto.
 */
public class StatisticalRuleDataDto {

	/** The rule name. */
	String ruleName;

	/** The max elements. */
	int maxElements;

	/** The values dto map. */
	Map<Integer, ValueDto> valuesDtoMap;

	/**
	 * Gets the rule name.
	 *
	 * @return the rule name
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * Sets the rule name.
	 *
	 * @param ruleName the new rule name
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * Gets the max elements.
	 *
	 * @return the max elements
	 */
	public int getMaxElements() {
		return maxElements;
	}

	/**
	 * Sets the max elements.
	 *
	 * @param maxElements the new max elements
	 */
	public void setMaxElements(int maxElements) {
		this.maxElements = maxElements;
	}

	/**
	 * Gets the values dto map.
	 *
	 * @return the values dto map
	 */
	public Map<Integer, ValueDto> getValuesDtoMap() {
		return valuesDtoMap;
	}

	/**
	 * Sets the values dto map.
	 *
	 * @param valuesDtoMap the values dto map
	 */
	public void setValuesDtoMap(Map<Integer, ValueDto> valuesDtoMap) {
		this.valuesDtoMap = valuesDtoMap;
	}
}
