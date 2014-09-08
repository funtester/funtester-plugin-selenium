package org.funtester.plugin.selenium.model;

import org.funtester.common.at.AbstractTestSuite;
import org.funtester.plugin.selenium.json.JsonMapper;

/**
 * JSON repository for a  {@link AbstractTestSuite}.
 * 
 * @author Thiago Delgado Pinto
 *
 */
public class JsonAbstractTestSuiteRepository implements AbstractTestSuiteRepository {
	
	private final String filePath;

	public JsonAbstractTestSuiteRepository(final String filePath) {
		this.filePath = filePath;
	}

	public AbstractTestSuite first() throws Exception {
		return JsonMapper.readObject( filePath, AbstractTestSuite.class );
	}

}
