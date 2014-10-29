package org.funtester.plugin.selenium.model;
import java.util.HashMap;
import java.util.Map;

import org.funtester.common.at.AbstractTestActionStep;
import org.funtester.common.at.AbstractTestElement;
import org.funtester.common.at.AbstractTestOracleStep;

public class Translator {
	
	private static final String DRIVER = "driver";
	private static final String EXTENDED_ACTION = "action";
	private static Map<String, String> translatedActions;
	private static String format;
	
	/*************
	 * Translate *
	 *************/
	
	public static String translateActionStep( AbstractTestActionStep step, AbstractTestElement element ) {
		setupTranslatedActions(); //Fill the list
		String translatedElement = getElement( step, element );
		return translateAction( step, element, translatedElement );
	}
	
	public static String translateOracleStep( AbstractTestOracleStep step ){
		return "";
	}
	
	/************
	 * Elements *
	 ************/
	
	//Obter Uma instância do web element
	private static String webElement( String internalTypeReference, String internalReferenceName ) {
		return  DRIVER + ".findElement( By." + internalTypeReference + "( \"" +  internalReferenceName + "\" ) )";
	}
	
	//O código varia de acordo com a ação e elemento
	private static String getElement( AbstractTestActionStep step, AbstractTestElement element ){
		
		//Selects são criados a partir de web elements
		if( step.getActionName().equals( "select" ) )
			return insideParenthesis(
						"new Select " + insideParenthesis( webElement( "id", element.getInternalName() ) )
				   );

		return webElement( "id", element.getInternalName() );
	}
	
	/***********
	 * Actions *
	 ***********/
	
	//OK
	private static String translateAction( AbstractTestActionStep step, AbstractTestElement element,  String translatedElements ) {
		
		if( step.getActionName().equals( "click" ) )
			return translatedActions.get( "click" ) + insideParenthesis( translatedElements ) + ";" + testId( step )
				   ;
		
		if( step.getActionName().equals( "type" ) )
			return translatedActions.get( "type" ) + 
				   insideParenthesis( translatedElements + "," + 
			       insideQuotes ( translateValue( element.getValue() ) ) ) + ";" + testId( step )
			       ;
		
		if( step.getActionName().equals( "select" ) )
			return translatedActions.get( "select" ) + 
				   insideParenthesis( translatedElements + "," + 
			       insideQuotes ( translateValue( element.getValue() ) ) ) + ";" + testId( step )
			       ;
		
		if( step.getActionName().equals( "select.first" ) )
			return translatedActions.get( "select.first" ) + 
				   insideParenthesis( translatedElements ) + ";" + testId( step )
				   ;
		
		if( step.getActionName().equals( "double.click" ) )
			return translatedActions.get( "doubleClick" ) + 
				   insideParenthesis( translatedElements ) + ";" + testId( step )
				   ;
		
		if( step.getActionName().equals( "drag.drop" ) )
			return translatedActions.get( "drag.drop" ) + 
				   insideParenthesis( translatedElements + "," + translatedElements ) + ";" + testId( step )
				   ;
		
		//Default values if there is no match 
		return "//Action [ " + step.getActionName() + " ] can't be translated";
	}
	
	public static String translateOracleActions( ){
		//Default values if there is no match 
		return "//Oracle [ " + "name" + " ] can't be translated";
	}
	
	//OK
	public static void setupTranslatedActions(){
		
		//Creates a translate map
		Map<String, String> reference = new HashMap<String, String>();
		
		//Commons Actions
		reference.put( "click", "clickSelenium" ); 
		reference.put( "type", "sendKeysSelenium" ); 
		reference.put( "select", "selectByVisibleTextSelenium" ); 
		reference.put( "select.first", "selectByIndexSelenium" );
		
		//Extended Actions
		reference.put( "double.click", "doubleClickSelenium" );
		reference.put( "click.hold", "clickAndHoldSelenium" );
		reference.put( "drag.drop", "dragAndDropSelenium" );
		reference.put( "key.down", "keyDownSelenium" );
		reference.put( "key.up", "keyUpSelenium" );
		
		//Set
		translatedActions = reference;
	}
	
	//Mudar
	private static String getBy( String tag ){
		return "id";
	}
	
	/********
	 * Misc *
	 ********/

	private static String insideParenthesis( String value ){
		return "( " + value + " )";
	}
	private static String insideQuotes( String value ){
		return "\"" + value + "\"";
	}
	
	private static String translateValue( Object value ){
		if( value == null )
			return "";
		return value.toString();
	}
	
	private static String testId( AbstractTestActionStep step ){
		return " // id=" + step.getId() + "|" + step.getStepId() ;
	}
}
