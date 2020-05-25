/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.testconditioncomment;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class TestCondnComntFactoryDefault.
 */
public class TestCondnComntFactoryDefault extends BaseFactory<TestConditionComment> implements TestConditionCommentFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.testconditioncomment.TestConditionCommentFactory#createTestConditionComment()
	 */
	@Override
	public TestConditionComment createTestConditionComment() {
		return new TestConditionComment();
	}

}
