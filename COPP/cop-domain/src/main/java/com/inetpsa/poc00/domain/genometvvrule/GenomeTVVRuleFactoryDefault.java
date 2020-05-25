package com.inetpsa.poc00.domain.genometvvrule;

import org.seedstack.business.domain.BaseFactory;

/*
 * Factory Class for GenomeTVVRule Entity
 */
public class GenomeTVVRuleFactoryDefault extends BaseFactory<GenomeTVVRule> implements GenomeTVVRuleFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the GenomeTVVRule
	 */
	@Override
	public GenomeTVVRule createGenomeTVVRule() {
		return new GenomeTVVRule();
	}

}
