package org.funtester.plugin.selenium.model;
import org.funtester.common.at.AbstractTestActionStep;
import org.funtester.common.at.AbstractTestElement;
import org.funtester.common.at.AbstractTestOracleStep;

public class Translator {
	
	private static final String DRIVER = "driver.";
	
	public static String translateActionStep( AbstractTestActionStep step, AbstractTestElement element ) {
		System.out.println( DRIVER + find( TranslateType( "value" ), element.getName() ) + action( step.getActionName() ) );
		System.out.println( "--------" );
		return "";
	}
	
	//Obter Uma instância do elemento
	private static String find( String internalTypeReference, String internalReferenceName ) {
		return "findElment( By." + internalTypeReference + "( \"" + internalReferenceName + "\" ) ).";
	}
	
	//TODO: Traduzir as actions
	private static String action( String actionName ){
		return actionName;
	}
	
	private static String TranslateType( String type ){
		
		if( type.equals( "id" ) ) 				return "id";
		if( type.equals( "name" ) ) 			return "name";
		if ( type.equals( "xpath" ) ) 			return "xpath";
		if( type.equals( "tagName" ) )			return "tagName";
		if( type.equals( "linkText" ) ) 		return "linkText";
		if( type.equals( "className" ) ) 		return "className";
		if( type.equals( "cssSelector" ) )	    return "cssSelector";
		if( type.equals( "partialLinkText" ) )  return "partialLinkText";
		
		return "id";
	}
	
}
