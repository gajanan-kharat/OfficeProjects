/*
 * Creation : Dec 7, 2016
 */
package com.inetpsa.poc00.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.dto.StatisticalRuleDataDto;
import com.inetpsa.poc00.dto.ValueDto;

/**
 * The Class RulesSchemaParser.
 */
public class RulesSchemaParser {

	/** The rules. */
	private static List<StatisticalRuleDataDto> rules = new ArrayList<>();

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(RulesSchemaParser.class);

	/**
	 * Parses the input.
	 */
	public void parseInput() {
		if (rules.isEmpty())
			parseXmlFile();

	}

	/**
	 * Parses the xml file.
	 */
	private void parseXmlFile() {

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(ConstantsApp.STATISTICAL_RULES_XML);

			// get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document dom = db.parse(in);
			parseDocument(dom);
		} catch (ParserConfigurationException pce) {
			logger.error("Error occured while parsing xml, ParserConfigurationException : ", pce);
		} catch (SAXException se) {
			logger.error("Error occured while parsing xml, SAXException : ", se);
		} catch (IOException ioe) {
			logger.error("Error occured while parsing xml, IOException : ", ioe);
		}
	}

	/**
	 * Parses the document.
	 *
	 * @param dom the dom
	 */
	private void parseDocument(Document dom) {

		Element docEle = dom.getDocumentElement();

		NodeList nl = docEle.getElementsByTagName(ConstantsApp.RULE);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				Element el = (Element) nl.item(i);

				StatisticalRuleDataDto rule = getRule(el);

				rules.add(rule);
			}
		}
	}

	/**
	 * Gets the rule.
	 *
	 * @param element the element
	 * @return the rule
	 */
	private StatisticalRuleDataDto getRule(Element element) {
		StatisticalRuleDataDto ruleDto = new StatisticalRuleDataDto();
		ruleDto.setRuleName(element.getAttribute(ConstantsApp.RULE_NAME));
		ruleDto.setMaxElements(Integer.parseInt(element.getAttribute(ConstantsApp.MAX_NO_OF_ELEMENTS)));
		NodeList nlist = element.getElementsByTagName(ConstantsApp.VALUE);
		Map<Integer, ValueDto> valuesMap = new HashMap<>();
		if (nlist != null && nlist.getLength() > 0) {

			for (int i = 0; i < nlist.getLength(); i++) {

				// get the employee element
				Element innerElement = (Element) nlist.item(i);

				Double anValue = getDoubleValue(getTextValue(innerElement, ConstantsApp.VALUE_AN));

				Double bnValue = getDoubleValue(getTextValue(innerElement, ConstantsApp.VALUE_BN));
				Double inValue = getDoubleValue(getTextValue(innerElement, ConstantsApp.VALUE_IN));
				Integer elementCount = new Integer(Integer.parseInt(getTextValue(innerElement, "ELEMENT_COUNT")));

				ValueDto valuedto = new ValueDto(anValue, bnValue, inValue, elementCount);
				valuesMap.put(elementCount, valuedto);
			}
		}
		ruleDto.setValuesDtoMap(valuesMap);
		return ruleDto;
	}

	private Double getDoubleValue(String textValue) {
		if (textValue == null)
			return null;
		String value = textValue.replaceAll(",", ".");
		if (!value.isEmpty())
			return Double.parseDouble(value);
		return new Double(0);
	}

	/**
	 * Gets the text value.
	 *
	 * @param ele the ele
	 * @param tagName the tag name
	 * @return the text value
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = "";
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Node n = nl.item(0);
			if (n.getFirstChild() != null) {
				textVal = n.getFirstChild().getNodeValue();
			}
		}

		return textVal;
	}

	/**
	 * Gets the rule values.
	 *
	 * @return the rule values
	 */
	public static List<StatisticalRuleDataDto> getRuleValues() {
		return rules;
	}

}
