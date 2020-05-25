package com.inetpsa.poc00.domain.genometvvrule;

import org.seedstack.business.domain.GenericFactory;


public interface GenomeTVVRuleFactory extends GenericFactory<GenomeTVVRule> {

	 /**
   * Factory create method.
   *
   * @param name   the dictionary name
   * @return the dictionary
   */
	GenomeTVVRule createGenomeTVVRule();
}