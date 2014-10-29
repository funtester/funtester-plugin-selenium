package org.funtester.plugin.selenium.model;

import java.util.*;

import org.funtester.common.at.AbstractTestActionStep;
import org.funtester.common.at.AbstractTestElement;

public class TranslateAction {
	
	//Types
	private static final String COMMON_TYPE = "COMMON";
	private static final String EXTENDED_TYPE = "EXTENDED";
	
	private String translatedActionCode;
	private Map<String, String> actionReference;
	private String actionType;
	
	private AbstractTestActionStep step;
	private AbstractTestElement element;
	
	public TranslateAction( AbstractTestActionStep step, AbstractTestElement element ){
		this.step = step;
		this.element = element;
	}
	
	public void translate(){
		this.setTranslate( step.getActionName() );
		this.translateToCode();
	}
	
	private void setTranslate( String actionName ){
		
		//Creates a translate map
		Map<String, String> reference = new HashMap<String, String>();
		
		//Commons Actions
		reference.put( "click", "click" ); 
		reference.put( "type", "sendKeys" ); 
		reference.put( "select", "selectByVisibleText" ); 
		reference.put( "select.first", "selectByIndex" );
		
		//Extended Actions
		reference.put( "double.click", "doubleClick" );
		reference.put( "click.hold", "clickAndHold" );
		reference.put( "drag.drop", "dragAndDrop" );
		reference.put( "key.down", "keyDown" );
		reference.put( "key.up", "keyUp" );
		
		//Set
		this.actionReference = reference;
	}
	
	private void translateToCode( ){
		
		if( this.step.getActionName().equals( "click" ) ){
			this.translatedActionCode = "." + this.actionReference.get( "click" ) + insideParenthesis( "" );
			this.actionType = COMMON_TYPE;
			return;
		}
		
		if( this.step.getActionName().equals( "type" ) ){
			this.translatedActionCode = "." + this.actionReference.get( "type" ) + insideParenthesis( this.element.getValueOption() );
			this.actionType = COMMON_TYPE;
			return;
		}
		
		if( this.step.getActionName().equals( "select" ) ){
			this.translatedActionCode = "." + this.actionReference.get( "select" ) + insideParenthesis( this.element.getValueOption() );
			this.actionType = COMMON_TYPE;
			return;
		}
		
		if( this.step.getActionName().equals( "select.first" ) ){
			this.translatedActionCode = "." + this.actionReference.get( "select.first" ) + insideParenthesis( "0" );
			this.actionType = COMMON_TYPE;
			return;
		}
		
		//Default values if there is no match 
		this.translatedActionCode = "Action [" + this.step.getActionName() + "] can't be translated";
		this.actionType = EXTENDED_TYPE;
	}
	
	public String getCommonType(){
		return COMMON_TYPE;
	}
	
	public String getExtendedType(){
		return EXTENDED_TYPE;
	}
	
	public String getTranslatedActionCode(){
		return this.translatedActionCode;
	}
	
	public String getActionType(){
		return this.actionType;
	}
	
	private static String insideParenthesis( String value ){
		return "( " + value + " )";
	}
}
