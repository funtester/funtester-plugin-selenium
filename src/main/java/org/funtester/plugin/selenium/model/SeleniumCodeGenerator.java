package org.funtester.plugin.selenium.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.funtester.common.at.AbstractTestCase;
import org.funtester.common.at.AbstractTestActionStep;
import org.funtester.common.at.AbstractTestElement;
import org.funtester.common.at.AbstractTestMethod;
import org.funtester.common.at.AbstractTestOracleStep;
import org.funtester.common.at.AbstractTestStep;
import org.funtester.common.at.AbstractTestSuite;
import org.funtester.plugin.code.TestCase;
import org.funtester.plugin.code.TestMethod;

public class SeleniumCodeGenerator {

	public void generate( AbstractTestSuite suite, String mainClass, String sourcePackage, int timeOutToBeVisible ){
		List< AbstractTestCase > testCases = suite.getTestCases();
		
		//Trasnforma o código
		for( AbstractTestCase abstractTestCase : testCases ){
			try {
				writeTest( createTestCase( abstractTestCase ) );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}
	
	private TestCase createTestCase( AbstractTestCase abstractTestCase ){
		TestCase testCase = new TestCase();
		
		testCase.withName( abstractTestCase.getName() );
		
		//Adding the test methods:
		for( AbstractTestMethod abstractTestMethod : abstractTestCase.getTestMethods() ){
			TestMethod testMethod = new TestMethod();
			
			testMethod.withName( abstractTestMethod.getName() );
			
			for( AbstractTestStep abstractTestStep : abstractTestMethod.getSteps() ){	
				if( abstractTestStep instanceof AbstractTestActionStep ){
					AbstractTestActionStep actionStep = ( AbstractTestActionStep ) abstractTestStep;
					for( AbstractTestElement abstractElement : actionStep.getElements() ){
						testMethod.addCommand( Translator.translateActionStep( actionStep, abstractElement ) );	
					}
				}else{
					AbstractTestOracleStep oracleStep = ( AbstractTestOracleStep ) abstractTestStep;
					testMethod.addCommand( oracleStep.getActionName() );
				}
					
			}
			
			testCase.addMethod( testMethod );
		}
		return testCase;
	}
	
	//Transforma o código
	private void writeTest( TestCase testCase ) throws IOException{}
	
}
