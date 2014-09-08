package org.funtester.plugin.selenium.model;

import org.funtester.common.at.AbstractTestSuite;


/**
 * Repository for a {@code AbstractTestSuite}.
 * 
 * @author Thiago Delgado Pinto
 *
 */
public interface AbstractTestSuiteRepository {

	/**
	 * Returns the first {@link AbstractTestSuite}.
	 * @return
	 * @throws Exception
	 */
	AbstractTestSuite first() throws Exception;
	
}
