package org.funtester.plugin.selenium.model;

import java.io.File;
import java.io.FileWriter;
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
import org.funtester.plugin.code.HelperMethod;
import org.funtester.plugin.code.TestAnnotation;
import org.funtester.plugin.code.TestCase;
import org.funtester.plugin.code.TestMethod;





import org.funtester.plugin.code.Variable;
import org.openqa.selenium.WebDriver;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class SeleniumCodeGenerator {

	public void generate( AbstractTestSuite suite, String mainClass, String sourcePackage, int timeOutToBeVisible ){
		List< AbstractTestCase > testCases = suite.getTestCases();
		
		//Trasnforma o c�digo
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
		
		//Add helpers methods
		testCase = addHelperMethods( testCase );
		
		//Add packages import
		testCase = addPackageImport( testCase );
		
		//Add variables
		testCase = addVariables( testCase );
		
		testCase.withName( abstractTestCase.getName() );
		//testCase = addHelperMethods( testCase );
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
					testMethod.addCommand( Translator.translateOracleStep ( oracleStep ) );
				}
			}
			
			testCase.addMethod( testMethod );
		}
		return testCase;
	}
	
	//Utiliza o template para escrever o c�digo
	private void writeTest( TestCase testCase ) throws IOException{
		
		Configuration cfg = new Configuration();	
		cfg.setDirectoryForTemplateLoading( new File( "src/main/resources/template" ) );
		cfg.setIncompatibleImprovements( new Version( 2, 3, 20 ) );
		cfg.setDefaultEncoding( "UTF-8" );
		cfg.setLocale( Locale.US );
		cfg.setTemplateExceptionHandler( TemplateExceptionHandler.HTML_DEBUG_HANDLER );
		
		Map< String, Object > input = new HashMap< String, Object >();
		input.put( "test", createTestCase( testCase ) );
		input.put( "annotation", createForSelenium() );
		
		Template template;
		Writer fileWriter = null;
		try {
			template = cfg.getTemplate( "java.ftl" );

			Writer consoleWriter = new OutputStreamWriter( System.out );
			template.process( input, consoleWriter );

			fileWriter = new FileWriter( new File( "output.txt" ) );

			template.process( input, fileWriter );
		} catch( IOException ex ){
			ex.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		finally {
			if ( fileWriter != null ) {
				fileWriter.close();
			}
		}
	}
	
	private static TestAnnotation createForSelenium() {
		TestAnnotation t = new TestAnnotation();
		
		t.addImport( "org.testng.*" );
		
		t.withTestCase( "@Test" );
		t.withTestMethod( "@Test" );
		
		t.withCategoryStart( "( groups={" );
		t.withCategoryEnd( "} )" );
		t.withCategoryEachStart( "" );
		t.withCategoryEachEnd( "" );
		t.withCategoryEachSeparator( ", " );
		
		t.withSetUpOnce( "@BeforeClass" );
		t.withTearDownOnce( "@AfterClass" );
		
		t.withSetUp( "@BeforeMethod" );
		t.withTearDown( "@AfterMethod" );
		
		return t;
	}
	
	private static TestCase createTestCase( TestCase t ) {
		
		t.addHeaderComment( "Generated by FunTester" );
		t.withNamespace( "functional" );
	
		t.addComment( "My Test Case" );
		t.withName( "MyTestCase" );
		
		t.addCategory( "functional" );
		t.addCategory( "slow" );
		t.addCategory( "bla" );
		
		//SETUP
		//Modificar o Driver
		t.addSetUpCommand( "this.driver = new FirefoxDriver();" );
		t.addSetUpCommand( "driver.get( " + insideQuotes( "site.com" ) +" );" );
		t.addSetUpCommand( "this.action = new Actions( this.driver );" );
		
		//TEARDOWN
		t.addTearDownCommand( "this.driver.close();" );
		t.addTearDownCommand( "this.driver.quit();" );
		
		return t;
	}
	
	public static TestCase addHelperMethods( TestCase t ){
		
		String driver = "driver";
		String action = "action";
		
		//Add Click
		HelperMethod hmClick = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "clickSelenium" )
				.addArg( new Variable( "WebElement", "element" ) )
				.addCommand( "element.click();" )
				;
		t.addHelperMethod( hmClick );
		
		//Add type
		HelperMethod hmSendKeys = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "sendKeysSelenium" )
				.addArg( new Variable( "WebElement", "element" ) )
				.addArg( new Variable( "String", "value" ) )
				.addCommand( "element.sendKeys( value );" )
				;
		t.addHelperMethod( hmSendKeys );
		
		//Add select
		
		HelperMethod hmSelect = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "selectByVisibleTextSelenium" )
				.addArg( new Variable( "Select", "element" ) )
				.addArg( new Variable( "String", "value" ) )
				.addCommand( "element.selectByVisibleText( value );" )
				;
		t.addHelperMethod( hmSelect );
		
		//Add select.first
		HelperMethod hmSelectFirst = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "selectFirstSelenium" )
				.addArg( new Variable( "Select", "element" ) )
				.addCommand( "element.selectByIndex( 0 );" )
				;
		t.addHelperMethod( hmSelectFirst );
		
		//Add double.click
		HelperMethod hmDoubleClick = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "doubleClickSelenium" )
				.addArg( new Variable( "WebElement", "element" ) )
				.addCommand( action + ".doubleClick( element ).perform();" )
				;
		t.addHelperMethod( hmDoubleClick );
		
		//Add Drag.Drop
		HelperMethod hmDragDrop = ( new HelperMethod() )
				.withReturnType( "void" )
				.withName( "dragAndDropSelenium" )
				.addArg( new Variable( "WebElement", "elementDrag" ) )
				.addArg( new Variable( "WebElement", "elementDrop" ) )
				.addCommand( action + ".dragAndDrop( elementDrag, elementDrop ).perform();" )
				;
		t.addHelperMethod( hmDragDrop );	
		
		return t;
	}
	
	public static TestCase addPackageImport( TestCase t ){
		
		t.addImport( "org.openqa.selenium.By" );
		t.addImport( "org.openqa.selenium.WebDriver" );
		t.addImport( "org.openqa.selenium.firefox.FirefoxDriver" );
		t.addImport( "org.openqa.selenium.support.ui.Select" );
		t.addImport( "org.testng.annotations.Test" );
		t.addImport( "org.openqa.selenium.WebElement" );
		t.addImport( "org.openqa.selenium.interactions.Actions" );
		t.addImport( "org.testng.annotations.*;" );
		return t;
	}
	
	public static TestCase addVariables( TestCase t ){
		
		t.addAttribute( new Variable( "WebDriver" , "driver" ) );
		t.addAttribute( new Variable( "Actions" , "action" ) );
		
		return t;
	}
	
	private static String insideQuotes( String value ){
		return "\"" + value + "\"";
	}
}
