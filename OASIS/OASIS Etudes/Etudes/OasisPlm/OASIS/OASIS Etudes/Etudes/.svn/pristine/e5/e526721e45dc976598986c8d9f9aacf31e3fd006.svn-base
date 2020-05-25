
package com.inetpsa.xml.services.oasisplm.interfaceoasis;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LawType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LawType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BINOMIAL"/>
 *     &lt;enumeration value="EXPO"/>
 *     &lt;enumeration value="LOG"/>
 *     &lt;enumeration value="NORMAL_PLAGE"/>
 *     &lt;enumeration value="NORMAL_STD"/>
 *     &lt;enumeration value="POISSON"/>
 *     &lt;enumeration value="RAYLEIGH"/>
 *     &lt;enumeration value="UNIFORM"/>
 *     &lt;enumeration value="WEIBULL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LawType")
@XmlEnum
public enum LawType {

    BINOMIAL,
    EXPO,
    LOG,
    NORMAL_PLAGE,
    NORMAL_STD,
    POISSON,
    RAYLEIGH,
    UNIFORM,
    WEIBULL;

    public String value() {
        return name();
    }

    public static LawType fromValue(String v) {
        return valueOf(v);
    }

}
