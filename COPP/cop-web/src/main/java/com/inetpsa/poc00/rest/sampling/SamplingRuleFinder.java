package com.inetpsa.poc00.rest.sampling;
import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;


/**
 * The Interface SamplingRuleFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)

public interface SamplingRuleFinder {

/**
 * Gets the all sampling rule.
 *
 * @return the all sampling rule
 */
public List<SamplingRuleRepresentation> getAllSamplingRule();

}
