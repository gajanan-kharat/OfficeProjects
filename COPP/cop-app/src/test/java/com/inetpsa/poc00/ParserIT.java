/*
 * Creation : Dec 7, 2016
 */
package com.inetpsa.poc00;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.util.RulesSchemaParser;

/**
 * The Class ParserIT.
 */
@RunWith(SeedITRunner.class)
public class ParserIT {

	/** The parser. */
	RulesSchemaParser parser = new RulesSchemaParser();

	/**
	 * Test parser.
	 */
	@Test
	public void testParser() {
		parser.parseInput();
	}
}
