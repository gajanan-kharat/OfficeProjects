
package com.inetpsa.xml.services.oasisplm.interfaceoasis;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CalculationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalculationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MONTE-CARLO"/>
 *     &lt;enumeration value="SPEC-IT"/>
 *     &lt;enumeration value="ARITHMETIC"/>
 *     &lt;enumeration value="SEMI-QUADRATIC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalculationType")
@XmlEnum
public enum CalculationType {

    @XmlEnumValue("MONTE-CARLO")
    MONTE_CARLO("MONTE-CARLO"),
    @XmlEnumValue("SPEC-IT")
    SPEC_IT("SPEC-IT"),
    ARITHMETIC("ARITHMETIC"),
    @XmlEnumValue("SEMI-QUADRATIC")
    SEMI_QUADRATIC("SEMI-QUADRATIC");
    private final String value;

    CalculationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculationType fromValue(String v) {
        for (CalculationType c: CalculationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
